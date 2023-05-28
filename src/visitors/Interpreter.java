package visitors;

import lexer.Position;
import parser.*;
import parser.program_components.*;
import parser.program_components.data_values.*;
import parser.program_components.expressions.*;
import parser.program_components.function_definitions.*;
import parser.program_components.parameters.*;
import parser.program_components.statements.*;
import visitors.exceptions.*;

import java.util.Map;
import java.util.Stack;

public class Interpreter implements IVisitor {
    private final IErrorHandler errorHandler;
    private final Stack<Context> contexts;

    private IExpression lastResult;

    private boolean returnFound;

    public Interpreter(IErrorHandler errorHandler) {
        this.errorHandler = errorHandler;
        contexts = new Stack<>();
        lastResult = null;
        returnFound = false;
    }

    public Context getLastContext() {
        if (contexts.isEmpty()) {
            return null;
        }
        return contexts.peek();
    }

    public IExpression getLastResult() {
        return lastResult;
    }

    @Override
    public void visit(Program program) {
        if (!program.functions().containsKey("main")) {
            errorHandler.handle(new MissingMainFunctionException(program.functions()));
        }

        IFunctionDef mainFunction = program.functions().get("main");
        visit(mainFunction);
        program.functions().remove("main");

        for (Map.Entry<String, IFunctionDef> f : program.functions().entrySet()) {
            visit(f.getValue());
        }
    }

    @Override
    public void visit(IFunctionDef f) {
        createNewContext();

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

        deleteLastContext();
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
        for (IStatement s : codeBlock.statements()) {
            if (!returnFound) {
                visit(s);
            } else {
                break;
            }
        }
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
        }
    }

    // statements
    @Override
    public void visit(AssignmentStatement stmnt) {
        visit(stmnt.exp());
        if (stmnt.param().getClass().equals(IntParameter.class)) {
            handleIntValueAssignment(stmnt, new IncompatibleDataTypeException(stmnt.param(), lastResult));
        } else if (stmnt.param().getClass().equals(DoubleParameter.class)) {
            handleDoubleValueAssignment(stmnt, new IncompatibleDataTypeException(stmnt.param(), lastResult));
        } else if (stmnt.param().getClass().equals(StringParameter.class)) {
            handleParamValueAssignment(lastResult.getClass().equals(StringValue.class), stmnt);
        } else if (stmnt.param().getClass().equals(BoolParameter.class)) {
            handleParamValueAssignment(lastResult.getClass().equals(BoolValue.class), stmnt);
        } else if (stmnt.param().getClass().equals(PointParameter.class)) {
            handleParamValueAssignment(lastResult.getClass().equals(PointValue.class), stmnt);
        } else if (stmnt.param().getClass().equals(SectionParameter.class)) {
            handleParamValueAssignment(lastResult.getClass().equals(SectionValue.class), stmnt);
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
            this.getLastContext().add(stmnt.param().name(), lastResult);
        } else {
            errorHandler.handle(new IncompatibleDataTypeException(stmnt.param(), lastResult));
        }
    }

    private void handleValueReassignment(AssignmentStatement stmnt) {
        if (!this.getLastContext().containsKey(stmnt.param().name())) {
            errorHandler.handle(new ParameterNotFoundException(stmnt.param(), lastResult));
        }

        IExpression value = this.getLastContext().get(stmnt.param().name());
        if (value.getClass().equals(IntValue.class)) {
            handleIntValueAssignment(stmnt, new IncompatibleDataTypeException(value, lastResult));
        } else if (value.getClass().equals(DoubleValue.class)) {
            handleDoubleValueAssignment(stmnt, new IncompatibleDataTypeException(value, lastResult));
        } else if (!value.getClass().equals(lastResult.getClass())) {
            errorHandler.handle(new IncompatibleDataTypeException(value, lastResult));
        } else {
            this.getLastContext().update(stmnt.param().name(), lastResult);
        }
    }

    private void handleIntValueAssignment(AssignmentStatement stmnt, IncompatibleDataTypeException exception) {
        if (lastResult.getClass().equals(IntValue.class)) {
            this.getLastContext().add(stmnt.param().name(), lastResult);
        } else if (lastResult.getClass().equals(DoubleValue.class)) {
            IntValue castedValue = new IntValue(lastResult.position(), ((DoubleValue) lastResult).value().intValue());
            this.getLastContext().add(stmnt.param().name(), castedValue);
        } else {
            errorHandler.handle(exception);
        }
    }

    private void handleDoubleValueAssignment(AssignmentStatement stmnt, IncompatibleDataTypeException exception) {
        if (lastResult.getClass().equals(DoubleValue.class)) {
            this.getLastContext().add(stmnt.param().name(), lastResult);
        } else if (lastResult.getClass().equals(IntValue.class)) {
            DoubleValue castedValue = new DoubleValue(lastResult.position(), ((IntValue) lastResult).value().doubleValue());
            this.getLastContext().add(stmnt.param().name(), castedValue);
        } else {
            errorHandler.handle(exception);
        }
    }

    @Override
    public void visit(IfStatement stmnt) {
        visit(stmnt.exp());
        registerErrorIfLastResultIsNull(stmnt);
        if (isConditionTrue()) {
            visit(stmnt.codeBlock());
        }
    }

    @Override
    public void visit(ElseIfStatement stmnt) {

    }

    @Override
    public void visit(ElseStatement stmnt) {

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
        } else {
            lastResult = null;
        }
    }

    @Override
    public void visit(IDataValue val) {
        lastResult = val;
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
        IExpression leftExp = lastResult;
        visit(exp.rightExp());
        boolean comparisonResult = areValuesEqual(leftExp, lastResult);
        lastResult = new BoolValue(exp.position(), comparisonResult);
    }

    @Override
    public void visit(GreaterOrEqualExpression exp) {
        visit(exp.leftExp());
        IExpression leftExp = lastResult;
        visit(exp.rightExp());
        boolean comparisonResult = (isLeftValueGreaterThanRightValue(leftExp, lastResult)
                || areValuesEqual(leftExp, lastResult));
        lastResult = new BoolValue(exp.position(), comparisonResult);
    }

    @Override
    public void visit(GreaterThanExpression exp) {
        visit(exp.leftExp());
        IExpression leftExp = lastResult;
        visit(exp.rightExp());
        boolean comparisonResult = isLeftValueGreaterThanRightValue(leftExp, lastResult);
        lastResult = new BoolValue(exp.position(), comparisonResult);
    }

    @Override
    public void visit(LessOrEqualExpression exp) {
        visit(exp.leftExp());
        IExpression leftExp = lastResult;
        visit(exp.rightExp());
        boolean comparisonResult = (isLeftValueLessThanRightValue(leftExp, lastResult)
                || areValuesEqual(leftExp, lastResult));
        lastResult = new BoolValue(exp.position(), comparisonResult);

    }

    @Override
    public void visit(LessThanExpression exp) {
        visit(exp.leftExp());
        IExpression leftExp = lastResult;
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
        IExpression leftExp = lastResult;
        visit(exp.rightExp());
        boolean comparisonResult = !(areValuesEqual(leftExp, lastResult));
        lastResult = new BoolValue(exp.position(), comparisonResult);
    }

    private boolean isLeftValueLessThanRightValue(IExpression leftExp, IExpression rightExp) {
        try {
            if (leftExp.getClass().equals(rightExp.getClass())) {
                return (!(areValuesEqual(leftExp, rightExp)) && !(isLeftValueGreaterThanRightValue(leftExp, rightExp)));
            }
        } catch (Exception e) {
            errorHandler.handle(new OperationDataTypeException(leftExp, rightExp));
        }
        return false;
    }

    private boolean areValuesEqual(IExpression leftExp, IExpression rightExp) {
        try {
            IDataValue leftValue = (IDataValue) leftExp;
            IDataValue rightValue = (IDataValue) rightExp;
            return leftValue.value().equals(rightValue.value());
        } catch (Exception e) {
            errorHandler.handle(new OperationDataTypeException(leftExp, rightExp));
        }
        return false;
    }

    private boolean isLeftValueGreaterThanRightValue(IExpression leftExp, IExpression rightExp) {
        try {
            IDataValue leftValue = (IDataValue) leftExp;
            IDataValue rightValue = (IDataValue) rightExp;
            if (leftValue.getClass().equals(IntValue.class) && rightValue.getClass().equals(IntValue.class)) {
                return ((IntValue) leftValue).value() > ((IntValue) rightValue).value();
            } else if (leftValue.getClass().equals(DoubleValue.class) && rightValue.getClass().equals(DoubleValue.class)) {
                return ((DoubleValue) leftValue).value() > ((DoubleValue) rightValue).value();
            }
            return false;
        } catch (Exception e) {
            errorHandler.handle(new OperationDataTypeException(leftExp, rightExp));
        }
        return false;
    }

    // arithmetic expressions
    @Override
    public void visit(AdditionExpression exp) {
        visit(exp.leftExp());
        IExpression leftExp = lastResult;
        visit(exp.rightExp());

        tryToAddExpressions(exp.position(), leftExp, lastResult);
    }

    @Override
    public void visit(SubtractionExpression exp) {
        visit(exp.leftExp());
        IExpression leftExp = lastResult;
        visit(exp.rightExp());

        tryToSubtractExpressions(exp.position(), leftExp, lastResult);
    }

    @Override
    public void visit(DiscreteDivisionExpression exp) {
        visit(exp.leftExp());
        IExpression leftExp = lastResult;
        visit(exp.rightExp());

        tryToDivideDiscretely(exp.position(), leftExp, lastResult);
    }

    @Override
    public void visit(DivisionExpression exp) {
        visit(exp.leftExp());
        IExpression leftExp = lastResult;
        visit(exp.rightExp());

        tryToDivide(exp.position(), leftExp, lastResult);
    }

    @Override
    public void visit(MultiplicationExpression exp) {
        visit(exp.leftExp());
        IExpression leftExp = lastResult;
        visit(exp.rightExp());

        tryToMultiply(exp.position(), leftExp, lastResult);
    }

    @Override
    public void visit(ParenthesesExpression exp) {

    }

    private void tryToAddExpressions(Position position, IExpression leftExp, IExpression rightExp) {
        if (leftExp.getClass().equals(IntValue.class) && rightExp.getClass().equals(IntValue.class)) {
            DoubleValue leftCastedValue = new DoubleValue(leftExp.position(), ((IntValue) leftExp).value().doubleValue());
            DoubleValue rightCastedValue = new DoubleValue(rightExp.position(), ((IntValue) rightExp).value().doubleValue());
            lastResult = new DoubleValue(position, leftCastedValue.value() + rightCastedValue.value());
        } else if (leftExp.getClass().equals(IntValue.class) && rightExp.getClass().equals(DoubleValue.class)) {
            DoubleValue leftCastedValue = new DoubleValue(leftExp.position(), ((IntValue) leftExp).value().doubleValue());
            DoubleValue rightCastedValue = (DoubleValue) rightExp;
            lastResult = new DoubleValue(position, leftCastedValue.value() + rightCastedValue.value());
        } else if (leftExp.getClass().equals(DoubleValue.class) && rightExp.getClass().equals(IntValue.class)) {
            DoubleValue leftCastedValue = (DoubleValue) leftExp;
            DoubleValue rightCastedValue = new DoubleValue(leftExp.position(), ((IntValue) rightExp).value().doubleValue());
            lastResult = new DoubleValue(position, leftCastedValue.value() + rightCastedValue.value());
        } else if (leftExp.getClass().equals(DoubleValue.class) && rightExp.getClass().equals(DoubleValue.class)) {
            DoubleValue leftCastedValue = (DoubleValue) leftExp;
            DoubleValue rightCastedValue = (DoubleValue) rightExp;
            lastResult = new DoubleValue(position, leftCastedValue.value() + rightCastedValue.value());
        } else {
            errorHandler.handle(new OperationDataTypeException(position, leftExp, rightExp));
        }
    }

    private void tryToSubtractExpressions(Position position, IExpression leftExp, IExpression rightExp) {
        if (leftExp.getClass().equals(IntValue.class) && rightExp.getClass().equals(IntValue.class)) {
            DoubleValue leftCastedValue = new DoubleValue(leftExp.position(), ((IntValue) leftExp).value().doubleValue());
            DoubleValue rightCastedValue = new DoubleValue(rightExp.position(), ((IntValue) rightExp).value().doubleValue());
            lastResult = new DoubleValue(position, leftCastedValue.value() - rightCastedValue.value());
        } else if (leftExp.getClass().equals(IntValue.class) && rightExp.getClass().equals(DoubleValue.class)) {
            DoubleValue leftCastedValue = new DoubleValue(leftExp.position(), ((IntValue) leftExp).value().doubleValue());
            DoubleValue rightCastedValue = (DoubleValue) rightExp;
            lastResult = new DoubleValue(position, leftCastedValue.value() - rightCastedValue.value());
        } else if (leftExp.getClass().equals(DoubleValue.class) && rightExp.getClass().equals(IntValue.class)) {
            DoubleValue leftCastedValue = (DoubleValue) leftExp;
            DoubleValue rightCastedValue = new DoubleValue(leftExp.position(), ((IntValue) rightExp).value().doubleValue());
            lastResult = new DoubleValue(position, leftCastedValue.value() - rightCastedValue.value());
        } else if (leftExp.getClass().equals(DoubleValue.class) && rightExp.getClass().equals(DoubleValue.class)) {
            DoubleValue leftCastedValue = (DoubleValue) leftExp;
            DoubleValue rightCastedValue = (DoubleValue) rightExp;
            lastResult = new DoubleValue(position, leftCastedValue.value() - rightCastedValue.value());
        } else {
            errorHandler.handle(new OperationDataTypeException(position, leftExp, rightExp));
        }
    }

    private void tryToDivideDiscretely(Position position, IExpression leftExp, IExpression rightExp) {
        if (leftExp.getClass().equals(IntValue.class) && rightExp.getClass().equals(IntValue.class)) {
            IntValue leftCastedValue = (IntValue) leftExp;
            IntValue rightCastedValue = (IntValue) rightExp;
            int operationResult = leftCastedValue.value() / rightCastedValue.value();
            lastResult = new IntValue(position, operationResult);
        } else if (leftExp.getClass().equals(IntValue.class) && rightExp.getClass().equals(DoubleValue.class)) {
            IntValue leftCastedValue = (IntValue) leftExp;
            IntValue rightCastedValue = new IntValue(rightExp.position(), ((DoubleValue) rightExp).value().intValue());
            int operationResult = leftCastedValue.value() / rightCastedValue.value();
            lastResult = new IntValue(position, operationResult);
        } else if (leftExp.getClass().equals(DoubleValue.class) && rightExp.getClass().equals(IntValue.class)) {
            IntValue leftCastedValue = new IntValue(rightExp.position(), ((DoubleValue) leftExp).value().intValue());
            IntValue rightCastedValue = (IntValue) rightExp;
            int operationResult = leftCastedValue.value() / rightCastedValue.value();
            lastResult = new IntValue(position, operationResult);
        } else if (leftExp.getClass().equals(DoubleValue.class) && rightExp.getClass().equals(DoubleValue.class)) {
            IntValue leftCastedValue = new IntValue(rightExp.position(), ((DoubleValue) leftExp).value().intValue());
            IntValue rightCastedValue = new IntValue(rightExp.position(), ((DoubleValue) rightExp).value().intValue());
            int operationResult = leftCastedValue.value() / rightCastedValue.value();
            lastResult = new IntValue(position, operationResult);
        } else {
            errorHandler.handle(new OperationDataTypeException(position, leftExp, rightExp));
        }
    }

    private void tryToDivide(Position position, IExpression leftExp, IExpression rightExp) {
        if (leftExp.getClass().equals(IntValue.class) && rightExp.getClass().equals(IntValue.class)) {
            DoubleValue leftCastedValue = new DoubleValue(leftExp.position(), ((IntValue) leftExp).value().doubleValue());
            DoubleValue rightCastedValue = new DoubleValue(rightExp.position(), ((IntValue) rightExp).value().doubleValue());
            lastResult = new DoubleValue(position, leftCastedValue.value() / rightCastedValue.value());
        } else if (leftExp.getClass().equals(IntValue.class) && rightExp.getClass().equals(DoubleValue.class)) {
            DoubleValue leftCastedValue = new DoubleValue(leftExp.position(), ((IntValue) leftExp).value().doubleValue());
            DoubleValue rightCastedValue = (DoubleValue) rightExp;
            lastResult = new DoubleValue(position, leftCastedValue.value() / rightCastedValue.value());
        } else if (leftExp.getClass().equals(DoubleValue.class) && rightExp.getClass().equals(IntValue.class)) {
            DoubleValue leftCastedValue = (DoubleValue) leftExp;
            DoubleValue rightCastedValue = new DoubleValue(leftExp.position(), ((IntValue) rightExp).value().doubleValue());
            lastResult = new DoubleValue(position, leftCastedValue.value() / rightCastedValue.value());
        } else if (leftExp.getClass().equals(DoubleValue.class) && rightExp.getClass().equals(DoubleValue.class)) {
            DoubleValue leftCastedValue = (DoubleValue) leftExp;
            DoubleValue rightCastedValue = (DoubleValue) rightExp;
            lastResult = new DoubleValue(position, leftCastedValue.value() / rightCastedValue.value());
        } else {
            errorHandler.handle(new OperationDataTypeException(position, leftExp, rightExp));
        }
    }

    private void tryToMultiply(Position position, IExpression leftExp, IExpression rightExp) {
        if (leftExp.getClass().equals(IntValue.class) && rightExp.getClass().equals(IntValue.class)) {
            DoubleValue leftCastedValue = new DoubleValue(leftExp.position(), ((IntValue) leftExp).value().doubleValue());
            DoubleValue rightCastedValue = new DoubleValue(rightExp.position(), ((IntValue) rightExp).value().doubleValue());
            lastResult = new DoubleValue(position, leftCastedValue.value() * rightCastedValue.value());
        } else if (leftExp.getClass().equals(IntValue.class) && rightExp.getClass().equals(DoubleValue.class)) {
            DoubleValue leftCastedValue = new DoubleValue(leftExp.position(), ((IntValue) leftExp).value().doubleValue());
            DoubleValue rightCastedValue = (DoubleValue) rightExp;
            lastResult = new DoubleValue(position, leftCastedValue.value() * rightCastedValue.value());
        } else if (leftExp.getClass().equals(DoubleValue.class) && rightExp.getClass().equals(IntValue.class)) {
            DoubleValue leftCastedValue = (DoubleValue) leftExp;
            DoubleValue rightCastedValue = new DoubleValue(leftExp.position(), ((IntValue) rightExp).value().doubleValue());
            lastResult = new DoubleValue(position, leftCastedValue.value() * rightCastedValue.value());
        } else if (leftExp.getClass().equals(DoubleValue.class) && rightExp.getClass().equals(DoubleValue.class)) {
            DoubleValue leftCastedValue = (DoubleValue) leftExp;
            DoubleValue rightCastedValue = (DoubleValue) rightExp;
            lastResult = new DoubleValue(position, leftCastedValue.value() * rightCastedValue.value());
        } else {
            errorHandler.handle(new OperationDataTypeException(position, leftExp, rightExp));
        }
    }

    // parameters
    @Override
    public void visit(IParameter param) {

    }

    @Override
    public void visit(BoolListParameter param) {

    }

    @Override
    public void visit(BoolParameter param) {

    }

    @Override
    public void visit(DoubleListParameter param) {

    }

    @Override
    public void visit(DoubleParameter param) {

    }

    @Override
    public void visit(FigureListParameter param) {

    }

    @Override
    public void visit(FigureParameter param) {

    }

    @Override
    public void visit(IntListParameter param) {

    }

    @Override
    public void visit(IntParameter param) {

    }

    @Override
    public void visit(PointListParameter param) {

    }

    @Override
    public void visit(PointParameter param) {

    }

    @Override
    public void visit(ReassignedParameter param) {

    }

    @Override
    public void visit(SceneListParameter param) {

    }

    @Override
    public void visit(SceneParameter param) {

    }

    @Override
    public void visit(SectionListParameter param) {

    }

    @Override
    public void visit(SectionParameter param) {

    }

    @Override
    public void visit(StringListParameter param) {

    }

    @Override
    public void visit(StringParameter param) {

    }

    // other components
    @Override
    public void visit(FunctionCall functionCall) {

    }

    @Override
    public void visit(Identifier identifier) {
        if (!this.getLastContext().containsKey(identifier.name())) {
            errorHandler.handle(new IdentifierNotFoundException(identifier));
        }

        lastResult = this.getLastContext().get(identifier.name());
    }

    @Override
    public void visit(ObjectAccess objectAccess) {

    }

    // utils
    protected void createNewContext() {
        contexts.add(new Context());
    }

    protected void deleteLastContext() {
        contexts.pop();
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
