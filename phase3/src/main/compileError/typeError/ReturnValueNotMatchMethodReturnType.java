package main.compileError.typeError;

import main.ast.nodes.statement.ReturnStmt;
import main.compileError.CompileError;

public class ReturnValueNotMatchMethodReturnType extends CompileError {

    public ReturnValueNotMatchMethodReturnType(ReturnStmt returnStmt) {
        super(returnStmt.getLine(), "Return value does not match with method return type");
    }

}
