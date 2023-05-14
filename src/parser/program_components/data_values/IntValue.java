package parser.program_components.data_values;

import lexer.Position;
import parser.IExpression;
import visitor.IVisitor;

public record IntValue(Position position, Integer value) implements IExpression {
    @Override
    public void accept(IVisitor visitor) {
        visitor.visit(this);
    }
}
