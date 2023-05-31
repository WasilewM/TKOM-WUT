package visitors;

import parser.*;
import parser.program_components.*;
import parser.program_components.expressions.*;
import parser.program_components.function_definitions.*;
import parser.program_components.statements.*;

public class ProgramPrinterVisitor implements IVisitor {
    private String prefix = "";


    // values
    @Override
    public void visit(IDataValue val) {

    }


    // expressions
    public void visit(IExpression exp) {

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

    // function definitions
//    @Override
    public void visit(IFunctionDef func) {
        printWithPrefix(func.getClass() + " \"" + func.name() + "\" at " + func.position().toString());
        increaseIntend();
        for (IParameter p : func.parameters().values()) {
            visit(p);
        }
        visit(func.functionCode());
        decreaseIntend();
    }

    @Override
    public void visit(BoolFunctionDef boolFunctionDef) {

    }

    @Override
    public void visit(BoolListFunctionDef boolListFunctionDef) {

    }

    @Override
    public void visit(DoubleFunctionDef doubleFunctionDef) {

    }

    @Override
    public void visit(DoubleListFunctionDef doubleListFunctionDef) {

    }

    @Override
    public void visit(FigureFunctionDef figureFunctionDef) {

    }

    @Override
    public void visit(FigureListFunctionDef figureListFunctionDef) {

    }

    @Override
    public void visit(IntFunctionDef intFunctionDef) {

    }

    @Override
    public void visit(IntListFunctionDef intListFunctionDef) {

    }

    @Override
    public void visit(PointFunctionDef pointFunctionDef) {

    }

    @Override
    public void visit(PointListFunctionDef pointListFunctionDef) {

    }

    @Override
    public void visit(SceneFunctionDef sceneFunctionDef) {

    }

    @Override
    public void visit(SceneListFunctionDef sceneListFunctionDef) {

    }

    @Override
    public void visit(SectionFunctionDef sectionFunctionDef) {

    }

    @Override
    public void visit(SectionListFunctionDef sectionListFunctionDef) {

    }

    @Override
    public void visit(StringFunctionDef stringFunctionDef) {

    }

    @Override
    public void visit(StringListFunctionDef stringListFunctionDef) {

    }

    // parameters
    @Override
    public void visit(IParameter param) {
        printWithPrefix(param.getClass() + " \"" + param.name() + "\" at " + param.position().toString());
    }

    // statements
    public void visit(IStatement stmnt) {
        if (stmnt.getClass().equals(AssignmentStatement.class)) {
            visit((AssignmentStatement) stmnt);
        } else if (stmnt.getClass().equals(ElseIfStatement.class)) {
            visit((ElseIfStatement) stmnt);
        } else if (stmnt.getClass().equals(ElseStatement.class)) {
            visit((ElseStatement) stmnt);
        } else if (stmnt.getClass().equals(IfStatement.class)) {
            visit((IfStatement) stmnt);
        } else if (stmnt.getClass().equals(ReturnStatement.class)) {
            visit((ReturnStatement) stmnt);
        } else if (stmnt.getClass().equals(WhileStatement.class)) {
            visit((WhileStatement) stmnt);
        } else {
            visit((ObjectAccess) stmnt);
        }
    }

    @Override
    public void visit(AssignmentStatement stmnt) {
        printWithPrefix(stmnt.getClass() + " at " + stmnt.position().toString());
        increaseIntend();
        visit(stmnt.exp());
        decreaseIntend();
    }

    @Override
    public void visit(ElseIfStatement stmnt) {
        printWithPrefix(stmnt.getClass() + " at " + stmnt.position().toString());
        increaseIntend();
        visit(stmnt.exp());
        visit(stmnt.codeBlock());
        decreaseIntend();

    }

    @Override
    public void visit(ElseStatement stmnt) {
        printWithPrefix(stmnt.getClass() + " at " + stmnt.position().toString());
        increaseIntend();
        visit(stmnt.codeBlock());
        decreaseIntend();
    }

    @Override
    public void visit(IfStatement stmnt) {
        printWithPrefix(stmnt.getClass() + " at " + stmnt.position().toString());
        increaseIntend();
        visit(stmnt.exp());
        visit(stmnt.codeBlock());

        increaseIntend();
        for (ElseIfStatement e : stmnt.elseIfStmnts()) {
            visit(e);
        }
        visit(stmnt.elseStmnt());
        decreaseIntend();

        decreaseIntend();
    }

    @Override
    public void visit(ReturnStatement stmnt) {

    }

    @Override
    public void visit(WhileStatement stmnt) {

    }

    // other
    @Override
    public void visit(CodeBlock block) {
        printWithPrefix(block.getClass() + " at " + block.position().toString());
        String previousPrefix = prefix;
        increaseIntend();

        for (IStatement stmnt : block.statements()) {
            visit(stmnt);
        }

        prefix = previousPrefix;
    }

    @Override
    public void visit(FunctionCall functionCall) {

    }

    @Override
    public void visit(Identifier identifier) {

    }

    @Override
    public void visit(ObjectAccess objectAccess) {

    }

    @Override
    public void visit(Program program) {
        printWithPrefix(program.getClass() + " " + program.position().toString());
        String previousPrefix = prefix;
        increaseIntend();
        for (IFunctionDef f : program.functions().values()) {
            visit(f);
        }
        decreaseIntend();
    }

    private void increaseIntend() {
        prefix += "  ";
    }

    private void decreaseIntend() {
        prefix = prefix.substring(0, prefix.length() - 2);
    }

    private void printWithPrefix(Object program) {
        System.out.println(prefix + program.toString());
    }
}
