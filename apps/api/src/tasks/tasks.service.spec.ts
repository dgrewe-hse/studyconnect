/// <reference types="jest" />
import { Test, TestingModule } from '@nestjs/testing';
import { TasksService } from './tasks.service';
import { getRepositoryToken } from '@nestjs/typeorm';
import { Task } from './task.entity';
import { TaskAssignment } from './task-assignment.entity';
import { createMockRepo } from '../../test/test-utils';
import { UsersService } from '../users/users.service';
import { GroupsService } from '../groups/groups.service';
import { CategoriesService } from '../categories/categories.service';

describe('TasksService', () => {
  let service: TasksService;

  const usersServiceMock = { findOne: jest.fn() };
  const groupsServiceMock = { findOne: jest.fn() };
  const categoriesServiceMock = { findOne: jest.fn() };

  beforeEach(async () => {
    const module: TestingModule = await Test.createTestingModule({
      providers: [
        TasksService,
        { provide: getRepositoryToken(Task), useValue: createMockRepo() },
        { provide: getRepositoryToken(TaskAssignment), useValue: createMockRepo() },
        { provide: UsersService, useValue: usersServiceMock },
        { provide: GroupsService, useValue: groupsServiceMock },
        { provide: CategoriesService, useValue: categoriesServiceMock },
      ],
    }).compile();

    service = module.get(TasksService);
  });

  it('should be defined', () => {
    expect(service).toBeDefined();
  });
});
