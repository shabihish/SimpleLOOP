package main.compileError.typeError;

import main.ast.nodes.declaration.classDec.classMembersDec.MethodDeclaration;
import main.compileError.CompileError;

public class VoidMethodHasReturn extends CompileError {

    public VoidMethodHasReturn(MethodDeclaration methodDeclaration) {
        super(methodDeclaration.getLine(), "Method "+  methodDeclaration.getMethodName().getName() + " should not have a return statement\n");
    }

}
