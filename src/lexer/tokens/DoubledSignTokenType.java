package lexer.tokens;

import lexer.TokenTypeEnum;

public class DoubledSignTokenType {
    private final TokenTypeEnum singleSingTokenType;
    private final TokenTypeEnum doubledSingTokenType;

    public DoubledSignTokenType(TokenTypeEnum singleSingTokenType, TokenTypeEnum doubledSingTokenType) {
        this.singleSingTokenType = singleSingTokenType;
        this.doubledSingTokenType = doubledSingTokenType;
    }

    public TokenTypeEnum getTokenTypeWhenSingleSign() {
        return singleSingTokenType;
    }

    public TokenTypeEnum getTokenTypeWhenDoubledSign() {
        return doubledSingTokenType;
    }
}
