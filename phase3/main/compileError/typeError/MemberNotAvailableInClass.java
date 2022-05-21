package main.compileError.typeError;

import main.compileError.CompileError;

public class MemberNotAvailableInClass extends CompileError {

    public MemberNotAvailableInClass(int line, String memberName, String className) {
        super(line, "There is no member named " + memberName + " in class " + className);
    }

}
