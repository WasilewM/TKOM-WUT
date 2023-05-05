package parser.program_components.expressions;

import parser.IExpression;

public record LessOrEqualExpression(IExpression leftExp, IExpression rightExp) implements IExpression {
}
