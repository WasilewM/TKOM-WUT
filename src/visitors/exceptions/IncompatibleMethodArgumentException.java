package visitors.exceptions;

import parser.IVisitable;

public class IncompatibleMethodArgumentException extends Exception {
    public IncompatibleMethodArgumentException(IVisitable instance, IVisitable arg) {
        super("InvalidMethodArgumentException: Instance of class: " + instance.getClass() + " received invalid argument at position: " + arg.position().toString());
    }
}
