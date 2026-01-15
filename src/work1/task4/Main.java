package work1.task4;

import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {
        BookStore store = new BookStore();

        // --- 1. Создаем книги ---
        // Книга 1: Старая, дешевая
        Book b1 = new Book("Война и Мир", 15.0, LocalDate.of(1869, 1, 1));
        // Книга 2: Новая, дорогая
        Book b2 = new Book("Java. Полное руководство", 60.0, LocalDate.of(2021, 12, 1));
        // Книга 3: Очень старая на складе (для теста "залежавшихся")
        Book b3 = new Book("Сказки", 5.0, LocalDate.of(2000, 1, 1));

        // "Состарим" поступление третьей книги на год назад
        b3.setReceiptDate(LocalDate.now().minusYears(1));

        store.registerBook(b1);
        store.registerBook(b2);
        store.registerBook(b3);

        // --- 2. Тест сортировки ---
        store.showBooks("price");   // Сортировка по цене
        store.showBooks("date");    // Сортировка по дате издания

        // --- 3. Тест "Залежавшихся книг" ---
        store.showStaleBooks(); // Должна вывести "Сказки"

        // --- 4. Тест процесса покупки и отсутствия товара ---

        // Сценарий 1: Успешная покупка
        System.out.println("\n--- Покупка 'Java' ---");
        store.createOrder(b2);
        store.completeOrder(1); // Заказ #1 выполнен

        // Сценарий 2: Покупка отсутствующей книги
        System.out.println("\n--- Покупка отсутствующей книги ---");
        store.writeOffBook(b1); // Списываем "Войну и Мир"

        store.createOrder(b1);  // Создаем Заказ #2 (Авто-запрос создастся)
        store.completeOrder(2); // Пытаемся завершить -> Должна быть ОШИБКА

        System.out.println("\n--- Завоз товара ---");
        store.addBookToStock(b1); // Завозим книгу -> Запрос закрывается

        store.completeOrder(2); // Пробуем снова -> Теперь УСПЕХ

        // --- 5. Тест отчетов за период ---
        // Немного "сжульничаем" для теста и изменим дату выполнения первого заказа на прошлый месяц
        // (в реальности так не делают, но нам нужно проверить фильтр дат)
        // Но так как у нас нет доступа к списку заказов из Main, мы просто проверим текущий месяц.

        LocalDate start = LocalDate.now().minusDays(30);
        LocalDate end = LocalDate.now();

        store.showCompletedOrders(start, end);
        store.showEarnings(start, end);
    }
}