package parser;

import parser.program_components.CodeBlock;
import parser.program_components.FunctionDef;
import parser.program_components.Parameter;
import parser.program_components.Program;

public interface IVisitor {
    void visit(Program p);

    void visit(FunctionDef f);

    void visit(CodeBlock c);

    void visit(Parameter p);
}
