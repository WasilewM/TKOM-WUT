package visitors.exceptions;

import parser.IExpression;
import parser.IParameter;

public class ParameterNotFoundExceptionException extends Exception {
    public ParameterNotFoundExceptionException(IParameter param, IExpression exp) {
        super("ParameterNotFoundExceptionException: Parameter: " + param.name() + " has not been initialized - it's data type is unknown; therefore, cannot reassign it to value: " + exp.toString());
    }
}
