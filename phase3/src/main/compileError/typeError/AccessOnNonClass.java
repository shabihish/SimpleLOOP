package main.compileError.typeError;

import main.compileError.CompileError;

public class AccessOnNonClass extends CompileError {

    public AccessOnNonClass(int line) {
        super(line, "Access on non-class");
    }

}
