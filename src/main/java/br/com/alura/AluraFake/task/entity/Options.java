package br.com.alura.AluraFake.task.entity;

import jakarta.persistence.Converter;
import jakarta.persistence.Embeddable;
import lombok.Data;

@Data
@Embeddable
public class Options {

    private String option;
    @Column(name = "is_correct")
    private Boolean isCorrect;


}
