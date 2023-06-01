package visitors.exceptions;

import parser.program_components.FunctionCall;

public class ExceededMaxRecursionDepthException extends Exception {
    public ExceededMaxRecursionDepthException(FunctionCall func, int maxRecursionDepth) {
        super("ExceededMaxRecursionDepthException: Function: " + func.identifier().name() + " called at position: " + func.position() + " exceeded max recursion depth: " + maxRecursionDepth);
    }
}
