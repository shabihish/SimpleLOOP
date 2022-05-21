package main.compileError.nameError;

import main.compileError.CompileError;

public class LocalVarConflictWithGlobalVar extends CompileError {
    public LocalVarConflictWithGlobalVar(int line, String localVar) {
        super(line, "Name of local variable " + localVar + " conflicts with a global variable’s name");
    }
}
