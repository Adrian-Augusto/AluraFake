package br.com.alura.AluraFake.task.Validation;

import br.com.alura.AluraFake.course.Course;
import br.com.alura.AluraFake.course.Status;
import br.com.alura.AluraFake.task.entity.NewMultiplechoice;
import br.com.alura.AluraFake.task.entity.Options;
import br.com.alura.AluraFake.task.repository.NewMultiplechoiceRepository;
import jakarta.validation.ValidationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class NewMultipleChoiceValidationTest {

    private NewMultiplechoiceRepository repository;
    private NewMultipleChoiceValidation validation;

    @BeforeEach
    void setup() {
        repository = mock(NewMultiplechoiceRepository.class);
        validation = new NewMultipleChoiceValidation(repository);
    }

    @Test
    void shouldThrowExceptionIfCourseNotBuilding() {
        Course course = new Course();
        course.setStatus(Status.PUBLISHED); // não está BUILDING

        NewMultiplechoice multiplechoice = new NewMultiplechoice();
        multiplechoice.setStatement("Enunciado qualquer");

        ValidationException exception = assertThrows(ValidationException.class, () -> {
            validation.validate(multiplechoice, course);
        });

        assertEquals("O curso não está com status BUILDING.", exception.getMessage());
    }

    @Test
    void shouldThrowExceptionIfStatementExists() {
        Course course = new Course();
        course.setStatus(Status.BUILDING);

        NewMultiplechoice multiplechoice = new NewMultiplechoice();
        multiplechoice.setStatement("Enunciado repetido");

        // Simula que o enunciado já existe
        when(repository.existsByCourseAndStatement(course, "Enunciado repetido")).thenReturn(true);

        ValidationException exception = assertThrows(ValidationException.class, () -> {
            validation.validate(multiplechoice, course);
        });

        assertEquals("O curso já possui uma atividade com este enunciado.", exception.getMessage());
    }

    @Test
    void shouldThrowExceptionIfOptionsInvalid() {
        List<Options> options = Arrays.asList(
                createOption("Opção 1", false),
                createOption("Opção 2", false)
        );

        NewMultiplechoice multiplechoice = new NewMultiplechoice();
        multiplechoice.setStatement("Um enunciado");
        multiplechoice.setOptions(options);

        Course course = new Course();
        course.setStatus(Status.BUILDING);

        when(repository.existsByCourseAndStatement(course, multiplechoice.getStatement())).thenReturn(false);

        ValidationException exception = assertThrows(ValidationException.class, () -> {
            validation.validate(multiplechoice, course);
        });

        assertTrue(exception.getMessage().contains("pelo menos duas alternativas corretas") ||
                exception.getMessage().contains("pelo menos uma alternativa incorreta"));
    }

    @Test
    void shouldPassValidationForValidMultipleChoice() {
        List<Options> options = Arrays.asList(
                createOption("Correta 1", true),
                createOption("Correta 2", true),
                createOption("Incorreta", false)
        );

        NewMultiplechoice multiplechoice = new NewMultiplechoice();
        multiplechoice.setStatement("Enunciado válido");
        multiplechoice.setOptions(options);

        Course course = new Course();
        course.setStatus(Status.BUILDING);

        when(repository.existsByCourseAndStatement(course, multiplechoice.getStatement())).thenReturn(false);

        assertDoesNotThrow(() -> validation.validateFull(multiplechoice, course));
    }

    private Options createOption(String text, boolean isCorrect) {
        Options option = new Options();
        option.setOption(text);
        option.setIscorrect(isCorrect);
        return option;
    }
}
