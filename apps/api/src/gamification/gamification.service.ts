import { Injectable } from '@nestjs/common';
import { InjectRepository } from '@nestjs/typeorm';
import { Repository } from 'typeorm';
import { UserAchievement, Badge } from './user-achievement.entity';
import { UsersService } from '../users/users.service';

@Injectable()
export class GamificationService {
  constructor(
    @InjectRepository(UserAchievement) private readonly repo: Repository<UserAchievement>,
    private readonly users: UsersService,
  ) {}

  async awardBadge(userId: string, badge: Badge, points = 10) {
    const user = await this.users.findOne(userId);
    if (!user) throw new Error('User not found');
    return this.repo.save(this.repo.create({ user, badge, points }));
  }

  getByUser(userId: string) {
    return this.repo.find({ where: { user: { id: userId } } as any });
  }
}
