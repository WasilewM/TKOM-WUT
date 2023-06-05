package visitors.exceptions;

import parser.IVisitable;

public class InvalidNumberOfArgumentsException extends Exception {
    public InvalidNumberOfArgumentsException(IVisitable obj) {
        super("InvalidNumberOfArgumentsException: Invalid number of arguments in function call or object access at position: " + obj.position().toString());
    }
}
