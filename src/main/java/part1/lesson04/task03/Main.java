package part1.lesson04.task03;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class Main {
    public static void main(String[] args) {

        int size = 10; //размер массива
        int border = 20; //граница максимального рандомного числа

        //генерирование массива случайных чисел
        Set<Number> set = generationNumbers(size, border);
        Number[] numbers = convertSetToArray(set);

        MathBox mathBox = new MathBox(numbers);
        System.out.println(mathBox);

        //добавление через метод ObjectBox и формирование строки
        mathBox.addObject(34);
        mathBox.addObject(25.32);
        System.out.println("dump - " + mathBox.dump());

        //суммирование
        Number summator = mathBox.summator();
        System.out.println("summator - " + summator);

        //удаление через метод ObjectBox
        mathBox.deleteObject(25.32);
        System.out.println("dump - " + mathBox.dump());

        //деление
        Number divisor = 2;
        mathBox.splitter(divisor);
        System.out.println(mathBox);

        //удаление целого числа
        int removeInt = 17;
        mathBox.removeInteger(removeInt);
        System.out.println(mathBox);

        //проверка добавления Object
        Object obj1 = "string";
        Object obj2 = 123;

        // без исключений, java не даст записать Object на этапе компиляции
//        mathBox.addObject(obj1);
//        mathBox.addObject(obj2);
    }

    /**
     * Генерирование чисел, как целочисленных, так и с плавающей точкой
     *
     * @param size   - размер генерируемого массива
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
                case 0:
                    elem = rnd.nextInt(border);
                    break;
                case 1:
                    elem = rnd.nextDouble() * border;
                    break;
            }

            set.add(elem);
        }

        return set;
    }

    /**
     * Преобразование коллекции в массив и приведение к большему количесву типов
     *
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
                    case 0:
                        numbers[i] = numbers[i].byteValue();
                        break;
                    case 1:
                        numbers[i] = numbers[i].shortValue();
                        break;
                    case 2:
                        numbers[i] = numbers[i].intValue();
                        break;
                    case 3:
                        numbers[i] = numbers[i].longValue();
                        break;
                }
            } else {
                int type = rnd.nextInt(2);

                switch (type) {
                    case 0:
                        numbers[i] = numbers[i].floatValue();
                        break;
                    case 1:
                        numbers[i] = numbers[i].doubleValue();
                        break;
                }
            }
        }

        return numbers;
    }
}
