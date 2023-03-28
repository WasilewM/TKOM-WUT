# 23L-TKOM
## Autor: Mateusz Wasilewski

## Temat projektu
Język umożliwiający opis punktów i odcinków na płaszczyźnie. Punkt i odcinek (zbudowany z punktów) są wbudowanymi typami języka. Z odcinków można budować figury geometryczne. Kolekcja figur tworzy scenę wyświetlaną na ekranie.

## Założenia
1. Projekt zostanie zrealizowany w języku Java.
1. Projektowany język będzie typowany silnie i statycznie.  
1. Za plik napisany w projektowanym języku uznawane będą pliki z rozszerzeniem `.surface`.
1. W języku dostępne będą następujące typy danych:
   1. Numeryczne: int / double  
   1. Tekstowy: String  
   1. Wbudowane typy obiektowe: Point, Section, Figure, Scene (opis tych typów przedstawiony jest niżej)  
   1. Kolekcja - lista: List. Do kolejnych elementów listy można odwoływać się poprzez indeksy: `someList[i]`. Ponadto. type `List` posiada wbudowane funkcje `add` do dodania elementu na koniec listy oraz `pop` do usunięcia ostatniego elementu z listy. 
   1. Boolean: bool
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
   1. `&&` - operator logiczny `i`
   1. `||` - operator logiczny `lub`
1. Istnieć będzie możliwość definiowania funkcji. Ponadto, będzie możliwość definiowania funkcji rekurencyjnych.

## Definicje wbudowanych typów obiektowych
@TODO: change DISPLAY_TYPE  
### Point
```
public class Point {
	private double x;
	private double y;
	private String color = #ffffff;
	
	public Point(double x, double y) {
		this.x = x;
		this.y = y;
	}
	
	public double getX() {
		return x;
	}
	
	public double getY() {
		return y;
	}
	
	public void setX(double newX) {
		x = newX;
	}
	
	public void setY(double newY) {
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
	
	public double getLeft() {
		return left;
	}
	
	public double getRight() {
		return right;
	}
	
	public void setLeft(double newLeft) {
		left = newLeft;
	}
	
	public void setRight(double newRigtht) {
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
@TODO

### Przykład kodu
```
List findMeassureCenter(List n) {
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

int main() {
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