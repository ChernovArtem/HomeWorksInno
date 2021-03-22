package part1.lesson07.task02;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.regex.Pattern;

/**
 * Генерирование текста (абзацы <- предложения <- слова)
 */
public class GenerationText {

    /** Класс для создания рандомных значений */
    private final static Random random = new Random();

    /** Лексические значения для формирования текста */
    private final static String lexicon = "abcdefghijklmnopqrstuvwxyz";

    /** Массив готовых слов */
    private final String[] words;

    /** Вероятность взятого слова из массива готовых слов */
    private final int probability;

    /** Размер текста, который осталось сгенерировать */
    private int lastSize;

    /**
     * Конструктор
     * @param size - размер текста
     * @param words - массив готовых слов
     * @param probability
     */
    GenerationText (int size, String[] words, int probability) {
        this.words = words;
        this.probability = probability;
        this.lastSize = size - 2; //резервирую 2 символа (на символ окончания предложения и перенос каретки)
    }

    /**
     * Генератор текста
     * @return возвращается готовый сгенерированный текст
     * @throws GeneraionTextException - не правильный формат данных. Не хватает размера файла для записи хоть одного предложения,
     * либо переданный массив готовых слов не удовлетворяет требованию 1 <= n <= 1000
     */
    public String generation() throws GeneraionTextException {

        if (lastSize < 2) {
            throw new GeneraionTextException("The size is not enough to form the text!");
        }
        if (words.length == 0 || words.length > 1000) {
            throw new GeneraionTextException("The number of words in the array must be between 1 and 1000!");
        }

        StringBuilder builder = new StringBuilder();
        while (lastSize > 0) {
            builder.append(generationParagraph());
        }

        return builder.toString();
    }

    /**
     * Формирование абзаца. В одном абзаце 1 <= n <= 20 предложений.
     * В начале абзаца добавляется табуляция, в конце - перенос каретки на новую строчку
     * @return сформированный абзац
     */
    private StringBuilder generationParagraph() {
        StringBuilder builder = new StringBuilder();

        int length = random.nextInt(20);

        //табуляция перед абзацем для удобства понимания
        builder.append("\t");
        lastSize--;

        //одно предложение будет точно сформировано
        builder.append(generationProposition());

        for (int i = 1; i <= length && lastSize > 1; i++) {

            //пробел перед новым предложением
            builder.append(" ");
            lastSize--;

            //генерирование нового предложения
            builder.append(generationProposition());
        }

        //перенос каретки на новую строчку
        builder.append("\n");
        lastSize--;

        return builder;
    }

    /**
     * Формирование предложения. В предложении 1 <= n <= 15 слов. Между словами произвольно ставятся запятые (1 к 10).
     * Слова разделены одним пробелом. Предложение начинается с заглавной буквы, а заканчивается '.'/'!'/'?'.
     * Предложения в одном абзаце разделены пробелом.
     * @return сформированное предложение
     */
    private StringBuilder generationProposition() {
        StringBuilder builder = new StringBuilder();

        int length = random.nextInt(15);

        //одно слово будет точно и с заглавной буквы
        builder.append(generationWord(true, length == 0));

        for (int i = 1; i <= length && lastSize > 1; i++) {

            //запятая, вероятность 1 к 10
            int commaProbability = random.nextInt(10);
            if (commaProbability == 0 && lastSize > 2) {
                builder.append(",");
                lastSize--;
            }

            //пробел перед новым словом
            builder.append(" ");
            lastSize--;

            //генерирование нового слова
            builder.append(generationWord(false, length == i));
        }

        //символ окончания предложения
        int finishSimbol = random.nextInt(3);
        switch (finishSimbol) {
            case 0: builder.append("."); break;
            case 1: builder.append("!"); break;
            case 2: builder.append("?"); break;
        }
        lastSize--;

        return builder;
    }

    /**
     * Берем слово из массива готовых слов, с вероятностью 1/probability, либо формируем новое слово
     * @param isStartProp - это слово для начало предложения (нужно первую букву сделать заглавной)
     * @param isFinishProp - это слово для конце предложения (нужно подобрать/сформировать слово определенного размера в конец текста)
     * @return готовое слово из массива или сформированное
     */
    private StringBuilder generationWord(boolean isStartProp, boolean isFinishProp) {
        StringBuilder builder = new StringBuilder();

        //вероятность взятия слова из массива готовых слов, вероятность 1/probability
        if (probability > 0) {
            builder.append(getReadyWord(isStartProp, isFinishProp));

            if (builder.length() > 0) {
                lastSize -= builder.length();
                return builder;
            }
        }

        //формирование слова, если вероятности нет или не удалось найти слово с нужным размером в конец текста
        int length = getLengthWord(isFinishProp);
        for (int i = 0; i <= length; i++) {
            final char chr;

            if (isStartProp && i == 0) {
                //Первая заглавная буква, если начало предложения
                chr = lexicon.toUpperCase().charAt(random.nextInt(lexicon.length()));
            } else {
                chr = lexicon.charAt(random.nextInt(lexicon.length()));
            }

            builder.append(chr);
        }

        lastSize -= builder.length();
        return builder;
    }

    /**
     * Берем слово из массива готовых слов, с вероятностью 1/probability
     * @param isStartProp - это слово для начало предложения
     * @param isFinishProp - это слово для конце предложения
     * @return готовое слово из массива
     */
    private StringBuilder getReadyWord(boolean isStartProp, boolean isFinishProp) {
        StringBuilder builder = new StringBuilder();

        final int readyWordProbability = random.nextInt(probability);
        if (readyWordProbability == 0) {

            String randomWord;
            int last;
            boolean isLastSizeEnough;

            //граница для подстраховки, чтобы не ушел навечно в цикл,
            //нужен только для записи последнего слова в сформированный текст,
            //допускаем что в массиве может не быть слова с нужным размером
            int saveBorder = words.length;
            do {
                randomWord = words[random.nextInt(words.length)];

                last = (lastSize - randomWord.length());
                isLastSizeEnough = (!isFinishProp && last < 2) || (isFinishProp && last < 4);
                saveBorder--;

                //если слово последнее в предложении, то нужно либо оставить место еще на 4 символа и больше,
                //либо найти слово, которе не оставит символов совсем.
                //если слово не последнее, то нужно от 2 символов или найти слово, чтобы не оставалось символов
            } while (isLastSizeEnough && last != 0 && saveBorder > 0);

            //границы не нарушены, удалось найти готовое слово
            if (saveBorder != 0) {
                if (isStartProp) {
                    //Первая заглавная буква, если начало предложения
                    char[] chars = randomWord.toCharArray();
                    chars[0] = randomWord.toUpperCase().charAt(0);
                    builder.append(chars);
                } else {
                    builder.append(randomWord);
                }
            }
        }

        return builder;
    }

    /**
     * Подбираем размер слова для его формирования. Размер слова 1 <= n <=15
     * @return размеер слово
     */
    private int getLengthWord(boolean isFinishProp) {

        int length;
        int last;
        boolean isLastSizeEnough;
        do {
            if (lastSize > 15) {
                length = random.nextInt(15);
            } else {
                length = random.nextInt(lastSize);
            }

            last = (lastSize - (length + 1));
            isLastSizeEnough = (!isFinishProp && last < 2) || (isFinishProp && last < 4);

            //если слово последнее в предложении, то нужно либо оставить место еще на 4 символа и больше,
            //либо сформировать слово, которе не оставит символов совсем.
            //если слово не последнее, то нужно от 2 символов или сформировать слово, чтобы не оставалось символов
        } while(isLastSizeEnough && last != 0);

        return length;
    }

    /**
     * Формирование массива готовых слов
     * @return массив готовых слов
     */
    public static String[] generationWords() {

        final int size = 1 + random.nextInt(1000);
        String[] words = new String[size];

        for (int i = 0; i < size; i++) {
            final StringBuilder builder = new StringBuilder();

            final int length = random.nextInt(15);
            for (int l = 0; l <= length; l++) {
                final char chr = lexicon.charAt(random.nextInt(lexicon.length()));
                builder.append(chr);
            }

            words[i] = builder.toString();
        }

        return words;
    }

    /**
     * Чтение файла из task01 и запись в массив готовых слов
     * @return массив готовых слов
     */
    public static String[] readWordsFromFile() {

        final String FILENAME_IN = "src/main/resources/lesson07/task01/in.txt";
        Set<String> set = new HashSet<>();

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

            String[] words = new String[set.size()];
            return set.toArray(words);
        } catch (FileNotFoundException ex) {
            throw new RuntimeException("File or directory not found!", ex);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }
}
