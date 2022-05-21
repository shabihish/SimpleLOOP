package main.ast.nodes.expression;

import main.visitor.IVisitor;

//line -> SELF
public class SelfClass extends Expression {
    @Override
    public String toString() {
        return "SelfClass";
    }

    @Override
    public <T> T accept(IVisitor<T> visitor) {
        return visitor.visit(this);
    }
}
