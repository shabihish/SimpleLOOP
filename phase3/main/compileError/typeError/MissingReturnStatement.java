package main.compileError.typeError;

import main.ast.nodes.declaration.classDec.classMembersDec.MethodDeclaration;
import main.compileError.CompileError;

public class MissingReturnStatement extends CompileError {

    public MissingReturnStatement(MethodDeclaration methodDeclaration) {
        super(methodDeclaration.getLine(), "Method " + methodDeclaration.getMethodName().getName() + " needs return statement");
    }

}
