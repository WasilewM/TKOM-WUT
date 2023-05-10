package parser.program_components;

import lexer.Position;
import parser.IExpression;
import parser.IVisitor;

public record Identifier(Position position, String name) implements IExpression {
    @Override
    public void accept(IVisitor visitor) {

    }
}
