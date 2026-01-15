package work1.task3;

public class CarPart implements IProductPart {
    private String name;

    public CarPart(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name;
    }
}