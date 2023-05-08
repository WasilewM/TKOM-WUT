package parser.program_components.data_values;

import parser.IExpression;

public record IntValue(Integer value) implements IExpression {
}
