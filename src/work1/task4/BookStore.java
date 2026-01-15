package work1.task4;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class BookStore {
    private List<Book> inventory = new ArrayList<>();
    private List<Order> orders = new ArrayList<>();
    private List<Request> requests = new ArrayList<>();

    // --- БАЗОВЫЕ ФУНКЦИИ (Задание 1) ---

    // 1. Добавить книгу в базу
    public void registerBook(Book book) {
        inventory.add(book);
    }

    // 2. Создать заказ
    public void createOrder(Book book) {
        Order newOrder = new Order(book);
        orders.add(newOrder);
        System.out.println("Создан заказ #" + newOrder.getId() + " на '" + book.getTitle() + "'");

        // Если книги нет, создаем запрос (по условию задачи)
        if (book.getStatus() == BookStatus.ABSENT) {
            requests.add(new Request(book));
            System.out.println("-> Книги нет в наличии. Создан запрос в отдел закупок.");
        }
    }

    // 3. Завершить заказ (только если нет активных запросов)
    public void completeOrder(int orderId) {
        Order order = null;
        // Ищем заказ по ID
        for (Order o : orders) {
            if (o.getId() == orderId) {
                order = o;
                break;
            }
        }

        if (order == null) {
            System.out.println("Ошибка: Заказ #" + orderId + " не найден.");
            return;
        }

        // Проверяем, есть ли незакрытые запросы на эту книгу
        boolean hasPendingRequest = false;
        for (Request req : requests) {
            if (req.getBook() == order.getBook() && !req.isFulfilled()) {
                hasPendingRequest = true;
                break;
            }
        }

        if (hasPendingRequest) {
            System.out.println("ОШИБКА: Нельзя завершить заказ #" + orderId + ". Книги нет на складе.");
        } else {
            order.setStatus(OrderStatus.COMPLETED);
            System.out.println("Успех: Заказ #" + orderId + " выполнен!");
        }
    }

    // 4. Отменить заказ
    public void cancelOrder(int orderId) {
        for (Order o : orders) {
            if (o.getId() == orderId) {
                o.setStatus(OrderStatus.CANCELED);
                System.out.println("Заказ #" + orderId + " отменен.");
            }
        }
    }

    // 5. Списать книгу (статус "отсутствует")
    public void writeOffBook(Book book) {
        book.setStatus(BookStatus.ABSENT);
        System.out.println("Книга списана: " + book.getTitle());
    }

    // 6. Добавить книгу на склад (закрывает запросы)
    public void addBookToStock(Book book) {
        book.setStatus(BookStatus.IN_STOCK);
        System.out.println("Поступление на склад: " + book.getTitle());

        // Ищем и закрываем запросы
        for (Request req : requests) {
            if (req.getBook() == book && !req.isFulfilled()) {
                req.setFulfilled(true);
                System.out.println("-> Запрос на книгу закрыт.");
            }
        }
    }

    // --- РАСШИРЕННЫЕ ФУНКЦИИ (Сортировки и Даты) ---

    // 7. Показать список книг с сортировкой
    public void showBooks(String sortType) {
        List<Book> sortedList = new ArrayList<>(inventory);

        switch (sortType) {
            case "alphabet":
                sortedList.sort(Comparator.comparing(Book::getTitle));
                break;
            case "price":
                sortedList.sort(Comparator.comparingDouble(Book::getPrice));
                break;
            case "date": // Дата издания
                sortedList.sort(Comparator.comparing(Book::getPublicationDate));
                break;
            case "stock": // Наличие
                sortedList.sort(Comparator.comparing(Book::getStatus));
                break;
        }

        System.out.println("\n--- Список книг (Сортировка: " + sortType + ") ---");
        for (Book b : sortedList) {
            System.out.println(b);
        }
    }

    // 8. Список выполненных заказов за период
    public void showCompletedOrders(LocalDate from, LocalDate to) {
        List<Order> result = new ArrayList<>();
        for (Order o : orders) {
            if (o.getStatus() == OrderStatus.COMPLETED && o.getExecutionDate() != null) {
                // Проверяем попадание в диапазон дат
                if (!o.getExecutionDate().isBefore(from) && !o.getExecutionDate().isAfter(to)) {
                    result.add(o);
                }
            }
        }
        // Сортировка по цене
        result.sort(Comparator.comparingDouble(Order::getPrice));

        System.out.println("\n--- Выполненные заказы с " + from + " по " + to + " ---");
        for (Order o : result) {
            System.out.println(o);
        }
    }

    // 9. Сумма заработка за период
    public void showEarnings(LocalDate from, LocalDate to) {
        double total = 0;
        for (Order o : orders) {
            if (o.getStatus() == OrderStatus.COMPLETED && o.getExecutionDate() != null) {
                if (!o.getExecutionDate().isBefore(from) && !o.getExecutionDate().isAfter(to)) {
                    total += o.getPrice();
                }
            }
        }
        System.out.println("\nЗаработано за период: " + total + "$");
    }

    // 10. "Залежавшиеся" книги (на складе > 6 месяцев)
    public void showStaleBooks() {
        LocalDate sixMonthsAgo = LocalDate.now().minusMonths(6);
        System.out.println("\n--- Залежавшиеся книги (более 6 мес.) ---");

        for (Book b : inventory) {
            if (b.getStatus() == BookStatus.IN_STOCK && b.getReceiptDate().isBefore(sixMonthsAgo)) {
                System.out.println(b.getTitle() + " | Поступила: " + b.getReceiptDate());
            }
        }
    }
}