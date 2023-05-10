package parser.program_components.data_values;

import lexer.Position;
import parser.IExpression;
import parser.IVisitor;

public record BoolValue(Position position, Boolean value) implements IExpression {
    @Override
    public void accept(IVisitor visitor) {

    }
}
