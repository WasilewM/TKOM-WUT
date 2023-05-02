import java.util.ArrayList;

public class MockedLexer implements ILexer {
    private final ArrayList<Token> tokens;
    private int currentIdx;

    public MockedLexer(ArrayList<Token> tokens) {
        this.tokens = tokens;
        currentIdx = 0;
    }

    @Override
    public Token lexToken() {
        if (currentIdx < tokens.size()) {
            return tokens.get(currentIdx++);
        }

        return new Token(new Position(1, 1), TokenTypeEnum.ETX);
    }
}
