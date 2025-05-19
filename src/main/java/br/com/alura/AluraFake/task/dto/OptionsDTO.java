package br.com.alura.AluraFake.task.dto;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class OptionsDTO {

    private String option;
    private boolean isCorrect=false;



}
