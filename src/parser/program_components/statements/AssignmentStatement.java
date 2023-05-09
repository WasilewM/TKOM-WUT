package parser.program_components.statements;

import parser.IExpression;
import parser.program_components.Parameter;

public record AssignmentStatement(Parameter param, IExpression alternativeExp) implements IExpression {
}
