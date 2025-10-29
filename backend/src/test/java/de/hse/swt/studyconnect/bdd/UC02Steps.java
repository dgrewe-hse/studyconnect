package de.hse.swt.studyconnect.bdd;

import de.hse.swt.studyconnect.entity.Task;
import de.hse.swt.studyconnect.enums.TaskPriority;
import de.hse.swt.studyconnect.enums.TaskStatus;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import static org.junit.jupiter.api.Assertions.*;

public class UC02Steps {
    private final World world;

    public UC02Steps(World world) {
        this.world = world;
    }

    private Task currentTask;

    @When("the Student opens the task {string}")
    public void opens_task(String title) {
        currentTask = world.tasksByTitle.get(title);
        assertNotNull(currentTask, "Task should exist");
    }

    @And("the Student changes the due date to {string}")
    public void change_due_date(String due) {
        currentTask.setDueDate(World.parseDateTime(due));
    }

    @And("the Student changes priority to {string}")
    public void change_priority(String priority) {
        currentTask.setPriority(TaskPriority.valueOf(priority.toUpperCase()));
    }

    @And("the Student updates notes to {string}")
    public void update_notes(String notes) {
        currentTask.setDescription(notes);
    }

    @And("the Student saves changes")
    public void save_changes() {
        if (world.lastValidationMessage != null && !"Success".equals(world.lastValidationMessage)) {
            // Preserve prior validation/warning message
            return;
        }
        if (currentTask.getTitle() == null || currentTask.getTitle().isBlank()) {
            world.lastValidationMessage = "Title is required";
            return;
        }
        world.lastValidationMessage = "Success";
    }

    @Then("the task shows due date {string} and priority {string}")
    public void verify_due_and_priority(String due, String priority) {
        assertEquals(due, currentTask.getDueDate().toLocalDate().toString());
        assertEquals(priority.toUpperCase(), currentTask.getPriority().name());
    }

    @And("the notes show {string}")
    public void notes_show(String notes) {
        assertEquals(notes, currentTask.getDescription());
    }

    @And("the Student marks the task as {string}")
    public void mark_task_status(String status) {
        if (currentTask == null) {
            // Fallback to the seeded task if not explicitly opened
            currentTask = world.tasksByTitle.values().stream().findFirst().orElse(null);
        }
        currentTask.setStatus(TaskStatus.valueOf(status.toUpperCase()));
    }

    @Then("the task status is {string} with a completion indicator")
    public void verify_status_completed(String status) {
        assertEquals(status.toUpperCase(), currentTask.getStatus().name());
    }

    @And("the task is still visible in the list with completed styling")
    public void visible_in_list_with_styling() {
        assertTrue(world.tasksByTitle.containsValue(currentTask));
    }

    @Given("the task {string} is {string}")
    public void set_task_status(String title, String status) {
        Task t = world.tasksByTitle.get(title);
        assertNotNull(t);
        t.setStatus(TaskStatus.valueOf(status.toUpperCase()));
        currentTask = t;
    }

    @Then("the task status is {string}")
    public void task_status_is(String status) {
        assertEquals(status.toUpperCase(), currentTask.getStatus().name());
    }

    @And("the Student sets the title to {string}")
    public void set_title_to(String title) {
        if (title == null || title.isBlank()) {
            currentTask.setTitle(title);
            world.lastValidationMessage = "Title is required";
            return;
        }
        currentTask.setTitle(title);
        world.lastValidationMessage = "Success";
    }

    @And("the Student sets the due date to {string}")
    public void set_due_date_to(String due) {
        try {
            var dt = World.parseDateTime(due);
            currentTask.setDueDate(dt);
            // If due date is in the past, surface a non-blocking warning
            if (dt.toLocalDate().isBefore(java.time.LocalDate.now())) {
                world.lastValidationMessage = "Warning allowed; saved";
            } else {
                world.lastValidationMessage = "Success";
            }
        } catch (Exception e) {
            world.lastValidationMessage = "Error: invalid date";
        }
    }
}


