CREATE TABLE alurafake.Options
(
    id         BIGINT AUTO_INCREMENT NOT NULL,
    `option`   VARCHAR(255)          NULL,
    is_correct BIT(1)                NOT NULL,
    task_id    BIGINT                NULL,
    CONSTRAINT pk_options PRIMARY KEY (id)
);

ALTER TABLE alurafake.Options
    ADD CONSTRAINT FK_OPTIONS_ON_TASK FOREIGN KEY (task_id) REFERENCES tasks (id);