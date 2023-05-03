package parser.program_components;

import parser.IStatement;
import parser.IVisitable;
import parser.IVisitor;

import java.util.List;

public record CodeBlock(List<IStatement> statements) implements IVisitable {

    @Override
    public void accept(IVisitor visitor) {
        visitor.visit(this);
    }
}
