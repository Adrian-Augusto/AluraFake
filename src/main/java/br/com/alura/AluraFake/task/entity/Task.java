package br.com.alura.AluraFake.task.entity;

import br.com.alura.AluraFake.course.Course;
import br.com.alura.AluraFake.task.Type.Type;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "task")
@Getter
@Setter
public abstract class   Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String statement;
    private int orderr;
    @ManyToOne
    @JoinColumn(name = "course_Id", nullable = false) // coluna FK no banco
    private Course course;
    @Enumerated(EnumType.STRING) // Salva como texto no banco, ex: "OPEN_TEXT"
    private Type type;

    public Task(Long id, String statement, int orderr, Course course, Type type) {
        this.id = id;
        this.statement = statement;
        this.orderr = orderr;
        this.course = course;
        this.type = type;
    }

    public Task() {
    }
}