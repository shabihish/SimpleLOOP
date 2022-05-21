package main.ast.nodes.expression;

import main.ast.nodes.expression.values.primitive.IntValue;
import main.visitor.IVisitor;

//line -> INCLUDE
public class SetInclude extends Expression{
    private Expression setArg;
    private Expression elementArg;

    public SetInclude(Expression setArg, Expression elementArg) {
        this.setArg = setArg;
        this.elementArg = elementArg;
    }

    public void setSetArg(Expression setArg) { this.setArg = setArg; }
    public Expression getSetArg() { return setArg; }

    public void setElementArg(Expression elementArg) { this.elementArg = elementArg; }
    public Expression getElementArg() {
        return elementArg;
    }

    @Override
    public String toString() {
        return "SetInclude";
    }

    @Override
    public <T> T accept(IVisitor<T> visitor) {
        return visitor.visit(this);
    }
}
