package main.compileError.typeError;

import main.compileError.CompileError;

public class MergeInputNotSet extends CompileError {

    public MergeInputNotSet(int line) {
        super(line, "Merge on non-Set");
    }

}
