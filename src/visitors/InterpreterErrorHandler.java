package visitors;

import parser.IErrorHandler;
import visitors.exceptions.MissingMainFunctionException;

import java.util.ArrayList;

public class InterpreterErrorHandler implements IErrorHandler {
    private final ArrayList<Exception> errorLogs;

    public InterpreterErrorHandler() {
        this.errorLogs = new ArrayList<>();
    }

    private static boolean isErrorCritical(Exception e) {
        return MissingMainFunctionException.class.equals(e.getClass());
    }

    private static boolean isErrorHandleable(Exception e) {
        return false;
    }

    @Override
    public void handle(Exception e) {
        if (isErrorCritical(e)) {
            errorLogs.add(e);
            exit();
        } else if (isErrorHandleable(e)) {
            errorLogs.add(e);
        } else {
            exit();
        }
    }

    @Override
    public ArrayList<Exception> getErrorLog() {
        return errorLogs;
    }

    protected void exit() {
        System.exit(1);
    }
}
