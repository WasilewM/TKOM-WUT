package parser.program_components.data_values;

import parser.IExpression;
import parser.IVisitor;

public record DoubleValue(Double value) implements IExpression {
    @Override
    public void accept(IVisitor visitor) {

    }
}
