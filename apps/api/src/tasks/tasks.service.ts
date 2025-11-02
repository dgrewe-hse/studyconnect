import {Injectable, NotFoundException, BadRequestException, ForbiddenException,} from '@nestjs/common';
import { InjectRepository } from '@nestjs/typeorm';
import { Repository, DeepPartial, In } from 'typeorm';
import { Task } from './task.entity';
import { TaskAssignment } from './task-assignment.entity';
import { UsersService } from '../users/users.service';
import { GroupsService } from '../groups/groups.service';
import { CategoriesService } from '../categories/categories.service';
import { TaskStatus } from '../common/enums/task-status.enum';
import { TaskPriority } from '../common/enums/task-priority.enum';

const ALLOWED: Record<TaskStatus, TaskStatus[]> = {
  [TaskStatus.OPEN]: [TaskStatus.IN_PROGRESS, TaskStatus.BLOCKED],
  [TaskStatus.IN_PROGRESS]: [TaskStatus.BLOCKED, TaskStatus.COMPLETED],
  [TaskStatus.BLOCKED]: [TaskStatus.IN_PROGRESS],
  [TaskStatus.COMPLETED]: [],
  [TaskStatus.OVERDUE]: [TaskStatus.IN_PROGRESS, TaskStatus.COMPLETED],
};

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
    if (dto.dueDate && new Date(dto.dueDate) < new Date()) {
      throw new BadRequestException('Due date is in the past');
    }

    const creator = await this.users.findOne(dto.creatorId);
    if (!creator) throw new NotFoundException('Creator not found');

    let group = undefined as (typeof this.groups extends any ? any : never);
    if (dto.groupId) {
      const g = await this.groups.findOne(dto.groupId);
      if (!g) throw new NotFoundException('Group not found');
      group = g;
    }

    let category = undefined as (typeof this.categories extends any ? any : never);
    if (dto.categoryId) {
      const c = await this.categories.findOne(dto.categoryId);
      if (!c) throw new NotFoundException('Category not found');
      category = c;
    }

    const payload: DeepPartial<Task> = {
      title: dto.title,
      notes: dto.notes,
      priority: dto.priority,
      dueDate: dto.dueDate ? new Date(dto.dueDate) : undefined,
      creator,
      group: group ?? undefined,
      category: category ?? undefined,
      status: TaskStatus.OPEN,
    };

    const entity = this.tasks.create(payload);
    return this.tasks.save(entity);
  }

  findAll() {
    return this.tasks.find();
  }

  async findOne(id: string) {
    const t = await this.tasks.findOne({
      where: { id },
      relations: ['assignees','assignees.user','group','creator','category','group.owner','group.members']

    });

    if (!t) return null;

    const needsReload =
      !Array.isArray(t.assignees) ||
      t.assignees.length === 0 ||
      t.assignees.some(a => !a?.user?.id);

    if (needsReload) {
      t.assignees = await this.assignments.find({
        where: { task: { id } },
        relations: ['user'],
      });
    }

    return t;
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
  
  async assignTask(
    groupId: string,
    taskId: string,
    assigneeEmail: string,
    currentUserEmail: string,
    currentUserId?: string,
  ) {
    return (this as any).assignTaskExtension(groupId, taskId, assigneeEmail, currentUserEmail, currentUserId);
  }

  async assignTaskExtension(
    groupId: string,
    taskId: string,
    assigneeEmail: string,
    currentUserEmail: string,
    currentUserId?: string,
  ) {
    console.log('assignTask: called', { groupId, taskId, assigneeEmail, currentUserEmail, currentUserId });

    const group: any = await this.groups.findOne(groupId);
    if (!group) throw new NotFoundException('Group not found');

    const rawMembers: any[] = Array.isArray(group.members) ? group.members : [];
    const members: Array<{ user: any; role?: string }> = rawMembers.map(m => (m?.user ? { user: m.user, role: m.role } : { user: m, role: undefined }));

    const me = members.find(m => m?.user?.email === currentUserEmail) ||
              (currentUserId ? members.find(m => m?.user?.id === currentUserId) : undefined);
    const meRole = (me?.role ?? me?.user?.role ?? '') as string;
    const isAdmin = meRole.toUpperCase() === 'ADMIN' ||
      (group.owner && (group.owner.email === currentUserEmail || group.owner.id === currentUserId));
    if (!isAdmin) throw new ForbiddenException('Forbidden');

    const assigneeMember = members.find(m => m?.user?.email === assigneeEmail);
    if (!assigneeMember) throw new ForbiddenException('User is not a member of this group');

    const t = await this.findOne(taskId);
    if (!t) throw new NotFoundException('Task not found');
    if ((t as any).group?.id && (t as any).group.id !== groupId) {
      throw new NotFoundException('Task not found');
    }

    const assigneeUserId = assigneeMember.user.id;
    if ((t.assignees ?? []).some(a => a?.user?.id === assigneeUserId)) {
      return this.findOne(taskId);
    }

    const u = await this.users.findOne(assigneeUserId);
    if (!u) throw new NotFoundException('User not found');

    const assignment = this.assignments.create({
      user: { id: u.id } as any,
      task: { id: taskId } as any,
      active: true,
      assignedAt: new Date(),
    });

    t.assignees = Array.isArray(t.assignees) ? [...t.assignees, assignment] : [assignment];
    await this.tasks.save(t);

    const afterRepoCheck = await this.assignments.find({
      where: { task: { id: taskId } },
      relations: ['user'],
    });
    console.log('assignments.table =', this.assignments.metadata.tableName);
    console.log('assignments.afterRepoCheck =', afterRepoCheck.map(a => a.user?.email));

    if (!afterRepoCheck.length) {
      await this.assignments.manager.query(
        'INSERT INTO task_assignment("userId","taskId","assignedAt","active") VALUES($1,$2,$3,$4) ON CONFLICT ("userId","taskId") DO NOTHING',
        [u.id, taskId, new Date(), true],
      );

      const raw = await this.assignments.manager.query(
        'SELECT id, "userId", "taskId" FROM task_assignment WHERE "taskId" = $1',
        [taskId],
      );
      console.log('assignTask: RAW snapshot after fallback ->', raw);

      if (!raw.length) {
        throw new Error('Assignment not persisted: check table name/columns/migrations/TypeORM wiring');
      }
    }
    const after = await this.findOne(taskId);
    console.log('assignTask: task.assignees AFTER=', (after?.assignees || []).map((x: any) => x?.user?.email));
    return after;
  }


  async setStatus(id: string, next: TaskStatus) {
    const qr = this.tasks.manager.connection.createQueryRunner();
    await qr.connect();
    await qr.startTransaction();
    try {
      const task = await qr.manager.findOne(Task, { where: { id } });
      if (!task) throw new NotFoundException('Task not found');

      const current = task.status as TaskStatus;
      const allowed = ALLOWED[current] ?? [];

      if (!allowed.includes(next)) {
        throw new BadRequestException('Invalid status transition');
      }

      if (next !== current) {
        task.status = next;
        await qr.manager.save(task);
      }

      await qr.commitTransaction();
      return this.findOne(id);
    } catch (e) {
      if (qr.isTransactionActive) await qr.rollbackTransaction();
      if (e instanceof BadRequestException || e instanceof NotFoundException) throw e;
      throw new BadRequestException('Invalid status transition');
    } finally {
      await qr.release();
    }
  }

  async evaluateOverdue(now: Date) {
    const list = await this.tasks.find({
      where: { status: In([TaskStatus.OPEN, TaskStatus.IN_PROGRESS]) },
    });

    const toMark = list.filter(t => t.dueDate && new Date(t.dueDate) < now);

    for (const t of toMark) {
      t.status = TaskStatus.OVERDUE;
      await this.tasks.save(t);
    }
    return { changed: toMark.length };
  }
}
