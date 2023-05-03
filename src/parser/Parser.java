package parser;

import lexer.ILexer;
import lexer.TokenTypeEnum;
import lexer.tokens.Token;
import parser.exceptions.*;
import parser.program_components.BlockStatement;
import parser.program_components.FunctionDef;
import parser.program_components.Parameter;
import parser.program_components.Program;

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
        if (!isCurrentTokenDataTypeKeyword()) {
            return false;
        }
        TokenTypeEnum functionType = currentToken.getTokenType();
        nextToken();

        if (currentToken.getTokenType() != TokenTypeEnum.IDENTIFIER) {
            errorHandler.handle(new MissingIdentifierException(currentToken.toString()));
        }
        String functionName = (String) currentToken.getValue();
        nextToken();

        if (!consumeIf(TokenTypeEnum.LEFT_BRACKET)) {
            errorHandler.handle(new MissingLeftBracketException(currentToken.toString()));
        }

        HashMap<String, Parameter> parameters = parseParameters();

        if (!consumeIf(TokenTypeEnum.RIGHT_BRACKET)) {
            errorHandler.handle(new MissingRightBracketException(currentToken.toString()));
        }

        if (!consumeIf(TokenTypeEnum.LEFT_CURLY_BRACKET)) {
            errorHandler.handle(new MissingLeftCurlyBracketException(currentToken.toString()));
        }

        if (!consumeIf(TokenTypeEnum.RIGHT_CURLY_BRACKET)) {
            errorHandler.handle(new MissingRightCurlyBracketException(currentToken.toString()));
        }

        if (!(functions.containsKey(functionName))) {
            functions.put(functionName, new FunctionDef(functionName, functionType, parameters, new BlockStatement(new ArrayList<>())));
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

        if (!isCurrentTokenDataTypeKeyword()) {
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
            if (!isCurrentTokenDataTypeKeyword()) {
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

    private boolean isCurrentTokenDataTypeKeyword() {
        return currentToken.getTokenType() == TokenTypeEnum.INT_KEYWORD
                || currentToken.getTokenType() == TokenTypeEnum.DOUBLE_KEYWORD
                || currentToken.getTokenType() == TokenTypeEnum.STRING_KEYWORD
                || currentToken.getTokenType() == TokenTypeEnum.BOOL_KEYWORD
                || currentToken.getTokenType() == TokenTypeEnum.POINT_KEYWORD
                || currentToken.getTokenType() == TokenTypeEnum.SECTION_KEYWORD
                || currentToken.getTokenType() == TokenTypeEnum.FIGURE_KEYWORD
                || currentToken.getTokenType() == TokenTypeEnum.SCENE_KEYWORD;
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
