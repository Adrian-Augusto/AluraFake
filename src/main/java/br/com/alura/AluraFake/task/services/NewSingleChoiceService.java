package br.com.alura.AluraFake.task.services;

import br.com.alura.AluraFake.course.Course;
import br.com.alura.AluraFake.course.CourseRepository;
import br.com.alura.AluraFake.task.MapperTask.NewOpenTextMapper;
import br.com.alura.AluraFake.task.MapperTask.NewSingleChoiceMapper;
import br.com.alura.AluraFake.task.dto.NewOpenTextDTO;
import br.com.alura.AluraFake.task.dto.NewSingleChoiceDTO;
import br.com.alura.AluraFake.task.entity.NewOpenText;
import br.com.alura.AluraFake.task.entity.NewSingleChoice;
import br.com.alura.AluraFake.task.repository.NewSingleChoiceRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NewSingleChoiceService {


    @Autowired private NewSingleChoiceRepository repository;

    @Autowired private CourseRepository courseRepository;

     public NewSingleChoice singlechoice (NewSingleChoiceDTO dto) {

        // Busca o curso pelo id, ou lança erro se não achar
        Course course = courseRepository.findById(dto.getCourseId())
                .orElseThrow(() -> new RuntimeException("Curso não encontrado"));

        // Mapeia o DTO para entidade
        NewSingleChoice entity = NewSingleChoiceMapper.toEntity(dto );

        // Atribui o curso na entidade
        entity.setCourse(course);

        // Salva e retorna
        return repository.save(entity);
    }}