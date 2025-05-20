package br.com.alura.AluraFake.course;

import br.com.alura.AluraFake.task.Type.Type;
import br.com.alura.AluraFake.task.entity.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CourseService {

    @Autowired
    private CourseRepository courseRepository;

    public Course publishCourse(Long courseId) {
        // Find course by ID
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new RuntimeException("Course not found"));

        // Check if the course is in BUILDING status
        if (course.getStatus() != Status.BUILDING) {
            throw new IllegalStateException("Course must be in BUILDING status to be published.");
        }

        List<Task> tasks = course.getTasks();

        // Check if the course contains at least one task of each type
        Set<Type> presentTypes = tasks.stream()
                .map(Task::getType)
                .collect(Collectors.toSet());

        if (!(presentTypes.contains(Type.SINGLE_CHOICE)
                && presentTypes.contains(Type.OPEN_TEXT)
                && presentTypes.contains(Type.MULTIPLE_CHOICE))) {
            throw new IllegalStateException("Course must contain at least one task of each type.");
        }

        // Check if task orders are in a continuous sequence
        List<Integer> orders = tasks.stream()
                .map(Task::getOrderr)
                .sorted()
                .collect(Collectors.toList());

        for (int i = 0; i < orders.size(); i++) {
            if (orders.get(i) != i + 1) {
                throw new IllegalStateException("Tasks must have a continuous sequential order.");
            }
        }

        // Update status and publication date
        course.setStatus(Status.PUBLISHED);
        course.setPublishedAt(LocalDateTime.now());

        return courseRepository.save(course);
    }
}
