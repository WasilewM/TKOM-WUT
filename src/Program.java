import java.util.HashMap;

public record Program(HashMap<String, FunctionDef> functions) implements IVisitable {

    @Override
    public void accept(IVisitor visitor) {
        visitor.visit(this);
    }
}
