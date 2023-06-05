package visitors.exceptions;

import lexer.Position;
import parser.IVisitable;

public class OperationDataTypeException extends Exception {
    public OperationDataTypeException(Position position, IVisitable left, IVisitable right) {
        super("OperationDataTypeException: Cannot perform operation at: " + position.toString() + " on parameters of types: " + left.getClass() + " and " + right.getClass());
    }

    public OperationDataTypeException(IVisitable left, IVisitable right) {
        super("OperationDataTypeException: Cannot perform operation at: " + left.position().toString() + " on parameters of types: " + left.getClass() + " and " + right.getClass());
    }
}
