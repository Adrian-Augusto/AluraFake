package br.com.alura.AluraFake.task.dto;

import br.com.alura.AluraFake.course.Course;
import br.com.alura.AluraFake.task.entity.Options;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter

public class NewSingleChoiceDTO {
    @NotNull(message = "Course ID is required")
    private Long courseId;
    @NotBlank(message = "Statement is required")
    @Size(min = 4, max = 255, message = "Statement must be between 4 and 255 characters")
    private String statement;
    @JsonIgnore
    private Course course;
    @Positive(message = "Order must be a positive integer")
    @Column(name = "orderr")
    private Integer order;
    @ManyToOne
    @Size( max = 6, message = "list min 0 list max 5")
    private List<Options> options = new ArrayList<>();

    public NewSingleChoiceDTO(Long courseId, String statement, Course course, Integer order, List<Options> options) {
        this.courseId = courseId;
        this.statement = statement;
        this.course = course;
        this.order = order;
        this.options = options;
    }
}