import lexer.Position;
import lexer.TokenTypeEnum;
import lexer.tokens.StringToken;
import lexer.tokens.Token;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import parser.Parser;
import parser.program_components.*;
import utils.MockedExitErrorHandler;
import utils.MockedLexer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ParserAlternativeExpressionTest {

    private ArrayList<Token> startTokens;

    @BeforeEach
    public void initTestParams() {
        startTokens = new ArrayList<>(
                Arrays.asList(
                        new Token(new Position(1, 1), TokenTypeEnum.INT_KEYWORD),
                        new StringToken("func", new Position(1, 5), TokenTypeEnum.IDENTIFIER),
                        new Token(new Position(1, 9), TokenTypeEnum.LEFT_BRACKET),
                        new Token(new Position(1, 10), TokenTypeEnum.RIGHT_BRACKET),
                        new Token(new Position(1, 11), TokenTypeEnum.LEFT_CURLY_BRACKET),
                        new Token(new Position(1, 12), TokenTypeEnum.IF_KEYWORD),
                        new Token(new Position(1, 15), TokenTypeEnum.LEFT_BRACKET)
                )
        );
    }

    @Test
    void parseAlternativeExpression_onlyLeftExp() {
        ArrayList<Token> testTokens = new ArrayList<>(startTokens);

        testTokens.add(new StringToken("ident", new Position(2, 5), TokenTypeEnum.IDENTIFIER));
        testTokens.add(new Token(new Position(50, 1), TokenTypeEnum.RIGHT_BRACKET));
        testTokens.add(new Token(new Position(100, 1), TokenTypeEnum.RIGHT_CURLY_BRACKET));
        HashMap<String, FunctionDef> expectedFunctions = new HashMap<>() {{
            put("func", new FunctionDef("func", TokenTypeEnum.INT_KEYWORD, new HashMap<>(), new CodeBlock(List.of(new IfExpression(new Identifier("ident"))))));
        }};

        MockedExitErrorHandler errorHandler = new MockedExitErrorHandler();
        Parser parser = new Parser(new MockedLexer(testTokens), errorHandler);
        Program program = parser.parse();

        assertEquals(expectedFunctions, program.functions());
        assertEquals(0, errorHandler.getErrorLog().size());
    }

    @Test
    void parseAlternativeExpression_leftAndRightExp() {
        ArrayList<Token> testTokens = new ArrayList<>(startTokens);

        testTokens.add(new StringToken("ident", new Position(2, 5), TokenTypeEnum.IDENTIFIER));
        testTokens.add(new Token(new Position(2, 12), TokenTypeEnum.OR_OPERATOR));
        testTokens.add(new StringToken("ident", new Position(2, 15), TokenTypeEnum.IDENTIFIER));
        testTokens.add(new Token(new Position(50, 1), TokenTypeEnum.RIGHT_BRACKET));
        testTokens.add(new Token(new Position(100, 1), TokenTypeEnum.RIGHT_CURLY_BRACKET));
        HashMap<String, FunctionDef> expectedFunctions = new HashMap<>() {{
            put("func", new FunctionDef("func", TokenTypeEnum.INT_KEYWORD, new HashMap<>(), new CodeBlock(List.of(new IfExpression(new AlternativeExpression(new Identifier("ident"), new Identifier("ident")))))));
        }};

        MockedExitErrorHandler errorHandler = new MockedExitErrorHandler();
        Parser parser = new Parser(new MockedLexer(testTokens), errorHandler);
        Program program = parser.parse();

        assertEquals(expectedFunctions, program.functions());
        assertEquals(0, errorHandler.getErrorLog().size());
    }

    @Test
    void parseAlternativeExpression_whenMultipleRightExp() {
        ArrayList<Token> testTokens = new ArrayList<>(startTokens);

        testTokens.add(new StringToken("ident", new Position(2, 5), TokenTypeEnum.IDENTIFIER));
        testTokens.add(new Token(new Position(2, 12), TokenTypeEnum.OR_OPERATOR));
        testTokens.add(new StringToken("ident", new Position(2, 15), TokenTypeEnum.IDENTIFIER));
        testTokens.add(new Token(new Position(2, 21), TokenTypeEnum.OR_OPERATOR));
        testTokens.add(new StringToken("ident", new Position(2, 24), TokenTypeEnum.IDENTIFIER));
        testTokens.add(new Token(new Position(50, 1), TokenTypeEnum.RIGHT_BRACKET));
        testTokens.add(new Token(new Position(100, 1), TokenTypeEnum.RIGHT_CURLY_BRACKET));
        HashMap<String, FunctionDef> expectedFunctions = new HashMap<>() {{
            put("func", new FunctionDef("func", TokenTypeEnum.INT_KEYWORD, new HashMap<>(), new CodeBlock(List.of(new IfExpression(new AlternativeExpression(new AlternativeExpression(new Identifier("ident"), new Identifier("ident")), new Identifier("ident")))))));
        }};

        MockedExitErrorHandler errorHandler = new MockedExitErrorHandler();
        Parser parser = new Parser(new MockedLexer(testTokens), errorHandler);
        Program program = parser.parse();

        assertEquals(expectedFunctions, program.functions());
        assertEquals(0, errorHandler.getErrorLog().size());
    }
}
