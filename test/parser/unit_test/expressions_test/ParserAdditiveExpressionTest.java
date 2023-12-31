package parser.unit_test.expressions_test;

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
import parser.program_components.expressions.AdditionExpression;
import parser.program_components.expressions.SubtractionExpression;
import parser.program_components.function_definitions.IntFunctionDef;
import parser.program_components.statements.IfStatement;
import parser.utils.MockedExitParserErrorHandler;
import parser.utils.MockedLexer;
import parser.utils.ParserSingleTestParams;

import java.util.*;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ParserAdditiveExpressionTest {

    private ArrayList<Token> startTokens;

    static Stream<Arguments> getAdditiveExpTestData() {
        return Stream.of(
                Arguments.of(
                        new ParserSingleTestParams(
                                Arrays.asList(
                                        new StringToken("ident", new Position(2, 5), TokenTypeEnum.IDENTIFIER),
                                        new Token(new Position(2, 12), TokenTypeEnum.ADDITION_OPERATOR),
                                        new StringToken("ident", new Position(2, 15), TokenTypeEnum.IDENTIFIER),
                                        new Token(new Position(50, 1), TokenTypeEnum.RIGHT_BRACKET),
                                        new Token(new Position(51, 1), TokenTypeEnum.LEFT_CURLY_BRACKET),
                                        new Token(new Position(52, 1), TokenTypeEnum.RIGHT_CURLY_BRACKET),
                                        new Token(new Position(100, 1), TokenTypeEnum.RIGHT_CURLY_BRACKET)
                                ),
                                new HashMap<>() {{
                                    put("func", new IntFunctionDef(new Position(1, 1), "func", new LinkedHashMap<>(), new CodeBlock(new Position(1, 11), List.of(new IfStatement(new Position(1, 12), new AdditionExpression(new Position(2, 12), new Identifier(new Position(2, 5), "ident"), new Identifier(new Position(2, 15), "ident")), new CodeBlock(new Position(51, 1), new ArrayList<>()))))));
                                }}
                        )
                ),
                Arguments.of(
                        new ParserSingleTestParams(
                                Arrays.asList(
                                        new StringToken("cube", new Position(2, 5), TokenTypeEnum.IDENTIFIER),
                                        new Token(new Position(2, 12), TokenTypeEnum.ADDITION_OPERATOR),
                                        new StringToken("sqrt", new Position(2, 15), TokenTypeEnum.IDENTIFIER),
                                        new Token(new Position(3, 12), TokenTypeEnum.ADDITION_OPERATOR),
                                        new StringToken("path", new Position(3, 15), TokenTypeEnum.IDENTIFIER),
                                        new Token(new Position(4, 12), TokenTypeEnum.ADDITION_OPERATOR),
                                        new StringToken("then", new Position(4, 15), TokenTypeEnum.IDENTIFIER),
                                        new Token(new Position(50, 1), TokenTypeEnum.RIGHT_BRACKET),
                                        new Token(new Position(51, 1), TokenTypeEnum.LEFT_CURLY_BRACKET),
                                        new Token(new Position(52, 1), TokenTypeEnum.RIGHT_CURLY_BRACKET),
                                        new Token(new Position(100, 1), TokenTypeEnum.RIGHT_CURLY_BRACKET)
                                ),
                                new HashMap<>() {{
                                    put("func", new IntFunctionDef(new Position(1, 1), "func", new LinkedHashMap<>(), new CodeBlock(new Position(1, 11), List.of(new IfStatement(new Position(1, 12), new AdditionExpression(new Position(4, 12), new AdditionExpression(new Position(3, 12), new AdditionExpression(new Position(2, 12), new Identifier(new Position(2, 5), "cube"), new Identifier(new Position(2, 15), "sqrt")), new Identifier(new Position(3, 15), "path")), new Identifier(new Position(4, 15), "then")), new CodeBlock(new Position(51, 1), new ArrayList<>()))))));
                                }}
                        )
                ),
                Arguments.of(
                        new ParserSingleTestParams(
                                Arrays.asList(
                                        new StringToken("ident", new Position(2, 5), TokenTypeEnum.IDENTIFIER),
                                        new Token(new Position(2, 12), TokenTypeEnum.SUBTRACTION_OPERATOR),
                                        new StringToken("ident", new Position(2, 15), TokenTypeEnum.IDENTIFIER),
                                        new Token(new Position(50, 1), TokenTypeEnum.RIGHT_BRACKET),
                                        new Token(new Position(51, 1), TokenTypeEnum.LEFT_CURLY_BRACKET),
                                        new Token(new Position(52, 1), TokenTypeEnum.RIGHT_CURLY_BRACKET),
                                        new Token(new Position(100, 1), TokenTypeEnum.RIGHT_CURLY_BRACKET)
                                ),
                                new HashMap<>() {{
                                    put("func", new IntFunctionDef(new Position(1, 1), "func", new LinkedHashMap<>(), new CodeBlock(new Position(1, 11), List.of(new IfStatement(new Position(1, 12), new SubtractionExpression(new Position(2, 12), new Identifier(new Position(2, 5), "ident"), new Identifier(new Position(2, 15), "ident")), new CodeBlock(new Position(51, 1), new ArrayList<>()))))));
                                }}
                        )
                ),
                Arguments.of(
                        new ParserSingleTestParams(
                                Arrays.asList(
                                        new StringToken("cube", new Position(2, 5), TokenTypeEnum.IDENTIFIER),
                                        new Token(new Position(2, 12), TokenTypeEnum.SUBTRACTION_OPERATOR),
                                        new StringToken("sqrt", new Position(2, 15), TokenTypeEnum.IDENTIFIER),
                                        new Token(new Position(3, 12), TokenTypeEnum.ADDITION_OPERATOR),
                                        new StringToken("path", new Position(3, 15), TokenTypeEnum.IDENTIFIER),
                                        new Token(new Position(4, 12), TokenTypeEnum.SUBTRACTION_OPERATOR),
                                        new StringToken("then", new Position(4, 15), TokenTypeEnum.IDENTIFIER),
                                        new Token(new Position(50, 1), TokenTypeEnum.RIGHT_BRACKET),
                                        new Token(new Position(51, 1), TokenTypeEnum.LEFT_CURLY_BRACKET),
                                        new Token(new Position(52, 1), TokenTypeEnum.RIGHT_CURLY_BRACKET),
                                        new Token(new Position(100, 1), TokenTypeEnum.RIGHT_CURLY_BRACKET)
                                ),
                                new HashMap<>() {{
                                    put("func", new IntFunctionDef(new Position(1, 1), "func", new LinkedHashMap<>(), new CodeBlock(new Position(1, 11), List.of(new IfStatement(new Position(1, 12), new SubtractionExpression(new Position(4, 12), new AdditionExpression(new Position(3, 12), new SubtractionExpression(new Position(2, 12), new Identifier(new Position(2, 5), "cube"), new Identifier(new Position(2, 15), "sqrt")), new Identifier(new Position(3, 15), "path")), new Identifier(new Position(4, 15), "then")), new CodeBlock(new Position(51, 1), new ArrayList<>()))))));
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
    @MethodSource("getAdditiveExpTestData")
    void parseAdditiveExpression(ParserSingleTestParams additionalTestParams) {
        ArrayList<Token> testTokens = new ArrayList<>(startTokens);
        testTokens.addAll(additionalTestParams.tokens());

        MockedExitParserErrorHandler errorHandler = new MockedExitParserErrorHandler();
        Parser parser = new Parser(new MockedLexer(testTokens), errorHandler);
        Program program = parser.parse();

        assertEquals(additionalTestParams.expectedFunctions(), program.functions());
        assertEquals(0, errorHandler.getErrorLog().size());
    }
}
