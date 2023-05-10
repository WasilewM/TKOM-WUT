package parser.program_components.statements;

import lexer.Position;
import parser.IExpression;
import parser.IStatement;
import parser.IVisitor;
import parser.program_components.CodeBlock;

public record WhileStatement(Position position, IExpression alternativeExp, CodeBlock codeBlock) implements IStatement {
    @Override
    public void accept(IVisitor visitor) {

    }
}
