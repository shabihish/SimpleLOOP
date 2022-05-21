package main.ast.nodes.declaration.classDec.classMembersDec;

import main.ast.nodes.declaration.Declaration;
import main.ast.nodes.declaration.variableDec.VariableDeclaration;
import main.ast.nodes.expression.Identifier;
import main.ast.nodes.statement.Statement;
import main.ast.types.Type;
import main.util.ArgPair;
import main.visitor.IVisitor;

import java.util.ArrayList;

//line -> IDENTIFIER
public class MethodDeclaration extends Declaration {
    protected Identifier methodName;
    protected Type returnType;
        protected ArrayList<ArgPair> args = new ArrayList<>();
    protected ArrayList<VariableDeclaration> localVars = new ArrayList<>();
    protected ArrayList<Statement> body = new ArrayList<>();
    protected boolean isPrivate;
    protected boolean doesReturn = false;

    public MethodDeclaration(Identifier methodName, Type returnType, boolean isPrivate) {
        this.methodName = methodName;
        this.returnType = returnType;
        this.isPrivate = isPrivate;
    }

    public MethodDeclaration() {

    }

    public Identifier getMethodName() {
        return methodName;
    }

    public void setMethodName(Identifier methodName) {
        this.methodName = methodName;
    }

    public Type getReturnType() {
        return returnType;
    }

    public void setReturnType(Type returnType) {
        this.returnType = returnType;
    }

    public ArrayList<ArgPair> getArgs() {
        return args;
    }

    public void setArgs(ArrayList<ArgPair> args) {
        this.args = args;
    }

    public ArrayList<VariableDeclaration> getLocalVars() {
        return localVars;
    }

    public void setLocalVars(ArrayList<VariableDeclaration> localVars) {
        this.localVars = localVars;
    }

    public ArrayList<Statement> getBody() {
        return body;
    }

    public void setBody(ArrayList<Statement> body) {
        this.body = body;
    }

    public void addLocalVar(VariableDeclaration varDeclaration) {
        this.localVars.add(varDeclaration);
    }

    public void addBodyStatement(Statement statement) {
        this.body.add(statement);
    }

    public void setPrivate(boolean isPrivate) {
        this.isPrivate = isPrivate;
    }

    public boolean getIsPrivate() {
        return isPrivate;
    }

    public boolean getDoesReturn() {
        return doesReturn;
    }

    public void setDoesReturn(boolean doesReturn) {
        this.doesReturn = doesReturn;
    }

    @Override
    public String toString() {
        return "MethodDeclaration_" + this.methodName.getName();
    }

    @Override
    public <T> T accept(IVisitor<T> visitor) {
        return visitor.visit(this);
    }
}
