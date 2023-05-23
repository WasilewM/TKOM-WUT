package parser.program_components.parameters;

import lexer.Position;
import parser.IParameter;
import parser.program_components.Identifier;
import visitors.IVisitor;

public record ReassignedParameter(Position position, String name) implements IParameter {

    public ReassignedParameter(Identifier ident) {
        this(ident.position(), ident.name());
    }

    @Override
    public void accept(IVisitor visitor) {
        visitor.visit(this);
    }
}
