package lexer.tokens;

import lexer.Position;
import lexer.TokenTypeEnum;

public class Token {
    private final Position position;

    private final TokenTypeEnum tokenType;

    public Token(Position position, TokenTypeEnum tokenType) {
        this.position = position;
        this.tokenType = tokenType;
    }

    public Position getPosition() {
        return position;
    }

    public TokenTypeEnum getTokenType() {
        return tokenType;
    }

    public Object getValue() {
        return null;
    }
}
