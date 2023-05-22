package visitors.utils;

import parser.IErrorHandler;
import visitors.Interpreter;

public class MockedContextDeletionInterpreter extends Interpreter {
    public MockedContextDeletionInterpreter(IErrorHandler errorHandler) {
        super(errorHandler);
    }

    @Override
    protected void deleteLastContext() {
    }
}
