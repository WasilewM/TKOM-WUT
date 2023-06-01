package visitors.exceptions;

import parser.IParameter;
import parser.IVisitable;

public class ParameterNotFoundException extends Exception {
    public ParameterNotFoundException(IParameter param, IVisitable exp) {
        super("ParameterNotFoundExceptionException: Parameter: " + param.name() + " has not been initialized - it's data type is unknown; therefore, cannot reassign it to value: " + exp.toString());
    }
}
