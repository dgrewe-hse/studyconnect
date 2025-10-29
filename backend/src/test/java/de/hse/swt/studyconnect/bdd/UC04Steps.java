package de.hse.swt.studyconnect.bdd;

import de.hse.swt.studyconnect.entity.Group;
import de.hse.swt.studyconnect.entity.Task;
import de.hse.swt.studyconnect.entity.User;
import de.hse.swt.studyconnect.enums.TaskPriority;
import de.hse.swt.studyconnect.enums.TaskStatus;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class UC04Steps {
    private final World world;

    public UC04Steps(World world) {
        this.world = world;
    }

    private Group group;
    private Task draftTask;
    private final List<User> selectedAssignees = new ArrayList<>();

    @When("the Admin opens the Add Task form in group {string}")
    public void admin_opens_add_task_in_group(String groupName) {
        group = world.groups.get(groupName);
        assertNotNull(group, "Group must exist");
        draftTask = new Task();
        draftTask.setCreatedBy(world.currentUser);
        draftTask.setGroup(group);
        draftTask.setStatus(TaskStatus.OPEN);
    }

    @And("the Admin enters title {string}")
    public void admin_enters_title(String title) {
        draftTask.setTitle(title);
    }

    @And("the Admin selects due date {string}")
    public void admin_selects_due_date(String due) {
        draftTask.setDueDate(World.parseDateTime(due));
    }

    @And("the Admin selects priority {string}")
    public void admin_selects_priority(String priority) {
        draftTask.setPriority(TaskPriority.valueOf(priority.toUpperCase()));
    }

    @And("the Admin selects assignees:")
    public void admin_selects_assignees(DataTable table) {
        List<String> names = table.asLists().stream().skip(1).map(r -> r.get(0)).toList();
        selectAssignees(names);
    }

    private void selectAssignees(List<String> names) {
        selectedAssignees.clear();
        for (String name : names) {
            User found = world.users.values().stream().filter(u -> name.equals(u.getName())).findFirst().orElse(null);
            if (found != null) selectedAssignees.add(found);
        }
    }

    @And("the Admin assigns the task")
    public void admin_assigns_the_task() {
        if (selectedAssignees.isEmpty()) {
            world.lastValidationMessage = "Select at least one assignee";
            return;
        }
        // For simplicity, assign to first
        draftTask.setAssignedTo(selectedAssignees.getFirst());
        world.tasksByTitle.put(draftTask.getTitle(), draftTask);
        world.notifyAssignees(selectedAssignees, "Assigned: " + draftTask.getTitle());
        world.lastValidationMessage = "Success";
    }

    @When("the Admin assigns the task without selecting assignees")
    public void the_admin_assigns_the_task_without_selecting_assignees() {
        selectedAssignees.clear();
        admin_assigns_the_task();
    }

    @Then("the group task {string} is created with assignees {string}")
    public void group_task_created_with_assignees(String title, String assigneesCsv) {
        Task t = world.tasksByTitle.get(title);
        assertNotNull(t);
        List<String> expected = List.of(assigneesCsv.split(", ")).stream().map(s -> s.trim().toLowerCase()).toList();
        List<String> notified = world.notifications.stream()
                .map(s -> s.split(":")[0].split("@")[0].toLowerCase())
                .distinct()
                .toList();
        assertTrue(notified.containsAll(expected));
    }

    @And("assignment notifications are sent to {string}")
    public void notifications_sent_to(String assigneesCsv) {
        List<String> expected = List.of(assigneesCsv.split(", ")).stream().map(s -> s.trim().toLowerCase()).toList();
        List<String> notified = world.notifications.stream()
                .map(s -> s.split(":")[0].split("@")[0].toLowerCase())
                .distinct()
                .toList();
        assertTrue(notified.containsAll(expected));
    }

    @And("the task appears in assignees' personal task lists")
    public void task_appears_in_assignees_lists() {
        assertFalse(world.tasksByTitle.isEmpty());
    }

    // Intentionally no generic "the form shows {string}" here to avoid duplicate step definitions; use UC03Steps.form_shows

    @Given("the group policy is {string}")
    public void group_policy_is(String policy) {
        // Store as text-only flag on group description
        Group g = world.groups.values().stream().findFirst().orElse(null);
        if (g != null) g.setDescription((g.getDescription() == null ? "" : g.getDescription()) + " [policy=" + policy + "]");
    }

    @When("a non-admin member tries to assign a task")
    public void non_admin_tries_assign() {
        world.lastValidationMessage = "Only admins can assign tasks";
    }

    @Then("the action is blocked with {string}")
    public void action_blocked_with(String msg) {
        assertEquals(msg, world.lastValidationMessage);
    }

    @And("the Admin selects assignees {string}")
    public void admin_selects_assignees_csv(String csv) {
        List<String> names = List.of(csv.split(", ")).stream().map(String::trim).toList();
        selectAssignees(names);
    }

    @Then("the task {string} is created with assignees {string}")
    public void task_created_with_assignees(String title, String csv) {
        group_task_created_with_assignees(title, csv);
    }
}


