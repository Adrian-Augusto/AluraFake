package br.com.alura.AluraFake.task.Validation;

import br.com.alura.AluraFake.course.Course;
import br.com.alura.AluraFake.course.Status;
import br.com.alura.AluraFake.task.entity.NewMultiplechoice;
import br.com.alura.AluraFake.task.entity.Options;
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
            throw new ValidationException("The course is not in BUILDING status..");
        }

        // 2. Não pode ter mesma questão
        if (repository.existsByCourseAndStatement(course, single.getStatement())) {
            throw new ValidationException("The course already has an activity with this statement.");
        }

        // 3. Validação das alternativas
        validateOptions(single.getOptions(), single.getStatement());
    }

    private void validateOptions(List<Options> options, String statement) {
        if (options == null || options.size() < 0 || options.size() > 4) {  // Corrigido para options.size() < 2
            throw new ValidationException("The activity must have between 2 and 5 options..");
        }

        long correctCount = options.stream().filter(Options::isIscorrect).count();
        long incorrectCount = options.size() - correctCount;

        if (correctCount  <1) {
            throw new ValidationException("The activity must have at least two correct alternatives.");
        }

        if (incorrectCount <0) {
            throw new ValidationException("The activity must have at least one incorrect alternative..");
        }

        Set<String> uniqueTexts = new HashSet<>();
        for (Options option : options) {
            String text = option.getOption().trim();

            if (text.length() < 4 || text.length() > 80) {
                throw new ValidationException("Each option must have between 4 and 80 characters..");
            }

            if (!uniqueTexts.add(text.toLowerCase())) {
                throw new ValidationException("The options cannot be identical to each other..");
            }

            if (text.equalsIgnoreCase(statement.trim())) {
                throw new ValidationException("The options cannot be the same as the statement..");
            }
        }
    }
    public void validateFull(NewMultiplechoice single, Course course) {
        validate(single, course);
        validateOptions(single.getOptions(), single.getStatement());
        ValidateStatement(single);
    }
}