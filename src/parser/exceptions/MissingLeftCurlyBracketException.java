package parser.exceptions;

public class MissingLeftCurlyBracketException extends Exception {
    public MissingLeftCurlyBracketException(String message) {
        super("MissingLeftCurlyBracketException: " + message);
    }
}
