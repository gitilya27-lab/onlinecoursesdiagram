import static org.junit.jupiter.api.Assertions.*;

/** Общие свойства абстрактного User проверяются через анонимного наследника. */
class UserTest {
    @org.junit.jupiter.api.Test
    void constructorSetsNameAndEmail() {
        User user = new User("Anna", "anna@example.com") {};
        assertEquals("Anna", user.getName());
        assertEquals("anna@example.com", user.getEmail());
    }

    @org.junit.jupiter.api.Test
    void accessorsAndToStringWork() {
        User user = new User() {};
        user.setName("Alex");
        user.setEmail("alex@example.com");
        assertEquals("Alex", user.getName());
        assertEquals("alex@example.com", user.getEmail());
        assertTrue(user.toString().contains("Alex"));
        assertTrue(user.toString().contains("alex@example.com"));
    }
}
