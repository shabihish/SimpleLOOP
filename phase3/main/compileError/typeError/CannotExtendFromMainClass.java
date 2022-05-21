package main.compileError.typeError;

import main.compileError.CompileError;

public class CannotExtendFromMainClass extends CompileError {

    public CannotExtendFromMainClass(int line) {
        super(line, "Classes cannot extend from Main class");
    }

}
