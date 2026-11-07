import java.time.LocalDate;

/**
 * Класс Certificate описывает сертификат, выдаваемый после завершения курса.
 */
public class Certificate {
    private String number;
    private LocalDate issuedAt;
    private Enrollment enrollment;

    public Certificate() {
    }

    public Certificate(String number, LocalDate issuedAt) {
        this.number = number;
        this.issuedAt = issuedAt;
    }

    public String getNumber() { return number; }
    public void setNumber(String number) { this.number = number; }

    public LocalDate getIssuedAt() { return issuedAt; }
    public void setIssuedAt(LocalDate issuedAt) { this.issuedAt = issuedAt; }

    public Enrollment getEnrollment() { return enrollment; }

    /**
     * Связывает сертификат с зачислением и обновляет обратную связь.
     *
     * @param enrollment зачисление, на основании которого выдан сертификат
     */
    public void setEnrollment(Enrollment enrollment) {
        if (this.enrollment == enrollment) return;
        Enrollment oldEnrollment = this.enrollment;
        this.enrollment = enrollment;
        if (oldEnrollment != null && oldEnrollment.getCertificate() == this) {
            oldEnrollment.setCertificate(null);
        }
        if (enrollment != null && enrollment.getCertificate() != this) {
            enrollment.setCertificate(this);
        }
    }

    /** @param enrollment зачисление, устанавливаемое во внутреннем поле */
    void setEnrollmentInternal(Enrollment enrollment) { this.enrollment = enrollment; }

    /**
     * Возвращает текстовое представление сертификата.
     *
     * @return номер и дата выдачи сертификата
     */
    @Override
    public String toString() {
        return "Certificate{number='" + number + '\'' +
                ", issuedAt=" + issuedAt + '}';
    }
}
