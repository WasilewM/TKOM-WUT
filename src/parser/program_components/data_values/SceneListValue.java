package parser.program_components.data_values;

import lexer.Position;
import parser.IExpression;
import visitors.IVisitor;

import java.util.ArrayList;

public record SceneListValue(Position position, ArrayList<SceneValue> value) implements IExpression {
    public SceneListValue(Position position) {
        this(position, new ArrayList<>());
    }

    @Override
    public void accept(IVisitor visitor) {
        visitor.visit(this);
    }
}
