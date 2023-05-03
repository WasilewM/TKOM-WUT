package parser.exceptions;

public class DuplicatedFunctionNameException extends Exception {
    public DuplicatedFunctionNameException(String message) {
        super("DuplicatedFunctionNameException: " + message);
    }
}
