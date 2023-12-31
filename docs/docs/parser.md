# Dokumentacja Parsera

## Obsługa błędów

Obsługa błędów realizowana jest przez obiekt implementujący interfejs `IErrorHandler`. Dostarczona implementacja tego
modułu dzieli zgłoszone błędy na dwie kategorie:

- `handleable exceptions` - błędy, które muszą zostać zgłoszone użytkownikowi, natomiast pomimo ich wystąpienia możliwe
  jest kontynuowanie analizy
- `critical exceptions` - błędy, które uniemożliwiają dalszą analizę, a ich wystąpienie skutkuje natychmiastowym
  przerwaniem przetwarzania

## Błędy "handleable exceptions"

- `MissingComaException`
- `MissingLeftBracketException`
- `MissingRightBracketException`
- `MissingSemicolonException`

## Błędy "critical exceptions"

- `AmbiguousExpressionException`
- `DuplicatedFunctionNameException`
- `DuplicatedParameterNameException`
- `MissingAssignmentOperatorException`
- `MissingCodeBlockException`
- `MissingDataTypeDeclarationException`
- `MissingExpressionException`
- `MissingIdentifierException`
- `MissingLeftCurlyBracketException`
- `MissingRightCurlyBracketException`
- `MissingRichtSquareBracketException`
- `UnclearExpressionException`
- `UnclosedParenthesesException`

## Testowanie

Testy parsera opierają się na analizie kolejnych Tokenów. Dla każdego pozytywnego testu zdefiniowana została prawidłowa
struktura programu, wynikająca z dostarczonych tokenów, która powinna być wynikiem działania parsera.

W ramach przykładu testów jednostkowych poniżej zamieszczone są testy jednostkowe na rozpoznanie pętli `while`:

```
package parser.unit_test.statements_test;

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
import parser.program_components.function_definitions.IntFunctionDef;
import parser.program_components.statements.WhileStatement;
import parser.utils.MockedExitParserErrorHandler;
import parser.utils.MockedLexer;
import parser.utils.ParserSingleTestParams;

import java.util.*;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ParserWhileStatementTest {

    private ArrayList<Token> startTokens;

    public static Stream<Arguments> getWhileStatementTestData() {
        return Stream.of(
                Arguments.of(
                        new ParserSingleTestParams(
                                Arrays.asList(
                                        new Token(new Position(2, 1), TokenTypeEnum.WHILE_KEYWORD),
                                        new Token(new Position(2, 4), TokenTypeEnum.LEFT_BRACKET),
                                        new StringToken("a", new Position(2, 5), TokenTypeEnum.IDENTIFIER),
                                        new Token(new Position(3, 1), TokenTypeEnum.RIGHT_BRACKET),
                                        new Token(new Position(51, 1), TokenTypeEnum.LEFT_CURLY_BRACKET),
                                        new Token(new Position(52, 1), TokenTypeEnum.RIGHT_CURLY_BRACKET),
                                        new Token(new Position(100, 1), TokenTypeEnum.RIGHT_CURLY_BRACKET)
                                ),
                                new HashMap<>() {{
                                    put("func", new IntFunctionDef(new Position(1, 1), "func", new LinkedHashMap<>(), new CodeBlock(new Position(1, 11), List.of(new WhileStatement(new Position(2, 1), new Identifier(new Position(2, 5), "a"), new CodeBlock(new Position(51, 1), new ArrayList<>()))))));
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
                        new Token(new Position(1, 11), TokenTypeEnum.LEFT_CURLY_BRACKET)
                )
        );
    }

    @ParameterizedTest
    @MethodSource("getWhileStatementTestData")
    void parseWhileStatement(ParserSingleTestParams additionalTestParams) {
        ArrayList<Token> testTokens = new ArrayList<>(startTokens);
        testTokens.addAll(additionalTestParams.tokens());

        MockedExitParserErrorHandler errorHandler = new MockedExitParserErrorHandler();
        Parser parser = new Parser(new MockedLexer(testTokens), errorHandler);
        Program program = parser.parse();

        assertEquals(additionalTestParams.expectedFunctions(), program.functions());
        assertEquals(0, errorHandler.getErrorLog().size());
    }
}
```