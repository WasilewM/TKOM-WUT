package parser.program_components.parameters;

import lexer.Position;
import parser.IParameter;
import parser.IVisitor;

public record DoubleListParameter(Position position, String name) implements IParameter {

    @Override
    public void accept(IVisitor visitor) {

    }
}