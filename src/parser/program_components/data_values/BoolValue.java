package parser.program_components.data_values;

import parser.IExpression;

public record BoolValue(Boolean value) implements IExpression {
}
