package br.com.alura.AluraFake.task.dto;

import br.com.alura.AluraFake.course.Course;
import br.com.alura.AluraFake.task.entity.Options;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.ManyToOne;
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
public class NewMultiplechoiceDTO {
    @NotNull(message = "Course ID is required")
    private Long courseId;
    @NotBlank(message = "Statement is required")
    @Size(min = 4, max = 255, message = "Statement must be between 4 and 255 characters")
    private String statement;
    @Positive(message = "Order must be a positive integer")
    @Column(name = "orderr")
    private Integer order;
    @ManyToOne
    @Size(min = 2, max = 5, message = "list min 2 list max 5")
    private List<Options> options = new ArrayList<>();


}


