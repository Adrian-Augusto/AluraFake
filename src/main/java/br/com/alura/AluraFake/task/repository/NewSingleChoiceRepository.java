package br.com.alura.AluraFake.task.repository;

import br.com.alura.AluraFake.task.entity.NewSingleChoice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NewSingleChoiceRepository extends JpaRepository<NewSingleChoice, Long> {

}
