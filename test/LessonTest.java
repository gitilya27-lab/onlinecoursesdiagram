import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

/** Тесты класса Lesson. */
class LessonTest {

    @org.junit.jupiter.api.Test
    @DisplayName("Геттеры, сеттеры и toString класса Lesson")
    void accessorsAndToStringWork() {
        Lesson lesson = new Lesson();
        lesson.setTitle("Collections");
        lesson.setDurationMin(55);
        assertEquals("Collections", lesson.getTitle());
        assertEquals(55, lesson.getDurationMin());
        assertTrue(lesson.toString().contains("Collections"));
    }
}
