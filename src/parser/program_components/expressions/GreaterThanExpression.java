package parser.program_components.expressions;

import parser.IExpression;

public record GreaterThanExpression(IExpression leftExp, IExpression rightExp) implements IExpression {
}
