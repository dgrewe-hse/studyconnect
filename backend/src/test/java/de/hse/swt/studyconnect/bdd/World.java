package de.hse.swt.studyconnect.bdd;

import de.hse.swt.studyconnect.entity.Group;
import de.hse.swt.studyconnect.entity.Task;
import de.hse.swt.studyconnect.entity.User;
import de.hse.swt.studyconnect.enums.TaskPriority;
import de.hse.swt.studyconnect.enums.TaskStatus;
 

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Simple in-memory world/context to back BDD steps without hitting a database.
 */
public class World {
    public User currentUser;
    public final Map<String, User> users = new HashMap<>();
    public final Map<String, Group> groups = new HashMap<>();
    public final Map<String, Task> tasksByTitle = new HashMap<>();
    public final List<String> notifications = new ArrayList<>();
    public String lastValidationMessage;
    public boolean lastOperationFailed;

    public static LocalDateTime parseDateTime(String input) {
        // Accept "yyyy-MM-dd" or "yyyy-MM-dd HH:mm"
        if (input == null || input.isBlank()) return null;
        if (input.matches("\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}")) {
            return LocalDateTime.parse(input, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        }
        if (input.matches("\\d{4}-\\d{2}-\\d{2}")) {
            return LocalDate.parse(input, DateTimeFormatter.ISO_DATE).atStartOfDay();
        }
        throw new IllegalArgumentException("Invalid date/time: " + input);
    }

    public Task createTask(String title, String notes, String due, String priority) {
        if (title == null || title.isBlank()) {
            lastValidationMessage = "Title is required";
            lastOperationFailed = true;
            return null;
        }
        if (title.length() > 200) {
            lastValidationMessage = "Title must be <= 200 chars";
            lastOperationFailed = true;
            return null;
        }
        if (notes != null && notes.length() > 1000) {
            lastValidationMessage = "Notes must be <= 1000 chars";
            lastOperationFailed = true;
            return null;
        }
        LocalDateTime dueDate = due == null ? null : parseDateTime(due);
        Task task = new Task(title, notes, dueDate, TaskPriority.valueOf(priority.toUpperCase()), currentUser);
        task.setStatus(TaskStatus.OPEN);
        tasksByTitle.put(title, task);
        lastValidationMessage = "Success";
        lastOperationFailed = false;
        return task;
    }

    public void notifyAssignees(Collection<User> assignees, String message) {
        notifications.addAll(assignees.stream().map(u -> u.getEmail() + ":" + message).collect(Collectors.toList()));
    }
}


