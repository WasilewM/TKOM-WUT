package parser.program_components.expressions;

import parser.IExpression;

public record NotEqualExpression(IExpression leftExp, IExpression rightExp) implements IExpression {
}
