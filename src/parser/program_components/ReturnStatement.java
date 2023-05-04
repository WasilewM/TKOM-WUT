package parser.program_components;

import parser.IStatement;

public record ReturnStatement(Object value) implements IStatement {
}
