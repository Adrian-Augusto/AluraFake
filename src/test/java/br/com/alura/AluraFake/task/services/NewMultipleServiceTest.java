package br.com.alura.AluraFake.task.services;

import br.com.alura.AluraFake.course.Course;
import br.com.alura.AluraFake.course.CourseRepository;
import br.com.alura.AluraFake.task.MapperTask.NewMultipleChoiceMapper;
import br.com.alura.AluraFake.task.Validation.NewMultipleChoiceValidation;
import br.com.alura.AluraFake.task.Validation.TaskMultipleManager;
import br.com.alura.AluraFake.task.dto.NewMultiplechoiceDTO;
import br.com.alura.AluraFake.task.entity.NewMultiplechoice;
import br.com.alura.AluraFake.task.entity.Options;
import br.com.alura.AluraFake.task.repository.NewMultiplechoiceRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class NewMultipleServiceTest {

    @InjectMocks
    private NewMultipleService service;

    @Mock
    private NewMultiplechoiceRepository repository;

    @Mock
    private CourseRepository courseRepository;

    @Mock
    private TaskMultipleManager manager;

    @Mock
    private NewMultipleChoiceValidation validation;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void deveCriarNovaAtividadeMultiplaComCursoMockado() {
        // Arrange
        Long cursoId = 1L;
        int ordem = 1;

        List<Options> options = new ArrayList<>();

        Options op1 = new Options();
        op1.setOption("Maçã");
        op1.setIscorrect(true);

        Options op2 = new Options();
        op2.setOption("Cenoura");
        op2.setIscorrect(false);

        Options op3 = new Options();
        op3.setOption("Banana");
        op3.setIscorrect(true);

        options.add(op1);
        options.add(op2);
        options.add(op3);

        NewMultiplechoiceDTO dto = new NewMultiplechoiceDTO();
        dto.setCourseId(cursoId);
        dto.setOrderr(ordem);
        dto.setStatement("Qual das opções são frutas?");
        dto.setOptions(options);

        Course mockCurso = new Course();
        mockCurso.setId(cursoId);
        mockCurso.setTitle("Curso Teste");

        NewMultiplechoice mappedEntity = NewMultipleChoiceMapper.toEntity(dto);
        mappedEntity.setCourse(mockCurso);

        when(courseRepository.findById(cursoId)).thenReturn(Optional.of(mockCurso));
        when(repository.findByCourseIdOrderByOrderrAsc(cursoId)).thenReturn(List.of());
        when(repository.saveAll(any())).thenReturn(null);
        when(repository.save(any(NewMultiplechoice.class))).thenAnswer(inv -> inv.getArgument(0));

        // Act
        NewMultiplechoice resultado = service.newMultipleChoice(dto);

        // Assert
        assertNotNull(resultado);
        assertEquals(mockCurso, resultado.getCourse());
        assertEquals("Qual das opções são frutas?", resultado.getStatement());
        assertEquals(3, resultado.getOptions().size());

        for (Options opt : resultado.getOptions()) {
            assertEquals(resultado, opt.getTask());
        }

        verify(validation).validateFull(any(NewMultiplechoice.class), eq(mockCurso));
        verify(manager).prepararParaInsercao(anyList(), eq(ordem));
        verify(repository).saveAll(any());
        verify(repository).save(any(NewMultiplechoice.class));
    }
}
