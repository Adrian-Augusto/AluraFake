package br.com.alura.AluraFake.task.MapperTask;

import br.com.alura.AluraFake.task.Type.Type;
import br.com.alura.AluraFake.task.dto.NewOpenTextDTO;
import br.com.alura.AluraFake.task.dto.NewSingleChoiceDTO;
import br.com.alura.AluraFake.task.dto.OptionsDTO;
import br.com.alura.AluraFake.task.entity.NewOpenText;
import br.com.alura.AluraFake.task.entity.NewSingleChoice;
import br.com.alura.AluraFake.task.entity.Options;
import jakarta.persistence.DiscriminatorValue;

import java.util.List;
import java.util.stream.Collectors;

public class NewSingleChoiceMapper {
    public static NewSingleChoice toEntity(NewSingleChoiceDTO dto) {
        if (dto == null) return null;

        NewSingleChoice entity = new NewSingleChoice();
        entity.setStatement(dto.getStatement());
        entity.setOrderr(dto.getOrderr());
        entity.setType(Type.SINGLE_CHOICE);

        if (dto.getOptions() != null) {
            List<Options> options = dto.getOptions().stream()
                    .map(optDto -> {
                        Options opt = new Options();
                        opt.setOption(optDto.getOption());
                        opt.setIscorrect(optDto.isIscorrect());
                        opt.setTask(entity);
                        return opt;
                    })
                    .collect(Collectors.toList());
            entity.setOptions(options);
        }

        return entity;
    }


}
