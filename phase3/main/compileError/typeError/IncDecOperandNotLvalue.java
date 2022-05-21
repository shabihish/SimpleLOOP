package main.compileError.typeError;

import main.compileError.CompileError;

public class IncDecOperandNotLvalue extends CompileError {

    public IncDecOperandNotLvalue(int line, String operatorName) {
        super(line, "Lvalue is required as the operand of " + operatorName);
    }

}
