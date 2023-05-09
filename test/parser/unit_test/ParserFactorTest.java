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
import parser.program_components.Identifier;
import parser.program_components.Program;
import parser.program_components.data_values.BoolValue;
import parser.program_components.expressions.ConjunctiveExpression;
import parser.program_components.expressions.NegatedExpression;
import parser.program_components.expressions.ParenthesesExpression;
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

public class ParserFactorTest {

    private ArrayList<Token> startTokens;

    static Stream<Arguments> getFactorTestData() {
        return Stream.of(
                Arguments.of(
                        new ParserSingleTestParams(
                                Arrays.asList(
                                        new Token(new Position(3, 1), TokenTypeEnum.LEFT_BRACKET),
                                        new StringToken("abv", new Position(4, 7), TokenTypeEnum.IDENTIFIER),
                                        new Token(new Position(5, 1), TokenTypeEnum.RIGHT_BRACKET),
                                        new Token(new Position(6, 12), TokenTypeEnum.SEMICOLON),
                                        new Token(new Position(7, 1), TokenTypeEnum.RIGHT_CURLY_BRACKET)
                                ),
                                new HashMap<>() {{
                                    put("func", new FunctionDef("func", TokenTypeEnum.BOOL_KEYWORD, new HashMap<>(), new CodeBlock(List.of(new ReturnStatement(new ParenthesesExpression(new Identifier("abv")))))));
                                }}
                        )
                ),
                Arguments.of(
                        new ParserSingleTestParams(
                                Arrays.asList(
                                        new Token(new Position(4, 7), TokenTypeEnum.NEGATION_OPERATOR),
                                        new Token(new Position(4, 27), TokenTypeEnum.BOOL_TRUE_VALUE_KEYWORD),
                                        new Token(new Position(6, 12), TokenTypeEnum.SEMICOLON),
                                        new Token(new Position(7, 1), TokenTypeEnum.RIGHT_CURLY_BRACKET)
                                ),
                                new HashMap<>() {{
                                    put("func", new FunctionDef("func", TokenTypeEnum.BOOL_KEYWORD, new HashMap<>(), new CodeBlock(List.of(new ReturnStatement(new NegatedExpression(new BoolValue(true)))))));
                                }}
                        )
                ),
                Arguments.of(
                        new ParserSingleTestParams(
                                Arrays.asList(
                                        new Token(new Position(4, 7), TokenTypeEnum.NEGATION_OPERATOR),
                                        new Token(new Position(5, 1), TokenTypeEnum.LEFT_BRACKET),
                                        new StringToken("a1", new Position(5, 10), TokenTypeEnum.IDENTIFIER),
                                        new Token(new Position(5, 20), TokenTypeEnum.AND_OPERATOR),
                                        new StringToken("a11", new Position(5, 30), TokenTypeEnum.IDENTIFIER),
                                        new Token(new Position(5, 100), TokenTypeEnum.RIGHT_BRACKET),
                                        new Token(new Position(6, 12), TokenTypeEnum.SEMICOLON),
                                        new Token(new Position(7, 1), TokenTypeEnum.RIGHT_CURLY_BRACKET)
                                ),
                                new HashMap<>() {{
                                    put("func", new FunctionDef("func", TokenTypeEnum.BOOL_KEYWORD, new HashMap<>(), new CodeBlock(List.of(new ReturnStatement(new NegatedExpression(new ParenthesesExpression(new ConjunctiveExpression(new Identifier("a1"), new Identifier("a11")))))))));
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
                        new Token(new Position(1, 14), TokenTypeEnum.LEFT_CURLY_BRACKET),
                        new Token(new Position(2, 1), TokenTypeEnum.RETURN_KEYWORD)
                )
        );
    }

    @ParameterizedTest
    @MethodSource("getFactorTestData")
    void parseFactor(ParserSingleTestParams additionalTestParams) {
        ArrayList<Token> testTokens = new ArrayList<>(startTokens);
        testTokens.addAll(additionalTestParams.tokens());

        MockedExitErrorHandler errorHandler = new MockedExitErrorHandler();
        Parser parser = new Parser(new MockedLexer(testTokens), errorHandler);
        Program program = parser.parse();

        assertEquals(additionalTestParams.expectedFunctions(), program.functions());
        assertEquals(0, errorHandler.getErrorLog().size());
    }
}
