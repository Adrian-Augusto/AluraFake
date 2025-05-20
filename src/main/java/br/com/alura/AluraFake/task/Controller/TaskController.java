package br.com.alura.AluraFake.task.Controller;

import br.com.alura.AluraFake.task.dto.NewMultiplechoiceDTO;
import br.com.alura.AluraFake.task.dto.NewOpenTextDTO;
import br.com.alura.AluraFake.task.dto.NewSingleChoiceDTO;
import br.com.alura.AluraFake.task.entity.NewMultiplechoice;
import br.com.alura.AluraFake.task.entity.NewOpenText;
import br.com.alura.AluraFake.task.entity.NewSingleChoice;
import br.com.alura.AluraFake.task.services.NewMultipleService;
import br.com.alura.AluraFake.task.services.NewOpenTextService;
import br.com.alura.AluraFake.task.services.NewSingleChoiceService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class TaskController {

    private final NewOpenTextService newOpenTex;
    private final NewMultipleService service;
    private final NewSingleChoiceService newSingle;

    public TaskController(NewOpenTextService newOpenTex, NewMultipleService service, NewSingleChoiceService newSingle) {
        this.newOpenTex = newOpenTex;
        this.service = service;
        this.newSingle = newSingle;
    }

    @PostMapping("/task/new/opentext")
    public ResponseEntity<NewOpenText> newOpenTextExercise(@Valid @RequestBody NewOpenTextDTO dto) {
        NewOpenText newOpenText = newOpenTex.newOpenTextExercise(dto);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/task/new/singlechoice")
    public ResponseEntity<NewSingleChoice> newSingleChoice(@Valid @RequestBody NewSingleChoiceDTO dto) {
        NewSingleChoice newSingleChoice = newSingle.singlechoice(dto);

        return ResponseEntity.ok().build();
    }

    @PostMapping("/task/new/multiplechoice")
    public ResponseEntity<NewMultiplechoice> newMultipleChoice(@Valid @RequestBody NewMultiplechoiceDTO dto) {
        NewMultiplechoice choice = service.newMultipleChoice(dto);
        return ResponseEntity.ok().build();
    }

}