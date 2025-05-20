package br.com.alura.AluraFake.course;

import br.com.alura.AluraFake.task.Type.Type;
import br.com.alura.AluraFake.task.entity.Task;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

class CourseServiceTest {

    @InjectMocks
    private CourseService courseService;

    @Mock
    private CourseRepository courseRepository;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    // Helper method to create a Task with type and order
    private Task createTask(Type type, int order) {
        Task task = mock(Task.class);
        when(task.getType()).thenReturn(type);
        when(task.getOrderr()).thenReturn(order);
        return task;
    }

    @Test
    void publishCourse_successfullyUpdatesStatusAndDate() {
        Long courseId = 1L;

        Course course = new Course();
        course.setId(courseId);
        course.setStatus(Status.BUILDING);

        List<Task> tasks = Arrays.asList(
                createTask(Type.SINGLE_CHOICE, 1),
                createTask(Type.OPEN_TEXT, 2),
                createTask(Type.MULTIPLE_CHOICE, 3)
        );
        course.setTasks(tasks);

        when(courseRepository.findById(courseId)).thenReturn(Optional.of(course));
        when(courseRepository.save(any(Course.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Course result = courseService.publishCourse(courseId);

        assertEquals(Status.PUBLISHED, result.getStatus());
        assertNotNull(result.getPublishedAt());
        verify(courseRepository).save(course);
    }

    @Test
    void publishCourse_throwsIfCourseNotFound() {
        when(courseRepository.findById(anyLong())).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> courseService.publishCourse(10L));

        assertEquals("Course not found", ex.getMessage());
    }

    @Test
    void publishCourse_throwsIfStatusNotBuilding() {
        Course course = new Course();
        course.setStatus(Status.PUBLISHED);
        when(courseRepository.findById(anyLong())).thenReturn(Optional.of(course));

        IllegalStateException ex = assertThrows(IllegalStateException.class,
                () -> courseService.publishCourse(1L));

        assertEquals("Course must be in BUILDING status to be published.", ex.getMessage());
    }

    @Test
    void publishCourse_throwsIfMissingTaskTypes() {
        Course course = new Course();
        course.setStatus(Status.BUILDING);

        List<Task> tasks = Collections.singletonList(createTask(Type.SINGLE_CHOICE, 1));
        course.setTasks(tasks);

        when(courseRepository.findById(anyLong())).thenReturn(Optional.of(course));

        IllegalStateException ex = assertThrows(IllegalStateException.class,
                () -> courseService.publishCourse(1L));

        assertEquals("Course must contain at least one task of each type.", ex.getMessage());
    }

    @Test
    void publishCourse_throwsIfOrderNotContinuous() {
        Course course = new Course();
        course.setStatus(Status.BUILDING);

        List<Task> tasks = Arrays.asList(
                createTask(Type.SINGLE_CHOICE, 1),
                createTask(Type.OPEN_TEXT, 3),
                createTask(Type.MULTIPLE_CHOICE, 4)
        );
        course.setTasks(tasks);

        when(courseRepository.findById(anyLong())).thenReturn(Optional.of(course));

        IllegalStateException ex = assertThrows(IllegalStateException.class,
                () -> courseService.publishCourse(1L));

        assertEquals("Tasks must have a continuous sequential order.", ex.getMessage());
    }
}
