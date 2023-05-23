package visitors.exceptions;

import parser.IExpression;
import parser.IVisitable;

public class IncompatibleDataTypesException extends Exception {
    public IncompatibleDataTypesException(IVisitable expected, IExpression actual) {
        super("IncompatibleDataTypesException: Expected value: " + expected.getClass() + " but received: " + actual.getClass());
    }
}
