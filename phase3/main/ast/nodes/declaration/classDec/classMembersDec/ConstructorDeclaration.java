package main.ast.nodes.declaration.classDec.classMembersDec;

import main.ast.nodes.expression.Identifier;
import main.visitor.IVisitor;

//line -> INITIALIZE
public class ConstructorDeclaration extends MethodDeclaration{

    public ConstructorDeclaration() {
        super();
    }

    @Override
    public Identifier getMethodName() {
        return new Identifier("initialize");
    }

    @Override
    public String toString() {
        return "ConstructorDeclaration";
    }

    @Override
    public <T> T accept(IVisitor<T> visitor) {
        return visitor.visit(this);
    }
}
