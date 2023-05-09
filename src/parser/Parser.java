package parser;

import lexer.ILexer;
import lexer.TokenTypeEnum;
import lexer.tokens.Token;
import parser.exceptions.*;
import parser.program_components.*;
import parser.program_components.data_values.BoolValue;
import parser.program_components.data_values.DoubleValue;
import parser.program_components.data_values.IntValue;
import parser.program_components.data_values.StringValue;
import parser.program_components.expressions.*;
import parser.program_components.statements.*;

import java.util.ArrayList;
import java.util.HashMap;

public class Parser implements IParser {
    private final ILexer lexer;
    private final ErrorHandler errorHandler;
    private Token currentToken;

    public Parser(ILexer lexer, ErrorHandler errorHandler) {
        this.lexer = lexer;
        this.errorHandler = errorHandler;
        currentToken = null;
    }

    @Override
    public Program parse() {
        nextToken();
        HashMap<String, FunctionDef> functions = new HashMap<>();
        while (parseFunctionDef(functions)) {
            assert true;
        }

        return new Program(functions);
    }

    //    @TODO wynieść dodawanie funkcji do mapy poza parsowanie
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

//        @TODO wynieść
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

    private CodeBlock parseCodeBlock() {
        if (!consumeIf(TokenTypeEnum.LEFT_CURLY_BRACKET)) {
            return null;
        }

        ArrayList<IStatement> statements = new ArrayList<>();
        IStatement statement = parseStatement();
        while (statement != null) {
            statements.add(statement);
            statement = parseStatement();
        }

        if (!consumeIf(TokenTypeEnum.RIGHT_CURLY_BRACKET)) {
            errorHandler.handle(new MissingRightCurlyBracketException(currentToken.toString()));
        }

        return new CodeBlock(statements);
    }

    private IStatement parseStatement() {
        ReturnStatement returnStmnt = parseReturnStatement();
        if (returnStmnt != null) {
            return returnStmnt;
        }

        AssignmentStatement assignmentStmnt = parseAssignmentStatement();
        if (assignmentStmnt != null) {
            return assignmentStmnt;
        }

        IfStatement ifStmnt = parseIfStatement();
        if (ifStmnt != null) {
            return ifStmnt;
        }

        return parseWhileStatement();
    }

    private ReturnStatement parseReturnStatement() {
        if (!consumeIf(TokenTypeEnum.RETURN_KEYWORD)) {
            return null;
        }

        IExpression exp = parseAlternativeExpression();
        registerErrorIfSemicolonIsMissing();

        return new ReturnStatement(exp);
    }

    private AssignmentStatement parseAssignmentStatement() {
        Parameter parameter = parseParameter();
        if (parameter == null) {
            return null;
        }

        if (!consumeIf(TokenTypeEnum.ASSIGNMENT_OPERATOR)) {
            return null;
        }

        IExpression exp = parseAlternativeExpression();
        registerErrorIfExpIsMissing(exp);
        registerErrorIfSemicolonIsMissing();

        return new AssignmentStatement(parameter, exp);
    }

    private IfStatement parseIfStatement() {
        if (!consumeIf(TokenTypeEnum.IF_KEYWORD)) {
            return null;
        }

        IExpression expression = parseConditionExpression();
        CodeBlock ifCodeBlock = parseCodeBlock();
        registerErrorIfCodeBlockIsMissing(ifCodeBlock);

        ArrayList<ElseIfStatement> elseIfStatements = parseElseIfStatements();
        IStatement elseExp = parseElseStatement();

        return new IfStatement(expression, ifCodeBlock, elseIfStatements, elseExp);
    }

    private ArrayList<ElseIfStatement> parseElseIfStatements() {
        ArrayList<ElseIfStatement> elseIfStatements = new ArrayList<>();
        while (consumeIf(TokenTypeEnum.ELSE_IF_KEYWORD)) {
            IExpression exp = parseConditionExpression();

            CodeBlock elseIfCodeBlock = parseCodeBlock();
            registerErrorIfCodeBlockIsMissing(elseIfCodeBlock);

            elseIfStatements.add(new ElseIfStatement(exp, elseIfCodeBlock));
        }
        return elseIfStatements;
    }

    private ElseStatement parseElseStatement() {
        if (consumeIf(TokenTypeEnum.ELSE_KEYWORD)) {
            parseLeftBracket();
            IExpression exp = parseAlternativeExpression();
            registerErrorIfExpIsMissing(exp);
            parseRightBracket();

            CodeBlock elseCodeBlock = parseCodeBlock();
            registerErrorIfCodeBlockIsMissing(elseCodeBlock);

            return new ElseStatement(exp, elseCodeBlock);
        }
        return new ElseStatement();
    }

    private WhileStatement parseWhileStatement() {
        if (!consumeIf(TokenTypeEnum.WHILE_KEYWORD)) {
            return null;
        }

        parseLeftBracket();
        IExpression exp = parseAlternativeExpression();
        registerErrorIfExpIsMissing(exp);
        parseRightBracket();

        CodeBlock codeBlock = parseCodeBlock();
        registerErrorIfCodeBlockIsMissing(codeBlock);
        return new WhileStatement(exp, codeBlock);
    }

    private HashMap<String, Parameter> parseParameters() {
        HashMap<String, Parameter> params = new HashMap<>();

        Parameter firstParam = parseParameter();
        if (firstParam == null) {
            return params;
        }

        params.put(firstParam.name(), firstParam);

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
                                String.format("Parameter %s at position: <line: %d, column %d>", nextParamName, currentToken.getPosition().getLineNumber(), currentToken.getPosition().getColumnNumber())
                        )
                );
            }
            params.put(nextParamName, new Parameter(nextParamType, nextParamName));
            nextToken();
        }

        return params;
    }

    private Parameter parseParameter() {
        if (!isCurrentTokenOfDataTypeKeyword()) {
            return null;
        }

        TokenTypeEnum paramType = currentToken.getTokenType();
        nextToken();

        if (currentToken.getTokenType() != TokenTypeEnum.IDENTIFIER) {
            errorHandler.handle(new MissingIdentifierException(currentToken.toString()));
        }

        String paramName = currentToken.getValue().toString();
        nextToken();

        return new Parameter(paramType, paramName);
    }

    private IExpression parseConditionExpression() {
        parseLeftBracket();
        IExpression expression = parseAlternativeExpression();
        registerErrorIfExpIsMissing(expression);
        parseRightBracket();
        return expression;
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

    private IExpression parseAlternativeExpression() {
        IExpression leftExp = parseConjunctiveExpression();

        if (leftExp == null) {
            return null;
        }

        while (consumeIf(TokenTypeEnum.OR_OPERATOR)) {
            IExpression rightExp = parseConjunctiveExpression();
            registerErrorIfExpIsMissing(rightExp);

            leftExp = new AlternativeExpression(leftExp, rightExp);
        }

        return leftExp;
    }

    private IExpression parseConjunctiveExpression() {
        IExpression leftExp = parseComparisonExpression();

        if (leftExp == null) {
            return null;
        }

        while (consumeIf(TokenTypeEnum.AND_OPERATOR)) {
            IExpression rightExp = parseComparisonExpression();
            registerErrorIfExpIsMissing(rightExp);

            leftExp = new ConjunctiveExpression(leftExp, rightExp);
        }

        return leftExp;
    }

    private IExpression parseComparisonExpression() {
        IExpression leftExp = parseAdditiveExpression();

        if (leftExp == null) {
            return null;
        }

        IExpression newLeftExp = parseLessThanExpression(leftExp);
        if (leftExp != newLeftExp) {
            registerErrorIfCurrentTokenIsComparisonOperator();
            return newLeftExp;
        }

        newLeftExp = parseLessOrEqualExpression(leftExp);
        if (leftExp != newLeftExp) {
            registerErrorIfCurrentTokenIsComparisonOperator();
            return newLeftExp;
        }

        newLeftExp = parseGreaterThanExpression(leftExp);
        if (leftExp != newLeftExp) {
            registerErrorIfCurrentTokenIsComparisonOperator();
            return newLeftExp;
        }

        newLeftExp = parseGreaterOrEqualExpression(leftExp);
        if (leftExp != newLeftExp) {
            registerErrorIfCurrentTokenIsComparisonOperator();
            return newLeftExp;
        }

        newLeftExp = parseEqualExpression(leftExp);
        if (leftExp != newLeftExp) {
            registerErrorIfCurrentTokenIsComparisonOperator();
            return newLeftExp;
        }

        newLeftExp = parseNotEqualExpression(leftExp);
        if (leftExp != newLeftExp) {
            registerErrorIfCurrentTokenIsComparisonOperator();
            return newLeftExp;
        }

        return leftExp;
    }

    private IExpression parseLessThanExpression(IExpression leftExp) {
        if (consumeIf(TokenTypeEnum.LESS_THAN_OPERATOR)) {
            IExpression rightExp = parseAdditiveExpression();
            registerErrorIfExpIsMissing(rightExp);

            leftExp = new LessThanExpression(leftExp, rightExp);
        }
        return leftExp;
    }

    private IExpression parseLessOrEqualExpression(IExpression leftExp) {
        if (consumeIf(TokenTypeEnum.LESS_OR_EQUAL_OPERATOR)) {
            IExpression rightExp = parseAdditiveExpression();
            registerErrorIfExpIsMissing(rightExp);

            leftExp = new LessOrEqualExpression(leftExp, rightExp);
        }
        return leftExp;
    }

    private IExpression parseGreaterThanExpression(IExpression leftExp) {
        if (consumeIf(TokenTypeEnum.GREATER_THAN_OPERATOR)) {
            IExpression rightExp = parseAdditiveExpression();
            registerErrorIfExpIsMissing(rightExp);

            leftExp = new GreaterThanExpression(leftExp, rightExp);
        }
        return leftExp;
    }

    private IExpression parseGreaterOrEqualExpression(IExpression leftExp) {
        if (consumeIf(TokenTypeEnum.GREATER_OR_EQUAL_OPERATOR)) {
            IExpression rightExp = parseAdditiveExpression();
            registerErrorIfExpIsMissing(rightExp);

            leftExp = new GreaterOrEqualExpression(leftExp, rightExp);
        }
        return leftExp;
    }

    private IExpression parseEqualExpression(IExpression leftExp) {
        if (consumeIf(TokenTypeEnum.EQUAL_OPERATOR)) {
            IExpression rightExp = parseAdditiveExpression();
            registerErrorIfExpIsMissing(rightExp);

            leftExp = new EqualExpression(leftExp, rightExp);
        }
        return leftExp;
    }

    private IExpression parseNotEqualExpression(IExpression leftExp) {
        if (consumeIf(TokenTypeEnum.NOT_EQUAL_OPERATOR)) {
            IExpression rightExp = parseAdditiveExpression();
            registerErrorIfExpIsMissing(rightExp);

            leftExp = new NotEqualExpression(leftExp, rightExp);
        }
        return leftExp;
    }

    private IExpression parseAdditiveExpression() {
        IExpression leftExp = parseMultiplicativeExpression();

        if (leftExp == null) {
            return null;
        }

        while (isCurrentTokenOfAdditiveOperatorType()) {
            if (currentToken.getTokenType() == TokenTypeEnum.ADDITION_OPERATOR) {
                nextToken();
                IExpression rightExp = parseMultiplicativeExpression();
                registerErrorIfExpIsMissing(rightExp);

                leftExp = new AdditionExpression(leftExp, rightExp);
            } else {
                nextToken();
                IExpression rightExp = parseMultiplicativeExpression();
                registerErrorIfExpIsMissing(rightExp);

                leftExp = new SubtractionExpression(leftExp, rightExp);
            }
        }

        return leftExp;
    }

    private IExpression parseMultiplicativeExpression() {
        IExpression leftExp = parseFactor();

        while (isCurrentTokenOfMultiplicativeOperatorType()) {
            if (currentToken.getTokenType() == TokenTypeEnum.MULTIPLICATION_OPERATOR) {
                nextToken();
                IExpression rightExp = parseFactor();
                registerErrorIfExpIsMissing(rightExp);

                leftExp = new MultiplicationExpression(leftExp, rightExp);
            } else if (currentToken.getTokenType() == TokenTypeEnum.DIVISION_OPERATOR) {
                nextToken();
                IExpression rightExp = parseFactor();
                registerErrorIfExpIsMissing(rightExp);

                leftExp = new DivisionExpression(leftExp, rightExp);
            } else {
                nextToken();
                IExpression rightExp = parseFactor();
                registerErrorIfExpIsMissing(rightExp);

                leftExp = new DiscreteDivisionExpression(leftExp, rightExp);
            }
        }

        return leftExp;
    }

    private IExpression parseFactor() {
        IExpression exp = parseParenthesesExpression();
        if (exp != null) {
            return exp;
        }

        return parseAssignableValue();
    }

    private IExpression parseParenthesesExpression() {
        if (!consumeIf(TokenTypeEnum.LEFT_BRACKET)) {
            return null;
        }

        IExpression exp = parseAlternativeExpression();
        registerErrorIfExpIsMissing(exp);

        if (!consumeIf(TokenTypeEnum.RIGHT_BRACKET)) {
            errorHandler.handle(new UnclosedParenthesesException(currentToken.toString()));
        }

        return new ParenthesesExpression(exp);
    }

    private IExpression parseAssignableValue() {
        if (consumeIf(TokenTypeEnum.NEGATION_OPERATOR)) {
            IExpression assignableValue = parsePositiveAssignableValue();
            registerErrorIfExpIsMissing(assignableValue);
            return new NegatedExpression(assignableValue);
        }

        return parsePositiveAssignableValue();
    }

    private IExpression parsePositiveAssignableValue() {
        IExpression exp = parseIdentifier();
        if (exp != null) {
            return exp;
        }

        exp = parseStringValue();
        if (exp != null) {
            return exp;
        }

        exp = parseIntValue();
        if (exp != null) {
            return exp;
        }

        exp = parseDoubleValue();
        if (exp != null) {
            return exp;
        }

        return parseBoolValue();
    }

    private IExpression parseStringValue() {
        if (currentToken.getTokenType() != TokenTypeEnum.STRING_VALUE) {
            return null;
        }

        String value = (String) currentToken.getValue();
        nextToken();
        return new StringValue(value);
    }

    private IExpression parseIntValue() {
        if (currentToken.getTokenType() != TokenTypeEnum.INT_VALUE) {
            return null;
        }

        int value = (int) currentToken.getValue();
        nextToken();
        return new IntValue(value);
    }

    private IExpression parseDoubleValue() {
        if (currentToken.getTokenType() != TokenTypeEnum.DOUBLE_VALUE) {
            return null;
        }

        double value = (double) currentToken.getValue();
        nextToken();
        return new DoubleValue(value);
    }

    private IExpression parseBoolValue() {
        if (consumeIf(TokenTypeEnum.BOOL_TRUE_VALUE_KEYWORD)) {
            return new BoolValue(true);
        } else if (consumeIf(TokenTypeEnum.BOOL_FALSE_VALUE_KEYWORD)) {
            return new BoolValue(false);
        }

        return null;
    }


    private IExpression parseIdentifier() {
        if (currentToken.getTokenType() != TokenTypeEnum.IDENTIFIER) {
            return null;
        }

        String identifierName = (String) currentToken.getValue();
        nextToken();

        return new Identifier(identifierName);
    }

    private void registerErrorIfExpIsMissing(IExpression exp) {
        if (exp == null) {
            errorHandler.handle(new MissingExpressionException(currentToken.toString()));
        }
    }

    private void registerErrorIfCodeBlockIsMissing(CodeBlock codeBlock) {
        if (codeBlock == null) {
            errorHandler.handle(new MissingCodeBlockException(currentToken.toString()));
        }
    }

    private void registerErrorIfCurrentTokenIsComparisonOperator() {
        registerErrorIfCurrentTokenIsOfType(TokenTypeEnum.LESS_THAN_OPERATOR);
        registerErrorIfCurrentTokenIsOfType(TokenTypeEnum.LESS_OR_EQUAL_OPERATOR);
        registerErrorIfCurrentTokenIsOfType(TokenTypeEnum.GREATER_THAN_OPERATOR);
        registerErrorIfCurrentTokenIsOfType(TokenTypeEnum.GREATER_OR_EQUAL_OPERATOR);
        registerErrorIfCurrentTokenIsOfType(TokenTypeEnum.EQUAL_OPERATOR);
        registerErrorIfCurrentTokenIsOfType(TokenTypeEnum.NOT_EQUAL_OPERATOR);
    }

    private void registerErrorIfCurrentTokenIsOfType(TokenTypeEnum expressionType) {
        if (currentToken.getTokenType() == expressionType) {
            errorHandler.handle(new UnclearExpressionException(currentToken.toString()));
        }
    }

    private void registerErrorIfSemicolonIsMissing() {
        if (!consumeIf(TokenTypeEnum.SEMICOLON)) {
            errorHandler.handle(new MissingSemicolonException(currentToken.toString()));
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

    private boolean isCurrentTokenOfAdditiveOperatorType() {
        return currentToken.getTokenType() == TokenTypeEnum.ADDITION_OPERATOR
                || currentToken.getTokenType() == TokenTypeEnum.SUBTRACTION_OPERATOR;
    }

    private boolean isCurrentTokenOfMultiplicativeOperatorType() {
        return currentToken.getTokenType() == TokenTypeEnum.MULTIPLICATION_OPERATOR
                || currentToken.getTokenType() == TokenTypeEnum.DIVISION_OPERATOR
                || currentToken.getTokenType() == TokenTypeEnum.DISCRETE_DIVISION_OPERATOR;
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
