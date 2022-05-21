package main.compileError.typeError;

import main.compileError.CompileError;

public class MainClassCantInherit extends CompileError {

    public MainClassCantInherit(int line) {
        super(line, "Main class cannot inherit any class");
    }

}
