package de.hse.swt.studyconnect.controller;

import de.hse.swt.studyconnect.dto.TaskCreateRequest;
import de.hse.swt.studyconnect.dto.TaskResponse;
import de.hse.swt.studyconnect.dto.TaskStatusUpdateRequest;
import de.hse.swt.studyconnect.dto.TaskUpdateRequest;
import de.hse.swt.studyconnect.dto.GroupSummary;
import de.hse.swt.studyconnect.dto.UserSummary;
import de.hse.swt.studyconnect.entity.Group;
import de.hse.swt.studyconnect.entity.Task;
import de.hse.swt.studyconnect.entity.User;
import de.hse.swt.studyconnect.enums.TaskStatus;
import de.hse.swt.studyconnect.repository.GroupRepository;
import de.hse.swt.studyconnect.repository.TaskRepository;
import de.hse.swt.studyconnect.repository.UserRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.net.URI;

/**
 * REST controller for managing personal and group tasks.
 *
 * <p>
 * The current user is identified via the {@code X-User-Id} header. This is a
 * simplification for the course setup and can later be replaced by proper
 * authentication and authorization.
 * </p>
 */
@RestController
@RequestMapping("/api/v1/tasks")
@Tag(name = "Tasks", description = "Endpoints for managing personal and group tasks")
public class TaskController {

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;
    private final GroupRepository groupRepository;

    public TaskController(TaskRepository taskRepository,
                          UserRepository userRepository,
                          GroupRepository groupRepository) {
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
        this.groupRepository = groupRepository;
    }

    /**
     * Lists personal tasks for the current user.
     */
    @GetMapping
    @Operation(
            summary = "List personal tasks",
            description = "Returns a paged list of tasks created by the current user."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Tasks returned successfully",
                    content = @Content(schema = @Schema(implementation = TaskResponse.class))),
            @ApiResponse(responseCode = "404", description = "User not found",
                    content = @Content)
    })
    public ResponseEntity<Page<TaskResponse>> listTasks(
            @Parameter(description = "Identifier of the current user", in = ParameterIn.HEADER, required = true)
            @RequestHeader("X-User-Id") Long currentUserId,
            Pageable pageable) {

        User currentUser = userRepository.findById(currentUserId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

        Page<Task> tasks = taskRepository.findByCreatedBy(currentUser, pageable);
        Page<TaskResponse> body = tasks.map(this::toTaskResponse);
        return ResponseEntity.ok(body);
    }

    /**
     * Retrieves a single task by id.
     */
    @GetMapping("/{id}")
    @Operation(summary = "Get task by id")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Task returned successfully",
                    content = @Content(schema = @Schema(implementation = TaskResponse.class))),
            @ApiResponse(responseCode = "404", description = "Task not found", content = @Content)
    })
    public ResponseEntity<TaskResponse> getTask(@PathVariable Long id) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Task not found"));
        return ResponseEntity.ok(toTaskResponse(task));
    }

    /**
     * Creates a new personal or group task.
     */
    @PostMapping
    @Operation(summary = "Create a new task")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Task created successfully",
                    content = @Content(schema = @Schema(implementation = TaskResponse.class))),
            @ApiResponse(responseCode = "400", description = "Invalid request data", content = @Content),
            @ApiResponse(responseCode = "404", description = "Related entity not found", content = @Content)
    })
    public ResponseEntity<TaskResponse> createTask(
            @Parameter(description = "Identifier of the current user", in = ParameterIn.HEADER, required = true)
            @RequestHeader("X-User-Id") Long currentUserId,
            @RequestBody TaskCreateRequest request) {

        User creator = userRepository.findById(currentUserId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

        Task task = new Task();
        task.setTitle(request.getTitle());
        task.setDescription(request.getDescription());
        task.setDueDate(request.getDueDate());
        task.setPriority(request.getPriority());
        task.setCategory(request.getCategory());
        task.setCreatedBy(creator);
        task.setStatus(TaskStatus.OPEN);

        if (request.getGroupId() != null) {
            Group group = groupRepository.findById(request.getGroupId())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Group not found"));
            task.setGroup(group);
        }

        if (request.getAssignedToId() != null) {
            User assignee = userRepository.findById(request.getAssignedToId())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Assignee not found"));
            task.setAssignedTo(assignee);
        }

        Task saved = taskRepository.save(task);
        TaskResponse body = toTaskResponse(saved);
        return ResponseEntity
                .created(URI.create("/api/v1/tasks/" + saved.getId()))
                .body(body);
    }

    /**
     * Updates an existing task.
     */
    @PutMapping("/{id}")
    @Operation(summary = "Update an existing task")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Task updated successfully",
                    content = @Content(schema = @Schema(implementation = TaskResponse.class))),
            @ApiResponse(responseCode = "404", description = "Task not found", content = @Content)
    })
    public ResponseEntity<TaskResponse> updateTask(
            @PathVariable Long id,
            @RequestBody TaskUpdateRequest request) {

        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Task not found"));

        if (request.getTitle() != null) {
            task.setTitle(request.getTitle());
        }
        if (request.getDescription() != null) {
            task.setDescription(request.getDescription());
        }
        if (request.getDueDate() != null) {
            task.setDueDate(request.getDueDate());
        }
        if (request.getPriority() != null) {
            task.setPriority(request.getPriority());
        }
        if (request.getStatus() != null) {
            task.setStatus(request.getStatus());
        }
        if (request.getCategory() != null) {
            task.setCategory(request.getCategory());
        }
        if (request.getAssignedToId() != null) {
            User assignee = userRepository.findById(request.getAssignedToId())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Assignee not found"));
            task.setAssignedTo(assignee);
        }

        Task saved = taskRepository.save(task);
        return ResponseEntity.ok(toTaskResponse(saved));
    }

    /**
     * Updates only the status of a task.
     */
    @PatchMapping("/{id}/status")
    @Operation(summary = "Update task status")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Status updated successfully",
                    content = @Content(schema = @Schema(implementation = TaskResponse.class))),
            @ApiResponse(responseCode = "404", description = "Task not found", content = @Content)
    })
    public ResponseEntity<TaskResponse> updateStatus(
            @PathVariable Long id,
            @RequestBody TaskStatusUpdateRequest request) {

        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Task not found"));

        task.setStatus(request.getStatus());
        Task saved = taskRepository.save(task);
        return ResponseEntity.ok(toTaskResponse(saved));
    }

    /**
     * Deletes a task by id.
     */
    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a task by id")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Task deleted successfully", content = @Content),
            @ApiResponse(responseCode = "404", description = "Task not found", content = @Content)
    })
    public ResponseEntity<Void> deleteTask(@PathVariable Long id) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Task not found"));
        taskRepository.delete(task);
        return ResponseEntity.noContent().build();
    }

    private TaskResponse toTaskResponse(Task task) {
        TaskResponse response = new TaskResponse();
        response.setId(task.getId());
        response.setTitle(task.getTitle());
        response.setDescription(task.getDescription());
        response.setDueDate(task.getDueDate());
        response.setPriority(task.getPriority());
        response.setStatus(task.getStatus());
        response.setCategory(task.getCategory());
        response.setCreatedAt(task.getCreatedAt());
        response.setUpdatedAt(task.getUpdatedAt());

        if (task.getCreatedBy() != null) {
            response.setCreatedBy(new UserSummary(task.getCreatedBy().getId(), task.getCreatedBy().getName()));
        }
        if (task.getAssignedTo() != null) {
            response.setAssignedTo(new UserSummary(task.getAssignedTo().getId(), task.getAssignedTo().getName()));
        }
        if (task.getGroup() != null) {
            Group group = task.getGroup();
            response.setGroup(new GroupSummary(group.getId(), group.getName(), group.getDescription(), group.getVisibility()));
        }

        return response;
    }
}

