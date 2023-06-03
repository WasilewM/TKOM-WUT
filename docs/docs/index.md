# O projekcie

## Autor: Mateusz Wasilewski

## Temat projektu

Język umożliwiający opis punktów i odcinków na płaszczyźnie. Punkt i odcinek (zbudowany z punktów) są wbudowanymi typami
języka. Z odcinków można budować figury geometryczne. Kolekcja figur tworzy scenę wyświetlaną na ekranie.

## Założenia

1. Projekt zostanie zrealizowany w języku Java.
1. Projektowany język będzie typowany silnie i statycznie.
1. Za plik napisany w projektowanym języku uznawane będą pliki z rozszerzeniem `.surface` lub strumień danych. Analiza
   programu kończona jest w wyniku otrzymania znaku końca tekstu `ETX`.
1. W języku dostępne będą następujące typy danych:
    1. Numeryczne: `Int`, `Double`
    1. Tekstowy: `String`
    1. Wbudowane typy obiektowe: `Point`, `Section`, `Figure`, `Scene` (opisy tych typów przedstawiony jest niżej)
    1. Kolekcja - lista: `List`. Do kolejnych elementów listy można odwoływać się poprzez indeksy: `someList[i]`.
       Ponadto. type `List` posiada wbudowane funkcje `add` do dodania elementu na koniec listy, `get(int i)` do
       sięgania do wskazanego elementu listy oraz `pop` do usunięcia
       ostatniego elementu z listy. Opis tego typu również znajduje się poniżej.
    1. Typ boole'owski: `Bool` o wartościach `True` i `False`.
1. Komentarze oznacza się znakami `#`. Komentarze obowiązują do końca linii.
1. Pętla realizowana będzie przez instrukcję `while`.
1. Sprawdzenie warunków logicznych będzie można zapisać w bloku `if`, `elseif`, `else`.
1. Znak końca linii może być reprezentowany przez `\r`, `\n`, `\r\n` lub `\n\r`. Przyjmowana będzie konwencja rozpoznana
   na końcu pierwszej linii pliku lub źródła. Zastosowanie dwóch lub więcej różnych konwencji w jednym pliku będzie
   powodowało generowanie komunikatów o błędach.
1. Dostępne operatory arytmetyczne:
    1. `+`
    1. `-`
    1. `*`
    1. `/`
    1. `//` - dzielenie całkowitoliczbowe
1. Operatory logiczne:
    1. `&&` - operator logiczny koniunkcji
    1. `||` - operator logiczny alternatywy
    1. `!` - operator logiczny negacji
1. Operatory porównania:
    1. `==` - operator logiczny równości
    1. `!=` - operator logiczny nierówności
    1. `<`
    1. `>`
    1. `<=`
    1. `>=`
1. Operator przypisania: `=`.
1. Istnieć będzie możliwość definiowania funkcji. Nazwa funkcji musi być reprezentowalna przez następujące wyrażenie
   regularne `[a-zA-Z][-a-zA-Z0-9]*`. Parametry do funkcji będą przekazywane poprzez referencję.
1. Istnieć będzie możliwość definiowania zmiennych. Nazwa zmiennej musi być reprezentowalna przez następujące wyrażenie
   regularne `[a-zA-Z][-a-zA-Z0-9]*`. Zmienne będą widoczne tylko w zakresie funkcji, w której zostały zdefiniowane.
1. Słowa kluczowe języka i znaki specjalne:
    1. Typy danych: `Int`, `Double`, `String`, `Point`, `Section`, `Figure`, `Scene`, `Bool`, `List`
    1. Instrukcje sterujące: `if`, `elseif`, `else`, `while`
    1. Dostępne operatory arytmetyczne
    1. Dostępne operatory logiczne
    1. Znak rozpoczęcia komentarza
    1. `main`
    1. `return`
    1. `void` - słowo kluczowe oznaczające brak zwracania danych z funkcji. W przypadku deklaracji funkcji `void` a
       następnie próbie zwrócenia jakichś danych z wykorzystaniem słowa kluczowego `return` wygenerowany zostanie błąd.
    1. Znaki otwierające i zamykające nawiasy: `(`, `)`, `[`, `]`, `{`, `}`
    1. Znak końca instrukcji `;`
    1. Operator odwołania do właściwości i metod obiekty: `.`
    1. Operator przypisania: `=`
    1. `,`
    1. nazwy wszystkich metod zapisanych poniżej w definicji typów wbudowanych `Point`, `Section`, `Figure` oraz `Scene`
1. Domyślne ograniczenia parametrów wypisanych poniżej zapisane będą w pliku konfiguracyjnym (patrz
   sekcja [Plik konfiguracyjny](#plik-konfiguracyjny)) i będzie możliwość ich modyfikacji.
    1. `IntMax` - maksymalny zakres zmiennych typu `Int`
    1. `IntMin` - minimalny zakres zmiennych typu `Int`
    1. `DoubleMax` - maksymalny zakres zmiennych typu `Double`
    1. `DoubleMin` - minimalny zakres zmiennych typu `Double`
    1. `RecursionMaxDepth` - maksymalna liczba poziomów rekursji

## Plik konfiguracyjny

Konfiguracja parametrów zapisana będzie w pliku `config.yaml`. Zawartość pliku będzie następująca:

```
IntMax: 2147483646
DoubleMax: 21474836460000000000
StringMaxLength: 1000
RecursionMaxDepth: 1000
```

## Gramatyka

### EBNF

```
program                 = { functionDef }
functionDef             = functionType, "(", { parameters }, ")", codeBlock
functionType            = parameter
                           | ( "void", identifier )
codeBlock               = "{", { stmnt }, "}"
stmnt                   = ifStmnt
                           | whileStmnt
                           | assignmentStmnt
                           | returnStmnt
                           | objectAccessStmnt
ifStmnt                 = "if", "(", alternativeExp, ")", "{", codeBlock, "}", { elseifStmnt }, [ elseStmnt ]
elseifStmnt             = "elseif", "(", alternativeExp, ")", "{", codeBlock, "}"
elseStmnt               = "else", "(", alternativeExp, ")", "{", codeBlock, "}"
whileStmnt              = "while", "(", alternativeExp, ")", "{", codeBlock, "}"
returnStmnt             = "return", alternativeExp , ";"
assignmentStmnt         = [ dataType ], identifier, assignmentOper, alternativeExp, ";"
parameters              = paremater, ",", { parameter }
parameter               = dataType, identifier


objectAccessStmnt       = objectAccessExp, ";"
objectAccessExp         = identOrFuncCallExp, { ".", identOrFuncCallExp }
identOrFuncCallExp      = identifier, { "(", [ alternativeExp ], ")" }
identifier              = letter { digit | literal }


alternativeExp          = conjunctiveExp, { orOper, conjunctiveExp }
conjunctiveExp          = comparisonExp, { andOper, comparisonExp }
comparisonExp           = additiveExp, [ comparisonOper, additiveExp ]
additiveExp             = multiplicativeExp, { additiveOper, multiplicativeExp }
multiplicativeExp       = factor, { multiplicativeOper, factor }
factor                  = [ notOper ] ( parenthesesExp | assignableValue )
parenthesesExp          = "(", alternativeExp, ")"
assignableValue         = objectAccessExp
                           | stringValue
                           | intValue
                           | doubleValue
                           | boolValue
                           | pointValue
                           | sectionValue
                           | figureValue
                           | sceneValue
                           | listValue


doubleValue             = intValue, [ ".", intValue ]
intValue                = zeroDigit
                           | notZeroDigit, { digit }
stringValue             = "\"", literal, "\""
literal                 = literalSign, { literalSign }
literalSign             = "_"
                           | letter
logicalOper             = andOper
                           | orOper
                           | notOper
comparisonOper          = equalOper
                           | notEqualOper
                           | lessThanOper
                           | lessThanOrEqualOper
                           | greaterThanOper
                           | greaterThanOrEqualOper


pointValue              = "Point", "(", assignableValue, ",", assignableValue, ")"
sectionValue            = "Section", "(", assignableValue, ",", assignableValue, ")"
figureValue             = "Figure", "(", ")"
sceneValue              = "Scene", "(", ")"

dataType                = "List", "[", listableDataType, "]"
                           | listableDataType
listableDataType        = "Int"
                           | "Double"
                           | "String"
                           | "Bool"
                           | "Point"
                           | "Section"
                           | "Scene"

listValue               = "[", listableDataType, "]"
boolValue               = "True"
                           | False   
equalOper               = "=="
notEqualOper            = "!="
lessThanOper            = "<"
lessThanOrEqualOper     = "<="
greaterThanOper         = ">"
greaterThanOrEqualOper  = ">="
andOper                 = "&&"
orOper                  = "||"
notOper                 = "!"
minusOper               = "-"
additiveOper            = "+"
                           | "-"
multiplicativeOper      = "*"
                           | "/"
                           | "//"
assignmentOper          = "="
comment                 = "#"
zeroDigit               = "0"
notZeroDigit            = "1".."9"
letter                  = "a".."z"
                           | "A".."Z"
```

### Analiza przykładowego bloku instrukcji if

```
ifStmnt: if (i == s1.length() && (((a+b) * d // g + e - f) >= c || !checkSomeBool()) { return True; }
ifStmnt: "if", "(", alternativeExp, "), "{", codeBlock, "}":
|-- alternativeExp: i == s1.length() && (((a+b) * d // g + e - f) >= c || !checkSomeBool())
|   alternativeExp: conjunctiveExp
|   |-- conjunctiveExp: i == s1.length() && (((a+b) * d // g + e - f) >= c || !checkSomeBool())
|       conjunctiveExp: comparisonExp, andOper, comparisonExp
|       |-- comparisonExp: i == s1.length()
|       |   comparisonExp: additiveExp, comparisonOper, additiveExp
|       |   |-- additiveExp: i
|       |   |   additiveExp: multiplicativeExp
|       |   |   |-- multiplicativeExp: i
|       |   |       multiplicativeExp: factor
|       |   |       |-- factor: i
|       |   |           factor: assignableValue
|       |   |           |-- assignableValue: i
|       |   |               assignableValue: positiveAssignableValue
|       |   |               |-- positiveAssignableValue: i
|       |   |                   positiveAssignableValue: identifier
|       |   |-- additiveExp: s1.length()
|       |       additiveExp: multiplicativeExp
|       |       |-- multiplicativeExp: s1.length()
|       |           multiplicativeExp: factor
|       |           |-- factor: s1.length()
|       |               factor: assignableValue
|       |               |-- assignableValue: s1.length()
|       |                   assignableValue: positiveAssignableValue
|       |                   |-- positiveAssignableValue: s1.length()
|       |                       positiveAssignableValue: functionCall
|       |-- comparisonExp: (((a+b) * d // g + e - f) >= c || !checkSomeBool())
|           comparisonExp: additiveExp, comparisonOper, additiveExp
|           |-- additiveExp: (((a+b) * d // g + e - f) >= c || !checkSomeBool())
|               additiveExp: multiplicativeExp
|               |-- multiplicativeExp: ((a+b) * d // g + e - f) >= c || !checkSomeBool())
|                   multiplicativeExp: factor
|                   |-- factor: (((a+b) * d // g + e - f) >= c || !checkSomeBool())
|                       factor: parenthesesExp
|                       |-- parenthesesExp: "(", alternativeExp, ")"
|                           parenthesesExp: ((a+b) * d // g + e - f >= c || !checkSomeBool())
|                           |-- alternativeExp: ((a+b) * d // g + e - f) >= c || !checkSomeBool()
|                               alternativeExp: conjunctiveExp, orOper, conjunctiveExp
|                               |-- conjunctiveExp: ((a+b) * d // g + e - f) >= c
|                               |   conjunctiveExp: multiplicativeExp
|                               |   |-- comparisonExp: (a+b) * d // g + e - f >= c
|                               |       comparisonExp: additiveExp, comparisonOper, additiveExp
|                               |       |-- additiveExp: ((a+b) * d // g + e - f)
|                               |       |   additiveExp: multiplicativeExp
|                               |       |   |-- multiplicativeExp: ((a+b) * d // g + e - f)
|                               |       |       multiplicativeExp: factor
|                               |       |       |-- factor: ((a+b) * d // g + e - f)
|                               |       |           factor: parenthesesExp
|                               |       |           |-- parenthesesExp: ((a+b) * d // g + e - f)
|                               |       |               parenthesesExp: "(", alternativeExp, ")"
|                               |       |               |-- alternativeExp: (a+b) * d // g + e - f
|                               |       |                   alternativeExp: conjunctiveExp
|                               |       |                   |-- conjunctiveExp: (a+b) * d // g + e - f
|                               |       |                       conjunctiveExp: comparisonExp
|                               |       |                       |-- comparisonExp: (a+b) * d // g + e - f
|                               |       |                           comparisonExp: addititveExp
|                               |       |                           |-- additveExp: (a+b) * d // g + e - f
|                               |       |                               additiveExp: multiplicativeExp, additiveOper, multiplicativeExp, additiveOper, multiplicativeExp
|                               |       |                               |-- multiplicativeExp: (a+b) * d // g
|                               |       |                               |   multiplicativeExp: factor, multiplicativeOper, factor, multiplicativeOper, factor
|                               |       |                               |   |-- factor: (a+b)
|                               |       |                               |   |   factor: parenthesesExp
|                               |       |                               |   |   |-- parenthesesExp: (a+b)
|                               |       |                               |   |       parenthesesExp: "(", alternativeExp, ")"
|                               |       |                               |   |       |-- alternativeExp: a+b
|                               |       |                               |   |           alternativeExp: conjunctiveExp
|                               |       |                               |   |           |-- conjunctiveExp: a+b
|                               |       |                               |   |               conjunctiveExp: comparisonExp
|                               |       |                               |   |               |-- comparisonExp: a+b
|                               |       |                               |   |                   comparisonExp: additiveExp
|                               |       |                               |   |                   |-- additiveExp: a+b
|                               |       |                               |   |                       additiveExp: multiplicativeExp, additiveOper, multiplicativeExp
|                               |       |                               |   |                       |-- multiplicativeExp: a
|                               |       |                               |   |                       |   multiplicativeExp: factor
|                               |       |                               |   |                       |   |-- factor: a
|                               |       |                               |   |                       |       factor: assignableValue
|                               |       |                               |   |                       |       |-- assignableValue: a
|                               |       |                               |   |                       |           assignableValue: positiveAssignableValue
|                               |       |                               |   |                       |           |-- positiveAssignableValue: a
|                               |       |                               |   |                       |               positiveAssignableValue: identifier
|                               |       |                               |   |                       |-- multiplicativeExp: b
|                               |       |                               |   |                           multiplicativeExp: factor
|                               |       |                               |   |                           |-- factor: b
|                               |       |                               |   |                               factor: assignableValue
|                               |       |                               |   |                               |-- assignableValue: b
|                               |       |                               |   |                                   assignableValue: positiveAssignableValue
|                               |       |                               |   |                                   |-- positiveAssignableValue: b
|                               |       |                               |   |                                       positiveAssignableValue: identifier
|                               |       |                               |   |-- factor: d
|                               |       |                               |   |   factor: assignableValue
|                               |       |                               |   |   |-- assignableValue: d
|                               |       |                               |   |       assignableValue: positiveAssignableValue
|                               |       |                               |   |       |-- positiveAssignableValue: d
|                               |       |                               |   |           positiveAssignableValue: identifier
|                               |       |                               |   |-- factor: g
|                               |       |                               |       factor: assignableValue
|                               |       |                               |       |-- assignableValue: g
|                               |       |                               |           assignableValue: positiveAssignableValue
|                               |       |                               |           |-- positiveAssignableValue: g
|                               |       |                               |               positiveAssignableValue: identifier
|                               |       |                               |-- multiplicativeExp: e
|                               |       |                               |   multiplicativeExp: factor
|                               |       |                               |   |-- factor: e
|                               |       |                               |       factor: assignableValue
|                               |       |                               |       |-- assignableValue: e
|                               |       |                               |           assignableValue: positiveAssignableValue
|                               |       |                               |           |-- positiveAssignableValue: e
|                               |       |                               |               positiveAssignableValue: identifier
|                               |       |                               |-- multiplicativeExp: f
|                               |       |                                   multiplicativeExp: factor
|                               |       |                                   |-- factor: f
|                               |       |                                       factor: assignableValue
|                               |       |                                       |-- assignableValue: f
|                               |       |                                           assignableValue: positiveAssignableValue
|                               |       |                                           |-- positiveAssignableValue: f
|                               |       |                                               positiveAssignableValue: identifier
|                               |       |-- additiveExp: c
|                               |           additiveExp: multiplicativeExp
|                               |           |-- multiplicativeExp: c
|                               |               multiplicativeExp: factor
|                               |               |-- factor: c
|                               |                   factor: assignableValue
|                               |                   |-- assignableValue: c
|                               |                       assignableValue: positiveAssignableValue
|                               |                       |-- positiveAssignableValue: c
|                               |                           positiveAssignableValue: identifier
|                               |-- conjunctiveExp: !checkSomeBool()
|                                   conjunctiveExp: comparisonExp
|                                   |-- comparisonExp: !checkSomeBool()
|                                       comparisonExp: additiveExp
|                                       |-- additiveExp: !checkSomeBool()
|                                           additiveExp: factor
|                                           |-- factor: !checkSomeBool()
|                                               factor: assignableValue
|                                               |-- assignableValue: !checkSomeBool()
|                                                   assignableValue: notOper, positiveAssignableValue
|                                                   |-- positiveAssignableValue: checkSomeBool()
|                                                       positiveAssignableValue: functionCall
|-- codeBlock: return True;
    codeBlock: "{", returnStmnt, "}"
    |-- returnStmnt: return True;
        returnStmnt: "return", assignableValue, ";"
        |-- assignableValue: True
            assignableValue: positiveAssignableValue
            |-- positiveAssignableValue: True
                positiveAssignableValue: boolValue
```

## Testowanie

Projekt został napisany w metodologii TDD. Biblioteką wykorzystaną do testów jest `JUnit5`.  
W przypadku leksera testy jednostkowe polegają na poprawnym preparsowaniu każdego tokena. Przygotowane zostały
zarówno testy pozytywne, jak i negatywne (tj. z niepoprawnymi tokenami).  
Testy parsera opierają się na analizie kolejnych Tokenów. Dla każdego pozytywnego testu zdefiniowana została prawidłowa
struktura programu, wynikająca z dostarczonych tokenów, która powinna być wynikiem działania parsera.
Ponadto, przygotowane zostaną również testy akceptacyjne, które będą polegały na analizie przykładowego kodu w
projektowanym języku. Przykład pozytywnego testu akceptacyjnego znajduje się w
sekcji [Przykłady kodu](./code_examples.md).