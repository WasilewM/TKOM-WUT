package parser.unit_test;

import lexer.Position;
import lexer.TokenTypeEnum;
import lexer.tokens.StringToken;
import lexer.tokens.Token;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import parser.Parser;
import parser.program_components.CodeBlock;
import parser.program_components.FunctionDef;
import parser.program_components.IfExpression;
import parser.program_components.Program;
import parser.utils.MockedExitErrorHandler;
import parser.utils.MockedLexer;
import parser.utils.ParserSingleTestParams;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ParserIfExpressionTest {

    private ArrayList<Token> startTokens;

    public static Stream<Arguments> getIfExpressionTestData() {
        return Stream.of(
                Arguments.of(
                        new ParserSingleTestParams(
                                Arrays.asList(
                                        new Token(new Position(2, 1), TokenTypeEnum.IF_KEYWORD),
                                        new Token(new Position(2, 4), TokenTypeEnum.LEFT_BRACKET),
                                        new Token(new Position(2, 5), TokenTypeEnum.RIGHT_BRACKET),
                                        new Token(new Position(100, 1), TokenTypeEnum.RIGHT_CURLY_BRACKET)
                                ),
                                new HashMap<>() {{
                                    put("func", new FunctionDef("func", TokenTypeEnum.INT_KEYWORD, new HashMap<>(), new CodeBlock(List.of(new IfExpression(null)))));
                                }}
                        )
                ),
                Arguments.of(
                        new ParserSingleTestParams(
                                Arrays.asList(
                                        new Token(new Position(2, 1), TokenTypeEnum.IF_KEYWORD),
                                        new Token(new Position(2, 4), TokenTypeEnum.LEFT_BRACKET),
                                        new Token(new Position(2, 5), TokenTypeEnum.RIGHT_BRACKET),
                                        new Token(new Position(3, 1), TokenTypeEnum.ELSE_IF_KEYWORD),
                                        new Token(new Position(3, 10), TokenTypeEnum.LEFT_BRACKET),
                                        new Token(new Position(3, 11), TokenTypeEnum.RIGHT_BRACKET),
                                        new Token(new Position(100, 1), TokenTypeEnum.RIGHT_CURLY_BRACKET)
                                ),
                                new HashMap<>() {{
                                    put("func", new FunctionDef("func", TokenTypeEnum.INT_KEYWORD, new HashMap<>(), new CodeBlock(List.of(new IfExpression(null)))));
                                }}
                        )
                ),
                Arguments.of(
                        new ParserSingleTestParams(
                                Arrays.asList(
                                        new Token(new Position(2, 1), TokenTypeEnum.IF_KEYWORD),
                                        new Token(new Position(2, 4), TokenTypeEnum.LEFT_BRACKET),
                                        new Token(new Position(2, 5), TokenTypeEnum.RIGHT_BRACKET),
                                        new Token(new Position(3, 1), TokenTypeEnum.ELSE_IF_KEYWORD),
                                        new Token(new Position(3, 10), TokenTypeEnum.LEFT_BRACKET),
                                        new Token(new Position(3, 11), TokenTypeEnum.RIGHT_BRACKET),
                                        new Token(new Position(4, 1), TokenTypeEnum.ELSE_IF_KEYWORD),
                                        new Token(new Position(4, 10), TokenTypeEnum.LEFT_BRACKET),
                                        new Token(new Position(4, 11), TokenTypeEnum.RIGHT_BRACKET),
                                        new Token(new Position(100, 1), TokenTypeEnum.RIGHT_CURLY_BRACKET)
                                ),
                                new HashMap<>() {{
                                    put("func", new FunctionDef("func", TokenTypeEnum.INT_KEYWORD, new HashMap<>(), new CodeBlock(List.of(new IfExpression(null)))));
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
                        new Token(new Position(1, 11), TokenTypeEnum.LEFT_CURLY_BRACKET)
                )
        );
    }

    @ParameterizedTest
    @MethodSource("getIfExpressionTestData")
    void parseIfExpression(ParserSingleTestParams additionalTestParams) {
        ArrayList<Token> testTokens = new ArrayList<>(startTokens);
        testTokens.addAll(additionalTestParams.tokens());

        MockedExitErrorHandler errorHandler = new MockedExitErrorHandler();
        Parser parser = new Parser(new MockedLexer(testTokens), errorHandler);
        Program program = parser.parse();

        assertEquals(additionalTestParams.expectedFunctions(), program.functions());
        assertEquals(0, errorHandler.getErrorLog().size());
    }
}
