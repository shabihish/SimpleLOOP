package main.compileError.nameError;

import main.ast.nodes.declaration.classDec.ClassDeclaration;
import main.ast.nodes.expression.Identifier;
import main.compileError.CompileError;

import java.security.NoSuchAlgorithmException;

public class ClassRedefinition extends CompileError {
    public ClassRedefinition(int line, String className) {
        super(line, "Redefinition of class " + className);
    }
}
