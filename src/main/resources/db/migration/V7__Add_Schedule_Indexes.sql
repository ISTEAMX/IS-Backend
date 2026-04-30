-- V7__Add_Schedule_Indexes.sql
-- Add composite indexes on schedules table for conflict-checking and filtering queries

CREATE INDEX idx_schedule_professor_day_hour
    ON schedules (professor_id, schedule_day, starting_hour);

CREATE INDEX idx_schedule_room_day_hour
    ON schedules (room_id, schedule_day, starting_hour);

