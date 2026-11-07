/**
 * Класс Lesson описывает отдельный урок внутри учебного модуля.
 */
public class Lesson {
    private String title;
    private int durationMin;
    private Module module;

    public Lesson() {
    }

    public Lesson(String title, int durationMin) {
        this.title = title;
        this.durationMin = durationMin;
    }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public int getDurationMin() { return durationMin; }
    public void setDurationMin(int durationMin) { this.durationMin = durationMin; }

    public Module getModule() { return module; }

    /**
     * Назначает уроку модуль и обновляет список уроков модуля.
     *
     * @param module модуль, которому принадлежит урок
     */
    public void setModule(Module module) {
        if (this.module == module) return;
        Module oldModule = this.module;
        this.module = module;
        if (oldModule != null) oldModule.removeLessonInternal(this);
        if (module != null) module.addLessonInternal(this);
    }

    /**
     * Возвращает текстовое представление урока.
     *
     * @return название, длительность и название модуля
     */
    @Override
    public String toString() {
        return "Lesson{title='" + title + '\'' +
                ", durationMin=" + durationMin +
                ", module=" + (module == null ? "none" : module.getTitle()) + '}';
    }
}
