package main.compileError.nameError;

import main.compileError.CompileError;
public class ClassInCyclicInheritance extends CompileError {

    public ClassInCyclicInheritance(int line, String className) {
        super(line, "Class " + className + " is in an inheritance cycle");
    }

}
