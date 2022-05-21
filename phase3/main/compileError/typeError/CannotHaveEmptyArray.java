package main.compileError.typeError;

import main.compileError.CompileError;

public class CannotHaveEmptyArray extends CompileError {

    public CannotHaveEmptyArray(int line) {
        super(line, "Cannot have empty array");
    }

}
