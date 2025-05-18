package br.com.alura.AluraFake.task.dto;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class OptionsDTO {

    private String option;
    private boolean isCorrect;

    public OptionsDTO(String option, boolean isCorrect) {
        this.option = option;
        this.isCorrect = isCorrect;
    }
}
