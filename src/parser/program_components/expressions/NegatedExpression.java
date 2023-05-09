package parser.program_components.expressions;

import parser.IExpression;

public record NegatedExpression(IExpression alternativeExp) implements IExpression {
}
