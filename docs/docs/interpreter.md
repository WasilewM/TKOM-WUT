# Dokumentacja Interpretera

## Niejawne rzutowanie

W operacjach arytmetycznych występuje niejawne rzutowanie typu pomiędzy wartościami `IntValue` oraz `DoubleValue`.  
Dla operacji:

- dodawania (`AdditionExpression`)
- odejmowania (`SubtractionExpression`)
- mnożenia (`MultiplicationExpression`)
- dzielenia (`DivisionExpression`)

Gdy przynajmniej jedna z wartości jest typu `DoubleValue`, to obie wartości są rzutowane do typu `DoubleValue`.  
Dla operacji dzielenia całkowitoliczbowego (`DiscreteDivisionExpression`) obie wartości są rzutowane do typu `IntValue`.

Ponadto, w przypadku przypisywania wartości do zmiennej lub zwracania wartości z funkcji może wystąpić niejawne
rzutowanie typu. Należy pamiętać o tym, że rzutowanie to jest dokonywane po określeniu wartości wyrażenia, co może mieć
wpływ na jego wynik.  
Poniżej znajduje się przykład w języku `Java`, który ilustruje różnice pomiędzy rzutowaniem wartości przed ewaluacją
oraz po ewaluacji wartości wyrażenia:

```
public class Main {
    public static void main(String[] args) {
        double val1 = 6.70;
        double val2 = 5.40;

        // int expression result when casting to int after expression evaluation
        int expResult = (int) (val1 + val2);
        System.out.println("int expression result when casting to int after expression evaluation: " + expResult);

        // int expression result when casting to int before expression evaluation
        expResult = ((int) val1) + ((int) val2);
        System.out.println("int expression result when casting to int before expression evaluation: " + expResult);

    }
}
```

Oraz wynik wykonania powyższego kodu:

```
int expression result when casting to int after expression evaluation: 12
int expression result when casting to int before expression evaluation: 11

Process finished with exit code 0
```

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