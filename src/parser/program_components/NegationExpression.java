package parser.program_components;

import parser.IExpression;

public record NegationExpression(IExpression alternativeExp) implements IExpression {
}
