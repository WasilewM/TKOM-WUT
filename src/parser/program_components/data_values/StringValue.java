package parser.program_components.data_values;

import parser.IExpression;

public record StringValue(String value) implements IExpression {
}
