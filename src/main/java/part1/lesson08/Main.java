package part1.lesson08;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {

    public static void main(String[] args) {

        int size = 100000;          //размер массива
        int borderRandom = 100;     //граница для создания случайных чисел

        int[] numbers = generationNumber(size, borderRandom);

        calculateFactorial(numbers);
    }

    /**
     * Создание массива со случайными числами
     * @param size - размер генерируемого массива
     * @param border - граница создания слцчайного числа
     * @return массив случайных чисел
     */
    private static int[] generationNumber(int size, int border) {
        int[] numbers = new int[size];

        final Random random = new Random();
        for (int i = 0; i < size; i++) {
            numbers[i] = random.nextInt(border);
        }

        return numbers;
    }

    /**
     * Расчет факториала
     * @param numbers - массив чисел
     */
    private static void calculateFactorial(int[] numbers) {
        final FactorialResultMap factorialResultMap = new FactorialResultMap();

        List<CalculationFactorial> calculationFactorialList = new ArrayList<>();
        for (int number : numbers) {
            calculationFactorialList.add(new CalculationFactorial(factorialResultMap, number));
        }

        long startTime = System.currentTimeMillis();
        ExecutorService executorService = Executors.newFixedThreadPool(10);

        try {
            executorService.invokeAll(calculationFactorialList);
        } catch (InterruptedException ex) {
            throw new RuntimeException(ex);
        }
        executorService.shutdown();

        for (int number : numbers) {
            System.out.println(number + "! = " + factorialResultMap.getResult(number));
        }

        long finishTime = System.currentTimeMillis();
        System.out.println("executionTime - " + (finishTime - startTime) + " ms");
    }
}
