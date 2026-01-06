package de.hse.swt.studyconnect.config;

import de.hse.swt.studyconnect.entity.Task;
import de.hse.swt.studyconnect.entity.User;
import de.hse.swt.studyconnect.enums.TaskPriority;
import de.hse.swt.studyconnect.repository.TaskRepository;
import de.hse.swt.studyconnect.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.time.LocalDateTime;

/**
 * Test data initializer for the {@code test} Spring profile.
 *
 * <p>
 * When the application is started with {@code spring.profiles.active=test},
 * this configuration inserts a small, deterministic set of users and tasks
 * into the in-memory H2 database so that API smoke tests and E2E tests can
 * rely on stable identifiers (e.g. {@code X-User-Id: 1}, task id=1).
 * </p>
 */
@Configuration
@Profile("test")
public class TestDataInitializer {

    private static final Logger log = LoggerFactory.getLogger(TestDataInitializer.class);

    /**
     * Initializes demo users and tasks for the test profile.
     *
     * <p>
     * The H2 database is re-created on each start (see
     * {@code spring.jpa.hibernate.ddl-auto=create-drop}), so inserting
     * users in a fixed order will produce stable ids:
     * </p>
     *
     * <ul>
     *     <li>User 1: id=1, email="user1@example.com", name="Test User One"</li>
     *     <li>User 2: id=2, email="user2@example.com", name="Test User Two"</li>
     *     <li>Task 1: id=1, title="Complete Chapter 3 Reading", created by User 1</li>
     * </ul>
     *
     * @param userRepository repository used to persist users
     * @param taskRepository repository used to persist tasks
     * @return a {@link CommandLineRunner} that creates the demo data
     */
    @Bean
    public CommandLineRunner initializeTestData(UserRepository userRepository, TaskRepository taskRepository) {
        return args -> {
            if (userRepository.count() > 0) {
                log.info("TestDataInitializer: users already present, skipping seeding.");
                return;
            }

            log.info("TestDataInitializer: inserting demo users and tasks for test profile.");

            // Create users
            User user1 = new User(
                    "user1@example.com",
                    "{noop}password1",
                    "Test User One"
            );

            User user2 = new User(
                    "user2@example.com",
                    "{noop}password2",
                    "Test User Two"
            );

            userRepository.save(user1);
            userRepository.save(user2);

            log.info("TestDataInitializer: created users with ids: {}, {}",
                    user1.getId(), user2.getId());

            // Create a sample task for user1
            // Due date is set to 7 days from now
            LocalDateTime dueDate = LocalDateTime.now().plusDays(7);
            
            Task sampleTask = new Task(
                    "Complete Chapter 3 Reading",
                    "Read and summarize Chapter 3 of the textbook. Focus on sections 3.2 and 3.3.",
                    dueDate,
                    TaskPriority.MEDIUM,
                    user1
            );
            
            // Set category for better organization
            sampleTask.setCategory("Study");
            
            taskRepository.save(sampleTask);

            log.info("TestDataInitializer: created task with id: {} for user {}", 
                    sampleTask.getId(), user1.getId());
        };
    }
}


