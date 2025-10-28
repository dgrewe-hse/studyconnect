import { Controller, Get, Post, Body, Param } from '@nestjs/common';
import { TasksService } from './tasks.service';
import { CreateTaskDto } from './dto/create-task.dto';
import { UpdateTaskStatusDto } from './dto/update-status.dto';

@Controller('tasks')
export class TasksController {
  constructor(private readonly tasks: TasksService) {}

  @Post()
  create(@Body() dto: CreateTaskDto) {
    return this.tasks.create(dto);
  }

  @Get() findAll() { return this.tasks.findAll(); }

  @Get(':id') findOne(@Param('id') id: string) { return this.tasks.findOne(id); }

  @Post(':id/status')
  setStatus(@Param('id') id: string, @Body() body: UpdateTaskStatusDto) {
    return this.tasks.setStatus(id, body.status);
  }

  @Post(':id/assign/:userId')
  assign(@Param('id') id: string, @Param('userId') userId: string) {
    return this.tasks.assign(id, userId);
  }

  @Post(':id/unassign/:userId')
  unassign(@Param('id') id: string, @Param('userId') userId: string) {
    return this.tasks.unassign(id, userId);
  }
}
