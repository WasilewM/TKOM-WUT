package parser.exceptions;

public class MissingCodeBlockException extends Exception {
    public MissingCodeBlockException(String message) {
        super("MissingRightCurlyBracketException: " + message);
    }
}
