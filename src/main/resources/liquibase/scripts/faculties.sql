-- liquibase formatted sql

-- changeset tuzlukov:1
CREATE INDEX faculties_name_index ON faculty(name);
CREATE INDEX faculties_color_index ON faculty(color);

-- changeset tuzlukov:2
CREATE INDEX faculties_name_and_color_index ON faculty(name, color);