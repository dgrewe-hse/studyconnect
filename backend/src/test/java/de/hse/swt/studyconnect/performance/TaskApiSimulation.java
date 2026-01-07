package de.hse.swt.studyconnect.performance;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;

import io.gatling.javaapi.core.*;
import io.gatling.javaapi.http.*;

import java.time.Instant;
import java.util.Iterator;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Stream;

/**
 * Gatling performance test for the Task REST API.
 *
 * <p>
 * This simulation exercises the following endpoints:
 * <ul>
 *     <li>{@code GET /api/v1/tasks} – list tasks for a user</li>
 *     <li>{@code POST /api/v1/tasks} – create a new task</li>
 * </ul>
 * The current user is identified via the {@code X-User-Id} header, just like in the
 * controller implementation.
 * </p>
 *
 * <p>
 * Configuration is driven by the following system properties (all optional):
 * <ul>
 *     <li>{@code targetUrl} – base URL of the backend (default: {@code http://localhost:8080})</li>
 *     <li>{@code userId} – numeric user id to send as {@code X-User-Id} (default: {@code 1})</li>
 * </ul>
 * Example:
 * <pre>{@code
 * mvn verify -DtargetUrl=http://localhost:8080 -DuserId=1
 * }</pre>
 * </p>
 */
public class TaskApiSimulation extends Simulation {

    /**
     * Resolve a system property with a sensible default.
     *
     * @param name         the system property name
     * @param defaultValue the default value if the property is not set or empty
     * @return the resolved value
     */
    private static String sysPropOrDefault(String name, String defaultValue) {
        String value = System.getProperty(name);
        if (value == null || value.isBlank()) {
            return defaultValue;
        }
        return value;
    }

    private final String baseUrl = sysPropOrDefault("targetUrl", "http://localhost:8080");
    private final String userId = sysPropOrDefault("userId", "1");

    /**
     * HTTP protocol configuration shared by all scenarios.
     */
    private final HttpProtocolBuilder httpProtocol = http
            .baseUrl(baseUrl)
            .acceptHeader("application/json")
            .contentTypeHeader("application/json")
            .header("X-User-Id", userId);

    /**
     * Simple feeder generating unique task titles and descriptions on the fly.
     *
     * <p>
     * Gatling Java DSL accepts a plain {@link Iterator} of maps as a feeder. Each
     * map becomes one row in the virtual user session.
     * </p>
     */
    private final Iterator<Map<String, Object>> taskFeeder =
            Stream.generate(() -> {
                        String id = UUID.randomUUID().toString();
                        return Map.<String, Object>of(
                                "title", "Perf Task " + id,
                                "description", "Performance test task created at " + Instant.now()
                        );
                    })
                    .iterator();

    /**
     * Scenario that repeatedly lists tasks for the configured user.
     */
    private final ScenarioBuilder listTasksScenario = scenario("ListTasksScenario")
            .exec(
                    http("List personal tasks")
                            .get("/api/v1/tasks")
                            .check(status().is(200))
            );

    /**
     * Scenario that repeatedly creates new tasks for the configured user.
     */
    private final ScenarioBuilder createTasksScenario = scenario("CreateTasksScenario")
            .feed(taskFeeder)
            .exec(
                    http("Create personal task")
                            .post("/api/v1/tasks")
                            .body(
                                    StringBody(
                                            """
                                            {
                                              "title": "#{title}",
                                              "description": "#{description}",
                                              "dueDate": null,
                                              "priority": null,
                                              "status": null,
                                              "category": null,
                                              "groupId": null,
                                              "assignedToId": null
                                            }
                                            """
                                    )
                            )
                            .check(status().is(201))
            );

    {
        int peakListUsers = 50;
        int peakCreateUsers = 20;

        setUp(
                listTasksScenario.injectOpen(
                        nothingFor(5),
                        rampUsersPerSec(1).to(peakListUsers).during(30),
                        constantUsersPerSec(peakListUsers).during(60)
                ),
                createTasksScenario.injectOpen(
                        nothingFor(5),
                        rampUsersPerSec(1).to(peakCreateUsers).during(30),
                        constantUsersPerSec(peakCreateUsers).during(60)
                )
        ).protocols(httpProtocol);
    }
}


