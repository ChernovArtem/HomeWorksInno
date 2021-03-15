package part1.lesson04.task02;

import java.util.ArrayList;
import java.util.List;

public class ObjectBox {

    private List<Object> objects = new ArrayList<>();

    /**
     * Добавление объекта в коллекцию
     * @param object - объект, который нужно добавить
     */
    public void addObject(Object object) {
        objects.add(object);
    }

    /**
     * Удаление объекта из коллекции
     * @param object - объект, который нужно удалить
     */
    public void deleteObject(Object object) {
        objects.remove(object);
    }

    /**
     * Вывод содержимого коллекции в строку
     * @return строка с содержимым коллекции
     */
    public String dump() {
        return objects.toString();
    }
}
