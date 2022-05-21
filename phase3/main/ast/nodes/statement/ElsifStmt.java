package main.ast.nodes.statement;

import main.ast.nodes.expression.Expression;
import main.visitor.IVisitor;

import java.util.ArrayList;

//line -> ELSIF
public class ElsifStmt extends Statement{
    private Expression condition;
    private Statement thenBody;

    public ElsifStmt(Expression expression, Statement thenBody) {
        this.condition = expression;
        this.thenBody = thenBody;
    }

    public Expression getCondition() {
        return condition;
    }

    public void setCondition(Expression condition) {
        this.condition = condition;
    }

    public Statement getThenBody() {
        return thenBody;
    }

    public void setThenBody(Statement thenBody) {
        this.thenBody = thenBody;
    }

    @Override
    public String toString() {
        return "ElsifStmt";
    }

    @Override
    public <T> T accept(IVisitor<T> visitor) {
        return visitor.visit(this);
    }
}
