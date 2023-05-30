package visitors.exceptions;

import parser.program_components.ObjectAccess;

public class UndefinedMethodCallException extends Exception {
    public UndefinedMethodCallException(ObjectAccess o) {
        super("UndefinedMethodCallException: ObjectAccess: leftExp: " + o.leftExp() + " rightExp: " + o.rightExp() + " called at position: " + o.position() + " has not been defined.");
    }
}
