package main.symbolTable.items;


import main.ast.nodes.declaration.variableDec.VariableDeclaration;
import main.ast.types.Type;

public class GlobalVariableSymbolTableItem extends SymbolTableItem {
    public static String START_KEY = "GlobalVar_";
    protected Type type;

    public GlobalVariableSymbolTableItem(VariableDeclaration varDeclaration) {
        this.name = varDeclaration.getVarName().getName();
        this.type = varDeclaration.getType();
    }

    public String getKey() {
        return START_KEY + this.name;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

}
