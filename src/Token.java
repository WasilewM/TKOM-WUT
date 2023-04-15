public class Token {
    private final Object value;
    private final Position position;

    private final TokenTypeEnum tokenType;

    public Token(Object value, Position position, TokenTypeEnum tokenType) {
        this.value = value;
        this.position = position;
        this.tokenType = tokenType;
    }

    public Object getValue() {
        return value;
    }

    public Position getPosition() {
        return position;
    }

    public TokenTypeEnum getTokenType() {
        return tokenType;
    }
}
