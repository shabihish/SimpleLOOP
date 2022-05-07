package main.compileError.nameError;

import main.compileError.CompileError;

public class FieldRedefinition extends CompileError {
    public FieldRedefinition(int l, String name) {

        super(l, "Redefinition of field " + name);
    }
}