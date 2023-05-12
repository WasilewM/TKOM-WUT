package parser.program_components.data_values;

import lexer.Position;
import parser.IExpression;
import parser.IVisitor;

import java.util.ArrayList;

public record SceneListValue(Position position, ArrayList<Integer> value) implements IExpression {
    public SceneListValue(Position position) {
        this(position, new ArrayList<>());
    }

    @Override
    public void accept(IVisitor visitor) {

    }
}
