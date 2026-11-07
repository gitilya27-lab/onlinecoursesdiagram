import java.util.ArrayList;
import java.util.List;

/**
 * Класс Category описывает категорию курсов.
 * Одна категория может объединять несколько курсов.
 */
public class Category {
    private String name;
    private final List<Course> courses = new ArrayList<>();

    public Category() {
    }

    public Category(String name) {
        this.name = name;
    }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public List<Course> getCourses() { return courses; }

    /**
     * Заменяет список курсов категории и обновляет обратные связи.
     *
     * @param newCourses новый список курсов
     */
    public void setCourses(List<Course> newCourses) {
        List<Course> copy = newCourses == null
                ? new ArrayList<>() : new ArrayList<>(newCourses);
        for (Course course : new ArrayList<>(courses)) {
            if (course.getCategory() == this) course.setCategory(null);
        }
        courses.clear();
        for (Course course : copy) addCourse(course);
    }

    /**
     * Добавляет курс в категорию и устанавливает категорию у курса.
     *
     * @param course курс, который нужно добавить
     */
    public void addCourse(Course course) {
        if (course != null) {
            course.setCategory(this);
        }
    }

    /** @param course курс, добавляемый во внутренний список */
    void addCourseInternal(Course course) {
        if (course != null && !courses.contains(course)) {
            courses.add(course);
        }
    }

    /** @param course курс, удаляемый из внутреннего списка */
    void removeCourseInternal(Course course) {
        courses.remove(course);
    }

    /**
     * Возвращает текстовое представление категории.
     *
     * @return название категории и количество курсов
     */
    @Override
    public String toString() {
        return "Category{name='" + name + '\'' +
                ", coursesCount=" + courses.size() + '}';
    }
}
