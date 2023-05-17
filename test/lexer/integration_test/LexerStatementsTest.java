package lexer.integration_test;

import lexer.Lexer;
import lexer.TokenTypeEnum;
import lexer.tokens.Token;
import lexer.utils.MultipleTokensTestParams;
import lexer.utils.SingleTokenDescription;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LexerStatementsTest {

    private static void performTest(MultipleTokensTestParams testScenarioParams) {
        InputStream inputStream = new ByteArrayInputStream(testScenarioParams.inputString().getBytes());
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        Lexer lex = new Lexer(bufferedReader);

        for (SingleTokenDescription tokenDesc : testScenarioParams.tokens()) {
            Token token = lex.lexToken();
            assertEquals(tokenDesc.getTokenType(), token.getTokenType());
            assertEquals(tokenDesc.getValue(), token.getValue());
            assertEquals(tokenDesc.getLineNumber(), token.getPosition().getLineNumber());
            assertEquals(tokenDesc.getColumnNumber(), token.getPosition().getColumnNumber());
        }
    }

    static Stream<Arguments> generateStatementTokensData() {
        return Stream.of(
                Arguments.of(new MultipleTokensTestParams("""
                        Int main() {
                        Double a = 4.01 / 2.67;
                        return 0;
                        }""",
                        Arrays.asList(new SingleTokenDescription(TokenTypeEnum.INT_KEYWORD, 1, 1),
                                new SingleTokenDescription(TokenTypeEnum.IDENTIFIER, "main", 1, 5),
                                new SingleTokenDescription(TokenTypeEnum.LEFT_BRACKET, 1, 9),
                                new SingleTokenDescription(TokenTypeEnum.RIGHT_BRACKET, 1, 10),
                                new SingleTokenDescription(TokenTypeEnum.LEFT_CURLY_BRACKET, 1, 12),
                                new SingleTokenDescription(TokenTypeEnum.DOUBLE_KEYWORD, 2, 1),
                                new SingleTokenDescription(TokenTypeEnum.IDENTIFIER, "a", 2, 8),
                                new SingleTokenDescription(TokenTypeEnum.ASSIGNMENT_OPERATOR, 2, 10),
                                new SingleTokenDescription(TokenTypeEnum.DOUBLE_VALUE, 4.01, 2, 12),
                                new SingleTokenDescription(TokenTypeEnum.DIVISION_OPERATOR, 2, 17),
                                new SingleTokenDescription(TokenTypeEnum.DOUBLE_VALUE, 2.67, 2, 19),
                                new SingleTokenDescription(TokenTypeEnum.SEMICOLON, 2, 23),
                                new SingleTokenDescription(TokenTypeEnum.RETURN_KEYWORD, 3, 1),
                                new SingleTokenDescription(TokenTypeEnum.INT_VALUE, 0, 3, 8),
                                new SingleTokenDescription(TokenTypeEnum.SEMICOLON, 3, 9),
                                new SingleTokenDescription(TokenTypeEnum.RIGHT_CURLY_BRACKET, 4, 1)))),
                Arguments.of(new MultipleTokensTestParams("while (a<=b && (c != d || e == f)) { return True; }",
                        Arrays.asList(new SingleTokenDescription(TokenTypeEnum.WHILE_KEYWORD, 1, 1),
                                new SingleTokenDescription(TokenTypeEnum.LEFT_BRACKET, 1, 7),
                                new SingleTokenDescription(TokenTypeEnum.IDENTIFIER, "a", 1, 8),
                                new SingleTokenDescription(TokenTypeEnum.LESS_OR_EQUAL_OPERATOR, 1, 9),
                                new SingleTokenDescription(TokenTypeEnum.IDENTIFIER, "b", 1, 11),
                                new SingleTokenDescription(TokenTypeEnum.AND_OPERATOR, 1, 13),
                                new SingleTokenDescription(TokenTypeEnum.LEFT_BRACKET, 1, 16),
                                new SingleTokenDescription(TokenTypeEnum.IDENTIFIER, "c", 1, 17),
                                new SingleTokenDescription(TokenTypeEnum.NOT_EQUAL_OPERATOR, 1, 19),
                                new SingleTokenDescription(TokenTypeEnum.IDENTIFIER, "d", 1, 22),
                                new SingleTokenDescription(TokenTypeEnum.OR_OPERATOR, 1, 24),
                                new SingleTokenDescription(TokenTypeEnum.IDENTIFIER, "e", 1, 27),
                                new SingleTokenDescription(TokenTypeEnum.EQUAL_OPERATOR, 1, 29),
                                new SingleTokenDescription(TokenTypeEnum.IDENTIFIER, "f", 1, 32),
                                new SingleTokenDescription(TokenTypeEnum.RIGHT_BRACKET, 1, 33),
                                new SingleTokenDescription(TokenTypeEnum.RIGHT_BRACKET, 1, 34),
                                new SingleTokenDescription(TokenTypeEnum.LEFT_CURLY_BRACKET, 1, 36),
                                new SingleTokenDescription(TokenTypeEnum.RETURN_KEYWORD, 1, 38),
                                new SingleTokenDescription(TokenTypeEnum.BOOL_TRUE_VALUE_KEYWORD, 1, 45),
                                new SingleTokenDescription(TokenTypeEnum.SEMICOLON, 1, 49),
                                new SingleTokenDescription(TokenTypeEnum.RIGHT_CURLY_BRACKET, 1, 51))))
        );
    }

    @ParameterizedTest
    @MethodSource("generateStatementTokensData")
    void lexStatement(MultipleTokensTestParams testScenarioParams) {
        performTest(testScenarioParams);
    }
}
