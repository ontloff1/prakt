package work1.task6;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Task6 {

    // Задача 1
    public static List<String> task1(List<String> names) {
        return names.stream()
                .filter(name -> name.length() > 5)
                .map(String::toUpperCase)
                .collect(Collectors.toList());
    }

    // Задача 2
    public static List<Integer> task2(List<Integer> numbers) {
        return numbers.stream()
                .distinct()
                .sorted()
                .collect(Collectors.toList());
    }

    // Задача 3
    public static long task3(List<String> strings) {
        return strings.stream()
                .filter(s -> s.startsWith("A"))
                .count();
    }

    // Задача 4
    public static OptionalInt task4(int[] numbers) {
        return IntStream.of(numbers)
                .filter(n -> n > 100)
                .findFirst();
    }

    // Задача 5
    public static boolean task5(int[] numbers) {
        return IntStream.of(numbers)
                .allMatch(n -> n > 0);
    }

    // Задача 6
    public static boolean task6(int[] numbers) {
        return IntStream.of(numbers)
                .anyMatch(n -> n > 0);
    }

    // Задача 7
    public static boolean task7(int[] numbers) {
        return IntStream.of(numbers)
                .noneMatch(n -> n < 0);
    }

    // Задача 8
    public static Map<Integer, List<String>> task8(List<String> strings) {
        return strings.stream()
                .collect(Collectors.groupingBy(String::length));
    }

    // Задача 9
    public static List<Integer> task9(List<List<Integer>> matrix) {
        return matrix.stream()
                .flatMap(List::stream)
                .collect(Collectors.toList());
    }

    // Задача 10
    public static Map<Integer, List<Person>> task10(List<Person> people) {
        return people.stream()
                .collect(Collectors.groupingBy(Person::getAge));
    }

    // Задача 11
    public static Map<Boolean, List<Integer>> task11(List<Integer> numbers) {
        return numbers.stream()
                .collect(Collectors.partitioningBy(n -> n % 2 == 0));
    }

    // Задача 12
    public static int task12(List<Integer> numbers) {
        return numbers.stream()
                .mapToInt(Integer::intValue)
                .sum();
    }

    // Задача 13
    public static OptionalDouble task13(List<Integer> numbers) {
        return numbers.stream()
                .mapToInt(Integer::intValue)
                .average();
    }

    // Задача 14
    public static List<Integer> task14(List<Integer> numbers) {
        return numbers.stream()
                .sorted(Collections.reverseOrder())
                .limit(3)
                .collect(Collectors.toList());
    }

    // Задача 15
    public static List<Integer> task15(List<Integer> numbers) {
        return numbers.stream()
                .skip(5)
                .limit(10)
                .collect(Collectors.toList());
    }

    // Задача 16
    public static List<Product> task16(List<Order> orders) {
        return orders.stream()
                .flatMap(order -> order.getProducts().stream())
                .collect(Collectors.toList());
    }

    // Задача 17
    public static OptionalInt task17(List<Integer> numbers) {
        return numbers.stream()
                .filter(n -> n % 2 == 0)
                .peek(System.out::println)
                .mapToInt(Integer::intValue)
                .max();
    }

    // Задача 18
    public static int task18(List<Integer> numbers) {
        return numbers.stream()
                .reduce(1, (a, b) -> a * b);
    }

    // Задача 19
    public static Map<String, Long> task19(List<Person> people) {
        return people.stream()
                .collect(Collectors.groupingBy(
                        Person::getProfession,
                        Collectors.counting()
                ));
    }

    // Задача 20
    public static List<String> task20(Stream<Person> peopleStream) {
        return peopleStream
                .filter(Person::isActive)
                .filter(p -> p.getAge() > 25)
                .map(Person::getName)
                .distinct()
                .sorted(Collections.reverseOrder())
                .limit(10)
                .collect(Collectors.toList());
    }
}