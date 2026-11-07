/**
 * Класс Test описывает проверочный тест, относящийся к модулю.
 * Хранит максимальный и проходной баллы.
 */
public class Test {
    private String title;
    private int maxScore;
    private int passingScore;
    private Module module;

    public Test() {
    }

    public Test(String title, int maxScore, int passingScore) {
        this.title = title;
        this.maxScore = maxScore;
        this.passingScore = passingScore;
    }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public int getMaxScore() { return maxScore; }
    public void setMaxScore(int maxScore) { this.maxScore = maxScore; }

    public int getPassingScore() { return passingScore; }
    public void setPassingScore(int passingScore) { this.passingScore = passingScore; }

    public Module getModule() { return module; }

    /**
     * Устанавливает модуль, к которому относится тест.
     *
     * @param module модуль теста
     */
    public void setModule(Module module) {
        if (this.module == module) return;
        Module oldModule = this.module;
        this.module = module;
        if (oldModule != null && oldModule.getTest() == this) {
            oldModule.setTestInternal(null);
        }
        if (module != null) {
            Test previousTest = module.getTest();
            if (previousTest != null && previousTest != this) {
                previousTest.setModuleInternal();
            }
            module.setTestInternal(this);
        }
    }

    /**
     *
     */
    void setModuleInternal() { this.module = null; }

    /**
     * Проверяет, достигнут ли проходной балл.
     * Балл должен находиться в диапазоне от нуля до максимального балла.
     *
     * @param score количество баллов, набранных студентом
     * @return true, если результат не ниже проходного и не выше максимального балла
     */
    public boolean isPassed(int score) {
        return score >= passingScore && score <= maxScore && score >= 0;
    }

    /**
     * Возвращает текстовое представление теста.
     *
     * @return название теста и параметры оценивания
     */
    @Override
    public String toString() {
        return "Test{title='" + title + '\'' +
                ", maxScore=" + maxScore +
                ", passingScore=" + passingScore + '}';
    }
}
