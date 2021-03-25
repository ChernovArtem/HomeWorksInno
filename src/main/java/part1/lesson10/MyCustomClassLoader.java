package part1.lesson10;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * Загрузчик классов
 */
public class MyCustomClassLoader extends ClassLoader {

    /** путь и название class-файла, который нужно найти и вернуть */
    final String pathfile;

    /**
     * Конструктор
     * @param pathfile - путь и название class-файла
     */
    MyCustomClassLoader(String pathfile) {
        this.pathfile = pathfile;
    }

    /**
     * Поиск сlass-файла
     * @param name - название класса для его поиска
     * @return класс {@link Class}
     */
    @Override
    protected Class findClass(String name) {

        File file = new File(pathfile);

        Class simple;
        try (BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file))) {

            byte[] content = new byte[(int) file.length()];
            bis.read(content);

            simple = defineClass(name, content, 0, content.length);
        } catch (IOException e) {
            throw new RuntimeException("");
        }

        return simple;
    }
}
