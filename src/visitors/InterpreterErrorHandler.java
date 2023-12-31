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
                || ParameterNotFoundException.class.equals(e.getClass())
                || MissingReturnValueException.class.equals(e.getClass())
                || NullExpressionException.class.equals(e.getClass())
                || IdentifierNotFoundException.class.equals(e.getClass())
                || OperationDataTypeException.class.equals(e.getClass())
                || UndefinedFunctionCallException.class.equals(e.getClass())
                || UndefinedMethodCallException.class.equals(e.getClass())
                || IncompatibleMethodArgumentException.class.equals(e.getClass())
                || InvalidNumberOfArgumentsException.class.equals(e.getClass())
                || IncompatibleArgumentsListException.class.equals(e.getClass())
                || ZeroDivisionException.class.equals(e.getClass())
                || ExceededMaxRecursionDepthException.class.equals(e.getClass())
                || ExceededFunctionCallStackSizeException.class.equals(e.getClass());
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
        printLogs();
        System.exit(1);
    }

    private void printLogs() {
        System.out.println(errorLogs);
    }
}
