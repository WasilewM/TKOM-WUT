package visitors.exceptions;

import parser.IExpression;
import parser.IParameter;

public class ParameterNotFoundException extends Exception {
    public ParameterNotFoundException(IParameter param, IExpression exp) {
        super("ParameterNotFoundExceptionException: Parameter: " + param.name() + " has not been initialized - it's data type is unknown; therefore, cannot reassign it to value: " + exp.toString());
    }
}
