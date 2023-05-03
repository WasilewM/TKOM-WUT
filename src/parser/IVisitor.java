package parser;

import parser.program_components.BlockStatement;
import parser.program_components.FunctionDef;
import parser.program_components.Parameter;
import parser.program_components.Program;

public interface IVisitor {
    void visit(Program p);
    void visit(FunctionDef f);
    void visit(BlockStatement b);
    void visit(Parameter p);
}
