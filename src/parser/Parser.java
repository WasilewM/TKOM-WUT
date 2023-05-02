package parser;

import lexer.ILexer;
import lexer.tokens.Token;
import lexer.TokenTypeEnum;
import parser.program_components.Program;
import parser.program_components.Parameter;
import parser.program_components.FunctionDef;
import parser.program_components.BlockStatement;

import java.util.ArrayList;
import java.util.HashMap;

public class Parser {
    private final ILexer lexer;
    private Token currentToken;

    public Parser(ILexer lexer) {
        this.lexer = lexer;
        currentToken = null;
    }

    public Program parse() {
        nextToken();
        HashMap<String, FunctionDef> functions = new HashMap<>();
        while (parseFunctionDef(functions)) {

        }

        return new Program(functions);
    }

    private boolean parseFunctionDef(HashMap<String,FunctionDef> functions) {
        if (currentToken.getTokenType() != TokenTypeEnum.INT_KEYWORD) {
            return false;
        }
        TokenTypeEnum functionType = currentToken.getTokenType();
        nextToken();

        if (currentToken.getTokenType() != TokenTypeEnum.IDENTIFIER) {
            assert true;
        }
        String functionName = (String) currentToken.getValue();
        nextToken();

        if (!consumeIf(TokenTypeEnum.LEFT_BRACKET)) {
            assert true;
        }

        if (!consumeIf(TokenTypeEnum.RIGHT_BRACKET)) {
            assert true;
        }

        if (!consumeIf(TokenTypeEnum.LEFT_CURLY_BRACKET)) {
            assert true;
        }

        if (!consumeIf(TokenTypeEnum.RIGHT_CURLY_BRACKET)) {
            assert true;
        }

        functions.put(functionName, new FunctionDef(functionName, functionType, new ArrayList<Parameter>(), new BlockStatement(new ArrayList<IStatement>())));
        return true;
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
