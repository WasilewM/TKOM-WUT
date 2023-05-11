package parser.unit_test.statements_test;

import lexer.Position;
import lexer.TokenTypeEnum;
import lexer.tokens.DoubleToken;
import lexer.tokens.IntegerToken;
import lexer.tokens.StringToken;
import lexer.tokens.Token;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import parser.Parser;
import parser.program_components.CodeBlock;
import parser.program_components.Program;
import parser.program_components.data_values.*;
import parser.program_components.function_definitions.BoolFunctionDef;
import parser.program_components.parameters.*;
import parser.program_components.statements.AssignmentStatement;
import parser.utils.MockedExitErrorHandler;
import parser.utils.MockedLexer;
import parser.utils.ParserSingleTestParams;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ParserAssignmentStatementTest {

    private ArrayList<Token> startTokens;

    public static Stream<Arguments> getAssignmentStatementTestData() {
        return Stream.of(
                Arguments.of(
                        new ParserSingleTestParams(
                                Arrays.asList(
                                        new Token(new Position(5, 7), TokenTypeEnum.INT_KEYWORD),
                                        new StringToken("A", new Position(7, 10), TokenTypeEnum.IDENTIFIER),
                                        new Token(new Position(8, 4), TokenTypeEnum.ASSIGNMENT_OPERATOR),
                                        new IntegerToken(1, new Position(8, 14)),
                                        new Token(new Position(8, 24), TokenTypeEnum.SEMICOLON),
                                        new Token(new Position(9, 4), TokenTypeEnum.RIGHT_CURLY_BRACKET)
                                ),
                                new HashMap<>() {{
                                    put("func", new BoolFunctionDef(new Position(1, 1), "func", new HashMap<>(), new CodeBlock(new Position(1, 14), List.of(new AssignmentStatement(new Position(5, 7), new IntParameter(new Position(5, 7), "A"), new IntValue(new Position(8, 14), 1))))));
                                }}
                        )
                ),
                Arguments.of(
                        new ParserSingleTestParams(
                                Arrays.asList(
                                        new Token(new Position(5, 7), TokenTypeEnum.INT_KEYWORD),
                                        new StringToken("A", new Position(7, 10), TokenTypeEnum.IDENTIFIER),
                                        new Token(new Position(8, 4), TokenTypeEnum.ASSIGNMENT_OPERATOR),
                                        new IntegerToken(1, new Position(8, 14)),
                                        new Token(new Position(8, 24), TokenTypeEnum.SEMICOLON),
                                        new StringToken("A", new Position(9, 10), TokenTypeEnum.IDENTIFIER),
                                        new Token(new Position(10, 4), TokenTypeEnum.ASSIGNMENT_OPERATOR),
                                        new IntegerToken(11, new Position(10, 14)),
                                        new Token(new Position(12, 24), TokenTypeEnum.SEMICOLON),
                                        new Token(new Position(13, 4), TokenTypeEnum.RIGHT_CURLY_BRACKET)
                                ),
                                new HashMap<>() {{
                                    put("func", new BoolFunctionDef(new Position(1, 1), "func", new HashMap<>(), new CodeBlock(new Position(1, 14), List.of(new AssignmentStatement(new Position(5, 7), new IntParameter(new Position(5, 7), "A"), new IntValue(new Position(8, 14), 1)), new AssignmentStatement(new Position(9, 10), new ReassignedParameter(new Position(9, 10), "A"), new IntValue(new Position(10, 14), 11))))));
                                }}
                        )
                ),
                Arguments.of(
                        new ParserSingleTestParams(
                                Arrays.asList(
                                        new Token(new Position(5, 7), TokenTypeEnum.POINT_KEYWORD),
                                        new StringToken("A", new Position(7, 10), TokenTypeEnum.IDENTIFIER),
                                        new Token(new Position(8, 4), TokenTypeEnum.ASSIGNMENT_OPERATOR),
                                        new Token(new Position(8, 14), TokenTypeEnum.POINT_KEYWORD),
                                        new Token(new Position(8, 23), TokenTypeEnum.LEFT_BRACKET),
                                        new DoubleToken(2.0, new Position(8, 24)),
                                        new Token(new Position(8, 30), TokenTypeEnum.COMMA),
                                        new DoubleToken(2.0, new Position(8, 34)),
                                        new Token(new Position(8, 44), TokenTypeEnum.RIGHT_BRACKET),
                                        new Token(new Position(8, 44), TokenTypeEnum.SEMICOLON),
                                        new Token(new Position(9, 4), TokenTypeEnum.RIGHT_CURLY_BRACKET)
                                ),
                                new HashMap<>() {{
                                    put("func", new BoolFunctionDef(new Position(1, 1), "func", new HashMap<>(), new CodeBlock(new Position(1, 14), List.of(new AssignmentStatement(new Position(5, 7), new PointParameter(new Position(5, 7), "A"), new PointValue(new Position(8, 14), new DoubleValue(new Position(8, 24), 2.0), new DoubleValue(new Position(8, 34), 2.0)))))));
                                }}
                        )
                ),
                Arguments.of(
                        new ParserSingleTestParams(
                                Arrays.asList(
                                        new Token(new Position(5, 7), TokenTypeEnum.SECTION_KEYWORD),
                                        new StringToken("A", new Position(7, 10), TokenTypeEnum.IDENTIFIER),
                                        new Token(new Position(8, 4), TokenTypeEnum.ASSIGNMENT_OPERATOR),
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
                                        new Token(new Position(39, 4), TokenTypeEnum.RIGHT_CURLY_BRACKET)
                                ),
                                new HashMap<>() {{
                                    put("func", new BoolFunctionDef(new Position(1, 1), "func", new HashMap<>(), new CodeBlock(new Position(1, 14), List.of(new AssignmentStatement(new Position(5, 7), new SectionParameter(new Position(5, 7), "A"), new SectionValue(new Position(8, 14), new PointValue(new Position(9, 14), new DoubleValue(new Position(9, 24), 2.0), new DoubleValue(new Position(9, 34), 2.0)), new PointValue(new Position(19, 14), new DoubleValue(new Position(19, 24), 2.0), new DoubleValue(new Position(19, 34), 2.0))))))));
                                }}
                        )
                ),
                Arguments.of(
                        new ParserSingleTestParams(
                                Arrays.asList(
                                        new Token(new Position(5, 7), TokenTypeEnum.FIGURE_KEYWORD),
                                        new StringToken("A", new Position(7, 10), TokenTypeEnum.IDENTIFIER),
                                        new Token(new Position(8, 4), TokenTypeEnum.ASSIGNMENT_OPERATOR),
                                        new Token(new Position(8, 14), TokenTypeEnum.FIGURE_KEYWORD),
                                        new Token(new Position(8, 24), TokenTypeEnum.LEFT_BRACKET),
                                        new Token(new Position(8, 34), TokenTypeEnum.RIGHT_BRACKET),
                                        new Token(new Position(8, 94), TokenTypeEnum.SEMICOLON),
                                        new Token(new Position(9, 4), TokenTypeEnum.RIGHT_CURLY_BRACKET)
                                ),
                                new HashMap<>() {{
                                    put("func", new BoolFunctionDef(new Position(1, 1), "func", new HashMap<>(), new CodeBlock(new Position(1, 14), List.of(new AssignmentStatement(new Position(5, 7), new FigureParameter(new Position(5, 7), "A"), new FigureValue(new Position(8, 14)))))));
                                }}
                        )
                ),
                Arguments.of(
                        new ParserSingleTestParams(
                                Arrays.asList(
                                        new Token(new Position(5, 7), TokenTypeEnum.SCENE_KEYWORD),
                                        new StringToken("A", new Position(7, 10), TokenTypeEnum.IDENTIFIER),
                                        new Token(new Position(8, 4), TokenTypeEnum.ASSIGNMENT_OPERATOR),
                                        new Token(new Position(8, 14), TokenTypeEnum.SCENE_KEYWORD),
                                        new Token(new Position(8, 24), TokenTypeEnum.LEFT_BRACKET),
                                        new Token(new Position(8, 34), TokenTypeEnum.RIGHT_BRACKET),
                                        new Token(new Position(8, 94), TokenTypeEnum.SEMICOLON),
                                        new Token(new Position(9, 4), TokenTypeEnum.RIGHT_CURLY_BRACKET)
                                ),
                                new HashMap<>() {{
                                    put("func", new BoolFunctionDef(new Position(1, 1), "func", new HashMap<>(), new CodeBlock(new Position(1, 14), List.of(new AssignmentStatement(new Position(5, 7), new SceneParameter(new Position(5, 7), "A"), new SceneValue(new Position(8, 14)))))));
                                }}
                        )
                )
        );
    }

    @BeforeEach
    public void initTestParams() {
        startTokens = new ArrayList<>(
                Arrays.asList(
                        new Token(new Position(1, 1), TokenTypeEnum.BOOL_KEYWORD),
                        new StringToken("func", new Position(1, 8), TokenTypeEnum.IDENTIFIER),
                        new Token(new Position(1, 12), TokenTypeEnum.LEFT_BRACKET),
                        new Token(new Position(1, 13), TokenTypeEnum.RIGHT_BRACKET),
                        new Token(new Position(1, 14), TokenTypeEnum.LEFT_CURLY_BRACKET)
                )
        );
    }

    @ParameterizedTest
    @MethodSource("getAssignmentStatementTestData")
    void parseAssignmentStatement(ParserSingleTestParams additionalTestParams) {
        ArrayList<Token> testTokens = new ArrayList<>(startTokens);
        testTokens.addAll(additionalTestParams.tokens());

        MockedExitErrorHandler errorHandler = new MockedExitErrorHandler();
        Parser parser = new Parser(new MockedLexer(testTokens), errorHandler);
        Program program = parser.parse();

        assertEquals(additionalTestParams.expectedFunctions(), program.functions());
        assertEquals(0, errorHandler.getErrorLog().size());
    }
}
