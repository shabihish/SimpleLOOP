package main.compileError.nameError;

import main.compileError.CompileError;

public class GlobalVarRedefinition extends CompileError {
    public GlobalVarRedefinition(int line, String globalVar) {
        super(line, "Redefinition of global variable " + globalVar);
    }
}
