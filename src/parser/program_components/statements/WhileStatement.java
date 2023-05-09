package parser.program_components.statements;

import parser.IExpression;
import parser.program_components.CodeBlock;

public record WhileStatement(IExpression alternativeExp, CodeBlock codeBlock) implements IExpression {
}
