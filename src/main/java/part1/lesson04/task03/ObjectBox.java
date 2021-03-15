package part1.lesson04.task03;

import java.util.ArrayList;
import java.util.List;

public abstract class ObjectBox<T> {

    protected List<T> list = new ArrayList<>();

    /**
     * Добавление объекта в коллекцию
     * @param object - объект, который нужно добавить
     */
    public void addObject(T object) {
        list.add(object);
    }

    /**
     * Удаление объекта из коллекции
     * @param object - объект, который нужно удалить
     */
    public void deleteObject(T object) {
        list.remove(object);
    }

    /**
     * Вывод содержимого коллекции в строку
     * @return строка с содержимым коллекции
     */
    public String dump() {
        return list.toString();
    }
}
