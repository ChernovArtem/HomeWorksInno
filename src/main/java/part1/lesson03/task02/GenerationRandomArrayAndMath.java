package part1.lesson03.task02;

import java.util.Random;
import java.util.Scanner;

/**
 * Генерация массива N элементов случайными числами от 0 до K.
 * Вывод значений из массива, которые удовлетворяют формуле sqrt(q) = k^2, где sqrt(q) - целочисленное число
 */
public class GenerationRandomArrayAndMath {

    public static void main(String[] args) {

        int n = getIntegerFromScanner("Enter the number of an array of random numbers:");
        int k = getIntegerFromScanner("Enter maximum value for random:");

        int[] array = fillArray(n, k);

        searchAndPrintResult(array);
    }

    /**
     * Получени числа из консоли
     * @param heading - заголовок сообщения, ребующий что-либо ввести
     * @return введеное число в консоли, не может быть отрицательное
     */
    private static int getIntegerFromScanner (String heading) {
        Scanner scanner = new Scanner(System.in);

        System.out.println(heading);
        String string = scanner.next();
        int number;
        try {
            number = Integer.valueOf(string);
            if (number < 0) {
                throw new NegativeNumberException();
            }
        } catch (NumberFormatException | NegativeNumberException ex) {
            throw new RuntimeException("Wrong number format!", ex);
        }

        return number;
    }

    /**
     * Генерирование массива N элементов случайными числами от 0 до K
     * @param n - количество элементов в массиве
     * @param k - ограничение случайных чисел
     * @return сгенерированный массив случайных чисел
     */
    private static int[] fillArray (int n, int k) {
        Random random = new Random();

        int[] array = new int[n];
        for (int i = 0; i < array.length - 1; i++) {
            array[i] = random.nextInt(k);
        }

        return array;
    }

    /**
     * Поиск в массиве элементов, удовлетворяющие требованию и вывод их на экран
     * @param array - сгенерированный массив случайных чисел
     */
    private static void searchAndPrintResult (int[] array) {
        boolean isShowResult = false;

        System.out.println("Results:");
        for (int elem : array) {
            if (isMathMethod(elem)) {
                if (isShowResult) {
                    System.out.print(", ");
                }
                System.out.print(elem);
                isShowResult = true;
            }
        }

        if (!isShowResult) {
            System.out.println("Nothing");
        }
    }

    /**
     * Математический метод, вычисляющий требование sqrt(q) = k^2
     * @param elem - элемент массива со случайным числом
     * @return результат проверки sqrt(q) = k^2
     */
    private static boolean isMathMethod (int elem) {
        int sqrtInt = (int) Math.sqrt(elem);
        int pow = (int) Math.pow(sqrtInt, 2);
        return elem == pow;
    }

    /**
     * Исключение для отрицательного числа
     */
    static class NegativeNumberException extends Exception {
        NegativeNumberException() {
            super();
        }
    }

}

