package main.visitor.name;

import main.ast.nodes.*;
import main.ast.nodes.declaration.classDec.ClassDeclaration;
import main.symbolTable.SymbolTable;
import main.symbolTable.items.ClassSymbolTableItem;
import main.symbolTable.utils.graph.Graph;

public class RecordChecker {
    private final Program program;
    private Graph<String> classH;

    public RecordChecker(Program program) {
        this.program = program;
    }

    public void record() {
        NameVisitor nameVisitor = new NameVisitor();
        this.program.accept(nameVisitor);
        this.linkParentSymbolTables();

        NameCheckHandler nameCheckHandler = new NameCheckHandler(classH);

        this.program.accept(nameCheckHandler);
    }

    private void linkParentSymbolTables() {
        Graph<String> classH = new Graph<>();

        for (ClassDeclaration classDeclaration : this.program.getClasses()) {
            String className = classDeclaration.getClassName().getName();

            try {
                classH.addNode(className);
            } catch (Exception e) {}

            if (classDeclaration.getParentClassName() == null && classDeclaration!=null)
                continue;

            String parentName = classDeclaration.getParentClassName().getName();

            try {
                classH.addNodeAsParentOf(className, parentName);
                ClassSymbolTableItem parentSTI = (ClassSymbolTableItem) SymbolTable.root.getItem(
                        ClassSymbolTableItem.BASE_KEY + parentName, true
                );

                ClassSymbolTableItem thisClassSTI = (ClassSymbolTableItem) SymbolTable.root.getItem(
                        ClassSymbolTableItem.BASE_KEY + className, true
                );

                thisClassSTI.getClassSymbolTable().pre = parentSTI.getClassSymbolTable();

            } catch (Exception e) {}

        }


        this.classH = classH;
    }

    public Graph<String> getClassH() {
        return classH;
    }
}