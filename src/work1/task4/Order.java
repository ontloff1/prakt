package work1.task4;

import java.time.LocalDate;

public class Order {
    private static int idCounter = 1;
    private int id;
    private Book book;
    private OrderStatus status;
    private double price;             // Цена на момент покупки
    private LocalDate executionDate;  // Дата выполнения

    public Order(Book book) {
        this.id = idCounter++;
        this.book = book;
        this.status = OrderStatus.NEW;
        this.price = book.getPrice(); // Фиксируем цену
    }

    public int getId() { return id; }
    public Book getBook() { return book; }
    public OrderStatus getStatus() { return status; }
    public double getPrice() { return price; }
    public LocalDate getExecutionDate() { return executionDate; }

    public void setStatus(OrderStatus status) {
        this.status = status;
        if (status == OrderStatus.COMPLETED) {
            this.executionDate = LocalDate.now(); // Ставим текущую дату
        }
    }

    // Для тестов (чтобы менять дату выполнения вручную)
    public void setExecutionDate(LocalDate executionDate) {
        this.executionDate = executionDate;
    }

    @Override
    public String toString() {
        String dateStr = (executionDate != null) ? executionDate.toString() : "не выполнен";
        return String.format("Заказ #%d | %s | Статус: %s | Выполнен: %s",
                id, book.getTitle(), status, dateStr);
    }
}