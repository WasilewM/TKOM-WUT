package parser.program_components.expressions;

import parser.IExpression;

public record EqualExpression(IExpression leftExp, IExpression rightExp) implements IExpression {
}
