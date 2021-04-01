package part1.lesson10;

import part1.lesson10.exception.FailedCompileException;
import part1.lesson10.exception.FailedReflectionException;

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
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        final String className = "SomeClass";
        final String filenameJava = "projectResources/lesson10/" + className + ".java";
        final String filenameClass = "projectResources/lesson10/" + className + ".class";

        String text = getTextFromConsole();
        writeTextToMethod(filenameJava, text);

        compileJavaFile(filenameJava);

        MyCustomClassLoader classLoader = new MyCustomClassLoader(filenameClass);
        loadClass(className, classLoader);
        runMethodDoWork(className, classLoader);
    }

    /**
     * Считывание кода для метода doWork построчно из консоли
     * @return - текст из консоли
     */
    private static String getTextFromConsole() {

        Scanner scanner = new Scanner(System.in);
        StringBuilder builder = new StringBuilder();

        System.out.println("Write the code for the doWork method (empty line - exit):");

        String textMethod;
        while (!(textMethod = scanner.nextLine()).equals("")) {
            builder.append(textMethod).append("\n");
        }

        return builder.toString();
    }

    /**
     * Чтение java-файла и запись текста из консоли в его метод doWork
     * @param filename - путь и название java-файла
     * @param text - код в виде текста, который нужно записать в метод
     */
    private static void writeTextToMethod(String filename, String text) {

        File file = new File(filename);
        StringBuilder builder = new StringBuilder();

        //чтение файла
        try (Reader reader = new FileReader(file)) {

            int chr;
            while ((chr = reader.read()) != -1) {
                builder.append((char) chr);
            }
        } catch (FileNotFoundException ex) {
            throw new RuntimeException("File not found!", ex);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }

        //поиск метода
        int index = builder.indexOf("public void doWork()");
        if (index <= 0) {
            return;
        }

        //ищем тело метода для записи,
        //если тела нет, то ничего не записываем
        char chr;
        for (int i = ++index; i < builder.length(); i++) {

            chr = builder.charAt(i);
            if (chr == '{') {
                builder.insert(++i, "\n" + text);
                break;
            } else if (chr == ';' || chr == '}') {
                break;
            }
        }

        //записываем в файл
        try (Writer writer = new FileWriter(file)) {
            writer.write(builder.toString());
            writer.flush();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    /**
     * Компилирование java-файла
     * @param filename - путь и название java-файла, который нужно скомпилировать
     */
    private static void compileJavaFile(String filename) {
        File file = new File(filename);

        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();

        System.out.println("Compiling " + file.getName());
        int result = compiler.run(null, null, null, file.getAbsolutePath());
        if (result != 0) {
            throw new FailedCompileException("Failed to compile: " + result);
        }
    }

    /**
     * Загрузка класса
     * @param nameClass - название класса
     * @param classLoader - класс для загрузки класса
     */
    private static void loadClass(String nameClass, MyCustomClassLoader classLoader) {
        try {
            System.out.println("Load Class");
            classLoader.loadClass(nameClass);
        } catch (ClassNotFoundException ex) {
            throw new RuntimeException(ex);
        }
    }

    /**
     * Запуск метода doWork
     * @param nameClass - название класса
     * @param classLoader - класс для загрузки класса
     */
    private static void runMethodDoWork(String nameClass, ClassLoader classLoader) {
        try {
            final Class clazz = Class.forName(nameClass, true, classLoader);
            Worker worker = (Worker) clazz.getDeclaredConstructor().newInstance();

            System.out.println("Start doWork method");
            worker.doWork();
        } catch (InstantiationException | IllegalAccessException |
                InvocationTargetException | NoSuchMethodException ex) {
            throw new FailedReflectionException(ex);
        } catch (ClassNotFoundException ex) {
            throw new RuntimeException(ex);
        }
    }
}
