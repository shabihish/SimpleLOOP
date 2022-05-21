package main.compileError.typeError;

import main.compileError.CompileError;

public class CallOnNoneCallable extends CompileError {

    public CallOnNoneCallable(int line) {
        super(line, "Calling a non-callable");
    }

}
