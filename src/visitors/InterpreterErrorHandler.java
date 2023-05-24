package visitors;

import parser.IErrorHandler;
import visitors.exceptions.*;

import java.util.ArrayList;

public class InterpreterErrorHandler implements IErrorHandler {
    private final ArrayList<Exception> errorLogs;

    public InterpreterErrorHandler() {
        this.errorLogs = new ArrayList<>();
    }

    private static boolean isErrorCritical(Exception e) {
        return MissingMainFunctionException.class.equals(e.getClass())
                || IncompatibleDataTypeException.class.equals(e.getClass())
                || ParameterNotFoundExceptionException.class.equals(e.getClass())
                || MissingReturnValueException.class.equals(e.getClass())
                || NullExpressionException.class.equals(e.getClass());
    }

    @Override
    public void handle(Exception e) {
        if (isErrorCritical(e)) {
            errorLogs.add(e);
        }

        exit();
    }

    @Override
    public ArrayList<Exception> getErrorLog() {
        return errorLogs;
    }

    protected void exit() {
        System.exit(1);
    }
}
