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
import main.compileError.CompileError;
import main.util.ArgPair;
import main.visitor.*;

import java.util.ArrayList;

public class ErrorReporter extends Visitor<Integer> {

    private int printErrors(Node node) {
        ArrayList<CompileError> errors = node.flushErrors();
        for(CompileError compileError : errors) {
            System.out.println(compileError.getMessage());
        }
        return errors.size();
    }

    @Override
    public Integer visit(Program program) {
        int numOfErrors = printErrors(program);
        for (VariableDeclaration variableDeclaration : program.getGlobalVariables()) {
            numOfErrors += variableDeclaration.accept(this);
        }
        for(ClassDeclaration classDeclaration : program.getClasses()) {
            numOfErrors += classDeclaration.accept(this);
        }
        return numOfErrors;
    }

    @Override
    public Integer visit(ClassDeclaration classDeclaration) {
        int numOfErrors = printErrors(classDeclaration);
        numOfErrors += classDeclaration.getClassName().accept(this);
        if(classDeclaration.getParentClassName() != null) {
            numOfErrors += classDeclaration.getParentClassName().accept(this);
        }
        for(FieldDeclaration fieldDeclaration : classDeclaration.getFields()) {
            numOfErrors += fieldDeclaration.accept(this);
        }
        if(classDeclaration.getConstructor() != null) {
            numOfErrors += classDeclaration.getConstructor().accept(this);
        }
        for(MethodDeclaration methodDeclaration : classDeclaration.getMethods()) {
            numOfErrors += methodDeclaration.accept(this);
        }
        return numOfErrors;
    }

    @Override
    public Integer visit(ConstructorDeclaration constructorDeclaration) {
        return this.visit((MethodDeclaration) constructorDeclaration);
    }

    @Override
    public Integer visit(MethodDeclaration methodDeclaration) {
        int numOfErrors = printErrors(methodDeclaration);
        numOfErrors += methodDeclaration.getMethodName().accept(this);
        for(ArgPair argPair : methodDeclaration.getArgs()) {
            numOfErrors += argPair.getVariableDeclaration().accept(this);
        }
        for(VariableDeclaration varDeclaration : methodDeclaration.getLocalVars()) {
            numOfErrors += varDeclaration.accept(this);
        }
        for(Statement statement : methodDeclaration.getBody()) {
            numOfErrors += statement.accept(this);
        }
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
        for(Statement statement : blockStmt.getStatements()) {
            numOfErrors += statement.accept(this);
        }
        return numOfErrors;
    }

    @Override
    public Integer visit(ConditionalStmt conditionalStmt) {
        int numOfErrors = printErrors(conditionalStmt);
        numOfErrors += conditionalStmt.getCondition().accept(this);
        numOfErrors += conditionalStmt.getThenBody().accept(this);
        for (ElsifStmt elsifStmt : conditionalStmt.getElsif())
            numOfErrors += elsifStmt.accept(this);
        if(conditionalStmt.getElseBody() != null) {
            numOfErrors += conditionalStmt.getElseBody().accept(this);
        }
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
        numOfErrors += returnStmt.getReturnedExpr().accept(this);
        return numOfErrors;
    }

    @Override
    public Integer visit(EachStmt eachStmt) {
        int numOfErrors = printErrors(eachStmt);
        numOfErrors += eachStmt.getVariable().accept(this);
        numOfErrors += eachStmt.getList().accept(this);
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

    @Override
    public Integer visit(ObjectMemberAccess objectMemberAccess) {
        int numOfErrors = printErrors(objectMemberAccess);
        numOfErrors += objectMemberAccess.getInstance().accept(this);
        numOfErrors += objectMemberAccess.getMemberName().accept(this);
        return numOfErrors;
    }

    @Override
    public Integer visit(Identifier identifier) {
        return printErrors(identifier);
    }

    @Override
    public Integer visit(ArrayAccessByIndex arrayAccessByIndex) {
        int numOfErrors = printErrors(arrayAccessByIndex);
        numOfErrors += arrayAccessByIndex.getInstance().accept(this);
        numOfErrors += arrayAccessByIndex.getIndex().accept(this);
        return numOfErrors;
    }

    @Override
    public Integer visit(MethodCall methodCall) {
        int numOfErrors = printErrors(methodCall);
        numOfErrors += methodCall.getInstance().accept(this);
        for(Expression expression : methodCall.getArgs()) {
            numOfErrors += expression.accept(this);
        }
        return numOfErrors;
    }

    @Override
    public Integer visit(NewClassInstance newClassInstance) {
        int numOfErrors = printErrors(newClassInstance);
        for(Expression expression : newClassInstance.getArgs()) {
            numOfErrors += expression.accept(this);
        }
        return numOfErrors;
    }

    @Override
    public Integer visit(SelfClass selfClass) {
        return printErrors(selfClass);
    }

    @Override
    public Integer visit(NullValue nullValue) {
        return printErrors(nullValue);
    }

    @Override
    public Integer visit(IntValue intValue) {
        return printErrors(intValue);
    }

    @Override
    public Integer visit(BoolValue boolValue) {
        return printErrors(boolValue);
    }

    @Override
    public Integer visit(SetInclude setInclude) {
        int numOfErrors = printErrors(setInclude);
        numOfErrors += setInclude.getSetArg().accept(this);
        numOfErrors += setInclude.getElementArg().accept(this);
        return numOfErrors;
    }

    @Override
    public Integer visit(SetValue setValue) {
        int numOfErrors = printErrors(setValue);
        for (IntValue intValue : setValue.getElements())
            numOfErrors += intValue.accept(this);
        return numOfErrors;
    }

    @Override
    public Integer visit(SetNew setNew) {
        int numOfErrors = printErrors(setNew);
        for (Expression expression : setNew.getArgs())
            numOfErrors += expression.accept(this);
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
    public Integer visit(SetMerge setMerge) {
        int numOfErrors = printErrors(setMerge);
        numOfErrors += setMerge.getSetArg().accept(this);
        for (Expression expression : setMerge.getElementArgs())
            numOfErrors += expression.accept(this);
        return numOfErrors;
    }

    @Override
    public Integer visit(SetAdd setAdd) {
        int numOfErrors = printErrors(setAdd);
        numOfErrors += setAdd.getSetArg().accept(this);
        numOfErrors += setAdd.getElementArg().accept(this);
        return numOfErrors;
    }

    @Override
    public Integer visit(RangeExpression rangeExpression) {
        int numOfErrors = printErrors(rangeExpression);
        numOfErrors += rangeExpression.getLeftExpression().accept(this);
        numOfErrors += rangeExpression.getRightExpression().accept(this);
        return numOfErrors;
    }
}
