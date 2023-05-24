package visitors.exceptions;

import parser.IExpression;
import parser.IVisitable;

public class IncompatibleDataTypeException extends Exception {
    public IncompatibleDataTypeException(IVisitable expected, IExpression actual) {
        super("IncompatibleDataTypeException: Expected value: " + expected.getClass() + " but received: " + actual.getClass());
    }
}
