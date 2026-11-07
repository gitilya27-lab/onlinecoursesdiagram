import java.time.LocalDate;

/**
 * Главный класс программы.
 * Создаёт объекты учебной платформы, связывает их между собой
 * и демонстрирует работу основных методов всех классов.
 */
public class Main {

    /**
     * Точка входа в программу.
     * Создаёт студента, преподавателя, категорию, курс, модули,
     * уроки, тест, зачисление и сертификат, после чего выводит результаты.
     *
     * @param args аргументы командной строки
     */
    static void main(String[] args) {
        System.out.println("Дата запуска программы: " + LocalDate.now());
        Student student = new Student(
                "Anna",
                "anna@example.com",
                LocalDate.now()
        );

        Teacher teacher = new Teacher(
                "Ivan",
                "ivan@example.com",
                "Java-разработчик и преподаватель"
        );

        System.out.println("=== Пользователи ===");

        System.out.println(student);
        System.out.println(teacher);


        Category category = new Category("Programming");

        Course course = new Course(
                "Java для начинающих",
                15000.0
        );

        course.setCategory(category);
        teacher.addCourse(course);

        System.out.println("\n=== Категория и курс ===");

        System.out.println(category);
        System.out.println(course);

        System.out.println("Курсы категории: " +
                category.getCourses());

        System.out.println("Курсы преподавателя: " +
                teacher.getCourses());



        Module module1 = course.addModule("Основы Java");

        Module module2 = course.addModule(
                "Объектно-ориентированное программирование"
        );



        Lesson lesson1 = new Lesson(
                "Переменные и типы данных",
                45
        );

        Lesson lesson2 = new Lesson(
                "Условия и циклы",
                60
        );

        Lesson lesson3 = new Lesson(
                "Классы и объекты",
                75
        );

        module1.addLesson(lesson1);
        module1.addLesson(lesson2);
        module2.addLesson(lesson3);


        Test test = new Test(
                "Тест по основам Java",
                100,
                60
        );

        module1.setTest(test);


        System.out.println("\n=== Модули, уроки и тест ===");

        System.out.println(module1);
        System.out.println(module2);

        System.out.println(lesson1);
        System.out.println(lesson2);
        System.out.println(lesson3);

        System.out.println(test);


        System.out.println("Количество уроков в курсе: " +
                course.lessonCount());

        System.out.println("Результат теста (80 баллов): " +
                test.isPassed(80));

        System.out.println("Результат теста (40 баллов): " +
                test.isPassed(40));


        Enrollment enrollment = new Enrollment(
                LocalDate.now(),
                EnrollmentStatus.ACTIVE,
                0
        );

        enrollment.setStudent(student);
        enrollment.setCourse(course);

        System.out.println("\n=== Зачисление ===");

        System.out.println(enrollment);


        System.out.println("Прогресс в начале: " +
                enrollment.progress() + "%");


        enrollment.completeLesson(lesson1);

        System.out.println("Прогресс после первого урока: " +
                enrollment.progress() + "%");

        enrollment.completeLesson(lesson2);

        System.out.println("Прогресс после второго урока: " +
                enrollment.progress() + "%");

        enrollment.completeLesson(lesson3);

        System.out.println("Прогресс после третьего урока: " +
                enrollment.progress() + "%");

        System.out.println("Статус зачисления: " +
                enrollment.getStatus());


        Certificate certificate = enrollment.issueCertificate();

        System.out.println("\n=== Сертификат ===");

        System.out.println(certificate);

        System.out.println("Сертификат в зачислении: " +
                enrollment.getCertificate());


        student.setEmail("anna.new@example.com");

        teacher.setBio(
                "Преподаватель Java и backend-разработки"
        );

        category.setName(
                "Разработка программного обеспечения"
        );

        course.setPrice(12500.0);

        module1.setOrderNumber(1);

        lesson1.setDurationMin(50);

        test.setPassingScore(70);

        certificate.setNumber("CERT-DEMO-001");

        certificate.setIssuedAt(LocalDate.now());

        System.out.println("\n=== После изменения данных ===");

        System.out.println("Email студента: " +
                student.getEmail());

        System.out.println("Описание преподавателя: " +
                teacher.getBio());

        System.out.println("Название категории: " +
                category.getName());

        System.out.println("Цена курса: " +
                course.getPrice());

        System.out.println("Порядковый номер модуля: " +
                module1.getOrderNumber());

        System.out.println("Длительность урока: " +
                lesson1.getDurationMin());

        System.out.println("Проходной балл: " +
                test.getPassingScore());

        System.out.println("Номер сертификата: " +
                certificate.getNumber());

        System.out.println("Дата выдачи сертификата: " +
                certificate.getIssuedAt());


        System.out.println("\n=== Все объекты ===");

        System.out.println(student);
        System.out.println(teacher);
        System.out.println(category);
        System.out.println(course);
        System.out.println(module1);
        System.out.println(module2);
        System.out.println(lesson1);
        System.out.println(test);
        System.out.println(enrollment);
        System.out.println(certificate);

        System.out.println("\nПрограмма завершена.");
    }
}