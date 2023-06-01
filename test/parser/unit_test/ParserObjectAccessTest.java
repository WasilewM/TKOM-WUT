package parser.unit_test;

import lexer.Position;
import lexer.TokenTypeEnum;
import lexer.tokens.IntegerToken;
import lexer.tokens.StringToken;
import lexer.tokens.Token;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import parser.Parser;
import parser.program_components.*;
import parser.program_components.data_values.IntValue;
import parser.program_components.function_definitions.IntFunctionDef;
import parser.program_components.statements.IfStatement;
import parser.program_components.statements.ReturnStatement;
import parser.utils.MockedExitParserErrorHandler;
import parser.utils.MockedLexer;
import parser.utils.ParserSingleTestParams;

import java.util.*;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ParserObjectAccessTest {
    private ArrayList<Token> startTokens;

    static Stream<Arguments> getObjectAccessTestData() {
        return Stream.of(
                Arguments.of(
                        new ParserSingleTestParams(
                                Arrays.asList(
                                        new Token(new Position(10, 5), TokenTypeEnum.RETURN_KEYWORD),
                                        new StringToken("func1", new Position(10, 5), TokenTypeEnum.IDENTIFIER),
                                        new Token(new Position(10, 10), TokenTypeEnum.DOT),
                                        new StringToken("func2", new Position(12, 5), TokenTypeEnum.IDENTIFIER),
                                        new Token(new Position(13, 7), TokenTypeEnum.SEMICOLON),
                                        new Token(new Position(100, 1), TokenTypeEnum.RIGHT_CURLY_BRACKET)
                                ),
                                new HashMap<>() {{
                                    put("func", new IntFunctionDef(new Position(1, 1), "func", new LinkedHashMap<>(), new CodeBlock(new Position(1, 14), List.of(new ReturnStatement(new Position(10, 5), new ObjectAccess(new Position(10, 5), new Identifier(new Position(10, 5), "func1"), new Identifier(new Position(12, 5), "func2")))))));
                                }}
                        )

                ),
                Arguments.of(
                        new ParserSingleTestParams(
                                Arrays.asList(
                                        new Token(new Position(10, 5), TokenTypeEnum.RETURN_KEYWORD),
                                        new StringToken("func1", new Position(10, 5), TokenTypeEnum.IDENTIFIER),
                                        new Token(new Position(10, 10), TokenTypeEnum.LEFT_BRACKET),
                                        new Token(new Position(10, 10), TokenTypeEnum.RIGHT_BRACKET),
                                        new Token(new Position(13, 7), TokenTypeEnum.SEMICOLON),
                                        new Token(new Position(100, 1), TokenTypeEnum.RIGHT_CURLY_BRACKET)
                                ),
                                new HashMap<>() {{
                                    put("func", new IntFunctionDef(new Position(1, 1), "func", new LinkedHashMap<>(), new CodeBlock(new Position(1, 14), List.of(new ReturnStatement(new Position(10, 5), new FunctionCall(new Position(10, 5), new Identifier(new Position(10, 5), "func1")))))));
                                }}
                        )

                ),
                Arguments.of(
                        new ParserSingleTestParams(
                                Arrays.asList(
                                        new StringToken("func1", new Position(10, 5), TokenTypeEnum.IDENTIFIER),
                                        new Token(new Position(10, 10), TokenTypeEnum.DOT),
                                        new StringToken("func2", new Position(12, 5), TokenTypeEnum.IDENTIFIER),
                                        new Token(new Position(12, 9), TokenTypeEnum.LEFT_BRACKET),
                                        new Token(new Position(12, 10), TokenTypeEnum.RIGHT_BRACKET),
                                        new Token(new Position(13, 7), TokenTypeEnum.SEMICOLON),
                                        new Token(new Position(100, 1), TokenTypeEnum.RIGHT_CURLY_BRACKET)
                                ),
                                new HashMap<>() {{
                                    put("func", new IntFunctionDef(new Position(1, 1), "func", new LinkedHashMap<>(), new CodeBlock(new Position(1, 14), List.of(new ObjectAccess(new Position(10, 5), new Identifier(new Position(10, 5), "func1"), new FunctionCall(new Position(12, 5), new Identifier(new Position(12, 5), "func2")))))));
                                }}
                        )

                ),
                Arguments.of(
                        new ParserSingleTestParams(
                                Arrays.asList(
                                        new StringToken("func1", new Position(10, 5), TokenTypeEnum.IDENTIFIER),
                                        new Token(new Position(10, 10), TokenTypeEnum.DOT),
                                        new StringToken("a", new Position(12, 5), TokenTypeEnum.IDENTIFIER),
                                        new Token(new Position(12, 6), TokenTypeEnum.DOT),
                                        new StringToken("b", new Position(12, 7), TokenTypeEnum.IDENTIFIER),
                                        new Token(new Position(12, 9), TokenTypeEnum.LEFT_BRACKET),
                                        new Token(new Position(12, 10), TokenTypeEnum.RIGHT_BRACKET),
                                        new Token(new Position(13, 7), TokenTypeEnum.SEMICOLON),
                                        new Token(new Position(100, 1), TokenTypeEnum.RIGHT_CURLY_BRACKET)
                                ),
                                new HashMap<>() {{
                                    put("func", new IntFunctionDef(new Position(1, 1), "func", new LinkedHashMap<>(), new CodeBlock(new Position(1, 14), List.of(new ObjectAccess(new Position(12, 6), new ObjectAccess(new Position(10, 5), new Identifier(new Position(10, 5), "func1"), new Identifier(new Position(12, 5), "a")), new FunctionCall(new Position(12, 7), new Identifier(new Position(12, 7), "b")))))));
                                }}
                        )

                ),
                Arguments.of(
                        new ParserSingleTestParams(
                                Arrays.asList(
                                        new StringToken("func1", new Position(10, 5), TokenTypeEnum.IDENTIFIER),
                                        new Token(new Position(10, 10), TokenTypeEnum.DOT),
                                        new StringToken("func2", new Position(12, 5), TokenTypeEnum.IDENTIFIER),
                                        new Token(new Position(12, 9), TokenTypeEnum.LEFT_BRACKET),
                                        new IntegerToken(4, new Position(12, 19)),
                                        new Token(new Position(13, 10), TokenTypeEnum.RIGHT_BRACKET),
                                        new Token(new Position(14, 7), TokenTypeEnum.SEMICOLON),
                                        new Token(new Position(100, 1), TokenTypeEnum.RIGHT_CURLY_BRACKET)
                                ),
                                new HashMap<>() {{
                                    put("func", new IntFunctionDef(new Position(1, 1), "func", new LinkedHashMap<>(), new CodeBlock(new Position(1, 14), List.of(new ObjectAccess(new Position(10, 5), new Identifier(new Position(10, 5), "func1"), new FunctionCall(new Position(12, 5), new Identifier(new Position(12, 5), "func2"), new IntValue(new Position(12, 19), 4)))))));
                                }}
                        )

                ),
                Arguments.of(
                        new ParserSingleTestParams(
                                Arrays.asList(
                                        new StringToken("func1", new Position(10, 5), TokenTypeEnum.IDENTIFIER),
                                        new Token(new Position(10, 10), TokenTypeEnum.DOT),
                                        new StringToken("func2", new Position(12, 5), TokenTypeEnum.IDENTIFIER),
                                        new Token(new Position(12, 9), TokenTypeEnum.LEFT_BRACKET),
                                        new IntegerToken(4, new Position(12, 19)),
                                        new Token(new Position(12, 20), TokenTypeEnum.COMMA),
                                        new IntegerToken(35, new Position(12, 29)),
                                        new Token(new Position(13, 10), TokenTypeEnum.RIGHT_BRACKET),
                                        new Token(new Position(14, 7), TokenTypeEnum.SEMICOLON),
                                        new Token(new Position(100, 1), TokenTypeEnum.RIGHT_CURLY_BRACKET)
                                ),
                                new HashMap<>() {{
                                    put("func", new IntFunctionDef(new Position(1, 1), "func", new LinkedHashMap<>(), new CodeBlock(new Position(1, 14), List.of(new ObjectAccess(new Position(10, 5), new Identifier(new Position(10, 5), "func1"), new FunctionCall(new Position(12, 5), new Identifier(new Position(12, 5), "func2"), new ArrayList<>(List.of(new IntValue(new Position(12, 19), 4), new IntValue(new Position(12, 20), 35)))))))));
                                }}
                        )

                ),
                Arguments.of(
                        new ParserSingleTestParams(
                                Arrays.asList(
                                        new Token(new Position(8, 5), TokenTypeEnum.IF_KEYWORD),
                                        new Token(new Position(8, 15), TokenTypeEnum.LEFT_BRACKET),
                                        new StringToken("func1", new Position(10, 5), TokenTypeEnum.IDENTIFIER),
                                        new Token(new Position(10, 10), TokenTypeEnum.DOT),
                                        new StringToken("func2", new Position(12, 5), TokenTypeEnum.IDENTIFIER),
                                        new Token(new Position(12, 9), TokenTypeEnum.LEFT_BRACKET),
                                        new Token(new Position(12, 10), TokenTypeEnum.RIGHT_BRACKET),
                                        new Token(new Position(13, 25), TokenTypeEnum.RIGHT_BRACKET),
                                        new Token(new Position(14, 1), TokenTypeEnum.LEFT_CURLY_BRACKET),
                                        new Token(new Position(15, 1), TokenTypeEnum.RIGHT_CURLY_BRACKET),
                                        new Token(new Position(100, 1), TokenTypeEnum.RIGHT_CURLY_BRACKET)
                                ),
                                new HashMap<>() {{
                                    put("func", new IntFunctionDef(new Position(1, 1), "func", new LinkedHashMap<>(), new CodeBlock(new Position(1, 14), List.of(new IfStatement(new Position(8, 5), new ObjectAccess(new Position(10, 5), new Identifier(new Position(10, 5), "func1"), new FunctionCall(new Position(12, 5), new Identifier(new Position(12, 5), "func2"))), new CodeBlock(new Position(14, 1), new ArrayList<>()))))));
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
                        new StringToken("func", new Position(1, 8), TokenTypeEnum.IDENTIFIER),
                        new Token(new Position(1, 12), TokenTypeEnum.LEFT_BRACKET),
                        new Token(new Position(1, 13), TokenTypeEnum.RIGHT_BRACKET),
                        new Token(new Position(1, 14), TokenTypeEnum.LEFT_CURLY_BRACKET)
                )
        );
    }

    @ParameterizedTest
    @MethodSource("getObjectAccessTestData")
    void parseObjectAccess(ParserSingleTestParams additionalTestParams) {
        ArrayList<Token> testTokens = new ArrayList<>(startTokens);
        testTokens.addAll(additionalTestParams.tokens());

        MockedExitParserErrorHandler errorHandler = new MockedExitParserErrorHandler();
        Parser parser = new Parser(new MockedLexer(testTokens), errorHandler);
        Program program = parser.parse();

        assertEquals(additionalTestParams.expectedFunctions(), program.functions());
        assertEquals(0, errorHandler.getErrorLog().size());
    }

}
