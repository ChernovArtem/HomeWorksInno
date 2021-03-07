package part1.lesson03.task03;

import part1.lesson03.task03.exception.DublicatePersonException;

import java.util.Objects;

/**
 * Объект персоны
 */
public class Person implements Comparable<Person> {

    /**
     * Перечисление пола
     */
    enum Sex {
        MAN,
        WOMAN
    }

    /** Возраст */
    private int age;

    /** Пол */
    private Sex sex;

    /** Имя */
    private String name;

    /**
     * Констркутор - создание нового объекта
     * @param age - возраст
     * @param sex - пол
     * @param name - имя
     */
    public Person(int age, Sex sex, String name) {
        this.age = age;
        this.sex = sex;
        this.name = name;
    }

    /**
     * Функция получения значения поля {@link Person#age}
     * @return возвращает возраст
     */
    public int getAge() {
        return age;
    }

    /**
     * Функция получения значения поля {@link Person#sex}
     * @return возвращает пол
     */
    public Sex getSex() {
        return sex;
    }

    /**
     * Функция получения значения поля {@link Person#name}
     * @return возвращает имя
     */
    public String getName() {
        return name;
    }

    /**
     * Сравнение для сортировки
     * @param o - класс {@link Person} для сравнения значений
     * @return разница значений, если равно - 0
     */
    public int compareTo(Person o) {

        if (this == o) {
            return 0;
        }

        int result = this.getSex().compareTo(o.getSex());
        if (result == 0) {
            result = o.getAge() - this.getAge();
        }
        if (result == 0) {
            result = this.getName().compareTo(o.getName());
        }
        if (result == 0) {
            throw new RuntimeException(new DublicatePersonException("Dublicate Person!"));
        }

        return result;
    }

    @Override
    public String toString() {
        return "Person{" +
                "age=" + age +
                ", sex=" + sex +
                ", name='" + name + '\'' +
                '}';
    }
}
