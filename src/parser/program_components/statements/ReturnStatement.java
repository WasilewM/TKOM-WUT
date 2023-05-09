package parser.program_components.statements;

import parser.IExpression;
import parser.IStatement;
import parser.IVisitor;

public record ReturnStatement(IExpression value) implements IStatement {
    @Override
    public void accept(IVisitor visitor) {

    }
}
