package work1.task2;

public abstract class Flower {

    protected String name;
    protected double price;

    public Flower(String name, double price) {
        this.name = name;
        this.price = price; //dewdwd
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }
}