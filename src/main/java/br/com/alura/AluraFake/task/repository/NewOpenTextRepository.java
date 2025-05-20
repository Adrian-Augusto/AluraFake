package br.com.alura.AluraFake.task.repository;

import br.com.alura.AluraFake.course.Course;
import br.com.alura.AluraFake.task.entity.NewOpenText;
import org.springframework.data.domain.Limit;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NewOpenTextRepository extends JpaRepository<NewOpenText, Long> {

    boolean existsByCourseAndStatement(Course course, String statement);

    List<NewOpenText> findByCourseIdOrderByOrderrAsc(Long courseId);

    List<NewOpenText> findByCourseIdOrderByOrderrAsc(Long courseId, Sort sort, Limit limit);
}
