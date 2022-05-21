package main.ast.nodes.expression.values;

import main.ast.nodes.expression.Expression;
import main.ast.nodes.expression.values.primitive.IntValue;
import main.visitor.IVisitor;

import java.util.ArrayList;

public class SetValue extends Value{
    private ArrayList<IntValue> elements = new ArrayList<>();

    public SetValue() {
    }

    public SetValue(ArrayList<IntValue> elements) {
        this.elements = elements;
    }

    public ArrayList<IntValue> getElements() {
        return elements;
    }

    public void setElements(ArrayList<IntValue> elements) {
        this.elements = elements;
    }

    public void addElement(IntValue expression) {
        this.elements.add(expression);
    }

    @Override
    public String toString() {
        return "SetValue";
    }

    @Override
    public <T> T accept(IVisitor<T> visitor) {
        return visitor.visit(this);
    }
}
