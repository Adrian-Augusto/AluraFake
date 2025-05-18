package br.com.alura.AluraFake.task.services;

import br.com.alura.AluraFake.task.MapperTask.NewOpenTextMapper;
import br.com.alura.AluraFake.task.dto.NewOpenTextDTO;
import br.com.alura.AluraFake.task.entity.NewOpenText;
import br.com.alura.AluraFake.task.repository.NewOpenTextRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NewOpenTextService {

    @Autowired
    private NewOpenTextRepository repository;

    @Transactional
    public NewOpenText newOpenTextExercise(NewOpenTextDTO dto) {
        // Conversão de DTO para entidade
        NewOpenText entity = NewOpenTextMapper.toEntity(dto);

        // Salvar no banco
        return repository.save(entity);
    }
}
