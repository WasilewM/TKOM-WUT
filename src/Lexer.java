import java.io.BufferedInputStream;
import java.io.IOException;

public class Lexer {
    private final BufferedInputStream inputStream;
    private Character currentChar;
    private String newlineConvention;
    private Position carriagePosition;
    private Token token;

    public Lexer(BufferedInputStream inputStream) {
        this.inputStream = inputStream;
        currentChar = null;
        newlineConvention = null;
        carriagePosition = new Position(1, 1);
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
            if (currentChar.equals('\n')) {
                carriagePosition.fitLine();
                carriagePosition.returnCarriage();
            }

            if (currentChar.equals(' ')) {
                carriagePosition.moveCarriage();
            }

            nextChar();
        }
        if (tryBuildNumber()
            || tryBuildIdentifier()) {
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
        Position tokenPosition = new Position(carriagePosition);
        while (Character.isDigit(currentChar)) {
            value = value * 10 + (currentChar - '0');
            nextChar();
            carriagePosition.moveCarriage();
        }

        if (currentChar.equals('.')) {
            nextChar();
            carriagePosition.moveCarriage();

            int decimalValue = 0;
            int digitsAfterDecimalPoint = 0;
            while (Character.isDigit(currentChar)) {
                decimalValue = decimalValue * 10 + (currentChar - '0');
                digitsAfterDecimalPoint++;
                nextChar();
                carriagePosition.moveCarriage();
            }

            double doubleValue = value + (decimalValue / Math.pow(10, digitsAfterDecimalPoint));
            token = new Token(doubleValue, tokenPosition);
        }
        else {
            token = new Token(value, tokenPosition);
        }
        return true;
    }

    private boolean tryBuildIdentifier() {
        if (!Character.isLetter(currentChar) && !currentChar.equals('_')) {
            return false;
        }

        StringBuilder identifier = new StringBuilder();
        identifier.append(currentChar);
        Position tokenPosition = new Position(carriagePosition);
        nextChar();
        carriagePosition.moveCarriage();

        while (Character.isLetter(currentChar) || currentChar.equals('_')) {
            identifier.append(currentChar);
            nextChar();
            carriagePosition.moveCarriage();
        }
        token = new Token(identifier.toString(), tokenPosition);
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
