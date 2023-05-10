package parser.program_components.statements;

import parser.IExpression;
import parser.IParameter;
import parser.IStatement;
import parser.IVisitor;

public record AssignmentStatement(IParameter param,
                                  IExpression alternativeExp) implements IStatement {
    @Override
    public void accept(IVisitor visitor) {

    }
}
