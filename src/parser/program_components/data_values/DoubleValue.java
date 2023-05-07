package parser.program_components.data_values;

import parser.IExpression;

public record DoubleValue(Double value) implements IExpression {
}
