package main.compileError.typeError;

import main.compileError.CompileError;

public class ArrayIndexNotInt extends CompileError {

    public ArrayIndexNotInt(int line) {
        super(line, "Array index is not an integer");
    }

}
