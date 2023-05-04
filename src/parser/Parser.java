package parser;

import lexer.ILexer;
import lexer.TokenTypeEnum;
import lexer.tokens.Token;
import parser.exceptions.*;
import parser.program_components.*;

import java.util.ArrayList;
import java.util.HashMap;

public class Parser {
    private final ILexer lexer;
    private final ErrorHandler errorHandler;
    private Token currentToken;

    public Parser(ILexer lexer, ErrorHandler errorHandler) {
        this.lexer = lexer;
        this.errorHandler = errorHandler;
        currentToken = null;
    }

    public Program parse() {
        nextToken();
        HashMap<String, FunctionDef> functions = new HashMap<>();
        while (parseFunctionDef(functions)) {
            assert true;
        }

        return new Program(functions);
    }

    private boolean parseFunctionDef(HashMap<String, FunctionDef> functions) {
        if (!isCurrentTokenOfDataTypeKeyword()) {
            return false;
        }
        TokenTypeEnum functionType = currentToken.getTokenType();
        nextToken();

        if (currentToken.getTokenType() != TokenTypeEnum.IDENTIFIER) {
            errorHandler.handle(new MissingIdentifierException(currentToken.toString()));
        }
        String functionName = (String) currentToken.getValue();
        nextToken();

        parseLeftBracket();
        HashMap<String, Parameter> parameters = parseParameters();
        parseRightBracket();

        CodeBlock codeBlock = parseCodeBlock();
        if (codeBlock == null) {
            errorHandler.handle(new MissingLeftCurlyBracketException(currentToken.toString()));
        }

        if (!(functions.containsKey(functionName))) {
            functions.put(functionName, new FunctionDef(functionName, functionType, parameters, codeBlock));
        } else {
            errorHandler.handle(
                    new DuplicatedFunctionNameException(
                            String.format("Function %s at position: <line: %d, column %d>", functionName, currentToken.getPosition().getLineNumber(), currentToken.getPosition().getColumnNumber())
                    )
            );
        }
        return true;
    }

    private HashMap<String, Parameter> parseParameters() {
        HashMap<String, Parameter> params = new HashMap<>();

        if (!isCurrentTokenOfDataTypeKeyword()) {
            return params;
        }

        TokenTypeEnum paramType = currentToken.getTokenType();
        nextToken();

        if (currentToken.getTokenType() != TokenTypeEnum.IDENTIFIER) {
            errorHandler.handle(new MissingIdentifierException(currentToken.toString()));
        }

        String paramName = currentToken.getValue().toString();
        params.put(paramName, new Parameter(paramType, paramName));
        nextToken();

        while (consumeIf(TokenTypeEnum.COMMA)) {
            if (!isCurrentTokenOfDataTypeKeyword()) {
                errorHandler.handle(new MissingDataTypeDeclarationException(currentToken.toString()));
                nextToken();
            }
            TokenTypeEnum nextParamType = currentToken.getTokenType();
            nextToken();

            if (currentToken.getTokenType() != TokenTypeEnum.IDENTIFIER) {
                errorHandler.handle(new MissingIdentifierException(currentToken.toString()));
            }

            String nextParamName = currentToken.getValue().toString();
            if (params.containsKey(nextParamName)) {
                errorHandler.handle(
                        new DuplicatedParameterNameException(
                                String.format("Parameter %s at position: <line: %d, column %d>", paramName, currentToken.getPosition().getLineNumber(), currentToken.getPosition().getColumnNumber())
                        )
                );
            }
            params.put(nextParamName, new Parameter(nextParamType, nextParamName));
            nextToken();
        }

        return params;
    }

    private CodeBlock parseCodeBlock() {
        if (!consumeIf(TokenTypeEnum.LEFT_CURLY_BRACKET)) {
            return null;
        }

        ArrayList<IExpression> expressions = new ArrayList<>();
        IExpression exp = parseExpression();
        while (exp != null) {
            expressions.add(exp);
            exp = parseExpression();
        }

        if (!consumeIf(TokenTypeEnum.RIGHT_CURLY_BRACKET)) {
            errorHandler.handle(new MissingRightCurlyBracketException(currentToken.toString()));
        }

        return new CodeBlock(expressions);
    }

    private IExpression parseExpression() {
        IExpression exp = parseReturnExpression();
        if (exp == null) {
            exp = parseIfExpression();
        }
        return exp;
    }

    private IExpression parseReturnExpression() {
        if (!consumeIf(TokenTypeEnum.RETURN_KEYWORD)) {
            return null;
        }

        Object value = null;
        if (isCurrentTokenOfBoolValue()) {
            if (currentToken.getTokenType() == TokenTypeEnum.BOOL_TRUE_VALUE_KEYWORD) {
                value = true;
            } else {
                value = false;
            }
            nextToken();
        } else if (isCurrentTokenOfDataValue()) {
            value = currentToken.getValue();
            nextToken();
        }

        if (!consumeIf(TokenTypeEnum.SEMICOLON)) {
            errorHandler.handle(new MissingSemicolonException(currentToken.toString()));
        }

        return new ReturnExpression(value);
    }

    private IExpression parseIfExpression() {
        if (!consumeIf(TokenTypeEnum.IF_KEYWORD)) {
            return null;
        }

        parseLeftBracket();
        parseRightBracket();

        return new IfExpression();
    }

    private void parseLeftBracket() {
        if (!consumeIf(TokenTypeEnum.LEFT_BRACKET)) {
            errorHandler.handle(new MissingLeftBracketException(currentToken.toString()));
        }
    }

    private void parseRightBracket() {
        if (!consumeIf(TokenTypeEnum.RIGHT_BRACKET)) {
            errorHandler.handle(new MissingRightBracketException(currentToken.toString()));
        }
    }

    private boolean isCurrentTokenOfDataTypeKeyword() {
        return currentToken.getTokenType() == TokenTypeEnum.INT_KEYWORD
                || currentToken.getTokenType() == TokenTypeEnum.DOUBLE_KEYWORD
                || currentToken.getTokenType() == TokenTypeEnum.STRING_KEYWORD
                || currentToken.getTokenType() == TokenTypeEnum.BOOL_KEYWORD
                || currentToken.getTokenType() == TokenTypeEnum.POINT_KEYWORD
                || currentToken.getTokenType() == TokenTypeEnum.SECTION_KEYWORD
                || currentToken.getTokenType() == TokenTypeEnum.FIGURE_KEYWORD
                || currentToken.getTokenType() == TokenTypeEnum.SCENE_KEYWORD;
    }

    private boolean isCurrentTokenOfBoolValue() {
        return currentToken.getTokenType() == TokenTypeEnum.BOOL_TRUE_VALUE_KEYWORD
                || currentToken.getTokenType() == TokenTypeEnum.BOOL_FALSE_VALUE_KEYWORD;
    }

    private boolean isCurrentTokenOfDataValue() {
        return currentToken.getTokenType() == TokenTypeEnum.INT_VALUE
                || currentToken.getTokenType() == TokenTypeEnum.DOUBLE_VALUE
                || currentToken.getTokenType() == TokenTypeEnum.STRING_VALUE;
    }

    private boolean consumeIf(TokenTypeEnum tokenType) {
        if (currentToken.getTokenType() == tokenType) {
            nextToken();
            return true;
        }

        return false;
    }

    private void nextToken() {
        currentToken = lexer.lexToken();
    }
}
