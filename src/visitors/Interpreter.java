package visitors;

import lexer.Position;
import parser.*;
import parser.program_components.*;
import parser.program_components.data_values.*;
import parser.program_components.data_values.lists.*;
import parser.program_components.expressions.*;
import parser.program_components.function_definitions.*;
import parser.program_components.parameters.*;
import parser.program_components.statements.*;
import visitors.exceptions.*;

import java.lang.reflect.Method;
import java.util.Map;

public class Interpreter implements IVisitor {
    private final IErrorHandler errorHandler;
    private final ContextManager contextManager;
    // attribute ifStatementsDepth is used to determine whether if or else if statement
    // has been visited in currently analyzed if statement
    private int ifStatementsDepth;
    private IVisitable lastResult;
    private boolean returnFound;

    public Interpreter(IErrorHandler errorHandler, ContextManager contextManager) {
        this.errorHandler = errorHandler;
        this.contextManager = contextManager;
        lastResult = null;
        returnFound = false;
        ifStatementsDepth = 0;
    }

    public IVisitable getLastResult() {
        return lastResult;
    }

    public ContextManager getContextManager() {
        return contextManager;
    }

    public int getIfStatementsDepth() {
        return ifStatementsDepth;
    }

    @Override
    public void visit(Program program) {
        if (!program.functions().containsKey("main")) {
            errorHandler.handle(new MissingMainFunctionException(program.functions()));
        }

        for (Map.Entry<String, IFunctionDef> f : program.functions().entrySet()) {
            f.getValue().accept(this);
            contextManager.addFunction(f.getKey(), f.getValue());
        }
    }

    @Override
    public void visit(BoolFunctionDef f) {
        preFunctionVisit(f);
        f.functionCode().accept(this);
        if (lastResult == null) {
            errorHandler.handle(new MissingReturnValueException(f));
        } else if (!lastResult.getClass().equals(BoolValue.class)) {
            errorHandler.handle(new IncompatibleDataTypeException(f, lastResult));
        }
        postFunctionVisit();
    }

    @Override
    public void visit(BoolListFunctionDef f) {
        preFunctionVisit(f);
        f.functionCode().accept(this);
        if (lastResult == null) {
            errorHandler.handle(new MissingReturnValueException(f));
        } else if (!lastResult.getClass().equals(BoolListValue.class)) {
            errorHandler.handle(new IncompatibleDataTypeException(f, lastResult));
        }
        postFunctionVisit();
    }

    @Override
    public void visit(DoubleFunctionDef f) {
        preFunctionVisit(f);
        f.functionCode().accept(this);
        if (lastResult == null) {
            errorHandler.handle(new MissingReturnValueException(f));
        } else if (lastResult.getClass().equals(IntValue.class)) {
            IntValue value = (IntValue) lastResult;
            lastResult = new DoubleValue(value.position(), value.value().doubleValue());
        } else if (!lastResult.getClass().equals(DoubleValue.class)) {
            errorHandler.handle(new IncompatibleDataTypeException(f, lastResult));
        }
        postFunctionVisit();
    }

    @Override
    public void visit(DoubleListFunctionDef f) {
        preFunctionVisit(f);
        f.functionCode().accept(this);
        if (lastResult == null) {
            errorHandler.handle(new MissingReturnValueException(f));
        } else if (!lastResult.getClass().equals(DoubleListValue.class)) {
            errorHandler.handle(new IncompatibleDataTypeException(f, lastResult));
        }
        postFunctionVisit();
    }

    @Override
    public void visit(FigureFunctionDef f) {
        preFunctionVisit(f);
        f.functionCode().accept(this);
        if (lastResult == null) {
            errorHandler.handle(new MissingReturnValueException(f));
        } else if (!lastResult.getClass().equals(FigureValue.class)) {
            errorHandler.handle(new IncompatibleDataTypeException(f, lastResult));
        }
        postFunctionVisit();
    }

    @Override
    public void visit(FigureListFunctionDef f) {
        preFunctionVisit(f);
        f.functionCode().accept(this);
        if (lastResult == null) {
            errorHandler.handle(new MissingReturnValueException(f));
        } else if (!lastResult.getClass().equals(FigureListValue.class)) {
            errorHandler.handle(new IncompatibleDataTypeException(f, lastResult));
        }
        postFunctionVisit();
    }

    @Override
    public void visit(IntFunctionDef f) {
        preFunctionVisit(f);
        f.functionCode().accept(this);
        if (lastResult == null) {
            errorHandler.handle(new MissingReturnValueException(f));
        } else if (lastResult.getClass().equals(DoubleValue.class)) {
            DoubleValue value = (DoubleValue) lastResult;
            lastResult = new IntValue(value.position(), value.value().intValue());
        } else if (!lastResult.getClass().equals(IntValue.class)) {
            errorHandler.handle(new IncompatibleDataTypeException(f, lastResult));
        }
        postFunctionVisit();
    }

    @Override
    public void visit(IntListFunctionDef f) {
        preFunctionVisit(f);
        visit((f.functionCode()));
        if (lastResult == null) {
            errorHandler.handle(new MissingReturnValueException(f));
        } else if (!lastResult.getClass().equals(IntListValue.class)) {
            errorHandler.handle(new IncompatibleDataTypeException(f, lastResult));
        }
        postFunctionVisit();
    }

    @Override
    public void visit(PointFunctionDef f) {
        preFunctionVisit(f);
        visit((f.functionCode()));
        if (lastResult == null) {
            errorHandler.handle(new MissingReturnValueException(f));
        } else if (!lastResult.getClass().equals(PointValue.class)) {
            errorHandler.handle(new IncompatibleDataTypeException(f, lastResult));
        }
        postFunctionVisit();
    }

    @Override
    public void visit(PointListFunctionDef f) {
        preFunctionVisit(f);
        visit((f.functionCode()));
        if (lastResult == null) {
            errorHandler.handle(new MissingReturnValueException(f));
        } else if (!lastResult.getClass().equals(PointListValue.class)) {
            errorHandler.handle(new IncompatibleDataTypeException(f, lastResult));
        }
        postFunctionVisit();
    }

    @Override
    public void visit(SceneFunctionDef f) {
        preFunctionVisit(f);
        f.functionCode().accept(this);
        if (lastResult == null) {
            errorHandler.handle(new MissingReturnValueException(f));
        } else if (!lastResult.getClass().equals(SceneValue.class)) {
            errorHandler.handle(new IncompatibleDataTypeException(f, lastResult));
        }
        postFunctionVisit();
    }

    @Override
    public void visit(SceneListFunctionDef f) {
        preFunctionVisit(f);
        f.functionCode().accept(this);
        if (lastResult == null) {
            errorHandler.handle(new MissingReturnValueException(f));
        } else if (!lastResult.getClass().equals(SceneListValue.class)) {
            errorHandler.handle(new IncompatibleDataTypeException(f, lastResult));
        }
        postFunctionVisit();
    }

    @Override
    public void visit(SectionFunctionDef f) {
        preFunctionVisit(f);
        f.functionCode().accept(this);
        if (lastResult == null) {
            errorHandler.handle(new MissingReturnValueException(f));
        } else if (!lastResult.getClass().equals(SectionValue.class)) {
            errorHandler.handle(new IncompatibleDataTypeException(f, lastResult));
        }
        postFunctionVisit();
    }

    @Override
    public void visit(SectionListFunctionDef f) {
        preFunctionVisit(f);
        f.functionCode().accept(this);
        if (lastResult == null) {
            errorHandler.handle(new MissingReturnValueException(f));
        } else if (!lastResult.getClass().equals(SectionListValue.class)) {
            errorHandler.handle(new IncompatibleDataTypeException(f, lastResult));
        }
        postFunctionVisit();
    }

    @Override
    public void visit(StringFunctionDef f) {
        preFunctionVisit(f);
        f.functionCode().accept(this);
        if (lastResult == null) {
            errorHandler.handle(new MissingReturnValueException(f));
        } else if (!lastResult.getClass().equals(StringValue.class)) {
            errorHandler.handle(new IncompatibleDataTypeException(f, lastResult));
        }
        postFunctionVisit();
    }

    @Override
    public void visit(StringListFunctionDef f) {
        preFunctionVisit(f);
        f.functionCode().accept(this);
        if (lastResult == null) {
            errorHandler.handle(new MissingReturnValueException(f));
        } else if (!lastResult.getClass().equals(StringListValue.class)) {
            errorHandler.handle(new IncompatibleDataTypeException(f, lastResult));
        }
        postFunctionVisit();
    }

    private void preFunctionVisit(IFunctionDef f) {
        contextManager.createNewFunctionContext();
        for (Map.Entry<String, IParameter> p : f.parameters().entrySet()) {
            p.getValue().accept(this);
        }
    }

    private void postFunctionVisit() {
        contextManager.deleteLastContext();
        returnFound = false;
    }

    @Override
    public void visit(CodeBlock codeBlock) {
        contextManager.createNewContext();
        for (IStatement s : codeBlock.statements()) {
            s.accept(this);
            if (returnFound) {
                break;
            }
        }
        contextManager.deleteLastContext();
    }

    // statements
    @Override
    public void visit(AssignmentStatement stmnt) {
        stmnt.exp().accept(this);
        if (stmnt.param().getClass().equals(IntParameter.class)) {
            handleIntValueAssignment(stmnt);
        } else if (stmnt.param().getClass().equals(DoubleParameter.class)) {
            handleDoubleValueAssignment(stmnt);
        } else if (stmnt.param().getClass().equals(StringParameter.class)) {
            handleParamValueAssignment(lastResult.getClass().equals(StringValue.class), stmnt);
        } else if (stmnt.param().getClass().equals(BoolParameter.class)) {
            handleParamValueAssignment(lastResult.getClass().equals(BoolValue.class), stmnt);
        } else if (stmnt.param().getClass().equals(PointParameter.class)) {
            handlePointValueAssignment(stmnt);
        } else if (stmnt.param().getClass().equals(SectionParameter.class)) {
            handleSectionValueAssignment(stmnt);
        } else if (stmnt.param().getClass().equals(FigureParameter.class)) {
            handleParamValueAssignment(lastResult.getClass().equals(FigureValue.class), stmnt);
        } else if (stmnt.param().getClass().equals(SceneParameter.class)) {
            handleParamValueAssignment(lastResult.getClass().equals(SceneValue.class), stmnt);
        } else if (stmnt.param().getClass().equals(IntListParameter.class)) {
            handleParamValueAssignment(lastResult.getClass().equals(IntListValue.class), stmnt);
        } else if (stmnt.param().getClass().equals(DoubleListParameter.class)) {
            handleParamValueAssignment(lastResult.getClass().equals(DoubleListValue.class), stmnt);
        } else if (stmnt.param().getClass().equals(StringListParameter.class)) {
            handleParamValueAssignment(lastResult.getClass().equals(StringListValue.class), stmnt);
        } else if (stmnt.param().getClass().equals(BoolListParameter.class)) {
            handleParamValueAssignment(lastResult.getClass().equals(BoolListValue.class), stmnt);
        } else if (stmnt.param().getClass().equals(PointListParameter.class)) {
            handleParamValueAssignment(lastResult.getClass().equals(PointListValue.class), stmnt);
        } else if (stmnt.param().getClass().equals(SectionListParameter.class)) {
            handleParamValueAssignment(lastResult.getClass().equals(SectionListValue.class), stmnt);
        } else if (stmnt.param().getClass().equals(FigureListParameter.class)) {
            handleParamValueAssignment(lastResult.getClass().equals(FigureListValue.class), stmnt);
        } else if (stmnt.param().getClass().equals(SceneListParameter.class)) {
            handleParamValueAssignment(lastResult.getClass().equals(SceneListValue.class), stmnt);
        } else if (stmnt.param().getClass().equals(ReassignedParameter.class)) {
            handleValueReassignment(stmnt);
        } else {
            errorHandler.handle(new IncompatibleDataTypeException(stmnt.param(), lastResult));
        }
    }

    private void handleParamValueAssignment(boolean assignmentCondition, AssignmentStatement stmnt) {
        if (assignmentCondition) {
            contextManager.add(stmnt.param().name(), lastResult);
        } else {
            errorHandler.handle(new IncompatibleDataTypeException(stmnt.param(), lastResult));
        }
    }

    private void handlePointValueAssignment(AssignmentStatement stmnt) {
        PointValue castedValue = castToPointValue(lastResult);
        contextManager.add(stmnt.param().name(), castedValue);
    }

    private void handleSectionValueAssignment(AssignmentStatement stmnt) {
        SectionValue castedValue = castToSectionValue(lastResult);
        contextManager.add(stmnt.param().name(), castedValue);
    }

    private void handleValueReassignment(AssignmentStatement stmnt) {
        if (!contextManager.containsKey(stmnt.param().name())) {
            errorHandler.handle(new ParameterNotFoundException(stmnt.param(), lastResult));
        }

        IVisitable value = contextManager.get(stmnt.param().name());
        if (value.getClass().equals(IntValue.class)) {
            IntValue castedValue = castToIntValue(lastResult);
            contextManager.update(stmnt.param().name(), castedValue);
        } else if (value.getClass().equals(DoubleValue.class)) {
            DoubleValue castedValue = castToDoubleValue(lastResult);
            contextManager.update(stmnt.param().name(), castedValue);
        } else if (!value.getClass().equals(lastResult.getClass())) {
            errorHandler.handle(new IncompatibleDataTypeException(value, lastResult));
        } else {
            contextManager.update(stmnt.param().name(), lastResult);
        }
    }

    private void handleIntValueAssignment(AssignmentStatement stmnt) {
        contextManager.add(stmnt.param().name(), castToIntValue(lastResult));
    }

    private void handleDoubleValueAssignment(AssignmentStatement stmnt) {
        contextManager.add(stmnt.param().name(), castToDoubleValue(lastResult));
    }

    @Override
    public void visit(IfStatement stmnt) {
        boolean visitedElseIfStmnt = false;
        registerErrorIsExpIsNull(stmnt, stmnt.exp());
        stmnt.exp().accept(this);
        if (isConditionTrue()) {
            ifStatementsDepth++;
            stmnt.codeBlock().accept(this);
            ifStatementsDepth--;
        } else {
            int previousIfStmntsDepth = ifStatementsDepth;
            for (ElseIfStatement s : stmnt.elseIfStmnts()) {
                s.accept(this);
                if (previousIfStmntsDepth != ifStatementsDepth) {
                    visitedElseIfStmnt = true;
                    ifStatementsDepth--;
                    break;
                }
            }

            if ((!visitedElseIfStmnt) && (stmnt.elseStmnt() != null)) {
                ifStatementsDepth++;
                visit(stmnt.elseStmnt());
                ifStatementsDepth--;
            }
        }
    }

    @Override
    public void visit(ElseIfStatement stmnt) {
        registerErrorIsExpIsNull(stmnt, stmnt.exp());
        stmnt.exp().accept(this);
        if (isConditionTrue()) {
            ifStatementsDepth++;
            stmnt.codeBlock().accept(this);
        }
    }

    @Override
    public void visit(ElseStatement stmnt) {
        stmnt.codeBlock().accept(this);
    }

    @Override
    public void visit(ReturnStatement stmnt) {
        if (stmnt.exp() != null) {
            stmnt.exp().accept(this);
        }
        returnFound = true;
    }

    @Override
    public void visit(WhileStatement stmnt) {
        registerErrorIsExpIsNull(stmnt, stmnt.exp());
        stmnt.exp().accept(this);
        while (isConditionTrue() && !returnFound) {
            stmnt.codeBlock().accept(this);
            if (!returnFound) {
                stmnt.exp().accept(this);
            }
        }
    }

    private boolean isConditionTrue() {
        if (lastResult.getClass().equals(IntValue.class)) {
            IntValue val = (IntValue) lastResult;
            return val.value() > 0;
        } else if (lastResult.getClass().equals(DoubleValue.class)) {
            DoubleValue val = (DoubleValue) lastResult;
            return val.value() > 0;
        } else if (lastResult.getClass().equals(BoolValue.class)) {
            BoolValue val = (BoolValue) lastResult;
            return val.value();
        }

        return false;
    }

    // expressions
    public void visit(IExpression exp) {
        if (exp == null) {
            lastResult = null;
        } else {
            exp.accept(this);
        }
    }

    @Override
    public void visit(IDataValue val) {
        if (val.getClass().equals(PointValue.class)) {
            visitPointValue((PointValue) val);
        } else if (val.getClass().equals(SectionValue.class)) {
            visitSectionValue((SectionValue) val);
        } else {
            lastResult = val;
        }
    }

    public void visitPointValue(PointValue val) {
        val.x().accept(this);
        IExpression x = (IExpression) lastResult;
        val.y().accept(this);
        IExpression y = (IExpression) lastResult;
        lastResult = new PointValue(val.position(), x, y);
    }

    public void visitSectionValue(SectionValue val) {
        val.first().accept(this);
        IExpression firstExp = (IExpression) lastResult;
        val.second().accept(this);
        IExpression secondExp = (IExpression) lastResult;
        lastResult = new SectionValue(val.position(), firstExp, secondExp);
    }

    @Override
    public void visit(AlternativeExpression exp) {
        exp.leftExp().accept(this);
        if (!isConditionTrue()) {
            exp.rightExp().accept(this);
        }

        saveExpressionBoolResult(exp);
    }

    @Override
    public void visit(ConjunctiveExpression exp) {
        exp.leftExp().accept(this);
        if (isConditionTrue()) {
            exp.rightExp().accept(this);
        }

        saveExpressionBoolResult(exp);
    }

    // comparison expressions
    @Override
    public void visit(EqualExpression exp) {
        exp.leftExp().accept(this);
        IVisitable leftExp = lastResult;
        exp.rightExp().accept(this);
        boolean comparisonResult = areValuesEqual(leftExp, lastResult);
        lastResult = new BoolValue(exp.position(), comparisonResult);
    }

    @Override
    public void visit(GreaterOrEqualExpression exp) {
        exp.leftExp().accept(this);
        IVisitable leftExp = lastResult;
        exp.rightExp().accept(this);
        boolean comparisonResult = (isLeftValueGreaterThanRightValue(leftExp, lastResult)
                || areValuesEqual(leftExp, lastResult));
        lastResult = new BoolValue(exp.position(), comparisonResult);
    }

    @Override
    public void visit(GreaterThanExpression exp) {
        exp.leftExp().accept(this);
        IVisitable leftExp = lastResult;
        exp.rightExp().accept(this);
        boolean comparisonResult = isLeftValueGreaterThanRightValue(leftExp, lastResult);
        lastResult = new BoolValue(exp.position(), comparisonResult);
    }

    @Override
    public void visit(LessOrEqualExpression exp) {
        exp.leftExp().accept(this);
        IVisitable leftExp = lastResult;
        exp.rightExp().accept(this);
        boolean comparisonResult = (isLeftValueLessThanRightValue(leftExp, lastResult)
                || areValuesEqual(leftExp, lastResult));
        lastResult = new BoolValue(exp.position(), comparisonResult);

    }

    @Override
    public void visit(LessThanExpression exp) {
        exp.leftExp().accept(this);
        IVisitable leftExp = lastResult;
        exp.rightExp().accept(this);
        boolean comparisonResult = isLeftValueLessThanRightValue(leftExp, lastResult);
        lastResult = new BoolValue(exp.position(), comparisonResult);
    }

    @Override
    public void visit(NegatedExpression exp) {

    }

    @Override
    public void visit(NotEqualExpression exp) {
        exp.leftExp().accept(this);
        IVisitable leftExp = lastResult;
        exp.rightExp().accept(this);
        boolean comparisonResult = !(areValuesEqual(leftExp, lastResult));
        lastResult = new BoolValue(exp.position(), comparisonResult);
    }

    private boolean isLeftValueLessThanRightValue(IVisitable left, IVisitable right) {
        try {
            if (left.getClass().equals(right.getClass())) {
                return (!(areValuesEqual(left, right)) && !(isLeftValueGreaterThanRightValue(left, right)));
            }
        } catch (Exception e) {
            errorHandler.handle(new OperationDataTypeException(left, right));
        }
        return false;
    }

    private boolean areValuesEqual(IVisitable left, IVisitable right) {
        try {
            IDataValue castedLeftValue = (IDataValue) left;
            IDataValue castedRightValue = (IDataValue) right;
            return castedLeftValue.value().equals(castedRightValue.value());
        } catch (Exception e) {
            errorHandler.handle(new OperationDataTypeException(left, right));
        }
        return false;
    }

    private boolean isLeftValueGreaterThanRightValue(IVisitable left, IVisitable tight) {
        try {
            IDataValue castedLeftValue = (IDataValue) left;
            IDataValue castedRightValue = (IDataValue) tight;
            if (castedLeftValue.getClass().equals(IntValue.class) && castedRightValue.getClass().equals(IntValue.class)) {
                return ((IntValue) castedLeftValue).value() > ((IntValue) castedRightValue).value();
            } else if (castedLeftValue.getClass().equals(DoubleValue.class) && castedRightValue.getClass().equals(DoubleValue.class)) {
                return ((DoubleValue) castedLeftValue).value() > ((DoubleValue) castedRightValue).value();
            }
            return false;
        } catch (Exception e) {
            errorHandler.handle(new OperationDataTypeException(left, tight));
        }
        return false;
    }

    // arithmetic expressions
    @Override
    public void visit(AdditionExpression exp) {
        exp.leftExp().accept(this);
        IVisitable leftExp = lastResult;
        exp.rightExp().accept(this);

        tryToAddExpressions(exp.position(), leftExp, lastResult);
    }

    @Override
    public void visit(SubtractionExpression exp) {
        exp.leftExp().accept(this);
        IVisitable leftExp = lastResult;
        exp.rightExp().accept(this);

        tryToSubtractExpressions(exp.position(), leftExp, lastResult);
    }

    @Override
    public void visit(DiscreteDivisionExpression exp) {
        exp.leftExp().accept(this);
        IVisitable leftExp = lastResult;
        exp.rightExp().accept(this);

        tryToDivideDiscretely(exp.position(), leftExp, lastResult);
    }

    @Override
    public void visit(DivisionExpression exp) {
        exp.leftExp().accept(this);
        IVisitable leftExp = lastResult;
        exp.rightExp().accept(this);

        tryToDivide(exp.position(), leftExp, lastResult);
    }

    @Override
    public void visit(MultiplicationExpression exp) {
        exp.leftExp().accept(this);
        IVisitable leftExp = lastResult;
        exp.rightExp().accept(this);

        tryToMultiply(exp.position(), leftExp, lastResult);
    }

    @Override
    public void visit(ParenthesesExpression exp) {

    }

    private void tryToAddExpressions(Position position, IVisitable leftExp, IVisitable rightExp) {
        DoubleValue leftCastedValue = castToDoubleValue(leftExp);
        DoubleValue rightCastedValue = castToDoubleValue(rightExp);
        lastResult = new DoubleValue(position, leftCastedValue.value() + rightCastedValue.value());
    }

    private void tryToSubtractExpressions(Position position, IVisitable leftExp, IVisitable rightExp) {
        DoubleValue leftCastedValue = castToDoubleValue(leftExp);
        DoubleValue rightCastedValue = castToDoubleValue(rightExp);
        lastResult = new DoubleValue(position, leftCastedValue.value() - rightCastedValue.value());
    }

    private void tryToDivideDiscretely(Position position, IVisitable leftExp, IVisitable rightExp) {
        IntValue leftCastedValue = castToIntValue(leftExp);
        IntValue rightCastedValue = castToIntValue(rightExp);
        lastResult = new IntValue(position, leftCastedValue.value() / rightCastedValue.value());
    }

    private void tryToDivide(Position position, IVisitable leftExp, IVisitable rightExp) {
        DoubleValue leftCastedValue = castToDoubleValue(leftExp);
        DoubleValue rightCastedValue = castToDoubleValue(rightExp);
        lastResult = new DoubleValue(position, leftCastedValue.value() / rightCastedValue.value());
    }

    private void tryToMultiply(Position position, IVisitable leftExp, IVisitable rightExp) {
        DoubleValue leftCastedValue = castToDoubleValue(leftExp);
        DoubleValue rightCastedValue = castToDoubleValue(rightExp);
        lastResult = new DoubleValue(position, leftCastedValue.value() * rightCastedValue.value());
    }

    @Override
    public void visit(IParameter param) {
        contextManager.add(param.name(), param);
    }

    // other components
    @Override
    public void visit(FunctionCall functionCall) {
        if (!contextManager.containsFunction(functionCall.identifier().name())) {
            if (!contextManager.isMethodImplemented(functionCall.identifier().name())) {
                errorHandler.handle(new UndefinedFunctionCallException(functionCall));
            }
        } else {
            IFunctionDef func = contextManager.getFunction(functionCall.identifier().name());
            func.accept(this);
        }
    }

    @Override
    public void visit(Identifier identifier) {
        if (!contextManager.containsKey(identifier.name())) {
            errorHandler.handle(new IdentifierNotFoundException(identifier));
        }

        lastResult = contextManager.get(identifier.name());
    }

    @Override
    public void visit(ObjectAccess objectAccess) {
        objectAccess.leftExp().accept(this);
        if (!objectAccess.rightExp().getClass().equals(FunctionCall.class)) {
            errorHandler.handle(new UndefinedMethodCallException(objectAccess));
        }
        FunctionCall funcCall = (FunctionCall) objectAccess.rightExp();

        if (funcCall.identifier().name().equals("add")) {
            handleAddToObject(funcCall);
        } else if (funcCall.identifier().name().equals("get")) {
            handleGetFromObject(funcCall);
        } else {
            errorHandler.handle(new UndefinedMethodCallException(objectAccess));
        }
    }

    private void handleAddToObject(FunctionCall funcCall) {
        try {
            IVisitable obj = lastResult;
            Class<?> clazz = obj.getClass();
            Method method = clazz.getMethod(funcCall.identifier().name(), IDataValue.class);
            funcCall.exp().accept(this);
            method.invoke(obj, lastResult);
        } catch (Exception e) {
            Throwable cause = e.getCause();
            if (cause instanceof IncompatibleDataTypeException) {
                errorHandler.handle((IncompatibleDataTypeException) cause);
            } else if (cause instanceof IncompatibleMethodArgumentException) {
                errorHandler.handle((IncompatibleMethodArgumentException) cause);
            } else {
                errorHandler.handle(new RuntimeException());
            }
        }
    }

    private void handleGetFromObject(FunctionCall funcCall) {
        try {
            Class<?> clazz = lastResult.getClass();
            Method method = clazz.getMethod(funcCall.identifier().name(), int.class);
            IntValue castedArg = castToIntValue(funcCall.exp());
            lastResult = (IVisitable) method.invoke(lastResult, castedArg.value());
        } catch (Exception e) {
            errorHandler.handle(e);
        }
    }

    // utils
    private SectionValue castToSectionValue(IVisitable value) {
        SectionValue castedValue = null;
        if (value.getClass().equals(SectionValue.class)) {
            castedValue = (SectionValue) value;
            PointValue firstPoint = castToPointValue(((SectionValue) value).first());
            PointValue secondPoint = castToPointValue(((SectionValue) value).second());
            castedValue = new SectionValue(castedValue.position(), firstPoint, secondPoint);
        } else {
            errorHandler.handle(new IncompatibleDataTypeException(new SectionValue(value.position(), null, null), value));
        }
        return castedValue;
    }

    private PointValue castToPointValue(IVisitable value) {
        PointValue castedValue = null;
        if (value.getClass().equals(PointValue.class)) {
            castedValue = (PointValue) value;
            DoubleValue xValue = castToDoubleValue(castedValue.x());
            DoubleValue yValue = castToDoubleValue(castedValue.y());
            castedValue = new PointValue(castedValue.position(), xValue, yValue);
        } else {
            errorHandler.handle(new IncompatibleDataTypeException(new PointValue(value.position(), null, null), value));
        }
        return castedValue;
    }

    private IntValue castToIntValue(IVisitable value) {
        IntValue castedValue = null;
        if (value.getClass().equals(IntValue.class)) {
            castedValue = (IntValue) value;
        } else if (value.getClass().equals(DoubleValue.class)) {
            castedValue = new IntValue(value.position(), ((DoubleValue) value).value().intValue());
        } else {
            errorHandler.handle(new IncompatibleDataTypeException(new IntValue(value.position(), null), value));
        }
        return castedValue;
    }

    private DoubleValue castToDoubleValue(IVisitable value) {
        DoubleValue castedValue = null;
        if (value.getClass().equals(IntValue.class)) {
            castedValue = new DoubleValue(value.position(), ((IntValue) value).value().doubleValue());
        } else if (value.getClass().equals(DoubleValue.class)) {
            castedValue = (DoubleValue) value;
        } else {
            errorHandler.handle(new IncompatibleDataTypeException(new DoubleValue(value.position(), null), value));
        }
        return castedValue;
    }

    private void saveExpressionBoolResult(IExpression exp) {
        if (lastResult.getClass().equals(BoolValue.class)) {
            BoolValue castedValue = (BoolValue) lastResult;
            lastResult = new BoolValue(exp.position(), castedValue.value());
        }
    }

    private void registerErrorIsExpIsNull(IStatement stmnt, IExpression exp) {
        if (exp == null) {
            errorHandler.handle(new NullExpressionException(stmnt));
        }
    }
}
