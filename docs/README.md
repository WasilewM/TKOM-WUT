# 23L-TKOM
## Autor: Mateusz Wasilewski

## Temat projektu
Język umożliwiający opis punktów i odcinków na płaszczyźnie. Punkt i odcinek (zbudowany z punktów) są wbudowanymi typami języka. Z odcinków można budować figury geometryczne. Kolekcja figur tworzy scenę wyświetlaną na ekranie.

## Założenia
1. Projekt zostanie zrealizowany w języku Java.
1. Projektowany język będzie typowany silnie i statycznie.  
1. Za plik napisany w projektowanym języku uznawane będą pliki z rozszerzeniem `.surface`.
1. W języku dostępne będą następujące typy danych:
   1. Numeryczne: `Int`, `Double`  
   1. Tekstowy: `String`  
   1. Wbudowane typy obiektowe: `Point`, `Section`, `Figure`, `Scene` (opis tych typów przedstawiony jest niżej)  
   1. Kolekcja - lista: List. Do kolejnych elementów listy można odwoływać się poprzez indeksy: `someList[i]`. Ponadto. type `List` posiada wbudowane funkcje `add` do dodania elementu na koniec listy oraz `pop` do usunięcia ostatniego elementu z listy. 
   1. Typ boole'owski: Bool
1. Komentarze oznacza się znakami `#`. Komentarze obowiązują do końca linii.  
1. Pętla realizowana będzie przez instrukcję `while`
1. Sprawdzenie warunków logicznych będzie można zapisać w bloku `if`, `elseif`, `else`.
1. Znak końca linii może być reprezentowany przez `\r`, `\n`, `\r\n` lub `\n\r`. Przyjmowana będzie konwencja rozpoznana na końcu pierwszej linii pliku. Zastosowanie dwóch lub więcej różnych konwencji w jednym pliku będzie powodowało generowanie komunikatów o błędach.
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
1. Istnieć będzie możliwość definiowania funkcji. Nazwa funkcji musi być reprezentowalna przez następujące wyrażenie regularne `[a-zA-Z][-a-zA-Z0-9]*`.
1. Istnieć będzie możliwość definiowania zmiennych. Nazwa zmiennej musi być reprezentowalna przez następujące wyrażenie regularne `[a-zA-Z][-a-zA-Z0-9]*`.
1. Słowa kluczowe języka i znaki specjalne:
   1. Typy danych: `Int`, `Double`, `String`, `Point`, `Section`, `Figure`, `Scene`, `Bool`, `List`
   1. Instrukcje sterujące: `if`, `elseif`, `else`, `while`
   1. Dostępne operatory arytmetyczne
   1. Dostępne operatory logiczne
   1. Znak rozpoczęcia komentarza
   1. `main`
   1. `return`
   1. `void` - słowo kluczowe oznaczające brak zwracania danych z funkcji. W przypadku deklaracji funkcji `void` a następnie próbie zwrócenia jakichś danych z wykorzystaniem słowa kluczowego `return` wygenerowany zostanie błąd.
   1. Znaki otwierające i zamykające nawiasy: `(`, `)`, `[`, `]`, `{`, `}`
   1. Znak końca instrukcji `;`
   1. Operator odwołania do właściwości i metod obiekty: `.`
   1. Operator przypisania: `=`
   1. `,`
   1. nazwy wszystkich metod zapisanych poniżej w definicji typów wbudowanych `Point`, `Section`, `Figure` oraz `Scene`

## Definicje wbudowanych typów obiektowych
Typ DISPLAY_TYPE użyty poniżej zostanie skonkretyzowany podczas implementacji wyświetlania obiektów.  
### Point
```
public class Point {
	private Double x;
	private Double y;
	private String color = #ffffff;
	
	public Point(Double x, Double y) {
		this.x = x;
		this.y = y;
	}
	
	public Double getX() {
		return x;
	}
	
	public Double getY() {
		return y;
	}
	
	public void setX(Double newX) {
		x = newX;
	}
	
	public void setY(Double newY) {
		y = newY;
	}
	
	public DISPLAY_TYPE display() {
	}
	
	public void setColor(String newColor) {
		color = newColor;
	}
	
	public String getColor() {
		return color;
	}
}
```

### Section
```
public class Section {
	private Point left;
	private Point right;
	private String color = #ffffff;
	
	public Section(Point left, Point right) {
		this.left = left;
		this.right = right;
	}
	
	public Double getLeft() {
		return left;
	}
	
	public Double getRight() {
		return right;
	}
	
	public void setLeft(Double newLeft) {
		left = newLeft;
	}
	
	public void setRight(Double newRigtht) {
		right = newRight;
	}
	
	public DISPLAY_TYPE display() {
	}
	
	public void setColor(String newColor) {
		color = newColor;
	}
	
	public String getColor() {
		return color;
	}
}
```

### Figure
```
public class Figure {
    public ArrayList<Section> sections = new ArrayList<>();
	
	public ArrayList<Section> getSections() {
		return sections;
	}
	
	public void add(Section newSection) {
		sections.add(newSection);
	}
		
	public void remove(Section newSection) {
		sections.remove(newSection);
	}

	public DISPLAY_TYPE display() {
	}
}
```

### Scene
```
public class Scene {
    public ArrayList<Figure> figures = new ArrayList<>();
	
	public ArrayList<Figure> getFigures() {
		return figures;
	}
	
	public void add(Figure newFigure) {
		figures.add(newFigure);
	}
		
	public void remove(Figure newFigure) {
		figures.remove(newFigure);
	}

	public DISPLAY_TYPE display() {
	}
}
```

## Gramatyka
### EBNF
```
program              = { functionDef }, { functionCall }
functionDef          = functionType, identifier, "(", { parameters }, ")", codeBlock
functionType         = "void"
                        | dataType
parameters           = { parameter }
parameter            = dataType, identifier
codeBlock            = "{", { ifBlock | whileBlock | functionCall | assignmentExp | returnExp | ";" }, "}"
ifBlock              = "if", "(", condition, ")", "{", codeBlock, "}", { elseIfBlock }, [ elseBlock ]
elseIfBlock          = "elseif", "(", condition, ")", "{", codeBlock, "}"
elseBlock            = "else", "(", condition, ")", "{", codeBlock, "}"
whileBlock           = "while", "(", condition, ")", "{", codeBlock, "}"
condition            = conditionAndExp, { orOper, conditionAndExp }
conditionAndExp      = conditionComparExp, { andOper, conditionComparExp }
conditionComparExp   = [notOper], singleCondition, [ comparisonOper, singleCondition ]
singleCondition      = identifier
                        |  condtition
assignmentExp        = dataType, identifier, assignmentOper, assignedValue, ";"
assignedValue        = assignableValue
                        | expression
expression           = multiplicativeExp, { additiveOper, multiplicativeExp }
multiplicativeExp    = parenthesesExp, { mulitplicativeOper, parenthesesExp }
perethesesExp        = "(", assignableValue, ")"
returnExp            = "return", assignableValue, ";"


assignableValue      = literal
                        | integer
                        | double
                        | functionCall
functionCall         = identifier, parameters, ";"
identifier           = letter { digit | literalSign }
double               = integer, [".", integer]
integer              = "0"
                        | notZeroDigit
literalSign          = "_"
                        | letter


dataType             = "Int"
                        | "Double"
                        | "String"
                        | "Point"
                        | "Section"
                        | "Scene"
                        | "Bool"
logicalOper          = andOper
                        | orOper
                        | notOper
comparisonOper       = "=="
                        | "!="
                        | "<"
                        | "<="
                        | ">"
                        | ">="
andOper              = "&&"
orOper               = "||"
notOper              = "!"
additivOper          = "+"
                        | "-"
multiplicativOper    = "*"
                        | "/"
                        | "//"
assignmentOper       = "="
comment              = "#"
notZeroDigit         = "1".."9"
letter               = "a".."z"
                        | "A".."Z"
```

### Przykład kodu
```
Int doSomeMath(Int n) {
   # condition bellow could be simplified by it has been written this way on purpose,
   # to show how logical operators will work
   if (n < 0 || n == 0 || n == 1) {
      return -1;         
   }
    
   Int i = 2;
   Int result = 0;
   while (i < n) {
      result = result - i * (i + n - i);
      i = i + 3;
   }
   
   return result;
}

List mergeSort(List n) {
    if (n.length() == 1) {
        return n;
    }
    
    # get a list of items from List n from index 0 to index n.length() // 2 (exclusively)
    List leftHalf = List();
    int l = 0;
    while (l < (n.length() // 2)) {
        leftHalf.add(n[l]);
        l += 1;
    }
    
    # get a list of items from List n from index n.length() // 2 (inclusively) to the last element
    List rightHalf = List();
    int r = n.length() // 2;
    while (r < n.length()) {
        rightHalf.add(n[r]);
        r += 1;
    }
    
    List sortedLeftHalf = mergeSort(leftHalf);
    List sortedRightHalf = mergeSort(rightHalf);
    
    int i = 0;
    int j = 0;
    List sortedList = List();
    
    while (i < sortedLeftHalf.length() && j < sortedRightHalf.length()) {
        if (i == sortedLeftHalf.length() && j < sortedRightHalf.length()) {
            sortedList.add(sortedRightHalf[j]);
            j += 1;
        }
        elseif (i < sortedLeftHalf.length() && j == sortedRightHalf.length()) {
            sortedList.add(sortedLeftHalf[i]);
            i += 1;
        }
        elseif (sortedLeftHalf[i] < sortedRightHalf[j]) {
            sortedList.add(sortedLeftHalf[i]);
            i += 1;
        }
        else {
            sortedList.add(sortedRightHalf[j]);
            j += 1;
        }
    }
    
    return sortedList;
}

Int main() {
	Point a = Point(1.01, 2.20);
	Point b = Point(3.03, 7.70);
	Point c = Point(11.11, 19.09);
	
	Section k = Section(a, b);
	Section l = Section(b, c);
	Section m = Section(c, a);
	
	List sections = List();
	sections.add(k);
	sections.add(l);
	sections.add(m);
	
	Scene s1 = Scene()
	s1.add(k);
	s2.add(l);
	s3.add(m);
}
```

## Obsługa błędów
Poniżej przedstawiony jest błędy kod:  
```
Int main() {
	Point a = Point("x", "y");  # konstruktor klasy Point wymaga dwóch wartości Double 
}
```
W rezultacie powyższy błędny kod powinien wygenerować następujący błąd:
```
Error in <line: 2, col: 21>: Double value expected
```

## Wyświetlanie
Obiekty (punkty, odcinki, figury) będą wyświetlane przy wykorzystaniu biblioteki `awt` oraz `swing`:
- W bibliotece `awt` znajdują się takie metody jak `drawPolygon(int[] x, int[] y, nPoints)` oraz `drawLine(int x1, int y1, int x2, int y2)` przy pomocy których możliwe będzie rysowanie krzywych z odcinków oraz na podstawie punktów.
- Wyświetlany obrazek umieszczany będzie w obiekcie klasy `JFrame` z biblioteki `swing`.