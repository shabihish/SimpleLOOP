package main.ast.nodes.declaration.classDec.classMembersDec;

import main.visitor.IVisitor;

//line -> INITIALIZE
public class ConstructorDeclaration extends MethodDeclaration{

    public ConstructorDeclaration() {
        super();
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
