package parser.program_components;

import parser.IExpression;

import java.util.ArrayList;
import java.util.List;

public record IfExpression(IExpression alternativeExp, List<IExpression> elseIfExps,
                           IExpression elseExp) implements IExpression {
    public IfExpression(IExpression alternativeExp) {
        this(alternativeExp, new ArrayList<>(), new ElseExpression(null));
    }

    public IfExpression(IExpression ifExp, List<IExpression> elseIfExps) {
        this(ifExp, elseIfExps, new ElseExpression(null));
    }

    public IfExpression(IExpression ifExp, IExpression elseExp) {
        this(ifExp, new ArrayList<>(), elseExp);
    }
}
