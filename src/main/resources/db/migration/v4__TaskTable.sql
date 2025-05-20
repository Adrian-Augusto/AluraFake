CREATE TABLE alurafake.tasks
(
    id        BIGINT AUTO_INCREMENT NOT NULL,
    dtype     VARCHAR(31)           NULL,
    statement VARCHAR(255)          NULL,
    orderr    INT                   NULL,
    course_id BIGINT                NOT NULL,
    type      VARCHAR(255)          NULL,
    CONSTRAINT pk_tasks PRIMARY KEY (id)
);

ALTER TABLE tasks
    ADD CONSTRAINT FK_TASKS_ON_COURSEID FOREIGN KEY (courseId) REFERENCES course (id);