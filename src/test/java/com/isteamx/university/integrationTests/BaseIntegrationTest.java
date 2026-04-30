package com.isteamx.university.integrationTests;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * Base class for integration tests that cleans up all tables before each test.
 * Tables are truncated in FK-safe order using TRUNCATE ... CASCADE.
 */
public abstract class BaseIntegrationTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @BeforeEach
    void cleanDatabase() {
        // H2 in PostgreSQL mode supports TRUNCATE ... CASCADE
        jdbcTemplate.execute("SET REFERENTIAL_INTEGRITY FALSE");
        jdbcTemplate.execute("TRUNCATE TABLE schedule_groups");
        jdbcTemplate.execute("TRUNCATE TABLE schedules");
        jdbcTemplate.execute("TRUNCATE TABLE student_groups");
        jdbcTemplate.execute("TRUNCATE TABLE subjects");
        jdbcTemplate.execute("TRUNCATE TABLE rooms");
        jdbcTemplate.execute("TRUNCATE TABLE professors");
        jdbcTemplate.execute("TRUNCATE TABLE users");
        jdbcTemplate.execute("SET REFERENTIAL_INTEGRITY TRUE");
    }
}

