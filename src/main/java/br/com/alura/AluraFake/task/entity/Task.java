package br.com.alura.AluraFake.task.entity;

import br.com.alura.AluraFake.course.Course;
import br.com.alura.AluraFake.task.Type.Type;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "tasks")
@Getter
@Setter
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String statement;

    private Integer orderr;

    @ManyToOne(optional = false)
    @JoinColumn(name = "courseId")
    @JsonIgnore
    private Course course;
    @Enumerated(EnumType.STRING)
    private Type type;
    // One-to-Many unidirecional: Task “dona” da FK task_id na tabela options
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "task_id") // cria a coluna options.task_id apontando para tasks.id
    private List<Options> options = new ArrayList<>();


    public Task(Long id, String statement, int order, Course course, Type type) {
        this.id = id;
        this.statement = statement;
        this.orderr = order;
        this.course = course;
        this.type = type;
    }

    public Task() {
    }
}