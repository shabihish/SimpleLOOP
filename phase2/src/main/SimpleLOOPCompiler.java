//package main;
//
//import main.ast.nodes.Program;
//import main.visitor.codeGenerator.CodeGenerator;
//import main.visitor.nameAnalyzer.NameAnalyzer;
//import main.visitor.typeChecker.TypeChecker;
//import main.visitor.utils.ASTTreePrinter;
//import main.visitor.utils.ErrorReporter;
//import org.antlr.v4.runtime.CharStream;
//import org.antlr.v4.runtime.CommonTokenStream;
//import parsers.SimpleLOOPLexer;
//import parsers.SimpleLOOPParser;
//
//import java.io.*;
//
//public class SimpleLOOPCompiler {
//    public void compile(CharStream textStream) {
//        System.out.println("\n--------------------------Compiling--------------------------");
//        SimpleLOOPLexer SimpleLOOPLexer = new SimpleLOOPLexer(textStream);
//        CommonTokenStream tokenStream = new CommonTokenStream(SimpleLOOPLexer);
//        SimpleLOOPParser SimpleLOOPParser = new SimpleLOOPParser(tokenStream);
//        Program program = SimpleLOOPParser.SimpleLOOP().SimpleLOOPProgram;
//        ErrorReporter errorReporter = new ErrorReporter();
//        NameAnalyzer nameAnalyzer = new NameAnalyzer(program);
//        nameAnalyzer.analyze();
//        int numberOfErrors = program.accept(errorReporter);
//        if(numberOfErrors > 0) {
//            System.out.println("\n" + numberOfErrors + " errors detected");
//            System.exit(1);
//        }
////        ASTTreePrinter astTreePrinter = new ASTTreePrinter();
////        program.accept(astTreePrinter);
//        TypeChecker typeChecker = new TypeChecker(nameAnalyzer.getClassHierarchy());
//        program.accept(typeChecker);
//        numberOfErrors = program.accept(errorReporter);
//        if(numberOfErrors > 0) {
//            System.out.println("\n" + numberOfErrors + " errors detected");
//            System.exit(1);
//        }
//        CodeGenerator codeGenerator = new CodeGenerator(nameAnalyzer.getClassHierarchy());
//        program.accept(codeGenerator);
//        System.out.println("Compilation successful");
//        runJasminFiles();
//    }
//
//    private void runJasminFiles() {
//        try {
//            System.out.println("\n-------------------Generating Class Files-------------------");
//            File dir = new File("./output");
//            Process process = Runtime.getRuntime().exec("java -jar jasmin.jar *.j", null, dir);
//            printResults(process.getInputStream());
//            printResults(process.getErrorStream());
//            System.out.println("\n---------------------------Output---------------------------");
//            process = Runtime.getRuntime().exec("java Main", null, dir);
//            printResults(process.getInputStream());
//            printResults(process.getErrorStream());
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    private void printResults(InputStream stream) {
//        BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
//        String line;
//        try {
//            while ((line = reader.readLine()) != null)
//                System.out.println(line);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//}

package main;

import main.visitor.ErrorReporter;
import main.visitor.name.ASTTreePrinter;
import main.visitor.name.NameAnalyzer;
import main.visitor.name.NameCollector;
import parsers.*;
import main.ast.nodes.Program;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CommonTokenStream;

import java.util.NavigableMap;

public class SimpleLOOPCompiler {
    public void compile(CharStream textStream) {
        SimpleLOOPLexer simpleLOOPLexer = new SimpleLOOPLexer(textStream);
        CommonTokenStream tokenStream = new CommonTokenStream(simpleLOOPLexer);
        SimpleLOOPParser simpleLOOPParser = new SimpleLOOPParser(tokenStream);

        Program program = simpleLOOPParser.simpleLOOP().simpleLOOPProgram;
        NameAnalyzer nameAnalyzer = new NameAnalyzer(program);
        nameAnalyzer.analyze();

        ErrorReporter errorReporter = new ErrorReporter();
        if (errorReporter.visit(program) == 0) {
            ASTTreePrinter astTreePrinter = new ASTTreePrinter();
            astTreePrinter.visit(program);
        }
    }
}
