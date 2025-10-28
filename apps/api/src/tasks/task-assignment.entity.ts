import { Entity, PrimaryGeneratedColumn, ManyToOne, Column, Unique } from 'typeorm';
import { User } from '../users/user.entity';
import { Task } from './task.entity';

@Entity()
@Unique(['user', 'task'])
export class TaskAssignment {
  @PrimaryGeneratedColumn('uuid') id: string;

  @ManyToOne(() => User, u => u.assignments, { eager: true })
  user: User;

  @ManyToOne(() => Task, t => t.assignees)
  task: Task;

  @Column({ type: 'timestamptz', default: () => 'CURRENT_TIMESTAMP' })
  assignedAt: Date;

  @Column({ default: true }) active: boolean;
}
