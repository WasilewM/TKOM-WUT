package visitors;

import parser.IDataValue;
import parser.IParameter;
import parser.program_components.*;
import parser.program_components.expressions.*;
import parser.program_components.function_definitions.*;
import parser.program_components.statements.*;

public interface IVisitor {
    void visit(IDataValue val);

    // expressions
    void visit(AdditionExpression additionExpression);

    void visit(AlternativeExpression alternativeExpression);

    void visit(ConjunctiveExpression conjunctiveExpression);

    void visit(DiscreteDivisionExpression discreteDivisionExpression);

    void visit(DivisionExpression divisionExpression);

    void visit(EqualExpression equalExpression);

    void visit(GreaterOrEqualExpression greaterOrEqualExpression);

    void visit(GreaterThanExpression greaterThanExpression);

    void visit(LessOrEqualExpression lessOrEqualExpression);

    void visit(LessThanExpression lessThanExpression);

    void visit(MultiplicationExpression multiplicationExpression);

    void visit(NegatedExpression negatedExpression);

    void visit(NotEqualExpression notEqualExpression);

    void visit(ParenthesesExpression parenthesesExpression);

    void visit(SubtractionExpression subtractionExpression);


    // function_definitions
    void visit(BoolFunctionDef boolFunctionDef);

    void visit(BoolListFunctionDef boolListFunctionDef);

    void visit(DoubleFunctionDef doubleFunctionDef);

    void visit(DoubleListFunctionDef doubleListFunctionDef);

    void visit(FigureFunctionDef figureFunctionDef);

    void visit(FigureListFunctionDef figureListFunctionDef);

    void visit(IntFunctionDef intFunctionDef);

    void visit(IntListFunctionDef intListFunctionDef);

    void visit(PointFunctionDef pointFunctionDef);

    void visit(PointListFunctionDef pointListFunctionDef);

    void visit(SceneFunctionDef sceneFunctionDef);

    void visit(SceneListFunctionDef sceneListFunctionDef);

    void visit(SectionFunctionDef sectionFunctionDef);

    void visit(SectionListFunctionDef sectionListFunctionDef);

    void visit(StringFunctionDef stringFunctionDef);

    void visit(StringListFunctionDef stringListFunctionDef);


    // parameters
    void visit(IParameter p);


    // statements
    void visit(AssignmentStatement assignmentStatement);

    void visit(ElseIfStatement elseIfStatement);

    void visit(ElseStatement elseStatement);

    void visit(IfStatement ifStatement);

    void visit(ReturnStatement returnStatement);

    void visit(WhileStatement whileStatement);


    // other
    void visit(CodeBlock codeBlock);

    void visit(FunctionCall functionCall);

    void visit(Identifier identifier);

    void visit(ObjectAccess objectAccess);

    void visit(Program program);
}
