package part1.lesson05.exception;

public class DublicateUnidException extends RuntimeException {
    public DublicateUnidException() {
        super("Unid is busy, use change method!");
    }
}
