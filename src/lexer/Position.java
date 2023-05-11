package lexer;

public class Position {
    private int lineNumber;
    private int columnNumber;

    public Position(int lineNumber, int columnNumber) {
        this.lineNumber = lineNumber;
        this.columnNumber = columnNumber;
    }

    public Position(Position otherPosition) {
        this.lineNumber = otherPosition.getLineNumber();
        this.columnNumber = otherPosition.getColumnNumber();
    }


    public int getLineNumber() {
        return lineNumber;
    }

    public int getColumnNumber() {
        return columnNumber;
    }

    public void fitLine() {
        lineNumber++;
    }

    public void moveCarriage() {
        columnNumber++;
    }

    public void returnCarriage() {
        columnNumber = 0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (!o.getClass().equals(this.getClass())) {
            return false;
        }

        return this.lineNumber == ((Position) o).lineNumber
                && this.columnNumber == ((Position) o).columnNumber;
    }

    @Override
    public String toString() {
        return String.format("Position: <line: %d, column %d>", this.getLineNumber(), this.getColumnNumber());
    }
}
