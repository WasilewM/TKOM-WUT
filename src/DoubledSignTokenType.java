public class DoubledSignTokenType {
    private final TokenTypeEnum singleSingTokenType;
    private final TokenTypeEnum doubledSingTokenType;

    public DoubledSignTokenType(TokenTypeEnum singleSingTokenType, TokenTypeEnum doubledSingTokenType) {
        this.singleSingTokenType = singleSingTokenType;
        this.doubledSingTokenType = doubledSingTokenType;
    }

    TokenTypeEnum getTokenTypeWhenSingleSign() {
        return singleSingTokenType;
    }

    TokenTypeEnum getTokenTypeWhenDoubledSign() {
        return doubledSingTokenType;
    }
}
