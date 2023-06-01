package visitors.exceptions;

import parser.IVisitable;

public class IncompatibleDataTypeException extends Exception {
    public IncompatibleDataTypeException(IVisitable expected, IVisitable actual) {
        super("IncompatibleDataTypeException: Expected value: " + expected.getClass() + " at position: " + expected.position().toString() + " but received: " + actual.getClass());
    }
}
