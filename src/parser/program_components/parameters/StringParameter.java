package parser.program_components.parameters;

import parser.IParameter;
import parser.IVisitor;

public record StringParameter(String name) implements IParameter {

    @Override
    public void accept(IVisitor visitor) {

    }
}
