package parser.program_components.parameters;

import lexer.Position;
import parser.IParameter;
import parser.IVisitor;

public record IntListParameter(Position position, String name) implements IParameter {

    @Override
    public void accept(IVisitor visitor) {
        visitor.visit(this);
    }
}
