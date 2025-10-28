import { Module } from '@nestjs/common';
import { ConfigModule, ConfigService } from '@nestjs/config';
import { TypeOrmModule } from '@nestjs/typeorm';
import { AppController } from './app.controller';
import { AppService } from './app.service';
import { UsersModule } from './users/users.module';
import { HealthModule } from './health/health.module';
import { CommonModule } from './common/common.module';
import { GroupsModule } from './groups/groups.module';
import { TasksModule } from './tasks/tasks.module';
import { CategoriesModule } from './categories/categories.module';
import { CommentsModule } from './comments/comments.module';
import { GamificationModule } from './gamification/gamification.module';

@Module({
  imports: [
    ConfigModule.forRoot({ isGlobal: true }),

    TypeOrmModule.forRootAsync({
      inject: [ConfigService],
      useFactory: (cs: ConfigService) => ({
        type: 'postgres',
        host: cs.get('DATABASE_HOST', 'localhost'),
        port: parseInt(cs.get('DATABASE_PORT', '5432'), 10),
        username: cs.get('DATABASE_USER', 'postgres'),
        password: cs.get('DATABASE_PASSWORD', 'postgres'),
        database: cs.get('DATABASE_NAME', 'studyconnect'),
        autoLoadEntities: true,
        synchronize: cs.get('DB_SYNC', 'true') === 'true',
        logging: cs.get('DB_LOGGING', 'true') === 'true',
      }),
    }),

    UsersModule,
    HealthModule,
    CommonModule,
    GroupsModule,
    TasksModule,
    CategoriesModule,
    CommentsModule,
    GamificationModule,

  ],
  
  controllers: [AppController],
  providers: [AppService],
})
export class AppModule {}
