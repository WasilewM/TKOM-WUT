import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class Lexer implements ILexer {
    private final BufferedReader bufferedReader;
    private Character currentChar = null;
    private int stringMaxLength = 1000;
    private int identifierMaxLength = 1000;
    private int maxInt = Integer.MAX_VALUE;
    private double maxDouble = Double.MAX_VALUE;
    private final Position carriagePosition;
    private Token token;
    private final HashMap<String, TokenTypeEnum> keywordTokens = new HashMap<>();
    // `singleSignTokens` is a map of tokens that cannot be duplicated
    // and those signs are valid tokens only when they occur as a single sign.
    private final HashMap<Character, TokenTypeEnum> singleSignTokens = new HashMap<>();
    // `singleOrDoubledSignTokens` is a map of tokens that can be duplicated
    // but those signs are valid tokens when they occur as a single or doubled sign.
    private final HashMap<Character, DoubledSignTokenType> singleOrDoubledSignTokens = new HashMap<>();
    // `doubledSignTokens` is a map of tokens that have be duplicated
    // in order to be valid tokens.
    // They are valid only when they occur as a doubled sign.
    private final HashMap<Character, DoubledSignTokenType> doubledSignTokens = new HashMap<>();
    // `complexSignTokens` represent valid tokens that can be composed of one or two signs.
    // When they are composed od two signs, those signs are different
    private final ArrayList<ComplexSignTokenType> complexSignTokens = new ArrayList<>();

    public Lexer(BufferedReader bufferedReader) {
        this.bufferedReader = bufferedReader;
        carriagePosition = new Position(1, 0);
        initKeywordTokens();
        initSingleSignTokens();
        initSingleOrDoubledSignTokens();
        initDoubledSignTokens();
        initComplexSignTokens();
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

    private void initSingleSignTokens() {
        singleSignTokens.put(';', TokenTypeEnum.SEMICOLON);
        singleSignTokens.put(',', TokenTypeEnum.COMMA);
        singleSignTokens.put('(', TokenTypeEnum.LEFT_BRACKET);
        singleSignTokens.put(')', TokenTypeEnum.RIGHT_BRACKET);
        singleSignTokens.put('[', TokenTypeEnum.LEFT_SQUARE_BRACKET);
        singleSignTokens.put(']', TokenTypeEnum.RIGHT_SQUARE_BRACKET);
        singleSignTokens.put('{', TokenTypeEnum.LEFT_CURLY_BRACKET);
        singleSignTokens.put('}', TokenTypeEnum.RIGHT_CURLY_BRACKET);
        singleSignTokens.put('.', TokenTypeEnum.DOT);

        // Arithmetic Operators
        singleSignTokens.put('+', TokenTypeEnum.ADDITION_OPERATOR);
        singleSignTokens.put('-', TokenTypeEnum.SUBTRACTION_OPERATOR);
        singleSignTokens.put('*', TokenTypeEnum.MULTIPLICATION_OPERATOR);
    }

    private void initSingleOrDoubledSignTokens() {
        singleOrDoubledSignTokens.put('=', new DoubledSignTokenType(TokenTypeEnum.ASSIGNMENT_OPERATOR, TokenTypeEnum.EQUAL_OPERATOR));
        singleOrDoubledSignTokens.put('/', new DoubledSignTokenType(TokenTypeEnum.DIVISION_OPERATOR, TokenTypeEnum.DISCRETE_DIVISION_OPERATOR));
    }

    private void initDoubledSignTokens() {
        doubledSignTokens.put('&', new DoubledSignTokenType(TokenTypeEnum.UNKNOWN_CHAR_ERROR, TokenTypeEnum.AND_OPERATOR));
        doubledSignTokens.put('|', new DoubledSignTokenType(TokenTypeEnum.UNKNOWN_CHAR_ERROR, TokenTypeEnum.OR_OPERATOR));
    }

    private void initComplexSignTokens() {
        complexSignTokens.add(new ComplexSignTokenType('<', TokenTypeEnum.LESS_THAN_OPERATOR, '=', TokenTypeEnum.LESS_OR_EQUAL_OPERATOR));
        complexSignTokens.add(new ComplexSignTokenType('>', TokenTypeEnum.GREATER_THAN_OPERATOR, '=', TokenTypeEnum.GREATER_OR_EQUAL_OPERATOR));
        complexSignTokens.add(new ComplexSignTokenType('!', TokenTypeEnum.NEGATION_OPERATOR, '=', TokenTypeEnum.NOT_EQUAL_OPERATOR));
    }

    public void setStringMaxLength(int stringMaxLength) {
        this.stringMaxLength = stringMaxLength;
    }

    @Override
    public void setIdentifierMaxLength(int identifierMaxLength) {
        this.identifierMaxLength = identifierMaxLength;
    }

    @Override
    public void setMaxInt(int maxInt) {
        this.maxInt = maxInt;
    }

    @Override
    public void setMaxDouble(double maxDouble) {
        this.maxDouble = maxDouble;
    }

    @Override
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
        if (!singleSignTokens.containsKey(currentChar)) {
            return false;
        }

        return tryBuildSingleSign(currentChar, singleSignTokens.get(currentChar));
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
        if (!doubledSignTokens.containsKey(currentChar)) {
            return false;
        }

        return tryOnlyDoubledSign(
                currentChar,
                doubledSignTokens.get(currentChar).getTokenTypeWhenSingleSign(),
                doubledSignTokens.get(currentChar).getTokenTypeWhenDoubledSign()
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
        for (ComplexSignTokenType o : complexSignTokens) {
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
