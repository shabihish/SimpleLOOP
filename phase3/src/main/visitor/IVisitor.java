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

public interface IVisitor<T> {

    T visit(Program program);

    T visit(ClassDeclaration classDeclaration);
    T visit(ConstructorDeclaration constructorDeclaration);
    T visit(MethodDeclaration methodDeclaration);
    T visit(FieldDeclaration fieldDeclaration);
    T visit(VariableDeclaration varDeclaration);

    T visit(AssignmentStmt assignmentStmt);
    T visit(BlockStmt blockStmt);
    T visit(ConditionalStmt conditionalStmt);
    T visit(ElsifStmt elsifStmt);
    T visit(MethodCallStmt methodCallStmt);
    T visit(PrintStmt print);
    T visit(ReturnStmt returnStmt);
    T visit(EachStmt EachStmt);

    T visit(BinaryExpression binaryExpression);
    T visit(UnaryExpression unaryExpression);
    T visit(TernaryExpression ternaryExpression);
    T visit(RangeExpression rangeExpression);
    T visit(ObjectMemberAccess objectOrListMemberAccess);
    T visit(Identifier identifier);
    T visit(ArrayAccessByIndex listAccessByIndex);
    T visit(MethodCall methodCall);
    T visit(NewClassInstance newClassInstance);
    T visit(SelfClass selfClass);
    T visit(NullValue nullValue);
    T visit(IntValue intValue);
    T visit(BoolValue boolValue);
    T visit(SetValue setValue);


    T visit(SetInclude setAdd);

    T visit(SetNew setNew);

    T visit(SetDelete setDelete);

    T visit(SetMerge setMerge);

    T visit(SetAdd setAdd);
}
