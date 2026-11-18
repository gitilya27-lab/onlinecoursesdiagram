import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;

import static org.junit.jupiter.api.Assertions.*;

/** Тесты логики проверки результата тестирования. */
class TestTest {

    private Test assessment;

    @BeforeEach
    void setUp() {
        assessment = new Test("Итоговый тест", 100, 60);
    }

    @org.junit.jupiter.api.Test
    @DisplayName("isPassed принимает проходной и максимальный баллы")
    void isPassedAcceptsScoresInPassingRange() {
        assertTrue(assessment.isPassed(60));
        assertTrue(assessment.isPassed(100));
    }

    @org.junit.jupiter.api.Test
    @DisplayName("isPassed отклоняет баллы ниже проходного, выше максимального и отрицательные")
    void isPassedRejectsScoresOutsideRange() {
        assertFalse(assessment.isPassed(59));
        assertFalse(assessment.isPassed(101));
        assertFalse(assessment.isPassed(-1));
    }

    @org.junit.jupiter.api.Test
    @DisplayName("Конструктор сохраняет переданные значения баллов")
    void constructorStoresProvidedScores() {
        Test test = new Test("Проверочный тест", 50, 60);

        assertEquals("Проверочный тест", test.getTitle());
        assertEquals(50, test.getMaxScore());
        assertEquals(60, test.getPassingScore());
    }


    @org.junit.jupiter.api.Test
    @DisplayName("Сеттеры сохраняют переданные значения без валидации")
    void settersStoreProvidedScores() {
        assessment.setPassingScore(101);
        assertEquals(101, assessment.getPassingScore());

        assessment.setMaxScore(0);
        assertEquals(0, assessment.getMaxScore());

        assessment.setMaxScore(50);
        assertEquals(50, assessment.getMaxScore());
    }

    @org.junit.jupiter.api.Test
    @DisplayName("setModule поддерживает связь теста и модуля с обеих сторон")
    void setModuleMaintainsOneToOneRelationship() {
        Module firstModule = new Module("Первый модуль", 1);
        Module secondModule = new Module("Второй модуль", 2);

        assessment.setModule(firstModule);
        assertSame(firstModule, assessment.getModule());
        assertSame(assessment, firstModule.getTest());

        assessment.setModule(secondModule);
        assertNull(firstModule.getTest());
        assertSame(secondModule, assessment.getModule());
        assertSame(assessment, secondModule.getTest());

        Test replacement = new Test("Дополнительный тест", 20, 10);
        replacement.setModule(secondModule);
        assertNull(assessment.getModule());
        assertSame(secondModule, replacement.getModule());

        replacement.setModule(null);
        assertNull(secondModule.getTest());
        assertNull(replacement.getModule());
    }

    @org.junit.jupiter.api.Test
    @DisplayName("Геттеры, сеттеры и toString теста работают")
    void accessorsAndToStringWork() {
        assessment.setTitle("Новый тест");
        assessment.setMaxScore(120);
        assessment.setPassingScore(80);

        assertEquals("Новый тест", assessment.getTitle());
        assertEquals(120, assessment.getMaxScore());
        assertEquals(80, assessment.getPassingScore());
        assertTrue(assessment.toString().contains("Новый тест"));
    }
}
