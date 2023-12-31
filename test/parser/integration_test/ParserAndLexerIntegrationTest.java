package parser.integration_test;

import lexer.Lexer;
import org.junit.jupiter.api.Test;
import parser.Parser;
import parser.utils.MockedExitParserErrorHandler;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ParserAndLexerIntegrationTest {

    @Test
    void parserInit() {
        InputStream inputStream = new ByteArrayInputStream("#hello there - init parser".getBytes());
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        Parser parser = new Parser(new Lexer(bufferedReader), new MockedExitParserErrorHandler());

        assertNotNull(parser);
    }
}
