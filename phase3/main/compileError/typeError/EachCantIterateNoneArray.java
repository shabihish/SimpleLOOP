package main.compileError.typeError;

import main.compileError.CompileError;

public class EachCantIterateNoneArray extends CompileError {

    public EachCantIterateNoneArray(int line) {
        super(line, "Each cannot iterate over a non-array");
    }

}
