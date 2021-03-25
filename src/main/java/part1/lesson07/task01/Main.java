package part1.lesson07.task01;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Set;
import java.util.TreeSet;
import java.util.regex.Pattern;

public class Main {

    private static final String PATHFILE = "projectResources/lesson07/";
    private static final String FILENAME_IN = PATHFILE + "in.txt";
    private static final String FILENAME_OUT = PATHFILE + "out.txt";

    public static void main(String[] args) {
        try {
            final Set<String> set = new TreeSet<>();

            readFile(set);
            writeFile(set);

            System.out.println("The sorted words have been written to the file!");
        } catch (FileNotFoundException ex) {
            System.out.println("File not found: " + ex.getMessage());
            ex.printStackTrace();
        } catch (IOException ex) {
            System.out.println("Input-Output Exception: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    /**
     * Чтение файла и запись слов
     * @param set - коллекция для записи слов
     */
    private static void readFile(Set<String> set) throws IOException {

        try (BufferedInputStream bis = new BufferedInputStream(new FileInputStream(FILENAME_IN))) {

            final Pattern p = Pattern.compile("[a-zA-Z']");

            StringBuilder builder = new StringBuilder();
            while (bis.available() > 0) {

                char chr = (char) bis.read();
                String str = String.valueOf(chr);

                if (p.matcher(str).find()) {

                    builder.append(chr);

                } else if (builder.length() > 0) {

                    String word = builder.toString().toLowerCase();
                    set.add(word);

                    builder = new StringBuilder();
                }
            }
        } catch (IOException ex) {
            throw ex;
        }
    }

    /**
     * Запись отсортированных слов
     * @param set - коллекция отсортированных слов
     */
    private static void writeFile(Set<String> set) throws IOException {

        try (BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(FILENAME_OUT))) {

            for (String word : set) {

                word += "\n"; //перенос строки после слова

                bos.write(word.getBytes());
                bos.flush();
            }
        } catch (IOException ex) {
            throw ex;
        }
    }
}
