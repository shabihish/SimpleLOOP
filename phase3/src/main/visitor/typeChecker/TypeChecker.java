package main.visitor.typeChecker;

import main.ast.nodes.*;
import main.ast.nodes.declaration.classDec.ClassDeclaration;
import main.ast.nodes.declaration.classDec.classMembersDec.ConstructorDeclaration;
import main.ast.nodes.declaration.classDec.classMembersDec.FieldDeclaration;
import main.ast.nodes.declaration.classDec.classMembersDec.MethodDeclaration;
import main.ast.nodes.declaration.variableDec.VariableDeclaration;
import main.ast.nodes.expression.*;
import main.ast.nodes.expression.operators.BinaryOperator;
import main.ast.nodes.expression.values.NullValue;
import main.ast.nodes.expression.values.SetValue;
import main.ast.nodes.expression.values.primitive.*;
import main.ast.nodes.statement.*;
import main.ast.nodes.statement.set.*;
import main.ast.types.NoType;
import main.ast.types.NullType;
import main.ast.types.Type;
import main.ast.types.primitives.BoolType;
import main.ast.types.primitives.IntType;
import main.ast.types.primitives.VoidType;
import main.ast.types.set.SetType;
import main.compileError.typeError.*;
import main.symbolTable.utils.graph.Graph;
import main.util.ArgPair;
import main.visitor.*;

import javax.lang.model.type.ArrayType;
import java.util.ArrayList;

public class TypeChecker extends Visitor<Void> {
    private Graph<String> classHierarchy;
    ExpressionTypeChecker expressionTypeChecker;
    private ClassDeclaration currentClass;

    public TypeChecker(Graph<String> classHierarchy) {
        this.classHierarchy = classHierarchy;
        this.expressionTypeChecker = new ExpressionTypeChecker(classHierarchy);
    }

    @Override
    public Void visit(Program program) {
        boolean isMainDefined = false;
        for (ClassDeclaration classDeclaration : program.getClasses()) {
            this.expressionTypeChecker.setCurrentClass(classDeclaration);
            classDeclaration.accept(this);
            if (classDeclaration.getClassName().getName().equals("Main"))
                isMainDefined = true;
        }
        if (!isMainDefined) {
            NoMainClass noMainClass = new NoMainClass();
            program.addError(noMainClass);
        }

        for (VariableDeclaration variableDeclaration : program.getGlobalVariables()) {
            variableDeclaration.accept(this);
        }
        return null;
    }

    @Override
    public Void visit(ClassDeclaration classDeclaration) {
        if (classDeclaration.getParentClassName() != null) {
//            this.expressionTypeChecker.checkTypeValidation(new ClassType(classDeclaration.getParentClassName()), classDeclaration);
            if (classDeclaration.getClassName().getName().equals("Main")) {
                MainClassCantInherit exception = new MainClassCantInherit(classDeclaration.getLine());
                classDeclaration.addError(exception);
            }
            if (classDeclaration.getParentClassName().getName().equals("Main")) {
                CannotExtendFromMainClass exception = new CannotExtendFromMainClass(classDeclaration.getLine());
                classDeclaration.addError(exception);
            }
        }
        for (FieldDeclaration fieldDeclaration : classDeclaration.getFields()) {
            fieldDeclaration.accept(this);
        }

        if (classDeclaration.getClassName().getName().equals("Main")) {
            if (classDeclaration.getConstructor() == null) {
                NoConstructorInMainClass exception = new NoConstructorInMainClass(classDeclaration);
                classDeclaration.addError(exception);
            } else if (classDeclaration.getConstructor().getArgs().size() != 0) {
                MainConstructorCantHaveArgs exception = new MainConstructorCantHaveArgs(classDeclaration.getLine());
                classDeclaration.addError(exception);
            }
        }

        if (classDeclaration.getConstructor() != null) {
            this.expressionTypeChecker.setCurrentMethod(classDeclaration.getConstructor());
            classDeclaration.getConstructor().accept(this);
        }

        for (MethodDeclaration methodDeclaration : classDeclaration.getMethods()) {
            this.expressionTypeChecker.setCurrentMethod(methodDeclaration);
            methodDeclaration.accept(this);
            boolean doesReturn = methodDeclaration.getDoesReturn();

            if (methodDeclaration.getReturnType() instanceof NullType && doesReturn) {
                MissingReturnStatement exception = new MissingReturnStatement(methodDeclaration);
                methodDeclaration.addError(exception);
            }

            if (!(methodDeclaration.getReturnType() instanceof NullType) && !doesReturn) {
                VoidMethodHasReturn exception = new VoidMethodHasReturn(methodDeclaration);
                methodDeclaration.addError(exception);
            }
        }
        return null;
    }

    @Override
    public Void visit(ConstructorDeclaration constructorDeclaration) {
//        ((MethodDeclaration) constructorDeclaration).accept(this);
        this.visit((MethodDeclaration) constructorDeclaration);
        return null;
    }

    @Override
    public Void visit(MethodDeclaration methodDeclaration) {
//        this.expressionTypeChecker.(methodDeclaration.getReturnType(), methodDeclaration);

        for(ArgPair arg : methodDeclaration.getArgs()) {
            arg.getVariableDeclaration().accept(this);
            if(arg.getDefaultValue() == null)
                continue;
            if (expressionTypeChecker.SubtypeChecking(arg.getDefaultValue().accept(expressionTypeChecker), arg.getVariableDeclaration().getType())){
                // TODO: Check if line checking is correct
                UnsupportedOperandType exception = new UnsupportedOperandType(arg.getVariableDeclaration().getLine(), BinaryOperator.assign.name());
                methodDeclaration.addError(exception);
            }
        }
        for(VariableDeclaration varDeclaration : methodDeclaration.getLocalVars()) {
            varDeclaration.accept(this);
        }

        boolean hasReturned = false;
        for(Statement statement : methodDeclaration.getBody()) {

            statement.accept(this);
            if(hasReturned) {
                UnreachableStatements exception = new UnreachableStatements(statement);
                statement.addError(exception);
            }
            if(statement instanceof ReturnStmt){
                hasReturned = true;

                Type returnType = ((ReturnStmt)statement).getReturnedExpr().accept(expressionTypeChecker);
                if (!expressionTypeChecker.SubtypeChecking(returnType, methodDeclaration.getReturnType())){
                    ReturnValueNotMatchMethodReturnType exception = new ReturnValueNotMatchMethodReturnType((ReturnStmt) statement);
                    methodDeclaration.addError(exception);
                }
            }
        }
        return null;
    }

    @Override
    public Void visit(FieldDeclaration fieldDeclaration) {
        fieldDeclaration.getVarDeclaration().accept(this);
        return null;
    }

    @Override
    public Void visit(VariableDeclaration varDeclaration) {
        this.expressionTypeChecker.checkTypeValidation(varDeclaration.getType(), varDeclaration);
        return null;
    }

    @Override
    public Void visit(AssignmentStmt assignmentStmt) {
        Type firstType = assignmentStmt.getlValue().accept(expressionTypeChecker);
        Type secondType = assignmentStmt.getrValue().accept(expressionTypeChecker);
        boolean isFirstLvalue = expressionTypeChecker.isLvalue(assignmentStmt.getlValue());
        if(!isFirstLvalue) {
            LeftSideNotLvalue exception = new LeftSideNotLvalue(assignmentStmt.getLine());
            assignmentStmt.addError(exception);
        }
        boolean isSubtype = expressionTypeChecker.SubtypeChecking( firstType,secondType);
        if(!isSubtype && !(firstType instanceof NoType && secondType instanceof NoType)) {
            UnsupportedOperandType exception = new UnsupportedOperandType(assignmentStmt.getLine(), BinaryOperator.assign.name());
            assignmentStmt.addError(exception);
            return null;
        }
        return null;
    }

    // TODO: Add code for non reachable statements exception
    // TODO: Add code for return state tracking
    @Override
    public Void visit(BlockStmt blockStmt) {
        for (Statement statement: blockStmt.getStatements())
            statement.accept(this);
        return null;
    }

    @Override
    public Void visit(ConditionalStmt conditionalStmt) {
        Type condType = conditionalStmt.getCondition().accept(expressionTypeChecker);
        if(!(condType instanceof BoolType || condType instanceof NoType)) {
            ConditionNotBool exception = new ConditionNotBool(conditionalStmt.getLine());
            conditionalStmt.addError(exception);
        }
//        RetConBrk thenRetConBrk = conditionalStmt.getThenBody().accept(this);
//        if(conditionalStmt.getElseBody() != null) {
//            RetConBrk elseRetConBrk = conditionalStmt.getElseBody().accept(this);
//            return new RetConBrk(thenRetConBrk.doesReturn && elseRetConBrk.doesReturn,
//                    thenRetConBrk.doesBreakContinue && elseRetConBrk.doesBreakContinue);
//        }
        return null;
    }

    @Override
    public Void visit(ElsifStmt elsifStmt) {
        return null;
    }

    @Override
    public Void visit(MethodCallStmt methodCallStmt) {
//        expressionTypeChecker.setIsInMethodCallStmt(true);

        methodCallStmt.getMethodCall().accept(expressionTypeChecker);
//        expressionTypeChecker.setIsInMethodCallStmt(false);
        return null;
    }

    //TODO: Check correctness
    @Override
    public Void visit(PrintStmt print) {
        Type argType = print.getArg().accept(expressionTypeChecker);
        if (!(argType instanceof IntType || argType instanceof ArrayType || argType instanceof SetType ||
                argType instanceof BoolType || argType instanceof NoType)) {
            UnsupportedTypeForPrint exception = new UnsupportedTypeForPrint(print.getLine());
            print.addError(exception);
        }
        return null;
    }


    @Override
    public Void visit(ReturnStmt returnStmt) {
        return null;
    }

    @Override
    public Void visit(EachStmt eachStmt) {
        Type varType = eachStmt.getVariable().accept(expressionTypeChecker);
        Type listType = eachStmt.getList().accept(expressionTypeChecker);
        // TODO: Could listType also be an instance of SetType?

        //I don know why the hell instanceof is not working

        if(!(listType.toString().equals("ArrayType") || listType instanceof NoType)) {
            EachCantIterateNoneArray exception = new EachCantIterateNoneArray(eachStmt.getLine());
            eachStmt.addError(exception);
            return null;
        }


        boolean typesMatch = expressionTypeChecker.VarTypeMatchArrayType(listType,varType);
        if(!typesMatch)
        {
            eachStmt.addError(new EachVarNotMatchList(eachStmt));
        }
        /* else if(!(listType instanceof NoType)) {
            ArrayList<Type> types = new ArrayList<>();
            for(ArrayType listNameType : ((ArrayType) listType).getElementsTypes())
                types.add(listNameType.getType());
            if(!expressionTypeChecker.areAllSameType(types)) {
                ForeachListElementsNotSameType exception = new ForeachListElementsNotSameType(foreachStmt.getLine());
                foreachStmt.addError(exception);
            }
            if((types.size() > 0) && !expressionTypeChecker.isSameType(varType, types.get(0))) {
                ForeachVarNotMatchList exception = new ForeachVarNotMatchList(foreachStmt);
                foreachStmt.addError(exception);
            }
        }
        boolean lastIsInFor = this.isInFor;
        this.isInFor = true;
        foreachStmt.getBody().accept(this);
        this.isInFor = lastIsInFor;*/
        return null;
    }

    @Override
    public Void visit(SetDelete setDelete) {
//        Type argType = setDelete.getElementArg().accept(expressionTypeChecker);
//        if (!expressionTypeChecker.isFirstSubTypeOfSecond(argType, IntType) || argType instanceof NoType){
//
//        }
        return null;
    }

    @Override
    public Void visit(SetMerge setMerge) {
//        Type argType = setMerge.accept(expressionTypeChecker);
//        if (!expressionTypeChecker.isFirstSubTypeOfSecond(argType, IntType) || argType instanceof NoType){
//
//        }
        return null;
    }

    @Override
    public Void visit(SetAdd setAdd) {
        //todo
        return null;
    }

    //TODO: Is setInclude also needed?

}