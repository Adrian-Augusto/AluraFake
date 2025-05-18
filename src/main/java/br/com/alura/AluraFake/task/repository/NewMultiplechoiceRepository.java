package br.com.alura.AluraFake.task.repository;

import br.com.alura.AluraFake.task.entity.NewMultiplechoice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NewMultiplechoiceRepository extends JpaRepository<NewMultiplechoice, Long> {
}
