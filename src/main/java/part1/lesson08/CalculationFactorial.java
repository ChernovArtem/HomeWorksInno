package part1.lesson08;

import java.math.BigInteger;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Класс для подсчета результата факториала в новых потоках
 */
public class CalculationFactorial implements Callable<BigInteger> {

    /** число по которому нужно посчитать факториал */
    private final int number;

    /** словарь с результатами факториалов */
    private final Map<Integer, BigInteger> factorialResultMap;

    /** очередь для перемножения чисел, тех что еще нет в справочнике */
    private final Queue<BigInteger> queue = new ConcurrentLinkedQueue<>();

    /** результат факториала */
    private BigInteger result = BigInteger.valueOf(1);

    /**
     * Конструктор
     * @param factorialResultMap - словарь с результатами факториалов
     * @param number - число по которому нужно посчитать факториал
     */
    CalculationFactorial(Map<Integer, BigInteger>  factorialResultMap, int number) {
        this.factorialResultMap = factorialResultMap;
        this.number = number;
    }

    /**
     * Callable-метод, который вызывается в новом потоке
     * Расчитывает факториала по числу, если его еще нет в справочнике
     * @return результат факториала
     */
    @Override
    public BigInteger call() {

        //проверяем что в справочнике уже есть результат факториала по этому числу
        if (factorialResultMap.containsKey(number)) {
            return factorialResultMap.get(number);
        } else if (number == 0) {
            factorialResultMap.put(number, result);
            return result;
        }

        //чтобы не проходить полностью все значения по числу от 1 до n,
        // проверяем на наибольшее число в справочнике, остальные добавляются в очередь для перемножения
        for (int i = number; i > 0; i--) {
            if (factorialResultMap.containsKey(i)) {
                queue.offer(factorialResultMap.get(i));
                break;
            }
            queue.offer(BigInteger.valueOf(i));
        }

        //перемножаем числа из очереди в новых потоках
        ExecutorService executorService = Executors.newFixedThreadPool(4);
        do {
            executorService.submit(new MultiplyRunnable());

            try {
                Thread.sleep(1);
            } catch (InterruptedException ex) {
                throw new RuntimeException(ex);
            }

        } while (queue.peek() != null);
        executorService.shutdown();

        //циклом немного быстрее все же, чем с потоками
//        BigInteger number1;
//        while ((number1 = queue.poll()) != null) {
//            result = result.multiply(number1);
//        }

        factorialResultMap.put(number, result);
        return result;
    }

    /**
     * Класс перемножения чисел в новых потоках
     */
    private class MultiplyRunnable implements Runnable {

        /**
         * Перемножаем числа из очереди, если они есть, а потом перемножаем с результатом
         */
        @Override
        public void run() {

            BigInteger number1;
            if ((number1 = queue.poll()) != null) {

                BigInteger number2;
                if ((number2 = queue.poll()) != null) {
                    number1 = number1.multiply(number2);
                }

                synchronized (result) {
                    result = result.multiply(number1);
                }
            }
        }
    }
}
