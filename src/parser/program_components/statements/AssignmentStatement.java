package parser.program_components.statements;

import parser.IExpression;
import parser.IStatement;
import parser.IVisitor;
import parser.program_components.Parameter;

public record AssignmentStatement(Parameter param, IExpression alternativeExp) implements IStatement {
    @Override
    public void accept(IVisitor visitor) {

    }
}
