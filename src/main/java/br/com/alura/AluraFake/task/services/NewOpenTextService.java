package br.com.alura.AluraFake.task.services;

import br.com.alura.AluraFake.course.Course;
import br.com.alura.AluraFake.course.CourseRepository;
import br.com.alura.AluraFake.task.MapperTask.NewOpenTextMapper;
import br.com.alura.AluraFake.task.Validation.TaskOrderManager;
import br.com.alura.AluraFake.task.dto.NewOpenTextDTO;
import br.com.alura.AluraFake.task.entity.NewOpenText;
import br.com.alura.AluraFake.task.repository.NewOpenTextRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NewOpenTextService {

    @Autowired private NewOpenTextRepository repository;

    @Autowired private CourseRepository courseRepository;

    @Autowired  private TaskOrderManager Manager;


    public NewOpenText newOpenTextExercise(NewOpenTextDTO dto) {

        // Busca o curso pelo id, ou lança erro se não achar
        Course course = courseRepository.findById(dto.getCourseId())
                .orElseThrow(() -> new RuntimeException("Course not found "));

        List<NewOpenText> existingTasks = repository.findByCourseIdOrderByOrderrAsc(dto.getCourseId());


        Manager.prepararParaInsercaoText(existingTasks, dto.getOrderr());

        repository.saveAll(existingTasks); // salvar tasks com ordens atualizadas

        // Mapeia o DTO para entidade
        NewOpenText entity = NewOpenTextMapper.toEntity(dto );

        // Atribui o curso na entidade
        entity.setCourse(course);

        // Salva e retorna
        return repository.save(entity);
    }}