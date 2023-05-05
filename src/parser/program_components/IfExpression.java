package parser.program_components;

import parser.IExpression;

import java.util.ArrayList;
import java.util.List;

public record IfExpression(IExpression alternativeExp, List<IExpression> elseIfExps) implements IExpression {
    public IfExpression(IExpression alternativeExp) {
        this(alternativeExp, new ArrayList<>());
    }
}
