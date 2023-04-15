import java.io.BufferedInputStream;
import java.io.IOException;
import java.util.HashMap;

public class Lexer {
    private final BufferedInputStream inputStream;
    private Character currentChar;
    private final String newlineConvention;
    private final Position carriagePosition;
    private Token token;
    private final HashMap<String, TokenTypeEnum> keywordTokens = new HashMap<>();

    public Lexer(BufferedInputStream inputStream) {
        this.inputStream = inputStream;
        currentChar = null;
        newlineConvention = null;
        carriagePosition = new Position(1, 0);
        initKeywordTokens();
    }

    private void initKeywordTokens() {
        keywordTokens.put("Int", TokenTypeEnum.INT_KEYWORD);
        keywordTokens.put("Double", TokenTypeEnum.DOUBLE_KEYWORD);
        keywordTokens.put("String", TokenTypeEnum.STRING_KEYWORD);
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
        if (currentChar == null) {
            nextChar();
        }

        while (Character.isWhitespace(currentChar)) {
            nextChar();
        }
        if (tryBuildNumber()
            || tryBuildIdentifierOrKeyword()
            || tryBuildComment()
            || tryBuildArithmeticOperator()
            || tryBuildAssignmentOrEqualOperator()
            || tryBuildNotEqualOrNegationOperator()
            || tryBuildComparisonOperator()
            || tryBuildLogicalOperator()) {
            return token;
        }

        return null;
    }

    private boolean tryBuildNumber() {
        if (!Character.isDigit(currentChar)) {
            return false;
        }

        int value = currentChar - '0';
        Position tokenPosition = new Position(carriagePosition);
        nextChar();
        while (Character.isDigit(currentChar)) {
            value = value * 10 + (currentChar - '0');
            nextChar();
        }

        if (currentChar.equals('.')) {
            nextChar();

            int decimalValue = 0;
            int digitsAfterDecimalPoint = 0;
            while (Character.isDigit(currentChar)) {
                decimalValue = decimalValue * 10 + (currentChar - '0');
                digitsAfterDecimalPoint++;
                nextChar();
            }

            double doubleValue = value + (decimalValue / Math.pow(10, digitsAfterDecimalPoint));
            token = new DoubleToken(doubleValue, tokenPosition);
        }
        else {
            token = new IntegerToken(value, tokenPosition);
        }
        return true;
    }

    private boolean tryBuildIdentifierOrKeyword() {
        if (!Character.isLetter(currentChar) && !currentChar.equals('_')) {
            return false;
        }

        StringBuilder identifier = new StringBuilder();
        identifier.append(currentChar);
        Position tokenPosition = new Position(carriagePosition);
        nextChar();

        while (Character.isLetter(currentChar) || currentChar.equals('_')) {
            identifier.append(currentChar);
            nextChar();
        }

        if (this.keywordTokens.containsKey(identifier.toString())) {
            token = new StringToken(null, tokenPosition, this.keywordTokens.get(identifier.toString()));
        }
        else {
            token = new StringToken(identifier.toString(), tokenPosition, TokenTypeEnum.STRING);
        }

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
        }

        if (currentChar.equals('\n')) {
            carriagePosition.fitLine();
            carriagePosition.returnCarriage();
        }

        token = new StringToken(comment.toString(), tokenPosition, TokenTypeEnum.COMMENT);
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
        token = new StringToken(null, tokenPosition, TokenTypeEnum.ADDITION_OPERATOR);
        return true;
    }

    private boolean tryBuildSubtractionOperator() {
        if (!currentChar.equals('-')) {
            return false;
        }

        Position tokenPosition = new Position(carriagePosition);
        token = new StringToken(null, tokenPosition, TokenTypeEnum.SUBTRACTION_OPERATOR);
        return true;
    }

    private boolean tryBuildMultiplicationOperator() {
        if (!currentChar.equals('*')) {
            return false;
        }

        Position tokenPosition = new Position(carriagePosition);
        token = new StringToken(null, tokenPosition, TokenTypeEnum.MULTIPLICATION_OPERATOR);
        return true;
    }

    private boolean tryBuildDivisionOrDiscreteDivisionOperator() {
        if (!currentChar.equals('/')) {
            return false;
        }

        Position tokenPosition = new Position(carriagePosition);
        nextChar();

        if (currentChar.equals('/')) {
            nextChar();
            token = new StringToken(null, tokenPosition, TokenTypeEnum.DISCRETE_DIVISION_OPERATOR);
        } else {
            token = new StringToken(null, tokenPosition, TokenTypeEnum.DIVISION_OPERATOR);
        }

        return true;
    }

    private boolean tryBuildAssignmentOrEqualOperator() {
        if (!currentChar.equals('=')) {
            return false;
        }

        Position tokenPosition = new Position(carriagePosition);
        nextChar();

        if (currentChar.equals('=')) {
            nextChar();
            token = new StringToken(null, tokenPosition, TokenTypeEnum.EQUAL_OPERATOR);
        }
        else {
            token = new StringToken(null, tokenPosition, TokenTypeEnum.ASSIGNMENT_OPERATOR);
        }

        return true;
    }

    private boolean tryBuildComparisonOperator() {
        return tryBuildLessThanOrLessOrEqualOperator()
                || tryBuildGreaterThanOrLessOrEqualOperator();
    }

    private boolean tryBuildNotEqualOrNegationOperator() {
        if (!currentChar.equals('!')) {
            return false;
        }

        Position tokenPosition = new Position(carriagePosition);
        nextChar();

        if (currentChar.equals('=')) {
            nextChar();
            token = new StringToken(null, tokenPosition, TokenTypeEnum.NOT_EQUAL_OPERATOR);
        }
        else {
            token = new StringToken(null, tokenPosition, TokenTypeEnum.NEGATION_OPERATOR);
        }

        return true;
    }

    private boolean tryBuildLessThanOrLessOrEqualOperator() {
        if (!currentChar.equals('<')) {
            return false;
        }

        Position tokenPosition = new Position(carriagePosition);
        nextChar();

        if (currentChar.equals('=')) {
            nextChar();
            token = new StringToken(null, tokenPosition, TokenTypeEnum.LESS_OR_EQUAL_OPERATOR);
            return true;
        }
        else {
            token = new StringToken(null, tokenPosition, TokenTypeEnum.LESS_THAN_OPERATOR);
        }

        return true;
    }

    private boolean tryBuildGreaterThanOrLessOrEqualOperator() {
        if (!currentChar.equals('>')) {
            return false;
        }

        Position tokenPosition = new Position(carriagePosition);
        nextChar();

        if (currentChar.equals('=')) {
            nextChar();
            token = new StringToken(null, tokenPosition, TokenTypeEnum.GREATER_OR_EQUAL_OPERATOR);
            return true;
        }
        else {
            token = new StringToken(null, tokenPosition, TokenTypeEnum.GREATER_THAN_OPERATOR);
        }

        return true;
    }

    private boolean tryBuildLogicalOperator() {
        return tryBuildAndOperator()
                || tryBuildOrOperator();
    }

    private boolean tryBuildAndOperator() {
        if (!currentChar.equals('&')) {
            return false;
        }

        Position tokenPosition = new Position(carriagePosition);
        nextChar();

        if (currentChar.equals('&')) {
            nextChar();
            token = new StringToken(null, tokenPosition, TokenTypeEnum.AND_OPERATOR);
            return true;
        }

        return false;
    }

    private boolean tryBuildOrOperator() {
        if (!currentChar.equals('|')) {
            return false;
        }

        Position tokenPosition = new Position(carriagePosition);
        nextChar();

        if (currentChar.equals('|')) {
            nextChar();
            token = new StringToken(null, tokenPosition, TokenTypeEnum.OR_OPERATOR);
            return true;
        }

        return false;
    }

    private void nextChar() {
        try {
            currentChar = (char) inputStream.read();
            updateCarriagePosition();
        } catch (IOException e) {
            // @TODO
        }
    }

    private void updateCarriagePosition() {
        if (currentChar.equals('\n')) {
            carriagePosition.fitLine();
            carriagePosition.returnCarriage();
        } else {
            carriagePosition.moveCarriage();
        }
    }
}
