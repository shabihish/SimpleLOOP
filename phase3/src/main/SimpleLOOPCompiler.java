

package main;

import main.visitor.utils.ASTTreePrinter;
import main.visitor.utils.ErrorReporter;
import parsers.*;
import main.ast.nodes.Program;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CommonTokenStream;
import main.visitor.nameAnalyzer.*;

public class SimpleLOOPCompiler {
    public void compile(CharStream textStream) {
        SimpleLOOPLexer simpleLOOPLexer = new SimpleLOOPLexer(textStream);
        CommonTokenStream tokenStream = new CommonTokenStream(simpleLOOPLexer);
        SimpleLOOPParser simpleLOOPParser = new SimpleLOOPParser(tokenStream);

        Program program = simpleLOOPParser.simpleLOOP().simpleLOOPProgram;
        ErrorReporter errorReporter = new ErrorReporter();
        ASTTreePrinter astTreePrinter = new ASTTreePrinter();

        NameAnalyzer nameAnalyzer = new NameAnalyzer(program);
        nameAnalyzer.analyze();
        int numberOfErrors = program.accept(errorReporter);
        if(numberOfErrors == 0)
            program.accept(astTreePrinter);

    }
}