package parser.program_components;

import parser.IExpression;
import parser.IVisitor;

public record Identifier(String name) implements IExpression {
    @Override
    public void accept(IVisitor visitor) {

    }
}
