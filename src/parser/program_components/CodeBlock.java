package parser.program_components;

import parser.IStatement;
import parser.IVisitable;
import parser.IVisitor;

import java.util.List;

public record CodeBlock(List<IStatement> expressions) implements IVisitable {

    @Override
    public void accept(IVisitor visitor) {
    }
}
