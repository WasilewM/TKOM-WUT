package parser.program_components.statements;

import lexer.Position;
import parser.IExpression;
import parser.IStatement;
import parser.IVisitor;

public record ReturnStatement(Position position, IExpression value) implements IStatement {
    @Override
    public void accept(IVisitor visitor) {

    }
}
