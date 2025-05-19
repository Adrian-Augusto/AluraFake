package br.com.alura.AluraFake.task.entity;

import br.com.alura.AluraFake.course.Course;
import br.com.alura.AluraFake.task.Type.Type;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


@Entity
@Table(name = "tasks")
@Getter
@Setter

public class   Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String statement;
    @Column(name = "orderr")
    private Integer order;

    @ManyToOne(optional = false)

    @JoinColumn(name = "courseId")
    @JsonIgnore
    private Course course;
    @Enumerated(EnumType.STRING) // Salva como texto no banco, ex: "OPEN_TEXT"
    private Type type;


    public Task(Long id, String statement, int order, Course course, Type type) {
        this.id = id;
        this.statement = statement;
        this.order = order;
        this.course = course;
        this.type = type;
    }

    public Task() {
    }
}