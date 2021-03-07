package part1.lesson03.task03.impl;

import part1.lesson03.task03.Person;
import part1.lesson03.task03.exception.DublicatePersonException;

/**
 * Интерфейс сортировки
 */
public interface SortImpl {

    /**
     * Метод сортировки
     * @param persons - массив класса {@link Person}
     */
    void sort(Person[] persons);

    /**
     * Вывод выполнения сортировки
     * @param typeSort - тип сортировки
     * @param persons - массив отсортированных персон
     * @param execTime - время выполнения сортировки
     */
    default void printResult(String typeSort, Person[] persons, long execTime) {
        System.out.println(typeSort + ", result: ");
        for (Person person : persons) {
            System.out.println(person);
        }
        System.out.println(typeSort + ", execution time - " + execTime + " ms");
    }
}
