package main.ast.nodes.statement;

import main.ast.nodes.expression.Expression;
import main.ast.nodes.expression.Identifier;
import main.ast.nodes.statement.Statement;
import main.visitor.IVisitor;

//line -> EACH
public class EachStmt extends Statement {
    private Identifier variable;
    private Expression list;
    private Statement body;

    public EachStmt(Identifier variable, Expression list) {
        this.variable = variable;
        this.list = list;
    }

    public Identifier getVariable() {
        return variable;
    }

    public void setVariable(Identifier variable) {
        this.variable = variable;
    }

    public Expression getList() {
        return list;
    }

    public void setList(Expression list) {
        this.list = list;
    }

    public Statement getBody() {
        return body;
    }

    public void setBody(Statement body) {
        this.body = body;
    }

    @Override
    public String toString() {
        return "EachStmt";
    }

    @Override
    public <T> T accept(IVisitor<T> visitor) {
        return visitor.visit(this);
    }
}
