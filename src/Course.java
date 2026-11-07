import java.util.ArrayList;
import java.util.List;

/**
 * Класс Course описывает учебный курс.
 * Курс относится к одной категории, может вестись несколькими
 * преподавателями и содержать модули и зачисления студентов.
 */
public class Course {
    private String title;
    private double price;
    private Category category;
    private final List<Teacher> teachers = new ArrayList<>();
    private final List<Module> modules = new ArrayList<>();
    private final List<Enrollment> enrollments = new ArrayList<>();

    public Course() {
    }

    public Course(String title, double price) {
        this.title = title;
        this.price = price;
    }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }

    public Category getCategory() { return category; }

    /**
     * Назначает курсу категорию и обновляет связь с обеих сторон.
     *
     * @param category категория курса; null удаляет связь
     */
    public void setCategory(Category category) {
        if (this.category == category) return;
        Category oldCategory = this.category;
        this.category = category;
        if (oldCategory != null) oldCategory.removeCourseInternal(this);
        if (category != null) category.addCourseInternal(this);
    }

    public List<Teacher> getTeachers() { return teachers; }

    /**
     * Заменяет список преподавателей курса и обновляет обратные связи.
     *
     * @param newTeachers новый список преподавателей
     */
    public void setTeachers(List<Teacher> newTeachers) {
        List<Teacher> copy = newTeachers == null
                ? new ArrayList<>() : new ArrayList<>(newTeachers);
        for (Teacher teacher : new ArrayList<>(teachers)) {
            teacher.removeCourseInternal(this);
        }
        teachers.clear();
        for (Teacher teacher : copy) addTeacher(teacher);
    }

    /**
     * Добавляет преподавателя к курсу и обновляет его список курсов.
     *
     * @param teacher преподаватель курса
     */
    public void addTeacher(Teacher teacher) {
        if (teacher != null && !teachers.contains(teacher)) {
            teachers.add(teacher);
            teacher.addCourseInternal(this);
        }
    }

    /** @param teacher преподаватель, добавляемый во внутренний список */
    void addTeacherInternal(Teacher teacher) {
        if (teacher != null && !teachers.contains(teacher)) teachers.add(teacher);
    }

    /** @param teacher преподаватель, удаляемый из внутреннего списка */
    void removeTeacherInternal(Teacher teacher) { teachers.remove(teacher); }

    public List<Module> getModules() { return modules; }

    /**
     * Заменяет список модулей курса, сохраняя связи с модулями.
     *
     * @param newModules новый список модулей
     */
    public void setModules(List<Module> newModules) {
        List<Module> copy = newModules == null
                ? new ArrayList<>() : new ArrayList<>(newModules);
        for (Module module : new ArrayList<>(modules)) module.setCourse(null);
        modules.clear();
        for (Module module : copy) addModule(module);
    }

    /**
     * Создаёт модуль с очередным порядковым номером и добавляет его к курсу.
     *
     * @param title название нового модуля
     * @return созданный и добавленный модуль
     */
    public Module addModule(String title) {
        Module module = new Module(title, modules.size() + 1);
        module.setCourse(this);
        return module;
    }

    /**
     * Добавляет существующий модуль к курсу.
     *
     * @param module модуль, который нужно добавить
     */
    public void addModule(Module module) {
        if (module != null) module.setCourse(this);
    }

    /** @param module модуль, добавляемый во внутренний список */
    void addModuleInternal(Module module) {
        if (module != null && !modules.contains(module)) modules.add(module);
    }

    /** @param module модуль, удаляемый из внутреннего списка */
    void removeModuleInternal(Module module) { modules.remove(module); }

    public List<Enrollment> getEnrollments() { return enrollments; }

    /**
     * Заменяет список зачислений курса и обновляет обратные связи.
     *
     * @param newEnrollments новый список зачислений
     */
    public void setEnrollments(List<Enrollment> newEnrollments) {
        List<Enrollment> copy = newEnrollments == null
                ? new ArrayList<>() : new ArrayList<>(newEnrollments);
        for (Enrollment enrollment : new ArrayList<>(enrollments)) {
            enrollment.setCourse(null);
        }
        enrollments.clear();
        for (Enrollment enrollment : copy) addEnrollment(enrollment);
    }

    /**
     * Добавляет зачисление к курсу.
     *
     * @param enrollment зачисление студента на этот курс
     */
    public void addEnrollment(Enrollment enrollment) {
        if (enrollment != null) enrollment.setCourse(this);
    }

    /** @param enrollment зачисление, добавляемое во внутренний список */
    void addEnrollmentInternal(Enrollment enrollment) {
        if (enrollment != null && !enrollments.contains(enrollment)) enrollments.add(enrollment);
    }

    /** @param enrollment зачисление, удаляемое из внутреннего списка */
    void removeEnrollmentInternal(Enrollment enrollment) { enrollments.remove(enrollment); }

    /**
     * Считает общее количество уроков во всех модулях курса.
     *
     * @return количество уроков курса
     */
    public int lessonCount() {
        int count = 0;
        for (Module module : modules) {
            count += module.getLessons().size();
        }
        return count;
    }

    /**
     * Возвращает краткую информацию о курсе.
     *
     * @return название, стоимость и количество связанных объектов
     */
    @Override
    public String toString() {
        return "Course{title='" + title + '\'' +
                ", price=" + price +
                ", category=" + (category == null ? "none" : category.getName()) +
                ", teachersCount=" + teachers.size() +
                ", modulesCount=" + modules.size() +
                ", enrollmentsCount=" + enrollments.size() + '}';
    }
}
