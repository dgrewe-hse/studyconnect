import { IsEmail } from 'class-validator';

export class AssignTaskDto {
  @IsEmail()
  assignee!: string;
}
