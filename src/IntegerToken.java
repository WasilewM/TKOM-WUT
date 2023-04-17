public class IntegerToken extends Token {
    public IntegerToken(Integer value, Position position) {
        super(value, position, TokenTypeEnum.INT_VALUE);
    }
}
