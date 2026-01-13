package work1.task1;

import java.util.Random;

public class NumberAnalyzer {

    public static void main(String[] args) {

        Random random = new Random();

        int number = random.nextInt(900) + 100;

        System.out.println("Сгенерированное число: " + number);


        int hundreds = number / 100;
        int tens = (number / 10) % 10;
        int ones = number % 10;


        int maxDigit = hundreds;
        if (tens > maxDigit) {
            maxDigit = tens;
        }
        if (ones > maxDigit) {
            maxDigit = ones;
        }

        System.out.println("Наибольшая цифра: " + maxDigit);
    }
}