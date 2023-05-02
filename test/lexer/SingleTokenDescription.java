import lexer.TokenTypeEnum;

public class SingleTokenDescription {
    private final TokenTypeEnum tokenType;
    private final Object value;
    private final int lineNumber;
    private final int columnNumber;

    public SingleTokenDescription(TokenTypeEnum tokenType, Object value, int lineNumber, int columnNumber) {
        this.tokenType = tokenType;
        this.value = value;
        this.lineNumber = lineNumber;
        this.columnNumber = columnNumber;
    }

    public SingleTokenDescription(TokenTypeEnum tokenType, int lineNumber, int columnNumber) {
        this.tokenType = tokenType;
        this.value = null;
        this.lineNumber = lineNumber;
        this.columnNumber = columnNumber;
    }

    public TokenTypeEnum getTokenType() {
        return tokenType;
    }

    public Object getValue() {
        return value;
    }

    public int getLineNumber() {
        return lineNumber;
    }

    public int getColumnNumber() {
        return columnNumber;
    }
}
