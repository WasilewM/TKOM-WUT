package parser.program_components.statements;

import lexer.Position;
import parser.IExpression;
import parser.IParameter;
import parser.IStatement;
import parser.IVisitor;

public record AssignmentStatement(Position position, IParameter param,
                                  IExpression alternativeExp) implements IStatement {
    @Override
    public void accept(IVisitor visitor) {

    }
}
