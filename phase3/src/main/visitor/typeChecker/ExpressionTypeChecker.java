package main.visitor.typeChecker;

import main.ast.nodes.declaration.classDec.ClassDeclaration;
import main.ast.nodes.declaration.classDec.classMembersDec.MethodDeclaration;
import main.ast.nodes.expression.*;
import main.ast.nodes.expression.values.NullValue;
import main.ast.nodes.expression.values.SetValue;
import main.ast.nodes.expression.values.primitive.BoolValue;
import main.ast.nodes.expression.values.primitive.IntValue;
import main.ast.types.Type;
import main.symbolTable.utils.graph.Graph;
import main.visitor.Visitor;

import main.ast.nodes.*;
//types
import main.ast.types.array.ArrayType;
import main.ast.types.set.SetType;
import main.ast.types.functionPointer.FptrType;
import main.ast.types.primitives.*;
import main.ast.types.NoType;
import main.ast.types.NullType;

//nodes
import main.ast.nodes.expression.operators.BinaryOperator;

//exceptions
import main.compileError.typeError.*;

import java.util.*;



public class ExpressionTypeChecker extends Visitor<Type> {
    private Graph<String> classHierarchy;


    public ExpressionTypeChecker(Graph<String> classHierarchy) {
        this.classHierarchy = classHierarchy;
    }


    public boolean typesMatch(Type firstType,Type secondType){
        if(firstType instanceof NoType)
            return true;
        if(firstType instanceof IntType  && secondType instanceof IntType)
            return true;
        if(firstType instanceof BoolType && secondType instanceof BoolType)
            return true;
        if(firstType instanceof VoidType && secondType instanceof VoidType)
            return true;
        if (firstType instanceof ArrayType && secondType instanceof ArrayType){
            return typesMatch(((ArrayType) firstType).getType(), ((ArrayType) secondType).getType());
        }

        if (firstType instanceof SetType && secondType instanceof SetType)
            return true;

        if(firstType instanceof FptrType && secondType instanceof FptrType) {
            Type t1 = ((FptrType) firstType).getReturnType();
            Type t2 = ((FptrType) secondType).getReturnType();
            if (!typesMatch(t1, t2))
                return false;
            ArrayList<Type> arg1 = ((FptrType) firstType).getArgumentsTypes();
            ArrayList<Type> arg2 = ((FptrType) secondType).getArgumentsTypes();

            if (arg1.size() != arg2.size())
                return false;
            else {
                for (int i = 0; i < arg1.size(); i++) {
                    if (!typesMatch(arg1.get(i), arg2.get(i)))
                        return false;
                }
            }
            return true;
        }

        else if(firstType instanceof NullType)
            return secondType instanceof NullType || secondType instanceof FptrType || secondType instanceof ClassType;

        if(firstType instanceof ClassType) {
            if(!(secondType instanceof ClassType))
                return false;
            return this.classHierarchy.isSecondNodeAncestorOf(((ClassType) firstType).getClassName().getName(), ((ClassType) secondType).getClassName().getName());
        }
        return false;
    }

    public boolean isLvalue(Expression expression){
        if(!(expression instanceof Identifier || expression instanceof ObjectMemberAccess ||
                expression instanceof ArrayAccessByIndex)) {
            return false;
        }
        return true;
    }

    @Override
    // assign,
    // eq, gt, lt, add, sub
    // mult, div,
    // and, or
    public Type visit(BinaryExpression binaryExpression) {
        //Todo

        BinaryOperator opt = binaryExpression.getBinaryOperator();
        Expression firstExpr = binaryExpression.getFirstOperand();
        Expression secondExpr = binaryExpression.getSecondOperand();
        Type firstExprType = firstExpr.accept(this);
        Type secondExprType = secondExpr.accept(this);
        if(firstExprType instanceof NoType && secondExprType instanceof NoType)
            return new NoType();

        else if(opt.equals(BinaryOperator.eq)) {
            if(firstExprType instanceof NoType && (secondExprType instanceof SetType || secondExprType instanceof ArrayType))
            {
                UnsupportedOperandType exception = new UnsupportedOperandType(binaryExpression.getLine(), opt.name());
                binaryExpression.addError(exception);
                return new NoType();
            }
            else if(secondExprType instanceof NoType && (firstExprType instanceof SetType || firstExprType instanceof ArrayType))
            {
                UnsupportedOperandType exception = new UnsupportedOperandType(binaryExpression.getLine(), opt.name());
                binaryExpression.addError(exception);
                return new NoType();
            }
            else if(firstExprType instanceof NoType || secondExprType instanceof NoType)
                return new NoType();

            else if(firstExprType instanceof ArrayType || secondExprType instanceof ArrayType
                    || firstExprType instanceof SetType || secondExprType instanceof SetType) {
                UnsupportedOperandType exception = new UnsupportedOperandType(firstExpr.getLine(), opt.name());
                binaryExpression.addError(exception);
                return new NoType();
            }
            else if((firstExprType instanceof ClassType || secondExprType instanceof NullType) ||
                    (secondExprType instanceof ClassType || firstExprType instanceof NullType)) {
                return new BoolType();
            }
            else if((firstExprType instanceof FptrType || secondExprType instanceof NullType) ||
                    (secondExprType instanceof FptrType || firstExprType instanceof NullType)) {
                return new BoolType();
            }
            else if(!typesMatch(firstExprType,secondExprType)) {
                UnsupportedOperandType exception = new UnsupportedOperandType(secondExpr.getLine(), opt.name());
                binaryExpression.addError(exception);
                return new NoType();
            }
            if(firstExprType instanceof NullType || secondExprType instanceof NullType)
                return new BoolType();
        }
        else if((opt == BinaryOperator.gt) || (opt == BinaryOperator.lt)) {
            if((firstExprType instanceof NoType && !(secondExprType instanceof IntType)) ||
                    (secondExprType instanceof NoType && !(firstExprType instanceof IntType))) {
                UnsupportedOperandType exception = new UnsupportedOperandType(binaryExpression.getLine(), opt.name());
                binaryExpression.addError(exception);
                return new NoType();
            }
            else if(firstExprType instanceof NoType || secondExprType instanceof NoType)
                return new NoType();

            else if((firstExprType instanceof IntType) && (secondExprType instanceof IntType))
                return new BoolType();
            UnsupportedOperandType exception = new UnsupportedOperandType(binaryExpression.getLine(), opt.name());
            binaryExpression.addError(exception);
            return new NoType();
        }

        else if((opt == BinaryOperator.add) || (opt == BinaryOperator.sub) ||
                (opt == BinaryOperator.mult) || (opt == BinaryOperator.div)) {

            if((firstExprType instanceof NoType && !(secondExprType instanceof IntType)) ||
                    (secondExprType instanceof NoType && !(firstExprType instanceof IntType))) {
                UnsupportedOperandType exception = new UnsupportedOperandType(binaryExpression.getLine(), opt.name());
                binaryExpression.addError(exception);
                return new NoType();
            }
            else if(firstExprType instanceof NoType || secondExprType instanceof NoType)
                return new NoType();
            else if((firstExprType instanceof IntType) && (secondExprType instanceof IntType))
                return new IntType();
            UnsupportedOperandType exception = new UnsupportedOperandType(binaryExpression.getLine(), opt.name());
            binaryExpression.addError(exception);
            return new NoType();
        }

        else if((opt == BinaryOperator.or) || (opt == BinaryOperator.and)) {
            if((firstExprType instanceof NoType && !(secondExprType instanceof BoolType)) ||
                    (secondExprType instanceof NoType && !(firstExprType instanceof BoolType))) {
                UnsupportedOperandType exception = new UnsupportedOperandType(binaryExpression.getLine(), opt.name());
                binaryExpression.addError(exception);
                return new NoType();
            }
            else if(firstExprType instanceof NoType || secondExprType instanceof NoType)
                return new NoType();
            else if((firstExprType instanceof BoolType) && (secondExprType instanceof BoolType))
                return new BoolType();
            UnsupportedOperandType exception = new UnsupportedOperandType(binaryExpression.getLine(), opt.name());
            binaryExpression.addError(exception);
            return new NoType();
        }

        else if(opt == BinaryOperator.assign) {
            boolean Lvalue = this.isLvalue(firstExpr);
            boolean match = this.typesMatch(secondExprType, firstExprType);
            if(!Lvalue) {
                LeftSideNotLvalue exception = new LeftSideNotLvalue(binaryExpression.getLine());
                binaryExpression.addError(exception);
            }
            if(firstExprType instanceof NoType || secondExprType instanceof NoType || (!Lvalue && match)) {
                return new NoType();
            }

            if(Lvalue && match)
                return secondExprType;

            UnsupportedOperandType exception = new UnsupportedOperandType(binaryExpression.getLine(), opt.name());
            binaryExpression.addError(exception);
            return new NoType();
        }
        return null;
    }


    @Override
    public Type visit(NewClassInstance newClassInstance) { //just type
        //todo
        return null;
    }

    @Override
    public Type visit(UnaryExpression unaryExpression) {
        //Todo
        return null;
    }

    @Override
    public Type visit(MethodCall methodCall) {
        //Todo
        return null;
    }

    @Override
    public Type visit(Identifier identifier) {
        //Todo
        return null;
    }

    @Override
    public Type visit(ArrayAccessByIndex arrayAccessByIndex) {
        //Todo
        return null;
    }

    @Override
    public Type visit(ObjectMemberAccess objectMemberAccess) {
        //Todo
        return null;
    }

    @Override
    public Type visit(SetNew setNew) {
        //Todo
        return null;
    }

    @Override
    public Type visit(SetInclude setInclude) {
        //Todo
        return null;
    }

    @Override
    public Type visit(RangeExpression rangeExpression) {
        //Todo
        return null;
    }

    @Override
    public Type visit(TernaryExpression ternaryExpression) {
        //Todo
        return null;
    }

    @Override
    public Type visit(IntValue intValue) {
        //Todo
        return null;
    }

    @Override
    public Type visit(BoolValue boolValue) {
        //Todo
        return null;
    }

    @Override
    public Type visit(SelfClass selfClass) {
        //todo
        return null;
    }

    @Override
    public Type visit(SetValue setValue) {
        //todo
        return null;
    }

    @Override
    public Type visit(NullValue nullValue) {
        //todo
        return null;
    }
}
