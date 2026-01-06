package de.hse.swt.studyconnect.config;

import de.hse.swt.studyconnect.entity.User;
import de.hse.swt.studyconnect.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

/**
 * Test data initializer for the {@code test} Spring profile.
 *
 * <p>
 * When the application is started with {@code spring.profiles.active=test},
 * this configuration inserts a small, deterministic set of users into the
 * in-memory H2 database so that API smoke tests can rely on stable user
 * identifiers (e.g. {@code X-User-Id: 1}).
 * </p>
 */
@Configuration
@Profile("test")
public class TestDataInitializer {

    private static final Logger log = LoggerFactory.getLogger(TestDataInitializer.class);

    /**
     * Initializes demo users for the test profile.
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
     * </ul>
     *
     * @param userRepository repository used to persist users
     * @return a {@link CommandLineRunner} that creates the demo data
     */
    @Bean
    public CommandLineRunner testUsersInitializer(UserRepository userRepository) {
        return args -> {
            if (userRepository.count() > 0) {
                log.info("TestDataInitializer: users already present, skipping seeding.");
                return;
            }

            log.info("TestDataInitializer: inserting demo users for test profile.");

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
        };
    }
}


