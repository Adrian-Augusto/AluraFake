package br.com.alura.AluraFake.task.dto;
import br.com.alura.AluraFake.task.entity.Options;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
public class OptionsDTO {
    private String option;
    private boolean iscorrect;
    private List<OptionsDTO> options = new ArrayList<>();

}
