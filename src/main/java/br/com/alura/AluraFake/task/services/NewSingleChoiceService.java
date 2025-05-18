package br.com.alura.AluraFake.task.services;

import br.com.alura.AluraFake.task.MapperTask.NewSingleChoiceMapper;
import br.com.alura.AluraFake.task.dto.NewSingleChoiceDTO;
import br.com.alura.AluraFake.task.entity.NewSingleChoice;
import br.com.alura.AluraFake.task.repository.NewSingleChoiceRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NewSingleChoiceService {
    @Autowired
    private NewSingleChoiceRepository repository;

    @Transactional
    public NewSingleChoice singlechoice(NewSingleChoiceDTO dto) {
        // Conversão de DTO para entidade
        NewSingleChoice entity = NewSingleChoiceMapper.toEntity(dto);


        return repository.save(entity);
    }
}

