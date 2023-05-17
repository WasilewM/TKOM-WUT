package parser.utils;

import lexer.ILexer;
import lexer.Position;
import lexer.TokenTypeEnum;
import lexer.tokens.Token;

import java.util.ArrayList;

public class MockedLexer implements ILexer {
    private final ArrayList<Token> tokens;
    private int currentIdx;
    private Position carriagePosition;

    public MockedLexer(ArrayList<Token> tokens) {
        this.tokens = tokens;
        currentIdx = 0;
        carriagePosition = new Position(1, 0);
    }

    @Override
    public Token lexToken() {
        if (currentIdx < tokens.size()) {
            Token currentToken = tokens.get(currentIdx);
            currentIdx++;
            carriagePosition = currentToken.getPosition();
            return currentToken;
        }

        return new Token(carriagePosition, TokenTypeEnum.ETX);
    }
}
