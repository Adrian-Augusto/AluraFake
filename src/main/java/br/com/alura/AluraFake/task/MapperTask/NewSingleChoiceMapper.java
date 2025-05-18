package br.com.alura.AluraFake.task.MapperTask;


import br.com.alura.AluraFake.task.dto.NewSingleChoiceDTO;
import br.com.alura.AluraFake.task.entity.NewSingleChoice;

import java.util.stream.Collectors;

public class NewSingleChoiceMapper {

    public static NewSingleChoice toEntity(NewSingleChoiceDTO dto) {
        if (dto == null) return null;

        NewSingleChoice entity = new NewSingleChoice();
        entity.setStatement(dto.getStatement());
        entity.setOrderr(dto.getOrderr());
        entity.setType(Type.SINGLE_CHOICE);

        // Mapear lista de OptionsDTO para lista de Options
        if (dto.getOption() != null) {
            List<Options> options = dto.getOption().stream()
                    .map(optDto -> {
                        Options opt = new Options();
                        opt.setOption(optDto.getOption());
                        opt.setIsCorrect(optDto.getIsCorrect());
                        return opt;
                    })
                    .collect(Collectors.toList());
            entity.setOption(options);
        }

        return entity;
    }


}
