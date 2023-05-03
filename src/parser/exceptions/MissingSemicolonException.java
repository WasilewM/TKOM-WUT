package parser.exceptions;

public class MissingSemicolonException extends Exception {
    public MissingSemicolonException(String message) {
        super("MissingSemicolonException: " + message);
    }
}
