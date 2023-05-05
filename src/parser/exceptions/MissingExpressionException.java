package parser.exceptions;

public class MissingExpressionException extends Exception {
    public MissingExpressionException(String message) {
        super("MissingExpressionException: " + message);
    }
}
