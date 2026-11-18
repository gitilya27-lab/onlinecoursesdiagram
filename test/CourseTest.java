import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/** Тесты логики класса Course. */
class CourseTest {

    private Course course;

    @BeforeEach
    void setUp() {
        course = new Course("Java", 15000.0);
    }

    @org.junit.jupiter.api.Test
    @DisplayName("Конструктор, геттеры, сеттеры и toString курса")
    void constructorAndAccessorsWork() {
        assertEquals("Java", course.getTitle());
        assertEquals(15000.0, course.getPrice(), 0.001);

        course.setTitle("Advanced Java");
        course.setPrice(20000.0);

        assertEquals("Advanced Java", course.getTitle());
        assertEquals(20000.0, course.getPrice(), 0.001);
        assertTrue(course.toString().contains("Advanced Java"));
    }

    @org.junit.jupiter.api.Test
    @DisplayName("addModule создаёт модуль и присваивает следующий номер")
    void addModuleCreatesAndConnectsModules() {
        Module first = course.addModule("Basics");
        Module second = course.addModule("OOP");

        assertEquals(1, first.getOrderNumber());
        assertEquals(2, second.getOrderNumber());
        assertSame(course, first.getCourse());
        assertSame(course, second.getCourse());
        assertEquals(2, course.getModules().size());
    }

    @org.junit.jupiter.api.Test
    @DisplayName("lessonCount суммирует уроки всех модулей курса")
    void lessonCountSumsLessonsFromAllModules() {
        Module first = course.addModule("Basics");
        Module second = course.addModule("OOP");

        first.addLesson(new Lesson("Variables", 30));
        first.addLesson(new Lesson("Loops", 40));
        second.addLesson(new Lesson("Classes", 50));

        assertEquals(3, course.lessonCount());
    }

    @org.junit.jupiter.api.Test
    @DisplayName("setCategory переносит курс из старой категории в новую")
    void setCategoryMaintainsBothSidesOfRelationship() {
        Category oldCategory = new Category("Programming");
        Category newCategory = new Category("Backend");

        course.setCategory(oldCategory);
        assertSame(course, oldCategory.getCourses().get(0));

        course.setCategory(newCategory);
        assertFalse(oldCategory.getCourses().contains(course));
        assertTrue(newCategory.getCourses().contains(course));
        assertSame(newCategory, course.getCategory());

        course.setCategory(null);
        assertTrue(newCategory.getCourses().isEmpty());
        assertNull(course.getCategory());
    }

    @org.junit.jupiter.api.Test
    @DisplayName("setTeachers поддерживает связи с преподавателями с обеих сторон")
    void setTeachersMaintainsBidirectionalRelationship() {
        Teacher first = new Teacher("Anna", "a@example.com", "Bio A");
        Teacher second = new Teacher("Ivan", "i@example.com", "Bio I");

        course.addTeacher(first);
        course.addTeacher(first); // Повторное добавление не должно создавать дубликат.
        assertEquals(1, course.getTeachers().size());
        assertTrue(first.getCourses().contains(course));

        course.setTeachers(List.of(second));
        assertFalse(first.getCourses().contains(course));
        assertTrue(second.getCourses().contains(course));
        assertEquals(List.of(second), course.getTeachers());

        course.setTeachers(null);
        assertTrue(course.getTeachers().isEmpty());
        assertFalse(second.getCourses().contains(course));
        course.addTeacher(null); // Проверка безопасной обработки null.
    }

    @org.junit.jupiter.api.Test
    @DisplayName("setModules заменяет модули и удаляет старые обратные связи")
    void setModulesReplacesModules() {
        Module oldModule = course.addModule("Old module");
        Module replacement = new Module("New module", 1);

        course.setModules(List.of(replacement));

        assertNull(oldModule.getCourse());
        assertSame(course, replacement.getCourse());
        assertEquals(List.of(replacement), course.getModules());

        course.setModules(null);
        assertNull(replacement.getCourse());
        assertTrue(course.getModules().isEmpty());
    }

    @org.junit.jupiter.api.Test
    @DisplayName("setEnrollments заменяет зачисления и синхронизирует курс")
    void setEnrollmentsReplacesEnrollments() {
        Enrollment oldEnrollment = new Enrollment();
        Enrollment newEnrollment = new Enrollment();

        course.addEnrollment(oldEnrollment);
        course.setEnrollments(List.of(newEnrollment));

        assertNull(oldEnrollment.getCourse());
        assertSame(course, newEnrollment.getCourse());
        assertEquals(List.of(newEnrollment), course.getEnrollments());

        course.setEnrollments(null);
        assertNull(newEnrollment.getCourse());
        assertTrue(course.getEnrollments().isEmpty());
        course.addEnrollment(null);
    }
}
