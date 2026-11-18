import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/** Тесты логики связей модуля с курсом, уроками и тестом. */
class ModuleTest {

    private Module module;

    @BeforeEach
    void setUp() {
        module = new Module("Основы", 1);
    }

    @org.junit.jupiter.api.Test
    @DisplayName("addLesson добавляет урок и устанавливает обратную связь")
    void addLessonMaintainsRelationship() {
        Lesson lesson = new Lesson("Переменные", 30);

        module.addLesson(lesson);
        module.addLesson(lesson);

        assertSame(module, lesson.getModule());
        assertEquals(1, module.getLessons().size());
    }

    @org.junit.jupiter.api.Test
    @DisplayName("setLessons заменяет список и очищает прежние связи")
    void setLessonsReplacesLessons() {
        Lesson oldLesson = new Lesson("Старый урок", 20);
        Lesson newLesson = new Lesson("Новый урок", 40);
        module.addLesson(oldLesson);

        module.setLessons(List.of(newLesson));

        assertNull(oldLesson.getModule());
        assertSame(module, newLesson.getModule());
        assertEquals(List.of(newLesson), module.getLessons());

        module.setLessons(null);
        assertNull(newLesson.getModule());
        assertTrue(module.getLessons().isEmpty());
        module.addLesson(null);
    }

    @org.junit.jupiter.api.Test
    @DisplayName("setCourse переносит модуль между курсами")
    void setCourseMovesModuleBetweenCourses() {
        Course first = new Course("Java", 1000);
        Course second = new Course("Python", 2000);

        module.setCourse(first);
        module.setCourse(second);

        assertFalse(first.getModules().contains(module));
        assertTrue(second.getModules().contains(module));
        assertSame(second, module.getCourse());

        module.setCourse(null);
        assertFalse(second.getModules().contains(module));
        assertNull(module.getCourse());
    }

    @org.junit.jupiter.api.Test
    @DisplayName("setTest заменяет тест и поддерживает связь с обеих сторон")
    void setTestReplacesPreviousTest() {
        Test first = new Test("Первый", 100, 50);
        Test second = new Test("Второй", 100, 70);

        module.setTest(first);
        module.setTest(second);

        assertNull(first.getModule());
        assertSame(module, second.getModule());
        assertSame(second, module.getTest());

        module.setTest(null);
        assertNull(second.getModule());
        assertNull(module.getTest());
        assertTrue(module.toString().contains("Основы"));
    }
    @org.junit.jupiter.api.Test
    @DisplayName("Поля модуля доступны через геттеры и сеттеры")
    void moduleAccessorsWork() {
        module.setTitle("Обновлённый модуль");
        module.setOrderNumber(3);

        assertEquals("Обновлённый модуль", module.getTitle());
        assertEquals(3, module.getOrderNumber());
        assertTrue(module.toString().contains("Обновлённый модуль"));
    }

}
