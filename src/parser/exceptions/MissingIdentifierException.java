package parser.exceptions;

public class MissingIdentifierException extends Exception {
    public MissingIdentifierException(String message) {
        super("MissingIdentifierException: " + message);
    }
}
