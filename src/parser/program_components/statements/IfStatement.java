package parser.program_components.statements;

import lexer.Position;
import parser.IExpression;
import parser.IStatement;
import visitor.IVisitor;
import parser.program_components.CodeBlock;

import java.util.ArrayList;
import java.util.List;

public record IfStatement(Position position, IExpression exp, CodeBlock codeBlock, List<ElseIfStatement> elseIfStmnts,
                          IStatement elseExp) implements IStatement {
    public IfStatement(Position position, IExpression alternativeExp, CodeBlock codeBlock) {
        this(position, alternativeExp, codeBlock, new ArrayList<>(), new ElseStatement(null, null, null));
    }

    public IfStatement(Position position, IExpression ifStmnt, CodeBlock codeBlock, List<ElseIfStatement> elseIfStmnts) {
        this(position, ifStmnt, codeBlock, elseIfStmnts, new ElseStatement(null, null, null));
    }

    public IfStatement(Position position, IExpression ifStmnt, CodeBlock codeBlock, IStatement elseExp) {
        this(position, ifStmnt, codeBlock, new ArrayList<>(), elseExp);
    }

    @Override
    public void accept(IVisitor visitor) {
        visitor.visit(this);
    }
}
