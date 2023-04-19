import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class Lexer {
    private final BufferedReader bufferedReader;
    private Character currentChar = null;
    private int stringMaxLength = 1000;
    private int identifierMaxLength = 1000;
    private int maxInt = Integer.MAX_VALUE;
    private double maxDouble = Double.MAX_VALUE;
    private final Position carriagePosition;
    private Token token;
    private final HashMap<String, TokenTypeEnum> keywordTokens = new HashMap<>();
    private final HashMap<Character, TokenTypeEnum> onlySingleSignTokens = new HashMap<>();
    private final HashMap<Character, DoubledSignTokenType> singleOrDoubledSignTokens = new HashMap<>();
    private final HashMap<Character, DoubledSignTokenType> onlyDoubledSingTokens = new HashMap<>();
    private final ArrayList<OneOrTwoSignsTokenType> oneOrTwoSingsTokens = new ArrayList<>();

    public Lexer(BufferedReader bufferedReader) {
        this.bufferedReader = bufferedReader;
        carriagePosition = new Position(1, 0);
        initKeywordTokens();
        initOnlySingleSignTokens();
        initSingleOrDoubledSignTokens();
        initOnlyDoubledSignTokens();
        initOneOrTwoSignsTokens();
    }

    private void initKeywordTokens() {
        keywordTokens.put("Int", TokenTypeEnum.INT_KEYWORD);
        keywordTokens.put("Double", TokenTypeEnum.DOUBLE_KEYWORD);
        keywordTokens.put("String", TokenTypeEnum.STRING_KEYWORD);
        keywordTokens.put("Point", TokenTypeEnum.POINT_KEYWORD);
        keywordTokens.put("Section", TokenTypeEnum.SECTION_KEYWORD);
        keywordTokens.put("Figure", TokenTypeEnum.FIGURE_KEYWORD);
        keywordTokens.put("Scene", TokenTypeEnum.SCENE_KEYWORD);
        keywordTokens.put("Bool", TokenTypeEnum.BOOL_KEYWORD);
        keywordTokens.put("True", TokenTypeEnum.BOOL_TRUE_VALUE_KEYWORD);
        keywordTokens.put("False", TokenTypeEnum.BOOL_FALSE_VALUE_KEYWORD);
        keywordTokens.put("List", TokenTypeEnum.LIST_KEYWORD);
        keywordTokens.put("while", TokenTypeEnum.WHILE_KEYWORD);
        keywordTokens.put("if", TokenTypeEnum.IF_KEYWORD);
        keywordTokens.put("elseif", TokenTypeEnum.ELSE_IF_KEYWORD);
        keywordTokens.put("else", TokenTypeEnum.ELSE_KEYWORD);
        keywordTokens.put("return", TokenTypeEnum.RETURN_KEYWORD);
        keywordTokens.put("void", TokenTypeEnum.VOID_KEYWORD);
    }

    private void initOnlySingleSignTokens() {
        onlySingleSignTokens.put(';', TokenTypeEnum.SEMICOLON);
        onlySingleSignTokens.put(',', TokenTypeEnum.COMMA);
        onlySingleSignTokens.put('(', TokenTypeEnum.LEFT_BRACKET);
        onlySingleSignTokens.put(')', TokenTypeEnum.RIGHT_BRACKET);
        onlySingleSignTokens.put('[', TokenTypeEnum.LEFT_SQUARE_BRACKET);
        onlySingleSignTokens.put(']', TokenTypeEnum.RIGHT_SQUARE_BRACKET);
        onlySingleSignTokens.put('{', TokenTypeEnum.LEFT_CURLY_BRACKET);
        onlySingleSignTokens.put('}', TokenTypeEnum.RIGHT_CURLY_BRACKET);
        onlySingleSignTokens.put('.', TokenTypeEnum.DOT);

        // Arithmetic Operators
        onlySingleSignTokens.put('+', TokenTypeEnum.ADDITION_OPERATOR);
        onlySingleSignTokens.put('-', TokenTypeEnum.SUBTRACTION_OPERATOR);
        onlySingleSignTokens.put('*', TokenTypeEnum.MULTIPLICATION_OPERATOR);
    }

    private void initSingleOrDoubledSignTokens() {
        singleOrDoubledSignTokens.put('=', new DoubledSignTokenType(TokenTypeEnum.ASSIGNMENT_OPERATOR, TokenTypeEnum.EQUAL_OPERATOR));
        singleOrDoubledSignTokens.put('/', new DoubledSignTokenType(TokenTypeEnum.DIVISION_OPERATOR, TokenTypeEnum.DISCRETE_DIVISION_OPERATOR));
    }

    private void initOnlyDoubledSignTokens() {
        onlyDoubledSingTokens.put('&', new DoubledSignTokenType(TokenTypeEnum.UNKNOWN_CHAR_ERROR, TokenTypeEnum.AND_OPERATOR));
        onlyDoubledSingTokens.put('|', new DoubledSignTokenType(TokenTypeEnum.UNKNOWN_CHAR_ERROR, TokenTypeEnum.OR_OPERATOR));
    }

    private void initOneOrTwoSignsTokens() {
        oneOrTwoSingsTokens.add(new OneOrTwoSignsTokenType('<', TokenTypeEnum.LESS_THAN_OPERATOR, '=', TokenTypeEnum.LESS_OR_EQUAL_OPERATOR));
        oneOrTwoSingsTokens.add(new OneOrTwoSignsTokenType('>', TokenTypeEnum.GREATER_THAN_OPERATOR, '=', TokenTypeEnum.GREATER_OR_EQUAL_OPERATOR));
        oneOrTwoSingsTokens.add(new OneOrTwoSignsTokenType('!', TokenTypeEnum.NEGATION_OPERATOR, '=', TokenTypeEnum.NOT_EQUAL_OPERATOR));
    }

    public void setStringMaxLength(int stringMaxLength) {
        this.stringMaxLength = stringMaxLength;
    }

    public void setIdentifierMaxLength(int identifierMaxLength) {
        this.identifierMaxLength = identifierMaxLength;
    }

    public void setMaxInt(int maxInt) {
        this.maxInt = maxInt;
    }

    public void setMaxDouble(double maxDouble) {
        this.maxDouble = maxDouble;
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
            || tryBuildString()
            || tryBuildComment()
            || tryBuildOnlySingleSignToken()
            || tryBuildOnlyDoubledSignToken()
            || tryBuildSingleOrDoubledSignToken()
            || tryBuildOneOrTwoSignsToken()) {
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
            try {
                int currentDigit = currentChar - '0';
                if (Math.multiplyExact(value, 10) <= maxInt
                    && Math.addExact(Math.multiplyExact(value, 10), currentDigit) <= maxInt) {
                    value = Math.multiplyExact(value, 10);
                    value = Math.addExact(value, currentDigit);
                }
                else {
                    throw new ArithmeticException(Integer.toString(value));
                }
            }
            catch (ArithmeticException e) {
                token = new StringToken(Integer.toString(value), tokenPosition, TokenTypeEnum.INT_EXCEEDED_RANGE_ERROR);
                return true;
            }
            nextChar();
        }

        if (currentChar.equals('.')) {
            nextChar();

            int decimalValue = 0;
            int digitsAfterDecimalPoint = 0;
            while (Character.isDigit(currentChar)) {
                try {
                    int currentDigit = currentChar - '0';
                    if (value + ((decimalValue * 10 + currentDigit) / Math.pow(10, digitsAfterDecimalPoint + 1)) <= maxDouble) {
                        decimalValue = Math.multiplyExact(decimalValue, 10);
                        decimalValue = Math.addExact(decimalValue, currentDigit);
                        digitsAfterDecimalPoint++;
                    }
                    else {
                        throw new ArithmeticException(Integer.toString(value));
                    }
                }
                catch (ArithmeticException e) {
                    token = new StringToken(Double.toString(value), tokenPosition, TokenTypeEnum.DOUBLE_EXCEEDED_RANGE_ERROR);
                    return true;
                }
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

        while ((Character.isLetter(currentChar)
                || Character.isDigit(currentChar)
                || currentChar.equals('_'))
                && identifier.length() < identifierMaxLength) {
            identifier.append(currentChar);
            nextChar();
        }

        if (identifier.length() == identifierMaxLength && isCurrentCharNotEqualETX()) {
            token = new StringToken(identifier.toString(), tokenPosition, TokenTypeEnum.IDENTIFIER_EXCEEDED_MAXIMUM_LENGTH_ERROR);
        }
        else if (this.keywordTokens.containsKey(identifier.toString())) {
            token = new Token(tokenPosition, this.keywordTokens.get(identifier.toString()));
        }
        else {
            token = new StringToken(identifier.toString(), tokenPosition, TokenTypeEnum.IDENTIFIER);
        }

        return true;
    }

    private boolean tryBuildString() {
        if (!currentChar.equals('\"')) {
            return false;
        }

        StringBuilder string = new StringBuilder();
        Position tokenPosition = new Position(carriagePosition);
        Character previousChar = currentChar;
        nextChar();

        while (!hasStringEndedCorrectly(previousChar)
                && isCurrentCharNotEqualETX()
                && string.length() < stringMaxLength) {

            if (currentChar.equals('\\')) {
                if (previousChar.equals('\\')) {
                    string.append(currentChar);
                }
            }
            else {
                string.append(currentChar);
            }
            previousChar = currentChar;
            nextChar();
        }

        if (string.length() == stringMaxLength && isCurrentCharNotEqualETX()) {
            token = new StringToken(string.toString(), tokenPosition, TokenTypeEnum.STRING_EXCEEDED_MAXIMUM_LENGTH_ERROR);
        }
        else if (hasStringEndedCorrectly(previousChar)) {
            nextChar();
            token = new StringToken(string.toString(), tokenPosition, TokenTypeEnum.STRING_VALUE);
        }
        else {
            token = new StringToken(string.toString(), tokenPosition, TokenTypeEnum.UNCLOSED_QUOTES_ERROR);
        }

        return true;
    }

    private boolean hasStringEndedCorrectly(Character previousChar) {
        return !previousChar.equals('\\') && currentChar.equals('\"');
    }

    private boolean tryBuildComment() {
        if (!currentChar.equals('#')) {
            return false;
        }
        StringBuilder comment = new StringBuilder();
        comment.append(currentChar);
        Position tokenPosition = new Position(carriagePosition);
        nextChar();

        while (!currentChar.equals('\n') && isCurrentCharNotEqualETX()) {
            comment.append(currentChar);
            nextChar();
        }

        token = new StringToken(comment.toString(), tokenPosition, TokenTypeEnum.COMMENT);
        return true;
    }

    private boolean isCurrentCharNotEqualETX() {
        return !currentChar.equals((char) (-1));
    }

    private boolean tryBuildOnlySingleSignToken() {
        if (!onlySingleSignTokens.containsKey(currentChar)) {
            return false;
        }

        return tryBuildSingleSign(currentChar, onlySingleSignTokens.get(currentChar));
    }

    private boolean tryBuildSingleSign(Character sign, TokenTypeEnum tokenType) {
        if (!currentChar.equals(sign)) {
            return false;
        }

        Position tokenPosition = new Position(carriagePosition);
        nextChar();
        token = new Token(tokenPosition, tokenType);
        return true;
    }

    private boolean tryBuildSingleOrDoubledSignToken() {
        if (!singleOrDoubledSignTokens.containsKey(currentChar)) {
            return false;
        }

        return tryBuildOneOrTwoSign(
                currentChar,
                singleOrDoubledSignTokens.get(currentChar).getTokenTypeWhenSingleSign(),
                currentChar,
                singleOrDoubledSignTokens.get(currentChar).getTokenTypeWhenDoubledSign()
        );
    }

    private boolean tryBuildOnlyDoubledSignToken() {
        if (!onlyDoubledSingTokens.containsKey(currentChar)) {
            return false;
        }

        return tryOnlyDoubledSign(
                currentChar,
                onlyDoubledSingTokens.get(currentChar).getTokenTypeWhenSingleSign(),
                onlyDoubledSingTokens.get(currentChar).getTokenTypeWhenDoubledSign()
        );
    }

    private boolean tryOnlyDoubledSign(Character sign, TokenTypeEnum tokenTypeForSingleSign, TokenTypeEnum tokenTypeForDoubledSign) {
        if (!currentChar.equals(sign)) {
            return false;
        }

        StringBuilder foundSign = new StringBuilder();
        foundSign.append(currentChar);
        Position tokenPosition = new Position(carriagePosition);
        nextChar();

        if (currentChar.equals(sign)) {
            nextChar();
            token = new Token(tokenPosition, tokenTypeForDoubledSign);
        }
        else {
            token = new StringToken(foundSign.toString(), tokenPosition, tokenTypeForSingleSign);
        }

        return true;
    }

    private boolean tryBuildOneOrTwoSignsToken() {
        for (OneOrTwoSignsTokenType o : oneOrTwoSingsTokens) {
            if (tryBuildOneOrTwoSign(o.getFirstSign(), o.getTokenTypeWhenOneSign(), o.getSecondSign(), o.getTokenTypeWhenTwoSigns())) {
                return true;
            }
        }

        return false;
    }

    private boolean tryBuildOneOrTwoSign(Character firstSign, TokenTypeEnum tokenTypeWhenOneSign, Character secondSign, TokenTypeEnum tokenTypeWhenTwoSigns) {
        if (!currentChar.equals(firstSign)) {
            return false;
        }

        Position tokenPosition = new Position(carriagePosition);
        nextChar();

        if (currentChar.equals(secondSign)) {
            nextChar();
            token = new Token(tokenPosition, tokenTypeWhenTwoSigns);
        }
        else {
            token = new Token(tokenPosition, tokenTypeWhenOneSign);
        }

        return true;
    }
    
    private void nextChar() {
        try {
            currentChar = (char) bufferedReader.read();
            updateCarriagePosition();
        } catch (IOException ignored) { }
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
