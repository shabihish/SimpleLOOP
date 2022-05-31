package main.visitor.typeChecker;

import main.ast.nodes.*;
import main.ast.nodes.declaration.classDec.classMembersDec.ConstructorDeclaration;
import main.ast.nodes.declaration.classDec.classMembersDec.FieldDeclaration;
import main.ast.nodes.declaration.classDec.classMembersDec.MethodDeclaration;
import main.ast.nodes.statement.set.*;
import main.ast.nodes.expression.*;
import main.ast.nodes.declaration.variableDec.VariableDeclaration;
import main.ast.nodes.expression.operators.BinaryOperator;
import main.ast.nodes.declaration.classDec.ClassDeclaration;
import main.ast.types.NoType;
import main.ast.types.Type;
import main.ast.types.NullType;
import main.ast.types.functionPointer.FptrType;
import main.ast.types.array.ArrayType;
import main.ast.nodes.statement.*;
import main.ast.types.primitives.BoolType;
import main.util.ArgPair;
import main.ast.types.primitives.IntType;
import main.ast.types.primitives.VoidType;
import main.compileError.typeError.*;
import main.ast.types.primitives.ClassType;
import main.symbolTable.utils.graph.Graph;
import main.ast.types.set.SetType;
import main.visitor.*;

import javax.swing.plaf.nimbus.State;

public class TypeChecker extends Visitor<Void> {

    ExpressionTypeChecker expressionTypeChecker;

    //    private Graph<String> classHierarchy;
    private ClassDeclaration currentClass;
    private MethodDeclaration currentMethod;
    private boolean hasReturned = false;


    // Checked
    public TypeChecker(Graph<String> classHierarchy) {
        this.expressionTypeChecker = new ExpressionTypeChecker(classHierarchy);
    }

    // Checked
    @Override
    public Void visit(Program program) {
        boolean isMainDefined = false;
        for (ClassDeclaration classDeclaration : program.getClasses()) {
            currentClass = classDeclaration;
            expressionTypeChecker.setCurrentClass(classDeclaration);
            classDeclaration.accept(this);
            // TODO: Is it needed to set the values back to null?
//            currentClass = null;
//            expressionTypeChecker.setCurrentClass(null);
            if (classDeclaration.getClassName().getName().equals("Main"))
                isMainDefined = true;
        }

        if (!isMainDefined)
            program.addError(new NoMainClass());

        // TODO: Could be reordered.
        for (VariableDeclaration variableDeclaration : program.getGlobalVariables()) {
            variableDeclaration.accept(this);
        }
        return null;
    }

    // Checked
    @Override
    public Void visit(ClassDeclaration classDeclaration) {
        if (classDeclaration.getParentClassName() != null) {
            expressionTypeChecker.checkTypeValidation(new ClassType(classDeclaration.getParentClassName()), classDeclaration);
            if (classDeclaration.getClassName().getName().equals("Main"))
                classDeclaration.addError(new MainClassCantInherit(classDeclaration.getLine()));
            if (classDeclaration.getParentClassName().getName().equals("Main"))
                classDeclaration.addError(new CannotExtendFromMainClass(classDeclaration.getLine()));
        }

        if (classDeclaration.getClassName().getName().equals("Main")) {
            if (classDeclaration.getConstructor() == null)
                classDeclaration.addError(new NoConstructorInMainClass(classDeclaration));
            else if (classDeclaration.getConstructor().getArgs().size() != 0)
                classDeclaration.addError(new MainConstructorCantHaveArgs(classDeclaration.getLine()));
        }

        // TODO: Double check the correctness.
        if (classDeclaration.getConstructor() != null) {
            expressionTypeChecker.setCurrentMethod(classDeclaration.getConstructor());
            currentMethod = classDeclaration.getConstructor();
            classDeclaration.getConstructor().accept(this);
        }

        for (MethodDeclaration methodDeclaration : classDeclaration.getMethods()) {
            expressionTypeChecker.setCurrentMethod(methodDeclaration);
            currentMethod = methodDeclaration;
            methodDeclaration.accept(this);
//            boolean doesReturn = methodDeclaration.getDoesReturn();
//
//            if (methodDeclaration.getReturnType() instanceof NullType && doesReturn) {
//                MissingReturnStatement exception = new MissingReturnStatement(methodDeclaration);
//                methodDeclaration.addError(exception);
//            }
//
//            if (!(methodDeclaration.getReturnType() instanceof NullType) && !doesReturn) {
//                VoidMethodHasReturn exception = new VoidMethodHasReturn(methodDeclaration);
//                methodDeclaration.addError(exception);
//            }
        }
        for (FieldDeclaration fieldDeclaration : classDeclaration.getFields()) {
            fieldDeclaration.accept(this);
        }
        return null;
    }

    // Checked
    @Override
    public Void visit(ConstructorDeclaration constructorDeclaration) {
        //TODO: Does this work?
//        ((MethodDeclaration) constructorDeclaration).accept(this);
        visit((MethodDeclaration) constructorDeclaration);
        return null;
    }

    // Checked
    @Override
    public Void visit(MethodDeclaration methodDeclaration) {
        expressionTypeChecker.checkTypeValidation(methodDeclaration.getReturnType(), methodDeclaration);
        for (ArgPair arg : methodDeclaration.getArgs()) {
            arg.getVariableDeclaration().accept(this);
            if (arg.getDefaultValue() == null)
                continue;
            // TODO: Is subtyping also applicable here?
            if (!expressionTypeChecker.SubtypeChecking(arg.getDefaultValue().accept(expressionTypeChecker), arg.getVariableDeclaration().getType())) {
                // TODO: Check if line checking is correct
                // TODO: Check if the error type's as expected
                methodDeclaration.addError(new UnsupportedOperandType(arg.getVariableDeclaration().getLine(), BinaryOperator.assign.name()));
            }
        }

        for (VariableDeclaration varDeclaration : methodDeclaration.getLocalVars()) {
            varDeclaration.accept(this);
        }

//        boolean hasReturned = false;
        for (Statement statement : methodDeclaration.getBody()) {
            statement.accept(this);
//            if (hasReturned) {
//                UnreachableStatements exception = new UnreachableStatements(statement);
//                statement.addError(exception);
//            }
//            if (statement instanceof ReturnStmt) {
//                hasReturned = true;
        }
        return null;
    }

    // Checked
    @Override
    public Void visit(FieldDeclaration fieldDeclaration) {
        fieldDeclaration.getVarDeclaration().accept(this);
        return null;
    }

    // Checked
    @Override
    public Void visit(VariableDeclaration varDeclaration) {
        expressionTypeChecker.checkTypeValidation(varDeclaration.getType(), varDeclaration);
        return null;
    }

    // Checked
    @Override
    public Void visit(AssignmentStmt assignmentStmt) {
        Type firstType = assignmentStmt.getlValue().accept(expressionTypeChecker);
        Type secondType = assignmentStmt.getrValue().accept(expressionTypeChecker);

        boolean isLeftLValue = expressionTypeChecker.isLvalue(assignmentStmt.getlValue());
        if (!isLeftLValue)
            assignmentStmt.addError(new LeftSideNotLvalue(assignmentStmt.getLine()));

        if (!expressionTypeChecker.SubtypeChecking(secondType, firstType) && !(firstType instanceof NoType || secondType instanceof NoType))
            assignmentStmt.addError(new UnsupportedOperandType(assignmentStmt.getLine(), BinaryOperator.assign.name()));
        return null;
    }

    // TODO: Add code for non reachable statements exception
    // TODO: Add code for return state tracking
    // Checked
    @Override
    public Void visit(BlockStmt blockStmt) {
        for (Statement statement : blockStmt.getStatements())
            statement.accept(this);
        return null;
    }

    // Checked
    @Override
    public Void visit(ConditionalStmt conditionalStmt) {
        Type condType = conditionalStmt.getCondition().accept(expressionTypeChecker);
        if (!(condType instanceof BoolType || condType instanceof NoType))
            conditionalStmt.addError(new ConditionNotBool(conditionalStmt.getLine()));
        conditionalStmt.getThenBody().accept(this);
        if (conditionalStmt.getElsif() != null) {
            for (ElsifStmt elsifStmt : conditionalStmt.getElsif())
                elsifStmt.accept(this);
        }
        if (conditionalStmt.getElseBody() != null) {
            conditionalStmt.getElseBody().accept(this);
        }
        return null;
    }

    // Checked
    @Override
    public Void visit(ElsifStmt elsifStmt) {
        Type condType = elsifStmt.getCondition().accept(expressionTypeChecker);
        if (!(condType instanceof BoolType || condType instanceof NoType))
            elsifStmt.addError(new ConditionNotBool(elsifStmt.getLine()));
        elsifStmt.getThenBody().accept(this);
        return null;
    }

    // TODO: Is setIsInMethodCallStmt really needed?
    // Checked
    @Override
    public Void visit(MethodCallStmt methodCallStmt) {
        expressionTypeChecker.setIsInMethodCallStmt(true);
        methodCallStmt.getMethodCall().accept(expressionTypeChecker);
        expressionTypeChecker.setIsInMethodCallStmt(false);
        return null;
    }

    // TODO: Check correctness
    // Checked
    @Override
    public Void visit(PrintStmt print) {
        Type argType = print.getArg().accept(expressionTypeChecker);
        if (!(argType instanceof IntType || argType instanceof ArrayType || argType instanceof SetType ||
                argType instanceof BoolType || argType instanceof NoType))
            print.addError(new UnsupportedTypeForPrint(print.getLine()));
        return null;
    }

    // Checked
    @Override
    public Void visit(ReturnStmt returnStmt) {
        if (currentMethod.getReturnType() instanceof VoidType)
            return null;
        if (!expressionTypeChecker.SubtypeChecking(returnStmt.getReturnedExpr().accept(expressionTypeChecker), currentMethod.getReturnType()))
            returnStmt.addError(new ReturnValueNotMatchMethodReturnType(returnStmt));
        return null;
    }

    // Checked
    @Override
    public Void visit(EachStmt eachStmt) {
        Type varType = eachStmt.getVariable().accept(expressionTypeChecker);
        Type listType = eachStmt.getList().accept(expressionTypeChecker);
        // TODO: Could listType also be an instance of SetType?
        //I dont know why the hell instanceof is not working
        // if (!(listType instanceof ArrayType || listType instanceof NoType))
        //eachStmt.addError(new EachCantIterateNoneArray(eachStmt.getLine()));

        if (!(listType.toString().equals("ArrayType") || listType instanceof NoType)) {
            eachStmt.addError(new EachCantIterateNoneArray(eachStmt.getLine()));
            return null;
        }
        boolean typesMatch = expressionTypeChecker.VarTypeMatchArrayType(listType, varType);
        if (!typesMatch) {
            eachStmt.addError(new EachVarNotMatchList(eachStmt));
            // TODO: Shouldn't also return in here?
        }

        eachStmt.getBody().accept(this);
        return null;
    }

//        boolean typesMatch = expressionTypeChecker.VarTypeMatchArrayType(listType, varType);
//        if (!typesMatch) {
//            eachStmt.addError(new EachVarNotMatchList(eachStmt));
//        }
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

    // Checked
    @Override
    public Void visit(SetDelete setDelete) {
        setDelete.getSetArg().accept(expressionTypeChecker);
        setDelete.getElementArg().accept(expressionTypeChecker);
        return null;
    }

    // TODO: Double check if having a NoType is also ok
    // TODO: Double check the line given
    // TODO: Are there any other possible combinations?
    // Checked
    @Override
    public Void visit(SetMerge setMerge) {
        if (setMerge.getElementArgs().size() == 1) {
            Expression arg = setMerge.getElementArgs().get(0);
            Type argType = arg.accept(expressionTypeChecker);
            /*if (arg instanceof Identifier) {
                if (!(argType instanceof SetType || argType instanceof NoType)) {
                    MergeInputNotSet exception = new MergeInputNotSet(setMerge.getLine());
                    setMerge.addError(exception);
                }
            } else */
            if (!(argType instanceof SetType || argType instanceof NoType))
                setMerge.addError(new MergeInputNotSet(setMerge.getLine()));
        } else {
            //TODO: Is comma-seperated-ints == single-int the case?
            for (Expression expression : setMerge.getElementArgs()) {
                Type type = expression.accept(expressionTypeChecker);
                if (!(type instanceof IntType || type instanceof NoType)) {
                    setMerge.addError(new MergeInputNotSet(setMerge.getLine()));
                    //TODO: Should it also return here?
                    return null;
                }
            }
        }
        setMerge.getSetArg().accept(expressionTypeChecker);
        return null;
    }

    // Checked
    @Override
    public Void visit(SetAdd setAdd) {
        Type type = setAdd.getElementArg().accept(expressionTypeChecker);
        if (!(type instanceof IntType || type instanceof NoType))
            setAdd.addError(new AddInputNotInt(setAdd.getLine()));
        return null;
    }
    //TODO: Is setInclude also needed?
}
