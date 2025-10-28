/// <reference types="jest" />
import { User } from './user.entity';

describe('User entity (domain)', () => {
  it('creates with email and displayName', () => {
    const u = new User();
    u.email = 'alice@example.com';
    u.displayName = 'Alice';
    expect(u.email).toMatch(/@/);
    expect(u.displayName.length).toBeGreaterThan(0);
  });
});
