package main.visitor.name;


import main.compileError.nameError.ClassInCyclicInheritance;
import main.compileError.nameError.FieldRedefinition;
import main.compileError.nameError.MethodNameConflictWithField;
import main.compileError.nameError.MethodRedefinition;

import main.ast.nodes.*;
import main.ast.nodes.declaration.classDec.ClassDeclaration;
import main.ast.nodes.declaration.classDec.classMembersDec.ConstructorDeclaration;
import main.ast.nodes.declaration.classDec.classMembersDec.FieldDeclaration;
import main.ast.nodes.declaration.classDec.classMembersDec.MethodDeclaration;
import main.ast.nodes.declaration.variableDec.VariableDeclaration;
import main.ast.nodes.expression.*;
import main.ast.nodes.expression.values.NullValue;
import main.ast.nodes.expression.values.SetValue;
import main.ast.nodes.expression.values.primitive.*;
import main.ast.nodes.statement.*;
import main.ast.nodes.statement.set.*;
import main.compileError.CompileError;
import main.symbolTable.SymbolTable;
import main.symbolTable.exceptions.ItemNotFoundException;
import main.symbolTable.items.ClassSymbolTableItem;
import main.symbolTable.utils.graph.Graph;
import main.symbolTable.utils.graph.exceptions.GraphDoesNotContainNodeException;
import main.symbolTable.utils.graph.exceptions.NodeAlreadyExistsException;
import main.visitor.*;

import java.lang.reflect.Field;
import java.util.ArrayList;
import javax.swing.plaf.nimbus.State;

import main.symbolTable.items.MethodSymbolTableItem;
import main.symbolTable.items.FieldSymbolTableItem;
import main.symbolTable.items.LocalVariableSymbolTableItem;

public class NameChecker extends Visitor<Void> {
    private String currentClassName;
    private Graph<String> classHierarchy;
    Program root;

    public NameChecker(Graph<String> classHierarchy) {
        this.classHierarchy = classHierarchy;
    }

    private SymbolTable getCurrentClassSymbolTable() {
        try {
            ClassSymbolTableItem classSymbolTableItem = (ClassSymbolTableItem)
                    SymbolTable.root.getItem(ClassSymbolTableItem.START_KEY + this.currentClassName, true);
            return classSymbolTableItem.getClassSymbolTable();
        } catch (ItemNotFoundException ignored) {
            return null;
        }
    }

    @Override
    public Void visit(Program program) {
        this.root = program;
        for(VariableDeclaration variableDeclaration : program.getGlobalVariables()) {
            variableDeclaration.accept(this);
        }
        for(ClassDeclaration classDeclaration : program.getClasses()) {
            this.currentClassName = classDeclaration.getClassName().getName();
            classDeclaration.accept(this);
        }
        return null;
    }

    @Override
    public Void visit(ClassDeclaration classDeclaration) {
        if(classDeclaration.getParentClassName() != null) {
            if (this.classHierarchy.isSecondNodeAncestorOf(classDeclaration.getParentClassName().getName(), classDeclaration.getClassName().getName())) {
                ClassInCyclicInheritance exception = new ClassInCyclicInheritance(classDeclaration.getLine() , classDeclaration.toString());
                classDeclaration.addError(exception);
            }
        }
        for(FieldDeclaration fieldDeclaration : classDeclaration.getFields()) {
            fieldDeclaration.accept(this);
        }
        if(classDeclaration.getConstructor() != null) {
            classDeclaration.getConstructor().accept(this);
        }
        for(MethodDeclaration methodDeclaration : classDeclaration.getMethods()) {
            methodDeclaration.accept(this);
        }
        return null;
    }

    @Override
    public Void visit(ConstructorDeclaration constructorDeclaration) {
        this.visit((MethodDeclaration) constructorDeclaration);
        return null;
    }

    @Override
    public Void visit(MethodDeclaration methodDeclaration) {
        if(!methodDeclaration.hasError()) {
            try {
                SymbolTable classSymbolTable = this.getCurrentClassSymbolTable();
                classSymbolTable.getItem(MethodSymbolTableItem.START_KEY + methodDeclaration.getMethodName().getName(), false);
                MethodRedefinition exception = new MethodRedefinition(methodDeclaration.getLine() ,methodDeclaration.getMethodName().getName());
                methodDeclaration.addError(exception);
            } catch (ItemNotFoundException ignored) {
            }
        }

        boolean errored = false;
        try {
            SymbolTable classSymbolTable = this.getCurrentClassSymbolTable();
            classSymbolTable.getItem(FieldSymbolTableItem.START_KEY + methodDeclaration.getMethodName().getName(), true);
            MethodNameConflictWithField exception = new MethodNameConflictWithField(methodDeclaration.getLine() , methodDeclaration.getMethodName().getName());
            methodDeclaration.addError(exception);
            errored = true;
        } catch (ItemNotFoundException ignored) {
        }
        if(!errored)
            for(ClassDeclaration classDeclaration : root.getClasses()) {
                String childName = classDeclaration.getClassName().getName();
                if(classHierarchy.isSecondNodeAncestorOf(childName, currentClassName)) {
                    try {
                        ClassSymbolTableItem childSymbolTableItem = (ClassSymbolTableItem) SymbolTable.root.getItem(ClassSymbolTableItem.START_KEY + childName, true);
                        SymbolTable childSymbolTable = childSymbolTableItem.getClassSymbolTable();
                        childSymbolTable.getItem(FieldSymbolTableItem.START_KEY + methodDeclaration.getMethodName().getName(), true);
                        MethodNameConflictWithField exception = new MethodNameConflictWithField(methodDeclaration.getLine() , methodDeclaration.getMethodName().getName());
                        methodDeclaration.addError(exception);
                        break;
                    } catch (ItemNotFoundException ignored) {
                    }
                }
            }
        return null;
    }

    @Override
    public Void visit(FieldDeclaration fieldDeclaration) {
        if(!fieldDeclaration.hasError()) {
            try {
                SymbolTable classSymbolTable = this.getCurrentClassSymbolTable();
                classSymbolTable.getItem(FieldSymbolTableItem.START_KEY + fieldDeclaration.getVarDeclaration().getVarName().getName(), false);
                FieldRedefinition exception = new FieldRedefinition(fieldDeclaration.getLine(), fieldDeclaration.getVarDeclaration().getVarName().getName());
                fieldDeclaration.addError(exception);
            } catch (ItemNotFoundException ignored) {
            }
        }
        return null;
    }

}