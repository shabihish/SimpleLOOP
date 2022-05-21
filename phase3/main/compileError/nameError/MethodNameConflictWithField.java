package main.compileError.nameError;

import main.compileError.CompileError;

public class MethodNameConflictWithField extends CompileError {
    public MethodNameConflictWithField(int line, String methodName) {
        super(line, "Name of method " + methodName + " conflicts with a field’s name");
    }
}
