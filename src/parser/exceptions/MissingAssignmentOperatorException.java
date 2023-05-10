package parser.exceptions;

public class MissingAssignmentOperatorException extends Exception {
    public MissingAssignmentOperatorException(String message) {
        super("MissingAssignmentOperatorException: " + message);
    }
}
