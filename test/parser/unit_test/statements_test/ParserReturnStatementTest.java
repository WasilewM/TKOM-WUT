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
import parser.program_components.Program;
import parser.program_components.data_values.*;
import parser.program_components.function_definitions.*;
import parser.program_components.statements.ReturnStatement;
import parser.utils.MockedExitParserErrorHandler;
import parser.utils.MockedLexer;
import parser.utils.ParserSingleTestParams;

import java.util.*;
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
                                    put("func", new IntFunctionDef(new Position(1, 1), "func", new LinkedHashMap<>(), new CodeBlock(new Position(1, 11), List.of(new ReturnStatement(new Position(2, 1), null)))));
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
                                    put("func", new IntFunctionDef(new Position(1, 1), "func", new LinkedHashMap<>(), new CodeBlock(new Position(1, 11), List.of(new ReturnStatement(new Position(2, 1), new IntValue(new Position(2, 7), 1))))));
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
                                    put("func", new DoubleFunctionDef(new Position(1, 1), "func", new LinkedHashMap<>(), new CodeBlock(new Position(1, 14), List.of(new ReturnStatement(new Position(2, 1), new DoubleValue(new Position(2, 7), 2.14))))));
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
                                    put("func", new StringFunctionDef(new Position(1, 1), "func", new LinkedHashMap<>(), new CodeBlock(new Position(1, 14), List.of(new ReturnStatement(new Position(2, 1), new StringValue(new Position(2, 7), "a"))))));
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
                                    put("func", new BoolFunctionDef(new Position(1, 1), "func", new LinkedHashMap<>(), new CodeBlock(new Position(1, 14), List.of(new ReturnStatement(new Position(2, 1), new BoolValue(new Position(2, 7), true))))));
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
                                    put("func", new BoolFunctionDef(new Position(1, 1), "func", new LinkedHashMap<>(), new CodeBlock(new Position(1, 14), List.of(new ReturnStatement(new Position(2, 1), new BoolValue(new Position(2, 7), false))))));
                                }}
                        )
                ),
                Arguments.of(
                        new ParserSingleTestParams(
                                Arrays.asList(
                                        new Token(new Position(1, 1), TokenTypeEnum.POINT_KEYWORD),
                                        new StringToken("func", new Position(1, 8), TokenTypeEnum.IDENTIFIER),
                                        new Token(new Position(1, 12), TokenTypeEnum.LEFT_BRACKET),
                                        new Token(new Position(1, 13), TokenTypeEnum.RIGHT_BRACKET),
                                        new Token(new Position(1, 14), TokenTypeEnum.LEFT_CURLY_BRACKET),
                                        new Token(new Position(2, 1), TokenTypeEnum.RETURN_KEYWORD),
                                        new Token(new Position(8, 14), TokenTypeEnum.POINT_KEYWORD),
                                        new Token(new Position(8, 23), TokenTypeEnum.LEFT_BRACKET),
                                        new DoubleToken(2.0, new Position(8, 24)),
                                        new Token(new Position(8, 30), TokenTypeEnum.COMMA),
                                        new DoubleToken(2.0, new Position(8, 34)),
                                        new Token(new Position(8, 44), TokenTypeEnum.RIGHT_BRACKET),
                                        new Token(new Position(8, 44), TokenTypeEnum.SEMICOLON),
                                        new Token(new Position(13, 1), TokenTypeEnum.RIGHT_CURLY_BRACKET)
                                ),
                                new HashMap<>() {{
                                    put("func", new PointFunctionDef(new Position(1, 1), "func", new LinkedHashMap<>(), new CodeBlock(new Position(1, 14), List.of(new ReturnStatement(new Position(2, 1), new PointValue(new Position(8, 14), new DoubleValue(new Position(8, 24), 2.0), new DoubleValue(new Position(8, 34), 2.0)))))));
                                }}
                        )
                ),
                Arguments.of(
                        new ParserSingleTestParams(
                                Arrays.asList(
                                        new Token(new Position(1, 1), TokenTypeEnum.SECTION_KEYWORD),
                                        new StringToken("func", new Position(1, 8), TokenTypeEnum.IDENTIFIER),
                                        new Token(new Position(1, 12), TokenTypeEnum.LEFT_BRACKET),
                                        new Token(new Position(1, 13), TokenTypeEnum.RIGHT_BRACKET),
                                        new Token(new Position(1, 14), TokenTypeEnum.LEFT_CURLY_BRACKET),
                                        new Token(new Position(2, 1), TokenTypeEnum.RETURN_KEYWORD),
                                        new Token(new Position(8, 14), TokenTypeEnum.SECTION_KEYWORD),
                                        new Token(new Position(8, 23), TokenTypeEnum.LEFT_BRACKET),
                                        new Token(new Position(9, 14), TokenTypeEnum.POINT_KEYWORD),
                                        new Token(new Position(9, 23), TokenTypeEnum.LEFT_BRACKET),
                                        new DoubleToken(2.0, new Position(9, 24)),
                                        new Token(new Position(9, 30), TokenTypeEnum.COMMA),
                                        new DoubleToken(2.0, new Position(9, 34)),
                                        new Token(new Position(9, 44), TokenTypeEnum.RIGHT_BRACKET),
                                        new Token(new Position(19, 1), TokenTypeEnum.COMMA),
                                        new Token(new Position(19, 14), TokenTypeEnum.POINT_KEYWORD),
                                        new Token(new Position(19, 23), TokenTypeEnum.LEFT_BRACKET),
                                        new DoubleToken(2.0, new Position(19, 24)),
                                        new Token(new Position(19, 30), TokenTypeEnum.COMMA),
                                        new DoubleToken(2.0, new Position(19, 34)),
                                        new Token(new Position(19, 44), TokenTypeEnum.RIGHT_BRACKET),
                                        new Token(new Position(20, 44), TokenTypeEnum.RIGHT_BRACKET),
                                        new Token(new Position(28, 94), TokenTypeEnum.SEMICOLON),
                                        new Token(new Position(13, 1), TokenTypeEnum.RIGHT_CURLY_BRACKET)
                                ),
                                new HashMap<>() {{
                                    put("func", new SectionFunctionDef(new Position(1, 1), "func", new LinkedHashMap<>(), new CodeBlock(new Position(1, 14), List.of(new ReturnStatement(new Position(2, 1), new SectionValue(new Position(8, 14), new PointValue(new Position(9, 14), new DoubleValue(new Position(9, 24), 2.0), new DoubleValue(new Position(9, 34), 2.0)), new PointValue(new Position(19, 14), new DoubleValue(new Position(19, 24), 2.0), new DoubleValue(new Position(19, 34), 2.0))))))));
                                }}
                        )
                ),
                Arguments.of(
                        new ParserSingleTestParams(
                                Arrays.asList(
                                        new Token(new Position(1, 1), TokenTypeEnum.FIGURE_KEYWORD),
                                        new StringToken("func", new Position(1, 8), TokenTypeEnum.IDENTIFIER),
                                        new Token(new Position(1, 12), TokenTypeEnum.LEFT_BRACKET),
                                        new Token(new Position(1, 13), TokenTypeEnum.RIGHT_BRACKET),
                                        new Token(new Position(1, 14), TokenTypeEnum.LEFT_CURLY_BRACKET),
                                        new Token(new Position(2, 1), TokenTypeEnum.RETURN_KEYWORD),
                                        new Token(new Position(8, 14), TokenTypeEnum.FIGURE_KEYWORD),
                                        new Token(new Position(8, 24), TokenTypeEnum.LEFT_BRACKET),
                                        new Token(new Position(8, 34), TokenTypeEnum.RIGHT_BRACKET),
                                        new Token(new Position(8, 94), TokenTypeEnum.SEMICOLON),
                                        new Token(new Position(13, 1), TokenTypeEnum.RIGHT_CURLY_BRACKET)
                                ),
                                new HashMap<>() {{
                                    put("func", new FigureFunctionDef(new Position(1, 1), "func", new LinkedHashMap<>(), new CodeBlock(new Position(1, 14), List.of(new ReturnStatement(new Position(2, 1), new FigureValue(new Position(8, 14)))))));
                                }}
                        )
                ),
                Arguments.of(
                        new ParserSingleTestParams(
                                Arrays.asList(
                                        new Token(new Position(1, 1), TokenTypeEnum.SCENE_KEYWORD),
                                        new StringToken("func", new Position(1, 8), TokenTypeEnum.IDENTIFIER),
                                        new Token(new Position(1, 12), TokenTypeEnum.LEFT_BRACKET),
                                        new Token(new Position(1, 13), TokenTypeEnum.RIGHT_BRACKET),
                                        new Token(new Position(1, 14), TokenTypeEnum.LEFT_CURLY_BRACKET),
                                        new Token(new Position(2, 1), TokenTypeEnum.RETURN_KEYWORD),
                                        new Token(new Position(8, 14), TokenTypeEnum.SCENE_KEYWORD),
                                        new Token(new Position(8, 24), TokenTypeEnum.LEFT_BRACKET),
                                        new Token(new Position(8, 34), TokenTypeEnum.RIGHT_BRACKET),
                                        new Token(new Position(8, 94), TokenTypeEnum.SEMICOLON),
                                        new Token(new Position(13, 1), TokenTypeEnum.RIGHT_CURLY_BRACKET)
                                ),
                                new HashMap<>() {{
                                    put("func", new SceneFunctionDef(new Position(1, 1), "func", new LinkedHashMap<>(), new CodeBlock(new Position(1, 14), List.of(new ReturnStatement(new Position(2, 1), new SceneValue(new Position(8, 14)))))));
                                }}
                        )
                )
        );
    }

    @ParameterizedTest
    @MethodSource("getReturnExpressionProgramData")
    void parseReturnExpression(ParserSingleTestParams testParams) {
        ArrayList<Token> tokens = new ArrayList<>(testParams.tokens());
        Parser parser = new Parser(new MockedLexer(tokens), new MockedExitParserErrorHandler());
        Program program = parser.parse();

        assertEquals(testParams.expectedFunctions(), program.functions());
    }
}
