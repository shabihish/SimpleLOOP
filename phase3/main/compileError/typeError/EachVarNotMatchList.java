package main.compileError.typeError;

import main.ast.nodes.statement.EachStmt;
import main.compileError.CompileError;

public class EachVarNotMatchList extends CompileError {

    public EachVarNotMatchList(EachStmt eachStmt) {
        super(eachStmt.getLine(), "Each variable " + eachStmt.getVariable().getName() + " is not same type as range input");
    }

}
