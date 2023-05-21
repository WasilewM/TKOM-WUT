package visitor;

import lexer.ILexer;
import lexer.TokenTypeEnum;
import lexer.tokens.Token;

public class CommentFilter implements ILexer {
    private final ILexer lexer;

    public CommentFilter(ILexer lexer) {
        this.lexer = lexer;
    }

    @Override
    public Token lexToken() {
        Token token = lexer.lexToken();

        while (token.getTokenType() == TokenTypeEnum.COMMENT && token.getTokenType() != TokenTypeEnum.ETX) {
            token = lexer.lexToken();
        }

        return token;
    }
}
