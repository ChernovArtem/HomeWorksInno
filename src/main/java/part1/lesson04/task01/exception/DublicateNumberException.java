package part1.lesson04.task01.exception;

/**
 * Исключение на одинаковые числа
 */
public class DublicateNumberException extends RuntimeException {
    public DublicateNumberException(String message) {
        super(message);
    }
}
