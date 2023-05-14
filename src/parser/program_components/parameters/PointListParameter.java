package parser.program_components.parameters;

import lexer.Position;
import parser.IParameter;
import visitor.IVisitor;

public record PointListParameter(Position position, String name) implements IParameter {

    @Override
    public void accept(IVisitor visitor) {
        visitor.visit(this);
    }
}
