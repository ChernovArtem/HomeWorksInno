package part1.lesson03.task03.sort.impl;

import part1.lesson03.task03.Person;
import part1.lesson03.task03.sort.Sort;

/**
 * Быстрая сортировка
 */
public class QuickSort implements Sort {

    public long sort(Person[] persons) {
        long startTime = System.currentTimeMillis();

        quickSort(persons, 0, persons.length - 1);

        long execTime = System.currentTimeMillis() - startTime;
        return execTime;
    }

    private void quickSort(Person[] persons, int leftBorder, int rightBorder) {

        int leftMarker = leftBorder;
        int rightMarker = rightBorder;

        if (persons.length == 0) {
            return;
        }

        Person person = persons[(leftMarker + rightMarker) / 2];

        do {
            while (person.compareTo(persons[leftMarker]) > 0) {
                leftMarker++;
            }

            while (person.compareTo(persons[rightMarker]) < 0) {
                rightMarker--;
            }

            if (leftMarker <= rightMarker) {
                if (leftMarker < rightMarker) {
                    Person temp = persons[leftMarker];
                    persons[leftMarker] = persons[rightMarker];
                    persons[rightMarker] = temp;
                }
                leftMarker++;
                rightMarker--;
            }
        } while (leftMarker <= rightMarker);

        if (leftMarker < rightBorder) {
            quickSort(persons, leftMarker, rightBorder);
        }
        if (leftBorder < rightMarker) {
            quickSort(persons, leftBorder, rightMarker);
        }
    }
}
