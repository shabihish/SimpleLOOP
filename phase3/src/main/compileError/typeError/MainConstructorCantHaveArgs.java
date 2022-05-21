package main.compileError.typeError;

import main.compileError.CompileError;

public class MainConstructorCantHaveArgs extends CompileError {

    public MainConstructorCantHaveArgs(int line) {
        super(line, "Main constructor cannot have arguments");
    }

}
