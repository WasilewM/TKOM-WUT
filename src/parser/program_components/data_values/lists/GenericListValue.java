package parser.program_components.data_values.lists;

import lexer.Position;
import parser.IDataValue;
import visitors.IVisitor;

import java.util.ArrayList;

public class GenericListValue implements IDataValue {
    protected final ArrayList<Object> values;
    protected final Position position;

    public GenericListValue(Position position, ArrayList<Object> values) {
        this.values = values;
        this.position = position;
    }

    public static ArrayList<String> getImplementedMethods() {
        ArrayList<String> implementedMethods = new ArrayList<>();
        implementedMethods.add("get");
        implementedMethods.add("add");
        return implementedMethods;
    }

    @Override
    public Object value() {
        return values;
    }

    @Override
    public Position position() {
        return position;
    }

    @Override
    public void accept(IVisitor visitor) {
        visitor.visit(this);
    }

    public Object get(int idx) {
        return values.get(idx);
    }

    public void add(IDataValue val) {
        values.add(val);
    }

    public int size() {
        return values.size();
    }
}
