package parser.program_components.statements;

import lexer.Position;
import parser.IExpression;
import parser.IStatement;
import visitor.IVisitor;
import parser.program_components.CodeBlock;

public record ElseIfStatement(Position position, IExpression exp, CodeBlock codeBlock) implements IStatement {
    @Override
    public void accept(IVisitor visitor) {
        visitor.visit(this);
    }
}
