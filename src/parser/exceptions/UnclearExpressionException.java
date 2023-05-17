package parser.exceptions;

public class UnclearExpressionException extends Exception {
    public UnclearExpressionException(String message) {
        super("UnclearExpressionException: " + message);
    }
}
