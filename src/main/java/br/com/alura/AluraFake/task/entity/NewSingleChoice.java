package br.com.alura.AluraFake.task.entity;

import br.com.alura.AluraFake.course.Course;
import br.com.alura.AluraFake.task.Type.Type;
import jakarta.persistence.*;import lombok.Getter;
import lombok.Setter;
import java.util.ArrayList;
import java.util.List;

    @Entity
    @Getter
    @Setter
    @DiscriminatorValue("SINGLE")
    public class NewSingleChoice extends Task {
    @OneToMany(mappedBy = "task", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Options> options = new ArrayList<>();

        public NewSingleChoice(Long id, String statement, int order, Course course, Type type, List<Options> options) {
            super(id, statement, order, course, type);
            this.options = options;
        }

        public NewSingleChoice(List<Options> options) {
            this.options = options;
        }

        public NewSingleChoice() {

        }
    }