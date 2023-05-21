package parser.program_components.parameters;

import lexer.Position;
import parser.IParameter;
import visitors.IVisitor;

public record SceneParameter(Position position, String name) implements IParameter {

    @Override
    public void accept(IVisitor visitor) {
        visitor.visit(this);
    }
}
