package br.com.alura.AluraFake.task.services;

import br.com.alura.AluraFake.course.Course;
import br.com.alura.AluraFake.course.CourseRepository;
import br.com.alura.AluraFake.task.MapperTask.NewMultipleChoiceMapper;
import br.com.alura.AluraFake.task.Validation.NewMultipleChoiceValidation;
import br.com.alura.AluraFake.task.Validation.TaskMultipleManager;
import br.com.alura.AluraFake.task.dto.NewMultiplechoiceDTO;
import br.com.alura.AluraFake.task.entity.NewMultiplechoice;
import br.com.alura.AluraFake.task.repository.NewMultiplechoiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NewMultipleService {

    @Autowired
    private NewMultiplechoiceRepository repository;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private NewMultipleChoiceValidation validation;

    @Autowired
    private TaskMultipleManager manager;

    public NewMultiplechoice newMultipleChoice(NewMultiplechoiceDTO dto) {
        // Busca o curso pelo id, ou lança erro se não achar
        Course course = courseRepository.findById(dto.getCourseId())
                .orElseThrow(() -> new RuntimeException("Course not found"));

        // Mapeia o DTO para entidade
        NewMultiplechoice entity = NewMultipleChoiceMapper.toEntity(dto);

        // Associa cada opção à task (para JPA fazer o vínculo correto)
        entity.getOptions().forEach(option -> option.setTask(entity));

        // Buscar todas as tasks do curso, ordenadas por ordem
        List<NewMultiplechoice> existingTasks = repository.findByCourseIdOrderByOrderrAsc(dto.getCourseId());

        // Preparar para inserção da nova task com deslocamento se necessário
        manager.prepararParaInsercao(existingTasks, dto.getOrderr());

        repository.saveAll(existingTasks); // salvar tasks com ordens atualizadas

        // Validação completa antes de salvar
        validation.validateFull(entity, course);

        // Atribui o curso na entidade
        entity.setCourse(course);

        // Salva e retorna
        return repository.save(entity);
    }
}
