-- V6__Schedule_Multiple_Groups.sql
-- Change schedules from single group to multiple groups (ManyToMany)

-- Create the join table
CREATE TABLE schedule_groups (
    schedule_id BIGINT NOT NULL,
    group_id BIGINT NOT NULL,
    PRIMARY KEY (schedule_id, group_id),
    CONSTRAINT fk_sg_schedule FOREIGN KEY (schedule_id) REFERENCES schedules (id) ON DELETE CASCADE,
    CONSTRAINT fk_sg_group FOREIGN KEY (group_id) REFERENCES student_groups (id) ON DELETE CASCADE
);

-- Migrate existing data from schedules.group_id into the join table
INSERT INTO schedule_groups (schedule_id, group_id)
SELECT id, group_id FROM schedules WHERE group_id IS NOT NULL;

-- Drop the old foreign key and column
ALTER TABLE schedules DROP CONSTRAINT fk_schedule_group;
ALTER TABLE schedules DROP COLUMN group_id;

