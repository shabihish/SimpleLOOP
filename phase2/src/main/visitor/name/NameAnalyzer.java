package main.visitor.name;

import main.ast.nodes.*;
import main.ast.nodes.declaration.classDec.ClassDeclaration;
import main.ast.nodes.declaration.classDec.classMembersDec.ConstructorDeclaration;
import main.ast.nodes.declaration.classDec.classMembersDec.FieldDeclaration;
import main.ast.nodes.declaration.classDec.classMembersDec.MethodDeclaration;
import main.ast.nodes.declaration.variableDec.VariableDeclaration;
import main.ast.nodes.expression.*;
import main.ast.nodes.expression.values.NullValue;
import main.ast.nodes.expression.values.SetValue;
import main.ast.nodes.expression.values.primitive.*;
import main.ast.nodes.statement.*;
import main.ast.nodes.statement.set.*;
import main.compileError.CompileError;
import main.symbolTable.SymbolTable;
import main.symbolTable.exceptions.ItemNotFoundException;
import main.symbolTable.items.ClassSymbolTableItem;
import main.symbolTable.utils.graph.Graph;
import main.symbolTable.utils.graph.exceptions.GraphDoesNotContainNodeException;
import main.symbolTable.utils.graph.exceptions.NodeAlreadyExistsException;
import main.visitor.*;

import java.lang.reflect.Field;
import java.util.ArrayList;
import javax.swing.plaf.nimbus.State;

public class NameAnalyzer {
    private Program program;
    private Graph<String> classHierarchy;

    public NameAnalyzer(Program program)
    {
        this.program = program;
    }

    public void analyze(){
        NameCollector nameCollector = new NameCollector();
        this.program.accept(nameCollector);
        this.linkParentSymbolTabels();
        NameChecker nameChecker = new NameChecker(classHierarchy);
        this.program.accept(nameChecker);
    }

    private void linkParentSymbolTabels()
    {
        Graph<String> classHierarchy = new Graph<>();
        for (ClassDeclaration classDeclaration : this.program.getClasses())
        {
            String className = classDeclaration.getClassName().getName();
            try{
                classHierarchy.addNode(className);
            }
            catch(NodeAlreadyExistsException ignored)
            {

            }
            if(classDeclaration.getParentClassName() == null)
                continue;
            String parentName = classDeclaration.getParentClassName().getName();
            try{
                classHierarchy.addNodeAsParentOf(className,parentName);
                ClassSymbolTableItem parentSTI = (ClassSymbolTableItem) SymbolTable.root.getItem(ClassSymbolTableItem.START_KEY+parentName,true);
                ClassSymbolTableItem thisClassSTI = (ClassSymbolTableItem) SymbolTable.root.getItem(ClassSymbolTableItem.START_KEY + className, true);
                thisClassSTI.getClassSymbolTable().pre = parentSTI.getClassSymbolTable();
            }
            catch (ItemNotFoundException | GraphDoesNotContainNodeException ignored)
            {

            }
        }
        this.classHierarchy = classHierarchy;
    }
    public Graph<String> getClassHierarchy() {return classHierarchy;}
}

