package main.compileError.typeError;

import main.compileError.CompileError;
import main.ast.nodes.expression.NewClassInstance;

public class ConstructorArgsNotMatchDefinition extends CompileError {

    public ConstructorArgsNotMatchDefinition(NewClassInstance newClassInstance) {
        super(newClassInstance.getLine(), "Constructor args do not match with definition of class " + newClassInstance.getClassType().getClassName().getName());
    }

}
