package main.compileError.typeError;

import main.compileError.CompileError;

public class ClassNotDeclared extends CompileError {

    public ClassNotDeclared(int line, String className) {
        super(line, "Class " + className + " is not declared");
    }

}
