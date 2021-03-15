package part1.lesson03.task03.exception;

/**
 * Исключение на одинаковые персоны (совпадает возраст и имя)
 */
public class DublicatePersonException extends RuntimeException {
    public DublicatePersonException(String message) {
        super(message);
    }
}
