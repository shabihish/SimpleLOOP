package main.visitor;

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
import main.visitor.*;

import java.lang.reflect.Field;
import java.util.ArrayList;
import javax.swing.plaf.nimbus.State;

public class ReporterUtil extends Visitor<Integer> {
    private int printErrors(Node node) {
        ArrayList<CompileError> errors = node.flushErrors();
        for(CompileError compileErrorException : errors) {
            System.out.println(compileErrorException.getMessage());
        }
        return errors.size();
    }

    @Override
    public Integer visit(Program program) {
        int numOfErrors = printErrors(program);
        for (VariableDeclaration variableDeclaration: program.getGlobalVariables())
            numOfErrors += variableDeclaration.accept(this);
        for (ClassDeclaration classDeclaration:program.getClasses())
            numOfErrors += classDeclaration.accept(this);
        return numOfErrors;
    }

    //different with ast not sure if 'if' are needed
    @Override
    public Integer visit(ClassDeclaration classDeclaration) {
        int numOfErrors = printErrors(classDeclaration);
        numOfErrors += classDeclaration.getClassName().accept(this);
        if (classDeclaration.getParentClassName() != null)
            numOfErrors += classDeclaration.getParentClassName().accept(this);
        for(FieldDeclaration fieldDeclaration : classDeclaration.getFields())
            numOfErrors += fieldDeclaration.accept(this);
        if (classDeclaration.getConstructor() != null)
            numOfErrors += classDeclaration.getConstructor().accept(this);
        for (MethodDeclaration method : classDeclaration.getMethods())
            numOfErrors += method.accept(this);

        return numOfErrors;
    }

    //different with AST
    @Override
    public Integer visit(ConstructorDeclaration constructorDeclaration) {
       // int numOfErrors = printErrors(c);
        int numOfErrors = this.visit((MethodDeclaration) constructorDeclaration);
        return numOfErrors;
    }

    //different with AST
    @Override
    public Integer visit(MethodDeclaration methodDeclaration) {
        int numOfErrors = printErrors(methodDeclaration);
        numOfErrors += methodDeclaration.getMethodName().accept(this);
        for(VariableDeclaration variableDeclaration : methodDeclaration.getArgs())
            numOfErrors += variableDeclaration.accept(this);
        for(VariableDeclaration variableDeclaration : methodDeclaration.getLocalVars())
            numOfErrors += variableDeclaration.accept(this);
        return numOfErrors;
    }

    @Override
    public Integer visit(FieldDeclaration fieldDeclaration) {
        int numOfErrors = printErrors(fieldDeclaration);
        numOfErrors += fieldDeclaration.getVarDeclaration().accept(this);
        return numOfErrors;
    }

    @Override
    public Integer visit(VariableDeclaration varDeclaration) {
        int numOfErrors = printErrors(varDeclaration);
        numOfErrors += varDeclaration.getVarName().accept(this);
        return numOfErrors;
    }

    @Override
    public Integer visit(AssignmentStmt assignmentStmt) {
        int numOfErrors = printErrors(assignmentStmt);
        numOfErrors += assignmentStmt.getlValue().accept(this);
        numOfErrors += assignmentStmt.getrValue().accept(this);
        return numOfErrors;
    }

    @Override
    public Integer visit(BlockStmt blockStmt) {
        int numOfErrors = printErrors(blockStmt);
        for (Statement stmt : blockStmt.getStatements())
            numOfErrors += stmt.accept(this);
        return numOfErrors;
    }

    @Override
    public Integer visit(ConditionalStmt conditionalStmt) {
        int numOfErrors = printErrors(conditionalStmt);
        numOfErrors += conditionalStmt.getCondition().accept(this);
        numOfErrors += conditionalStmt.getThenBody().accept(this);
        if (conditionalStmt.getElseBody() != null)
            numOfErrors += conditionalStmt.getElseBody().accept(this);
        for (ElsifStmt elsifStmt : conditionalStmt.getElsif())
            numOfErrors += elsifStmt.accept(this);
        return numOfErrors;
    }

    @Override
    public Integer visit(ElsifStmt elsifStmt) {
        int numOfErrors = printErrors(elsifStmt);
        numOfErrors += elsifStmt.getCondition().accept(this);
        numOfErrors += elsifStmt.getThenBody().accept(this);
        return numOfErrors;
    }

    @Override
    public Integer visit(MethodCallStmt methodCallStmt) {
        int numOfErrors = printErrors(methodCallStmt);
        numOfErrors += methodCallStmt.getMethodCall().accept(this);
        return numOfErrors;
    }

    @Override
    public Integer visit(PrintStmt print) {
        int numOfErrors = printErrors(print);
        numOfErrors += print.getArg().accept(this);
        return numOfErrors;
    }

    @Override
    public Integer visit(ReturnStmt returnStmt) {
        int numOfErrors = printErrors(returnStmt);
        if (returnStmt.getReturnedExpr() != null) //why if????
            numOfErrors += returnStmt.getReturnedExpr().accept(this);
        return numOfErrors;
    }

    //same as AST
    //not sure
    //check it
    @Override
    public Integer visit(EachStmt eachStmt) {
        int numOfErrors = printErrors(eachStmt);
        numOfErrors += eachStmt.getList().accept(this);
        numOfErrors += eachStmt.getVariable().accept(this);
        numOfErrors += eachStmt.getBody().accept(this);
        return numOfErrors;
    }

    @Override
    public Integer visit(BinaryExpression binaryExpression) {
        int numOfErrors = printErrors(binaryExpression);
        numOfErrors += binaryExpression.getFirstOperand().accept(this);
        numOfErrors += binaryExpression.getSecondOperand().accept(this);
        return numOfErrors;
    }

    @Override
    public Integer visit(UnaryExpression unaryExpression) {
        int numOfErrors = printErrors(unaryExpression);
        numOfErrors += unaryExpression.getOperand().accept(this);
        return numOfErrors;
    }

    @Override
    public Integer visit(TernaryExpression ternaryExpression) {
        int numOfErrors = printErrors(ternaryExpression);
        numOfErrors += ternaryExpression.getCondition().accept(this);
        numOfErrors += ternaryExpression.getTrueExpression().accept(this);
        numOfErrors += ternaryExpression.getFalseExpression().accept(this);
        return numOfErrors;
    }

    //order is correct
    @Override
    public Integer visit(ObjectMemberAccess objectOrListMemberAccess) {
        int numOfErrors = printErrors(objectOrListMemberAccess);
        numOfErrors += objectOrListMemberAccess.getInstance().accept(this);
        numOfErrors += objectOrListMemberAccess.getMemberName().accept(this);
        return numOfErrors;
    }

    @Override
    public Integer visit(Identifier identifier) {
        int numOfErrors = printErrors(identifier);
        return numOfErrors;
    }

    //order is correct
    @Override
    public Integer visit(ArrayAccessByIndex arrayAccessByIndex) {
        int numOfErrors = printErrors(arrayAccessByIndex);
        numOfErrors += arrayAccessByIndex.getInstance().accept(this);
        numOfErrors += arrayAccessByIndex.getIndex().accept(this);
        return numOfErrors;
    }

    //order is correct
    @Override
    public Integer visit(MethodCall methodCall) {
        int numOfErrors = printErrors(methodCall);
        numOfErrors += methodCall.getInstance().accept(this);
        for (Expression arg : methodCall.getArgs())
            numOfErrors += arg.accept(this);
        return numOfErrors;
    }


    @Override
    public Integer visit(NewClassInstance newClassInstance) {
        int numOfErrors = printErrors(newClassInstance);
        for (Expression arg : newClassInstance.getArgs())
            numOfErrors += arg.accept(this);
        return numOfErrors;
    }

    @Override
    public Integer visit(SelfClass selfClass) {
        int numOfErrors = printErrors(selfClass);
        return numOfErrors;
    }

    @Override
    public Integer visit(NullValue nullValue) {
        int numOfErrors = printErrors(nullValue);
        return numOfErrors;
    }

    @Override
    public Integer visit(IntValue intValue) {
        int numOfErrors = printErrors(intValue);
        return numOfErrors;
    }

    @Override
    public Integer visit(BoolValue boolValue) {
        int numOfErrors = printErrors(boolValue);
        return numOfErrors;
    }

    //order is ok
    @Override
    public Integer visit(SetInclude setAdd) {
        int numOfErrors = printErrors(setAdd);
        numOfErrors += setAdd.getSetArg().accept(this);
        numOfErrors += setAdd.getElementArg().accept(this);
        return numOfErrors;
    }

    @Override
    public Integer visit(SetValue setValue) {
        int numOfErrors = printErrors(setValue);
        for (IntValue val: setValue.getElements())
            numOfErrors += val.accept(this);
        return numOfErrors;
    }

    @Override
    public Integer visit(SetNew setMerge) {
        int numOfErrors = printErrors(setMerge);
        for (Expression arg: setMerge.getArgs())
            numOfErrors += arg.accept(this);
        return numOfErrors;
    }

    @Override
    public Integer visit(SetDelete setDelete) {
        int numOfErrors = printErrors(setDelete);
        numOfErrors += setDelete.getSetArg().accept(this);
        numOfErrors += setDelete.getElementArg().accept(this);
        return numOfErrors;
    }

    @Override
    public Integer visit(SetMerge setAdd) {
        int numOfErrors = printErrors(setAdd);
        numOfErrors += setAdd.getSetArg().accept(this);
        for (Expression arg: setAdd.getElementArgs())
            numOfErrors += arg.accept(this);
        return numOfErrors;
    }

    // Overridden
    @Override
    public Integer visit(SetAdd setAdd) {
        int numOfErrors = printErrors(setAdd);
        numOfErrors += setAdd.getSetArg().accept(this);
        numOfErrors += setAdd.getElementArg().accept(this);
        return numOfErrors;
    }

    //
    @Override
    public Integer visit(RangeExpression rangeExpression) {
        int numOfErrors = printErrors(rangeExpression);
        numOfErrors += rangeExpression.getLeftExpression().accept(this);
        numOfErrors += rangeExpression.getRightExpression().accept(this);
        return numOfErrors;
    }

}

