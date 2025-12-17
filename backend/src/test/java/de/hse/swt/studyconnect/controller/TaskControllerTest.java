package de.hse.swt.studyconnect.controller;

import de.hse.swt.studyconnect.dto.TaskCreateRequest;
import de.hse.swt.studyconnect.entity.Task;
import de.hse.swt.studyconnect.entity.User;
import de.hse.swt.studyconnect.enums.TaskPriority;
import de.hse.swt.studyconnect.enums.TaskStatus;
import de.hse.swt.studyconnect.repository.GroupRepository;
import de.hse.swt.studyconnect.repository.TaskRepository;
import de.hse.swt.studyconnect.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Web MVC tests for {@link TaskController} using standalone MockMvc setup.
 */
@ExtendWith(MockitoExtension.class)
@DisplayName("TaskController Web MVC Tests")
class TaskControllerTest {

    private MockMvc mockMvc;

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private GroupRepository groupRepository;

    @InjectMocks
    private TaskController taskController;

    @BeforeEach
    void setUp() {
        this.mockMvc = MockMvcBuilders
                .standaloneSetup(taskController)
                .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
                .build();
    }

    @Test
    @DisplayName("GET /api/v1/tasks should return tasks for current user")
    void listTasksShouldReturnTasksForCurrentUser() throws Exception {
        User user = new User("user@example.com", "hash", "User");
        user.setId(1L);

        Task task = new Task("Title", "Desc", LocalDateTime.now().plusDays(1), TaskPriority.MEDIUM, user);
        task.setId(10L);
        task.setStatus(TaskStatus.OPEN);

        Page<Task> page = new PageImpl<>(List.of(task), PageRequest.of(0, 20), 1);

        Mockito.when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        Mockito.when(taskRepository.findByCreatedBy(Mockito.eq(user), Mockito.any())).thenReturn(page);

        mockMvc.perform(get("/api/v1/tasks")
                        .header("X-User-Id", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(1)))
                .andExpect(jsonPath("$.content[0].id", is(10)))
                .andExpect(jsonPath("$.content[0].title", is("Title")));
    }

    @Test
    @DisplayName("POST /api/v1/tasks should create a new task")
    void createTaskShouldCreateNewTask() throws Exception {
        User user = new User("user@example.com", "hash", "User");
        user.setId(1L);

        Mockito.when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        TaskCreateRequest request = new TaskCreateRequest("Title", LocalDateTime.parse("2025-06-30T18:00:00"), TaskPriority.HIGH);

        Task saved = new Task("Title", "null", request.getDueDate(), TaskPriority.HIGH, user);
        saved.setId(20L);
        saved.setStatus(TaskStatus.OPEN);

        Mockito.when(taskRepository.save(Mockito.any(Task.class))).thenReturn(saved);

        String json = """
                {
                  "title": "Title",
                  "dueDate": "2025-06-30T18:00:00",
                  "priority": "HIGH"
                }
                """;

        mockMvc.perform(post("/api/v1/tasks")
                        .header("X-User-Id", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", "/api/v1/tasks/20"))
                .andExpect(jsonPath("$.id", is(20)))
                .andExpect(jsonPath("$.title", is("Title")))
                .andExpect(jsonPath("$.priority", is("HIGH")))
                .andExpect(jsonPath("$.status", is("OPEN")));
    }
}

