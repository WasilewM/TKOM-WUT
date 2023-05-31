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
            visit(f.getValue());
            contextManager.addFunction(f.getKey(), f.getValue());
        }
    }

    @Override
    public void visit(IFunctionDef f) {
        contextManager.createNewFunctionContext();

        for (Map.Entry<String, IParameter> p : f.parameters().entrySet()) {
            visit(p.getValue());
        }

        if (f.getClass().equals(IntFunctionDef.class)) {
            visit((IntFunctionDef) f);
        } else if (f.getClass().equals(IntListFunctionDef.class)) {
            visit((IntListFunctionDef) f);
        } else if (f.getClass().equals(DoubleFunctionDef.class)) {
            visit((DoubleFunctionDef) f);
        } else if (f.getClass().equals(DoubleListFunctionDef.class)) {
            visit((DoubleListFunctionDef) f);
        } else if (f.getClass().equals(BoolFunctionDef.class)) {
            visit((BoolFunctionDef) f);
        } else if (f.getClass().equals(BoolListFunctionDef.class)) {
            visit((BoolListFunctionDef) f);
        } else if (f.getClass().equals(FigureFunctionDef.class)) {
            visit((FigureFunctionDef) f);
        } else if (f.getClass().equals(FigureListFunctionDef.class)) {
            visit((FigureListFunctionDef) f);
        } else if (f.getClass().equals(PointFunctionDef.class)) {
            visit((PointFunctionDef) f);
        } else if (f.getClass().equals(PointListFunctionDef.class)) {
            visit((PointListFunctionDef) f);
        } else if (f.getClass().equals(SceneFunctionDef.class)) {
            visit((SceneFunctionDef) f);
        } else if (f.getClass().equals(SceneListFunctionDef.class)) {
            visit((SceneListFunctionDef) f);
        } else if (f.getClass().equals(SectionFunctionDef.class)) {
            visit((SectionFunctionDef) f);
        } else if (f.getClass().equals(SectionListFunctionDef.class)) {
            visit((SectionListFunctionDef) f);
        } else if (f.getClass().equals(StringFunctionDef.class)) {
            visit((StringFunctionDef) f);
        } else if (f.getClass().equals(StringListFunctionDef.class)) {
            visit((StringListFunctionDef) f);
        }

        contextManager.deleteLastContext();
        returnFound = false;
    }

    @Override
    public void visit(BoolFunctionDef f) {
        visit(f.functionCode());
        if (lastResult == null) {
            errorHandler.handle(new MissingReturnValueException(f));
        } else if (!lastResult.getClass().equals(BoolValue.class)) {
            errorHandler.handle(new IncompatibleDataTypeException(f, lastResult));
        }
    }

    @Override
    public void visit(BoolListFunctionDef f) {
        visit(f.functionCode());
        if (lastResult == null) {
            errorHandler.handle(new MissingReturnValueException(f));
        } else if (!lastResult.getClass().equals(BoolListValue.class)) {
            errorHandler.handle(new IncompatibleDataTypeException(f, lastResult));
        }
    }

    @Override
    public void visit(DoubleFunctionDef f) {
        visit(f.functionCode());
        if (lastResult == null) {
            errorHandler.handle(new MissingReturnValueException(f));
        } else if (lastResult.getClass().equals(IntValue.class)) {
            IntValue value = (IntValue) lastResult;
            lastResult = new DoubleValue(value.position(), value.value().doubleValue());
        } else if (!lastResult.getClass().equals(DoubleValue.class)) {
            errorHandler.handle(new IncompatibleDataTypeException(f, lastResult));
        }
    }

    @Override
    public void visit(DoubleListFunctionDef f) {
        visit(f.functionCode());
        if (lastResult == null) {
            errorHandler.handle(new MissingReturnValueException(f));
        } else if (!lastResult.getClass().equals(DoubleListValue.class)) {
            errorHandler.handle(new IncompatibleDataTypeException(f, lastResult));
        }
    }

    @Override
    public void visit(FigureFunctionDef f) {
        visit(f.functionCode());
        if (lastResult == null) {
            errorHandler.handle(new MissingReturnValueException(f));
        } else if (!lastResult.getClass().equals(FigureValue.class)) {
            errorHandler.handle(new IncompatibleDataTypeException(f, lastResult));
        }
    }

    @Override
    public void visit(FigureListFunctionDef f) {
        visit(f.functionCode());
        if (lastResult == null) {
            errorHandler.handle(new MissingReturnValueException(f));
        } else if (!lastResult.getClass().equals(FigureListValue.class)) {
            errorHandler.handle(new IncompatibleDataTypeException(f, lastResult));
        }
    }

    @Override
    public void visit(IntFunctionDef f) {
        visit(f.functionCode());
        if (lastResult == null) {
            errorHandler.handle(new MissingReturnValueException(f));
        } else if (lastResult.getClass().equals(DoubleValue.class)) {
            DoubleValue value = (DoubleValue) lastResult;
            lastResult = new IntValue(value.position(), value.value().intValue());
        } else if (!lastResult.getClass().equals(IntValue.class)) {
            errorHandler.handle(new IncompatibleDataTypeException(f, lastResult));
        }
    }

    @Override
    public void visit(IntListFunctionDef f) {
        visit((f.functionCode()));
        if (lastResult == null) {
            errorHandler.handle(new MissingReturnValueException(f));
        } else if (!lastResult.getClass().equals(IntListValue.class)) {
            errorHandler.handle(new IncompatibleDataTypeException(f, lastResult));
        }
    }

    @Override
    public void visit(PointFunctionDef f) {
        visit((f.functionCode()));
        if (lastResult == null) {
            errorHandler.handle(new MissingReturnValueException(f));
        } else if (!lastResult.getClass().equals(PointValue.class)) {
            errorHandler.handle(new IncompatibleDataTypeException(f, lastResult));
        }
    }

    @Override
    public void visit(PointListFunctionDef f) {
        visit((f.functionCode()));
        if (lastResult == null) {
            errorHandler.handle(new MissingReturnValueException(f));
        } else if (!lastResult.getClass().equals(PointListValue.class)) {
            errorHandler.handle(new IncompatibleDataTypeException(f, lastResult));
        }
    }

    @Override
    public void visit(SceneFunctionDef f) {
        visit(f.functionCode());
        if (lastResult == null) {
            errorHandler.handle(new MissingReturnValueException(f));
        } else if (!lastResult.getClass().equals(SceneValue.class)) {
            errorHandler.handle(new IncompatibleDataTypeException(f, lastResult));
        }
    }

    @Override
    public void visit(SceneListFunctionDef f) {
        visit(f.functionCode());
        if (lastResult == null) {
            errorHandler.handle(new MissingReturnValueException(f));
        } else if (!lastResult.getClass().equals(SceneListValue.class)) {
            errorHandler.handle(new IncompatibleDataTypeException(f, lastResult));
        }
    }

    @Override
    public void visit(SectionFunctionDef f) {
        visit(f.functionCode());
        if (lastResult == null) {
            errorHandler.handle(new MissingReturnValueException(f));
        } else if (!lastResult.getClass().equals(SectionValue.class)) {
            errorHandler.handle(new IncompatibleDataTypeException(f, lastResult));
        }
    }

    @Override
    public void visit(SectionListFunctionDef f) {
        visit(f.functionCode());
        if (lastResult == null) {
            errorHandler.handle(new MissingReturnValueException(f));
        } else if (!lastResult.getClass().equals(SectionListValue.class)) {
            errorHandler.handle(new IncompatibleDataTypeException(f, lastResult));
        }
    }

    @Override
    public void visit(StringFunctionDef f) {
        visit(f.functionCode());
        if (lastResult == null) {
            errorHandler.handle(new MissingReturnValueException(f));
        } else if (!lastResult.getClass().equals(StringValue.class)) {
            errorHandler.handle(new IncompatibleDataTypeException(f, lastResult));
        }
    }

    @Override
    public void visit(StringListFunctionDef f) {
        visit(f.functionCode());
        if (lastResult == null) {
            errorHandler.handle(new MissingReturnValueException(f));
        } else if (!lastResult.getClass().equals(StringListValue.class)) {
            errorHandler.handle(new IncompatibleDataTypeException(f, lastResult));
        }
    }

    @Override
    public void visit(CodeBlock codeBlock) {
        contextManager.createNewContext();
        for (IStatement s : codeBlock.statements()) {
            visit(s);
            if (returnFound) {
                break;
            }
        }
        contextManager.deleteLastContext();
    }

    private void visit(IStatement stmnt) {
        if (stmnt.getClass().equals(AssignmentStatement.class)) {
            visit((AssignmentStatement) stmnt);
        } else if (stmnt.getClass().equals(IfStatement.class)) {
            visit((IfStatement) stmnt);
        } else if (stmnt.getClass().equals(ReturnStatement.class)) {
            visit((ReturnStatement) stmnt);
        } else if (stmnt.getClass().equals(WhileStatement.class)) {
            visit((WhileStatement) stmnt);
        } else if (stmnt.getClass().equals(FunctionCall.class)) {
            visit((FunctionCall) stmnt);
        } else if (stmnt.getClass().equals(ObjectAccess.class)) {
            visit((ObjectAccess) stmnt);
        }
    }

    // statements
    @Override
    public void visit(AssignmentStatement stmnt) {
        visit(stmnt.exp());
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
        visit(stmnt.exp());
        registerErrorIfLastResultIsNull(stmnt);
        if (isConditionTrue()) {
            ifStatementsDepth++;
            visit(stmnt.codeBlock());
            ifStatementsDepth--;
        } else {
            int previousIfStmntsDepth = ifStatementsDepth;
            for (ElseIfStatement s : stmnt.elseIfStmnts()) {
                visit(s);
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
        visit(stmnt.exp());
        if (isConditionTrue()) {
            ifStatementsDepth++;
            visit(stmnt.codeBlock());
        }
    }

    @Override
    public void visit(ElseStatement stmnt) {
        visit(stmnt.codeBlock());
    }

    @Override
    public void visit(ReturnStatement stmnt) {
        visit(stmnt.exp());
        returnFound = true;
    }

    @Override
    public void visit(WhileStatement stmnt) {
        visit(stmnt.exp());
        registerErrorIfLastResultIsNull(stmnt);
        while (isConditionTrue() && !returnFound) {
            visit(stmnt.codeBlock());
            if (!returnFound) {
                visit(stmnt.exp());
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
        } else if (exp.getClass().equals(IntValue.class)) {
            visit((IntValue) exp);
        } else if (exp.getClass().equals(IntListValue.class)) {
            visit((IntListValue) exp);
        } else if (exp.getClass().equals(DoubleValue.class)) {
            visit((DoubleValue) exp);
        } else if (exp.getClass().equals(DoubleListValue.class)) {
            visit((DoubleListValue) exp);
        } else if (exp.getClass().equals(StringValue.class)) {
            visit((StringValue) exp);
        } else if (exp.getClass().equals(StringListValue.class)) {
            visit((StringListValue) exp);
        } else if (exp.getClass().equals(BoolValue.class)) {
            visit((BoolValue) exp);
        } else if (exp.getClass().equals(BoolListValue.class)) {
            visit((BoolListValue) exp);
        } else if (exp.getClass().equals(FigureValue.class)) {
            visit((FigureValue) exp);
        } else if (exp.getClass().equals(FigureListValue.class)) {
            visit((FigureListValue) exp);
        } else if (exp.getClass().equals(PointValue.class)) {
            visit((PointValue) exp);
        } else if (exp.getClass().equals(PointListValue.class)) {
            visit((PointListValue) exp);
        } else if (exp.getClass().equals(SceneValue.class)) {
            visit((SceneValue) exp);
        } else if (exp.getClass().equals(SceneListValue.class)) {
            visit((SceneListValue) exp);
        } else if (exp.getClass().equals(SectionValue.class)) {
            visit((SectionValue) exp);
        } else if (exp.getClass().equals(SectionListValue.class)) {
            visit((SectionListValue) exp);
        } else if (exp.getClass().equals(AlternativeExpression.class)) {
            visit((AlternativeExpression) exp);
        } else if (exp.getClass().equals(ConjunctiveExpression.class)) {
            visit((ConjunctiveExpression) exp);
        } else if (exp.getClass().equals(EqualExpression.class)) {
            visit((EqualExpression) exp);
        } else if (exp.getClass().equals(GreaterOrEqualExpression.class)) {
            visit((GreaterOrEqualExpression) exp);
        } else if (exp.getClass().equals(GreaterThanExpression.class)) {
            visit((GreaterThanExpression) exp);
        } else if (exp.getClass().equals(NotEqualExpression.class)) {
            visit((NotEqualExpression) exp);
        } else if (exp.getClass().equals(LessOrEqualExpression.class)) {
            visit((LessOrEqualExpression) exp);
        } else if (exp.getClass().equals(LessThanExpression.class)) {
            visit((LessThanExpression) exp);
        } else if (exp.getClass().equals(AdditionExpression.class)) {
            visit((AdditionExpression) exp);
        } else if (exp.getClass().equals(SubtractionExpression.class)) {
            visit((SubtractionExpression) exp);
        } else if (exp.getClass().equals(DiscreteDivisionExpression.class)) {
            visit((DiscreteDivisionExpression) exp);
        } else if (exp.getClass().equals(DivisionExpression.class)) {
            visit((DivisionExpression) exp);
        } else if (exp.getClass().equals(MultiplicationExpression.class)) {
            visit((MultiplicationExpression) exp);
        } else if (exp.getClass().equals(Identifier.class)) {
            visit((Identifier) exp);
        } else if (exp.getClass().equals(FunctionCall.class)) {
            visit((FunctionCall) exp);
        } else if (exp.getClass().equals(ObjectAccess.class)) {
            visit((ObjectAccess) exp);
        } else {
            lastResult = null;
        }
    }

    @Override
    public void visit(IDataValue val) {
        if (val.getClass().equals(PointValue.class)) {
            visit(val);
        } else if (val.getClass().equals(SectionValue.class)) {
            visit((SectionValue) val);
        } else {
            lastResult = val;
        }
    }

    public void visit(PointValue val) {
        visit(val.x());
        IExpression x = (IExpression) lastResult;
        visit(val.y());
        IExpression y = (IExpression) lastResult;
        lastResult = new PointValue(val.position(), x, y);
    }

    public void visit(SectionValue val) {
        visit(val.first());
        IExpression firstExp = (IExpression) lastResult;
        visit(val.second());
        IExpression secondExp = (IExpression) lastResult;
        lastResult = new SectionValue(val.position(), firstExp, secondExp);
    }

    @Override
    public void visit(AlternativeExpression exp) {
        visit(exp.leftExp());
        if (!isConditionTrue()) {
            visit(exp.rightExp());
        }

        saveExpressionBoolResult(exp);
    }

    @Override
    public void visit(ConjunctiveExpression exp) {
        visit(exp.leftExp());
        if (isConditionTrue()) {
            visit(exp.rightExp());
        }

        saveExpressionBoolResult(exp);
    }

    // comparison expressions
    @Override
    public void visit(EqualExpression exp) {
        visit(exp.leftExp());
        IVisitable leftExp = lastResult;
        visit(exp.rightExp());
        boolean comparisonResult = areValuesEqual(leftExp, lastResult);
        lastResult = new BoolValue(exp.position(), comparisonResult);
    }

    @Override
    public void visit(GreaterOrEqualExpression exp) {
        visit(exp.leftExp());
        IVisitable leftExp = lastResult;
        visit(exp.rightExp());
        boolean comparisonResult = (isLeftValueGreaterThanRightValue(leftExp, lastResult)
                || areValuesEqual(leftExp, lastResult));
        lastResult = new BoolValue(exp.position(), comparisonResult);
    }

    @Override
    public void visit(GreaterThanExpression exp) {
        visit(exp.leftExp());
        IVisitable leftExp = lastResult;
        visit(exp.rightExp());
        boolean comparisonResult = isLeftValueGreaterThanRightValue(leftExp, lastResult);
        lastResult = new BoolValue(exp.position(), comparisonResult);
    }

    @Override
    public void visit(LessOrEqualExpression exp) {
        visit(exp.leftExp());
        IVisitable leftExp = lastResult;
        visit(exp.rightExp());
        boolean comparisonResult = (isLeftValueLessThanRightValue(leftExp, lastResult)
                || areValuesEqual(leftExp, lastResult));
        lastResult = new BoolValue(exp.position(), comparisonResult);

    }

    @Override
    public void visit(LessThanExpression exp) {
        visit(exp.leftExp());
        IVisitable leftExp = lastResult;
        visit(exp.rightExp());
        boolean comparisonResult = isLeftValueLessThanRightValue(leftExp, lastResult);
        lastResult = new BoolValue(exp.position(), comparisonResult);
    }

    @Override
    public void visit(NegatedExpression exp) {

    }

    @Override
    public void visit(NotEqualExpression exp) {
        visit(exp.leftExp());
        IVisitable leftExp = lastResult;
        visit(exp.rightExp());
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
        visit(exp.leftExp());
        IVisitable leftExp = lastResult;
        visit(exp.rightExp());

        tryToAddExpressions(exp.position(), leftExp, lastResult);
    }

    @Override
    public void visit(SubtractionExpression exp) {
        visit(exp.leftExp());
        IVisitable leftExp = lastResult;
        visit(exp.rightExp());

        tryToSubtractExpressions(exp.position(), leftExp, lastResult);
    }

    @Override
    public void visit(DiscreteDivisionExpression exp) {
        visit(exp.leftExp());
        IVisitable leftExp = lastResult;
        visit(exp.rightExp());

        tryToDivideDiscretely(exp.position(), leftExp, lastResult);
    }

    @Override
    public void visit(DivisionExpression exp) {
        visit(exp.leftExp());
        IVisitable leftExp = lastResult;
        visit(exp.rightExp());

        tryToDivide(exp.position(), leftExp, lastResult);
    }

    @Override
    public void visit(MultiplicationExpression exp) {
        visit(exp.leftExp());
        IVisitable leftExp = lastResult;
        visit(exp.rightExp());

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
            visit(func);
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
        visit(objectAccess.leftExp());
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
            visit(funcCall.exp());
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

    private void registerErrorIfLastResultIsNull(IStatement stmnt) {
        if (lastResult == null) {
            errorHandler.handle(new NullExpressionException(stmnt));
        }
    }
}
