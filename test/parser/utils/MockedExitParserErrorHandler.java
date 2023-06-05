package parser.utils;

import parser.ParserErrorHandler;

public class MockedExitParserErrorHandler extends ParserErrorHandler {

    @Override
    protected void exit() throws RuntimeException {
        throw new RuntimeException();
    }
}
