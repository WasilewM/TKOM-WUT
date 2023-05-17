package parser.exceptions;

public class MissingLeftBracketException extends Exception {
    public MissingLeftBracketException(String message) {
        super("MissingLeftBracketException: " + message);
    }
}
