package main.visitor;

import main.ast.nodes.Program;
import main.ast.nodes.declaration.classDec.ClassDeclaration;
import main.ast.nodes.declaration.classDec.classMembersDec.ConstructorDeclaration;
import main.ast.nodes.declaration.classDec.classMembersDec.FieldDeclaration;
import main.ast.nodes.declaration.classDec.classMembersDec.MethodDeclaration;
import main.ast.nodes.declaration.variableDec.VariableDeclaration;
import main.ast.nodes.expression.*;
import main.ast.nodes.expression.values.*;
import main.ast.nodes.expression.values.primitive.*;
import main.ast.nodes.statement.*;
import main.ast.nodes.statement.set.*;

public class Visitor<T> implements IVisitor<T> {

    @Override
    public T visit(Program program) {
        return null;
    }

    @Override
    public T visit(ClassDeclaration classDeclaration) {
        return null;
    }

    @Override
    public T visit(ConstructorDeclaration constructorDeclaration) {
        return null;
    }

    @Override
    public T visit(MethodDeclaration methodDeclaration) {
        return null;
    }

    @Override
    public T visit(FieldDeclaration fieldDeclaration) {
        return null;
    }

    @Override
    public T visit(VariableDeclaration varDeclaration) {
        return null;
    }

    @Override
    public T visit(AssignmentStmt assignmentStmt) {
        return null;
    }

    @Override
    public T visit(BlockStmt blockStmt) {
        return null;
    }

    @Override
    public T visit(ConditionalStmt conditionalStmt) {
        return null;
    }

    @Override
    public T visit(ElsifStmt elsifStmt) {
        return null;
    }

    @Override
    public T visit(MethodCallStmt methodCallStmt) {
        return null;
    }

    @Override
    public T visit(PrintStmt print) {
        return null;
    }

    @Override
    public T visit(ReturnStmt returnStmt) {
        return null;
    }

    @Override
    public T visit(EachStmt eachStmt) {
        return null;
    }

    @Override
    public T visit(BinaryExpression binaryExpression) {
        return null;
    }

    @Override
    public T visit(UnaryExpression unaryExpression) {
        return null;
    }

    @Override
    public T visit(TernaryExpression ternaryExpression) {
        return null;
    }

    @Override
    public T visit(RangeExpression rangeExpression) {
        return null;
    }

    @Override
    public T visit(ObjectMemberAccess objectOrArrayMemberAccess) {
        return null;
    }

    @Override
    public T visit(Identifier identifier) {
        return null;
    }

    @Override
    public T visit(ArrayAccessByIndex arrayAccessByIndex) {
        return null;
    }

    @Override
    public T visit(MethodCall methodCall) {
        return null;
    }

    @Override
    public T visit(NewClassInstance newClassInstance) {
        return null;
    }

    @Override
    public T visit(SelfClass selfClass) {
        return null;
    }

    @Override
    public T visit(NullValue nullValue) {
        return null;
    }

    @Override
    public T visit(IntValue intValue) {
        return null;
    }

    @Override
    public T visit(BoolValue boolValue) {
        return null;
    }

    @Override
    public T visit(SetValue setValue) {
        return null;
    }

    @Override
    public T visit(SetInclude setAdd) {
        return null;
    }

    @Override
    public T visit(SetNew setNew) {
        return null;
    }

    @Override
    public T visit(SetDelete setDelete) {
        return null;
    }

    @Override
    public T visit(SetMerge setMerge) {
        return null;
    }

    @Override
    public T visit(SetAdd setAdd) {
        return null;
    }
}
