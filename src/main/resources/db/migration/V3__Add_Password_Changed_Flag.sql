-- V3__Add_Password_Changed_Flag.sql
-- Adds a flag to track whether a user has changed their default password

ALTER TABLE users ADD COLUMN password_changed BOOLEAN NOT NULL DEFAULT FALSE;

-- Mark admins/professors seeded in V2 as needing password change (already false by default)
-- Any future users registered through the app will have this set to true on registration

