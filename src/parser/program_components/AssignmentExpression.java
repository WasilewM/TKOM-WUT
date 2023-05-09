package parser.program_components;

import parser.IExpression;

public record AssignmentExpression(Parameter param, IExpression alternativeExp) implements IExpression {
}
