package parser.exceptions;

public class MissingRightCurlyBracketException extends Exception {
    public MissingRightCurlyBracketException(String message) {
        super("MissingRightCurlyBracketException: " + message);
    }
}
