package visitors.exceptions;

import parser.program_components.FunctionCall;

public class UndefinedFunctionCallException extends Exception {
    public UndefinedFunctionCallException(FunctionCall func) {
        super("UndefinedFunctionCallException: Function: " + func.identifier().name() + " called at position: " + func.position() + " has not been defined.");
    }
}
