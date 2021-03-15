package part1.lesson03.task03.sort.impl;

import part1.lesson03.task03.Person;
import part1.lesson03.task03.sort.Sort;

/**
 * Сортировка пузырьком
 */
public class BubbleSort implements Sort {

    public long sort(Person[] persons) {
        long startTime = System.currentTimeMillis();

        boolean sorted = false;
        while (!sorted) {
            sorted = true;

            for (int i = 0; i < persons.length - 1; i++) {
                if (persons[i].compareTo(persons[i + 1]) > 0) {
                    Person temp = persons[i];
                    persons[i] = persons[i + 1];
                    persons[i + 1] = temp;
                    sorted = false;
                }
            }
        }

        long execTime = System.currentTimeMillis() - startTime;
        return execTime;
    }
}
