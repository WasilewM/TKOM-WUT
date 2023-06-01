package visitors.exceptions;

import parser.IFunctionDef;

import java.util.HashMap;

public class MissingMainFunctionException extends Exception {
    public MissingMainFunctionException(HashMap<String, IFunctionDef> functions) {
        super("MissingMainFunctionException: Defined functions:" + functions.keySet());
    }
}
