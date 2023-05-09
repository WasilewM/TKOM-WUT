package parser.program_components.statements;

import parser.IExpression;
import parser.program_components.CodeBlock;

import java.util.ArrayList;
import java.util.List;

public record IfStatement(IExpression alternativeExp, CodeBlock codeBlock, List<IExpression> elseIfStmnts,
                          IExpression elseExp) implements IExpression {
    public IfStatement(IExpression alternativeExp, CodeBlock codeBlock) {
        this(alternativeExp, codeBlock, new ArrayList<>(), new ElseStatement(null, null));
    }

    public IfStatement(IExpression ifStmnt, CodeBlock codeBlock, List<IExpression> elseIfStmnts) {
        this(ifStmnt, codeBlock, elseIfStmnts, new ElseStatement(null, null));
    }

    public IfStatement(IExpression ifStmnt, CodeBlock codeBlock, IExpression elseExp) {
        this(ifStmnt, codeBlock, new ArrayList<>(), elseExp);
    }
}
