public class ComplexSignTokenType {
    private final Character firstSign;
    private final Character secondSign;
    private final TokenTypeEnum oneSignTokenType;
    private final TokenTypeEnum twoSingsTokenType;

    public ComplexSignTokenType(Character firstSign, TokenTypeEnum oneSingTokenType, Character secondSign, TokenTypeEnum twoSingsTokenType) {
        this.firstSign = firstSign;
        this.oneSignTokenType = oneSingTokenType;
        this.secondSign = secondSign;
        this.twoSingsTokenType = twoSingsTokenType;
    }

    public Character getFirstSign() {
        return firstSign;
    }

    public Character getSecondSign() {
        return secondSign;
    }

    public TokenTypeEnum getTokenTypeWhenOneSign() {
        return oneSignTokenType;
    }

    public TokenTypeEnum getTokenTypeWhenTwoSigns() {
        return twoSingsTokenType;
    }
}
