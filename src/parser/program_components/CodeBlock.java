package parser.program_components;

import parser.IExpression;
import parser.IVisitable;
import parser.IVisitor;

import java.util.List;

public record CodeBlock(List<IExpression> expressions) implements IVisitable {

    @Override
    public void accept(IVisitor visitor) {
        visitor.visit(this);
    }
}
