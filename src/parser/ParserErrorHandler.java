package parser;

import parser.exceptions.*;

import java.util.ArrayList;

public class ParserErrorHandler implements IErrorHandler {
    private final ArrayList<Exception> errorLogs;

    public ParserErrorHandler() {
        errorLogs = new ArrayList<>();
    }

    private static boolean isErrorCritical(Exception e) {
        return MissingIdentifierException.class.equals(e.getClass())
                || MissingLeftCurlyBracketException.class.equals(e.getClass())
                || MissingRightCurlyBracketException.class.equals(e.getClass())
                || MissingLeftSquareBracketException.class.equals(e.getClass())
                || MissingRightSquareBracketException.class.equals(e.getClass())
                || DuplicatedFunctionNameException.class.equals(e.getClass())
                || DuplicatedParameterNameException.class.equals(e.getClass())
                || MissingDataTypeDeclarationException.class.equals(e.getClass())
                || MissingExpressionException.class.equals(e.getClass())
                || UnclearExpressionException.class.equals(e.getClass())
                || MissingCodeBlockException.class.equals(e.getClass())
                || UnclosedParenthesesException.class.equals(e.getClass())
                || MissingAssignmentOperatorException.class.equals(e.getClass())
                || AmbiguousExpressionException.class.equals(e.getClass());
    }

    private static boolean isErrorHandleable(Exception e) {
        return MissingLeftBracketException.class.equals(e.getClass())
                || MissingRightBracketException.class.equals(e.getClass())
                || MissingSemicolonException.class.equals(e.getClass())
                || MissingCommaException.class.equals(e.getClass());
    }

    @Override
    public ArrayList<Exception> getErrorLog() {
        return errorLogs;
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

    protected void exit() {
        printLogs();
        System.exit(1);
    }

    private void printLogs() {
        System.out.println(errorLogs);
    }
}
