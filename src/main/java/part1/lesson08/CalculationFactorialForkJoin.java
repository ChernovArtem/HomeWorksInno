package part1.lesson08;

import java.math.BigInteger;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

/**
 * Класс для подсчета результата факториала в новых потоках
 */
public class CalculationFactorialForkJoin implements Callable<BigInteger> {

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
    CalculationFactorialForkJoin(Map<Integer, BigInteger>  factorialResultMap, int number) {
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

        //перемножаем числа из очереди в новых потоках с помощью Fork/Join
        ForkJoinPool forkJoinPool = new ForkJoinPool(10);
        forkJoinPool.invoke(new MultiplyTask());
        forkJoinPool.shutdown();

        factorialResultMap.put(number, result);
        return result;
    }

    /**
     * Класс перемножения чисел в новых потоках
     */
    private class MultiplyTask extends RecursiveAction {

        /**
         * Перемножаем число из очереди с результатом и создаем еще несколько потоков
         */
        @Override
        protected void compute() {
            BigInteger number1;
            if ((number1 = queue.poll()) != null) {
                synchronized (result) {
                    result = result.multiply(number1);
                }

                if (queue.peek() != null) {
                    MultiplyTask task1 = new MultiplyTask();
                    MultiplyTask task2 = new MultiplyTask();

                    invokeAll(task1, task2);
                }
            }
        }
    }
}
