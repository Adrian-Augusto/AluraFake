package br.com.alura.AluraFake.task.Controller;

import br.com.alura.AluraFake.task.dto.NewOpenTextDTO;
import br.com.alura.AluraFake.task.entity.NewOpenText;
import br.com.alura.AluraFake.task.entity.NewSingleChoice;
import br.com.alura.AluraFake.task.services.NewOpenTextService;
import br.com.alura.AluraFake.task.services.NewSingleChoiceService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class TaskController {

    private final   NewOpenTextService service;
    private final   NewSingleChoiceService singleChoiceService;

    public TaskController(NewOpenTextService service, NewSingleChoiceService singleChoiceService) {
        this.service = service;
        this.singleChoiceService = singleChoiceService;
    }

    @PostMapping("/task/new/opentext")
    public ResponseEntity<NewOpenText> newOpenTextExercise(@Valid @RequestBody NewOpenTextDTO dto) {
        NewOpenText newOpenText = service.newOpenTextExercise(dto);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/task/new/singlechoice")
    public ResponseEntity<NewSingleChoice>newSingleChoice(@Valid @RequestBody NewSingleChoiceDTO dto) {
        NewSingleChoice newSingleChoice = singleChoiceService.singlechoice(dto);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/task/new/multiplechoice")
    public ResponseEntity newMultipleChoice() {
        return ResponseEntity.ok().build();
    }

}