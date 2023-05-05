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
import parser.program_components.expressions.*;
import utils.MockedExitErrorHandler;
import utils.MockedLexer;
import utils.ParserSingleTestParams;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ParserComparisonExpressionTest {

    private ArrayList<Token> startTokens;

    static Stream<Arguments> getComparisonExpTestData() {
        return Stream.of(
                Arguments.of(
                        new ParserSingleTestParams(
                                Arrays.asList(
                                        new StringToken("x1_someName1", new Position(2, 5), TokenTypeEnum.IDENTIFIER),
                                        new Token(new Position(3, 1), TokenTypeEnum.LESS_THAN_OPERATOR),
                                        new StringToken("y2_someName2", new Position(4, 1), TokenTypeEnum.IDENTIFIER),
                                        new Token(new Position(50, 1), TokenTypeEnum.RIGHT_BRACKET),
                                        new Token(new Position(100, 1), TokenTypeEnum.RIGHT_CURLY_BRACKET)
                                ),
                                new HashMap<>() {{
                                    put("func", new FunctionDef("func", TokenTypeEnum.INT_KEYWORD, new HashMap<>(), new CodeBlock(List.of(new IfExpression(new LessThanExpression(new Identifier("x1_someName1"), new Identifier("y2_someName2")))))));
                                }}
                        )
                ),
                Arguments.of(
                        new ParserSingleTestParams(
                                Arrays.asList(
                                        new StringToken("x1_someName1", new Position(2, 5), TokenTypeEnum.IDENTIFIER),
                                        new Token(new Position(3, 1), TokenTypeEnum.LESS_OR_EQUAL_OPERATOR),
                                        new StringToken("y2_someName2", new Position(4, 1), TokenTypeEnum.IDENTIFIER),
                                        new Token(new Position(50, 1), TokenTypeEnum.RIGHT_BRACKET),
                                        new Token(new Position(100, 1), TokenTypeEnum.RIGHT_CURLY_BRACKET)
                                ),
                                new HashMap<>() {{
                                    put("func", new FunctionDef("func", TokenTypeEnum.INT_KEYWORD, new HashMap<>(), new CodeBlock(List.of(new IfExpression(new LessOrEqualExpression(new Identifier("x1_someName1"), new Identifier("y2_someName2")))))));
                                }}
                        )
                ),
                Arguments.of(
                        new ParserSingleTestParams(
                                Arrays.asList(
                                        new StringToken("x1_someName1", new Position(2, 5), TokenTypeEnum.IDENTIFIER),
                                        new Token(new Position(3, 1), TokenTypeEnum.GREATER_THAN_OPERATOR),
                                        new StringToken("y2_someName2", new Position(4, 1), TokenTypeEnum.IDENTIFIER),
                                        new Token(new Position(50, 1), TokenTypeEnum.RIGHT_BRACKET),
                                        new Token(new Position(100, 1), TokenTypeEnum.RIGHT_CURLY_BRACKET)
                                ),
                                new HashMap<>() {{
                                    put("func", new FunctionDef("func", TokenTypeEnum.INT_KEYWORD, new HashMap<>(), new CodeBlock(List.of(new IfExpression(new GreaterThanExpression(new Identifier("x1_someName1"), new Identifier("y2_someName2")))))));
                                }}
                        )
                ),
                Arguments.of(
                        new ParserSingleTestParams(
                                Arrays.asList(
                                        new StringToken("x1_someName1", new Position(2, 5), TokenTypeEnum.IDENTIFIER),
                                        new Token(new Position(3, 1), TokenTypeEnum.GREATER_OR_EQUAL_OPERATOR),
                                        new StringToken("y2_someName2", new Position(4, 1), TokenTypeEnum.IDENTIFIER),
                                        new Token(new Position(50, 1), TokenTypeEnum.RIGHT_BRACKET),
                                        new Token(new Position(100, 1), TokenTypeEnum.RIGHT_CURLY_BRACKET)
                                ),
                                new HashMap<>() {{
                                    put("func", new FunctionDef("func", TokenTypeEnum.INT_KEYWORD, new HashMap<>(), new CodeBlock(List.of(new IfExpression(new GreaterOrEqualExpression(new Identifier("x1_someName1"), new Identifier("y2_someName2")))))));
                                }}
                        )
                ),
                Arguments.of(
                        new ParserSingleTestParams(
                                Arrays.asList(
                                        new StringToken("x1_someName1", new Position(2, 5), TokenTypeEnum.IDENTIFIER),
                                        new Token(new Position(3, 1), TokenTypeEnum.EQUAL_OPERATOR),
                                        new StringToken("y2_someName2", new Position(4, 1), TokenTypeEnum.IDENTIFIER),
                                        new Token(new Position(50, 1), TokenTypeEnum.RIGHT_BRACKET),
                                        new Token(new Position(100, 1), TokenTypeEnum.RIGHT_CURLY_BRACKET)
                                ),
                                new HashMap<>() {{
                                    put("func", new FunctionDef("func", TokenTypeEnum.INT_KEYWORD, new HashMap<>(), new CodeBlock(List.of(new IfExpression(new EqualExpression(new Identifier("x1_someName1"), new Identifier("y2_someName2")))))));
                                }}
                        )
                ),
                Arguments.of(
                        new ParserSingleTestParams(
                                Arrays.asList(
                                        new StringToken("x1_someName1", new Position(2, 5), TokenTypeEnum.IDENTIFIER),
                                        new Token(new Position(3, 1), TokenTypeEnum.NOT_EQUAL_OPERATOR),
                                        new StringToken("y2_someName2", new Position(4, 1), TokenTypeEnum.IDENTIFIER),
                                        new Token(new Position(50, 1), TokenTypeEnum.RIGHT_BRACKET),
                                        new Token(new Position(100, 1), TokenTypeEnum.RIGHT_CURLY_BRACKET)
                                ),
                                new HashMap<>() {{
                                    put("func", new FunctionDef("func", TokenTypeEnum.INT_KEYWORD, new HashMap<>(), new CodeBlock(List.of(new IfExpression(new NotEqualExpression(new Identifier("x1_someName1"), new Identifier("y2_someName2")))))));
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
    @MethodSource("getComparisonExpTestData")
    void parseComparisonExpression(ParserSingleTestParams additionalTestParams) {
        ArrayList<Token> testTokens = new ArrayList<>(startTokens);
        testTokens.addAll(additionalTestParams.tokens());

        MockedExitErrorHandler errorHandler = new MockedExitErrorHandler();
        Parser parser = new Parser(new MockedLexer(testTokens), errorHandler);
        Program program = parser.parse();

        assertEquals(additionalTestParams.expectedFunctions(), program.functions());
        assertEquals(0, errorHandler.getErrorLog().size());
    }
}
