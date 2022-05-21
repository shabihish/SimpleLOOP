package main.compileError.typeError;

import main.compileError.CompileError;

public class SetIncludeInputNotInt extends CompileError {

    public SetIncludeInputNotInt(int line) {
        super(line, "Set include method input is not an integer");
    }

}
