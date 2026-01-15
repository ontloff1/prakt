package work1.task3;

public class CarAssemblyLine implements IAssemblyLine {
    // Линия знает о трех шагах (из требования) [cite: 1349]
    private ILineStep step1;
    private ILineStep step2;
    private ILineStep step3;

    // Конструктор принимает 3 шага
    public CarAssemblyLine(ILineStep step1, ILineStep step2, ILineStep step3) {
        this.step1 = step1;
        this.step2 = step2;
        this.step3 = step3;
    }

    @Override
    public IProduct assembleProduct(IProduct product) {
        System.out.println("--- Начало сборки автомобиля ---");

        // Шаг 1: Строим часть и ставим в продукт
        IProductPart part1 = step1.buildProductPart();
        product.installFirstPart(part1);

        // Шаг 2
        IProductPart part2 = step2.buildProductPart();
        product.installSecondPart(part2);

        // Шаг 3
        IProductPart part3 = step3.buildProductPart();
        product.installThirdPart(part3);

        System.out.println("--- Сборка завершена ---");
        return product;
    }
}