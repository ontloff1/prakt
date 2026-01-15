package work1.task2;

public class Main {
    public static void main(String[] args) {

        Flower rose1 = new Rose(100);
        Flower rose2 = new Rose(100);
        Flower tulip1 = new Tulip(60);

        Flower[] bouquet = {rose1, rose2, tulip1};

        double totalCost = 0;

        System.out.println("Состав букета:");

        for (Flower currentFlower : bouquet) {
            System.out.println(currentFlower.getName() + " - " + currentFlower.getPrice());
            totalCost = totalCost + currentFlower.getPrice();
        }

        System.out.println("-----------------");
        System.out.println("Общая стоимость: " + totalCost);
    }
}