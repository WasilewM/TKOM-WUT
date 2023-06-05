package parser.program_components.statements;

import lexer.Position;
import parser.IStatement;
import parser.program_components.CodeBlock;
import visitors.IVisitor;

public record ElseStatement(Position position, CodeBlock codeBlock) implements IStatement {

    public ElseStatement() {
        this(null, null);
    }

    @Override
    public void accept(IVisitor visitor) {
        visitor.visit(this);
    }
}
