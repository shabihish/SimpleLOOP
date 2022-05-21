package main.ast.nodes.statement.set;

import main.ast.nodes.expression.Expression;
import main.ast.nodes.statement.Statement;
import main.visitor.IVisitor;

import java.util.ArrayList;

public class SetMerge extends Statement {
    private Expression setArg;
    private ArrayList<Expression> elementArgs;

    public SetMerge(Expression setArg, ArrayList<Expression> elementArgs) {
        this.setArg = setArg;
        this.elementArgs = elementArgs;
    }

    public void setSetArg(Expression setArg) { this.setArg = setArg; }
    public Expression getSetArg() { return setArg; }

    public void setElementArgs(ArrayList<Expression> elementArgs) { this.elementArgs = elementArgs; }
    public ArrayList<Expression> getElementArgs() {
        return elementArgs;
    }

    @Override
    public String toString() {
        return "setMerge";
    }

    @Override
    public <T> T accept(IVisitor<T> visitor) {
        return visitor.visit(this);
    }
}
