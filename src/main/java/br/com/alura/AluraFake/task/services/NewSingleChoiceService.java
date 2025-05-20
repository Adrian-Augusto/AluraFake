package br.com.alura.AluraFake.task.services;

import br.com.alura.AluraFake.course.Course;
import br.com.alura.AluraFake.course.CourseRepository;
import br.com.alura.AluraFake.task.MapperTask.NewSingleChoiceMapper;
import br.com.alura.AluraFake.task.Validation.NewSingleChoiceValidation;
import br.com.alura.AluraFake.task.Validation.TaskOrderManager;
import br.com.alura.AluraFake.task.dto.NewSingleChoiceDTO;
import br.com.alura.AluraFake.task.entity.NewSingleChoice;
import br.com.alura.AluraFake.task.repository.NewSingleChoiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class NewSingleChoiceService {

    @Autowired
    private NewSingleChoiceRepository repository;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private NewSingleChoiceValidation validation;

    @Autowired
    private TaskOrderManager taskOrderManager;

    @Transactional
    public NewSingleChoice singlechoice(NewSingleChoiceDTO dto) {
        Course course = courseRepository.findById(dto.getCourseId())
                .orElseThrow(() -> new RuntimeException("Course not found "));

        NewSingleChoice entity = NewSingleChoiceMapper.toEntity(dto);

        validation.validateFull(entity, course);

        entity.setCourse(course);

        // Buscar todas as tasks do curso, ordenadas por ordem
        List<NewSingleChoice> existingTasks = repository.findByCourseIdOrderByOrderrAsc(dto.getCourseId());

        // Preparar para inserção da nova task com deslocamento se necessário
        taskOrderManager.prepararParaInsercao(existingTasks, dto.getOrderr());

        repository.saveAll(existingTasks); // salvar tasks com ordens atualizadas

        return repository.save(entity); // salvar a nova task
    }





}