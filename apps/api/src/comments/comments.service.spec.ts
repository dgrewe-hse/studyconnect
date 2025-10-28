/// <reference types="jest" />
import { Test, TestingModule } from '@nestjs/testing';
import { CommentsService } from './comments.service';
import { getRepositoryToken } from '@nestjs/typeorm';
import { Comment } from './comment.entity';
import { createMockRepo } from '../../test/test-utils';
import { UsersService } from '../users/users.service';
import { TasksService } from '../tasks/tasks.service';
import { GroupsService } from '../groups/groups.service';

describe('CommentsService', () => {
  let service: CommentsService;

  const usersServiceMock = { findOne: jest.fn() };
  const tasksServiceMock = { findOne: jest.fn() };
  const groupsServiceMock = { findOne: jest.fn() };

  beforeEach(async () => {
    const module: TestingModule = await Test.createTestingModule({
      providers: [
        CommentsService,
        { provide: getRepositoryToken(Comment), useValue: createMockRepo() },
        { provide: UsersService, useValue: usersServiceMock },
        { provide: TasksService, useValue: tasksServiceMock },
        { provide: GroupsService, useValue: groupsServiceMock },
      ],
    }).compile();

    service = module.get(CommentsService);
  });

  it('should be defined', () => {
    expect(service).toBeDefined();
  });
});
