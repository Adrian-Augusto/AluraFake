package br.com.alura.AluraFake.task.Validation;

import br.com.alura.AluraFake.course.Course;
import br.com.alura.AluraFake.course.Status;
import br.com.alura.AluraFake.task.Exeception.NewSingleException;
import br.com.alura.AluraFake.task.entity.NewSingleChoice;
import br.com.alura.AluraFake.task.entity.Options;
import br.com.alura.AluraFake.task.repository.NewSingleChoiceRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class NewSingleChoiceValidationTest {

    private NewSingleChoiceRepository repository;
    private NewSingleChoiceValidation validation;

    @BeforeEach
    void setUp() {
        repository = Mockito.mock(NewSingleChoiceRepository.class);
        validation = new NewSingleChoiceValidation(repository);
    }

    @Test
    void deveLancarErroQuandoStatusNaoForBUILDING() {
        Course curso = new Course();
        curso.setStatus(Status.PUBLISHED); // inválido

        NewSingleChoice atividade = new NewSingleChoice();
        atividade.setStatement("Qual a capital do Brasil?");
        atividade.setOptions(List.of(newOption("Brasília", true), newOption("São Paulo", false)));

        assertThrows(NewSingleException.class, () -> validation.validate(atividade, curso));
    }

    @Test
    void deveLancarErroQuandoEnunciadoJaExiste() {
        Course curso = new Course();
        curso.setStatus(Status.BUILDING);

        NewSingleChoice atividade = new NewSingleChoice();
        atividade.setStatement("Questão repetida");
        atividade.setOptions(List.of(newOption("Opção 1", true), newOption("Opção 2", false)));

        when(repository.existsByCourseAndStatement(curso, "Questão repetida")).thenReturn(true);

        assertThrows(NewSingleException.class, () -> validation.validate(atividade, curso));
    }

    @Test
    void deveLancarErroQuandoTemMaisDeUmaCorreta() {
        Course curso = cursoValido();

        NewSingleChoice atividade = new NewSingleChoice();
        atividade.setStatement("2+2?");
        atividade.setOptions(List.of(
                newOption("4", true),
                newOption("Quatro", true)
        ));

        assertThrows(NewSingleException.class, () -> validation.validate(atividade, curso));
    }

    @Test
    void deveLancarErroQuandoAlternativasForemIguais() {
        Course curso = cursoValido();

        NewSingleChoice atividade = new NewSingleChoice();
        atividade.setStatement("Capital do Brasil?");
        atividade.setOptions(List.of(
                newOption("Brasília", true),
                newOption("Brasília", true),
                newOption("Brasília", true),
                newOption("Brasília", false)
        ));

        assertThrows(NewSingleException.class, () -> validation.validate(atividade, curso));
    }

    @Test
    void deveLancarErroQuandoAlternativaIgualAoEnunciado() {
        Course curso = cursoValido();

        NewSingleChoice atividade = new NewSingleChoice();
        atividade.setStatement("Brasil");
        atividade.setOptions(List.of(
                newOption("Brasil", true),
                newOption("Argentina", false)
        ));

        assertThrows(NewSingleException.class, () -> validation.validate(atividade, curso));
    }

    // Helpers

    private Course cursoValido() {
        Course curso = new Course();
        curso.setStatus(Status.BUILDING);
        return curso;
    }

    private Options newOption(String texto, boolean correta) {
        Options opt = new Options();
        opt.setOption(texto);
        opt.setIscorrect(correta);
        return opt;
    }
}
