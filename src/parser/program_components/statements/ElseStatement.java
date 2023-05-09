package parser.program_components.statements;

import parser.IExpression;
import parser.program_components.CodeBlock;

public record ElseStatement(IExpression alternativeExp, CodeBlock codeBlock) implements IExpression {
    public ElseStatement() {
        this(null, null);
    }
}
