/**
 * Абстрактный класс User описывает общие данные пользователя системы.
 * От него наследуются студент и преподаватель.
 */
public abstract class User {
    private String name;
    private String email;

    protected User() {
    }

    public User(String name, String email) {
        this.name = name;
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Возвращает общую информацию о пользователе.
     *
     * @return имя и электронная почта пользователя
     */
    @Override
    public String toString() {
        return "name='" + name + '\'' +
                ", email='" + email + '\'';
    }
}