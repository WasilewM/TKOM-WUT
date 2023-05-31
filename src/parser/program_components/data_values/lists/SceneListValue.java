package parser.program_components.data_values.lists;

import lexer.Position;
import parser.IDataValue;
import parser.program_components.data_values.SceneValue;
import visitors.IVisitor;

import java.util.ArrayList;

public record SceneListValue(Position position, ArrayList<SceneValue> value) implements IDataValue {
    public SceneListValue(Position position) {
        this(position, new ArrayList<>());
    }

    @Override
    public void accept(IVisitor visitor) {
        visitor.visit(this);
    }
}
