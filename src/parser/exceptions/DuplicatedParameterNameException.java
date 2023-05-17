package parser.exceptions;

public class DuplicatedParameterNameException extends Exception {
    public DuplicatedParameterNameException(String message) {
        super("DuplicatedParameterNameException: " + message);
    }
}
