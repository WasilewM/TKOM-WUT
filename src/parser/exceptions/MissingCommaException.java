package parser.exceptions;

public class MissingCommaException extends Exception {
    public MissingCommaException(String message) {
        super("MissingCommaException: " + message);
    }
}
