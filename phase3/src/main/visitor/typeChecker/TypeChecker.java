package main.visitor.typeChecker;

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
import main.ast.types.NoType;
import main.ast.types.Type;
import main.ast.types.primitives.BoolType;
import main.ast.types.primitives.IntType;
import main.ast.types.set.SetType;
import main.compileError.typeError.UnsupportedTypeForPrint;
import main.symbolTable.utils.graph.Graph;
import main.visitor.*;

import javax.lang.model.type.ArrayType;

public class TypeChecker extends Visitor<Void> {
    private Graph<String> classHierarchy;
    ExpressionTypeChecker expressionTypeChecker;

    public TypeChecker(Graph<String> classHierarchy){
        this.classHierarchy = classHierarchy;
        this.expressionTypeChecker = new ExpressionTypeChecker(classHierarchy);
    }

    @Override
    public Void visit(Program program) {
        //todo
        return null;
    }

    @Override
    public Void visit(ClassDeclaration classDeclaration) {
        //todo
        return null;
    }

    @Override
    public Void visit(ConstructorDeclaration constructorDeclaration) {
        //todo
        return null;
    }

    @Override
    public Void visit(MethodDeclaration methodDeclaration) {
        //todo
        return null;
    }

    @Override
    public Void visit(FieldDeclaration fieldDeclaration) {
        //todo
        return null;
    }

    @Override
    public Void visit(VariableDeclaration varDeclaration) {
        //todo
        return null;
    }

    @Override
    public Void visit(AssignmentStmt assignmentStmt) {
        //todo
        return null;
    }

    @Override
    public Void visit(BlockStmt blockStmt) {
        //todo
        return null;
    }

    @Override
    public Void visit(ConditionalStmt conditionalStmt) {
        //todo
        return null;
    }

    @Override
    public Void visit(ElsifStmt elsifStmt) {
        //todo
        return null;
    }

    @Override
    public Void visit(MethodCallStmt methodCallStmt) {
        //todo
        return null;
    }

    @Override
    public Void visit(PrintStmt print) {
        Type argType = print.getArg().accept(expressionTypeChecker);
        if(!(argType instanceof IntType || argType instanceof ArrayType || argType instanceof SetType ||
                argType instanceof BoolType || argType instanceof NoType)) {
            UnsupportedTypeForPrint exception = new UnsupportedTypeForPrint(print.getLine());
            print.addError(exception);
        }
        return null;
    }

    @Override
    public Void visit(ReturnStmt returnStmt) {
        //todo
        return null;
    }

    @Override
    public Void visit(EachStmt eachStmt) {
        //todo
        return null;
    }

    @Override
    public Void visit(SetDelete setDelete) {
        //todo
        return null;
    }

    @Override
    public Void visit(SetMerge setMerge) {
        //todo
        return null;
    }

    @Override
    public Void visit(SetAdd setAdd) {
        //todo
        return null;
    }

}
