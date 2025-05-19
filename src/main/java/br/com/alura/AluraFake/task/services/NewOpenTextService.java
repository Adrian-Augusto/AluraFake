package br.com.alura.AluraFake.task.services;

import br.com.alura.AluraFake.course.Course;
import br.com.alura.AluraFake.course.CourseRepository;
import br.com.alura.AluraFake.task.MapperTask.NewOpenTextMapper;
import br.com.alura.AluraFake.task.dto.NewOpenTextDTO;
import br.com.alura.AluraFake.task.entity.NewOpenText;
import br.com.alura.AluraFake.task.repository.NewOpenTextRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NewOpenTextService {

    @Autowired
    private NewOpenTextRepository repository;

    @Autowired
    private CourseRepository courseRepository;

        public NewOpenText newOpenTextExercise(NewOpenTextDTO dto) {

            // Busca o curso pelo id, ou lança erro se não achar
            Course course = courseRepository.findById(dto.getCourseId())
                    .orElseThrow(() -> new RuntimeException("Curso não encontrado"));

            // Mapeia o DTO para entidade
            NewOpenText entity = NewOpenTextMapper.toEntity(dto );

            // Atribui o curso na entidade
            entity.setCourse(course);

            // Salva e retorna
            return repository.save(entity);
            }}