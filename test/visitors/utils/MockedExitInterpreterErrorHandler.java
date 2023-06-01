package visitors.utils;

import visitors.InterpreterErrorHandler;

public class MockedExitInterpreterErrorHandler extends InterpreterErrorHandler {

    @Override
    protected void exit() throws RuntimeException {
        throw new RuntimeException();
    }
}
