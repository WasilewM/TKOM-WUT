public class Token {
    private final Object value;
    private final Position position;

    public Token(Object value, Position position) {
        this.value = value;
        this.position = position;
    }

    public Object getValue() {
        return value;
    }

    public Position getPosition() {
        return position;
    }
}
