
package work1.task6;


import java.util.List;

public class Order {
    private final List<Product> products;

    public Order(List<Product> products) {
        this.products = products;
    }

    public List<Product> getProducts() { return products; }
}