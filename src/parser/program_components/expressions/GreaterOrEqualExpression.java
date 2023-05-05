package parser.program_components.expressions;

import parser.IExpression;

public record GreaterOrEqualExpression(IExpression leftExp, IExpression rightExp) implements IExpression {
}
