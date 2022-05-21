package main.compileError.typeError;

import main.ast.nodes.declaration.classDec.ClassDeclaration;
import main.compileError.CompileError;

public class NoConstructorInMainClass extends CompileError {

    public NoConstructorInMainClass(ClassDeclaration mainClassDeclaration) {
        super(mainClassDeclaration.getLine(), "No constructor found for Main class");
    }

}
