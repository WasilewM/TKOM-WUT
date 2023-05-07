package parser.program_components;

import parser.IExpression;

import java.util.ArrayList;
import java.util.List;

public record IfExpression(IExpression alternativeExp, CodeBlock codeBlock, List<IExpression> elseIfExps,
                           IExpression elseExp) implements IExpression {
    public IfExpression(IExpression alternativeExp, CodeBlock codeBlock) {
        this(alternativeExp, codeBlock, new ArrayList<>(), new ElseExpression(null, null));
    }

    public IfExpression(IExpression ifExp, CodeBlock codeBlock, List<IExpression> elseIfExps) {
        this(ifExp, codeBlock, elseIfExps, new ElseExpression(null, null));
    }

    public IfExpression(IExpression ifExp, CodeBlock codeBlock, IExpression elseExp) {
        this(ifExp, codeBlock, new ArrayList<>(), elseExp);
    }
}
