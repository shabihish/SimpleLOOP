package main.compileError.typeError;

import main.compileError.CompileError;

public class AccessByIndexOnNoneArray extends CompileError {

    public AccessByIndexOnNoneArray(int line) {
        super(line, "Access by index on a non-array");
    }

}
