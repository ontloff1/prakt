package work1.task3;

public class LineStep implements ILineStep {
    private String partName;

    public LineStep(String partName) {
        this.partName = partName;
    }

    @Override
    public IProductPart buildProductPart() {
        System.out.println("Производится деталь: " + partName);
        return new CarPart(partName);
    }
}