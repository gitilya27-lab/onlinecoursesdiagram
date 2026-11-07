import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Класс Student описывает студента, зарегистрированного в системе.
 * Студент наследует общие данные от User и может иметь несколько зачислений.
 */
public class Student extends User {
    private LocalDate registeredAt;
    private final List<Enrollment> enrollments = new ArrayList<>();

    public Student() {
        super();
        this.registeredAt = LocalDate.now();
    }

    public Student(String name, String email, LocalDate registeredAt) {
        super(name, email);
        this.registeredAt = registeredAt == null ? LocalDate.now() : registeredAt;
    }

    public LocalDate getRegisteredAt() { return registeredAt; }
    public void setRegisteredAt(LocalDate registeredAt) { this.registeredAt = registeredAt; }

    public List<Enrollment> getEnrollments() { return enrollments; }

    /**
     * Заменяет список зачислений студента и обновляет обратные связи.
     *
     * @param newEnrollments новый список зачислений
     */
    public void setEnrollments(List<Enrollment> newEnrollments) {
        List<Enrollment> copy = newEnrollments == null
                ? new ArrayList<>() : new ArrayList<>(newEnrollments);
        for (Enrollment enrollment : new ArrayList<>(enrollments)) {
            enrollment.setStudent(null);
        }
        enrollments.clear();
        for (Enrollment enrollment : copy) addEnrollment(enrollment);
    }

    /**
     * Добавляет зачисление в список студента и связывает его со студентом.
     *
     * @param enrollment зачисление, которое нужно добавить
     */
    public void addEnrollment(Enrollment enrollment) {
        if (enrollment != null) {
            enrollment.setStudent(this);
        }
    }

    /** @param enrollment зачисление, добавляемое во внутренний список */
    void addEnrollmentInternal(Enrollment enrollment) {
        if (enrollment != null && !enrollments.contains(enrollment)) {
            enrollments.add(enrollment);
        }
    }

    /** @param enrollment зачисление, удаляемое из внутреннего списка */
    void removeEnrollmentInternal(Enrollment enrollment) {
        enrollments.remove(enrollment);
    }

    /**
     * Возвращает текстовое представление студента.
     *
     * @return данные студента и количество его зачислений
     */
    @Override
    public String toString() {
        return "Student{" + super.toString() +
                ", registeredAt=" + registeredAt +
                ", enrollmentsCount=" + enrollments.size() + '}';
    }
}
