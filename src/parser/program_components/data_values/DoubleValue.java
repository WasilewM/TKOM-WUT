package parser.program_components.data_values;

import lexer.Position;
import parser.IExpression;
import parser.IVisitor;

public record DoubleValue(Position position, Double value) implements IExpression {
    @Override
    public void accept(IVisitor visitor) {

    }
}
