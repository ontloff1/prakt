package work1.task3;

public class Car implements IProduct {
    private String body;
    private String chassis;
    private String engine;

    @Override
    public void installFirstPart(IProductPart part) {
        this.body = part.getName();
        System.out.println("Установлен кузов: " + this.body);
    }

    @Override
    public void installSecondPart(IProductPart part) {
        this.chassis = part.getName();
        System.out.println("Установлено шасси: " + this.chassis);
    }

    @Override
    public void installThirdPart(IProductPart part) {
        this.engine = part.getName();
        System.out.println("Установлен двигатель: " + this.engine);
    }

    // Метод, чтобы посмотреть результат
    public void showCar() {
        System.out.println("\nМашина готова: Кузов [" + body + "], Шасси [" + chassis + "], Двигатель [" + engine + "]");
    }
}