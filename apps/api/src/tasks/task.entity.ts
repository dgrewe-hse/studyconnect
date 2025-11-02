import { Entity, PrimaryGeneratedColumn, Column, ManyToOne, OneToMany } from 'typeorm';
import { TaskStatus } from '../common/enums/task-status.enum';
import { TaskPriority } from '../common/enums/task-priority.enum';
import { User } from '../users/user.entity';
import { Group } from '../groups/group.entity';
import { TaskAssignment } from './task-assignment.entity';
import { Category } from '../categories/category.entity';

@Entity()
export class Task {
  @PrimaryGeneratedColumn('uuid') id: string;

  @Column() title: string;

  @Column({ type: 'text', nullable: true }) notes?: string;

  @Column({ type: 'enum', enum: TaskStatus, default: TaskStatus.OPEN })
  status: TaskStatus = TaskStatus.OPEN;

  @Column({ type: 'enum', enum: TaskPriority, default: TaskPriority.MEDIUM })
  priority: TaskPriority = TaskPriority.MEDIUM;

  @Column({ type: 'timestamptz', nullable: true }) dueDate?: Date;

  @ManyToOne(() => User, u => u.createdTasks, { eager: true })
  creator: User;

  @ManyToOne(() => Group, g => g.tasks, { nullable: true, eager: true })
  group?: Group;

  @ManyToOne(() => Category, c => c.tasks, { nullable: true, eager: true })
  category?: Category;

  @OneToMany(() => TaskAssignment, a => a.task, { cascade: true, eager: true })
  assignees: TaskAssignment[];

  setStatus(next: TaskStatus): TaskStatus {
    const allowed = new Map<TaskStatus, TaskStatus[]>([
      [TaskStatus.OPEN, [TaskStatus.IN_PROGRESS, TaskStatus.BLOCKED]],
      [TaskStatus.IN_PROGRESS, [TaskStatus.BLOCKED, TaskStatus.COMPLETED]],
      [TaskStatus.BLOCKED, [TaskStatus.IN_PROGRESS]],
      [TaskStatus.COMPLETED, []],
      [TaskStatus.OVERDUE, [TaskStatus.IN_PROGRESS, TaskStatus.COMPLETED]],
    ]);
    
    const allowedTransitions = allowed.get(this.status);
    if (!allowedTransitions) {
      throw new Error(`Unknown current status: ${this.status}`);
    }
    if (!allowedTransitions.includes(next)) {
      throw new Error(`Invalid status transition: ${this.status} → ${next}`);
    }

    if (next === this.status) {
      return this.status;
    }

    return next;
  }

  isOverdue(now = new Date()): boolean {
    return !!this.dueDate && this.status !== TaskStatus.COMPLETED && this.dueDate.getTime() < now.getTime();
  }
}
