import java.io.BufferedInputStream;
import java.io.IOException;

public class Lexer {
    private final BufferedInputStream inputStream;
    private Character currentChar;
    private String newlineConvention;
    private Position position;
    private Token token;

    public Lexer(BufferedInputStream inputStream) {
        this.inputStream = inputStream;
        currentChar = null;
        newlineConvention = null;
        position = new Position(1, 1);
    }

    public BufferedInputStream getInputStream() {
        return inputStream;
    }

    public Character getCurrentChar() {
        return currentChar;
    }

    public String getNewlineConvention() {
        return newlineConvention;
    }

    public Token lexToken() {
        nextChar();
        while (Character.isWhitespace(currentChar)) {
            nextChar();
        }
        if (tryBuildNumber()) {
            return token;
        }

        return null;
    }

    private boolean tryBuildNumber() {
        if (!Character.isDigit(currentChar)) {
            return false;
        }

        int value = currentChar - '0';
        nextChar();
        while (Character.isDigit(currentChar)) {
            value = value * 10 + currentChar - '0';
            nextChar();
        }

        token = new Token(value, position);
        return true;
    }

    private void nextChar() {
        try {
            currentChar = (char) inputStream.read();
        } catch (IOException e) {
            // @TODO
        }
    }
}
