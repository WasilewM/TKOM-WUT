package parser.program_components.expressions;

import parser.IExpression;

public record DiscreteDivisionExpression(IExpression leftExp, IExpression rightExp) implements IExpression {
}
