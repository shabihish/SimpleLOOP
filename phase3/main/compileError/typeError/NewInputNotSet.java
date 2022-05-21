package main.compileError.typeError;

import main.compileError.CompileError;

public class NewInputNotSet extends CompileError {

    public NewInputNotSet(int line) {
        super(line, "New on non-Set");
    }

}
