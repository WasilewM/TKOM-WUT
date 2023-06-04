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