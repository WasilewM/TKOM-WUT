package parser.program_components.data_values;

import lexer.Position;
import parser.IDataValue;
import visitors.IVisitor;

import java.util.ArrayList;

public class GenericListValue implements IDataValue {
    protected final ArrayList<Object> value;
    protected final Position position;

    public GenericListValue(Position position, ArrayList<Object> value) {
        this.value = value;
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
        return value;
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
        return value.get(idx);
    }

    public void add(IDataValue val) {
        value.add(val);
    }

    public int size() {
        return value.size();
    }
}
