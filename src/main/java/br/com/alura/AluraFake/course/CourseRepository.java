package br.com.alura.AluraFake.course;

import br.com.alura.AluraFake.task.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseRepository extends JpaRepository<Course, Long>{


}
