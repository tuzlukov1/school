ALTER TABLE student ADD CONSTRAINT age_constraint CHECK (age>15);

ALTER TABLE student ADD CONSTRAINT name_unique UNIQUE(name), ALTER COLUMN name SET NOT NULL;

ALTER TABLE student ALTER age SET DEFAULT 20;

ALTER TABLE faculty ADD CONSTRAINT name_color_unique UNIQUE (name, color);