package part1.lesson05.exception;

public class NotFoundUnidException extends RuntimeException {
    public NotFoundUnidException() {
        super("Unid not found, use add method!");
    }
}
