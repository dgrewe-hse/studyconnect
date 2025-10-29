# UC03 — Create Study Group and Invite Member

## Summary
- **Primary actor**: Group Administrator (Student creating a group)
- **Goal**: Create a study group and invite members via email/username
- **Scope**: StudyConnect Web App
- **Level**: User goal

## One-sentence definition
Group Administrator is creating a study group and inviting a member to coordinate collaboration.

## Stakeholders and Interests
- **Admin**: Wants an easy way to set up a group and control membership
- **Invited Student**: Wants clear invitation and easy acceptance
- **System**: Must enforce visibility, capacity, and invitation expiry

## Preconditions
- User is authenticated
- User has permission to create a group (becomes Admin of the new group)

## Trigger
- User clicks "Create Group" on the `Groups` page

## Main Success Scenario (Basic Flow)
1. System displays group creation form
2. User enters Name (<= 100 chars) and Description (<= 500 chars)
3. User selects Visibility (Private | Public)
4. User reviews/sets Settings: Join approval, Task assignment permissions, Comment permissions
5. User sets Maximum members (default 20, up to 50)
6. User clicks "Create Group"
7. System validates and creates the group; user becomes Group Admin
8. System displays group page with member management section
9. User enters invitee identifier(s) (email/username) and sends invitations
10. System sends invitations with 7-day expiry and records pending status

## Alternative / Extension Flows
- 5a. Max members > 50 → System shows validation error; user corrects; resume at step 5
- 9a. Invalid email/username → System shows inline error; user corrects
- 9b. Duplicate invitation → System warns and prevents duplicate send
- 10a. Invitation delivery failure → System shows error and suggests retry

## Postconditions
- Group exists with configured settings; Admin is recorded as creator
- Invitations are persisted with expiry = 7 days

## Business Rules
- Only Admins can invite/remove members
- Group cannot be deleted while active members exist (spec restriction)
- Admin cannot remove themselves without transferring role first

## Non-Functional Requirements
- Create group operation < 1s; invitation send < 2s
- Clear feedback for invite status (pending/accepted/expired)
- Privacy: Visibility and member data exposure follow group settings

## Success Metrics
- Time-to-first-invite after group creation
- Percentage of successful invitation acceptances

## Notes for ATDD/BDD
- Scenarios for settings combinations, capacity limits, and invitation expiry handling

