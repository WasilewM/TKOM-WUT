public class OneOrTwoSignsTokenType {
    private final Character firstSign;
    private final Character secondSign;
    private final TokenTypeEnum oneSingTokenType;
    private final TokenTypeEnum twoSingsTokenType;

    public OneOrTwoSignsTokenType(Character firstSign, TokenTypeEnum oneSingTokenType, Character secondSign, TokenTypeEnum twoSingsTokenType) {
        this.firstSign = firstSign;
        this.oneSingTokenType = oneSingTokenType;
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
        return oneSingTokenType;
    }

    public TokenTypeEnum getTokenTypeWhenTwoSigns() {
        return twoSingsTokenType;
    }
}
