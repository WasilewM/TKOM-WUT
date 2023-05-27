package visitors.exceptions;

import parser.program_components.Identifier;

public class IdentifierNotFoundException extends Exception {
    public IdentifierNotFoundException(Identifier ident) {
        super("IdentifierNotFoundException: Identifier: " + ident.name() + " at position: " + ident.position() + " has not been found.");
    }
}
