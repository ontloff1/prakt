package work1.task6;

import java.util.Arrays;
import java.util.List;

class Main {

    public static void main(String[] args) {
        List<Person> people = Arrays.asList(
                new Person("Alice", 30, "Engineer", true),
                new Person("Bob", 28, "Designer", false),
                new Person("Charlie", 35, "Engineer", true)
        );

        List<String> result = Task6.task20(people.stream());
        result.forEach(System.out::println);
    }
}