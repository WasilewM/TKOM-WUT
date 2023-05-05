package parser.program_components.expressions;

import parser.IExpression;

public record SubtractionExpression(IExpression leftExp, IExpression rightExp) implements IExpression {
}
