package part1.lesson04.task03;

import part1.lesson04.task01.exception.DublicateNumberException;
import part1.lesson04.task01.exception.WrongTypeNumberException;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.Objects;

public class MathBox extends ObjectBox<Number> {

    /**
     * Конструктор
     * @param numbers - массив чисел
     */
    MathBox(Number[] numbers) {
        for (Number number : numbers) {
            number = cast(number);
            if (list.contains(number)) {
                throw new DublicateNumberException("Dublicate number - " + number);
            }
            list.add(number);
        }
    }

    /**
     * Складывание всех элементов коллекции в одну сумму
     * @return сумма всех элементов коллекции
     */
    Number summator() {
        Double result = 0d;

        for (Number elem : list) {
            result += elem.doubleValue();
        }
        return result;
    }

    /**
     * Деление всех элементов коллекции на делитель
     * @param divisor - делитель
     */
    void splitter(Number divisor) {

        ListIterator<Number> it = list.listIterator();
        while (it.hasNext()) {

            Number elem = it.next();

            Number division = elem.doubleValue() / divisor.doubleValue();
            Number remaind = elem.doubleValue() % divisor.doubleValue();

            //если нет остатка от деления, то записываем целочисленное число
            if (remaind.doubleValue() == 0) {
                it.set(division.longValue());
            } else {
                it.set(division);
            }
        }
    }

    /**
     * Удаление целого числа из коллекции
     * @param number - число для удаление из коллекции
     */
    void removeInteger (Integer number) {
//        if (list.contains(number.longValue())) {
//            list.remove(number.longValue());
//        }

        //в методе remove происходит поиск для удаления, отдельно не вижу смысла проверять как выше
        list.remove(number.longValue());
    }

    /**
     * Преобразование чисел к общим видам,
     * либо {@link Long} для целочисленных, либо {@link Double} для чисел с плавающей точкой
     * @param number - число/класс для приведение к типу
     * @return - число приведенное к типу {@link Long} или {@link Double}
     */
    private Number cast(Number number) {
        switch (number.getClass().getSimpleName()) {
            case "Byte":
            case "Short":
            case "Integer":
            case "Long":
                return number.longValue();
            case "Float":
            case "Double":
                return number.doubleValue();
            default:
                throw new WrongTypeNumberException("Wrong Type Number - " + number.getClass().getName());
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MathBox mathBox = (MathBox) o;
        return Objects.equals(list, mathBox.list);
    }

    @Override
    public int hashCode() {
        return Objects.hash(list);
    }

    @Override
    public String toString() {
        return "MathBox{" +
                "list=" + list +
                '}';
    }
}
