package part1.lesson10;

import part1.lesson10.exception.FailedCompileException;
import part1.lesson10.exception.FailedReflectionException;

import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
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

        String data = readFileAndAddText(file, text);
        writeFile(file, data);
    }

    /**
     * Чтение файла и запись кода в метод
     * @param file - файл, который нужно прочитать
     * @param text - код, что нужно записать в метод
     * @return - прочитанные данные из текста, с добавленным кодом в метод
     */
    private static String readFileAndAddText(File file, String text) {
        StringBuilder builder = new StringBuilder();

        try (BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file))) {

            int flag = 0;
            while (bis.available() > 0) {

                char chr = (char) bis.read();
                builder.append(chr);

                if ((flag == 0 && builder.toString().contains("public void doWork()"))
                        || (flag == 1 && chr == ';')) {
                    flag++;
                } else if (flag == 1 && chr == '{') {
                    builder.append("\n").append(text);
                    flag++;
                }
            }
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }

        return builder.toString();
    }

    /**
     * Запись в файл
     * @param file - файл, куда записывать
     * @param data - данные, которые записать
     */
    private static void writeFile(File file, String data) {
        try (BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file))) {
            bos.write(data.getBytes());
            bos.flush();
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
