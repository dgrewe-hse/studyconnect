/// <reference types="jest" />
import { Test, TestingModule } from '@nestjs/testing';
import { TasksController } from './tasks.controller';
import { TasksService } from './tasks.service';

describe('TasksController', () => {
  let controller: TasksController;

  const tasksServiceMock = {
    create: jest.fn(),
    findAll: jest.fn(),
    findOne: jest.fn(),
    setStatus: jest.fn(),
    assign: jest.fn(),
    unassign: jest.fn(),
  };

  beforeEach(async () => {
    const module: TestingModule = await Test.createTestingModule({
      controllers: [TasksController],
      providers: [{ provide: TasksService, useValue: tasksServiceMock }],
    }).compile();

    controller = module.get(TasksController);
  });

  it('should be defined', () => {
    expect(controller).toBeDefined();
  });
});
