package main.compileError.nameError;

import main.compileError.CompileError;

public class MethodRedefinition extends CompileError {
    public MethodRedefinition(int line, String methodName) {
        super(line, "Redefinition of method " + methodName);
    }
}
