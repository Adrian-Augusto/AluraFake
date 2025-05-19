package br.com.alura.AluraFake.task.services;
import br.com.alura.AluraFake.course.Course;

import br.com.alura.AluraFake.course.CourseRepository;
import br.com.alura.AluraFake.task.MapperTask.NewSingleChoiceMapper;
import br.com.alura.AluraFake.task.Validation.NewSingleChoiceValidation;
import br.com.alura.AluraFake.task.dto.NewSingleChoiceDTO;
import br.com.alura.AluraFake.task.entity.NewSingleChoice;
import br.com.alura.AluraFake.task.entity.Options;
import br.com.alura.AluraFake.task.repository.NewSingleChoiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class NewSingleChoiceService {


        @Autowired
        private NewSingleChoiceRepository repository;

        @Autowired
        private CourseRepository courseRepository;

        @Autowired
        private NewSingleChoiceValidation validation;

        public NewSingleChoice singlechoice(NewSingleChoiceDTO dto) {

            // Busca o curso pelo id, ou lança erro se não achar
            Course course = courseRepository.findById(dto.getCourseId())
                    .orElseThrow(() -> new RuntimeException("Curso não encontrado"));

            // Mapeia o DTO para entidade
            NewSingleChoice entity = NewSingleChoiceMapper.toEntity(dto);

            // Valida a entidade com o curso
            validation.validateFull(entity, course);

            // Atribui o curso na entidade
            entity.setCourse(course);

            // Salva e retorna
            return repository.save(entity);
        }
    }
