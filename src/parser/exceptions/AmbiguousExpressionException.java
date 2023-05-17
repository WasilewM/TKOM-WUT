package parser.exceptions;

public class AmbiguousExpressionException extends Exception {
    public AmbiguousExpressionException(String message) {
        super("AmbiguousExpressionException: " + message);
    }
}
