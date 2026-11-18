import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

/** Тесты логики прохождения курса и зачисления студента. */
class EnrollmentTest {

    private Course course;
    private Module module;
    private Lesson firstLesson;
    private Lesson secondLesson;
    private Student student;
    private Enrollment enrollment;

    @BeforeEach
    void setUp() {
        course = new Course("Java", 12000.0);
        module = course.addModule("Основы Java");
        firstLesson = new Lesson("Переменные", 40);
        secondLesson = new Lesson("Условия", 50);
        module.addLesson(firstLesson);
        module.addLesson(secondLesson);

        student = new Student("Maria", "maria@example.com", LocalDate.now());
        enrollment = new Enrollment(LocalDate.now(), EnrollmentStatus.ACTIVE, 0);
        enrollment.setStudent(student);
        enrollment.setCourse(course);
    }

    @org.junit.jupiter.api.Test
    @DisplayName("Начальный прогресс равен нулю")
    void progressStartsAtZero() {
        assertEquals(0.0, enrollment.progress(), 0.001);
        assertEquals(0.0, new Enrollment().progress(), 0.001);
    }

    @org.junit.jupiter.api.Test
    @DisplayName("progress рассчитывает процент завершённых уроков")
    void progressCalculatesPercentage() {
        enrollment.completeLesson(firstLesson);

        assertEquals(50.0, enrollment.progress(), 0.001);
        assertEquals(1, enrollment.getCompletedLessons());

        enrollment.setCompletedLessons(10);
        assertEquals(100.0, enrollment.progress(), 0.001);
    }

    @org.junit.jupiter.api.Test
    @DisplayName("completeLesson переводит зачисление в COMPLETED после последнего урока")
    void completeLessonCompletesEnrollment() {
        enrollment.completeLesson(firstLesson);
        enrollment.completeLesson(secondLesson);

        assertEquals(2, enrollment.getCompletedLessons());
        assertEquals(100.0, enrollment.progress(), 0.001);
        assertEquals(EnrollmentStatus.COMPLETED, enrollment.getStatus());

        enrollment.completeLesson(firstLesson);
        assertEquals(2, enrollment.getCompletedLessons());
    }

    @org.junit.jupiter.api.Test
    @DisplayName("completeLesson игнорирует null и урок из другого курса")
    void completeLessonIgnoresNullAndForeignLesson() {
        enrollment.completeLesson(null);

        Course anotherCourse = new Course("Python", 9000.0);
        Module anotherModule = anotherCourse.addModule("Python basics");
        Lesson foreignLesson = new Lesson("Syntax", 30);
        anotherModule.addLesson(foreignLesson);

        enrollment.completeLesson(foreignLesson);

        assertEquals(0, enrollment.getCompletedLessons());
        assertEquals(0.0, enrollment.progress(), 0.001);
    }

    @org.junit.jupiter.api.Test
    @DisplayName("completeLesson не меняет зачисление с неактивным статусом")
    void completeLessonDoesNothingWhenEnrollmentIsNotActive() {
        enrollment.setStatus(EnrollmentStatus.DROPPED);
        enrollment.completeLesson(firstLesson);

        assertEquals(0, enrollment.getCompletedLessons());
        assertEquals(EnrollmentStatus.DROPPED, enrollment.getStatus());
    }

    @org.junit.jupiter.api.Test
    @DisplayName("issueCertificate не выдаёт сертификат до завершения курса")
    void issueCertificateRequiresCompletedStatus() {
        assertNull(enrollment.issueCertificate());
        assertNull(enrollment.getCertificate());
    }

    @org.junit.jupiter.api.Test
    @DisplayName("issueCertificate создаёт сертификат и повторно возвращает тот же объект")
    void issueCertificateCreatesAndReusesCertificate() {
        enrollment.completeLesson(firstLesson);
        enrollment.completeLesson(secondLesson);

        Certificate certificate = enrollment.issueCertificate();

        assertNotNull(certificate);
        assertTrue(certificate.getNumber().startsWith("CERT-"));
        assertEquals(LocalDate.now(), certificate.getIssuedAt());
        assertSame(enrollment, certificate.getEnrollment());
        assertSame(certificate, enrollment.getCertificate());
        assertSame(certificate, enrollment.issueCertificate());
    }

    @org.junit.jupiter.api.Test
    @DisplayName("Сеттеры студента и курса обновляют связи с обеих сторон")
    void relationshipSettersUpdateBothSides() {
        Student anotherStudent = new Student("Alex", "alex@example.com", LocalDate.now());
        Course anotherCourse = new Course("SQL", 5000.0);

        enrollment.setStudent(anotherStudent);
        enrollment.setCourse(anotherCourse);

        assertFalse(student.getEnrollments().contains(enrollment));
        assertTrue(anotherStudent.getEnrollments().contains(enrollment));
        assertFalse(course.getEnrollments().contains(enrollment));
        assertTrue(anotherCourse.getEnrollments().contains(enrollment));

        enrollment.setStudent(null);
        enrollment.setCourse(null);

        assertNull(enrollment.getStudent());
        assertNull(enrollment.getCourse());
    }

    @org.junit.jupiter.api.Test
    @DisplayName("Конструктор Enrollment задаёт значения по умолчанию и ограничивает отрицательные уроки")
    void constructorNormalizesInput() {
        Enrollment created = new Enrollment(null, null, -5);

        assertEquals(LocalDate.now(), created.getEnrolledAt());
        assertEquals(EnrollmentStatus.ACTIVE, created.getStatus());
        assertEquals(0, created.getCompletedLessons());

        created.setCompletedLessons(-10);
        assertEquals(0, created.getCompletedLessons());
        LocalDate anotherDate = LocalDate.of(2026, 11, 13);
        created.setEnrolledAt(anotherDate);
        created.setStatus(EnrollmentStatus.DROPPED);
        assertEquals(anotherDate, created.getEnrolledAt());
        assertEquals(EnrollmentStatus.DROPPED, created.getStatus());
        assertTrue(created.toString().contains("Enrollment"));
    }
    @org.junit.jupiter.api.Test
    @DisplayName("completeLesson ничего не делает без курса, модуля или уроков в курсе")
    void completeLessonRequiresCourseAndModule() {
        Lesson lessonWithoutModule = new Lesson("Не привязан", 20);
        Enrollment withoutCourse = new Enrollment();
        withoutCourse.completeLesson(lessonWithoutModule);
        assertEquals(0, withoutCourse.getCompletedLessons());

        Course emptyCourse = new Course("Empty", 100.0);
        Enrollment withEmptyCourse = new Enrollment();
        withEmptyCourse.setCourse(emptyCourse);
        withEmptyCourse.completeLesson(lessonWithoutModule);
        assertEquals(0, withEmptyCourse.getCompletedLessons());

        assertEquals(0.0, withEmptyCourse.progress(), 0.001);
    }

    @org.junit.jupiter.api.Test
    @DisplayName("setCertificate заменяет и удаляет сертификат с обеих сторон")
    void setCertificateMaintainsBidirectionalRelationship() {
        Certificate first = new Certificate("CERT-1", LocalDate.now());
        Certificate second = new Certificate("CERT-2", LocalDate.now());

        enrollment.setCertificate(first);
        assertSame(enrollment, first.getEnrollment());

        enrollment.setCertificate(second);
        assertNull(first.getEnrollment());
        assertSame(enrollment, second.getEnrollment());
        assertSame(second, enrollment.getCertificate());

        enrollment.setCertificate(null);
        assertNull(second.getEnrollment());
        assertNull(enrollment.getCertificate());
    }

}
