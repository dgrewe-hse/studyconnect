import { Controller, Get, Post, Body, Param } from '@nestjs/common';
import { UsersService } from './users.service';
import { UserRole } from '../common/enums/user-role.enum';

@Controller('users')
export class UsersController {
  constructor(private readonly users: UsersService) {}

  @Post()
  create(@Body() body: { email: string; displayName: string; role?: UserRole }) {
    return this.users.create({ email: body.email, displayName: body.displayName, role: body.role });
  }

  @Get() findAll() { return this.users.findAll(); }

  @Get(':id') findOne(@Param('id') id: string) { return this.users.findOne(id); }
}
