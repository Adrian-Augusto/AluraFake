package br.com.alura.AluraFake.task.Controller;

import br.com.alura.AluraFake.task.dto.NewSingleChoiceDTO;
import br.com.alura.AluraFake.task.entity.NewSingleChoice;
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
    private NewSingleChoiceService newSingle;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testNewSingleChoice() {
        // Arrange - criar DTO de entrada e entidade mockada de retorno
        NewSingleChoiceDTO dto = new NewSingleChoiceDTO();
        dto.setCourseId(1L);
        dto.setStatement("Pergunta teste");

        NewSingleChoice entity = new NewSingleChoice();
        entity.setId(123L);
        entity.setStatement("Pergunta teste");

        // Configura mock para retornar a entidade ao chamar service
        when(newSingle.singlechoice(dto)).thenReturn(entity);

        // Act - chamar o método do controller
        ResponseEntity<NewSingleChoiceDTO> response = controller.newSingleChoice(dto);

        // Assert
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());

        NewSingleChoiceDTO responseBody = response.getBody();
        assertNotNull(responseBody);
        assertEquals(123L, responseBody.getCourseId()); // cuidado com o campo usado!
        assertEquals("Pergunta teste", responseBody.getStatement());

        // Verifica se service foi chamado 1 vez
        verify(newSingle, times(1)).singlechoice(dto);
    }
}
