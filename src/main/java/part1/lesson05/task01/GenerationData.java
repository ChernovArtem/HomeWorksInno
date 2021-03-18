package part1.lesson05.task01;

import part1.lesson03.task03.Person;
import part1.lesson05.task01.object.Animal;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

/**
 * класс для генерации случайных данных
 */
public class GenerationData {

    /** класс для получения случайных чисел */
    private Random random = new Random();

    /**
     * Генерация картотеки животных
     * @param size - размер коллекции
     */
    public AnimalCardIndex generationAnimalCardIndex(int size) {

        Set<Animal> animals = new HashSet<>(size);

        Person owner = generationPerson();
        int numberAnimals = 1 + random.nextInt(2);

        for (int i = 1; i <= size; i++) {

            if (i == numberAnimals) {
                owner = generationPerson();
                numberAnimals = i + 1 + random.nextInt(2);
            }

            int length = random.nextInt(5) + 3;
            String nickname = generationName(length);
            int weight = random.nextInt(151);

            Animal animal = new Animal(i, nickname, owner, weight);
            animals.add(animal);
        }

        AnimalCardIndex animalCardIndex = new AnimalCardIndex(animals);

        return  animalCardIndex;
    }

    /**
     * Генерация владельцев животных
     * @return
     */
    private Person generationPerson() {

        int age = random.nextInt(101);
        Person.Sex sex = Person.Sex.values() [random.nextInt(2)];

        int length = random.nextInt(3) + 4;
        String name = generationName(length);

        return new Person(age, sex, name);
    }

    /**
     * Генерирует имя персоны/кличка животного
     * @return сгенерированное имя/кличка
     */
    private String generationName(int length) {

        final String lexicon = "abcdefghijklmnopqrstuvwxyz";

        StringBuilder builder = new StringBuilder();

        //Первая заглавная буква имени/кличка
        char chr = lexicon.toUpperCase().charAt(random.nextInt(lexicon.length()));
        builder.append(chr);

        //остальные буквы имени/кличка
        for (int i = 0; i < length; i++) {
            chr = lexicon.charAt(random.nextInt(lexicon.length()));
            builder.append(chr);
        }

        return builder.toString();
    }

}
