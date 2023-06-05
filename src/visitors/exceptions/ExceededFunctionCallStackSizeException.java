package visitors.exceptions;

import parser.program_components.FunctionCall;

public class ExceededFunctionCallStackSizeException extends Exception {
    public ExceededFunctionCallStackSizeException(FunctionCall func, int maxSize) {
        super("ExceededFunctionCallStackException: Function: " + func.identifier().name() + " called at position: " + func.position() + " exceeded max function call stack size: " + maxSize);
    }
}
