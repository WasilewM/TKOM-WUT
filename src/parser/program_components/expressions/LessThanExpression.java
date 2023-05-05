package parser.program_components.expressions;

import parser.IExpression;

public record LessThanExpression(IExpression leftExp, IExpression rightExp) implements IExpression {
}
