package br.com.alura.AluraFake.task.services;

import br.com.alura.AluraFake.course.Course;
import br.com.alura.AluraFake.course.CourseRepository;
import br.com.alura.AluraFake.task.MapperTask.NewMultipleChoiceMapper;
import br.com.alura.AluraFake.task.Validation.NewSingleChoiceValidation;
import br.com.alura.AluraFake.task.dto.NewMultiplechoiceDTO;
import br.com.alura.AluraFake.task.entity.NewMultiplechoice;
import br.com.alura.AluraFake.task.entity.NewSingleChoice;
import br.com.alura.AluraFake.task.repository.NewMultiplechoiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NewMultipleService {

    @Autowired private NewMultiplechoiceRepository repository;

    @Autowired private CourseRepository courseRepository;


    public NewMultiplechoice newMultipleChoice(NewMultiplechoiceDTO dto) {


        // Busca o curso pelo id, ou lança erro se não achar
        Course course = courseRepository.findById(dto.getCourseId())
                .orElseThrow(() -> new RuntimeException("Curso não encontrado"));

        // Mapeia o DTO para entidade
        NewMultiplechoice entity = NewMultipleChoiceMapper.toEntity(dto);
        // Atribui o curso na entidade
        entity.setCourse(course);

        // Salva e retorna
        return repository.save(entity);
    }
}
