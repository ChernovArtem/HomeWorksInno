package part1.lesson05.object;

import part1.lesson03.task03.Person;
import java.util.Objects;

/**
 * объект животное
 */
public class Animal {


    /** универсальный идентификатор */
    final private int unid;

    /** кличка животного */
    final private String nickname;

    /** владелец */
    final private Person owner;

    /** вес */
    final private int weight;

    /**
     * Конструктор
     * @param unid - идентификатор
     * @param nickname - кличка
     * @param owner - владелец
     * @param weight - вес
     */
    public Animal(int unid, String nickname, Person owner, int weight) {
        this.unid = unid;
        this.nickname = nickname;
        this.owner = owner;
        this.weight = weight;
    }

    /**
     * Возвражает кличку животного {@link Animal#nickname}
     * @return кличка животного
     */
    public String getNickname() {
        return nickname;
    }

    /**
     * Возвращает владельца животного {@link Animal#owner}
     * @return владелец животного
     */
    public Person getOwner() {
        return owner;
    }

    /**
     * Возвращает вес животного {@link Animal#weight}
     * @return
     */
    public int getWeight() {
        return weight;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Animal animal = (Animal) o;
        return unid == animal.unid;
    }

    @Override
    public int hashCode() {
        return Objects.hash(unid);
    }

    @Override
    public String toString() {
        return "Animal{" +
                "unid = " + unid +
                ", nickname = '" + nickname + '\'' +
                ", owner = " + owner +
                ", weight = " + weight + " kg"+
                '}';
    }
}
