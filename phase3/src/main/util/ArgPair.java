package main.util;

import main.ast.nodes.declaration.variableDec.VariableDeclaration;
import main.ast.nodes.expression.Expression;


public class ArgPair {
    private VariableDeclaration variableDeclaration;
    private Expression defaultValue;

    public ArgPair(VariableDeclaration variableDeclaration, Expression expression) {
        this.variableDeclaration = variableDeclaration;
        this.defaultValue = expression;
    }

    public ArgPair(VariableDeclaration variableDeclaration) {
        this.variableDeclaration = variableDeclaration;
    }

    public VariableDeclaration getVariableDeclaration() {
        return variableDeclaration;
    }

    public void setVariableDeclaration(VariableDeclaration variableDeclaration) {
        this.variableDeclaration = variableDeclaration;
    }

    public Expression getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(Expression defaultValue) {
        this.defaultValue = defaultValue;
    }
}
