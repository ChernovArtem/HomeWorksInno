package part1.lesson04.task01;

import java.util.*;

public class Main {

    public static void main(String[] args) {

        int size = 100; //размер массива
        int border = 10; //граница максимального рандомного числа

        //генерирование случайных чисел
        Set<Number> set = generationNumbers(size, border);
        Number[] numbers = convertSetToArray(set);

        MathBox mathBox = new MathBox(numbers);
        System.out.println(mathBox);

        //суммируем
        Number summator = mathBox.summator();
        System.out.println("summator - " + summator);

        //делим
        Number divisor = 2;
        mathBox.splitter(divisor);
        System.out.println(mathBox);

        //удаляем целое число
        int removeInt = 3;
        mathBox.removeInteger(removeInt);
        System.out.println(mathBox);

        //пример работы с Map
        MathBox mathBox1 = new MathBox(new Number[]{1, 2, 3, 4, 5});
        MathBox mathBox2 = new MathBox(new Number[]{5, 4, 3, 2, 1});
        MathBox mathBox3 = new MathBox(new Number[]{1, 2, 3});
        MathBox mathBox4 = new MathBox(new Number[]{1, 2, 3, 4, 5});

        Map<MathBox, String> map = new HashMap<>();
        map.put(mathBox1, "mathBox1");
        map.put(mathBox2, "mathBox2");
        map.put(mathBox3, "mathBox3");
        System.out.println(map);

        map.put(mathBox4, "mathBox4");
        System.out.println(map);
    }

    /**
     * Генерирование чисел, как целочисленных, так и с плавающей точкой
     * @param size - размер генерируемого массива
     * @param border - граница максимального рандомного числа
     * @return коллекция {@link Set} с генерируемыми числами
     */
    private static Set<Number> generationNumbers(int size, int border) {

        Set<Number> set = new HashSet<>();

        Random rnd = new Random();
        while (set.size() != size) {

            int type = rnd.nextInt(2);

            Number elem = 0;
            switch (type) {
                case 0: elem = rnd.nextInt(border); break;
                case 1: elem = rnd.nextDouble() * border; break;
            }

            set.add(elem);
        }

        return set;
    }

    /**
     * Преобразование коллекции в массив и приведение к большему количесву типов
     * @param set - коллекция с генерируемыми числами
     * @return массив с генерируемыми числами и разными классами (от {@link Byte} до {@link Double})
     */
    private static Number[] convertSetToArray(Set<Number> set) {
        Random rnd = new Random();

        Number[] numbers = set.toArray(new Number[0]);

        for (int i = 0; i < set.size(); i++) {
            if (numbers[i] instanceof Integer) {
                int type = rnd.nextInt(4);

                switch (type) {
                    case 0: numbers[i] = numbers[i].byteValue(); break;
                    case 1: numbers[i] = numbers[i].shortValue(); break;
                    case 2: numbers[i] = numbers[i].intValue(); break;
                    case 3: numbers[i] = numbers[i].longValue(); break;
                }
            } else {
                int type = rnd.nextInt(2);

                switch (type) {
                    case 0: numbers[i] = numbers[i].floatValue(); break;
                    case 1: numbers[i] = numbers[i].doubleValue(); break;
                }
            }
        }

        return numbers;
    }
}
