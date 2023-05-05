package parser.utils;

import parser.ErrorHandler;

public class MockedExitErrorHandler extends ErrorHandler {

    @Override
    protected void exit() throws RuntimeException {
        throw new RuntimeException();
    }
}
