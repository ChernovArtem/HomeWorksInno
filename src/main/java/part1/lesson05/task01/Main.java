package part1.lesson05.task01;

import part1.lesson03.task03.Person;
import part1.lesson05.task01.object.Animal;

public class Main {

    public static void main(String[] args) {

        int size = 100; //размер массива

        //генерируем данные
        GenerationData generationData = new GenerationData();
        AnimalCardIndex animals = generationData.generationAnimalCardIndex(size);
        System.out.println(animals);

        //добавляем животных
        Person owner = new Person(22, Person.Sex.MAN, "Jony");
        animals.add(101, "Dog", owner, 16);
        animals.add(125, "Dog", owner, 16);
        //animals.add(new Animal(35, "Dog", owner, 16)); //exception

        //изменение по unid
        animals.changeAnimalByUnid(77, "Cat", owner, 8);
        //animals.changeAnimalByUnid(777, "Cat", owner, 8); //exception

        //поиск по unid
        Animal cat = animals.searchByNickname("Cat");
        System.out.println("search - " + cat);

        //сортировка
        System.out.println("Sorting By Name:");
        animals.printSortByName();
        System.out.println("Sorting By Owner:");
        animals.printSortByOwner();
        System.out.println("Sorting By Weight:");
        animals.printSortByWeight();
        System.out.println("Sorting By All Field (Owner, Nickname and Weight):");
        animals.printSort();
    }
}
