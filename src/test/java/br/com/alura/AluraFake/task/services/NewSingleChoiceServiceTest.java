package br.com.alura.AluraFake.task.services;

import br.com.alura.AluraFake.course.Course;
import br.com.alura.AluraFake.course.CourseRepository;
import br.com.alura.AluraFake.task.MapperTask.NewSingleChoiceMapper;
import br.com.alura.AluraFake.task.Validation.NewSingleChoiceValidation;
import br.com.alura.AluraFake.task.Validation.TaskOrderManager;
import br.com.alura.AluraFake.task.dto.NewSingleChoiceDTO;
import br.com.alura.AluraFake.task.entity.NewSingleChoice;
import br.com.alura.AluraFake.task.entity.Options;
import br.com.alura.AluraFake.task.repository.NewSingleChoiceRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class NewSingleChoiceServiceTest {

    @InjectMocks
    private NewSingleChoiceService service;

    @Mock
    private NewSingleChoiceRepository repository;

    @Mock
    private CourseRepository courseRepository;

    @Mock
    private NewSingleChoiceValidation validation;

    @Mock
    private TaskOrderManager taskOrderManager;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void deveCriarNovaAtividadeDeEscolhaUnica() {
        // Arrange
        Long cursoId = 1L;
        int ordem = 1;

        NewSingleChoiceDTO dto = new NewSingleChoiceDTO();
        dto.setCourseId(cursoId);
        dto.setOrderr(ordem);
        dto.setStatement("Qual é a capital da França?");

        List<Options> options = new ArrayList<>();
        Options op1 = new Options();
        op1.setOption("Paris");
        op1.setIscorrect(true);

        Options op2 = new Options();
        op2.setOption("Londres");
        op2.setIscorrect(false);

        dto.setOptions(options);
        dto.getOptions().add(op1);
        dto.getOptions().add(op2);

        Course curso = new Course();
        curso.setId(cursoId);
        curso.setTitle("Geografia");

        NewSingleChoice mappedEntity = NewSingleChoiceMapper.toEntity(dto);
        mappedEntity.setCourse(curso);

        when(courseRepository.findById(cursoId)).thenReturn(Optional.of(curso));
        when(repository.findByCourseIdOrderByOrderrAsc(cursoId)).thenReturn(new ArrayList<>());
        when(repository.save(any(NewSingleChoice.class))).thenAnswer(inv -> inv.getArgument(0));

        // Act
        NewSingleChoice resultado = service.singlechoice(dto);

        // Assert
        assertNotNull(resultado);
        assertEquals(curso, resultado.getCourse());
        assertEquals("Qual é a capital da França?", resultado.getStatement());
        assertEquals(2, resultado.getOptions().size());

        for (Options opt : resultado.getOptions()) {
            assertEquals(resultado, opt.getTask());
        }

        verify(validation).validateFull(any(NewSingleChoice.class), eq(curso));
        verify(taskOrderManager).prepararParaInsercao(anyList(), eq(ordem));
        verify(repository).saveAll(anyList());
        verify(repository).save(any(NewSingleChoice.class));
    }

    @Test
    void deveLancarExcecaoSeCursoNaoForEncontrado() {
        // Arrange
        NewSingleChoiceDTO dto = new NewSingleChoiceDTO();
        dto.setCourseId(999L);
        dto.setOrderr(1);
        dto.setStatement("Questão sem curso");

        when(courseRepository.findById(dto.getCourseId())).thenReturn(Optional.empty());

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            service.singlechoice(dto);
        });

        assertEquals("Course not found ", exception.getMessage());
        verify(repository, never()).save(any());
    }
}
