package part1.lesson12;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

public class Main {

    public static void main(String[] args) {
        final Random random = new Random();

        //тут будут храниться результаты
        final Map<Integer, BigInteger> resultMap = new ConcurrentHashMap<>();

        final int size = 100000;          //размер массива
        final int borderRandom = 100;     //граница для создания случайных чисел

        //генерирование случайных чисел
        int[] numbers = new int[size];
        for (int i = 0; i < size; i++) {
            numbers[i] = random.nextInt(borderRandom);
        }

        long startTime = System.currentTimeMillis();

        //используем стрим (быстро и коротко)
        Arrays.stream(numbers)
                .distinct()
                .parallel()
                .forEach(number -> {
                    //проверяем есть ли значение в мапе, если нет, то высчитываем его
                    final BigInteger value = resultMap.computeIfAbsent(number, (key) -> {
                        BigInteger result = BigInteger.valueOf(1);

                        for (int i = number; i > 0; i--) {
                            if (resultMap.containsKey(i)) {
                                result = result.multiply(resultMap.get(i));
                                break;
                            }
                            result = result.multiply(BigInteger.valueOf(i));
                        }

                        return result;
                    });

                    //проверяем значение в мапе, если его нет, то записываем
                    resultMap.putIfAbsent(number, value);
                });

        //выводим результаты
        Arrays.stream(numbers)
                .forEach(number -> System.out.println(number + "! = " + resultMap.get(number)));

        long finishTime = System.currentTimeMillis();
        System.out.println("executionTime - " + (finishTime - startTime) + " ms");
    }
}
