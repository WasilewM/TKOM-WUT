# Dokumentacja Interpretera

## Niejawne rzutowanie

## Obsługa błędów

Obsługa błędów realizowana jest przez obiekt implementujący interfejs `IErrorHandler`. Dostarczona implementacja tego
modułu traktuje wszystkie błędy (wymienione poniżej) jako krytyczne, tj. ich wystąpienie skutkuje natychmiastowym
przerwaniem przetwarzania.

## Błędy

- `ExceededFunctionCallStackSizeException`
- `ExceededMaxRecursionDepthException`
- `IdentifierNotFoundException`
- `IncompatibleArgumentsListException`
- `IncompatibleDataTypeException`
- `IncompatibleMethodArgumentException`
- `InvalidNumberOfArgumentsException`
- `MissingMainFunctionException`
- `MissingReturnValueException`
- `NullExpressionException`
- `OperationDataTypeException`
- `ParameterNotFoundException`
- `UndefinedFunctionCallExpression`
- `UndefinedMethodCallExpression`
- `ZeroDivisionException`