import java.time.LocalDate;
import java.util.UUID;

/**
 * Класс Enrollment описывает запись студента на определённый курс.
 * Хранит дату зачисления, статус и количество завершённых уроков.
 */
public class Enrollment {
    private LocalDate enrolledAt;
    private EnrollmentStatus status;
    private int completedLessons;
    private Student student;
    private Course course;
    private Certificate certificate;

    public Enrollment() {
        this.enrolledAt = LocalDate.now();
        this.status = EnrollmentStatus.ACTIVE;
    }

    public Enrollment(LocalDate enrolledAt, EnrollmentStatus status,
                      int completedLessons) {
        this.enrolledAt = enrolledAt == null ? LocalDate.now() : enrolledAt;
        this.status = status == null ? EnrollmentStatus.ACTIVE : status;
        this.completedLessons = Math.max(0, completedLessons);
    }

    public LocalDate getEnrolledAt() { return enrolledAt; }
    public void setEnrolledAt(LocalDate enrolledAt) { this.enrolledAt = enrolledAt; }

    public EnrollmentStatus getStatus() { return status; }
    public void setStatus(EnrollmentStatus status) { this.status = status; }

    public int getCompletedLessons() { return completedLessons; }
    public void setCompletedLessons(int completedLessons) {
        this.completedLessons = Math.max(0, completedLessons);
    }

    public Student getStudent() { return student; }

    /**
     * Назначает студента этому зачислению и обновляет список студента.
     *
     * @param student студент, который записан на курс
     */
    public void setStudent(Student student) {
        if (this.student == student) return;
        Student oldStudent = this.student;
        this.student = student;
        if (oldStudent != null) oldStudent.removeEnrollmentInternal(this);
        if (student != null) student.addEnrollmentInternal(this);
    }

    public Course getCourse() { return course; }

    /**
     * Назначает курс этому зачислению и обновляет список курса.
     *
     * @param course курс, на который записан студент
     */
    public void setCourse(Course course) {
        if (this.course == course) return;
        Course oldCourse = this.course;
        this.course = course;
        if (oldCourse != null) oldCourse.removeEnrollmentInternal(this);
        if (course != null) course.addEnrollmentInternal(this);
    }

    public Certificate getCertificate() { return certificate; }

    /**
     * Устанавливает сертификат и синхронизирует связь с ним.
     *
     * @param certificate сертификат; null удаляет связь
     */
    public void setCertificate(Certificate certificate) {
        if (this.certificate == certificate) return;
        Certificate oldCertificate = this.certificate;
        this.certificate = certificate;
        if (oldCertificate != null) oldCertificate.setEnrollmentInternal(null);
        if (certificate != null) certificate.setEnrollmentInternal(this);
    }

    /**
     * Отмечает урок как завершённый, если он относится к курсу зачисления.
     * Метод рассчитан на один вызов для каждого завершённого урока.
     * После завершения всех уроков статус автоматически меняется на COMPLETED.
     *
     * @param lesson завершённый урок
     */
    public void completeLesson(Lesson lesson) {
        if (lesson == null || status != EnrollmentStatus.ACTIVE || course == null) {
            return;
        }

        Module module = lesson.getModule();
        if (module == null || module.getCourse() != course) {
            return;
        }

        int totalLessons = course.lessonCount();
        if (totalLessons == 0 || completedLessons >= totalLessons) {
            return;
        }

        completedLessons++;
        if (completedLessons >= totalLessons) {
            completedLessons = totalLessons;
            status = EnrollmentStatus.COMPLETED;
        }
    }

    /**
     * Рассчитывает процент завершённых уроков курса.
     *
     * @return прогресс от 0 до 100 процентов; 0, если в курсе нет уроков
     */
    public double progress() {
        if (course == null || course.lessonCount() == 0) return 0.0;
        int totalLessons = course.lessonCount();
        int completed = Math.min(completedLessons, totalLessons);
        return completed * 100.0 / totalLessons;
    }

    /**
     * Выдаёт сертификат, если зачисление завершено.
     * Повторный вызов возвращает уже созданный сертификат.
     *
     * @return созданный сертификат или null, если курс ещё не завершён
     */
    public Certificate issueCertificate() {
        if (status != EnrollmentStatus.COMPLETED) return null;
        if (certificate != null) return certificate;

        String number = "CERT-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        Certificate newCertificate = new Certificate(number, LocalDate.now());
        setCertificate(newCertificate);
        return newCertificate;
    }

    /**
     * Возвращает краткую информацию о зачислении.
     *
     * @return дата, статус, прогресс, студент и курс
     */
    @Override
    public String toString() {
        return "Enrollment{enrolledAt=" + enrolledAt +
                ", status=" + status +
                ", completedLessons=" + completedLessons +
                ", progress=" + progress() + "%" +
                ", student=" + (student == null ? "none" : student.getName()) +
                ", course=" + (course == null ? "none" : course.getTitle()) +
                ", certificate=" + (certificate == null ? "none" : certificate.getNumber()) + '}';
    }
}
