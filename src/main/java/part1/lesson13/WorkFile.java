package part1.lesson13;

import part1.lesson10.MyCustomClassLoader;

import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.lang.reflect.InvocationTargetException;

/**
 * класс для работы с классом
 */
public class WorkFile {

    /** общее название классов, от которого создаются новые классы */
    private final static String GENERAL_NAME = "SomeClass";

    /** общий путь до файлов */
    private final static String GENERAL_PATH = "projectResources/lesson13/";

    /** просто название класса, где добавляется счетчик */
    private final String className;

    /** название класса с путем в формате java */
    private final String filenameJava;

    /** название класса с путем в формате class */
    private final String filenameClass;

    /**
     * Конструктор
     * @param i - счетчик создаваемого/редактируемого/компилируемого класса
     */
    public WorkFile(int i) {
        this.className = GENERAL_NAME + i;
        this.filenameJava = GENERAL_PATH + className + ".java";
        this.filenameClass = GENERAL_PATH + className + ".class";
    }

    /**
     * Чтение класса и запись в переменную для дальнейшей работы
     * @return прочитанный общий класс
     */
    public StringBuilder readClass() {
        final String filenameJava = GENERAL_PATH + GENERAL_NAME + ".java";

        StringBuilder builder = new StringBuilder();

        try (Reader reader = new FileReader(filenameJava)) {
            int chr;
            while ((chr = reader.read()) != -1) {
                builder.append((char) chr);
            }
        } catch (FileNotFoundException ex) {
            throw new RuntimeException("File not found!", ex);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }

        return builder;
    }

    /**
     * Изменяем название класса
     * @param builder - общий класс
     * @return измененный класс
     */
    public String changeClass(StringBuilder builder) {

        String str = builder.toString();
        str = str.replace(GENERAL_NAME, className);

        return str;
    }

    /**
     * Записываем класс
     * @param str - изменения для записи
     */
    public void writeClass(String str) {
        try (Writer writer = new FileWriter(filenameJava)) {
            writer.write(str);
            writer.flush();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    /**
     * Компилируем класс и удаляем файл-java, иначе их будет много в системе
     */
    public void compile() {
        File file = new File(filenameJava);

        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        compiler.run(null, null, null, file.getAbsolutePath());

        file.delete();
    }

    /**
     * Загружаем и инициализируем класс, также удалаяем файл-class, иначе их будет много в системе
     * @return проинициализированный класс
     */
    public Object loadAndInitClass() {
        try {
            MyCustomClassLoader classLoader = new MyCustomClassLoader(filenameClass);

            classLoader.loadClass(className);
            Class clazz = Class.forName(className, true, classLoader);

            File file = new File(filenameClass);
            file.delete();

            return clazz.getDeclaredConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException
                | InvocationTargetException | NoSuchMethodException ex) {
            throw new RuntimeException(ex);
        } catch (ClassNotFoundException ex) {
            throw new RuntimeException(ex);
        }
    }
}