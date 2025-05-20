package br.com.alura.AluraFake.task.MapperTask;

import br.com.alura.AluraFake.task.Type.Type;
import br.com.alura.AluraFake.task.dto.NewMultiplechoiceDTO;
import br.com.alura.AluraFake.task.entity.NewMultiplechoice;
import br.com.alura.AluraFake.task.entity.Options;

import java.util.List;
import java.util.stream.Collectors;

public class NewMultipleChoiceMapper {

    public static NewMultiplechoice toEntity(NewMultiplechoiceDTO dto) {
        if (dto == null) return null;

        NewMultiplechoice entity = new NewMultiplechoice();
        entity.setStatement(dto.getStatement());
        entity.setOrderr(dto.getOrderr());
        entity.setType(Type.MULTIPLE_CHOICE);

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
    }}