package work1.task3;

public class Main {
    public static void main(String[] args) {
        // 1. Создаем этапы (рабочих мест) для нашей линии
        // Вариант 1: Кузов, Шасси, Двигатель
        ILineStep bodyStep = new LineStep("Кузов Седан");
        ILineStep chassisStep = new LineStep("Шасси 4x4");
        ILineStep engineStep = new LineStep("Двигатель V8");

        // 2. Создаем линию и передаем ей эти этапы
        IAssemblyLine assemblyLine = new CarAssemblyLine(bodyStep, chassisStep, engineStep);

        // 3. Создаем пустую "болванку" машины (скелет)
        Car car = new Car();

        // 4. Запускаем конвейер
        assemblyLine.assembleProduct(car);

        // 5. Проверяем результат
        car.showCar();
    }
}