package parser.program_components;

import lexer.Position;
import parser.IExpOrStmnt;
import parser.IExpression;
import visitors.IVisitor;

import java.util.ArrayList;
import java.util.Objects;

public class FunctionCall implements IExpOrStmnt {
    private final Position position;
    private final Identifier identifier;
    private final ArrayList<IExpression> exp;

    public FunctionCall(Position position, Identifier identifier,
                        ArrayList<IExpression> exp) {
        this.position = position;
        this.identifier = identifier;
        this.exp = exp;
    }

    public FunctionCall(Position position, Identifier identifier) {
        this(position, identifier, new ArrayList<>());
    }

    public FunctionCall(Position position, Identifier identifier, IExpression exp) {
        this.position = position;
        this.identifier = identifier;
        this.exp = new ArrayList<>();
        this.exp.add(exp);
    }

    @Override
    public void accept(IVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public Position position() {
        return position;
    }

    public Identifier identifier() {
        return identifier;
    }

    public ArrayList<IExpression> exp() {
        return exp;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (FunctionCall) obj;
        return Objects.equals(this.position, that.position) &&
                Objects.equals(this.identifier, that.identifier) &&
                Objects.equals(this.exp, that.exp);
    }

    @Override
    public int hashCode() {
        return Objects.hash(position, identifier, exp);
    }

    @Override
    public String toString() {
        return "FunctionCall[" +
                "position=" + position + ", " +
                "identifier=" + identifier + ", " +
                "exp=" + exp + ']';
    }

}
