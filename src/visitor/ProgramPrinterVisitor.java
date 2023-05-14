package visitor;

import parser.IFunctionDef;
import parser.program_components.*;
import parser.program_components.data_values.*;
import parser.program_components.expressions.*;
import parser.program_components.parameters.*;
import parser.program_components.statements.*;

public class ProgramPrinterVisitor implements IVisitor {
    private String prefix = "";

    @Override
    public void visit(BoolListValue boolListValue) {

    }

    @Override
    public void visit(BoolValue boolValue) {

    }

    @Override
    public void visit(DoubleListValue doubleListValue) {

    }

    @Override
    public void visit(DoubleValue doubleValue) {

    }

    @Override
    public void visit(FigureListValue figureListValue) {

    }

    @Override
    public void visit(FigureValue figureValue) {

    }

    @Override
    public void visit(IntListValue intListValue) {

    }

    @Override
    public void visit(IntValue intValue) {

    }

    @Override
    public void visit(PointListValue pointListValue) {

    }

    @Override
    public void visit(PointValue pointValue) {

    }

    @Override
    public void visit(SceneListValue sceneListValue) {

    }

    @Override
    public void visit(SceneValue sceneValue) {

    }

    @Override
    public void visit(SectionListValue sectionListValue) {

    }

    @Override
    public void visit(SectionValue sectionValue) {

    }

    @Override
    public void visit(StringListValue stringListValue) {

    }

    @Override
    public void visit(StringValue stringValue) {

    }

    @Override
    public void visit(AdditionExpression additionExpression) {

    }

    @Override
    public void visit(AlternativeExpression alternativeExpression) {

    }

    @Override
    public void visit(ConjunctiveExpression conjunctiveExpression) {

    }

    @Override
    public void visit(DiscreteDivisionExpression discreteDivisionExpression) {

    }

    @Override
    public void visit(DivisionExpression divisionExpression) {

    }

    @Override
    public void visit(EqualExpression equalExpression) {

    }

    @Override
    public void visit(GreaterOrEqualExpression greaterOrEqualExpression) {

    }

    @Override
    public void visit(GreaterThanExpression greaterThanExpression) {

    }

    @Override
    public void visit(LessOrEqualExpression lessOrEqualExpression) {

    }

    @Override
    public void visit(LessThanExpression lessThanExpression) {

    }

    @Override
    public void visit(MultiplicationExpression multiplicationExpression) {

    }

    @Override
    public void visit(NegatedExpression negatedExpression) {

    }

    @Override
    public void visit(NotEqualExpression notEqualExpression) {

    }

    @Override
    public void visit(ParenthesesExpression parenthesesExpression) {

    }

    @Override
    public void visit(SubtractionExpression subtractionExpression) {

    }

    @Override
    public void visit(IFunctionDef func) {
        printWithPrefix(func.getClass() + " " + func.name() + " at " + func.position().toString());
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

    @Override
    public void visit(AssignmentStatement assignmentStatement) {

    }

    @Override
    public void visit(ElseIfStatement elseIfStatement) {

    }

    @Override
    public void visit(ElseStatement elseStatement) {

    }

    @Override
    public void visit(IfStatement ifStatement) {

    }

    @Override
    public void visit(ReturnStatement returnStatement) {

    }

    @Override
    public void visit(WhileStatement whileStatement) {

    }

    @Override
    public void visit(CodeBlock codeBlock) {

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
        prefix += "  ";
        for (IFunctionDef f : program.functions().values()) {
            visit(f);
        }
        prefix = prefix.substring(0, prefix.length() - 2);
    }

    private void printWithPrefix(Object program) {
        System.out.println(prefix + program.toString());
    }
}
