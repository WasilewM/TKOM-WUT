package lexer.tokens;

import lexer.Position;
import lexer.TokenTypeEnum;

public class StringToken extends Token {
    private final String value;

    public StringToken(String value, Position position, TokenTypeEnum tokenType) {
        super(position, tokenType);
        this.value = value;
    }

    @Override
    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return super.toString() + String.format(" with value: %s", value);
    }
}
