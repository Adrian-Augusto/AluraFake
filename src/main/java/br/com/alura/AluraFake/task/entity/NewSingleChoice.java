package br.com.alura.AluraFake.task.entity;

import br.com.alura.AluraFake.course.Course;
import br.com.alura.AluraFake.task.Type.Type;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter

@NoArgsConstructor
public class NewSingleChoice extends Task {
    @OneToMany(mappedBy = "task", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Options> options = new ArrayList<>();
    protected Type type=Type.SINGLE_CHOICE;

    public NewSingleChoice(Long id, String statement, int order, Course course, Type type, List<Options> options, Type type1) {
        super(id, statement, order, course, type);
        this.options = options;
        this.type = type1;
    }

    public NewSingleChoice(List<Options> options, Type type) {
        this.options = options;
        this.type = type;
    }

}