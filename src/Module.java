import java.util.ArrayList;
import java.util.List;

/**
 * Класс Module описывает модуль курса.
 * Модуль содержит уроки и может иметь один итоговый тест.
 */
public class Module {
    private String title;
    private int orderNumber;
    private Course course;
    private final List<Lesson> lessons = new ArrayList<>();
    private Test test;

    public Module() {
    }

    public Module(String title, int orderNumber) {
        this.title = title;
        this.orderNumber = orderNumber;
    }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public int getOrderNumber() { return orderNumber; }
    public void setOrderNumber(int orderNumber) { this.orderNumber = orderNumber; }

    public Course getCourse() { return course; }

    /**
     * Устанавливает курс-владелец модуля и синхронизирует связь.
     *
     * @param course курс, которому принадлежит модуль
     */
    public void setCourse(Course course) {
        if (this.course == course) return;
        Course oldCourse = this.course;
        this.course = course;
        if (oldCourse != null) oldCourse.removeModuleInternal(this);
        if (course != null) course.addModuleInternal(this);
    }

    public List<Lesson> getLessons() { return lessons; }

    /**
     * Заменяет список уроков модуля и обновляет обратные связи.
     *
     * @param newLessons новый список уроков
     */
    public void setLessons(List<Lesson> newLessons) {
        List<Lesson> copy = newLessons == null
                ? new ArrayList<>() : new ArrayList<>(newLessons);
        for (Lesson lesson : new ArrayList<>(lessons)) lesson.setModule(null);
        lessons.clear();
        for (Lesson lesson : copy) addLesson(lesson);
    }

    /**
     * Добавляет урок в модуль и назначает ему этот модуль.
     *
     * @param lesson урок, который нужно добавить
     */
    public void addLesson(Lesson lesson) {
        if (lesson != null) lesson.setModule(this);
    }

    /** @param lesson урок, добавляемый во внутренний список */
    void addLessonInternal(Lesson lesson) {
        if (lesson != null && !lessons.contains(lesson)) lessons.add(lesson);
    }

    /** @param lesson урок, удаляемый из внутреннего списка */
    void removeLessonInternal(Lesson lesson) { lessons.remove(lesson); }

    public Test getTest() { return test; }

    /**
     * Назначает модулю тест. Если тест был связан с другим модулем,
     * старая связь автоматически удаляется.
     *
     * @param test тест модуля; null удаляет тест
     */
    public void setTest(Test test) {
        if (this.test == test) return;
        Test oldTest = this.test;
        this.test = test;
        if (oldTest != null) oldTest.setModuleInternal();
        if (test != null) test.setModule(this);
    }

    /** @param test тест, устанавливаемый во внутреннем поле */
    void setTestInternal(Test test) { this.test = test; }

    /**
     * Возвращает краткую информацию о модуле.
     *
     * @return название, номер, количество уроков и наличие теста
     */
    @Override
    public String toString() {
        return "Module{title='" + title + '\'' +
                ", orderNumber=" + orderNumber +
                ", lessonsCount=" + lessons.size() +
                ", hasTest=" + (test != null) + '}';
    }
}
