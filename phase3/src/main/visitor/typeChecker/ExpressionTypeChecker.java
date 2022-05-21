package main.visitor.typeChecker;

import main.ast.nodes.expression.*;
import main.ast.nodes.expression.values.NullValue;
import main.ast.nodes.expression.values.SetValue;
import main.ast.nodes.expression.values.primitive.BoolValue;
import main.ast.nodes.expression.values.primitive.IntValue;
import main.ast.types.Type;
import main.symbolTable.utils.graph.Graph;
import main.visitor.Visitor;

public class ExpressionTypeChecker extends Visitor<Type> {
    private Graph<String> classHierarchy;

    public ExpressionTypeChecker(Graph<String> classHierarchy) {
        this.classHierarchy = classHierarchy;
    }

    @Override
    public Type visit(BinaryExpression binaryExpression) {
        //Todo
        return null;
    }

    @Override
    public Type visit(NewClassInstance newClassInstance) {
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
