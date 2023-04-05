import java.io.BufferedInputStream;
import java.io.IOException;

public class Lexer {
    private final BufferedInputStream inputStream;
    private Character currentChar;
    private final String newlineConvention;
    private final Position carriagePosition;
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
            || tryBuildIdentifier()
            || tryBuildComment()
            || tryBuildArithmeticOperator()) {
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
            token = new DoubleToken(doubleValue, tokenPosition);
        }
        else {
            token = new IntegerToken(value, tokenPosition);
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
        token = new StringToken(identifier.toString(), tokenPosition);
        return true;
    }

    private boolean tryBuildComment() {
        if (!currentChar.equals('#')) {
            return false;
        }
        StringBuilder comment = new StringBuilder();
        comment.append(currentChar);
        Position tokenPosition = new Position(carriagePosition);
        nextChar();

        while (!currentChar.equals('\n') && !currentChar.equals((char) (-1))) {
            comment.append(currentChar);
            nextChar();
            carriagePosition.moveCarriage();
        }

        if (currentChar.equals('\n')) {
            carriagePosition.fitLine();
            carriagePosition.returnCarriage();
        }

        token = new CommentToken(comment.toString(), tokenPosition);
        return true;
    }


    private boolean tryBuildArithmeticOperator() {
        return tryBuildAdditionOperator()
                || tryBuildSubtractionOperator()
                || tryBuildMultiplicationOperator()
                || tryBuildDivisionOrDiscreteDivisionOperator();
    }

    private boolean tryBuildAdditionOperator() {
        if (!currentChar.equals('+')) {
            return false;
        }

        Position tokenPosition = new Position(carriagePosition);
        token = new AdditionOperatorToken(currentChar.toString(), tokenPosition);
        return true;
    }

    private boolean tryBuildSubtractionOperator() {
        if (!currentChar.equals('-')) {
            return false;
        }

        Position tokenPosition = new Position(carriagePosition);
        token = new SubtractionOperatorToken(currentChar.toString(), tokenPosition);
        return true;
    }

    private boolean tryBuildMultiplicationOperator() {
        if (!currentChar.equals('*')) {
            return false;
        }

        Position tokenPosition = new Position(carriagePosition);
        token = new MultiplicationOperatorToken(currentChar.toString(), tokenPosition);
        return true;
    }

    private boolean tryBuildDivisionOrDiscreteDivisionOperator() {
        if (!currentChar.equals('/')) {
            return false;
        }

        StringBuilder operator = new StringBuilder();
        operator.append(currentChar);
        Position tokenPosition = new Position(carriagePosition);
        nextChar();

        if (currentChar.equals('/')) {
            operator.append(currentChar);
            nextChar();
            token = new DiscreteDivisionOperatorToken(operator.toString(), tokenPosition);
        } else {
            token = new DivisionOperatorToken(operator.toString(), tokenPosition);
        }

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
