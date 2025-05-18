package br.com.alura.AluraFake.task.MapperTask;

import br.com.alura.AluraFake.task.Type.Type;
import br.com.alura.AluraFake.task.dto.NewMultiplechoiceDTO;
import br.com.alura.AluraFake.task.entity.NewMultiplechoice;

public class NewMultipleChoiceMapper {

    public static NewMultiplechoice toEntity(NewMultiplechoiceDTO dto) {
        if (dto == null) return null;

        NewMultiplechoice entity = new NewMultiplechoice();
        entity.setStatement(dto.getStatement());
        entity.setOrderr(dto.getOrderr());
        entity.setType(Type.MULTIPLE_CHOICE);

        return entity;
    }

    public static NewMultiplechoiceDTO toDto(NewMultiplechoice entity) {
        if (entity == null) return null;
        NewMultiplechoiceDTO dto = new NewMultiplechoiceDTO();
        dto.setStatement(entity.getStatement());
        dto.setOrderr(entity.getOrderr());
        return dto;
    }
}
