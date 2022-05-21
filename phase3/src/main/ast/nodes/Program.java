package main.ast.nodes;

import main.ast.nodes.declaration.classDec.ClassDeclaration;
import main.ast.nodes.declaration.variableDec.VariableDeclaration;
import main.visitor.IVisitor;

import java.util.ArrayList;

//line -> 1
public class Program extends Node {
    private ArrayList<VariableDeclaration> globalVariables = new ArrayList<>();
    private ArrayList<ClassDeclaration> classes = new ArrayList<>();

    public Program() {
    }

    public Program(ArrayList<VariableDeclaration> globalVariables, ArrayList<ClassDeclaration> classes) {
        this.globalVariables = globalVariables;
        this.classes = classes;
    }

    public ArrayList<ClassDeclaration> getClasses() {
        return classes;
    }

    public void setClasses(ArrayList<ClassDeclaration> classes) {
        this.classes = classes;
    }

    public void addClass(ClassDeclaration classDeclaration) {
        classes.add(classDeclaration);
    }

    public ArrayList<VariableDeclaration> getGlobalVariables() {
        return globalVariables;
    }

    public void setGlobalVariables(ArrayList<VariableDeclaration> globalVariables) {
        this.globalVariables = globalVariables;
    }

    public void addGlobalVariable(VariableDeclaration variableDeclaration) {
        globalVariables.add(variableDeclaration);
    }

    @Override
    public String toString() {
        return "Program";
    }

    public <R> R accept(IVisitor<R> visitor) {
        return visitor.visit(this);
    }
}
