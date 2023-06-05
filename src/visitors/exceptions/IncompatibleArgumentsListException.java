package visitors.exceptions;

import parser.IVisitable;
import parser.program_components.FunctionCall;

import java.util.ArrayList;

public class IncompatibleArgumentsListException extends Exception {
    public IncompatibleArgumentsListException(FunctionCall f, ArrayList<IVisitable> expectedArgs, ArrayList<IVisitable> receivedArgs) {
        super("IncompatibleArgumentsListException: Function call at position: " + f.position() + " expected following list of arguments: " + expectedArgs + " but received: " + receivedArgs);
    }
}
