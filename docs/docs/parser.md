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