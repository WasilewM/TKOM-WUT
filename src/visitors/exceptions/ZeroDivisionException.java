package visitors.exceptions;

import lexer.Position;

public class ZeroDivisionException extends Exception {
    public ZeroDivisionException(Position position) {
        super("ZeroDivisionException: at position" + position);
    }
}
