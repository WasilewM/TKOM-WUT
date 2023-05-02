package lexer.tokens;

import lexer.Position;
import lexer.TokenTypeEnum;

public class DoubleToken extends Token {
    private final Double value;
    public DoubleToken(Double value, Position position) {
        super(position, TokenTypeEnum.DOUBLE_VALUE);
        this.value = value;
    }

    @Override
    public Double getValue() {
        return value;
    }
}
