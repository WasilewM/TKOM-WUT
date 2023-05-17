package parser.exceptions;

public class MissingLeftSquareBracketException extends Exception {
    public MissingLeftSquareBracketException(String message) {
        super("MissingLeftSquareBracketException: " + message);
    }
}
