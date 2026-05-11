-- V8__Add_Pending_Column_To_Schedules.sql
-- Add pending approval status to schedules

ALTER TABLE schedules ADD COLUMN pending VARCHAR(50) NOT NULL DEFAULT 'APPROVED';

