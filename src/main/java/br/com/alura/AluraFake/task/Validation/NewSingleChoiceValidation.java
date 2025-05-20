package br.com.alura.AluraFake.task.Validation;
import br.com.alura.AluraFake.course.Course;
import br.com.alura.AluraFake.course.Status;
import br.com.alura.AluraFake.task.Exeception.NewSingleException;
import br.com.alura.AluraFake.task.entity.NewSingleChoice;
import br.com.alura.AluraFake.task.entity.Options;
import br.com.alura.AluraFake.task.repository.NewSingleChoiceRepository;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class NewSingleChoiceValidation {

    private final NewSingleChoiceRepository repository;

    public NewSingleChoiceValidation(NewSingleChoiceRepository repository) {
        this.repository = repository;
    }
    public void ValidateStatement(NewSingleChoice single){
        String statement = single.getStatement();
        if (statement == null || statement.trim().isEmpty()){}
    }


    public void validate(NewSingleChoice single, Course course) {
        // 1. Curso deve estar em BUILDING
        if (!Status.BUILDING.name().equalsIgnoreCase(course.getStatus().name())) {
            throw new NewSingleException("The course is not in BUILDING status.");
        }

        // 2. Não pode ter mesma questão
        if (repository.existsByCourseAndStatement(course, single.getStatement())) {
            throw new NewSingleException("The course already contains an activity with this statement.");
        }

        // 3. Validação das alternativas
        validateOptions(single.getOptions(), single.getStatement());
    }

    private void validateOptions(List<Options> options, String statement) {
        if (options == null || options.size() < 1 || options.size() > 4) {
            throw new NewSingleException("The activity must have between 2 and 5 options.");
        }

        long correctCount = options.stream().filter(Options::isIscorrect).count();
        if (correctCount != 1) {
            throw new NewSingleException("The activity must have exactly one correct option.");
        }

        Set<String> uniqueTexts = new HashSet<>();
        for (Options option : options) {
            String text = option.getOption().trim();

            if (text.length() < 4 || text.length() > 80) {
                throw new NewSingleException("Each option must be between 4 and 80 characters.");
            }

            if (!uniqueTexts.add(text.toLowerCase())) {
                throw new NewSingleException("Options must not be duplicates.");
            }

            if (text.equalsIgnoreCase(statement.trim())) {
                throw new NewSingleException("Options must not match the statement.");
            }
        }
    }
    public void validateFull(NewSingleChoice single, Course course) {
        validate(single, course);
        validateOptions(single.getOptions(), single.getStatement());
        ValidateStatement(single);
    }
}