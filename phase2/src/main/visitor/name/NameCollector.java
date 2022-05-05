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
import main.symbolTable.exceptions.ItemAlreadyExistsException;
import main.symbolTable.items.ClassSymbolTableItem;
import main.compileError.nameError.ClassRedefinition;
import main.symbolTable.items.MethodSymbolTableItem;
import main.symbolTable.items.FieldSymbolTableItem;
import main.symbolTable.items.LocalVariableSymbolTableItem;
import main.compileError.nameError.LocalVarRedefinition;


public class NameCollector extends Visitor<Void> {

    @Override
    public Void visit(Program program) {
        SymbolTable.push(new SymbolTable());
        SymbolTable.root = SymbolTable.top;
        for(ClassDeclaration classDeclaration : program.getClasses()) {
            classDeclaration.accept(this);
        }
        return null;
    }

    @Override
    public Void visit(ClassDeclaration classDeclaration) {
        ClassSymbolTableItem classSymbolTableItem = new ClassSymbolTableItem(classDeclaration);
        SymbolTable.push(new SymbolTable(SymbolTable.top));
        classSymbolTableItem.setClassSymbolTable(SymbolTable.top);
        try {
            SymbolTable.root.put(classSymbolTableItem);
        } catch (ItemAlreadyExistsException e) {
            ClassRedefinition exception = new ClassRedefinition(classDeclaration.getLine() , classDeclaration.toString());
            classDeclaration.addError(exception);
            //exception.handleException();   ------> i dont get this line
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
        SymbolTable.pop();
        return null;
    }

    @Override
    public Void visit(ConstructorDeclaration constructorDeclaration) {
        this.visit((MethodDeclaration) constructorDeclaration);
        return null;
    }

    @Override
    public Void visit(MethodDeclaration methodDeclaration) {
        MethodSymbolTableItem methodSymbolTableItem = new MethodSymbolTableItem(methodDeclaration);
        SymbolTable methodSymbolTable = new SymbolTable(SymbolTable.top);
        methodSymbolTableItem.setMethodSymbolTable(methodSymbolTable);
        try {
            SymbolTable.top.put(methodSymbolTableItem);
        } catch (ItemAlreadyExistsException e) {
            MethodRedefinition exception = new MethodRedefinition(methodDeclaration.getLine(), methodDeclaration.toString());
            methodDeclaration.addError(exception);
        }
        SymbolTable.push(methodSymbolTable);
        for(VariableDeclaration varDeclaration : methodDeclaration.getArgs()) {
            varDeclaration.accept(this);
        }
        for(VariableDeclaration varDeclaration : methodDeclaration.getLocalVars()) {
            varDeclaration.accept(this);
        }
        SymbolTable.pop();
        return null;
    }

    @Override
    public Void visit(FieldDeclaration fieldDeclaration) {
        try {
            SymbolTable.top.put(new FieldSymbolTableItem(fieldDeclaration));
        } catch (ItemAlreadyExistsException e) {
            FieldRedefinition exception = new FieldRedefinition(fieldDeclaration.getLine(), fieldDeclaration.toString());
            fieldDeclaration.addError(exception);
        }
        return null;
    }

    @Override
    public Void visit(VariableDeclaration varDeclaration) {
        try {
            SymbolTable.top.put(new LocalVariableSymbolTableItem(varDeclaration));
        } catch (ItemAlreadyExistsException e) {
            LocalVarRedefinition exception = new LocalVarRedefinition(varDeclaration.getLine(), varDeclaration.toString());
            varDeclaration.addError(exception);
        }
        return null;
    }

}