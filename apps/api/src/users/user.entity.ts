import { Entity, PrimaryGeneratedColumn, Column, OneToMany, ManyToMany } from 'typeorm';
import { UserRole } from '../common/enums/user-role.enum';
import { Group } from '../groups/group.entity';
import { Task } from '../tasks/task.entity';
import { TaskAssignment } from '../tasks/task-assignment.entity';
import { UserAchievement } from '../gamification/user-achievement.entity';
import { Comment } from '../comments/comment.entity';

@Entity()
export class User {
  @PrimaryGeneratedColumn('uuid') id: string;

  @Column({ unique: true }) email: string;

  @Column() displayName: string;

  @Column({ type: 'enum', enum: UserRole, default: UserRole.STUDENT })
  role: UserRole;

  @OneToMany(() => Group, g => g.owner) ownedGroups: Group[];
  @ManyToMany(() => Group, g => g.members) groups: Group[];

  @OneToMany(() => Task, t => t.creator) createdTasks: Task[];
  @OneToMany(() => TaskAssignment, ta => ta.user) assignments: TaskAssignment[];

  @OneToMany(() => UserAchievement, ua => ua.user) achievements: UserAchievement[];
  @OneToMany(() => Comment, c => c.author) comments: Comment[];
}
