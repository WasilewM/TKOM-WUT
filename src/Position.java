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
        columnNumber = 1;
    }
}
