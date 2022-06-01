package main.visitor.typeChecker;

import main.ast.nodes.declaration.classDec.ClassDeclaration;
import main.ast.nodes.expression.*;
import main.ast.nodes.declaration.classDec.classMembersDec.ConstructorDeclaration;
import main.ast.nodes.declaration.classDec.classMembersDec.FieldDeclaration;
import main.ast.nodes.expression.operators.TernaryOperator;
import main.ast.nodes.expression.values.SetValue;
import main.ast.nodes.expression.operators.UnaryOperator;
import main.ast.nodes.declaration.classDec.classMembersDec.MethodDeclaration;
import main.ast.types.Type;
import main.ast.nodes.expression.values.NullValue;
import main.ast.nodes.expression.values.primitive.BoolValue;
import main.symbolTable.utils.graph.Graph;

import main.visitor.Visitor;
import main.util.ArgPair;
import main.ast.nodes.expression.values.Value;
import main.ast.nodes.expression.values.primitive.IntValue;
import main.ast.nodes.*;
//types
import main.ast.types.array.ArrayType;
import main.ast.types.set.SetType;
import main.ast.types.primitives.*;
import main.ast.types.NullType;
import main.ast.types.functionPointer.FptrType;
import main.ast.types.NoType;

//exceptions
import main.compileError.typeError.*;
import main.symbolTable.items.ClassSymbolTableItem;
import main.symbolTable.SymbolTable;
import main.symbolTable.items.*;
import main.symbolTable.exceptions.*;

//nodes
import main.ast.nodes.expression.operators.BinaryOperator;

//import main.symbolTable.utils.*;
import main.ast.types.primitives.*;

import java.util.*;


public class ExpressionTypeChecker extends Visitor<Type> {
    private final Graph<String> classHierarchy;
    private ClassDeclaration currClass;
    private MethodDeclaration currMethod;

    private boolean isInMethodCallStmt;

    // TODO: Are these fields needed?
    private final boolean checkingMemberAccess = false;
    private final boolean checkingListIndex = false;


    private boolean isExpressionLValue = true;
    public boolean LValueVisitor = false;

    public void setCurrentClass(ClassDeclaration classDec) {
        this.currClass = classDec;
    }

    public void setCurrentMethod(MethodDeclaration methodDec) {
        this.currMethod = methodDec;
    }

    public ClassDeclaration getCurrentClass() {
        return currClass;
    }

    public MethodDeclaration getCurrentMethod() {
        return currMethod;
    }

    public void setIsInMethodCallStmt(boolean cond) {
        isInMethodCallStmt = cond;
    }

    public boolean getIsInMethodCallStmt() {
        return isInMethodCallStmt;
    }

    public ExpressionTypeChecker(Graph<String> classHierarchy) {
        this.classHierarchy = classHierarchy;
    }


    public boolean VarTypeMatchArrayType(Type t1, Type t2) {
        if (t1 instanceof NoType || t2 instanceof NoType)
            return true;
        if (SubtypeChecking(((ArrayType) t1).getType(), t2))
            return true;
        return false;
    }

    public boolean SubtypeChecking(Type t1, Type t2) {
        if (t1 instanceof NoType)
            return true;

        if (t1 instanceof NullType && (t2 instanceof NullType || t2 instanceof FptrType || t2 instanceof ClassType))
            return true;

        if (t1 instanceof FptrType && t2 instanceof FptrType) {
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

        if (t1 instanceof ClassType && t2 instanceof ClassType) {
            if (((ClassType) t1).getClassName().getName().equals(((ClassType) t2).getClassName().getName())) {
                return true;
            } else
                return this.classHierarchy.isSecondNodeAncestorOf(((ClassType) t1).getClassName().getName(), ((ClassType) t2).getClassName().getName());
        }

        if (t1 instanceof ArrayType && t2 instanceof ArrayType) {
            return SubtypeChecking(((ArrayType) t1).getType(), ((ArrayType) t2).getType());
        }
        if (t1 instanceof SetType && t2 instanceof SetType)
            return true;
        if (t1.toString().equals(t2.toString()))
            return true;
        return false;

    }


    public boolean isLvalue(Expression expression) {
        boolean prevIsCatchErrorsActive = Node.isCatchErrorsActive;
        boolean prevLValueVisitor = LValueVisitor;
        Node.isCatchErrorsActive = false;
        LValueVisitor = true;
        isExpressionLValue = true;
        expression.accept(this);
        boolean isLeftVal = LValueVisitor;
        Node.isCatchErrorsActive = prevIsCatchErrorsActive;
        LValueVisitor = prevLValueVisitor;
        return isExpressionLValue;
    }

    public boolean TypesMatch(Type firstType, Type secondType) {
        // TODO: Shouldn't this also check the inverse case?
        return SubtypeChecking(firstType, secondType);
    }

    private boolean IsEqualityExprType(Type type) {
        if (type instanceof SetType || type instanceof ArrayType)
            return false;
        return true;
    }

    public Type visit(BinaryExpression binaryExpression) {
        //Todo
        isExpressionLValue = false;
        BinaryOperator opt = binaryExpression.getBinaryOperator();
        Expression firstExpr = binaryExpression.getFirstOperand();
        Expression secondExpr = binaryExpression.getSecondOperand();
        Type firstExprType = firstExpr.accept(this);
        Type secondExprType = secondExpr.accept(this);

        if (firstExprType instanceof NoType && secondExprType instanceof NoType)
            return new NoType();

        else if (opt.equals(BinaryOperator.eq)) {
            if ((IsEqualityExprType(firstExprType) && !IsEqualityExprType(secondExprType)) || (IsEqualityExprType(secondExprType) && !IsEqualityExprType(firstExprType))) {
                UnsupportedOperandType exception = new UnsupportedOperandType(binaryExpression.getLine(), opt.name());
                binaryExpression.addError(exception);
                return new NoType();
            }
            if (firstExprType instanceof NoType || secondExprType instanceof NoType)
                return new NoType();

            if (TypesMatch(firstExprType, secondExprType))
                return new BoolType();
            if (firstExprType instanceof NullType || secondExprType instanceof NullType)
                return new BoolType();
        } else if ((opt == BinaryOperator.gt) || (opt == BinaryOperator.lt)) {
            if ((firstExprType instanceof NoType && !(secondExprType instanceof IntType)) ||
                    (secondExprType instanceof NoType && !(firstExprType instanceof IntType))) {
                UnsupportedOperandType exception = new UnsupportedOperandType(binaryExpression.getLine(), opt.name());
                binaryExpression.addError(exception);
                return new NoType();
            } else if (firstExprType instanceof NoType || secondExprType instanceof NoType)
                return new NoType();
            else if ((firstExprType instanceof IntType) && (secondExprType instanceof IntType))
                return new BoolType();
        } else if ((opt == BinaryOperator.add) || (opt == BinaryOperator.sub) ||
                (opt == BinaryOperator.mult) || (opt == BinaryOperator.div)) {

            if ((firstExprType instanceof NoType && !(secondExprType instanceof IntType)) ||
                    (secondExprType instanceof NoType && !(firstExprType instanceof IntType))) {
                UnsupportedOperandType exception = new UnsupportedOperandType(binaryExpression.getLine(), opt.name());
                binaryExpression.addError(exception);
                return new NoType();
            } else if (firstExprType instanceof NoType || secondExprType instanceof NoType)
                return new NoType();
            else if ((firstExprType instanceof IntType) && (secondExprType instanceof IntType))
                return new IntType();
        } else if ((opt == BinaryOperator.or) || (opt == BinaryOperator.and)) {
            if ((firstExprType instanceof NoType && !(secondExprType instanceof BoolType)) ||
                    (secondExprType instanceof NoType && !(firstExprType instanceof BoolType))) {
                UnsupportedOperandType exception = new UnsupportedOperandType(binaryExpression.getLine(), opt.name());
                binaryExpression.addError(exception);
                return new NoType();
            } else if (firstExprType instanceof NoType || secondExprType instanceof NoType)
                return new NoType();
            else if ((firstExprType instanceof BoolType) && (secondExprType instanceof BoolType))
                return new BoolType();
        } else if (opt == BinaryOperator.assign) {
            boolean Lvalue = this.isLvalue(firstExpr);
            boolean match = this.TypesMatch(secondExprType, firstExprType);
            if (!Lvalue) {
                LeftSideNotLvalue exception = new LeftSideNotLvalue(binaryExpression.getLine());
                binaryExpression.addError(exception);
            }
            if (firstExprType instanceof NoType || secondExprType instanceof NoType || (!Lvalue && match)) {
                return new NoType();
            }
            if (Lvalue && match)
                return secondExprType;
        }

        UnsupportedOperandType exception = new UnsupportedOperandType(binaryExpression.getLine(), opt.name());
        binaryExpression.addError(exception);
        return new NoType();
    }


    @Override
    public Type visit(NewClassInstance newClassInstance) {
        this.isExpressionLValue = false;
        ClassType classtype = newClassInstance.getClassType();
        String classname = classtype.getClassName().getName();
        boolean classDeclared = this.classHierarchy.doesGraphContainNode(classname);
        if (!classDeclared) {
            ClassNotDeclared exception = new ClassNotDeclared(newClassInstance.getLine(), classname);
            newClassInstance.addError(exception);
            for (Expression expr : newClassInstance.getArgs())
                expr.accept(this);
            return new NoType();
        }
        try {
            ClassSymbolTableItem classSymbolTableItem = (ClassSymbolTableItem) SymbolTable.root.getItem(ClassSymbolTableItem.START_KEY + classname, true);
            ConstructorDeclaration constructor = classSymbolTableItem.getClassDeclaration().getConstructor();
            ArrayList<Expression> args = newClassInstance.getArgs();
            if (constructor == null && args.size() != 0) {
                newClassInstance.addError((new ConstructorArgsNotMatchDefinition(newClassInstance)));
                return new NoType();
            } else if (constructor == null)
                return new NoType();
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
        } catch (ItemNotFoundException e) {
            newClassInstance.addError(new ClassNotDeclared(newClassInstance.getLine(), classname));
            return new NoType();
        }
        return classtype;

    }


    @Override
    public Type visit(UnaryExpression unaryExpression) {
        this.isExpressionLValue = false;
        Type operandType = unaryExpression.getOperand().accept(this);
        UnaryOperator opt = unaryExpression.getOperator();
        if (opt == UnaryOperator.not) {
            if (operandType instanceof BoolType)
                return new BoolType();
            if (operandType instanceof NoType)
                return new NoType();
        }
        if (opt == UnaryOperator.minus) {
            if (operandType instanceof IntType)
                return new IntType();
            if (operandType instanceof NoType)
                return new NoType();
        }
        if (opt == UnaryOperator.postdec || opt == UnaryOperator.postinc) {
            boolean flag = true;

            if(!this.LValueVisitor)
                flag = this.isLvalue(unaryExpression.getOperand());

            if (!flag && !this.LValueVisitor) {

                unaryExpression.addError(new IncDecOperandNotLvalue(unaryExpression.getLine(), opt.name()));
                flag = false;
            }

            if (operandType instanceof IntType) {
                if (flag)
                    return operandType;
                return new NoType();
            }
            if (operandType instanceof NoType)
                return new NoType();
            unaryExpression.addError(new UnsupportedOperandType(unaryExpression.getLine(), opt.name()));
            return new NoType();
        }
        unaryExpression.addError(new UnsupportedOperandType(unaryExpression.getLine(), opt.name()));
        return new NoType();
    }

    @Override
    public Type visit(MethodCall methodCall) {
        this.isExpressionLValue = false;
        Type instanceType = methodCall.getInstance().accept(this);
        ArrayList<Expression> args = methodCall.getArgs();

        if (instanceType instanceof FptrType) {
            boolean flag = false;
            if (((FptrType) instanceType).getReturnType() instanceof VoidType && !isInMethodCallStmt) {
                flag = true;
                methodCall.addError(new CantUseValueOfVoidMethod(methodCall.getLine()));
            }

            ArrayList<Type> argTypes = ((FptrType) instanceType).getArgumentsTypes();
            ArrayList<Type> argsWithType = new ArrayList<>();
            for (int i = 0; i < args.size(); i++) {
                argsWithType.add(args.get(i).accept(this));
            }

            if (argTypes.size() != args.size()) {
                methodCall.addError(new MethodCallNotMatchDefinition(methodCall.getLine()));
                return new NoType();
            }

            for (int i = 0; i < argTypes.size(); i++) {
                if (!SubtypeChecking(argsWithType.get(i), argTypes.get(i))) {
                    methodCall.addError(new MethodCallNotMatchDefinition(methodCall.getLine()));
                    return new NoType();
                }
            }

            if (flag)
                return new VoidType();
            return ((FptrType) instanceType).getReturnType();
        } else if (instanceType instanceof NoType) {
            for (int i = 0; i < args.size(); i++) {
                args.get(i).accept(this);
            }
            return new NoType();
        } else {
            methodCall.addError(new CallOnNoneCallable(methodCall.getLine()));
            return new NoType();
        }
    }

    public boolean checkTypeValidation(Type type, Node node) {
        if (!(type instanceof ClassType || type instanceof FptrType || type instanceof ArrayType))
            return true;
        if (type instanceof ArrayType) {
            ArrayList<Expression> dims = ((ArrayType) type).getDimensions();
            Type elementType = ((ArrayType) type).getType();
            for (Expression expr : dims) {
                // TODO: What if expr is not an instance of IntValue?
                // TODO: IntType vs IntValue?
                // TODO: What if the dimension expression is not constant?
                if (expr instanceof IntValue && ((IntValue) expr).getConstant() == 0) {
                    node.addError(new CannotHaveEmptyArray(node.getLine()));
                    return false;
                }
            }

            // TODO: Does this case ever happen?
            if (dims.size() == 0) {
                node.addError(new CannotHaveEmptyArray(node.getLine()));
                return false;
            }
            return checkTypeValidation(elementType, node);
        }

        if (type instanceof ClassType) {
            String className = ((ClassType) type).getClassName().getName();
            if (!this.classHierarchy.doesGraphContainNode(className)) {
                ClassNotDeclared exception = new ClassNotDeclared(node.getLine(), className);
                node.addError(exception);
                return false;
            }
        }
        if (type instanceof FptrType) {
            Type retType = ((FptrType) type).getReturnType();
            ArrayList<Type> argsType = ((FptrType) type).getArgumentsTypes();
            // return this.checkTypeExistence(retType, node);
            for (Type argType : argsType)
                return this.checkTypeExistence(argType);
        }
        return true;
    }

    public boolean checkTypeExistence(Type type) {
        if (!(type instanceof ClassType || type instanceof FptrType || type instanceof ArrayType))
            return true;
        if (type instanceof ArrayType) {

            ArrayList<Expression> dims = ((ArrayType) type).getDimensions();
            if (dims.size() == 0) {
                return false;
            }
            Type elemtype = ((ArrayType)type).getType();
            return this.checkTypeExistence(elemtype);

        }
        if (type instanceof ClassType) {
            String className = ((ClassType) type).getClassName().getName();
            if (!this.classHierarchy.doesGraphContainNode(className)) {
                return false;
            }
        }
        if (type instanceof FptrType) {
            Type retType = ((FptrType) type).getReturnType();
            ArrayList<Type> argsType = ((FptrType) type).getArgumentsTypes();
            for (Type argType : argsType)
                return this.checkTypeExistence(argType);
        }
        return true;
    }

    @Override
    public Type visit(Identifier identifier) {
        try {
            ClassSymbolTableItem classSymbolTableItem = (ClassSymbolTableItem) SymbolTable.root.getItem(ClassSymbolTableItem.START_KEY + this.currClass.getClassName().getName(), true);
            SymbolTable classSymbolTable = classSymbolTableItem.getClassSymbolTable();
            MethodSymbolTableItem methodSymbolTableItem = (MethodSymbolTableItem) classSymbolTable.getItem(MethodSymbolTableItem.START_KEY + this.currMethod.getMethodName().getName(), true);
            SymbolTable methodSymbolTable = methodSymbolTableItem.getMethodSymbolTable();
            LocalVariableSymbolTableItem localVariableSymbolTableItem = (LocalVariableSymbolTableItem) methodSymbolTable.getItem(LocalVariableSymbolTableItem.START_KEY + identifier.getName(), true);
            Type idType = localVariableSymbolTableItem.getType();
            boolean valid = checkTypeExistence(idType);
            if (valid)
                return idType;
            return new NoType();
        } catch (ItemNotFoundException e) {
            if (!this.LValueVisitor)
                identifier.addError(new VarNotDeclared(identifier.getLine(), identifier.getName()));
            return new NoType();
        }

    }

    @Override
    //need check
    public Type visit(ArrayAccessByIndex arrayAccessByIndex) {
        //Todo
        Type instanceType = arrayAccessByIndex.getInstance().accept(this);
        boolean tmp = this.isExpressionLValue;
        Type indexType = arrayAccessByIndex.getIndex().accept(this);


        this.isExpressionLValue = tmp;
        if (!(indexType instanceof IntType || indexType instanceof NoType))
            if (!this.LValueVisitor)
                arrayAccessByIndex.addError(new ArrayIndexNotInt(arrayAccessByIndex.getLine()));
        if (!(instanceType instanceof ArrayType || instanceType instanceof NoType)) {
            if (!this.LValueVisitor)
                arrayAccessByIndex.addError(new AccessByIndexOnNoneArray(arrayAccessByIndex.getLine()));
            return new NoType();
        }
        if (instanceType instanceof ArrayType) {
            ArrayList<Expression> dims = ((ArrayType) instanceType).getDimensions();
            if (dims.size() < 1) {
                if (!this.LValueVisitor)
                    arrayAccessByIndex.addError(new AccessByIndexOnNoneArray(arrayAccessByIndex.getLine()));
                return new NoType();
            }
            if (dims.size() == 1) {
                return ((ArrayType) instanceType).getType();
            } else {
                ArrayList<Expression> newDims = new ArrayList<>();
                for (int i = 1; i < dims.size(); i++)
                    newDims.add(dims.get(i));
                return new ArrayType(((ArrayType) instanceType).getType(), newDims);
            }
        }
        return new NoType();
    }

    @Override
    //need check
    public Type visit(ObjectMemberAccess objectMemberAccess) {
        Type instanceType = objectMemberAccess.getInstance().accept(this);
//        Type instanceType = objectMemberAccess.get().accept(this);

        if (!(instanceType instanceof ClassType || instanceType instanceof NoType)) {

            objectMemberAccess.addError(new AccessOnNonClass(objectMemberAccess.getLine()));
            return new NoType();
        }
        if (instanceType instanceof NoType) {
            return new NoType();
        }

        String className = ((ClassType) instanceType).getClassName().getName();
        SymbolTable classSymbolTable;
        String memberName = objectMemberAccess.getMemberName().getName();
        try {
            classSymbolTable = ((ClassSymbolTableItem) SymbolTable.root.getItem(ClassSymbolTableItem.START_KEY + className, true)).getClassSymbolTable();
        } catch (ItemNotFoundException classNotFound) {
            return new NoType();
        }

        try {
            FieldSymbolTableItem fieldSymbolTableItem = (FieldSymbolTableItem) classSymbolTable.getItem(FieldSymbolTableItem.START_KEY + memberName, true);
            if (this.checkTypeExistence(fieldSymbolTableItem.getType())) {
                return fieldSymbolTableItem.getType();
            }
            return new NoType();
        } catch (ItemNotFoundException memberNotField) {
            try {
                MethodSymbolTableItem methodSymbolTableItem = (MethodSymbolTableItem) classSymbolTable.getItem(MethodSymbolTableItem.START_KEY + memberName, true);
                this.isExpressionLValue = false;
                return new FptrType(methodSymbolTableItem.getArgTypes(), methodSymbolTableItem.getReturnType());
            } catch (ItemNotFoundException memberNotFound) {
                if (memberName == className) {
                    this.isExpressionLValue = false;
                    return new FptrType(new ArrayList<>(), new NullType());
                }
                MemberNotAvailableInClass exception = new MemberNotAvailableInClass(objectMemberAccess.getLine(), memberName, className);
                objectMemberAccess.addError(exception);
                return new NoType();
            }
        }
    }

    @Override
    public Type visit(SetNew setNew) {
        //Todo
        ArrayList<Expression> types = new ArrayList<>();
        for (Expression element : setNew.getArgs()) {
            Type elementType = element.accept(this);
            if (!(elementType instanceof IntType || elementType instanceof NoType)) {
                setNew.addError(new NewInputNotSet(setNew.getLine()));
                return new NoType();
            }
        }
        return new SetType();
    }

    @Override
    public Type visit(SetInclude setInclude) {
        //Todo
        Type setArg = setInclude.getSetArg().accept(this);
        if (!(setArg instanceof SetType || setArg instanceof NoType)) {
            //TODO: Check
            return new NoType();
        }
        Type IncludeExprType = setInclude.getElementArg().accept(this);
        if (!(IncludeExprType instanceof IntType || IncludeExprType instanceof NoType)) {
            setInclude.addError(new SetIncludeInputNotInt(setInclude.getLine()));
            return new NoType();
        }
        return new BoolType();
    }

    @Override
    public Type visit(RangeExpression rangeExpression) {
        //Todo
        Type LeftExprType = rangeExpression.getLeftExpression().accept(this);
        Type RightExprType = rangeExpression.getRightExpression().accept(this);

        if (!(LeftExprType instanceof IntType || LeftExprType instanceof NoType)) {
            rangeExpression.addError(new EachRangeNotInt(rangeExpression.getLine()));
            return new NoType();
        }
        // Todo: Is it needed to also check for the right side being of NoType?
        if (RightExprType instanceof IntType) {
            ArrayList<Expression> dims = new ArrayList<>();
            dims.add(new IntValue(1));
            return new ArrayType(new IntType(), dims);
        }
        if ((LeftExprType instanceof NoType || LeftExprType instanceof IntType) && (RightExprType instanceof NoType || RightExprType instanceof IntType))
            return new NoType();
        rangeExpression.addError(new EachRangeNotInt(rangeExpression.getLine()));
        return new NoType();
    }

    @Override
    public Type visit(TernaryExpression ternaryExpression) {
        Type ConditionType = ternaryExpression.getCondition().accept(this);
        if (!(ConditionType instanceof BoolType || ConditionType instanceof NoType)) {
            ternaryExpression.addError(new ConditionNotBool(ternaryExpression.getLine()));
            return new NoType();
        }
        Type TrueExprType = ternaryExpression.getTrueExpression().accept(this);
        Type FalseExprType = ternaryExpression.getFalseExpression().accept(this);
        //TODO: Should check for operand subtyping in here or should the two expressions be of the same type exactly?
        if (SubtypeChecking(TrueExprType, FalseExprType) && SubtypeChecking(FalseExprType, TrueExprType) || TrueExprType instanceof NoType || FalseExprType instanceof NoType) {
            return TrueExprType;
        }
        ternaryExpression.addError(new UnsupportedOperandType(ternaryExpression.getLine(), TernaryOperator.ternary.name()));
        return new NoType();
    }

    @Override
    public Type visit(IntValue intValue) {
        //Todo
        this.isExpressionLValue = false;
        return new IntType();
    }

    @Override
    public Type visit(BoolValue boolValue) {
        //Todo
        this.isExpressionLValue = false;
        return new BoolType();
    }

    // TODO: How is the self.sth type propagation handled?
    @Override
    public Type visit(SelfClass selfClass) {
        //todo
        return new ClassType(this.getCurrentClass().getClassName());
    }

    @Override
    public Type visit(SetValue setValue) {
        //todo
        this.isExpressionLValue = false;
        return new SetType();
    }

    @Override
    public Type visit(NullValue nullValue) {
        //todo
        this.isExpressionLValue = false;
        return new NullType();

    }
}
