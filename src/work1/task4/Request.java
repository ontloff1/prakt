package work1.task4;

public class Request {
    private static int requestCounter = 1; // Просто для статистики
    private int id;
    private Book book;
    private boolean isFulfilled; // Выполнен ли запрос

    public Request(Book book) {
        this.id = requestCounter++;
        this.book = book;
        this.isFulfilled = false;
    }

    public Book getBook() { return book; }
    public boolean isFulfilled() { return isFulfilled; }
    public void setFulfilled(boolean fulfilled) { isFulfilled = fulfilled; }

    @Override
    public String toString() {
        return "Запрос #" + id + " на книгу: '" + book.getTitle() + "' (Закрыт: " + isFulfilled + ")";
    }
}