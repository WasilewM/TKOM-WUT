package parser.exceptions;

public class MissingRightSquareBracketException extends Exception {
    public MissingRightSquareBracketException(String message) {
        super("MissingRightSquareBracketException: " + message);
    }
}
