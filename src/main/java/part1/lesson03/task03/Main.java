package part1.lesson03.task03;

import part1.lesson03.task03.sort.Sort;
import part1.lesson03.task03.sort.impl.BubbleSort;
import part1.lesson03.task03.sort.impl.QuickSort;

import java.util.Random;

public class Main {

    public static void main(String[] args) {

        int lengthArray = 12500; //размер массива персон
        Person[] persons = generationPersons(lengthArray);
        Person[] persons2 = persons.clone();

        Sort bubbleSort = new BubbleSort();
        Sort quickSort = new QuickSort();

        long execTime = bubbleSort.sort(persons);
        quickSort.printResult("bubbleSort", persons, execTime);

        execTime = quickSort.sort(persons2);
        quickSort.printResult("quickSort", persons, execTime);
    }

    /**
     * Генерирование массива персон
     * @param lengthArray - размер массива персон
     * @return сгенерированный массив персон
     */
    private static Person[] generationPersons(int lengthArray) {
        Person[] persons = new Person[lengthArray];

        Random random = new Random();
        for (int i = 0; i < persons.length; i++) {

            int age = random.nextInt(101);
            Person.Sex sex = Person.Sex.values() [random.nextInt(2)];
            String name = generationName();

            persons[i] = new Person(age, sex, name);
        }

        return persons;
    }

    /**
     * Генерирует имя персоны
     * @return сгенерированное имя
     */
    private static String generationName () {

        final String lexicon = "abcdefghijklmnopqrstuvwxyz";

        Random random = new Random();
        StringBuilder builder = new StringBuilder();

        //Первая заглавная буква имени
        char chr = lexicon.toUpperCase().charAt(random.nextInt(lexicon.length()));
        builder.append(chr);

        //остальные буквы имени
        int length = random.nextInt(3) + 4;
        for (int i = 0; i < length; i++) {
            chr = lexicon.charAt(random.nextInt(lexicon.length()));
            builder.append(chr);
        }

        return builder.toString();
    }
}
