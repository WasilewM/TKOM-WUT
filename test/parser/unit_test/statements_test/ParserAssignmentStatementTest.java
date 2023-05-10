package parser.unit_test.statements_test;

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
import parser.program_components.CodeBlock;
import parser.program_components.Program;
import parser.program_components.data_values.IntValue;
import parser.program_components.function_definitions.BoolFunctionDef;
import parser.program_components.parameters.IntParameter;
import parser.program_components.parameters.ReassignedParameter;
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
                                    put("func", new BoolFunctionDef("func", new HashMap<>(), new CodeBlock(List.of(new AssignmentStatement(new IntParameter("A"), new IntValue(1))))));
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
                                        new StringToken("A", new Position(7, 10), TokenTypeEnum.IDENTIFIER),
                                        new Token(new Position(8, 4), TokenTypeEnum.ASSIGNMENT_OPERATOR),
                                        new IntegerToken(11, new Position(8, 14)),
                                        new Token(new Position(8, 24), TokenTypeEnum.SEMICOLON),
                                        new Token(new Position(9, 4), TokenTypeEnum.RIGHT_CURLY_BRACKET)
                                ),
                                new HashMap<>() {{
                                    put("func", new BoolFunctionDef("func", new HashMap<>(), new CodeBlock(List.of(new AssignmentStatement(new IntParameter("A"), new IntValue(1)), new AssignmentStatement(new ReassignedParameter("A"), new IntValue(11))))));
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
