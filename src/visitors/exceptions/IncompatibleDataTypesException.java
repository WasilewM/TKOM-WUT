package visitors.exceptions;

import parser.IExpression;
import parser.IParameter;

public class IncompatibleDataTypesException extends Exception {
    public IncompatibleDataTypesException(IParameter expectedParam, IExpression actualExp) {
        super("MissingMainFunctionException: Expected value for parameter: " + expectedParam.getClass() + " but received: " + actualExp.getClass());
    }
}
