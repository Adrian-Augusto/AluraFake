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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TaskControllerTest {

    @InjectMocks
    private TaskController controller;

    @Mock
    private NewOpenTextService newOpenTextService;

    @Mock
    private NewMultipleService newMultipleService;

    @Mock
    private NewSingleChoiceService newSingleChoiceService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testNewOpenTextExercise() {
        // Arrange
        NewOpenTextDTO dto = new NewOpenTextDTO();
        NewOpenText entity = new NewOpenText();
        entity.setId(10L);

        when(newOpenTextService.newOpenTextExercise(dto)).thenReturn(entity);

        // Act
        ResponseEntity<NewOpenText> response = controller.newOpenTextExercise(dto);

        // Assert
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());  // corpo não pode ser nulo
        assertEquals(10L, response.getBody().getId());

        verify(newOpenTextService, times(1)).newOpenTextExercise(dto);
    }

    @Test
    void testNewSingleChoice() {
        // Arrange
        NewSingleChoiceDTO dto = new NewSingleChoiceDTO();
        dto.setCourseId(1L);
        dto.setStatement("Pergunta teste");

        NewSingleChoice entity = new NewSingleChoice();
        entity.setId(123L);
        entity.setStatement("Pergunta teste");

        when(newSingleChoiceService.singlechoice(dto)).thenReturn(entity);

        // Act
        ResponseEntity<NewSingleChoice> response = controller.newSingleChoice(dto);

        // Assert
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertEquals(123L, response.getBody().getId());
        assertEquals("Pergunta teste", response.getBody().getStatement());

        verify(newSingleChoiceService, times(1)).singlechoice(dto);
    }

    @Test
    void testNewMultipleChoice() {
        // Arrange
        NewMultiplechoiceDTO dto = new NewMultiplechoiceDTO();
        NewMultiplechoice entity = new NewMultiplechoice();
        entity.setId(50L);

        when(newMultipleService.newMultipleChoice(dto)).thenReturn(entity);

        // Act
        ResponseEntity<NewMultiplechoice> response = controller.newMultipleChoice(dto);

        // Assert
        assertNotNull(response, "ResponseEntity não deve ser nulo");
        assertEquals(200, response.getStatusCodeValue(), "Código HTTP deve ser 200");
        assertNotNull(response.getBody(), "Corpo da resposta não deve ser nulo");
        assertEquals(50L, response.getBody().getId(), "ID do corpo deve ser 50L");

        verify(newMultipleService, times(1)).newMultipleChoice(dto);
    }}