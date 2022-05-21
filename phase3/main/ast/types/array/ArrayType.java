package main.ast.types.array;

import main.ast.nodes.expression.Expression;
import main.ast.types.Type;

import java.util.ArrayList;

public class ArrayType extends Type {
    private Type elementType;
    private ArrayList<Expression> dimensions;

    public ArrayType(Type elementType, ArrayList<Expression> dimensions) {
        this.elementType = elementType;
        this.dimensions = dimensions;
    }

    public Type getType() {
        return elementType;
    }
    public ArrayList<Expression> getDimensions() {
        return dimensions;
    }
    public void setType(Type elementType) {
        this.elementType = elementType;
    }
    public void setDimensions(ArrayList<Expression> dimensions) {
        this.dimensions = dimensions;
    }

    @Override
    public String toString() {
        return "ArrayType";
    }
}

