import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

/** Тесты класса Category. */
class CategoryTest {

    private Category category;
    private Course course;

    @BeforeEach
    void setUp() {
        category = new Category("Development");
        course = new Course("Java", 10000.0);
    }

    @org.junit.jupiter.api.Test
    @DisplayName("Category.addCourse и setCourses поддерживают связи с курсами")
    void categoryMethodsUpdateCourseCategory() {
        category.addCourse(course);
        assertSame(category, course.getCategory());
        assertTrue(category.getCourses().contains(course));

        Category anotherCategory = new Category("Backend");
        anotherCategory.setCourses(List.of(course));

        assertFalse(category.getCourses().contains(course));
        assertSame(anotherCategory, course.getCategory());

        anotherCategory.setCourses(null);
        assertNull(course.getCategory());
    }

    @org.junit.jupiter.api.Test
    @DisplayName("Геттеры, сеттеры и toString класса Category")
    void accessorsAndToStringWork() {
        category.setName("Backend Development");
        assertEquals("Backend Development", category.getName());
        assertTrue(category.toString().contains("Backend Development"));
    }
}
