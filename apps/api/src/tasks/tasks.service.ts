import { Injectable, NotFoundException } from '@nestjs/common';
import { InjectRepository } from '@nestjs/typeorm';
import { Repository, DeepPartial } from 'typeorm';
import { Task } from './task.entity';
import { TaskAssignment } from './task-assignment.entity';
import { UsersService } from '../users/users.service';
import { GroupsService } from '../groups/groups.service';
import { CategoriesService } from '../categories/categories.service';
import { TaskStatus } from '../common/enums/task-status.enum';
import { TaskPriority } from '../common/enums/task-priority.enum';

@Injectable()
export class TasksService {
  constructor(
    @InjectRepository(Task) private readonly tasks: Repository<Task>,
    @InjectRepository(TaskAssignment) private readonly assignments: Repository<TaskAssignment>,
    private readonly users: UsersService,
    private readonly groups: GroupsService,
    private readonly categories: CategoriesService,
  ) {}

  async create(dto: {
    title: string;
    notes?: string;
    priority?: TaskPriority;
    dueDate?: string;
    creatorId: string;
    groupId?: string;
    categoryId?: string;
  }) {
    const creator = await this.users.findOne(dto.creatorId);
    if (!creator) throw new NotFoundException('Creator not found');

    const rawGroup = dto.groupId ? await this.groups.findOne(dto.groupId) : undefined;
    const group = rawGroup ?? undefined;

    const rawCategory = dto.categoryId ? await this.categories.findOne(dto.categoryId) : undefined;
    const category = rawCategory ?? undefined;

    const payload: DeepPartial<Task> = {
      title: dto.title,
      notes: dto.notes,
      priority: dto.priority,
      dueDate: dto.dueDate ? new Date(dto.dueDate) : undefined,
      creator,
      group,
      category,
      status: TaskStatus.OPEN,
    };

    const entity = this.tasks.create(payload);
    return this.tasks.save(entity);
  }

  findAll() { return this.tasks.find(); }
  findOne(id: string) { return this.tasks.findOne({ where: { id } }); }

  async setStatus(id: string, status: TaskStatus) {
    const t = await this.findOne(id);
    if (!t) throw new NotFoundException('Task not found');
    t.setStatus(status);
    return this.tasks.save(t);
  }

  async assign(taskId: string, userId: string) {
    const [t, u] = await Promise.all([this.findOne(taskId), this.users.findOne(userId)]);
    if (!t || !u) throw new NotFoundException('Task or User not found');
    const existing = (t.assignees ?? []).find(a => a.user.id === u.id);
    if (existing) return t;
    const a = this.assignments.create({ task: t, user: u });
    await this.assignments.save(a);
    return this.findOne(taskId);
  }

  async unassign(taskId: string, userId: string) {
    const t = await this.findOne(taskId);
    if (!t) throw new NotFoundException('Task not found');
    const toRemove = (t.assignees ?? []).find(a => a.user.id === userId);
    if (toRemove) await this.assignments.remove(toRemove);
    return this.findOne(taskId);
  }
}
