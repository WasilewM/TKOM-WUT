package parser.exceptions;

public class UnclosedParenthesesException extends Exception {
    public UnclosedParenthesesException(String message) {
        super("UnclosedParenthesesException: " + message);
    }
}
