/// <reference types="jest" />
import { Test, TestingModule } from '@nestjs/testing';
import { CommentsController } from './comments.controller';
import { CommentsService } from './comments.service';

describe('CommentsController', () => {
  let controller: CommentsController;

  const commentsServiceMock = {
    create: jest.fn(),
    findForTask: jest.fn(),
    findForGroup: jest.fn(),
  };

  beforeEach(async () => {
    const module: TestingModule = await Test.createTestingModule({
      controllers: [CommentsController],
      providers: [{ provide: CommentsService, useValue: commentsServiceMock }],
    }).compile();

    controller = module.get(CommentsController);
  });

  it('should be defined', () => {
    expect(controller).toBeDefined();
  });
});
