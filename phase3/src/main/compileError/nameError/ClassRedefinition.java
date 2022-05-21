package main.compileError.nameError;

import main.compileError.CompileError;

public class ClassRedefinition extends CompileError {
    public ClassRedefinition(int line, String className) {
        super(line, "Redefinition of class " + className);
    }
}
