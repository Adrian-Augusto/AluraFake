package br.com.alura.AluraFake.task.services;

import br.com.alura.AluraFake.course.Course;
import br.com.alura.AluraFake.course.CourseRepository;
import br.com.alura.AluraFake.task.MapperTask.NewOpenTextMapper;
import br.com.alura.AluraFake.task.Validation.TaskOrderManager;
import br.com.alura.AluraFake.task.dto.NewOpenTextDTO;
import br.com.alura.AluraFake.task.entity.NewOpenText;
import br.com.alura.AluraFake.task.repository.NewOpenTextRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class NewOpenTextServiceTest {

    @InjectMocks
    private NewOpenTextService service;

    @Mock
    private NewOpenTextRepository repository;

    @Mock
    private CourseRepository courseRepository;

    @Mock
    private TaskOrderManager manager;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void deveCriarNovaTarefaDeTextoAberto() {
        // Arrange
        Long cursoId = 1L;
        int ordem = 2;

        NewOpenTextDTO dto = new NewOpenTextDTO();
        dto.setCourseId(cursoId);
        dto.setOrderr(ordem);
        dto.setStatement("Explique o conceito de polimorfismo na programação orientada a objetos.");

        Course mockCurso = new Course();
        mockCurso.setId(cursoId);
        mockCurso.setTitle("Curso Java");

        List<NewOpenText> existingTasks = new ArrayList<>();

        NewOpenText mappedEntity = NewOpenTextMapper.toEntity(dto);
        mappedEntity.setCourse(mockCurso);

        when(courseRepository.findById(cursoId)).thenReturn(Optional.of(mockCurso));
        when(repository.findByCourseIdOrderByOrderrAsc(cursoId)).thenReturn(existingTasks);
        when(repository.save(any(NewOpenText.class))).thenAnswer(inv -> inv.getArgument(0));

        // Act
        NewOpenText resultado = service.newOpenTextExercise(dto);

        // Assert
        assertNotNull(resultado);
        assertEquals(mockCurso, resultado.getCourse());
        assertEquals(dto.getStatement(), resultado.getStatement());
        assertEquals(ordem, resultado.getOrderr());

        verify(manager).prepararParaInsercaoText(existingTasks, ordem);
        verify(repository).saveAll(existingTasks);
        verify(repository).save(any(NewOpenText.class));
    }

    @Test
    void deveLancarExcecaoQuandoCursoNaoForEncontrado() {
        // Arrange
        Long cursoIdInexistente = 999L;
        NewOpenTextDTO dto = new NewOpenTextDTO();
        dto.setCourseId(cursoIdInexistente);
        dto.setOrderr(1);
        dto.setStatement("Pergunta sem curso válido.");

        when(courseRepository.findById(cursoIdInexistente)).thenReturn(Optional.empty());

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            service.newOpenTextExercise(dto);
        });

        assertEquals("Course not found ", exception.getMessage());
        verify(repository, never()).save(any());
    }
}
