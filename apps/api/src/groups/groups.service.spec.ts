/// <reference types="jest" />
import { Test, TestingModule } from '@nestjs/testing';
import { GroupsService } from './groups.service';
import { getRepositoryToken } from '@nestjs/typeorm';
import { Group } from './group.entity';
import { createMockRepo } from '../../test/test-utils';
import { UsersService } from '../users/users.service';

describe('GroupsService', () => {
  let service: GroupsService;

  const usersServiceMock = {
    findOne: jest.fn(),
  };

  beforeEach(async () => {
    const module: TestingModule = await Test.createTestingModule({
      providers: [
        GroupsService,
        { provide: getRepositoryToken(Group), useValue: createMockRepo() },
        { provide: UsersService, useValue: usersServiceMock },
      ],
    }).compile();

    service = module.get(GroupsService);
  });

  it('should be defined', () => {
    expect(service).toBeDefined();
  });
});
