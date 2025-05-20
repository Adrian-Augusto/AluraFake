package br.com.alura.AluraFake.task.repository;

import br.com.alura.AluraFake.task.Type.Type;
import br.com.alura.AluraFake.task.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository

public interface TaskRepository extends JpaRepository<Task, Long> {

}
