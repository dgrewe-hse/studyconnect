/// <reference types="jest" />
import { Test, TestingModule } from '@nestjs/testing';
import { GroupsController } from './groups.controller';
import { GroupsService } from './groups.service';

describe('GroupsController', () => {
  let controller: GroupsController;

  const groupsServiceMock = {
    create: jest.fn(),
    findAll: jest.fn(),
    addMember: jest.fn(),
    removeMember: jest.fn(),
  };

  beforeEach(async () => {
    const module: TestingModule = await Test.createTestingModule({
      controllers: [GroupsController],
      providers: [{ provide: GroupsService, useValue: groupsServiceMock }],
    }).compile();

    controller = module.get(GroupsController);
  });

  it('should be defined', () => {
    expect(controller).toBeDefined();
  });
});
