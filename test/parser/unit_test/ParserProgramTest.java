package parser.unit_test;

import lexer.Position;
import lexer.TokenTypeEnum;
import lexer.tokens.StringToken;
import lexer.tokens.Token;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import parser.Parser;
import parser.program_components.CodeBlock;
import parser.program_components.Program;
import parser.program_components.function_definitions.*;
import parser.program_components.parameters.DoubleParameter;
import parser.program_components.parameters.IntParameter;
import parser.program_components.parameters.StringParameter;
import parser.utils.MockedExitErrorHandler;
import parser.utils.MockedLexer;
import parser.utils.ParserSingleTestParams;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ParserProgramTest {

    static Stream<Arguments> getTestProgramData() {
        return Stream.of(
                Arguments.of(
                        new ParserSingleTestParams(
                                new ArrayList<>(),
                                new HashMap<>()
                        )
                ),
                Arguments.of(
                        new ParserSingleTestParams(
                                Arrays.asList(
                                        new Token(new Position(1, 1), TokenTypeEnum.INT_KEYWORD),
                                        new StringToken("main", new Position(1, 5), TokenTypeEnum.IDENTIFIER),
                                        new Token(new Position(1, 9), TokenTypeEnum.LEFT_BRACKET),
                                        new Token(new Position(1, 10), TokenTypeEnum.RIGHT_BRACKET),
                                        new Token(new Position(1, 11), TokenTypeEnum.LEFT_CURLY_BRACKET),
                                        new Token(new Position(1, 12), TokenTypeEnum.RIGHT_CURLY_BRACKET)
                                ),
                                new HashMap<>() {{
                                    put("main", new IntFunctionDef(new Position(1, 1), "main", new HashMap<>(), new CodeBlock(new Position(1, 11), new ArrayList<>())));
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
                                        new Token(new Position(1, 15), TokenTypeEnum.RIGHT_CURLY_BRACKET)
                                ),
                                new HashMap<>() {{
                                    put("func", new DoubleFunctionDef(new Position(1, 1), "func", new HashMap<>(), new CodeBlock(new Position(1, 14), new ArrayList<>())));
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
                                        new Token(new Position(1, 15), TokenTypeEnum.RIGHT_CURLY_BRACKET)
                                ),
                                new HashMap<>() {{
                                    put("func", new StringFunctionDef(new Position(1, 1), "func", new HashMap<>(), new CodeBlock(new Position(1, 14), new ArrayList<>())));
                                }}
                        )
                ),
                Arguments.of(
                        new ParserSingleTestParams(
                                Arrays.asList(
                                        new Token(new Position(1, 1), TokenTypeEnum.BOOL_KEYWORD),
                                        new StringToken("func", new Position(1, 6), TokenTypeEnum.IDENTIFIER),
                                        new Token(new Position(1, 10), TokenTypeEnum.LEFT_BRACKET),
                                        new Token(new Position(1, 11), TokenTypeEnum.RIGHT_BRACKET),
                                        new Token(new Position(1, 12), TokenTypeEnum.LEFT_CURLY_BRACKET),
                                        new Token(new Position(1, 13), TokenTypeEnum.RIGHT_CURLY_BRACKET)
                                ),
                                new HashMap<>() {{
                                    put("func", new BoolFunctionDef(new Position(1, 1), "func", new HashMap<>(), new CodeBlock(new Position(1, 12), new ArrayList<>())));
                                }}
                        )
                ),
                Arguments.of(
                        new ParserSingleTestParams(
                                Arrays.asList(
                                        new Token(new Position(1, 1), TokenTypeEnum.POINT_KEYWORD),
                                        new StringToken("func", new Position(1, 7), TokenTypeEnum.IDENTIFIER),
                                        new Token(new Position(1, 11), TokenTypeEnum.LEFT_BRACKET),
                                        new Token(new Position(1, 12), TokenTypeEnum.RIGHT_BRACKET),
                                        new Token(new Position(1, 13), TokenTypeEnum.LEFT_CURLY_BRACKET),
                                        new Token(new Position(1, 14), TokenTypeEnum.RIGHT_CURLY_BRACKET)
                                ),
                                new HashMap<>() {{
                                    put("func", new PointFunctionDef(new Position(1, 1), "func", new HashMap<>(), new CodeBlock(new Position(1, 13), new ArrayList<>())));
                                }}
                        )
                ),
                Arguments.of(
                        new ParserSingleTestParams(
                                Arrays.asList(
                                        new Token(new Position(1, 1), TokenTypeEnum.SECTION_KEYWORD),
                                        new StringToken("func", new Position(1, 9), TokenTypeEnum.IDENTIFIER),
                                        new Token(new Position(1, 13), TokenTypeEnum.LEFT_BRACKET),
                                        new Token(new Position(1, 14), TokenTypeEnum.RIGHT_BRACKET),
                                        new Token(new Position(1, 15), TokenTypeEnum.LEFT_CURLY_BRACKET),
                                        new Token(new Position(1, 16), TokenTypeEnum.RIGHT_CURLY_BRACKET)
                                ),
                                new HashMap<>() {{
                                    put("func", new SectionFunctionDef(new Position(1, 1), "func", new HashMap<>(), new CodeBlock(new Position(1, 15), new ArrayList<>())));
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
                                        new Token(new Position(1, 15), TokenTypeEnum.RIGHT_CURLY_BRACKET)
                                ),
                                new HashMap<>() {{
                                    put("func", new FigureFunctionDef(new Position(1, 1), "func", new HashMap<>(), new CodeBlock(new Position(1, 14), new ArrayList<>())));
                                }}
                        )
                ),
                Arguments.of(
                        new ParserSingleTestParams(
                                Arrays.asList(
                                        new Token(new Position(1, 1), TokenTypeEnum.SCENE_KEYWORD),
                                        new StringToken("func", new Position(1, 7), TokenTypeEnum.IDENTIFIER),
                                        new Token(new Position(1, 11), TokenTypeEnum.LEFT_BRACKET),
                                        new Token(new Position(1, 12), TokenTypeEnum.RIGHT_BRACKET),
                                        new Token(new Position(1, 13), TokenTypeEnum.LEFT_CURLY_BRACKET),
                                        new Token(new Position(1, 14), TokenTypeEnum.RIGHT_CURLY_BRACKET)
                                ),
                                new HashMap<>() {{
                                    put("func", new SceneFunctionDef(new Position(1, 1), "func", new HashMap<>(), new CodeBlock(new Position(1, 13), new ArrayList<>())));
                                }}
                        )
                ),
                Arguments.of(
                        new ParserSingleTestParams(
                                Arrays.asList(
                                        new Token(new Position(1, 1), TokenTypeEnum.LIST_KEYWORD),
                                        new Token(new Position(1, 11), TokenTypeEnum.LEFT_SQUARE_BRACKET),
                                        new Token(new Position(1, 21), TokenTypeEnum.INT_KEYWORD),
                                        new Token(new Position(1, 31), TokenTypeEnum.RIGHT_SQUARE_BRACKET),
                                        new StringToken("func", new Position(2, 7), TokenTypeEnum.IDENTIFIER),
                                        new Token(new Position(2, 11), TokenTypeEnum.LEFT_BRACKET),
                                        new Token(new Position(2, 12), TokenTypeEnum.RIGHT_BRACKET),
                                        new Token(new Position(2, 13), TokenTypeEnum.LEFT_CURLY_BRACKET),
                                        new Token(new Position(2, 14), TokenTypeEnum.RIGHT_CURLY_BRACKET)
                                ),
                                new HashMap<>() {{
                                    put("func", new IntListFunctionDef(new Position(1, 1), "func", new HashMap<>(), new CodeBlock(new Position(2, 13), new ArrayList<>())));
                                }}
                        )
                ),
                Arguments.of(
                        new ParserSingleTestParams(
                                Arrays.asList(
                                        new Token(new Position(1, 1), TokenTypeEnum.LIST_KEYWORD),
                                        new Token(new Position(1, 11), TokenTypeEnum.LEFT_SQUARE_BRACKET),
                                        new Token(new Position(1, 21), TokenTypeEnum.DOUBLE_KEYWORD),
                                        new Token(new Position(1, 31), TokenTypeEnum.RIGHT_SQUARE_BRACKET),
                                        new StringToken("func", new Position(2, 7), TokenTypeEnum.IDENTIFIER),
                                        new Token(new Position(2, 11), TokenTypeEnum.LEFT_BRACKET),
                                        new Token(new Position(2, 12), TokenTypeEnum.RIGHT_BRACKET),
                                        new Token(new Position(2, 13), TokenTypeEnum.LEFT_CURLY_BRACKET),
                                        new Token(new Position(2, 14), TokenTypeEnum.RIGHT_CURLY_BRACKET)
                                ),
                                new HashMap<>() {{
                                    put("func", new DoubleListFunctionDef(new Position(1, 1), "func", new HashMap<>(), new CodeBlock(new Position(2, 13), new ArrayList<>())));
                                }}
                        )
                ),
                Arguments.of(
                        new ParserSingleTestParams(
                                Arrays.asList(
                                        new Token(new Position(1, 1), TokenTypeEnum.LIST_KEYWORD),
                                        new Token(new Position(1, 11), TokenTypeEnum.LEFT_SQUARE_BRACKET),
                                        new Token(new Position(1, 21), TokenTypeEnum.BOOL_KEYWORD),
                                        new Token(new Position(1, 31), TokenTypeEnum.RIGHT_SQUARE_BRACKET),
                                        new StringToken("func", new Position(2, 7), TokenTypeEnum.IDENTIFIER),
                                        new Token(new Position(2, 11), TokenTypeEnum.LEFT_BRACKET),
                                        new Token(new Position(2, 12), TokenTypeEnum.RIGHT_BRACKET),
                                        new Token(new Position(2, 13), TokenTypeEnum.LEFT_CURLY_BRACKET),
                                        new Token(new Position(2, 14), TokenTypeEnum.RIGHT_CURLY_BRACKET)
                                ),
                                new HashMap<>() {{
                                    put("func", new BoolListFunctionDef(new Position(1, 1), "func", new HashMap<>(), new CodeBlock(new Position(2, 13), new ArrayList<>())));
                                }}
                        )
                ),
                Arguments.of(
                        new ParserSingleTestParams(
                                Arrays.asList(
                                        new Token(new Position(1, 1), TokenTypeEnum.INT_KEYWORD),
                                        new StringToken("cube", new Position(1, 5), TokenTypeEnum.IDENTIFIER),
                                        new Token(new Position(1, 9), TokenTypeEnum.LEFT_BRACKET),
                                        new Token(new Position(1, 10), TokenTypeEnum.INT_KEYWORD),
                                        new StringToken("a", new Position(1, 14), TokenTypeEnum.IDENTIFIER),
                                        new Token(new Position(1, 16), TokenTypeEnum.RIGHT_BRACKET),
                                        new Token(new Position(1, 17), TokenTypeEnum.LEFT_CURLY_BRACKET),
                                        new Token(new Position(1, 18), TokenTypeEnum.RIGHT_CURLY_BRACKET)
                                ),
                                new HashMap<>() {{
                                    put("cube", new IntFunctionDef(new Position(1, 1), "cube", new HashMap<>() {{
                                        put("a", new IntParameter(new Position(1, 10), "a"));
                                    }}, new CodeBlock(new Position(1, 17), new ArrayList<>())));
                                }}
                        )
                ),
                Arguments.of(
                        new ParserSingleTestParams(
                                Arrays.asList(
                                        new Token(new Position(1, 1), TokenTypeEnum.INT_KEYWORD),
                                        new StringToken("cube", new Position(1, 5), TokenTypeEnum.IDENTIFIER),
                                        new Token(new Position(1, 9), TokenTypeEnum.LEFT_BRACKET),
                                        new Token(new Position(1, 10), TokenTypeEnum.INT_KEYWORD),
                                        new StringToken("a", new Position(1, 14), TokenTypeEnum.IDENTIFIER),
                                        new Token(new Position(1, 15), TokenTypeEnum.COMMA),
                                        new Token(new Position(1, 17), TokenTypeEnum.INT_KEYWORD),
                                        new StringToken("b", new Position(1, 21), TokenTypeEnum.IDENTIFIER),
                                        new Token(new Position(1, 22), TokenTypeEnum.RIGHT_BRACKET),
                                        new Token(new Position(1, 23), TokenTypeEnum.LEFT_CURLY_BRACKET),
                                        new Token(new Position(1, 24), TokenTypeEnum.RIGHT_CURLY_BRACKET)
                                ),
                                new HashMap<>() {{
                                    put("cube", new IntFunctionDef(new Position(1, 1), "cube", new HashMap<>() {{
                                        put("a", new IntParameter(new Position(1, 10), "a"));
                                        put("b", new IntParameter(new Position(1, 17), "b"));
                                    }}, new CodeBlock(new Position(1, 23), new ArrayList<>())));
                                }}
                        )
                ),
                Arguments.of(
                        new ParserSingleTestParams(
                                Arrays.asList(
                                        new Token(new Position(1, 1), TokenTypeEnum.INT_KEYWORD),
                                        new StringToken("cube", new Position(1, 5), TokenTypeEnum.IDENTIFIER),
                                        new Token(new Position(1, 9), TokenTypeEnum.LEFT_BRACKET),
                                        new Token(new Position(1, 10), TokenTypeEnum.DOUBLE_KEYWORD),
                                        new StringToken("a", new Position(1, 17), TokenTypeEnum.IDENTIFIER),
                                        new Token(new Position(1, 18), TokenTypeEnum.COMMA),
                                        new Token(new Position(1, 17), TokenTypeEnum.STRING_KEYWORD),
                                        new StringToken("b", new Position(1, 27), TokenTypeEnum.IDENTIFIER),
                                        new Token(new Position(1, 28), TokenTypeEnum.RIGHT_BRACKET),
                                        new Token(new Position(1, 29), TokenTypeEnum.LEFT_CURLY_BRACKET),
                                        new Token(new Position(1, 30), TokenTypeEnum.RIGHT_CURLY_BRACKET)
                                ),
                                new HashMap<>() {{
                                    put("cube", new IntFunctionDef(new Position(1, 1), "cube", new HashMap<>() {{
                                        put("a", new DoubleParameter(new Position(1, 10), "a"));
                                        put("b", new StringParameter(new Position(1, 17), "b"));
                                    }}, new CodeBlock(new Position(1, 29), new ArrayList<>())));
                                }}
                        )
                )

        );
    }

    @Test
    void parserInit() {
        ArrayList<Token> tokens = new ArrayList<>();
        Parser parser = new Parser(new MockedLexer(tokens), new MockedExitErrorHandler());

        assertNotNull(parser);
    }

    @ParameterizedTest
    @MethodSource("getTestProgramData")
    void parseProgram(ParserSingleTestParams testParams) {
        ArrayList<Token> tokens = new ArrayList<>(testParams.tokens());
        Parser parser = new Parser(new MockedLexer(tokens), new MockedExitErrorHandler());
        Program program = parser.parse();

        assertEquals(testParams.expectedFunctions(), program.functions());
    }
}
