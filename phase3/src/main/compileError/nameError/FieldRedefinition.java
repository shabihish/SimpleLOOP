package main.compileError.nameError;

import main.compileError.CompileError;

public class FieldRedefinition extends CompileError {
    public FieldRedefinition(int line, String fieldName) {
        super(line, "Redefinition of field " + fieldName);
    }
}