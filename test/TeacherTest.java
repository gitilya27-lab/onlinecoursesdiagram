import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

/** Тесты класса Teacher. */
class TeacherTest {

    private Teacher teacher;
    private Course course;

    @BeforeEach
    void setUp() {
        teacher = new Teacher("Oleg", "oleg@example.com", "Java teacher");
        course = new Course("Java", 10000.0);
    }

    @org.junit.jupiter.api.Test
    @DisplayName("Teacher.addCourse поддерживает двустороннюю связь")
    void teacherAddCourseUpdatesBothSides() {
        teacher.addCourse(course);

        assertTrue(teacher.getCourses().contains(course));
        assertTrue(course.getTeachers().contains(teacher));

        Teacher anotherTeacher = new Teacher("Eva", "eva@example.com", "Bio");
        anotherTeacher.setCourses(List.of(course));

        // Course допускает нескольких преподавателей, поэтому первый остаётся связанным.
        assertTrue(teacher.getCourses().contains(course));
        assertTrue(anotherTeacher.getCourses().contains(course));
        assertTrue(course.getTeachers().contains(teacher));
        assertTrue(course.getTeachers().contains(anotherTeacher));

        anotherTeacher.setCourses(null);
        assertTrue(anotherTeacher.getCourses().isEmpty());
        assertFalse(course.getTeachers().contains(anotherTeacher));
        assertTrue(course.getTeachers().contains(teacher));
    }

    @org.junit.jupiter.api.Test
    @DisplayName("Геттеры, сеттеры и toString класса Teacher")
    void accessorsAndToStringWork() {
        teacher.setName("Oleg Updated");
        teacher.setEmail("teacher@example.com");
        teacher.setBio("Senior Java developer");
        assertEquals("Oleg Updated", teacher.getName());
        assertEquals("teacher@example.com", teacher.getEmail());
        assertEquals("Senior Java developer", teacher.getBio());
        assertTrue(teacher.toString().contains("Senior Java developer"));
    }
}
