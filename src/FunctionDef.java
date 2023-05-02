import java.util.ArrayList;

public record FunctionDef(String name, TokenTypeEnum functionType, ArrayList<Parameter> parameters,
                          BlockStatement statement) implements IVisitable {

    @Override
    public void accept(IVisitor visitor) {
        visitor.visit(this);
    }
}
