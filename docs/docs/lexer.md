# Dokumentacja Leksera

## Tokeny

W projekcie tokeny są reprezentowane przez `lexer.TokenTypeEnum`.

### Słowa kluczowe oraz odpowiadające im tokeny

| Słowo kluczowe | Nazwa tokenu             |
|----------------|--------------------------|
| Int            | INT_KEYWORD              |
| Double         | DOUBLE_KEYWORD           |
| String         | STRING                   |
| Point          | POINT_KEYWORD            |
| Section        | SECTION_KEYWORD          |
| Figure         | FIGURE_KEYWORD           |
| Scene          | SCENE_KEYWORD            |
| List           | LIST_KEYWORD             |
| Bool           | BOOL_KEYWORD             |
| True           | BOOL_TRUE_VALUE_KEYWORD  |
| False          | BOOL_FALSE_VALUE_KEYWORD |
| while          | WHILE_KEYWORD            |
| if             | IF_KEYWORD               |
| elseif         | ELSE_IF_KEYWORD          |
| else           | ELSE_KEYWORD             |
| return         | RETURN_KEYWORD           |
| void           | VOID_KEYWORD             |

### Operatory oraz odpowiadające im tokeny

Operator logiczny `lub`, tj. `||`, psuł formatowanie poniższej tabeli i dlatego nie został w niej umieszczony.

| Operator | Nazwa tokenu               |
|----------|----------------------------|
| `+`      | ADDITION_OPERATOR          |
| `-`      | SUBTRACTION_OPERATOR       |
| `*`      | MULTIPLICATION_OPERATOR    |
| `/`      | DIVISION_OPERATOR          |
| `//`     | DISCRETE_DIVISION_OPERATOR |
| `&&`     | AND_OPERATOR               |
|          | OR_OPERATOR                |
| `!`      | NEGATION_OPERATOR          |
| `==`     | EQUAL_OPERATOR             |
| `!=`     | NOT_EQUAL_OPERATOR         |
| `<`      | LESS_THAN_OPERATOR         |
| `>`      | GREATER_THAN_OPERATOR      |
| `<=`     | LESS_OR_EQUAL_OPERATOR     |
| `>=`     | GREATER_OR_EQUAL_OPERATOR  |
| `=`      | ASSIGNMENT_OPERATOR        |

### Znaki specjalne oraz odpowiadające im tokeny

| Znak specjalny | Nazwa tokenu         |
|----------------|----------------------|
| `#`            | COMMENT              |
| `(`            | LEFT_BRACKET         |
| `)`            | RIGHT_BRACKET        |
| `[`            | LEFT_SQUARE_BRACKET  |
| `]`            | RIGHT_SQUARE_BRACKET |
| `{`            | LEFT_CURLY_BRACKET   |
| `}`            | RIGHT_CURLY_BRACKET  |
| `;`            | SEMICOLON            |
| `.`            | DOT                  |
| `,`            | COMMA                |

### Inne tokeny będące częścią projektowanego języka

| Element języka                 | Nazwa tokenu |
|--------------------------------|--------------|
| Wartość zmiennej typu `String` | STRING_VALUE |
| Wartość zmiennej typu `Int`    | INT_VALE     |
| Wartość zmiennej typu `Double` | DOUBLE_VALE  |
| Nazwa zmiennej lub funkcji     | IDENTIFIER   |

### Tokeny błędów rozpoznanych przez lekser

| Błąd                                                     | Nazwa tokenu                             |
|----------------------------------------------------------|------------------------------------------|
| Nieznany znak                                            | UNKNOWN_CHAR_ERROR                       |
| Niedomknięty cudzysłów w wartości zmiennej typu `String` | UNCLOSED_QUOTES_ERROR                    |
| Długość zmiennej `String` przekracza ustaloną granicę    | STRING_EXCEEDED_MAXIMUM_LENGTH_ERROR     |
| Długość identyfikatora przekracza ustaloną granicę       | IDENTIFIER_EXCEEDED_MAXIMUM_LENGTH_ERROR |
| Zmienna typu `Int` przekroczyła ustalony zakres          | INT_EXCEEDED_RANGE_ERROR                 |
| Zmienna typu `Double` przekroczyła ustalony zakres       | DOUBLE_EXCEEDED_RANGE_ERROR              |

## Testowanie

Testy jednostkowe napisane zostały z wykorzystaniem biblioteki `JUnit5`. W ramach testów dostarczany jest ciąg znaków,
który następnie powinien zostać przeanalizowany na poprawny token (w przypadku testów negatywnych) lub zwrócona powinna
być informacja o braku możliwości stworzenia prawidłowego tokenu.

W ramach przykładu testów jednostkowych poniżej zamieszczone są testy jednostkowe na rozpoznanie identyfikatora:

```
package lexer.unit_test;

import lexer.Lexer;
import lexer.TokenTypeEnum;
import lexer.tokens.StringToken;
import lexer.utils.SingleTokenDescription;
import lexer.utils.SingleTokenTestParams;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LexerIdentifierTest {

    private static void performTest(SingleTokenTestParams testScenarioParams) {
        InputStream inputStream = new ByteArrayInputStream(testScenarioParams.inputString().getBytes());
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        Lexer lex = new Lexer(bufferedReader);

        StringToken token = (StringToken) lex.lexToken();
        assertEquals(testScenarioParams.token().getTokenType(), token.getTokenType());
        assertEquals(StringToken.class, token.getClass());
        assertEquals(testScenarioParams.token().getValue(), token.getValue());
        assertEquals(testScenarioParams.token().getLineNumber(), token.getPosition().getLineNumber());
        assertEquals(testScenarioParams.token().getColumnNumber(), token.getPosition().getColumnNumber());
    }

    static Stream<Arguments> generateIdentifierTokensData() {
        return Stream.of(
                Arguments.of(new SingleTokenTestParams("a", new SingleTokenDescription(TokenTypeEnum.IDENTIFIER, "a", 1, 1))),
                Arguments.of(new SingleTokenTestParams("a4", new SingleTokenDescription(TokenTypeEnum.IDENTIFIER, "a4", 1, 1))),
                Arguments.of(new SingleTokenTestParams("a4b6_c7", new SingleTokenDescription(TokenTypeEnum.IDENTIFIER, "a4b6_c7", 1, 1))),
                Arguments.of(new SingleTokenTestParams("hello_There_Identifier", new SingleTokenDescription(TokenTypeEnum.IDENTIFIER, "hello_There_Identifier", 1, 1))),
                Arguments.of(new SingleTokenTestParams("\n\n\n   \n\n\n hello_There_Identifier\n", new SingleTokenDescription(TokenTypeEnum.IDENTIFIER, "hello_There_Identifier", 7, 2)))
        );
    }

    static Stream<Arguments> generateIdentifierLookingSimilarToKeywordTokensData() {
        return Stream.of(
                Arguments.of(new SingleTokenTestParams("Integer", new SingleTokenDescription(TokenTypeEnum.IDENTIFIER, "Integer", 1, 1))),
                Arguments.of(new SingleTokenTestParams("myDouble", new SingleTokenDescription(TokenTypeEnum.IDENTIFIER, "myDouble", 1, 1))),
                Arguments.of(new SingleTokenTestParams("some_String", new SingleTokenDescription(TokenTypeEnum.IDENTIFIER, "some_String", 1, 1))),
                Arguments.of(new SingleTokenTestParams("main", new SingleTokenDescription(TokenTypeEnum.IDENTIFIER, "main", 1, 1))),  // main is not a keyword, but it's easy to expect that ii is a keyword
                Arguments.of(new SingleTokenTestParams("firstPoint", new SingleTokenDescription(TokenTypeEnum.IDENTIFIER, "firstPoint", 1, 1))),
                Arguments.of(new SingleTokenTestParams("\n\nPointlessValue", new SingleTokenDescription(TokenTypeEnum.IDENTIFIER, "PointlessValue", 3, 1)))
        );
    }

    @ParameterizedTest
    @MethodSource("generateIdentifierTokensData")
    void lexIdentifier(SingleTokenTestParams testScenarioParams) {
        performTest(testScenarioParams);
    }

    @ParameterizedTest
    @MethodSource("generateIdentifierLookingSimilarToKeywordTokensData")
    void lexIdentifierLookingSimilarToKeywords(SingleTokenTestParams testScenarioParams) {
        performTest(testScenarioParams);
    }

    @Test
    void lexIdentifierLongerThanAllowedLength() {
        InputStream inputStream = new ByteArrayInputStream("thisIsSomeVeryLongIdentifier_12345678".getBytes());
        int identifierMaxLength = 14;
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        Lexer lex = new Lexer(bufferedReader);
        lex.setIdentifierMaxLength(identifierMaxLength);
        StringToken token = (StringToken) lex.lexToken();

        assertEquals(TokenTypeEnum.IDENTIFIER_EXCEEDED_MAXIMUM_LENGTH_ERROR, token.getTokenType());
        assertEquals(StringToken.class, token.getClass());
        assertEquals("thisIsSomeVery", token.getValue());
        assertEquals(1, token.getPosition().getLineNumber());
        assertEquals(1, token.getPosition().getColumnNumber());
    }
}
```