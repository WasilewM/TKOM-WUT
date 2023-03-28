# 23L-TKOM
## Autor: Mateusz Wasilewski

## Temat projektu
Język umożliwiający opis punktów i odcinków na płaszczyźnie. Punkt i odcinek (zbudowany z punktów) są wbudowanymi typami języka. 
Z odcinków można budować figury geometryczne. Kolekcja figur tworzy scenę wyświetlaną na ekranie.

## Założenia
1. Projekt zostanie zrealizowany w języku Java.
2. Projektowany język będzie typowany silnie i statycznie.  
3. W języku dostępne będą następujące typy danych:  
  - Numeryczne: int / double  
  - Tekstowy: String  
  - Wbudowane typy obiektowe: Point, Section, Figure, Scene (opis tych typów przedstawiony jest niżej)  
  - Kolekcja - lista: List  
4. Komentarze oznacza się znakami `//`. Komentarze obowiązują do końca linii.  

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

## Przykład kodu
```
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