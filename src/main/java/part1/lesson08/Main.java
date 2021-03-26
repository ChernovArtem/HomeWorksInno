package part1.lesson08;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ForkJoinPool;

public class Main {

    public static void main(String[] args) {

        int size = 100000;          //размер массива
        int borderRandom = 100;     //граница для создания случайных чисел

        int[] numbers = generationNumber(size, borderRandom);

        calculateFactorial(numbers);            //1 вариант
        calculateFactorialForkJoin(numbers);    //2 вариант
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
     * Расчет факториала с использованием ExecutorService
     * @param numbers - массив чисел
     */
    private static void calculateFactorial(int[] numbers) {
        final Map<Integer, BigInteger> factorialResultMap = new ConcurrentHashMap<>();

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

        printResult(numbers, factorialResultMap);

        long finishTime = System.currentTimeMillis();
        System.out.println("executionTime - " + (finishTime - startTime) + " ms");
    }

    /**
     * Расчет факториала с использованием ForkJoinPool
     * @param numbers - массив чисел
     */
    private static void calculateFactorialForkJoin(int[] numbers) {
        final Map<Integer, BigInteger> factorialResultMap = new ConcurrentHashMap<>();

        List<CalculationFactorialForkJoin> calculationFactorialList = new ArrayList<>();
        for (int number : numbers) {
            calculationFactorialList.add(new CalculationFactorialForkJoin(factorialResultMap, number));
        }

        long startTime = System.currentTimeMillis();

        ForkJoinPool forkJoinPool = new ForkJoinPool(10);
        forkJoinPool.invokeAll(calculationFactorialList);
        forkJoinPool.shutdown();

        printResult(numbers, factorialResultMap);

        long finishTime = System.currentTimeMillis();
        System.out.println("executionTime - " + (finishTime - startTime) + " ms");
    }

    /**
     * Вывод результата
     * @param numbers - массив чисел
     * @param factorialResultMap - результат факториалов
     */
    private static void printResult(int[] numbers, Map<Integer, BigInteger>  factorialResultMap) {
        for (int number : numbers) {
            System.out.println(number + "! = " + factorialResultMap.get(number));
        }
    }
}
