package parser.program_components.expressions;

import parser.IExpression;

public record MultiplicationExpression(IExpression leftExp, IExpression rightExp) implements IExpression {
}
