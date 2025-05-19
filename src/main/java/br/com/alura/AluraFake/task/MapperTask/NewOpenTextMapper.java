package br.com.alura.AluraFake.task.MapperTask;

import br.com.alura.AluraFake.task.Type.Type;
import br.com.alura.AluraFake.task.dto.NewOpenTextDTO;
import br.com.alura.AluraFake.task.dto.OptionsDTO;
import br.com.alura.AluraFake.task.entity.NewOpenText;

public class NewOpenTextMapper {
    public static NewOpenText toEntity(NewOpenTextDTO dto) {
        if (dto == null) return null;



        NewOpenText entity = new NewOpenText();
        entity.setStatement(dto.getStatement());
        entity.setOrder(dto.getOrder());
        entity.setType(Type.OPEN_TEXT);

        return entity;
    }

    public static NewOpenTextDTO toDto(NewOpenText entity) {
        if (entity == null) return null;
        NewOpenTextDTO dto = new NewOpenTextDTO();
        dto.setStatement(entity.getStatement());
        return dto;
    }
}
