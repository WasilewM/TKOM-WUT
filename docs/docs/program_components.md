# Dokumentacja struktury programu

## Definicje wbudowanych typów obiektowych

Typ DISPLAY_TYPE użyty poniżej zostanie skonkretyzowany podczas implementacji wyświetlania obiektów.

### Wyświetlanie

Obiekty (punkty, odcinki, figury) będą wyświetlane przy wykorzystaniu biblioteki `awt` oraz `swing`:

- W bibliotece `awt` znajdują się takie metody jak `drawPolygon(int[] x, int[] y, nPoints)`
  oraz `drawLine(int x1, int y1, int x2, int y2)` przy pomocy których możliwe będzie rysowanie krzywych z odcinków oraz
  na podstawie punktów.
- Wyświetlany obrazek umieszczany będzie w obiekcie klasy `JFrame` z biblioteki `swing`.

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

### List

Obiekt typu klasy `List` może przyjmować wszystkie typy obiektowe zdefiniowane w języku z wyłączeniem `List`, tj.: `Int`
, `Double`, `String`, `Point`, `Section`, `Figure`, `Scene`, `Bool`. Zagnieżdżanie list nie będzie dozwolone.

```
public class List {
	public ArrayList<Object> list = new ArrayList<>();
	
	public List() {
	}
	
	public void add(Object o) {
        list.add(o);
	}
	
	public Object get(int i) {
	    return list[i];
	}
	
	public void pop() {
	    list.remove(list.size() - 1);
	}
}
```

### Figure

Figura będzie krzywą łamaną, tj. nie będzie konieczności przekazania do niej takich odcinków, aby stworzyła figurę
zamkniętą. Takie podejście wydaje się logiczne z racji na możliwość dodawania kolejnych odcinków do figury. Należy
jednak pamiętać o tym, że kolejny dodawany odcinek powinien się łączyć z ostatnio dodanym odcinkiem.

```
public class Figure {
    public List sections = List();
	
	public List getSections() {
		return sections;
	}
	
	public void add(Section newSection) {
	    if ((newSection.left == sections[section.length() - 1].left)
            || (newSection.right == sections[section.length() - 1]).left)
            || (newSection.left == sections[section.length() - 1]).right)
            || (newSection.right == sections[section.length() - 1]).right)) {
		    
		    sections.add(newSection);
	    }
	    else {
            throw new IllegalArgumentException("Sections must join together to form figure. Dangling section cannot be added.");
	    }
	}
		
	public void pop() {
		sections.pop();
	}

	public DISPLAY_TYPE display() {
	}
}
```

### Scene

```
public class Scene {
    public List<Figure> figures = List();
	
	public List getFigures() {
		return figures;
	}
	
	public void add(Figure newFigure) {
		figures.add(newFigure);
	}
		
	public void pop() {
		figures.pop();
	}

	public DISPLAY_TYPE display() {
	}
}
```