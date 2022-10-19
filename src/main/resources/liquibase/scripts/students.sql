-- liquibase formatted sql

-- changeset tuzlukov:1
CREATE INDEX students_name_index ON student(name);