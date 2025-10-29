package de.hse.swt.studyconnect.bdd;

import de.hse.swt.studyconnect.entity.Group;
import de.hse.swt.studyconnect.entity.User;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class CommonSteps {

    private final World world;

    public CommonSteps(World world) {
        this.world = world;
    }

    @Given("a Student is authenticated")
    public void a_student_is_authenticated() {
        User u = new User();
        u.setId(1L);
        u.setEmail("student@example.com");
        u.setName("Student");
        world.users.put(u.getEmail(), u);
        world.currentUser = u;
        assertNotNull(world.currentUser);
    }

    @And("the Student is on the My Tasks page")
    public void on_my_tasks_page() {
        // No-op for backend; presence is implied
        assertNotNull(world.currentUser);
    }

    @Given("at least one task exists titled {string} with status {string}")
    public void at_least_one_task_exists_titled_with_status(String title, String status) {
        world.createTask(title, "", "2025-11-05", "MEDIUM");
        world.tasksByTitle.get(title).setStatus(de.hse.swt.studyconnect.enums.TaskStatus.valueOf(status.toUpperCase()));
        assertTrue(world.tasksByTitle.containsKey(title));
    }

    @And("the Student has permission to create groups")
    public void student_has_permission() {
        // No explicit permission model; assume allowed
        assertNotNull(world.currentUser);
    }

    @And("the Student is Admin of the group {string} with members:")
    public void student_is_admin_of_group_with_members(String groupName, DataTable dataTable) {
        Group g = new Group();
        g.setId(10L);
        g.setName(groupName);
        g.setCreatedBy(world.currentUser);
        world.groups.put(groupName, g);
        List<String> members = dataTable.asLists().stream().skip(1).map(row -> row.get(0)).toList();
        for (String m : members) {
            User u = new User();
            u.setId((long) (world.users.size() + 2));
            u.setName(m);
            u.setEmail(m.toLowerCase() + "@example.com");
            world.users.put(u.getEmail(), u);
        }
        assertTrue(world.groups.containsKey(groupName));
    }
}


