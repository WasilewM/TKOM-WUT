package parser.program_components.statements;

import parser.IExpression;
import parser.IStatement;
import parser.IVisitor;
import parser.program_components.CodeBlock;

public record ElseStatement(IExpression alternativeExp, CodeBlock codeBlock) implements IStatement {
    public ElseStatement() {
        this(null, null);
    }

    @Override
    public void accept(IVisitor visitor) {

    }
}
