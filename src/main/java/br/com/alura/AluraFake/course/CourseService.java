package br.com.alura.AluraFake.course;

import br.com.alura.AluraFake.task.Type.Type;
import br.com.alura.AluraFake.task.entity.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CourseService {

    @Autowired
    private CourseRepository courseRepository;

    public Course publishCourse(Long courseId) {
        // Busca o curso pelo ID
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new RuntimeException("Curso não encontrado"));

        // Verifica se está em status BUILDING
        if (course.getStatus() != Status.BUILDING) {
            throw new IllegalStateException("Curso deve estar em status BUILDING para ser publicado.");
        }

        List<Task> tasks = course.getTasks();

        // Verifica se tem pelo menos uma atividade de cada tipo
        Set<Type> tiposPresentes = tasks.stream()
                .map(Task::getType)
                .collect(Collectors.toSet());

        if (!(tiposPresentes.contains(Type.SINGLE_CHOICE)
                && tiposPresentes.contains(Type.OPEN_TEXT)
                && tiposPresentes.contains(Type.MULTIPLE_CHOICE))) {
            throw new IllegalStateException("Curso deve conter pelo menos uma atividade de cada tipo.");
        }

        // Verifica ordem contínua
        List<Integer> ordens = tasks.stream()
                .map(Task::getOrderr)
                .sorted()
                .collect(Collectors.toList());

        for (int i = 0; i < ordens.size(); i++) {
            if (ordens.get(i) != i + 1) {
                throw new IllegalStateException("Atividades devem ter ordem sequencial contínua.");
            }
        }

        // Atualiza status e data de publicação
        course.setStatus(Status.PUBLISHED);
        course.setPublishedAt(LocalDateTime.now());

        return courseRepository.save(course);
    }
}
