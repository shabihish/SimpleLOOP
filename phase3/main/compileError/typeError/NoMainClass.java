package main.compileError.typeError;

import main.compileError.CompileError;

public class NoMainClass extends CompileError {

    public NoMainClass() {
        super(1, "Main class not found");
    }

}
