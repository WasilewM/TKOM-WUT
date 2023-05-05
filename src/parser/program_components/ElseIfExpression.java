package parser.program_components;

import parser.IExpression;

public record ElseIfExpression(IExpression alternativeExp) implements IExpression {
}
