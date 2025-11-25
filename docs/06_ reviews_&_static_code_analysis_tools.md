# 6 Reviews & Static Code Analysis Tools

## 6.2 Retrospective – Reflection on the Review

### **What we noticed**
During the review, we quickly realized that the process did not help us as much as expected. The main difficulty was reviewing code written in programming languages or frameworks we were unfamiliar with. Because we did not fully understand parts of the codebase, identifying real defects versus intended design choices became extremely time-consuming and often uncertain. Instead of spotting meaningful issues, we spent most of the time trying to interpret basic structures, which reduced the effectiveness of the review.

Another major challenge was the planning phase. Since we had no prior experience with the provided review templates, understanding the master plan and the review structure took longer than the actual review itself. We had to spend additional time figuring out how to apply the process correctly before we could even begin evaluating the other group’s work.

### **Effectiveness of the review method**
For our team, the chosen review approach did not prove effective. The combination of unfamiliar code, unclear expectations, and a strict template-driven process created more overhead than value. The list of findings also led to long internal discussions—especially about how to prioritize issues. Without being able to communicate directly with the other group, it was difficult to judge whether certain aspects were intentional design decisions or actual mistakes.

Because of this, the review felt more like guesswork than a structured quality assurance activity.

### **Suitability for our team**
Given our limited experience with code reviews and the lack of shared technological background with the reviewed team, reviews in this format are not very suitable for us at this stage. They may be more helpful in the future when:

- the codebase uses languages or frameworks we understand,
- we have more experience with review templates and processes, and
- communication with the authors is possible to clarify uncertainties.

### **Conclusion**
Overall, the review did not provide the expected benefits. It was time-consuming, difficult to execute, and did not lead to clear or confident findings. For our team, this type of formal review process is only useful when the reviewed material is familiar, the process is well understood, and communication between teams is possible.











# Code Review: Tasks Managment

## Functional Discrepancies
* Task Status: Code (apps/api/src/common/enums/task-status.enum.ts) implements blocked and overdue states, which are not defined in Specification 3.1.1
* Overdue Handling: Code changes database status to overdue however Specification 3.4.1 requires visual indicator also

## Technical Issues
* Type Safety: Excessive use of "as any" casting is bad practice because it bypasses TypeScript type checking

## Missing Features
* Filtering: "findAll" in service and controller files returns all records without filtering. Specification 3.1.2 requires filtering by status, category and priority


# Code Review: Groups Collaboration

## Functional Discrepancies
* Group Properties: entity is missing "visibility" and "max members" fields as required in Specification 3.2.1
* Data Privacy: "findAll" returns all groups in the database, but according to specification 5.3.1 only a list of the the users groups should be returned

## Technical Issues
* Security: No authorization checks in controller or service
* Input Validation: No DTOs used in Controller

## Missing Features
* Filtering: No way to filter groups or search by name, neither in groups controller nor in service








# Code Review: Deadlines and Notifications

## Functional Discrepancies
* Notifications: The requirement for supportive reminders/notifications is unimplemented. There is no NotificationService visible in the Repo.
* Upcoming Activities: No logic exists to filter or highlight tasks due in the near future 

## Technical Issues
* Missing Dependencies: TasksService does not inject any notification handler. Task assignments and status changes occur silently.
* Architecture: The current design forces the frontend to poll for updates, as there are no WebSockets or Push mechanisms implemented to satisfy the notification requirement.





# Code Review: Motivation

## Functional Discrepancies
* Lack of Integration: Requirement states the system shall "integrate" points for completed tasks. TasksService updates task status but never calls GamificationService. The features are completely disconnected.
* Milestone Logic: Code can save badges but lacks logic on when to award them (missing counters; Spec 3.5.1).

## Technical Issues
* Architecture: Missing event-driven architecture. TasksService should emit events (e.g., task.completed) for the GamificationService to listen to, rather than requiring tight coupling.

## Missing Features
* Progress Visualization: Specification 3.5.2 requires data for "Weekly completion charts" and "Category breakdowns". The current service only provides a raw list of badges/points, lacking the necessary aggregation endpoints.
* Group Comparison: Requirement for "Group contribution comparison" (Spec 3.5.2) is unimplemented.