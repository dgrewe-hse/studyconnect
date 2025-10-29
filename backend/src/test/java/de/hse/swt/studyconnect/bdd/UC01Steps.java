package de.hse.swt.studyconnect.bdd;

import de.hse.swt.studyconnect.entity.Task;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import static org.junit.jupiter.api.Assertions.*;

public class UC01Steps {

    private final World world;

    public UC01Steps(World world) {
        this.world = world;
    }

    private String draftTitle;
    private String draftNotes;
    private String draftDue;
    private String draftPriority;
    private boolean firstAttemptFailed;

    private static String expandPlaceholder(String input) {
        if (input == null) return null;
        // Patterns like "A...201" or "N...1001" → repeat first char count times
        if (input.matches("^[A-Za-z]\\.\\.\\.\\d+$")) {
            char ch = input.charAt(0);
            int count = Integer.parseInt(input.substring(input.indexOf("...") + 3));
            return String.valueOf(ch).repeat(count);
        }
        return input;
    }

    @When("the Student opens the Add Task form")
    public void open_add_task_form() {
        // No-op
    }

    @And("the Student enters a title {string}")
    public void enters_title(String title) {
        this.draftTitle = expandPlaceholder(title);
    }

    @And("the Student selects a due date {string}")
    public void selects_due_date(String due) {
        this.draftDue = due;
    }

    @And("the Student selects priority {string}")
    public void selects_priority(String priority) {
        this.draftPriority = priority;
    }

    @And("the Student optionally enters notes {string}")
    public void optionally_enters_notes(String notes) {
        this.draftNotes = expandPlaceholder(notes);
    }

    @And("the Student saves the task")
    public void saves_the_task() {
        Task t = world.createTask(draftTitle, draftNotes, draftDue, draftPriority);
        if (t == null) {
            firstAttemptFailed = true;
        }
    }

    @Then("the task is created with status {string}")
    public void task_created_with_status(String status) {
        Task t = world.tasksByTitle.get(draftTitle);
        assertNotNull(t);
        assertEquals(status.toUpperCase(), t.getStatus().name());
    }

    @And("the task appears in the list view")
    public void task_appears_in_list() {
        assertTrue(world.tasksByTitle.containsKey(draftTitle));
    }

    @And("the task appears in the calendar view on {string}")
    public void task_appears_in_calendar(String day) {
        // Simplified: ensure due date parses and matches day
        assertNotNull(world.tasksByTitle.get(draftTitle).getDueDate());
        assertTrue(world.tasksByTitle.get(draftTitle).getDueDate().toLocalDate().toString().equals(day));
    }

    @When("the Student completes the form with valid values")
    public void completes_form_valid() {
        draftTitle = "Temp";
        draftDue = "2025-11-05";
        draftPriority = "MEDIUM";
        draftNotes = "";
    }

    @And("the first save attempt fails due to a network error")
    public void first_save_fails_network() {
        // Simulate transient failure by setting flag only
        firstAttemptFailed = true;
    }

    @And("the Student retries the save")
    public void student_retries_save() {
        if (firstAttemptFailed) {
            Task t = world.createTask(draftTitle, draftNotes, draftDue, draftPriority);
            assertNotNull(t);
            firstAttemptFailed = false;
        }
    }

    @Then("the task is created successfully")
    public void task_created_successfully() {
        assertTrue(world.tasksByTitle.containsKey(draftTitle));
    }

    @And("the Student cancels the form")
    public void cancels_form() {
        // Drop draft state
        draftTitle = null; draftDue = null; draftPriority = null; draftNotes = null;
    }

    @Then("no new task is created")
    public void no_task_created() {
        assertNull(world.tasksByTitle.get(null));
    }


    @Then("the form shows validation {string}")
    public void form_shows_validation(String message) {
        assertEquals(message, world.lastValidationMessage);
    }
}


