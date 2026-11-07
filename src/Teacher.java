import java.util.ArrayList;
import java.util.List;

/**
 * Класс Teacher описывает преподавателя.
 * Преподаватель наследует имя и электронную почту от User
 * и может вести несколько курсов.
 */
public class Teacher extends User {
    private String bio;
    private final List<Course> courses = new ArrayList<>();

    public Teacher() {
        super();
    }

    public Teacher(String name, String email, String bio) {
        super(name, email);
        this.bio = bio;
    }

    public String getBio() { return bio; }
    public void setBio(String bio) { this.bio = bio; }

    public List<Course> getCourses() { return courses; }

    /**
     * Заменяет список курсов преподавателя и обновляет обратные связи.
     *
     * @param newCourses новый список курсов
     */
    public void setCourses(List<Course> newCourses) {
        List<Course> copy = newCourses == null
                ? new ArrayList<>() : new ArrayList<>(newCourses);
        for (Course course : new ArrayList<>(courses)) {
            course.removeTeacherInternal(this);
        }
        courses.clear();
        for (Course course : copy) addCourse(course);
    }

    /**
     * Добавляет курс в список курсов преподавателя.
     * Связь со второй стороны также обновляется.
     *
     * @param course курс, который ведёт преподаватель
     */
    public void addCourse(Course course) {
        if (course != null) {
            course.addTeacher(this);
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
     * Возвращает текстовое представление преподавателя.
     *
     * @return данные преподавателя и количество его курсов
     */
    @Override
    public String toString() {
        return "Teacher{" + super.toString() +
                ", bio='" + bio + '\'' +
                ", coursesCount=" + courses.size() + '}';
    }
}
