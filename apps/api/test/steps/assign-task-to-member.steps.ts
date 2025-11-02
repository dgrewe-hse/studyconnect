import { Given, When, Then } from '@cucumber/cucumber';
import { strict as assert } from 'assert';

Given('a group {string} with members:', async function (groupName: string, table) {
  const gRes = await this.http
    .post('/groups')
    .set('Authorization', `Bearer ${this.authToken}`)
    .send({ name: groupName });
  this.groupId = gRes.body.id;

  for (const { role, user } of table.hashes()) {
    const email = user.trim();
    const displayName = email.split('@')[0];

    const uRes = await this.http
      .post('/users')
      .set('Authorization', `Bearer ${this.authToken}`)
      .send({ email, displayName, role: role.trim().toUpperCase() });

    let userId: string | undefined = uRes.body?.id;
    if (!userId) {
      const all = await this.http.get('/users').set('Authorization', `Bearer ${this.authToken}`);
      const found = (Array.isArray(all.body) ? all.body : []).find((x: any) => x.email === email);
      if (found) userId = found.id;
    }

    if (!userId) {
      console.log('Assign-step: could not determine userId for', email, 'create-res:', uRes.status, uRes.body);
    } else {
      const add = await this.http
        .post(`/groups/${this.groupId}/members/${userId}`)
        .set('Authorization', `Bearer ${this.authToken}`)
        .send();
      console.log('Assign-step: added member', email, '->', add.status);
    }
  }
});

Given('a task {string} exists in group {string}', async function (title: string, _groupName: string) {
  const res = await this.http
    .post('/tasks')
    .set('Authorization', `Bearer ${this.authToken}`)
    .send({
      title: title.replace(/^"|"$/g, ''),
      creatorId: this.currentUser?.id,
      groupId: this.groupId,
    });
  this.lastTaskId = res.body?.id;
});

Given('the current user is {string}', function (email: string) {
  this.currentUser = { id: this.currentUser?.id ?? '11111111-1111-1111-1111-111111111111', email: email.replace(/^"|"$/g, '') };
});

When('the user assigns {string} to {string}', async function (_title: string, email: string) {
  const assigneeEmail = email.replace(/^"|"$/g, '');
  console.log('Assign-step: PATCH assignee ->', { path: `/tasks/groups/${this.groupId}/tasks/${this.lastTaskId}/assignee`, groupId: this.groupId, taskId: this.lastTaskId, assignee: assigneeEmail, currentUser: this.currentUser });
  const res = await this.http
    .patch(`/tasks/groups/${this.groupId}/tasks/${this.lastTaskId}/assignee`)
    .set('Authorization', `Bearer ${this.authToken}`)
    .send({ assignee: assigneeEmail });

  this.lastStatus = res.status;
  this.lastBody = res.body;
  console.log('Assign-step: PATCH result ->', { status: res.status, body: res.body });
});

Then('the task {string} shows assignee {string}', async function (_title: string, email: string) {
  const res = await this.http
    .get(`/tasks/${this.lastTaskId}`)
    .set('Authorization', `Bearer ${this.authToken}`);

  const emailLimpio = email.replace(/^"|"$/g, '');
  const assignee = res.body?.assignee ?? res.body?.assignees?.[0]?.user?.email;
  assert.equal(assignee, emailLimpio, `Expected ${emailLimpio}, got ${assignee}. Body=${JSON.stringify(res.body)}`);
});