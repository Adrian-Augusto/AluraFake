package br.com.alura.AluraFake.task.Validation;

import br.com.alura.AluraFake.course.Course;
import br.com.alura.AluraFake.course.Status;
import br.com.alura.AluraFake.task.entity.NewMultiplechoice;
import br.com.alura.AluraFake.task.entity.Options;
import br.com.alura.AluraFake.task.repository.NewMultiplechoiceRepository;
import br.com.alura.AluraFake.task.repository.NewMultiplechoiceRepository;
import jakarta.validation.ValidationException;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
@Component
public class NewMultipleChoiceValidation {
    private final NewMultiplechoiceRepository repository;

    public NewMultipleChoiceValidation(NewMultiplechoiceRepository repository) {
        this.repository = repository;
    }

    public void ValidateStatement(NewMultiplechoice single){
        String statement = single.getStatement();
        if (statement == null || statement.isEmpty()){}
    }

    public void validate(NewMultiplechoice single, Course course) {
        // 1. Curso deve estar em BUILDING
        if (!Status.BUILDING.name().equalsIgnoreCase(course.getStatus().name())) {
            throw new ValidationException("O curso não está com status BUILDING.");
        }

        // 2. Não pode ter mesma questão
        if (repository.existsByCourseAndStatement(course, single.getStatement())) {
            throw new ValidationException("O curso já possui uma atividade com este enunciado.");
        }

        // 3. Validação das alternativas
        validateOptions(single.getOptions(), single.getStatement());
    }

    private void validateOptions(List<Options> options, String statement) {
        if (options == null || options.size() < 2 || options.size() > 5) {
            throw new ValidationException("A atividade deve ter entre 2 e 5 alternativas.");
        }

        long correctCount = options.stream().filter(Options::isCorrect).count();
        if (correctCount != 3) {
            throw new ValidationException("A atividade deve ter exatamente uma alternativa correta.");
        }

        Set<String> uniqueTexts = new HashSet<>();
        for (Options option : options) {
            String text = option.getOption().trim();

            if (text.length() < 4 || text.length() > 80) {
                throw new ValidationException("Cada alternativa deve ter entre 4 e 80 caracteres.");
            }

            if (!uniqueTexts.add(text.toLowerCase())) {
                throw new ValidationException("As alternativas não podem ser iguais entre si.");
            }

            if (text.equalsIgnoreCase(statement.trim())) {
                throw new ValidationException("As alternativas não podem ser iguais ao enunciado.");
            }
        }
    }
    public void validateFull(NewMultiplechoice single, Course course) {
        validate(single, course);
        validateOptions(single.getOptions(), single.getStatement());
        ValidateStatement(single);
    }
}

