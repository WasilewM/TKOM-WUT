package lexer.tokens;

import lexer.Position;
import lexer.TokenTypeEnum;

public class IntegerToken extends Token {
    private final Integer value;

    public IntegerToken(Integer value, Position position) {
        super(position, TokenTypeEnum.INT_VALUE);
        this.value = value;
    }

    @Override
    public Integer getValue() {
        return value;
    }
}
