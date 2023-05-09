package parser.program_components.data_values;

import parser.IExpression;
import parser.IVisitor;

public record StringValue(String value) implements IExpression {
    @Override
    public void accept(IVisitor visitor) {

    }
}
