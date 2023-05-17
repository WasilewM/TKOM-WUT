package parser.exceptions;

public class MissingRightBracketException extends Exception {
    public MissingRightBracketException(String message) {
        super("MissingRightBracketException: " + message);
    }
}
