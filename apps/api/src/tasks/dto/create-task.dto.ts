import { TaskPriority } from '../../common/enums/task-priority.enum';
export class CreateTaskDto {
  title: string;
  notes?: string;
  priority?: TaskPriority;
  dueDate?: string;
  creatorId: string;
  groupId?: string;
  categoryId?: string;
}