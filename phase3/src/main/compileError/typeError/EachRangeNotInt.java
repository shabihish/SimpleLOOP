package main.compileError.typeError;

import main.ast.nodes.statement.EachStmt;
import main.compileError.CompileError;

public class EachRangeNotInt extends CompileError {

    public EachRangeNotInt(int line) {
        super(line, "Each range expression is not an integer");
    }

}
