import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.*;
import java.util.Arrays;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

public class LexerVariableAssignmentsTest {

    @ParameterizedTest
    @MethodSource("generateVariablesAssignmentTokensData")
    void lexVariablesAssignment(MultipleTokensTestParams testScenarioParams) {
        performTest(testScenarioParams);
    }

    private static void performTest(MultipleTokensTestParams testScenarioParams) {
        InputStream inputStream = new ByteArrayInputStream(testScenarioParams.getInputString().getBytes());
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        Lexer lex = new Lexer(bufferedReader);

        for (SingleTokenDescription tokenDesc : testScenarioParams.getTokens()) {
            Token token = lex.lexToken();
            assertEquals(tokenDesc.getTokenType(), token.getTokenType());
            assertEquals(tokenDesc.getValue(), token.getValue());
            assertEquals(tokenDesc.getLineNumber(), token.getPosition().getLineNumber());
            assertEquals(tokenDesc.getColumnNumber(), token.getPosition().getColumnNumber());
        }
    }

    static Stream<Arguments> generateVariablesAssignmentTokensData() {
        return Stream.of(
                Arguments.of(new MultipleTokensTestParams("Int a = 4 - 9;",
                        Arrays.asList(new SingleTokenDescription(TokenTypeEnum.INT_KEYWORD, 1, 1),
                                new SingleTokenDescription(TokenTypeEnum.IDENTIFIER, "a", 1, 5),
                                new SingleTokenDescription(TokenTypeEnum.ASSIGNMENT_OPERATOR, 1, 7),
                                new SingleTokenDescription(TokenTypeEnum.INT_VALUE, 4, 1, 9),
                                new SingleTokenDescription(TokenTypeEnum.SUBTRACTION_OPERATOR, 1, 11),
                                new SingleTokenDescription(TokenTypeEnum.INT_VALUE, 9, 1, 13),
                                new SingleTokenDescription(TokenTypeEnum.SEMICOLON, 1, 14)))),
                Arguments.of(new MultipleTokensTestParams("Double d = (4.04 / 29.012);",
                        Arrays.asList(new SingleTokenDescription(TokenTypeEnum.DOUBLE_KEYWORD, 1, 1),
                                new SingleTokenDescription(TokenTypeEnum.IDENTIFIER, "d", 1, 8),
                                new SingleTokenDescription(TokenTypeEnum.ASSIGNMENT_OPERATOR, 1, 10),
                                new SingleTokenDescription(TokenTypeEnum.LEFT_BRACKET, 1, 12),
                                new SingleTokenDescription(TokenTypeEnum.DOUBLE_VALUE, 4.04, 1, 13),
                                new SingleTokenDescription(TokenTypeEnum.DIVISION_OPERATOR, 1, 18),
                                new SingleTokenDescription(TokenTypeEnum.DOUBLE_VALUE, 29.012, 1, 20),
                                new SingleTokenDescription(TokenTypeEnum.RIGHT_BRACKET, 1, 26),
                                new SingleTokenDescription(TokenTypeEnum.SEMICOLON, 1, 27)))),
                Arguments.of(new MultipleTokensTestParams("String my_text = \"4 - 9 abcd\";",
                        Arrays.asList(new SingleTokenDescription(TokenTypeEnum.STRING_KEYWORD, 1, 1),
                                new SingleTokenDescription(TokenTypeEnum.IDENTIFIER, "my_text", 1, 8),
                                new SingleTokenDescription(TokenTypeEnum.ASSIGNMENT_OPERATOR, 1, 16),
                                new SingleTokenDescription(TokenTypeEnum.STRING_VALUE, "4 - 9 abcd", 1, 18),
                                new SingleTokenDescription(TokenTypeEnum.SEMICOLON, 1, 30)))),
                Arguments.of(new MultipleTokensTestParams("Bool boolean_Val_ = \n\n\n True\n;",
                        Arrays.asList(new SingleTokenDescription(TokenTypeEnum.BOOL_KEYWORD, 1, 1),
                                new SingleTokenDescription(TokenTypeEnum.IDENTIFIER, "boolean_Val_", 1, 6),
                                new SingleTokenDescription(TokenTypeEnum.ASSIGNMENT_OPERATOR, 1, 19),
                                new SingleTokenDescription(TokenTypeEnum.BOOL_TRUE_VALUE_KEYWORD, 4, 2),
                                new SingleTokenDescription(TokenTypeEnum.SEMICOLON, 5, 1)))),
                Arguments.of(new MultipleTokensTestParams("Bool boolean_Val_ = \n\n\n False\n;",
                        Arrays.asList(new SingleTokenDescription(TokenTypeEnum.BOOL_KEYWORD, 1, 1),
                                new SingleTokenDescription(TokenTypeEnum.IDENTIFIER, "boolean_Val_", 1, 6),
                                new SingleTokenDescription(TokenTypeEnum.ASSIGNMENT_OPERATOR, 1, 19),
                                new SingleTokenDescription(TokenTypeEnum.BOOL_FALSE_VALUE_KEYWORD, 4, 2),
                                new SingleTokenDescription(TokenTypeEnum.SEMICOLON, 5, 1))))
        );
    }
}
