/// <reference types="jest" />
import { Test, TestingModule } from '@nestjs/testing';
import { CategoriesService } from './categories.service';
import { getRepositoryToken } from '@nestjs/typeorm';
import { Category } from './category.entity';
import { createMockRepo } from '../../test/test-utils';

describe('CategoriesService', () => {
  let service: CategoriesService;

  beforeEach(async () => {
    const module: TestingModule = await Test.createTestingModule({
      providers: [
        CategoriesService,
        { provide: getRepositoryToken(Category), useValue: createMockRepo() },
      ],
    }).compile();

    service = module.get(CategoriesService);
  });

  it('should be defined', () => {
    expect(service).toBeDefined();
  });
});
