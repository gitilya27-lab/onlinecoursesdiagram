import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import java.time.LocalDate;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

/** Тесты класса Student. */
class StudentTest {

    private Student student;

    @BeforeEach
    void setUp() {
        student = new Student("Nina", "nina@example.com", LocalDate.now());
    }

    @org.junit.jupiter.api.Test
    @DisplayName("Student.addEnrollment устанавливает связь со студентом")
    void studentAddEnrollmentUpdatesBothSides() {
        Enrollment enrollment = new Enrollment();
        student.addEnrollment(enrollment);

        assertSame(student, enrollment.getStudent());
        assertTrue(student.getEnrollments().contains(enrollment));

        Student anotherStudent = new Student("Alex", "alex@example.com", LocalDate.now());
        anotherStudent.setEnrollments(List.of(enrollment));

        assertFalse(student.getEnrollments().contains(enrollment));
        assertTrue(anotherStudent.getEnrollments().contains(enrollment));
        anotherStudent.setEnrollments(null);
        assertNull(enrollment.getStudent());
    }

    @org.junit.jupiter.api.Test
    @DisplayName("Геттеры, сеттеры и toString класса Student")
    void accessorsAndToStringWork() {
        LocalDate date = LocalDate.of(2026, 11, 13);
        student.setName("Nina Updated");
        student.setEmail("updated@example.com");
        student.setRegisteredAt(date);
        assertEquals("Nina Updated", student.getName());
        assertEquals("updated@example.com", student.getEmail());
        assertEquals(date, student.getRegisteredAt());
        assertTrue(student.toString().contains("Nina Updated"));
    }
}
