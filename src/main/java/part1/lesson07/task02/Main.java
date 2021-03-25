package part1.lesson07.task02;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class Main {

    public static void main(String[] args) {

        final String path = "projectResources/lesson07/";      //каталог где создать файлы
        int n = 5;                                                      //кол-во создаваемых файлов
        final int size = 10000;                                         //размер файлов
        //final String[] words = GenerationText.generationWords();      //массив слов (генерирование)
        final String[] words = GenerationText.readWordsFromFile();      //массив слов (чтение из файла in.txt task01)
        final int probability = 10;                                     //вероятность слов из массива в предложении

        File[] files = getFiles(path, n, size, words, probability);

        System.out.println();
        System.out.println("Generated files:");
        for (File file : files) {
            System.out.println(file.getName());
        }
    }

    /**
     * Создание файлов с генерируемым текстом
     * @param path - каталог для записи файлов
     * @param n - количество файлов
     * @param size - размер файлов
     * @param words - массив слов
     * @param probability - вероятность
     * @return созданные файлы
     */
    private static File[] getFiles(String path, int n, int size, String[] words, int probability) {

        File[] files = new File[n];

        for (int i = 0; i < n; i++) {

            final String nameFile = "generation" + (i == 0 ? "" : i) + ".txt";
            final String pathFile = path + nameFile;

            files[i] = new File(pathFile);
            try (BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(files[i]))) {

                System.out.println("Generation file № " + (i + 1) + "!");

                GenerationText generationText = new GenerationText(size, words, probability);
                String text = generationText.generation();

                bos.write(text.getBytes());
                bos.flush();

            } catch (FileNotFoundException ex) {
                throw new RuntimeException("File or directory not found!", ex);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            } catch (GeneraionTextException ex) {
                throw new RuntimeException(ex);
            }
        }

        return files;
    }
}