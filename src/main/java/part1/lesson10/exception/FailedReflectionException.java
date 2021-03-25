package part1.lesson10.exception;

public class FailedReflectionException extends RuntimeException {
    public FailedReflectionException(ReflectiveOperationException ex) {
        super(ex);
    }
}
