package parser;

import lexer.ILexer;
import lexer.TokenTypeEnum;
import lexer.tokens.Token;
import parser.exceptions.*;
import parser.program_components.CodeBlock;
import parser.program_components.Identifier;
import parser.program_components.Program;
import parser.program_components.data_values.BoolValue;
import parser.program_components.data_values.DoubleValue;
import parser.program_components.data_values.IntValue;
import parser.program_components.data_values.StringValue;
import parser.program_components.expressions.*;
import parser.program_components.function_definitions.*;
import parser.program_components.parameters.BoolParameter;
import parser.program_components.parameters.DoubleParameter;
import parser.program_components.parameters.IntParameter;
import parser.program_components.parameters.StringParameter;
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
        HashMap<String, IFunctionDef> functions = new HashMap<>();
        IFunctionDef newFunction = parseFunctionDef();
        while (newFunction != null) {
            if (!(functions.containsKey(newFunction.name()))) {
                functions.put(newFunction.name(), newFunction);
                newFunction = parseFunctionDef();
            } else {
                errorHandler.handle(
                        new DuplicatedFunctionNameException(
                                String.format("Function %s at position: <line: %d, column %d>", newFunction.name(), currentToken.getPosition().getLineNumber(), currentToken.getPosition().getColumnNumber())
                        )
                );
            }
        }

        return new Program(functions);
    }

    /* program = { functionDef } */
    private IFunctionDef parseFunctionDef() {
        if (!isCurrentTokenOfDataTypeKeyword()) {
            return null;
        }
        TokenTypeEnum functionType = currentToken.getTokenType();
        nextToken();

        if (currentToken.getTokenType() != TokenTypeEnum.IDENTIFIER) {
            errorHandler.handle(new MissingIdentifierException(currentToken.toString()));
        }
        String functionName = (String) currentToken.getValue();
        nextToken();

        parseLeftBracket();
        HashMap<String, IParameter> parameters = parseParameters();
        parseRightBracket();

        CodeBlock codeBlock = parseCodeBlock();
        if (codeBlock == null) {
            errorHandler.handle(new MissingLeftCurlyBracketException(currentToken.toString()));
        }

        if (functionType == TokenTypeEnum.INT_KEYWORD) {
            return new IntFunctionDef(functionName, parameters, codeBlock);
        } else if (functionType == TokenTypeEnum.DOUBLE_KEYWORD) {
            return new DoubleFunctionDef(functionName, parameters, codeBlock);
        } else if (functionType == TokenTypeEnum.STRING_KEYWORD) {
            return new StringFunctionDef(functionName, parameters, codeBlock);
        } else if (functionType == TokenTypeEnum.BOOL_KEYWORD) {
            return new BoolFunctionDef(functionName, parameters, codeBlock);
        } else if (functionType == TokenTypeEnum.POINT_KEYWORD) {
            return new PointFunctionDef(functionName, parameters, codeBlock);
        } else if (functionType == TokenTypeEnum.SECTION_KEYWORD) {
            return new SectionFunctionDef(functionName, parameters, codeBlock);
        } else if (functionType == TokenTypeEnum.FIGURE_KEYWORD) {
            return new FigureFunctionDef(functionName, parameters, codeBlock);
        } else {
            return new SceneFunctionDef(functionName, parameters, codeBlock);
        }
    }

    /* codeBlock = "{", { stmnt }, "}" */
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

    /* stmnt = ifStmnt | whileStmnt | functionCall | assignmentStmnt | returnStmnt */
    private IStatement parseStatement() {
        IfStatement ifStmnt = parseIfStatement();
        if (ifStmnt != null) {
            return ifStmnt;
        }

        WhileStatement whileStmnt = parseWhileStatement();
        if (whileStmnt != null) {
            return whileStmnt;
        }

        AssignmentStatement assignmentStmnt = parseAssignmentStatement();
        if (assignmentStmnt != null) {
            return assignmentStmnt;
        }

        return parseReturnStatement();
    }

    /* returnStmnt = "return", alternativeExpression , ";" */
    private ReturnStatement parseReturnStatement() {
        if (!consumeIf(TokenTypeEnum.RETURN_KEYWORD)) {
            return null;
        }

        IExpression exp = parseAlternativeExpression();
        registerErrorIfSemicolonIsMissing();

        return new ReturnStatement(exp);
    }

    /* assignmentStmnt = [ dataType ], identifier, assignmentOper, alternativeExp, ";" */
    private AssignmentStatement parseAssignmentStatement() {
        TokenTypeEnum dataType = null;
        if (isCurrentTokenOfDataTypeKeyword()) {
            dataType = currentToken.getTokenType();
            nextToken();
        }

        if (currentToken.getTokenType() != TokenTypeEnum.IDENTIFIER) {
            return null;
        }

        String identifierName = (String) currentToken.getValue();
        nextToken();

        if (!consumeIf(TokenTypeEnum.ASSIGNMENT_OPERATOR)) {
            return null;
        }

        IExpression exp = parseAlternativeExpression();
        registerErrorIfExpIsMissing(exp);
        registerErrorIfSemicolonIsMissing();

        return new AssignmentStatement(dataType, identifierName, exp);
    }

    /* ifStmnt = "if", "(", alternativeExp, ")", "{", codeBlock, "}", { elseifStmnt }, [ elseStmnt ] */
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

    /* elseifStmnt = "elseif", "(", alternativeExp, ")", "{", codeBlock, "}" */
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

    /* elseStmnt = "else", "(", alternativeExp, ")", "{", codeBlock, "}" */
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

    /* whileStmnt = "while", "(", alternativeExp, ")", "{", codeBlock, "}" */
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

    /* parameters = parameter, ",", { parameter } */
    private HashMap<String, IParameter> parseParameters() {
        HashMap<String, IParameter> params = new HashMap<>();

        IParameter firstParam = parseParameter();
        if (firstParam == null) {
            return params;
        }

        params.put(firstParam.name(), firstParam);

        while (consumeIf(TokenTypeEnum.COMMA)) {
            if (!isCurrentTokenOfDataTypeKeyword()) {
                errorHandler.handle(new MissingDataTypeDeclarationException(currentToken.toString()));
                nextToken();
            }
            IParameter nextParam = parseParameter();

            if (nextParam == null) {
                errorHandler.handle(new MissingIdentifierException(currentToken.toString()));
            } else {
                if (params.containsKey(nextParam.name())) {
                    errorHandler.handle(
                            new DuplicatedParameterNameException(
                                    String.format("Parameter %s at position: <line: %d, column %d>", nextParam.name(), currentToken.getPosition().getLineNumber(), currentToken.getPosition().getColumnNumber())
                            )
                    );
                }
                params.put(nextParam.name(), nextParam);
                nextToken();
            }

        }

        return params;
    }

    /*
        parameter = dataType, identifier
        dataType = "Int" | "Double" | "String" | "Point" | "Section" | "Scene" | "Bool" | "List"
    */
    private IParameter parseParameter() {
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

        if (paramType == TokenTypeEnum.INT_KEYWORD) {
            return new IntParameter(paramName);
        } else if (paramType == TokenTypeEnum.DOUBLE_KEYWORD) {
            return new DoubleParameter(paramName);
        } else if (paramType == TokenTypeEnum.STRING_KEYWORD) {
            return new StringParameter(paramName);
        } else {
            return new BoolParameter(paramName);
        }
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

    /* alternativeExp = conjunctiveExp, { orOper, conjunctiveExp } */
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

    /* conjunctiveExp = comparisonExp, { andOper, comparisonExp } */
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

    /*
        comparisonExp = additiveExp, [ comparisonOper, additiveExp ]
        comparisonOper = equalOper | notEqualOper | lessThanOper | lessThanOrEqualOper | greaterThanOper | greaterThanOrEqualOper
    */
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

    /*
        additiveExp = multiplicativeExp, { additiveOper, multiplicativeExp }
        additiveOper = "+" | "-"
    */
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

    /*
        multiplicativeExp = factor, { multiplicativeOper, factor }
        multiplicativeOper = "*" | "/" | "//"
    */
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

    /* factor =  [ notOper ] ( parenthesesExp | assignableValue ) */
    private IExpression parseFactor() {
        if (consumeIf(TokenTypeEnum.NEGATION_OPERATOR)) {
            IExpression exp = parseParenthesesExpOrAssignableVal();
            registerErrorIfExpIsMissing(exp);
            return new NegatedExpression(exp);
        }

        return parseParenthesesExpOrAssignableVal();
    }

    private IExpression parseParenthesesExpOrAssignableVal() {
        IExpression exp = parseParenthesesExpression();
        if (exp != null) {
            return exp;
        }

        return parseAssignableValue();
    }

    /* parenthesesExp = "(", alternativeExp, ")" */
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

    /* assignableValue = objectAccess | stringValue | intValue | doubleValue | bool_value | list_value */
    private IExpression parseAssignableValue() {
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

    /* stringValue = "\"", literal, "\"" */
    private IExpression parseStringValue() {
        if (currentToken.getTokenType() != TokenTypeEnum.STRING_VALUE) {
            return null;
        }

        String value = (String) currentToken.getValue();
        nextToken();
        return new StringValue(value);
    }

    /* intValue = zeroDigit | notZeroDigit, { digit } */
    private IExpression parseIntValue() {
        if (currentToken.getTokenType() != TokenTypeEnum.INT_VALUE) {
            return null;
        }

        int value = (int) currentToken.getValue();
        nextToken();
        return new IntValue(value);
    }

    /* doubleValue = intValue, [ ".", intValue ] */
    private IExpression parseDoubleValue() {
        if (currentToken.getTokenType() != TokenTypeEnum.DOUBLE_VALUE) {
            return null;
        }

        double value = (double) currentToken.getValue();
        nextToken();
        return new DoubleValue(value);
    }

    /* bool_value = "True" | False */
    private IExpression parseBoolValue() {
        if (consumeIf(TokenTypeEnum.BOOL_TRUE_VALUE_KEYWORD)) {
            return new BoolValue(true);
        } else if (consumeIf(TokenTypeEnum.BOOL_FALSE_VALUE_KEYWORD)) {
            return new BoolValue(false);
        }

        return null;
    }

    /* identifier = letter { digit | literal } */
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
