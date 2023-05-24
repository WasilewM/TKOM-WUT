package visitors;

import parser.*;
import parser.program_components.*;
import parser.program_components.data_values.*;
import parser.program_components.expressions.*;
import parser.program_components.function_definitions.*;
import parser.program_components.parameters.*;
import parser.program_components.statements.*;
import visitors.exceptions.IncompatibleDataTypesException;
import visitors.exceptions.MissingMainFunctionException;
import visitors.exceptions.MissingReturnValueException;
import visitors.exceptions.ParameterNotFoundExceptionException;

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
        }

        deleteLastContext();
    }

    @Override
    public void visit(BoolFunctionDef f) {
        visit(f.functionCode());
        if (lastResult == null) {
            errorHandler.handle(new MissingReturnValueException(f));
        } else if (!lastResult.getClass().equals(BoolValue.class)) {
            errorHandler.handle(new IncompatibleDataTypesException(f, lastResult));
        }
    }

    @Override
    public void visit(BoolListFunctionDef f) {
        visit(f.functionCode());
        if (lastResult == null) {
            errorHandler.handle(new MissingReturnValueException(f));
        } else if (!lastResult.getClass().equals(BoolListValue.class)) {
            errorHandler.handle(new IncompatibleDataTypesException(f, lastResult));
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
            errorHandler.handle(new IncompatibleDataTypesException(f, lastResult));
        }
    }

    @Override
    public void visit(DoubleListFunctionDef f) {
        visit(f.functionCode());
        if (lastResult == null) {
            errorHandler.handle(new MissingReturnValueException(f));
        } else if (!lastResult.getClass().equals(DoubleListValue.class)) {
            errorHandler.handle(new IncompatibleDataTypesException(f, lastResult));
        }
    }

    @Override
    public void visit(FigureFunctionDef f) {
        visit(f.functionCode());
        if (lastResult == null) {
            errorHandler.handle(new MissingReturnValueException(f));
        } else if (!lastResult.getClass().equals(FigureValue.class)) {
            errorHandler.handle(new IncompatibleDataTypesException(f, lastResult));
        }
    }

    @Override
    public void visit(FigureListFunctionDef f) {
        visit(f.functionCode());
        if (lastResult == null) {
            errorHandler.handle(new MissingReturnValueException(f));
        } else if (!lastResult.getClass().equals(FigureListValue.class)) {
            errorHandler.handle(new IncompatibleDataTypesException(f, lastResult));
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
            errorHandler.handle(new IncompatibleDataTypesException(f, lastResult));
        }
    }

    @Override
    public void visit(IntListFunctionDef f) {
        visit((f.functionCode()));
        if (lastResult == null) {
            errorHandler.handle(new MissingReturnValueException(f));
        } else if (!lastResult.getClass().equals(IntListValue.class)) {
            errorHandler.handle(new IncompatibleDataTypesException(f, lastResult));
        }
    }

    @Override
    public void visit(PointFunctionDef f) {

    }

    @Override
    public void visit(PointListFunctionDef f) {

    }

    @Override
    public void visit(SceneFunctionDef f) {

    }

    @Override
    public void visit(SceneListFunctionDef f) {

    }

    @Override
    public void visit(SectionFunctionDef f) {

    }

    @Override
    public void visit(SectionListFunctionDef f) {

    }

    @Override
    public void visit(StringFunctionDef f) {

    }

    @Override
    public void visit(StringListFunctionDef f) {

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
        } else if (stmnt.getClass().equals(ReturnStatement.class)) {
            visit((ReturnStatement) stmnt);
        }
    }

    // statements
    @Override
    public void visit(AssignmentStatement stmnt) {
        if (stmnt.param().getClass().equals(IntParameter.class)) {
            handleIntValueAssignment(stmnt, new IncompatibleDataTypesException(stmnt.param(), stmnt.exp()));
        } else if (stmnt.param().getClass().equals(DoubleParameter.class)) {
            handleDoubleValueAssignment(stmnt, new IncompatibleDataTypesException(stmnt.param(), stmnt.exp()));
        } else if (stmnt.param().getClass().equals(StringParameter.class)) {
            handleParamValueAssignment(stmnt.exp().getClass().equals(StringValue.class), stmnt);
        } else if (stmnt.param().getClass().equals(BoolParameter.class)) {
            handleParamValueAssignment(stmnt.exp().getClass().equals(BoolValue.class), stmnt);
        } else if (stmnt.param().getClass().equals(PointParameter.class)) {
            handleParamValueAssignment(stmnt.exp().getClass().equals(PointValue.class), stmnt);
        } else if (stmnt.param().getClass().equals(SectionParameter.class)) {
            handleParamValueAssignment(stmnt.exp().getClass().equals(SectionValue.class), stmnt);
        } else if (stmnt.param().getClass().equals(FigureParameter.class)) {
            handleParamValueAssignment(stmnt.exp().getClass().equals(FigureValue.class), stmnt);
        } else if (stmnt.param().getClass().equals(SceneParameter.class)) {
            handleParamValueAssignment(stmnt.exp().getClass().equals(SceneValue.class), stmnt);
        } else if (stmnt.param().getClass().equals(IntListParameter.class)) {
            handleParamValueAssignment(stmnt.exp().getClass().equals(IntListValue.class), stmnt);
        } else if (stmnt.param().getClass().equals(DoubleListParameter.class)) {
            handleParamValueAssignment(stmnt.exp().getClass().equals(DoubleListValue.class), stmnt);
        } else if (stmnt.param().getClass().equals(StringListParameter.class)) {
            handleParamValueAssignment(stmnt.exp().getClass().equals(StringListValue.class), stmnt);
        } else if (stmnt.param().getClass().equals(BoolListParameter.class)) {
            handleParamValueAssignment(stmnt.exp().getClass().equals(BoolListValue.class), stmnt);
        } else if (stmnt.param().getClass().equals(PointListParameter.class)) {
            handleParamValueAssignment(stmnt.exp().getClass().equals(PointListValue.class), stmnt);
        } else if (stmnt.param().getClass().equals(SectionListParameter.class)) {
            handleParamValueAssignment(stmnt.exp().getClass().equals(SectionListValue.class), stmnt);
        } else if (stmnt.param().getClass().equals(FigureListParameter.class)) {
            handleParamValueAssignment(stmnt.exp().getClass().equals(FigureListValue.class), stmnt);
        } else if (stmnt.param().getClass().equals(SceneListParameter.class)) {
            handleParamValueAssignment(stmnt.exp().getClass().equals(SceneListValue.class), stmnt);
        } else if (stmnt.param().getClass().equals(ReassignedParameter.class)) {
            handleValueReassignment(stmnt);
        } else {
            errorHandler.handle(new IncompatibleDataTypesException(stmnt.param(), stmnt.exp()));
        }
    }

    private void handleParamValueAssignment(boolean assignmentCondition, AssignmentStatement stmnt) {
        if (assignmentCondition) {
            this.getLastContext().add(stmnt.param().name(), stmnt.exp());
        } else {
            errorHandler.handle(new IncompatibleDataTypesException(stmnt.param(), stmnt.exp()));
        }
    }

    private void handleValueReassignment(AssignmentStatement stmnt) {
        if (!this.getLastContext().containsKey(stmnt.param().name())) {
            errorHandler.handle(new ParameterNotFoundExceptionException(stmnt.param(), stmnt.exp()));
        }

        IExpression value = this.getLastContext().get(stmnt.param().name());
        if (value.getClass().equals(IntValue.class)) {
            handleIntValueAssignment(stmnt, new IncompatibleDataTypesException(value, stmnt.exp()));
        } else if (value.getClass().equals(DoubleValue.class)) {
            handleDoubleValueAssignment(stmnt, new IncompatibleDataTypesException(value, stmnt.exp()));
        } else if (!value.getClass().equals(stmnt.exp().getClass())) {
            errorHandler.handle(new IncompatibleDataTypesException(value, stmnt.exp()));
        } else {
            this.getLastContext().update(stmnt.param().name(), stmnt.exp());
        }
    }

    private void handleIntValueAssignment(AssignmentStatement stmnt, IncompatibleDataTypesException exception) {
        if (stmnt.exp().getClass().equals(IntValue.class)) {
            this.getLastContext().add(stmnt.param().name(), stmnt.exp());
        } else if (stmnt.exp().getClass().equals(DoubleValue.class)) {
            IntValue castedValue = new IntValue(stmnt.exp().position(), ((DoubleValue) stmnt.exp()).value().intValue());
            this.getLastContext().add(stmnt.param().name(), castedValue);
        } else {
            errorHandler.handle(exception);
        }
    }

    private void handleDoubleValueAssignment(AssignmentStatement stmnt, IncompatibleDataTypesException exception) {
        if (stmnt.exp().getClass().equals(DoubleValue.class)) {
            this.getLastContext().add(stmnt.param().name(), stmnt.exp());
        } else if (stmnt.exp().getClass().equals(IntValue.class)) {
            DoubleValue castedValue = new DoubleValue(stmnt.exp().position(), ((IntValue) stmnt.exp()).value().doubleValue());
            this.getLastContext().add(stmnt.param().name(), castedValue);
        } else {
            errorHandler.handle(exception);
        }
    }

    @Override
    public void visit(ElseIfStatement stmnt) {

    }

    @Override
    public void visit(ElseStatement stmnt) {

    }

    @Override
    public void visit(IfStatement stmnt) {

    }

    @Override
    public void visit(ReturnStatement stmnt) {
        visit(stmnt.exp());
        returnFound = true;
    }

    @Override
    public void visit(WhileStatement stmnt) {

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
        } else if (exp.getClass().equals(BoolValue.class)) {
            visit((BoolValue) exp);
        } else if (exp.getClass().equals(BoolListValue.class)) {
            visit((BoolListValue) exp);
        } else if (exp.getClass().equals(FigureValue.class)) {
            visit((FigureValue) exp);
        } else if (exp.getClass().equals(FigureListValue.class)) {
            visit((FigureListValue) exp);
        }
    }

    @Override
    public void visit(AdditionExpression exp) {

    }

    @Override
    public void visit(AlternativeExpression exp) {

    }

    @Override
    public void visit(ConjunctiveExpression exp) {

    }

    @Override
    public void visit(DiscreteDivisionExpression exp) {

    }

    @Override
    public void visit(DivisionExpression exp) {

    }

    @Override
    public void visit(EqualExpression exp) {

    }

    @Override
    public void visit(GreaterOrEqualExpression exp) {

    }

    @Override
    public void visit(GreaterThanExpression exp) {

    }

    @Override
    public void visit(LessOrEqualExpression exp) {

    }

    @Override
    public void visit(LessThanExpression exp) {

    }

    @Override
    public void visit(MultiplicationExpression exp) {

    }

    @Override
    public void visit(NegatedExpression exp) {

    }

    @Override
    public void visit(NotEqualExpression exp) {

    }

    @Override
    public void visit(ParenthesesExpression exp) {

    }

    @Override
    public void visit(SubtractionExpression exp) {

    }

    // values
    @Override
    public void visit(IDataValue val) {
        lastResult = val;
    }

    // parameters
    @Override
    public void visit(IParameter p) {

    }

    @Override
    public void visit(BoolListParameter boolListParameter) {

    }

    @Override
    public void visit(BoolParameter boolParameter) {

    }

    @Override
    public void visit(DoubleListParameter doubleListParameter) {

    }

    @Override
    public void visit(DoubleParameter doubleParameter) {

    }

    @Override
    public void visit(FigureListParameter figureListParameter) {

    }

    @Override
    public void visit(FigureParameter figureParameter) {

    }

    @Override
    public void visit(IntListParameter intListParameter) {

    }

    @Override
    public void visit(IntParameter intParameter) {

    }

    @Override
    public void visit(PointListParameter pointListParameter) {

    }

    @Override
    public void visit(PointParameter pointParameter) {

    }

    @Override
    public void visit(ReassignedParameter reassignedParameter) {

    }

    @Override
    public void visit(SceneListParameter sceneListParameter) {

    }

    @Override
    public void visit(SceneParameter sceneParameter) {

    }

    @Override
    public void visit(SectionListParameter sectionListParameter) {

    }

    @Override
    public void visit(SectionParameter sectionParameter) {

    }

    @Override
    public void visit(StringListParameter stringListParameter) {

    }

    @Override
    public void visit(StringParameter stringParameter) {

    }

    // other components
    @Override
    public void visit(FunctionCall functionCall) {

    }

    @Override
    public void visit(Identifier identifier) {

    }

    @Override
    public void visit(ObjectAccess objectAccess) {

    }

    //
    protected void createNewContext() {
        contexts.add(new Context());
    }

    protected void deleteLastContext() {
        contexts.pop();
    }
}
