public interface IVisitor {
    void visit(Program p);
    void visit(FunctionDef f);
    void visit(BlockStatement b);
}
