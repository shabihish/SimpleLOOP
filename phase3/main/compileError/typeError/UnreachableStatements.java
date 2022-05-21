package main.compileError.typeError;

import main.ast.nodes.statement.Statement;
import main.compileError.CompileError;

public class UnreachableStatements extends CompileError {

    public UnreachableStatements(Statement statement) {
        super(statement.getLine(), "Statements are unreachable");
    }

}
