package parser.unit_test.expressions_test;

import lexer.Position;
import lexer.TokenTypeEnum;
import lexer.tokens.StringToken;
import lexer.tokens.Token;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import parser.Parser;
import parser.program_components.*;
import parser.program_components.expressions.MultiplicationExpression;
import parser.utils.MockedExitErrorHandler;
import parser.utils.MockedLexer;
import parser.utils.ParserSingleTestParams;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ParserMultiplicativeExpressionTest {

    private ArrayList<Token> startTokens;

    static Stream<Arguments> getMultiplicativeExpTestData() {
        return Stream.of(
                Arguments.of(
                        new ParserSingleTestParams(
                                Arrays.asList(
                                        new StringToken("ident", new Position(2, 5), TokenTypeEnum.IDENTIFIER),
                                        new Token(new Position(2, 12), TokenTypeEnum.MULTIPLICATION_OPERATOR),
                                        new StringToken("ident", new Position(2, 15), TokenTypeEnum.IDENTIFIER),
                                        new Token(new Position(50, 1), TokenTypeEnum.RIGHT_BRACKET),
                                        new Token(new Position(100, 1), TokenTypeEnum.RIGHT_CURLY_BRACKET)
                                ),
                                new HashMap<>() {{
                                    put("func", new FunctionDef("func", TokenTypeEnum.INT_KEYWORD, new HashMap<>(), new CodeBlock(List.of(new IfExpression(new MultiplicationExpression(new Identifier("ident"), new Identifier("ident")))))));
                                }}
                        )
                ),
                Arguments.of(
                        new ParserSingleTestParams(
                                Arrays.asList(
                                        new StringToken("ident", new Position(2, 5), TokenTypeEnum.IDENTIFIER),
                                        new Token(new Position(2, 12), TokenTypeEnum.MULTIPLICATION_OPERATOR),
                                        new StringToken("ident", new Position(2, 15), TokenTypeEnum.IDENTIFIER),
                                        new Token(new Position(3, 12), TokenTypeEnum.MULTIPLICATION_OPERATOR),
                                        new StringToken("ident", new Position(3, 15), TokenTypeEnum.IDENTIFIER),
                                        new Token(new Position(50, 1), TokenTypeEnum.RIGHT_BRACKET),
                                        new Token(new Position(100, 1), TokenTypeEnum.RIGHT_CURLY_BRACKET)
                                ),
                                new HashMap<>() {{
                                    put("func", new FunctionDef("func", TokenTypeEnum.INT_KEYWORD, new HashMap<>(), new CodeBlock(List.of(new IfExpression(new MultiplicationExpression(new MultiplicationExpression(new Identifier("ident"), new Identifier("ident")), new Identifier("ident")))))));
                                }}
                        )
                )
        );
    }

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

    @ParameterizedTest
    @MethodSource("getMultiplicativeExpTestData")
    void parseMultiplicativeExpression(ParserSingleTestParams additionalTestParams) {
        ArrayList<Token> testTokens = new ArrayList<>(startTokens);
        testTokens.addAll(additionalTestParams.tokens());

        MockedExitErrorHandler errorHandler = new MockedExitErrorHandler();
        Parser parser = new Parser(new MockedLexer(testTokens), errorHandler);
        Program program = parser.parse();

        assertEquals(additionalTestParams.expectedFunctions(), program.functions());
        assertEquals(0, errorHandler.getErrorLog().size());
    }
}
