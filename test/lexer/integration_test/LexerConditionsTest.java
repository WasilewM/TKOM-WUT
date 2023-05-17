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

public class LexerConditionsTest {

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

    static Stream<Arguments> generateConditionTokensData() {
        return Stream.of(
                Arguments.of(new MultipleTokensTestParams("a___\n!=a4",
                        Arrays.asList(new SingleTokenDescription(TokenTypeEnum.IDENTIFIER, "a___", 1, 1),
                                new SingleTokenDescription(TokenTypeEnum.NOT_EQUAL_OPERATOR, 2, 1),
                                new SingleTokenDescription(TokenTypeEnum.IDENTIFIER, "a4", 2, 3)))),
                Arguments.of(new MultipleTokensTestParams("4 + 3 >= 81",
                        Arrays.asList(new SingleTokenDescription(TokenTypeEnum.INT_VALUE, 4, 1, 1),
                                new SingleTokenDescription(TokenTypeEnum.ADDITION_OPERATOR, 1, 3),
                                new SingleTokenDescription(TokenTypeEnum.INT_VALUE, 3, 1, 5),
                                new SingleTokenDescription(TokenTypeEnum.GREATER_OR_EQUAL_OPERATOR, 1, 7),
                                new SingleTokenDescription(TokenTypeEnum.INT_VALUE, 81, 1, 10)))),
                Arguments.of(new MultipleTokensTestParams("!prime(a) && (b > 0 || c<=\nd)",
                        Arrays.asList(new SingleTokenDescription(TokenTypeEnum.NEGATION_OPERATOR, 1, 1),
                                new SingleTokenDescription(TokenTypeEnum.IDENTIFIER, "prime", 1, 2),
                                new SingleTokenDescription(TokenTypeEnum.LEFT_BRACKET, 1, 7),
                                new SingleTokenDescription(TokenTypeEnum.IDENTIFIER, "a", 1, 8),
                                new SingleTokenDescription(TokenTypeEnum.RIGHT_BRACKET, 1, 9),
                                new SingleTokenDescription(TokenTypeEnum.AND_OPERATOR, 1, 11),
                                new SingleTokenDescription(TokenTypeEnum.LEFT_BRACKET, 1, 14),
                                new SingleTokenDescription(TokenTypeEnum.IDENTIFIER, "b", 1, 15),
                                new SingleTokenDescription(TokenTypeEnum.GREATER_THAN_OPERATOR, 1, 17),
                                new SingleTokenDescription(TokenTypeEnum.INT_VALUE, 0, 1, 19),
                                new SingleTokenDescription(TokenTypeEnum.OR_OPERATOR, 1, 21),
                                new SingleTokenDescription(TokenTypeEnum.IDENTIFIER, "c", 1, 24),
                                new SingleTokenDescription(TokenTypeEnum.LESS_OR_EQUAL_OPERATOR, 1, 25),
                                new SingleTokenDescription(TokenTypeEnum.IDENTIFIER, "d", 2, 1),
                                new SingleTokenDescription(TokenTypeEnum.RIGHT_BRACKET, 2, 2))))
        );
    }

    @ParameterizedTest
    @MethodSource("generateConditionTokensData")
    void lexVariablesAssignment(MultipleTokensTestParams testScenarioParams) {
        performTest(testScenarioParams);
    }
}
