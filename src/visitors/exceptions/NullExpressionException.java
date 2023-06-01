package visitors.exceptions;

import parser.IVisitable;

public class NullExpressionException extends Exception {
    public NullExpressionException(IVisitable actual) {
        super("NullExpressionException: Null expression found in: " + actual.getClass() + " at position: " + actual.position().toString());
    }
}
