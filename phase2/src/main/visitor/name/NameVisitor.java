package main.visitor.name;


import main.compileError.nameError.*;

import main.ast.nodes.*;
import main.ast.nodes.declaration.classDec.classMembersDec.MethodDeclaration;
import main.ast.nodes.declaration.classDec.ClassDeclaration;
import main.ast.nodes.declaration.classDec.classMembersDec.ConstructorDeclaration;
import main.symbolTable.exceptions.ItemNotFoundException;
import main.ast.nodes.declaration.variableDec.VariableDeclaration;
import main.symbolTable.SymbolTable;
import main.ast.nodes.declaration.classDec.classMembersDec.FieldDeclaration;
import main.visitor.*;
import main.symbolTable.items.*;

import java.security.NoSuchAlgorithmException;
import main.symbolTable.items.ClassSymbolTableItem;
import main.symbolTable.exceptions.ItemAlreadyExistsException;



public class NameVisitor extends Visitor<Void> {

    @Override
    public Void visit(Program program) {
        SymbolTable.push(new SymbolTable());
        SymbolTable.root = SymbolTable.top;
        for (VariableDeclaration globalVariableDeclaration : program.getGlobalVariables()) {
            globalVariableDeclaration.accept(this);
        }
        for (ClassDeclaration classDeclaration : program.getClasses()) {
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
            ClassRedefinition exception = new ClassRedefinition(
                    classDeclaration.getLine(), classDeclaration.getClassName().getName()
            );

            classDeclaration.addError(exception);
            try {
                classDeclaration.getClassName().setName(Utils.genRandomName("CLASS_" + classDeclaration.getClassName().getName()));
                ClassSymbolTableItem classSymbolTableItem1 = new ClassSymbolTableItem(classDeclaration);
                classSymbolTableItem1.setClassSymbolTable(SymbolTable.top);
                SymbolTable.root.put(classSymbolTableItem1);
            } catch (NoSuchAlgorithmException | ItemAlreadyExistsException noSuchAlgorithmException) {
                noSuchAlgorithmException.printStackTrace();
            }
        }

        for (FieldDeclaration fieldDeclaration : classDeclaration.getFields()) {
            fieldDeclaration.accept(this);
        }

        if (classDeclaration.getConstructor() != null && classDeclaration!=null) {
            classDeclaration.getConstructor().accept(this);
        }

        for (MethodDeclaration methodDeclaration : classDeclaration.getMethods()) {
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
            MethodRedefinition exception = new MethodRedefinition(methodDeclaration.getLine(), methodDeclaration.getMethodName().getName());
            methodDeclaration.addError(exception);
        }

        SymbolTable.push(methodSymbolTable);

        for (VariableDeclaration varDeclaration : methodDeclaration.getArgs()) {
            varDeclaration.accept(this);
        }

        for (VariableDeclaration varDeclaration : methodDeclaration.getLocalVars()) {
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
//          // TODO: Should the name be taken like this?
            FieldRedefinition exception = new FieldRedefinition(fieldDeclaration.getLine(), fieldDeclaration.getVarDeclaration().getVarName().getName());
            fieldDeclaration.addError(exception);

            try {
                fieldDeclaration.getVarDeclaration().getVarName().setName(Utils.genRandomName("FIELD_" + fieldDeclaration.getVarDeclaration().getVarName().getName()));
                SymbolTable.top.put(new FieldSymbolTableItem(fieldDeclaration));
            } catch (NoSuchAlgorithmException | ItemAlreadyExistsException noSuchAlgorithmException) {
                noSuchAlgorithmException.printStackTrace();
            }
        }
        return null;
    }

    @Override
    public Void visit(VariableDeclaration varDeclaration) {
        if (!varDeclaration.isGlobal()) {
            try {
                GlobalVariableSymbolTableItem globalVariableSymbolTableItem = new GlobalVariableSymbolTableItem(varDeclaration);
                SymbolTable.top.getItem(globalVariableSymbolTableItem.getKey(), true);
                LocalVarConflictWithGlobalVar exception = new LocalVarConflictWithGlobalVar(varDeclaration.getLine(), varDeclaration.getVarName().getName());
                varDeclaration.addError(exception);

                varDeclaration.getVarName().setName(Utils.genRandomName("VAR_" + varDeclaration.getVarName().getName()));
            } catch (ItemNotFoundException | NoSuchAlgorithmException ignored) {
            }
            try {
                SymbolTable.top.put(new LocalVariableSymbolTableItem(varDeclaration));
            } catch (ItemAlreadyExistsException e) {
                LocalVarRedefinition exception = new LocalVarRedefinition(varDeclaration.getLine(), varDeclaration.getVarName().getName());
                varDeclaration.addError(exception);
            }
        } else {
            try {
                SymbolTable.top.put(new GlobalVariableSymbolTableItem(varDeclaration));
            } catch (ItemAlreadyExistsException e) {
                GlobalVarRedefinition exception = new GlobalVarRedefinition(varDeclaration.getLine(), varDeclaration.getVarName().getName());
                varDeclaration.addError(exception);

                try {
                    varDeclaration.getVarName().setName(Utils.genRandomName("GLVAR_" + varDeclaration.getVarName().getName()));
                    SymbolTable.top.put(new GlobalVariableSymbolTableItem(varDeclaration));
                } catch (NoSuchAlgorithmException | ItemAlreadyExistsException noSuchAlgorithmException) {
                    noSuchAlgorithmException.printStackTrace();
                }
            }
        }
        return null;
    }
}