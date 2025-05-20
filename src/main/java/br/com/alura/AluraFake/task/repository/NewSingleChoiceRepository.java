package br.com.alura.AluraFake.task.repository;

import br.com.alura.AluraFake.course.Course;
import br.com.alura.AluraFake.task.entity.NewSingleChoice;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NewSingleChoiceRepository extends JpaRepository<NewSingleChoice, Long> {

    boolean existsByCourseAndStatement(Course course, String statement);

    List<NewSingleChoice> findByCourseIdOrderByOrderrAsc(Long courseId);
}