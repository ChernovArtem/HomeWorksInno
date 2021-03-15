package part1.lesson04.task01.exception;

/**
 * Исключение, если не удалось найти нужный класс для числа
 */
public class WrongTypeNumberException extends RuntimeException {
    public WrongTypeNumberException(String message) {
        super(message);
    }
}
