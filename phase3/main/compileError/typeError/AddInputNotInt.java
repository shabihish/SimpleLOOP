package main.compileError.typeError;

import main.compileError.CompileError;

public class AddInputNotInt extends CompileError {

    public AddInputNotInt(int line) {
        super(line, "Set add method input is not an integer");
    }

}
