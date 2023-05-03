package parser;

import lexer.ILexer;
import lexer.tokens.Token;
import lexer.TokenTypeEnum;
import parser.exceptions.*;
import parser.program_components.Program;
import parser.program_components.FunctionDef;
import parser.program_components.BlockStatement;

import java.util.ArrayList;
import java.util.HashMap;

public class Parser {
    private final ILexer lexer;
    private Token currentToken;
    private final ErrorHandler errorHandler;

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

    private boolean parseFunctionDef(HashMap<String,FunctionDef> functions) {
        if (notStartsWithDataTypeKeyword()) {
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
            functions.put(functionName, new FunctionDef(functionName, functionType, new ArrayList<>(), new BlockStatement(new ArrayList<>())));
        } else {
            errorHandler.handle(
                    new DuplicatedFunctionNameException(
                            String.format("Function name %s at position: <line: %d, column %d>", functionName, currentToken.getPosition().getLineNumber(), currentToken.getPosition().getColumnNumber())
                    )
            );
        }
        return true;
    }

    private boolean notStartsWithDataTypeKeyword() {
        return currentToken.getTokenType() != TokenTypeEnum.INT_KEYWORD
                && currentToken.getTokenType() != TokenTypeEnum.DOUBLE_KEYWORD
                && currentToken.getTokenType() != TokenTypeEnum.STRING_KEYWORD
                && currentToken.getTokenType() != TokenTypeEnum.BOOL_KEYWORD
                && currentToken.getTokenType() != TokenTypeEnum.POINT_KEYWORD
                && currentToken.getTokenType() != TokenTypeEnum.SECTION_KEYWORD
                && currentToken.getTokenType() != TokenTypeEnum.FIGURE_KEYWORD
                && currentToken.getTokenType() != TokenTypeEnum.SCENE_KEYWORD;
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
