import org.junit.jupiter.api.DisplayName;
import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.*;

/** Тесты класса Certificate. */
class CertificateTest {

    @org.junit.jupiter.api.Test
    @DisplayName("Certificate.setEnrollment синхронизирует связь и позволяет переназначение")
    void certificateMaintainsEnrollmentRelationship() {
        Enrollment firstEnrollment = new Enrollment();
        Enrollment secondEnrollment = new Enrollment();
        Certificate certificate = new Certificate("CERT-001", LocalDate.now());

        certificate.setEnrollment(firstEnrollment);
        assertSame(certificate, firstEnrollment.getCertificate());
        assertSame(firstEnrollment, certificate.getEnrollment());

        certificate.setEnrollment(secondEnrollment);
        assertNull(firstEnrollment.getCertificate());
        assertSame(certificate, secondEnrollment.getCertificate());

        certificate.setEnrollment(null);
        assertNull(secondEnrollment.getCertificate());
        assertTrue(certificate.toString().contains("CERT-001"));
    }

    @org.junit.jupiter.api.Test
    @DisplayName("Геттеры, сеттеры и toString класса Certificate")
    void accessorsAndToStringWork() {
        LocalDate date = LocalDate.of(2026, 11, 13);
        Certificate certificate = new Certificate();
        certificate.setNumber("CERT-ACCESSORS");
        certificate.setIssuedAt(date);
        assertEquals("CERT-ACCESSORS", certificate.getNumber());
        assertEquals(date, certificate.getIssuedAt());
        assertTrue(certificate.toString().contains("CERT-ACCESSORS"));
    }
}
