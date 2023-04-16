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
    private final HashMap<Character, TokenTypeEnum> singleSignsReservedByLanguage = new HashMap<>();
    private final HashMap<Character, DoubledSignTokenType> doubledSignsReservedByLanguage = new HashMap<>();

    public Lexer(BufferedInputStream inputStream) {
        this.inputStream = inputStream;
        currentChar = null;
        newlineConvention = null;
        carriagePosition = new Position(1, 0);
        initKeywordTokens();
        initSingleSignsReservedByLanguage();
        initDoubledSignsReservedByLanguage();
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
        keywordTokens.put("True", TokenTypeEnum.BOOL_TRUE_VALUE);
        keywordTokens.put("False", TokenTypeEnum.BOOL_FALSE_VALUE);
        keywordTokens.put("List", TokenTypeEnum.LIST_KEYWORD);
        keywordTokens.put("while", TokenTypeEnum.WHILE_KEYWORD);
        keywordTokens.put("if", TokenTypeEnum.IF_KEYWORD);
        keywordTokens.put("elseif", TokenTypeEnum.ELSE_IF_KEYWORD);
        keywordTokens.put("else", TokenTypeEnum.ELSE_KEYWORD);
        keywordTokens.put("main", TokenTypeEnum.MAIN_KEYWORD);
        keywordTokens.put("return", TokenTypeEnum.RETURN_KEYWORD);
        keywordTokens.put("void", TokenTypeEnum.VOID_KEYWORD);
    }

    private void initSingleSignsReservedByLanguage() {
        singleSignsReservedByLanguage.put(';', TokenTypeEnum.SEMICOLON);
        singleSignsReservedByLanguage.put(',', TokenTypeEnum.COMMA);
        singleSignsReservedByLanguage.put('(', TokenTypeEnum.LEFT_BRACKET);
        singleSignsReservedByLanguage.put(')', TokenTypeEnum.RIGHT_BRACKET);
        singleSignsReservedByLanguage.put('[', TokenTypeEnum.LEFT_SQUARE_BRACKET);
        singleSignsReservedByLanguage.put(']', TokenTypeEnum.RIGHT_SQUARE_BRACKET);
        singleSignsReservedByLanguage.put('{', TokenTypeEnum.LEFT_CURLY_BRACKET);
        singleSignsReservedByLanguage.put('}', TokenTypeEnum.RIGHT_CURLY_BRACKET);

        // Arithmetic Operators
        singleSignsReservedByLanguage.put('+', TokenTypeEnum.ADDITION_OPERATOR);
        singleSignsReservedByLanguage.put('-', TokenTypeEnum.SUBTRACTION_OPERATOR);
        singleSignsReservedByLanguage.put('*', TokenTypeEnum.MULTIPLICATION_OPERATOR);
    }

    private void initDoubledSignsReservedByLanguage() {
        doubledSignsReservedByLanguage.put('=', new DoubledSignTokenType(TokenTypeEnum.ASSIGNMENT_OPERATOR, TokenTypeEnum.EQUAL_OPERATOR));
        doubledSignsReservedByLanguage.put('/', new DoubledSignTokenType(TokenTypeEnum.DIVISION_OPERATOR, TokenTypeEnum.DISCRETE_DIVISION_OPERATOR));
        doubledSignsReservedByLanguage.put('&', new DoubledSignTokenType(TokenTypeEnum.ERROR, TokenTypeEnum.AND_OPERATOR));
        doubledSignsReservedByLanguage.put('|', new DoubledSignTokenType(TokenTypeEnum.ERROR, TokenTypeEnum.OR_OPERATOR));
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
            || tryBuildString()
            || tryBuildComment()
            || tryBuildNotEqualOrNegationOperator()
            || tryBuildComparisonOperator()
            || tryBuildSingleSignReservedByLanguage()
            || tryBuildDoubledSignReservedByLanguage() ) {
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

        while (!(!previousChar.equals('\\') && currentChar.equals('\"')) && !currentChar.equals((char) (-1))) {
            string.append(currentChar);
            previousChar = currentChar;
            nextChar();
        }

        if (!previousChar.equals('\\') && currentChar.equals('\"')) {
            token = new StringToken(string.toString(), tokenPosition, TokenTypeEnum.STRING);
            return true;
        }

        // @TODO
        return false;
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

    private boolean tryBuildSingleSignReservedByLanguage() {
        for (HashMap.Entry<Character, TokenTypeEnum> s : singleSignsReservedByLanguage.entrySet()) {
            if (tryBuildSingleSign(s.getKey(), s.getValue())) {
                return true;
            }
        }

        return false;
    }

    private boolean tryBuildSingleSign(Character sign, TokenTypeEnum tokenType) {
        if (!currentChar.equals(sign)) {
            return false;
        }

        Position tokenPosition = new Position(carriagePosition);
        nextChar();
        token = new StringToken(null, tokenPosition, tokenType);
        return true;
    }

    private boolean tryBuildDoubledSignReservedByLanguage() {
        for (HashMap.Entry<Character, DoubledSignTokenType> s : doubledSignsReservedByLanguage.entrySet()) {
            if (tryBuildSingleOrDoubledSign(s.getKey(), s.getValue().getTokenTypeWhenSingleSign(), s.getValue().getTokenTypeWhenDoubledSign())) {
                return true;
            }
        }

        return false;
    }

    private boolean tryBuildSingleOrDoubledSign(Character sign, TokenTypeEnum tokenTypeForSingleSign, TokenTypeEnum tokenTypeForDoubledSign) {
        if (!currentChar.equals(sign)) {
            return false;
        }

        Position tokenPosition = new Position(carriagePosition);
        nextChar();

        if (currentChar.equals(sign)) {
            nextChar();
            token = new StringToken(null, tokenPosition, tokenTypeForDoubledSign);
        }
        else {
            token = new StringToken(null, tokenPosition, tokenTypeForSingleSign);
        }

        return true;
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
