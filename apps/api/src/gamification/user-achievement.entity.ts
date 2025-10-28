// src/gamification/user-achievement.entity.ts
import { Entity, PrimaryGeneratedColumn, Column, ManyToOne } from 'typeorm';
import { User } from '../users/user.entity';

export enum Badge {
  FIRST_TASK='FIRST_TASK',
  STREAK_7='STREAK_7',
  STREAK_30='STREAK_30',
  GROUP_LEADER='GROUP_LEADER'
}

@Entity()
export class UserAchievement {
  @PrimaryGeneratedColumn('uuid') id: string;

  @ManyToOne(() => User, u => u.achievements, { eager: true })
  user: User;

  @Column({ type: 'enum', enum: Badge }) badge: Badge;

  @Column({ default: 0 }) points: number;

  @Column({ type: 'timestamptz', default: () => 'CURRENT_TIMESTAMP' })
  awardedAt: Date;
}
