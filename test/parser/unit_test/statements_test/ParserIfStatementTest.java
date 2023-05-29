package parser.unit_test.statements_test;

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
import parser.program_components.Identifier;
import parser.program_components.Program;
import parser.program_components.function_definitions.IntFunctionDef;
import parser.program_components.statements.ElseIfStatement;
import parser.program_components.statements.ElseStatement;
import parser.program_components.statements.IfStatement;
import parser.utils.MockedExitParserErrorHandler;
import parser.utils.MockedLexer;
import parser.utils.ParserSingleTestParams;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ParserIfStatementTest {

    private ArrayList<Token> startTokens;

    public static Stream<Arguments> getIfStatementTestData() {
        return Stream.of(
                Arguments.of(
                        new ParserSingleTestParams(
                                Arrays.asList(
                                        new Token(new Position(2, 1), TokenTypeEnum.IF_KEYWORD),
                                        new Token(new Position(2, 4), TokenTypeEnum.LEFT_BRACKET),
                                        new StringToken("a", new Position(2, 5), TokenTypeEnum.IDENTIFIER),
                                        new Token(new Position(3, 1), TokenTypeEnum.RIGHT_BRACKET),
                                        new Token(new Position(51, 1), TokenTypeEnum.LEFT_CURLY_BRACKET),
                                        new Token(new Position(52, 1), TokenTypeEnum.RIGHT_CURLY_BRACKET),
                                        new Token(new Position(100, 1), TokenTypeEnum.RIGHT_CURLY_BRACKET)
                                ),
                                new HashMap<>() {{
                                    put("func", new IntFunctionDef(new Position(1, 1), "func", new HashMap<>(), new CodeBlock(new Position(1, 11), List.of(new IfStatement(new Position(2, 1), new Identifier(new Position(2, 5), "a"), new CodeBlock(new Position(51, 1), new ArrayList<>()))))));
                                }}
                        )
                ),
                Arguments.of(
                        new ParserSingleTestParams(
                                Arrays.asList(
                                        new Token(new Position(2, 1), TokenTypeEnum.IF_KEYWORD),
                                        new Token(new Position(2, 4), TokenTypeEnum.LEFT_BRACKET),
                                        new StringToken("cube", new Position(2, 5), TokenTypeEnum.IDENTIFIER),
                                        new Token(new Position(2, 11), TokenTypeEnum.RIGHT_BRACKET),
                                        new Token(new Position(2, 21), TokenTypeEnum.LEFT_CURLY_BRACKET),
                                        new Token(new Position(2, 31), TokenTypeEnum.RIGHT_CURLY_BRACKET),
                                        new Token(new Position(3, 1), TokenTypeEnum.ELSE_IF_KEYWORD),
                                        new Token(new Position(3, 10), TokenTypeEnum.LEFT_BRACKET),
                                        new StringToken("cube2", new Position(4, 5), TokenTypeEnum.IDENTIFIER),
                                        new Token(new Position(5, 11), TokenTypeEnum.RIGHT_BRACKET),
                                        new Token(new Position(14, 1), TokenTypeEnum.LEFT_CURLY_BRACKET),
                                        new Token(new Position(15, 1), TokenTypeEnum.RIGHT_CURLY_BRACKET),
                                        new Token(new Position(100, 1), TokenTypeEnum.RIGHT_CURLY_BRACKET)
                                ),
                                new HashMap<>() {{
                                    put("func", new IntFunctionDef(new Position(1, 1), "func", new HashMap<>(), new CodeBlock(new Position(1, 11), List.of(new IfStatement(new Position(2, 1), new Identifier(new Position(2, 5), "cube"), new CodeBlock(new Position(2, 21), new ArrayList<>()), List.of(new ElseIfStatement(new Position(3, 1), new Identifier(new Position(4, 5), "cube2"), new CodeBlock(new Position(14, 1), new ArrayList<>()))))))));
                                }}
                        )
                ),
                Arguments.of(
                        new ParserSingleTestParams(
                                Arrays.asList(
                                        new Token(new Position(2, 1), TokenTypeEnum.IF_KEYWORD),
                                        new Token(new Position(2, 4), TokenTypeEnum.LEFT_BRACKET),
                                        new StringToken("isHandleable", new Position(2, 5), TokenTypeEnum.IDENTIFIER),
                                        new Token(new Position(2, 11), TokenTypeEnum.RIGHT_BRACKET),
                                        new Token(new Position(2, 21), TokenTypeEnum.LEFT_CURLY_BRACKET),
                                        new Token(new Position(2, 31), TokenTypeEnum.RIGHT_CURLY_BRACKET),
                                        new Token(new Position(3, 1), TokenTypeEnum.ELSE_IF_KEYWORD),
                                        new Token(new Position(3, 10), TokenTypeEnum.LEFT_BRACKET),
                                        new StringToken("x", new Position(3, 15), TokenTypeEnum.IDENTIFIER),
                                        new Token(new Position(3, 21), TokenTypeEnum.RIGHT_BRACKET),
                                        new Token(new Position(3, 100), TokenTypeEnum.LEFT_CURLY_BRACKET),
                                        new Token(new Position(3, 101), TokenTypeEnum.RIGHT_CURLY_BRACKET),
                                        new Token(new Position(4, 1), TokenTypeEnum.ELSE_IF_KEYWORD),
                                        new Token(new Position(4, 10), TokenTypeEnum.LEFT_BRACKET),
                                        new StringToken("y", new Position(4, 25), TokenTypeEnum.IDENTIFIER),
                                        new Token(new Position(4, 31), TokenTypeEnum.RIGHT_BRACKET),
                                        new Token(new Position(14, 1), TokenTypeEnum.LEFT_CURLY_BRACKET),
                                        new Token(new Position(15, 1), TokenTypeEnum.RIGHT_CURLY_BRACKET),
                                        new Token(new Position(100, 1), TokenTypeEnum.RIGHT_CURLY_BRACKET)
                                ),
                                new HashMap<>() {{
                                    put("func", new IntFunctionDef(new Position(1, 1), "func", new HashMap<>(), new CodeBlock(new Position(1, 11), List.of(new IfStatement(new Position(2, 1), new Identifier(new Position(2, 5), "isHandleable"), new CodeBlock(new Position(2, 21), new ArrayList<>()), Arrays.asList(new ElseIfStatement(new Position(3, 1), new Identifier(new Position(3, 15), "x"), new CodeBlock(new Position(3, 100), new ArrayList<>())), new ElseIfStatement(new Position(4, 1), new Identifier(new Position(4, 25), "y"), new CodeBlock(new Position(14, 1), new ArrayList<>()))))))));
                                }}
                        )
                ),
                Arguments.of(
                        new ParserSingleTestParams(
                                Arrays.asList(
                                        new Token(new Position(2, 1), TokenTypeEnum.IF_KEYWORD),
                                        new Token(new Position(2, 4), TokenTypeEnum.LEFT_BRACKET),
                                        new StringToken("cube", new Position(2, 5), TokenTypeEnum.IDENTIFIER),
                                        new Token(new Position(2, 11), TokenTypeEnum.RIGHT_BRACKET),
                                        new Token(new Position(2, 21), TokenTypeEnum.LEFT_CURLY_BRACKET),
                                        new Token(new Position(2, 31), TokenTypeEnum.RIGHT_CURLY_BRACKET),
                                        new Token(new Position(3, 1), TokenTypeEnum.ELSE_KEYWORD),
                                        new Token(new Position(12, 21), TokenTypeEnum.LEFT_CURLY_BRACKET),
                                        new Token(new Position(12, 31), TokenTypeEnum.RIGHT_CURLY_BRACKET),
                                        new Token(new Position(100, 1), TokenTypeEnum.RIGHT_CURLY_BRACKET)
                                ),
                                new HashMap<>() {{
                                    put("func", new IntFunctionDef(new Position(1, 1), "func", new HashMap<>(), new CodeBlock(new Position(1, 11), List.of(new IfStatement(new Position(2, 1), new Identifier(new Position(2, 5), "cube"), new CodeBlock(new Position(2, 21), new ArrayList<>()), new ElseStatement(new Position(3, 1), new CodeBlock(new Position(12, 21), new ArrayList<>())))))));
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
    @MethodSource("getIfStatementTestData")
    void parseIfStatement(ParserSingleTestParams additionalTestParams) {
        ArrayList<Token> testTokens = new ArrayList<>(startTokens);
        testTokens.addAll(additionalTestParams.tokens());

        MockedExitParserErrorHandler errorHandler = new MockedExitParserErrorHandler();
        Parser parser = new Parser(new MockedLexer(testTokens), errorHandler);
        Program program = parser.parse();

        assertEquals(additionalTestParams.expectedFunctions(), program.functions());
        assertEquals(0, errorHandler.getErrorLog().size());
    }
}
