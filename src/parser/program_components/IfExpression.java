package parser.program_components;

import parser.IExpression;

public record IfExpression(IExpression alternativeExp) implements IExpression {
}
