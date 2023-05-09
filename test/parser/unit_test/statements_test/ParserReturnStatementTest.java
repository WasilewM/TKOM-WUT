package parser.unit_test.statements_test;

import lexer.Position;
import lexer.TokenTypeEnum;
import lexer.tokens.DoubleToken;
import lexer.tokens.IntegerToken;
import lexer.tokens.StringToken;
import lexer.tokens.Token;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import parser.Parser;
import parser.program_components.CodeBlock;
import parser.program_components.FunctionDef;
import parser.program_components.Program;
import parser.program_components.data_values.BoolValue;
import parser.program_components.data_values.DoubleValue;
import parser.program_components.data_values.IntValue;
import parser.program_components.data_values.StringValue;
import parser.program_components.statements.ReturnStatement;
import parser.utils.MockedExitErrorHandler;
import parser.utils.MockedLexer;
import parser.utils.ParserSingleTestParams;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ParserReturnStatementTest {

    static Stream<Arguments> getReturnExpressionProgramData() {
        return Stream.of(
                Arguments.of(
                        new ParserSingleTestParams(
                                Arrays.asList(
                                        new Token(new Position(1, 1), TokenTypeEnum.INT_KEYWORD),
                                        new StringToken("func", new Position(1, 5), TokenTypeEnum.IDENTIFIER),
                                        new Token(new Position(1, 9), TokenTypeEnum.LEFT_BRACKET),
                                        new Token(new Position(1, 10), TokenTypeEnum.RIGHT_BRACKET),
                                        new Token(new Position(1, 11), TokenTypeEnum.LEFT_CURLY_BRACKET),
                                        new Token(new Position(2, 1), TokenTypeEnum.RETURN_KEYWORD),
                                        new Token(new Position(2, 7), TokenTypeEnum.SEMICOLON),
                                        new Token(new Position(3, 1), TokenTypeEnum.RIGHT_CURLY_BRACKET)
                                ),
                                new HashMap<>() {{
                                    put("func", new FunctionDef("func", TokenTypeEnum.INT_KEYWORD, new HashMap<>(), new CodeBlock(List.of(new ReturnStatement(null)))));
                                }}
                        )
                ),
                Arguments.of(
                        new ParserSingleTestParams(
                                Arrays.asList(
                                        new Token(new Position(1, 1), TokenTypeEnum.INT_KEYWORD),
                                        new StringToken("func", new Position(1, 5), TokenTypeEnum.IDENTIFIER),
                                        new Token(new Position(1, 9), TokenTypeEnum.LEFT_BRACKET),
                                        new Token(new Position(1, 10), TokenTypeEnum.RIGHT_BRACKET),
                                        new Token(new Position(1, 11), TokenTypeEnum.LEFT_CURLY_BRACKET),
                                        new Token(new Position(2, 1), TokenTypeEnum.RETURN_KEYWORD),
                                        new IntegerToken(1, new Position(2, 7)),
                                        new Token(new Position(2, 8), TokenTypeEnum.SEMICOLON),
                                        new Token(new Position(3, 1), TokenTypeEnum.RIGHT_CURLY_BRACKET)
                                ),
                                new HashMap<>() {{
                                    put("func", new FunctionDef("func", TokenTypeEnum.INT_KEYWORD, new HashMap<>(), new CodeBlock(List.of(new ReturnStatement(new IntValue(1))))));
                                }}
                        )
                ),
                Arguments.of(
                        new ParserSingleTestParams(
                                Arrays.asList(
                                        new Token(new Position(1, 1), TokenTypeEnum.DOUBLE_KEYWORD),
                                        new StringToken("func", new Position(1, 8), TokenTypeEnum.IDENTIFIER),
                                        new Token(new Position(1, 12), TokenTypeEnum.LEFT_BRACKET),
                                        new Token(new Position(1, 13), TokenTypeEnum.RIGHT_BRACKET),
                                        new Token(new Position(1, 14), TokenTypeEnum.LEFT_CURLY_BRACKET),
                                        new Token(new Position(2, 1), TokenTypeEnum.RETURN_KEYWORD),
                                        new DoubleToken(2.14, new Position(2, 7)),
                                        new Token(new Position(2, 11), TokenTypeEnum.SEMICOLON),
                                        new Token(new Position(3, 1), TokenTypeEnum.RIGHT_CURLY_BRACKET)
                                ),
                                new HashMap<>() {{
                                    put("func", new FunctionDef("func", TokenTypeEnum.DOUBLE_KEYWORD, new HashMap<>(), new CodeBlock(List.of(new ReturnStatement(new DoubleValue(2.14))))));
                                }}
                        )
                ),
                Arguments.of(
                        new ParserSingleTestParams(
                                Arrays.asList(
                                        new Token(new Position(1, 1), TokenTypeEnum.STRING_KEYWORD),
                                        new StringToken("func", new Position(1, 8), TokenTypeEnum.IDENTIFIER),
                                        new Token(new Position(1, 12), TokenTypeEnum.LEFT_BRACKET),
                                        new Token(new Position(1, 13), TokenTypeEnum.RIGHT_BRACKET),
                                        new Token(new Position(1, 14), TokenTypeEnum.LEFT_CURLY_BRACKET),
                                        new Token(new Position(2, 1), TokenTypeEnum.RETURN_KEYWORD),
                                        new StringToken("a", new Position(2, 7), TokenTypeEnum.STRING_VALUE),
                                        new Token(new Position(2, 8), TokenTypeEnum.SEMICOLON),
                                        new Token(new Position(3, 1), TokenTypeEnum.RIGHT_CURLY_BRACKET)
                                ),
                                new HashMap<>() {{
                                    put("func", new FunctionDef("func", TokenTypeEnum.STRING_KEYWORD, new HashMap<>(), new CodeBlock(List.of(new ReturnStatement(new StringValue("a"))))));
                                }}
                        )
                ),
                Arguments.of(
                        new ParserSingleTestParams(
                                Arrays.asList(
                                        new Token(new Position(1, 1), TokenTypeEnum.BOOL_KEYWORD),
                                        new StringToken("func", new Position(1, 8), TokenTypeEnum.IDENTIFIER),
                                        new Token(new Position(1, 12), TokenTypeEnum.LEFT_BRACKET),
                                        new Token(new Position(1, 13), TokenTypeEnum.RIGHT_BRACKET),
                                        new Token(new Position(1, 14), TokenTypeEnum.LEFT_CURLY_BRACKET),
                                        new Token(new Position(2, 1), TokenTypeEnum.RETURN_KEYWORD),
                                        new Token(new Position(2, 7), TokenTypeEnum.BOOL_TRUE_VALUE_KEYWORD),
                                        new Token(new Position(2, 12), TokenTypeEnum.SEMICOLON),
                                        new Token(new Position(3, 1), TokenTypeEnum.RIGHT_CURLY_BRACKET)
                                ),
                                new HashMap<>() {{
                                    put("func", new FunctionDef("func", TokenTypeEnum.BOOL_KEYWORD, new HashMap<>(), new CodeBlock(List.of(new ReturnStatement(new BoolValue(true))))));
                                }}
                        )
                ),
                Arguments.of(
                        new ParserSingleTestParams(
                                Arrays.asList(
                                        new Token(new Position(1, 1), TokenTypeEnum.BOOL_KEYWORD),
                                        new StringToken("func", new Position(1, 8), TokenTypeEnum.IDENTIFIER),
                                        new Token(new Position(1, 12), TokenTypeEnum.LEFT_BRACKET),
                                        new Token(new Position(1, 13), TokenTypeEnum.RIGHT_BRACKET),
                                        new Token(new Position(1, 14), TokenTypeEnum.LEFT_CURLY_BRACKET),
                                        new Token(new Position(2, 1), TokenTypeEnum.RETURN_KEYWORD),
                                        new Token(new Position(2, 7), TokenTypeEnum.BOOL_FALSE_VALUE_KEYWORD),
                                        new Token(new Position(2, 12), TokenTypeEnum.SEMICOLON),
                                        new Token(new Position(3, 1), TokenTypeEnum.RIGHT_CURLY_BRACKET)
                                ),
                                new HashMap<>() {{
                                    put("func", new FunctionDef("func", TokenTypeEnum.BOOL_KEYWORD, new HashMap<>(), new CodeBlock(List.of(new ReturnStatement(new BoolValue(false))))));
                                }}
                        )
                )
        );
    }

    @ParameterizedTest
    @MethodSource("getReturnExpressionProgramData")
    void parseReturnExpression(ParserSingleTestParams testParams) {
        ArrayList<Token> tokens = new ArrayList<>(testParams.tokens());
        Parser parser = new Parser(new MockedLexer(tokens), new MockedExitErrorHandler());
        Program program = parser.parse();

        assertEquals(testParams.expectedFunctions(), program.functions());
    }
}
