package parser;

import parser.exceptions.MissingIdentifierException;
import parser.exceptions.MissingLeftBracketException;

import java.util.ArrayList;

public class ErrorHandler {
    private final ArrayList<Exception> errorLogs;

    public ErrorHandler() {
        errorLogs = new ArrayList<>();
    }

    public ArrayList<Exception> getErrorLog() {
        return errorLogs;
    }

    public void handle(Exception e) {
        if (MissingIdentifierException.class.equals(e.getClass())) {
            errorLogs.add(e);
            exit();
        }
        if (MissingLeftBracketException.class.equals(e.getClass())) {
            errorLogs.add(e);
        }
    }

    protected void exit() {
        System.exit(1);
    }
}
