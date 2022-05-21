package main.compileError.typeError;

import main.compileError.CompileError;

public class CantUseValueOfVoidMethod extends CompileError {

    public CantUseValueOfVoidMethod(int line) {
        super(line, "Cannot use return value of a method having void return type");
    }

}
