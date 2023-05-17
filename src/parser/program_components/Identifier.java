package parser.program_components;

import lexer.Position;
import parser.IExpression;
import visitor.IVisitor;

public record Identifier(Position position, String name) implements IExpression {
    @Override
    public void accept(IVisitor visitor) {
        visitor.visit(this);
    }
}
