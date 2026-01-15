package work1.task4;

import java.time.LocalDate;

public class Book {
    private String title;
    private double price;
    private BookStatus status;
    private LocalDate publicationDate; // Дата издания
    private LocalDate receiptDate;     // Дата поступления на склад (для поиска "залежавшихся")

    public Book(String title, double price, LocalDate publicationDate) {
        this.title = title;
        this.price = price;
        this.publicationDate = publicationDate;
        this.status = BookStatus.IN_STOCK;
        this.receiptDate = LocalDate.now(); // По умолчанию считаем, что поступила сегодня
    }

    // Геттеры
    public String getTitle() { return title; }
    public double getPrice() { return price; }
    public BookStatus getStatus() { return status; }
    public LocalDate getPublicationDate() { return publicationDate; }
    public LocalDate getReceiptDate() { return receiptDate; }

    // Сеттеры
    public void setStatus(BookStatus status) {
        this.status = status;
        if (status == BookStatus.IN_STOCK) {
            this.receiptDate = LocalDate.now(); // Обновляем дату поступления при завозе
        }
    }

    // Метод для тестов (чтобы искусственно "состарить" книгу)
    public void setReceiptDate(LocalDate receiptDate) {
        this.receiptDate = receiptDate;
    }

    @Override
    public String toString() {
        return String.format("Книга: '%s' | %.2f$ | %s | Издана: %s",
                title, price, status, publicationDate);
    }
}