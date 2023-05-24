package visitors.exceptions;

import parser.IFunctionDef;

public class MissingReturnValueException extends Exception {
    public MissingReturnValueException(IFunctionDef function) {
        super("MissingReturnValueException: Function: " + function.name() + " of type: " + function.getClass() + " at position: " + function.position().toString() + " does not return any value");
    }
}
