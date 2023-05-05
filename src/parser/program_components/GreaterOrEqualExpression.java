package parser.program_components;

import parser.IExpression;

public record GreaterOrEqualExpression(IExpression leftExp, IExpression rightExp) implements IExpression {
}
