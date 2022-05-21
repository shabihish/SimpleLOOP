package main.compileError.typeError;

import main.compileError.CompileError;

public class UnsupportedTypeForPrint extends CompileError {

    public UnsupportedTypeForPrint(int line) {
        super(line, "Unsupported type for print");
    }

}
