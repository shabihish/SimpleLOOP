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
    private ClassDeclaration currClass;
    private MethodDeclaration currMethod;

    public ExpressionTypeChecker(Graph<String> classHierarchy) {
        this.classHierarchy = classHierarchy;
    }

    public boolean SubtypeChecking(Type t1, Type t2)
    {
        if(t1 instanceof NoType)
            return true;
        if(t1.toString().equals(t2.toString()))
            return true;
        if( t1 instanceof NullType && (t2 instanceof FptrType || t2 instanceof ClassType))
            return true;
        if(t1 instanceof FptrType)
        {
            if (!SubtypeChecking(((FptrType) t1).getReturnType(), ((FptrType) t2).getReturnType()))
                return false;
            ArrayList<Type> arg1 = ((FptrType) t1).getArgumentsTypes();
            ArrayList<Type> arg2 = ((FptrType) t2).getArgumentsTypes();
            if (arg1.size() != arg2.size())
                return false;
            for (int i = 0; i < arg1.size(); i++) {
                if (!SubtypeChecking(arg1.get(i), arg2.get(i)))
                    return false;
            }

            return true;
        }
        if(t1 instanceof ClassType) {
            if (((ClassType) t1).getClassName().getName().equals(((ClassType) t2).getClassName().getName())){
                return true;
            }
            else
                return this.classHierarchy.isSecondNodeAncestorOf(((ClassType) t1).getClassName().getName(), ((ClassType) t2).getClassName().getName());
        }

       if (t1 instanceof ArrayType && t2 instanceof ArrayType){
            return SubtypeChecking(((ArrayType)t1).getType(), ((ArrayType) t2).getType());
        }
        if (t1 instanceof SetType && t2 instanceof SetType)
            return true;
        return false;
        //if(t1 instance of Arraytype
        //if(t1 instance of SetType

    }
    public boolean TypesMatch(Type firstType,Type secondType){
        // are bool and int subtype??
        //what if type is not a real type??
        return SubtypeChecking(firstType, secondType);
    }

    public boolean isLvalue(Expression expression){
      
    }

    private boolean IsEqualityExprType(Type type)
    {
        if(type instanceof SetType || type instanceof ArrayType)
            return false;
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
            if((IsEqualityExprType(firstExprType) && !IsEqualityExprType(secondExprType))||(IsEqualityExprType(secondExprType) && !IsEqualityExprType(firstExprType)))
//            if(firstExprType instanceof NoType && (secondExprType instanceof SetType || secondExprType instanceof ArrayType))
//            {
//                UnsupportedOperandType exception = new UnsupportedOperandType(binaryExpression.getLine(), opt.name());
//                binaryExpression.addError(exception);
//                return new NoType();
//            }
//
//            else if(secondExprType instanceof NoType && (firstExprType instanceof SetType || firstExprType instanceof ArrayType))
            {
                UnsupportedOperandType exception = new UnsupportedOperandType(binaryExpression.getLine(), opt.name());
                binaryExpression.addError(exception);
                return new NoType();
            }
            if(secondExprType instanceof NoType || secondExprType instanceof NoType)
                return new NoType();
            if(TypesMatch(firstExprType,secondExprType))
                return new BoolType();
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
        }

        else if(opt == BinaryOperator.assign) {
            boolean Lvalue = this.isLvalue(firstExpr);
            boolean match = this.TypesMatch(secondExprType, firstExprType);
            if(!Lvalue) {
                LeftSideNotLvalue exception = new LeftSideNotLvalue(binaryExpression.getLine());
                binaryExpression.addError(exception);
            }
            if(firstExprType instanceof NoType || secondExprType instanceof NoType || (!Lvalue && match)) {
                return new NoType();
            }
            if(Lvalue && match)
                return secondExprType;
        }
        UnsupportedOperandType exception = new UnsupportedOperandType(binaryExpression.getLine(), operator.name());
        binaryExpression.addError(exception);
        return new NoType();
    }


    @Override
    public Type visit(NewClassInstance newClassInstance) { //just type
        //todo
        ClassType classtype = newClassInstance.getClassType();
        String classname = classtype.getClassName().getName();
        boolean classDeclared = this.classHierarchy.doesGraphContainNode(classname);
        if(!classDeclared)
        {
            ClassNotDeclared exception = new ClassNotDeclared(newClassInstance.getLine(), classname);
            newClassInstance.addError(exception);
            return new NoType();
        }
        try {
            ClassSymbolTableItem classSymbolTableItem = (ClassSymbolTableItem) SymbolTable.root.getItem(ClassSymbolTableItem.START_KEY + classname, true);
            ConstructorDeclaration constructor = classSymbolTableItem.getClassDeclaration().getConstructor();
            ArrayList<Expression> args = newClassInstance.getArgs();
            if (constructor == null && args.size()!=0) {
                newClassInstance.addError((new ConstructorArgsNotMatchDefinition(newClassInstance)));
                return new NoType();
            }
            else {
                ArrayList<ArgPair> constructorTypes = constructor.getArgs();
                if (args.size() != constructorTypes.size()) {
                    newClassInstance.addError(new ConstructorArgsNotMatchDefinition(newClassInstance));
                    return new NoType();
                }
                for (int i = 0; i < args.size(); i++) {
                    Type constructorArgType = constructorTypes.get(i).getVariableDeclaration().getType();
                    Type newInstancetype = args.get(i).accept(this);
                    if (!TypesMatch(newInstancetype, constructorArgType)) {
                        newClassInstance.addError(new ConstructorArgsNotMatchDefinition(newClassInstance));
                        return new NoType();
                    }
                }
            }

        }
        catch (ItemNotFoundException e){
            newClassInstance.addError(new ClassNotDeclared(newClassInstance.getLine(), classname));
            return new NoType();
        }
        return classtype;

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
