package parser.program_components.statements;

import lexer.Position;
import parser.IExpression;
import parser.IStatement;
import parser.program_components.CodeBlock;
import visitors.IVisitor;

import java.util.ArrayList;
import java.util.List;

public record IfStatement(Position position, IExpression exp, CodeBlock codeBlock, List<ElseIfStatement> elseIfStmnts,
                          ElseStatement elseStmnt) implements IStatement {
    public IfStatement(Position position, IExpression exp, CodeBlock codeBlock) {
        this(position, exp, codeBlock, new ArrayList<>(), null);
    }

    public IfStatement(Position position, IExpression ifStmnt, CodeBlock codeBlock, List<ElseIfStatement> elseIfStmnts) {
        this(position, ifStmnt, codeBlock, elseIfStmnts, null);
    }

    public IfStatement(Position position, IExpression ifStmnt, CodeBlock codeBlock, ElseStatement elseStmnt) {
        this(position, ifStmnt, codeBlock, new ArrayList<>(), elseStmnt);
    }

    @Override
    public void accept(IVisitor visitor) {
        visitor.visit(this);
    }
}
