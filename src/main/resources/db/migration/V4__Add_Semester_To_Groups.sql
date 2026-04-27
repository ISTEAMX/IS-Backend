-- V4__Add_Semester_To_Groups.sql
-- Add semester column to student_groups table

ALTER TABLE student_groups ADD COLUMN semester INT NOT NULL DEFAULT 1;

