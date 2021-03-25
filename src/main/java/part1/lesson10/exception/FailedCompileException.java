package part1.lesson10.exception;

public class FailedCompileException extends RuntimeException {
    public FailedCompileException(String message) {
        super(message);
    }
}
