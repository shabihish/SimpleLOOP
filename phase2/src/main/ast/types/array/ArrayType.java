package main.ast.types.array;

import main.ast.types.Type;

import java.util.ArrayList;

public class ArrayType extends Type {
    private Type elementType;
    private ArrayList<Integer> dimensions;

    public ArrayType(Type elementType, ArrayList<Integer> dimensions) {
        this.elementType = elementType;
        this.dimensions = dimensions;
    }

    public Type getType() {
        return elementType;
    }
    public ArrayList<Integer> getDimensions() {
        return dimensions;
    }
    public void setType(Type elementType) {
        this.elementType = elementType;
    }
    public void setDimensions(ArrayList<Integer> dimensions) {
        this.dimensions = dimensions;
    }

    @Override
    public String toString() {
        return "ArrayType";
    }
}

