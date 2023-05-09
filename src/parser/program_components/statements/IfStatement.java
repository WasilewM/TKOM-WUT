package parser.program_components.statements;

import parser.IExpression;
import parser.IStatement;
import parser.IVisitor;
import parser.program_components.CodeBlock;

import java.util.ArrayList;
import java.util.List;

public record IfStatement(IExpression alternativeExp, CodeBlock codeBlock, List<ElseIfStatement> elseIfStmnts,
                          IStatement elseExp) implements IStatement {
    public IfStatement(IExpression alternativeExp, CodeBlock codeBlock) {
        this(alternativeExp, codeBlock, new ArrayList<>(), new ElseStatement(null, null));
    }

    public IfStatement(IExpression ifStmnt, CodeBlock codeBlock, List<ElseIfStatement> elseIfStmnts) {
        this(ifStmnt, codeBlock, elseIfStmnts, new ElseStatement(null, null));
    }

    public IfStatement(IExpression ifStmnt, CodeBlock codeBlock, IStatement elseExp) {
        this(ifStmnt, codeBlock, new ArrayList<>(), elseExp);
    }

    @Override
    public void accept(IVisitor visitor) {

    }
}
