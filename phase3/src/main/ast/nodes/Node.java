package main.ast.nodes;

import main.compileError.CompileError;
import main.visitor.IVisitor;

import java.util.ArrayList;

public abstract class Node {
    private int line;
    private ArrayList<CompileError> errors = new ArrayList<>();
    public static boolean isCatchErrorsActive = true;

    public ArrayList<CompileError> flushErrors() {
        ArrayList<CompileError> errors = this.errors;
        this.errors = new ArrayList<>();
        return errors;
    }

    public void addError(CompileError e) {
        if(Node.isCatchErrorsActive) {
            this.errors.add(e);
        }
    }

    public boolean hasError() {
        return this.errors.size() != 0;
    }

    public int getLine() {
        return line;
    }

    public void setLine(int line) {
        this.line = line;
    }

    public abstract String toString();

    public abstract <T> T accept(IVisitor<T> visitor);
}

