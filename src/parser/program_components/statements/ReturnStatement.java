package parser.program_components.statements;

import lexer.Position;
import parser.IExpression;
import parser.IStatement;
import visitor.IVisitor;

public record ReturnStatement(Position position, IExpression exp) implements IStatement {
    @Override
    public void accept(IVisitor visitor) {
        visitor.visit(this);
    }
}
