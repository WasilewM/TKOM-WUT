package parser;

import lexer.ILexer;
import lexer.Position;
import lexer.TokenTypeEnum;
import lexer.tokens.Token;
import parser.exceptions.*;
import parser.program_components.*;
import parser.program_components.data_values.*;
import parser.program_components.expressions.*;
import parser.program_components.function_definitions.*;
import parser.program_components.parameters.*;
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

    /* program = { functionDef } */
    @Override
    public Program parse() {
        nextToken();
        Position position = currentToken.getPosition();
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

        return new Program(position, functions);
    }

    /* functionDef = functionType, "(", { parameters }, ")", codeBlock */
    private IFunctionDef parseFunctionDef() {
        IParameter functionType = parseFunctionType();
        if (functionType == null) {
            return null;
        }

        parseLeftBracketWithoutReturningIt();
        HashMap<String, IParameter> parameters = parseParameters();
        parseRightBracketWithoutReturningIt();

        CodeBlock codeBlock = parseCodeBlock();
        if (codeBlock == null) {
            errorHandler.handle(new MissingLeftCurlyBracketException(currentToken.toString()));
        }

        if (functionType.getClass().equals(IntParameter.class)) {
            return new IntFunctionDef((IntParameter) functionType, parameters, codeBlock);
        } else if (functionType.getClass().equals(DoubleParameter.class)) {
            return new DoubleFunctionDef((DoubleParameter) functionType, parameters, codeBlock);
        } else if (functionType.getClass().equals(StringParameter.class)) {
            return new StringFunctionDef((StringParameter) functionType, parameters, codeBlock);
        } else if (functionType.getClass().equals(BoolParameter.class)) {
            return new BoolFunctionDef((BoolParameter) functionType, parameters, codeBlock);
        } else if (functionType.getClass().equals(PointParameter.class)) {
            return new PointFunctionDef((PointParameter) functionType, parameters, codeBlock);
        } else if (functionType.getClass().equals(SectionParameter.class)) {
            return new SectionFunctionDef((SectionParameter) functionType, parameters, codeBlock);
        } else if (functionType.getClass().equals(FigureParameter.class)) {
            return new FigureFunctionDef((FigureParameter) functionType, parameters, codeBlock);
        } else if (functionType.getClass().equals(SceneParameter.class)) {
            return new SceneFunctionDef((SceneParameter) functionType, parameters, codeBlock);
        } else if (functionType.getClass().equals(IntListParameter.class)) {
            return new IntListFunctionDef((IntListParameter) functionType, parameters, codeBlock);
        } else if (functionType.getClass().equals(DoubleListParameter.class)) {
            return new DoubleListFunctionDef((DoubleListParameter) functionType, parameters, codeBlock);
        } else if (functionType.getClass().equals(BoolListParameter.class)) {
            return new BoolListFunctionDef((BoolListParameter) functionType, parameters, codeBlock);
        } else if (functionType.getClass().equals(StringListParameter.class)) {
            return new StringListFunctionDef((StringListParameter) functionType, parameters, codeBlock);
        } else if (functionType.getClass().equals(PointListParameter.class)) {
            return new PointListFunctionDef((PointListParameter) functionType, parameters, codeBlock);
        } else if (functionType.getClass().equals(SectionListParameter.class)) {
            return new SectionListFunctionDef((SectionListParameter) functionType, parameters, codeBlock);
        } else if (functionType.getClass().equals(FigureListParameter.class)) {
            return new FigureListFunctionDef((FigureListParameter) functionType, parameters, codeBlock);
        } else if (functionType.getClass().equals(SceneListParameter.class)) {
            return new SceneListFunctionDef((SceneListParameter) functionType, parameters, codeBlock);
        } else {
            errorHandler.handle(new RuntimeException(currentToken.toString()));
            return null;
        }
    }

    /* codeBlock = "{", { stmnt }, "}" */
    private CodeBlock parseCodeBlock() {
        Position position = currentToken.getPosition();
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

        return new CodeBlock(position, statements);
    }

    /* stmnt = ifStmnt | whileStmnt | assignmentStmnt | returnStmnt | objectAccessStmnt */
    private IStatement parseStatement() {
        IfStatement ifStmnt = parseIfStatement();
        if (ifStmnt != null) {
            return ifStmnt;
        }

        WhileStatement whileStmnt = parseWhileStatement();
        if (whileStmnt != null) {
            return whileStmnt;
        }

        IStatement stmnt = parseAssignmentOrObjectAccessStatement();
        if (stmnt != null) {
            return stmnt;
        }

        return parseReturnStatement();
    }

    /* returnStmnt = "return", alternativeExp , ";" */
    private ReturnStatement parseReturnStatement() {
        Position position = currentToken.getPosition();
        if (!consumeIf(TokenTypeEnum.RETURN_KEYWORD)) {
            return null;
        }

        IExpression exp = parseAlternativeExpression();
        parseSemicolonWithoutReturningIt();

        return new ReturnStatement(position, exp);
    }

    /*
        assignmentStmnt = [ dataType ], identifier, assignmentOper, alternativeExp, ";"
        parameter = dataType, identifier
    */
    private IStatement parseAssignmentOrObjectAccessStatement() {
        Position position = currentToken.getPosition();
        IParameter param = parseParameter();
        IExpression exp = null;
        if (param == null) {
            exp = parseIdentifierOrFunctionCallExpression();
            if (exp == null) {
                return null;
            } else if (exp.getClass().equals(Identifier.class)) {
                param = new ReassignedParameter((Identifier) exp);
            }
        }

        if (consumeIf(TokenTypeEnum.ASSIGNMENT_OPERATOR)) {
            return parseRestOfAssignmentStatement(position, param);
        }

        return parseRestOfObjectAccessStatement(position, exp);
    }

    private AssignmentStatement parseRestOfAssignmentStatement(Position position, IParameter param) {
        IExpression exp = parseAlternativeExpression();
        registerErrorIfExpIsMissing(exp);
        parseSemicolonWithoutReturningIt();

        return new AssignmentStatement(position, param, exp);
    }

    /* objectAccessStmnt = objectAccessExp, ";" */
    private IStatement parseRestOfObjectAccessStatement(Position position, IExpression leftExp) {
        if (!consumeIf(TokenTypeEnum.DOT)) {
            errorHandler.handle(new AmbiguousExpressionException(currentToken.toString()));
        }
        IExpression rightExp = parseIdentifierOrFunctionCallExpression();
        registerErrorIfExpIsMissing(rightExp);
        ObjectAccess objectAccessStmnt = new ObjectAccess(position, leftExp, rightExp);

        position = currentToken.getPosition();
        while (consumeIf(TokenTypeEnum.DOT)) {
            rightExp = parseIdentifierOrFunctionCallExpression();
            registerErrorIfExpIsMissing(rightExp);
            objectAccessStmnt = new ObjectAccess(position, objectAccessStmnt, rightExp);
            position = currentToken.getPosition();
        }

        parseSemicolonWithoutReturningIt();
        return objectAccessStmnt;
    }

    /* ifStmnt = "if", "(", alternativeExp, ")", "{", codeBlock, "}", { elseifStmnt }, [ elseStmnt ] */
    private IfStatement parseIfStatement() {
        Position position = currentToken.getPosition();
        if (!consumeIf(TokenTypeEnum.IF_KEYWORD)) {
            return null;
        }

        IExpression expression = parseConditionExpression();
        CodeBlock ifCodeBlock = parseCodeBlock();
        registerErrorIfCodeBlockIsMissing(ifCodeBlock);

        ArrayList<ElseIfStatement> elseIfStatements = parseElseIfStatements();
        IStatement elseExp = parseElseStatement();

        return new IfStatement(position, expression, ifCodeBlock, elseIfStatements, elseExp);
    }

    /* elseifStmnt = "elseif", "(", alternativeExp, ")", "{", codeBlock, "}" */
    private ArrayList<ElseIfStatement> parseElseIfStatements() {
        ArrayList<ElseIfStatement> elseIfStatements = new ArrayList<>();
        Position position = currentToken.getPosition();
        while (consumeIf(TokenTypeEnum.ELSE_IF_KEYWORD)) {
            IExpression exp = parseConditionExpression();

            CodeBlock elseIfCodeBlock = parseCodeBlock();
            registerErrorIfCodeBlockIsMissing(elseIfCodeBlock);

            elseIfStatements.add(new ElseIfStatement(position, exp, elseIfCodeBlock));
            position = currentToken.getPosition();
        }
        return elseIfStatements;
    }

    /* elseStmnt = "else", "(", alternativeExp, ")", "{", codeBlock, "}" */
    private ElseStatement parseElseStatement() {
        Position position = currentToken.getPosition();
        if (consumeIf(TokenTypeEnum.ELSE_KEYWORD)) {
            parseLeftBracketWithoutReturningIt();
            IExpression exp = parseAlternativeExpression();
            registerErrorIfExpIsMissing(exp);
            parseRightBracketWithoutReturningIt();

            CodeBlock elseCodeBlock = parseCodeBlock();
            registerErrorIfCodeBlockIsMissing(elseCodeBlock);

            return new ElseStatement(position, exp, elseCodeBlock);
        }
        return new ElseStatement();
    }

    /* whileStmnt = "while", "(", alternativeExp, ")", "{", codeBlock, "}" */
    private WhileStatement parseWhileStatement() {
        Position position = currentToken.getPosition();
        if (!consumeIf(TokenTypeEnum.WHILE_KEYWORD)) {
            return null;
        }

        parseLeftBracketWithoutReturningIt();
        IExpression exp = parseAlternativeExpression();
        registerErrorIfExpIsMissing(exp);
        parseRightBracketWithoutReturningIt();

        CodeBlock codeBlock = parseCodeBlock();
        registerErrorIfCodeBlockIsMissing(codeBlock);
        return new WhileStatement(position, exp, codeBlock);
    }

    /* functionType = parameter | ( "void", identifier ) */
    private IParameter parseFunctionType() {
        return parseParameter();
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
            IParameter nextParam = parseParameter();
            if (nextParam == null) {
                errorHandler.handle(new MissingDataTypeDeclarationException(currentToken.toString()));
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
        dataType = "List", "[", listableDataType, "]" | listableDataType
        listableDataType = "Int" | "Double" | "String" | "Bool" | "Point" | "Section" | "Scene"
    */
    private IParameter parseParameter() {
        IParameter param = parseListDataTypeParameter();
        if (param != null) {
            return param;
        }

        return parseListableDataTypeParameter();
    }

    private IParameter parseListDataTypeParameter() {
        Position position = currentToken.getPosition();
        if (!consumeIf(TokenTypeEnum.LIST_KEYWORD)) {
            return null;
        }

        parseLeftSquareBracketWithoutReturningIt();
        TokenTypeEnum listParamType = parseListableDataType();
        parseRightSquareBracketWithoutReturningIt();

        String paramName = parseIdentifierName();

        if (listParamType == TokenTypeEnum.INT_KEYWORD) {
            return new IntListParameter(position, paramName);
        } else if (listParamType == TokenTypeEnum.DOUBLE_KEYWORD) {
            return new DoubleListParameter(position, paramName);
        } else if (listParamType == TokenTypeEnum.BOOL_KEYWORD) {
            return new BoolListParameter(position, paramName);
        } else if (listParamType == TokenTypeEnum.STRING_KEYWORD) {
            return new StringListParameter(position, paramName);
        } else if (listParamType == TokenTypeEnum.POINT_KEYWORD) {
            return new PointListParameter(position, paramName);
        } else if (listParamType == TokenTypeEnum.SECTION_KEYWORD) {
            return new SectionListParameter(position, paramName);
        } else if (listParamType == TokenTypeEnum.FIGURE_KEYWORD) {
            return new FigureListParameter(position, paramName);
        } else if (listParamType == TokenTypeEnum.SCENE_KEYWORD) {
            return new SceneListParameter(position, paramName);
        } else {
            errorHandler.handle(new RuntimeException(currentToken.toString()));
            return null;
        }
    }

    private IParameter parseListableDataTypeParameter() {
        Position position = currentToken.getPosition();
        if (isNotCurrentTokenOfListableDataTypeKeyword()) {
            return null;
        }

        TokenTypeEnum paramType = currentToken.getTokenType();
        nextToken();

        String paramName = parseIdentifierName();

        if (paramType == TokenTypeEnum.INT_KEYWORD) {
            return new IntParameter(position, paramName);
        } else if (paramType == TokenTypeEnum.DOUBLE_KEYWORD) {
            return new DoubleParameter(position, paramName);
        } else if (paramType == TokenTypeEnum.STRING_KEYWORD) {
            return new StringParameter(position, paramName);
        } else if (paramType == TokenTypeEnum.BOOL_KEYWORD) {
            return new BoolParameter(position, paramName);
        } else if (paramType == TokenTypeEnum.POINT_KEYWORD) {
            return new PointParameter(position, paramName);
        } else if (paramType == TokenTypeEnum.SECTION_KEYWORD) {
            return new SectionParameter(position, paramName);
        } else if (paramType == TokenTypeEnum.FIGURE_KEYWORD) {
            return new FigureParameter(position, paramName);
        } else {
            return new SceneParameter(position, paramName);
        }
    }

    private IExpression parseConditionExpression() {
        parseLeftBracketWithoutReturningIt();
        IExpression expression = parseAlternativeExpression();
        registerErrorIfExpIsMissing(expression);
        parseRightBracketWithoutReturningIt();
        return expression;
    }

    /* alternativeExp = conjunctiveExp, { orOper, conjunctiveExp } */
    private IExpression parseAlternativeExpression() {
        IExpression leftExp = parseConjunctiveExpression();

        if (leftExp == null) {
            return null;
        }

        Position position = currentToken.getPosition();
        while (consumeIf(TokenTypeEnum.OR_OPERATOR)) {
            IExpression rightExp = parseConjunctiveExpression();
            registerErrorIfExpIsMissing(rightExp);

            leftExp = new AlternativeExpression(position, leftExp, rightExp);
            position = currentToken.getPosition();
        }

        return leftExp;
    }

    /* conjunctiveExp = comparisonExp, { andOper, comparisonExp } */
    private IExpression parseConjunctiveExpression() {
        IExpression leftExp = parseComparisonExpression();

        if (leftExp == null) {
            return null;
        }

        Position position = currentToken.getPosition();
        while (consumeIf(TokenTypeEnum.AND_OPERATOR)) {
            IExpression rightExp = parseComparisonExpression();
            registerErrorIfExpIsMissing(rightExp);

            leftExp = new ConjunctiveExpression(position, leftExp, rightExp);
            position = currentToken.getPosition();
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
        Position position = currentToken.getPosition();
        if (consumeIf(TokenTypeEnum.LESS_THAN_OPERATOR)) {
            IExpression rightExp = parseAdditiveExpression();
            registerErrorIfExpIsMissing(rightExp);

            leftExp = new LessThanExpression(position, leftExp, rightExp);
        }
        return leftExp;
    }

    private IExpression parseLessOrEqualExpression(IExpression leftExp) {
        Position position = currentToken.getPosition();
        if (consumeIf(TokenTypeEnum.LESS_OR_EQUAL_OPERATOR)) {
            IExpression rightExp = parseAdditiveExpression();
            registerErrorIfExpIsMissing(rightExp);

            leftExp = new LessOrEqualExpression(position, leftExp, rightExp);
        }
        return leftExp;
    }

    private IExpression parseGreaterThanExpression(IExpression leftExp) {
        Position position = currentToken.getPosition();
        if (consumeIf(TokenTypeEnum.GREATER_THAN_OPERATOR)) {
            IExpression rightExp = parseAdditiveExpression();
            registerErrorIfExpIsMissing(rightExp);

            leftExp = new GreaterThanExpression(position, leftExp, rightExp);
        }
        return leftExp;
    }

    private IExpression parseGreaterOrEqualExpression(IExpression leftExp) {
        Position position = currentToken.getPosition();
        if (consumeIf(TokenTypeEnum.GREATER_OR_EQUAL_OPERATOR)) {
            IExpression rightExp = parseAdditiveExpression();
            registerErrorIfExpIsMissing(rightExp);

            leftExp = new GreaterOrEqualExpression(position, leftExp, rightExp);
        }
        return leftExp;
    }

    private IExpression parseEqualExpression(IExpression leftExp) {
        Position position = currentToken.getPosition();
        if (consumeIf(TokenTypeEnum.EQUAL_OPERATOR)) {
            IExpression rightExp = parseAdditiveExpression();
            registerErrorIfExpIsMissing(rightExp);

            leftExp = new EqualExpression(position, leftExp, rightExp);
        }
        return leftExp;
    }

    private IExpression parseNotEqualExpression(IExpression leftExp) {
        Position position = currentToken.getPosition();
        if (consumeIf(TokenTypeEnum.NOT_EQUAL_OPERATOR)) {
            IExpression rightExp = parseAdditiveExpression();
            registerErrorIfExpIsMissing(rightExp);

            leftExp = new NotEqualExpression(position, leftExp, rightExp);
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

        Position position = currentToken.getPosition();
        while (isCurrentTokenOfAdditiveOperatorType()) {
            if (currentToken.getTokenType() == TokenTypeEnum.ADDITION_OPERATOR) {
                nextToken();
                IExpression rightExp = parseMultiplicativeExpression();
                registerErrorIfExpIsMissing(rightExp);

                leftExp = new AdditionExpression(position, leftExp, rightExp);
            } else {
                nextToken();
                IExpression rightExp = parseMultiplicativeExpression();
                registerErrorIfExpIsMissing(rightExp);

                leftExp = new SubtractionExpression(position, leftExp, rightExp);
            }
            position = currentToken.getPosition();
        }

        return leftExp;
    }

    /*
        multiplicativeExp = factor, { multiplicativeOper, factor }
        multiplicativeOper = "*" | "/" | "//"
    */
    private IExpression parseMultiplicativeExpression() {
        IExpression leftExp = parseFactor();

        Position position = currentToken.getPosition();
        while (isCurrentTokenOfMultiplicativeOperatorType()) {
            if (currentToken.getTokenType() == TokenTypeEnum.MULTIPLICATION_OPERATOR) {
                nextToken();
                IExpression rightExp = parseFactor();
                registerErrorIfExpIsMissing(rightExp);

                leftExp = new MultiplicationExpression(position, leftExp, rightExp);
            } else if (currentToken.getTokenType() == TokenTypeEnum.DIVISION_OPERATOR) {
                nextToken();
                IExpression rightExp = parseFactor();
                registerErrorIfExpIsMissing(rightExp);

                leftExp = new DivisionExpression(position, leftExp, rightExp);
            } else {
                nextToken();
                IExpression rightExp = parseFactor();
                registerErrorIfExpIsMissing(rightExp);

                leftExp = new DiscreteDivisionExpression(position, leftExp, rightExp);
            }
            position = currentToken.getPosition();
        }

        return leftExp;
    }

    /* factor =  [ notOper ] ( parenthesesExp | assignableValue ) */
    private IExpression parseFactor() {
        Position position = currentToken.getPosition();
        if (consumeIf(TokenTypeEnum.NEGATION_OPERATOR)) {
            IExpression exp = parseParenthesesExpOrAssignableVal();
            registerErrorIfExpIsMissing(exp);
            return new NegatedExpression(position, exp);
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
        Position position = currentToken.getPosition();
        if (!consumeIf(TokenTypeEnum.LEFT_BRACKET)) {
            return null;
        }

        IExpression exp = parseAlternativeExpression();
        registerErrorIfExpIsMissing(exp);

        if (!consumeIf(TokenTypeEnum.RIGHT_BRACKET)) {
            errorHandler.handle(new UnclosedParenthesesException(currentToken.toString()));
        }

        return new ParenthesesExpression(position, exp);
    }

    /* assignableValue = objectAccessExp | stringValue | intValue | doubleValue | boolValue | listValue */
    private IExpression parseAssignableValue() {
        IExpression exp = parseObjectAccessExpression();
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

        exp = parseBoolValue();
        if (exp != null) {
            return exp;
        }

        exp = parsePointValue();
        if (exp != null) {
            return exp;
        }

        exp = parseSectionValue();
        if (exp != null) {
            return exp;
        }

        exp = parseFigureValue();
        if (exp != null) {
            return exp;
        }

        exp = parseSceneValue();
        if (exp != null) {
            return exp;
        }

        return parseListValue();
    }

    /* stringValue = "\"", literal, "\"" */
    private IExpression parseStringValue() {
        Position position = currentToken.getPosition();
        if (currentToken.getTokenType() != TokenTypeEnum.STRING_VALUE) {
            return null;
        }

        String value = (String) currentToken.getValue();
        nextToken();
        return new StringValue(position, value);
    }

    /* intValue = zeroDigit | notZeroDigit, { digit } */
    private IExpression parseIntValue() {
        Position position = currentToken.getPosition();
        if (currentToken.getTokenType() != TokenTypeEnum.INT_VALUE) {
            return null;
        }

        int value = (int) currentToken.getValue();
        nextToken();
        return new IntValue(position, value);
    }

    /* doubleValue = intValue, [ ".", intValue ] */
    private IExpression parseDoubleValue() {
        Position position = currentToken.getPosition();
        if (currentToken.getTokenType() != TokenTypeEnum.DOUBLE_VALUE) {
            return null;
        }

        double value = (double) currentToken.getValue();
        nextToken();
        return new DoubleValue(position, value);
    }

    /* bool_value = "True" | False */
    private IExpression parseBoolValue() {
        Position position = currentToken.getPosition();
        if (consumeIf(TokenTypeEnum.BOOL_TRUE_VALUE_KEYWORD)) {
            return new BoolValue(position, true);
        } else if (consumeIf(TokenTypeEnum.BOOL_FALSE_VALUE_KEYWORD)) {
            return new BoolValue(position, false);
        }

        return null;
    }

    /* pointValue = "Point", "(", assignableValue, ",", assignableValue, ")" */
    private IExpression parsePointValue() {
        Position position = currentToken.getPosition();
        if (!consumeIf(TokenTypeEnum.POINT_KEYWORD)) {
            return null;
        }

        parseLeftBracketWithoutReturningIt();
        IExpression firstExp = parseAssignableValue();
        registerErrorIfExpIsMissing(firstExp);

        parseCommaWithoutReturningIt();

        IExpression secondExp = parseAssignableValue();
        registerErrorIfExpIsMissing(secondExp);
        parseRightBracketWithoutReturningIt();

        return new PointValue(position, firstExp, secondExp);
    }

    /* sectionValue = "Section", "(", assignableValue, ",", assignableValue, ")" */
    private IExpression parseSectionValue() {
        Position position = currentToken.getPosition();
        if (!consumeIf(TokenTypeEnum.SECTION_KEYWORD)) {
            return null;
        }

        parseLeftBracketWithoutReturningIt();
        IExpression firstExp = parseAssignableValue();
        registerErrorIfExpIsMissing(firstExp);

        parseCommaWithoutReturningIt();

        IExpression secondExp = parseAssignableValue();
        registerErrorIfExpIsMissing(secondExp);
        parseRightBracketWithoutReturningIt();

        return new SectionValue(position, firstExp, secondExp);
    }

    /* figureValue = "Figure", "(", ")" */
    private IExpression parseFigureValue() {
        Position position = currentToken.getPosition();
        if (!consumeIf(TokenTypeEnum.FIGURE_KEYWORD)) {
            return null;
        }

        parseLeftBracketWithoutReturningIt();
        parseRightBracketWithoutReturningIt();
        return new FigureValue(position);
    }

    /* sceneValue = "Scene", "(", ")" */
    private IExpression parseSceneValue() {
        Position position = currentToken.getPosition();
        if (!consumeIf(TokenTypeEnum.SCENE_KEYWORD)) {
            return null;
        }

        parseLeftBracketWithoutReturningIt();
        parseRightBracketWithoutReturningIt();
        return new SceneValue(position);
    }

    /* listValue = "[", listableDataType, "]" */
    private IExpression parseListValue() {
        Position position = currentToken.getPosition();
        if (!consumeIf(TokenTypeEnum.LEFT_SQUARE_BRACKET)) {
            return null;
        }

        TokenTypeEnum listParamType = parseListableDataType();
        parseRightSquareBracketWithoutReturningIt();

        if (listParamType == TokenTypeEnum.INT_KEYWORD) {
            return new IntListValue(position);
        } else if (listParamType == TokenTypeEnum.DOUBLE_KEYWORD) {
            return new DoubleListValue(position);
        } else if (listParamType == TokenTypeEnum.BOOL_KEYWORD) {
            return new BoolListValue(position);
        } else if (listParamType == TokenTypeEnum.STRING_KEYWORD) {
            return new StringListValue(position);
        } else if (listParamType == TokenTypeEnum.POINT_KEYWORD) {
            return new PointListValue(position);
        } else if (listParamType == TokenTypeEnum.SECTION_KEYWORD) {
            return new SectionListValue(position);
        } else if (listParamType == TokenTypeEnum.FIGURE_KEYWORD) {
            return new FigureListValue(position);
        } else if (listParamType == TokenTypeEnum.SCENE_KEYWORD) {
            return new SceneListValue(position);
        } else {
            errorHandler.handle(new RuntimeException(currentToken.toString()));
            return null;
        }
    }

    private TokenTypeEnum parseListableDataType() {
        if (isNotCurrentTokenOfListableDataTypeKeyword()) {
            errorHandler.handle(new MissingDataTypeDeclarationException(currentToken.toString()));
        }

        TokenTypeEnum listParamType = currentToken.getTokenType();
        nextToken();
        return listParamType;
    }

    /* objectAccessExp = memberAccessExp, { ".", memberAccessExp } */
    private IExpression parseObjectAccessExpression() {
        Position position = currentToken.getPosition();
        IExpression leftExp = parseIdentifierOrFunctionCallExpression();

        if (!consumeIf(TokenTypeEnum.DOT)) {
            return leftExp;
        }

        IExpression rightExp = parseIdentifierOrFunctionCallExpression();
        registerErrorIfExpIsMissing(rightExp);

        return new ObjectAccess(position, leftExp, rightExp);
    }

    private String parseIdentifierName() {
        if (currentToken.getTokenType() != TokenTypeEnum.IDENTIFIER) {
            errorHandler.handle(new MissingIdentifierException(currentToken.toString()));
        }

        String paramName = currentToken.getValue().toString();
        nextToken();
        return paramName;
    }

    private IExpression parseIdentifierOrFunctionCallExpression() {
        Position position = currentToken.getPosition();
        IExpression identifier = parseIdentifier();

        if (!consumeIf(TokenTypeEnum.LEFT_BRACKET)) {
            return identifier;
        }
        parseRightBracketWithoutReturningIt();
        return new FunctionCall(position, identifier);
    }

    /* identifier = letter { digit | literal } */
    private IExpression parseIdentifier() {
        Position position = currentToken.getPosition();
        if (currentToken.getTokenType() != TokenTypeEnum.IDENTIFIER) {
            return null;
        }

        String identifierName = (String) currentToken.getValue();
        nextToken();

        return new Identifier(position, identifierName);
    }

    /* listableDataType = "Int" | "Double" | "String" | "Bool" | "Point" | "Section" | "Scene" */
    private boolean isNotCurrentTokenOfListableDataTypeKeyword() {
        return currentToken.getTokenType() != TokenTypeEnum.INT_KEYWORD
                && currentToken.getTokenType() != TokenTypeEnum.DOUBLE_KEYWORD
                && currentToken.getTokenType() != TokenTypeEnum.STRING_KEYWORD
                && currentToken.getTokenType() != TokenTypeEnum.BOOL_KEYWORD
                && currentToken.getTokenType() != TokenTypeEnum.POINT_KEYWORD
                && currentToken.getTokenType() != TokenTypeEnum.SECTION_KEYWORD
                && currentToken.getTokenType() != TokenTypeEnum.FIGURE_KEYWORD
                && currentToken.getTokenType() != TokenTypeEnum.SCENE_KEYWORD;
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

    private void parseLeftBracketWithoutReturningIt() {
        if (!consumeIf(TokenTypeEnum.LEFT_BRACKET)) {
            errorHandler.handle(new MissingLeftBracketException(currentToken.toString()));
        }
    }

    private void parseRightBracketWithoutReturningIt() {
        if (!consumeIf(TokenTypeEnum.RIGHT_BRACKET)) {
            errorHandler.handle(new MissingRightBracketException(currentToken.toString()));
        }
    }

    private void parseLeftSquareBracketWithoutReturningIt() {
        if (!consumeIf(TokenTypeEnum.LEFT_SQUARE_BRACKET)) {
            errorHandler.handle(new MissingLeftSquareBracketException(currentToken.toString()));
        }
    }

    private void parseRightSquareBracketWithoutReturningIt() {
        if (!consumeIf(TokenTypeEnum.RIGHT_SQUARE_BRACKET)) {
            errorHandler.handle(new MissingRightSquareBracketException(currentToken.toString()));
        }
    }

    private void parseCommaWithoutReturningIt() {
        if (!consumeIf(TokenTypeEnum.COMMA)) {
            errorHandler.handle(new MissingCommaException(currentToken.toString()));
        }
    }

    private void parseSemicolonWithoutReturningIt() {
        if (!consumeIf(TokenTypeEnum.SEMICOLON)) {
            errorHandler.handle(new MissingSemicolonException(currentToken.toString()));
        }
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
