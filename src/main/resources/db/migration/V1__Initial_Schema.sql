-- V1__Initial_Schema.sql
-- Initial database schema for University ISTEAMX project

-- Table: users
CREATE TABLE users (
    id BIGSERIAL PRIMARY KEY,
    first_name VARCHAR(255) NOT NULL,
    last_name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    role VARCHAR(50) NOT NULL
);

-- Table: professors
CREATE TABLE professors (
    id BIGSERIAL PRIMARY KEY,
    first_name VARCHAR(255) NOT NULL,
    last_name VARCHAR(255) NOT NULL,
    department VARCHAR(255) NOT NULL,
    user_id BIGINT NOT NULL UNIQUE,
    CONSTRAINT fk_professor_user FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE
);

-- Table: student_groups
CREATE TABLE student_groups (
    id BIGSERIAL PRIMARY KEY,
    identifier VARCHAR(255) NOT NULL UNIQUE,
    specialization VARCHAR(255) NOT NULL,
    year INT NOT NULL
);

-- Table: rooms
CREATE TABLE rooms (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL UNIQUE,
    capacity INT NOT NULL,
    type VARCHAR(100) NOT NULL,
    location VARCHAR(255) NOT NULL UNIQUE
);

-- Table: subjects
CREATE TABLE subjects (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    activity_type VARCHAR(100) NOT NULL
);

-- Table: schedules
CREATE TABLE schedules (
    id BIGSERIAL PRIMARY KEY,
    schedule_day VARCHAR(50) NOT NULL,
    starting_hour VARCHAR(10) NOT NULL,
    ending_hour VARCHAR(10) NOT NULL,
    frequency VARCHAR(50) NOT NULL,
    pending VARCHAR(50) NOT NULL ,
    professor_id BIGINT NOT NULL,
    room_id BIGINT NOT NULL,
    group_id BIGINT NOT NULL,
    subject_id BIGINT NOT NULL,
    CONSTRAINT fk_schedule_professor FOREIGN KEY (professor_id) REFERENCES professors (id),
    CONSTRAINT fk_schedule_room FOREIGN KEY (room_id) REFERENCES rooms (id),
    CONSTRAINT fk_schedule_group FOREIGN KEY (group_id) REFERENCES student_groups (id),
    CONSTRAINT fk_schedule_subject FOREIGN KEY (subject_id) REFERENCES subjects (id)
);
