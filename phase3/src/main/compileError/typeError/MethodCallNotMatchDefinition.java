package main.compileError.typeError;

import main.compileError.CompileError;

public class MethodCallNotMatchDefinition extends CompileError {

    public MethodCallNotMatchDefinition(int line) {
        super(line, "Args in method call do not match with definition");
    }

}
