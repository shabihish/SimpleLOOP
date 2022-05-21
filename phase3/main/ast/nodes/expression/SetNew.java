package main.ast.nodes.expression;

import main.visitor.IVisitor;

import java.util.ArrayList;

//line -> NEW
public class SetNew extends Expression{
    private ArrayList<Expression> args;

    public SetNew(ArrayList<Expression> args) {
        this.args = args;
    }

    public void setArgs(ArrayList<Expression> args) { this.args = args; }
    public ArrayList<Expression> getArgs() { return args; }

    @Override
    public String toString() {
        return "SetNew";
    }

    @Override
    public <T> T accept(IVisitor<T> visitor) {
        return visitor.visit(this);
    }
}
