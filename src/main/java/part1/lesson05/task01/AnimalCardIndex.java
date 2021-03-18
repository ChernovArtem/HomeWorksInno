package part1.lesson05.task01;

import part1.lesson03.task03.Person;
import part1.lesson05.task01.exception.DublicateUnidException;
import part1.lesson05.task01.exception.NotFoundUnidException;
import part1.lesson05.task01.object.Animal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Картотека животных
 */
public class AnimalCardIndex {

    /** Коллекция с животными */
    private final Set<Animal> set;

    /** класс с сортировкой животных */
    private Sorted sorted = new Sorted();

    /**
     * Конструктор
     * @param set - коллекция животных
     */
    AnimalCardIndex(Set<Animal> set) {
        this.set = new HashSet<>(set);
    }

    /**
     * Добавление животного
     * @param unid - идентификатор
     * @param name - кличка
     * @param owner - владелец
     * @param weight - вес
     */
    public void add(int unid, String name, Person owner, int weight) {
        Animal animal = new Animal(unid, name, owner, weight);
        add(animal);
    }

    /**
     * Добавление животного
     * @param animal - класс объекта животного
     */
    public void add(Animal animal) {
        if (!set.contains(animal)) {
            set.add(animal);
        } else {
            throw new DublicateUnidException();
        }
    }

    /**
     * Изменение животного по Unid
     * @param unid - идентификатор
     * @param name - кличка
     * @param owner - владелец
     * @param weight - вес
     */
    public void changeAnimalByUnid(int unid, String name, Person owner, int weight) {
        Animal animal = new Animal(unid, name, owner, weight);
        changeAnimalByUnid(animal);

    }

    /**
     * Изменение животного по Unid
     * @param animal - класс объекта животного
     */
    public void changeAnimalByUnid(Animal animal) {
        if (set.contains(animal)) {
            set.remove(animal);
            set.add(animal);
        } else {
            throw new NotFoundUnidException();
        }
    }

    /**
     * Вывод на экран списка животных в отсортированном порядке по кличке
     */
    public void printSortByName() {
        System.out.println(sorted.sortByNickname());
    }

    /**
     * Вывод на экран списка животных в отсортированном порядке по владельцу
     */
    public void printSortByOwner() {
        System.out.println(sorted.sortByOwner());
    }

    /**
     * Вывод на экран списка животных в отсортированном порядке по весу
     */
    public void printSortByWeight() {
        System.out.println(sorted.sortByWeight());
    }

    /**
     * Вывод на экран списка животных в отсортированном порядке по полям хозяин, кличка животного и вес
     */
    public void printSort() {
        System.out.println(sorted.sortByAllField());
    }

    /**
     * Поиск животного по его кличке
     * @param nickname - кличка животного
     * @return найденный объект животного. Если объект не удалось найти, будет <code>null</code>
     */
    public Animal searchByNickname(String nickname) {
        List<Animal> sortedList = sorted.sortByNickname();
        return binarySearch(sortedList, 0, sortedList.size() - 1, nickname);
    }

    /**
     * Бинарный поиск по кличке
     * @param animals - коллекция животных
     * @param firstElement - номер первого элемента
     * @param lastElement - номер последнего элемента
     * @param nicknameToSearch - кличка, которую ищем
     * @return объеект животного, иначе если не нашли - <code>null</code>
     */
    private Animal binarySearch(List<Animal> animals, int firstElement, int lastElement, String nicknameToSearch) {

        if (lastElement >= firstElement) {
            int mid = firstElement + (lastElement - firstElement) / 2;

            Animal midAnimal = animals.get(mid);
            String nickname = midAnimal.getNickname();

            if (nickname.equals(nicknameToSearch))
                return midAnimal;

            if (nickname.compareTo(nicknameToSearch) > 0)
                return binarySearch(animals, firstElement, mid - 1, nicknameToSearch);

            return binarySearch(animals, mid + 1, lastElement, nicknameToSearch);
        }

        return null;
    }

    /**
     * класс для сортировки по полям
     */
    private class Sorted {

        /**
         * Сортировка по кличке
         * @return отсортированный список
         */
        private List<Animal> sortByNickname() {

            List<Animal> sortedList = new ArrayList(set);
            sortedList.sort((o1, o2) -> {
                if (o1 == o2) {
                    return 0;
                }

                String nickname1 = o1.getNickname();
                String nickname2 = o2.getNickname();

                return nickname1.compareTo(nickname2);
            });

            return sortedList;
        }

        /**
         * Сортировка по владельцу
         * @return отсортированный список
         */
        private List<Animal> sortByOwner() {

            List<Animal> sortedList = new ArrayList(set);
            sortedList.sort((o1, o2) -> {
                if (o1 == o2) {
                    return 0;
                }

                Person owner1 = o1.getOwner();
                Person owner2 = o2.getOwner();

                return owner1.compareTo(owner2);
            });

            return sortedList;
        }

        /**
         * Сортировка по весу
         * @return отсортированный список
         */
        private List<Animal> sortByWeight() {

            List<Animal> sortedList = new ArrayList(set);
            sortedList.sort((o1, o2) -> {
                if (o1 == o2) {
                    return 0;
                }

                int weight1 = o1.getWeight();
                int weight2 = o2.getWeight();

                return weight1 - weight2;
            });

            return sortedList;
        }

        /**
         * Сортировка по владельцу, кличке и весу
         * @return отсортированный список
         */
        private List<Animal> sortByAllField() {
            List<Animal> sortedList = new ArrayList(set);
            sortedList.sort((o1, o2) -> {
                if (o1 == o2) {
                    return 0;
                }

                Person owner1 = o1.getOwner();
                Person owner2 = o2.getOwner();
                int result = owner1.compareTo(owner2);

                if (result == 0) {
                    String nickname1 = o1.getNickname();
                    String nickname2 = o2.getNickname();
                    result = nickname1.compareTo(nickname2);
                }

                if (result == 0) {
                    int weight1 = o1.getWeight();
                    int weight2 = o2.getWeight();
                    result = weight1 - weight2;
                }

                return result;
            });

            return sortedList;
        }
    }

    @Override
    public String toString() {
        return "AnimalCardIndex{" +
                "set=" + set +
                '}';
    }
}
