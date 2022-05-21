package main.compileError.nameError;

import main.compileError.CompileError;

public class LocalVarRedefinition extends CompileError {
    public LocalVarRedefinition(int line, String varName) {
        super(line, "Redefinition of local variable " + varName);
    }
}
