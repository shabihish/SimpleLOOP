package main.visitor.utils;

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
import main.util.ArgPair;
import main.visitor.*;

public class ASTTreePrinter extends Visitor<Void> {
    public void messagePrinter(int line, String message){
        System.out.println("Line " + line + ": " + message);
    }

    @Override
    public Void visit(Program program) {
        messagePrinter(program.getLine(), program.toString());
        for (VariableDeclaration variableDeclaration : program.getGlobalVariables())
            variableDeclaration.accept(this);
        for (ClassDeclaration classDeclaration: program.getClasses())
            classDeclaration.accept(this);
        return null;
    }

    @Override
    public Void visit(ClassDeclaration classDeclaration) {
        messagePrinter(classDeclaration.getLine(), classDeclaration.toString());
        classDeclaration.getClassName().accept(this);
        if (classDeclaration.getParentClassName() != null)
            classDeclaration.getParentClassName().accept(this);
        for (FieldDeclaration fieldDeclaration : classDeclaration.getFields())
            fieldDeclaration.accept(this);
        if (classDeclaration.getConstructor() != null)
            classDeclaration.getConstructor().accept(this);
        for (MethodDeclaration methodDeclaration : classDeclaration.getMethods())
            methodDeclaration.accept(this);
        return null;
    }

    @Override
    public Void visit(ConstructorDeclaration constructorDeclaration) {
        messagePrinter(constructorDeclaration.getLine(), constructorDeclaration.toString());
        for (ArgPair argPair : constructorDeclaration.getArgs())
            argPair.getVariableDeclaration().accept(this);
        for (VariableDeclaration variableDeclaration : constructorDeclaration.getLocalVars())
            variableDeclaration.accept(this);
        for (Statement statement : constructorDeclaration.getBody())
            statement.accept(this);
        return null;
    }

    @Override
    public Void visit(MethodDeclaration methodDeclaration) {
        messagePrinter(methodDeclaration.getLine(), methodDeclaration.toString());
        methodDeclaration.getMethodName().accept(this);
        for (ArgPair argPair : methodDeclaration.getArgs()) {
            argPair.getVariableDeclaration().accept(this);
            if (argPair.getDefaultValue() != null)
                argPair.getDefaultValue().accept(this);
        }
        for (VariableDeclaration variableDeclaration : methodDeclaration.getLocalVars())
            variableDeclaration.accept(this);
        for (Statement statement : methodDeclaration.getBody())
            statement.accept(this);
        return null;
    }

    @Override
    public Void visit(FieldDeclaration fieldDeclaration) {
        messagePrinter(fieldDeclaration.getLine(), fieldDeclaration.toString());
        fieldDeclaration.getVarDeclaration().accept(this);
        return null;
    }

    @Override
    public Void visit(VariableDeclaration varDeclaration) {
        messagePrinter(varDeclaration.getLine(), varDeclaration.toString());
        varDeclaration.getVarName().accept(this);
        return null;
    }

    @Override
    public Void visit(AssignmentStmt assignmentStmt) {
        messagePrinter(assignmentStmt.getLine(), assignmentStmt.toString());
        assignmentStmt.getlValue().accept(this);
        assignmentStmt.getrValue().accept(this);
        return null;
    }

    @Override
    public Void visit(BlockStmt blockStmt) {
        messagePrinter(blockStmt.getLine(), blockStmt.toString());
        for (Statement statement : blockStmt.getStatements())
            statement.accept(this);
        return null;
    }

    @Override
    public Void visit(ConditionalStmt conditionalStmt) {
        messagePrinter(conditionalStmt.getLine(), conditionalStmt.toString());
        conditionalStmt.getCondition().accept(this);
        conditionalStmt.getThenBody().accept(this);
        for (Statement statement : conditionalStmt.getElsif())
            statement.accept(this);
        if (conditionalStmt.getElseBody() != null)
            conditionalStmt.getElseBody().accept(this);
        return null;
    }

    @Override
    public Void visit(ElsifStmt elsifStmt) {
        messagePrinter(elsifStmt.getLine(), elsifStmt.toString());
        elsifStmt.getCondition().accept(this);
        elsifStmt.getThenBody().accept(this);
        return null;
    }

    @Override
    public Void visit(MethodCallStmt methodCallStmt) {
        messagePrinter(methodCallStmt.getLine(), methodCallStmt.toString());
        methodCallStmt.getMethodCall().accept(this);
        return null;
    }

    @Override
    public Void visit(PrintStmt print) {
        messagePrinter(print.getLine(), print.toString());
        print.getArg().accept(this);
        return null;
    }

    @Override
    public Void visit(ReturnStmt returnStmt) {
        messagePrinter(returnStmt.getLine(), returnStmt.toString());
        returnStmt.getReturnedExpr().accept(this);
        return null;
    }

    @Override
    public Void visit(EachStmt eachStmt) {
        messagePrinter(eachStmt.getLine(), eachStmt.toString());
        eachStmt.getVariable().accept(this);
        eachStmt.getList().accept(this);
        eachStmt.getBody().accept(this);
        return null;
    }

    @Override
    public Void visit(BinaryExpression binaryExpression) {
        messagePrinter(binaryExpression.getLine(), binaryExpression.toString());
        binaryExpression.getFirstOperand().accept(this);
        binaryExpression.getSecondOperand().accept(this);
        return null;
    }

    @Override
    public Void visit(UnaryExpression unaryExpression) {
        messagePrinter(unaryExpression.getLine(), unaryExpression.toString());
        unaryExpression.getOperand().accept(this);
        return null;
    }

    @Override
    public Void visit(TernaryExpression ternaryExpression) {
        messagePrinter(ternaryExpression.getLine(), ternaryExpression.toString());
        ternaryExpression.getCondition().accept(this);
        ternaryExpression.getTrueExpression().accept(this);
        ternaryExpression.getFalseExpression().accept(this);
        return null;
    }

    @Override
    public Void visit(ObjectMemberAccess objectMemberAccess) {
        messagePrinter(objectMemberAccess.getLine(), objectMemberAccess.toString());
        objectMemberAccess.getInstance().accept(this);
        objectMemberAccess.getMemberName().accept(this);
        return null;
    }

    @Override
    public Void visit(Identifier identifier) {
        messagePrinter(identifier.getLine(), identifier.toString());
        return null;
    }

    @Override
    public Void visit(ArrayAccessByIndex arrayAccessByIndex) {
        messagePrinter(arrayAccessByIndex.getLine(), arrayAccessByIndex.toString());
        arrayAccessByIndex.getInstance().accept(this);
        arrayAccessByIndex.getIndex().accept(this);
        return null;
    }

    @Override
    public Void visit(MethodCall methodCall) {
        messagePrinter(methodCall.getLine(), methodCall.toString());
        methodCall.getInstance().accept(this);
        for (Expression expression : methodCall.getArgs())
            expression.accept(this);
        return null;
    }

    @Override
    public Void visit(NewClassInstance newClassInstance) {
        messagePrinter(newClassInstance.getLine(), newClassInstance.toString());
        for (Expression expression : newClassInstance.getArgs())
            expression.accept(this);
        return null;
    }

    @Override
    public Void visit(SelfClass selfClass) {
        messagePrinter(selfClass.getLine(), selfClass.toString());
        return null;
    }

    @Override
    public Void visit(NullValue nullValue) {
        messagePrinter(nullValue.getLine(), nullValue.toString());
        return null;
    }

    @Override
    public Void visit(IntValue intValue) {
        messagePrinter(intValue.getLine(), intValue.toString());
        return null;
    }

    @Override
    public Void visit(BoolValue boolValue) {
        messagePrinter(boolValue.getLine(), boolValue.toString());
        return null;
    }

    @Override
    public Void visit(SetInclude setInclude) {
        messagePrinter(setInclude.getLine(), setInclude.toString());
        setInclude.getSetArg().accept(this);
        setInclude.getElementArg().accept(this);
        return null;
    }

    @Override
    public Void visit(SetValue setValue) {
        messagePrinter(setValue.getLine(), setValue.toString());
        for (IntValue intValue : setValue.getElements())
            intValue.accept(this);
        return null;
    }

    @Override
    public Void visit(SetNew setNew) {
        messagePrinter(setNew.getLine(), setNew.toString());
        for (Expression expression : setNew.getArgs())
            expression.accept(this);
        return null;
    }

    @Override
    public Void visit(SetDelete setDelete) {
        messagePrinter(setDelete.getLine(), setDelete.toString());
        setDelete.getSetArg().accept(this);
        setDelete.getElementArg().accept(this);
        return null;
    }

    @Override
    public Void visit(SetAdd setAdd) {
        messagePrinter(setAdd.getLine(), setAdd.toString());
        setAdd.getSetArg().accept(this);
        setAdd.getElementArg().accept(this);
        return null;
    }

    @Override
    public Void visit(SetMerge setMerge) {
        messagePrinter(setMerge.getLine(), setMerge.toString());
        setMerge.getSetArg().accept(this);
        for (Expression expression : setMerge.getElementArgs())
            expression.accept(this);
        return null;
    }

    @Override
    public Void visit(RangeExpression rangeExpression) {
        messagePrinter(rangeExpression.getLine(), rangeExpression.toString());
        rangeExpression.getLeftExpression().accept(this);
        rangeExpression.getRightExpression().accept(this);
        return null;
    }
}
