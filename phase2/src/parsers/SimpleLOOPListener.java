// Generated from E:/UT/S6/TA/Compiler/SimpleLOOP-Compiler/src/main/grammar\SimpleLOOP.g4 by ANTLR 4.9.2
package parsers;

     import main.ast.nodes.*;
     import main.ast.nodes.declaration.*;
     import main.ast.nodes.declaration.classDec.*;
     import main.ast.nodes.declaration.variableDec.*;
     import main.ast.nodes.expression.*;
     import main.ast.nodes.expression.operators.*;
     import main.ast.nodes.expression.values.*;
     import main.ast.nodes.expression.values.primitive.*;
     import main.ast.nodes.statement.*;
     import main.ast.nodes.statement.set.*;
     import main.ast.types.*;
     import main.ast.types.primitives.*;
     import main.ast.types.set.*;
     import main.ast.types.functionPointer.*;
     import main.ast.types.array.*;
     import java.util.*;
 
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link SimpleLOOPParser}.
 */
public interface SimpleLOOPListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link SimpleLOOPParser#simpleLOOP}.
	 * @param ctx the parse tree
	 */
	void enterSimpleLOOP(SimpleLOOPParser.SimpleLOOPContext ctx);
	/**
	 * Exit a parse tree produced by {@link SimpleLOOPParser#simpleLOOP}.
	 * @param ctx the parse tree
	 */
	void exitSimpleLOOP(SimpleLOOPParser.SimpleLOOPContext ctx);
	/**
	 * Enter a parse tree produced by {@link SimpleLOOPParser#program}.
	 * @param ctx the parse tree
	 */
	void enterProgram(SimpleLOOPParser.ProgramContext ctx);
	/**
	 * Exit a parse tree produced by {@link SimpleLOOPParser#program}.
	 * @param ctx the parse tree
	 */
	void exitProgram(SimpleLOOPParser.ProgramContext ctx);
	/**
	 * Enter a parse tree produced by {@link SimpleLOOPParser#constructor}.
	 * @param ctx the parse tree
	 */
	void enterConstructor(SimpleLOOPParser.ConstructorContext ctx);
	/**
	 * Exit a parse tree produced by {@link SimpleLOOPParser#constructor}.
	 * @param ctx the parse tree
	 */
	void exitConstructor(SimpleLOOPParser.ConstructorContext ctx);
	/**
	 * Enter a parse tree produced by {@link SimpleLOOPParser#classDeclaration}.
	 * @param ctx the parse tree
	 */
	void enterClassDeclaration(SimpleLOOPParser.ClassDeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link SimpleLOOPParser#classDeclaration}.
	 * @param ctx the parse tree
	 */
	void exitClassDeclaration(SimpleLOOPParser.ClassDeclarationContext ctx);
	/**
	 * Enter a parse tree produced by {@link SimpleLOOPParser#field_decleration}.
	 * @param ctx the parse tree
	 */
	void enterField_decleration(SimpleLOOPParser.Field_declerationContext ctx);
	/**
	 * Exit a parse tree produced by {@link SimpleLOOPParser#field_decleration}.
	 * @param ctx the parse tree
	 */
	void exitField_decleration(SimpleLOOPParser.Field_declerationContext ctx);
	/**
	 * Enter a parse tree produced by {@link SimpleLOOPParser#method}.
	 * @param ctx the parse tree
	 */
	void enterMethod(SimpleLOOPParser.MethodContext ctx);
	/**
	 * Exit a parse tree produced by {@link SimpleLOOPParser#method}.
	 * @param ctx the parse tree
	 */
	void exitMethod(SimpleLOOPParser.MethodContext ctx);
	/**
	 * Enter a parse tree produced by {@link SimpleLOOPParser#methodBody}.
	 * @param ctx the parse tree
	 */
	void enterMethodBody(SimpleLOOPParser.MethodBodyContext ctx);
	/**
	 * Exit a parse tree produced by {@link SimpleLOOPParser#methodBody}.
	 * @param ctx the parse tree
	 */
	void exitMethodBody(SimpleLOOPParser.MethodBodyContext ctx);
	/**
	 * Enter a parse tree produced by {@link SimpleLOOPParser#methodArgsDec}.
	 * @param ctx the parse tree
	 */
	void enterMethodArgsDec(SimpleLOOPParser.MethodArgsDecContext ctx);
	/**
	 * Exit a parse tree produced by {@link SimpleLOOPParser#methodArgsDec}.
	 * @param ctx the parse tree
	 */
	void exitMethodArgsDec(SimpleLOOPParser.MethodArgsDecContext ctx);
	/**
	 * Enter a parse tree produced by {@link SimpleLOOPParser#argDec}.
	 * @param ctx the parse tree
	 */
	void enterArgDec(SimpleLOOPParser.ArgDecContext ctx);
	/**
	 * Exit a parse tree produced by {@link SimpleLOOPParser#argDec}.
	 * @param ctx the parse tree
	 */
	void exitArgDec(SimpleLOOPParser.ArgDecContext ctx);
	/**
	 * Enter a parse tree produced by {@link SimpleLOOPParser#methodArgs}.
	 * @param ctx the parse tree
	 */
	void enterMethodArgs(SimpleLOOPParser.MethodArgsContext ctx);
	/**
	 * Exit a parse tree produced by {@link SimpleLOOPParser#methodArgs}.
	 * @param ctx the parse tree
	 */
	void exitMethodArgs(SimpleLOOPParser.MethodArgsContext ctx);
	/**
	 * Enter a parse tree produced by {@link SimpleLOOPParser#body}.
	 * @param ctx the parse tree
	 */
	void enterBody(SimpleLOOPParser.BodyContext ctx);
	/**
	 * Exit a parse tree produced by {@link SimpleLOOPParser#body}.
	 * @param ctx the parse tree
	 */
	void exitBody(SimpleLOOPParser.BodyContext ctx);
	/**
	 * Enter a parse tree produced by {@link SimpleLOOPParser#blockStatement}.
	 * @param ctx the parse tree
	 */
	void enterBlockStatement(SimpleLOOPParser.BlockStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link SimpleLOOPParser#blockStatement}.
	 * @param ctx the parse tree
	 */
	void exitBlockStatement(SimpleLOOPParser.BlockStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link SimpleLOOPParser#singleStatement}.
	 * @param ctx the parse tree
	 */
	void enterSingleStatement(SimpleLOOPParser.SingleStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link SimpleLOOPParser#singleStatement}.
	 * @param ctx the parse tree
	 */
	void exitSingleStatement(SimpleLOOPParser.SingleStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link SimpleLOOPParser#addStatement}.
	 * @param ctx the parse tree
	 */
	void enterAddStatement(SimpleLOOPParser.AddStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link SimpleLOOPParser#addStatement}.
	 * @param ctx the parse tree
	 */
	void exitAddStatement(SimpleLOOPParser.AddStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link SimpleLOOPParser#mergeStatement}.
	 * @param ctx the parse tree
	 */
	void enterMergeStatement(SimpleLOOPParser.MergeStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link SimpleLOOPParser#mergeStatement}.
	 * @param ctx the parse tree
	 */
	void exitMergeStatement(SimpleLOOPParser.MergeStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link SimpleLOOPParser#deleteStatement}.
	 * @param ctx the parse tree
	 */
	void enterDeleteStatement(SimpleLOOPParser.DeleteStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link SimpleLOOPParser#deleteStatement}.
	 * @param ctx the parse tree
	 */
	void exitDeleteStatement(SimpleLOOPParser.DeleteStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link SimpleLOOPParser#varDecStatement}.
	 * @param ctx the parse tree
	 */
	void enterVarDecStatement(SimpleLOOPParser.VarDecStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link SimpleLOOPParser#varDecStatement}.
	 * @param ctx the parse tree
	 */
	void exitVarDecStatement(SimpleLOOPParser.VarDecStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link SimpleLOOPParser#ifStatement}.
	 * @param ctx the parse tree
	 */
	void enterIfStatement(SimpleLOOPParser.IfStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link SimpleLOOPParser#ifStatement}.
	 * @param ctx the parse tree
	 */
	void exitIfStatement(SimpleLOOPParser.IfStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link SimpleLOOPParser#elsifStatement}.
	 * @param ctx the parse tree
	 */
	void enterElsifStatement(SimpleLOOPParser.ElsifStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link SimpleLOOPParser#elsifStatement}.
	 * @param ctx the parse tree
	 */
	void exitElsifStatement(SimpleLOOPParser.ElsifStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link SimpleLOOPParser#condition}.
	 * @param ctx the parse tree
	 */
	void enterCondition(SimpleLOOPParser.ConditionContext ctx);
	/**
	 * Exit a parse tree produced by {@link SimpleLOOPParser#condition}.
	 * @param ctx the parse tree
	 */
	void exitCondition(SimpleLOOPParser.ConditionContext ctx);
	/**
	 * Enter a parse tree produced by {@link SimpleLOOPParser#elseStatement}.
	 * @param ctx the parse tree
	 */
	void enterElseStatement(SimpleLOOPParser.ElseStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link SimpleLOOPParser#elseStatement}.
	 * @param ctx the parse tree
	 */
	void exitElseStatement(SimpleLOOPParser.ElseStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link SimpleLOOPParser#printStatement}.
	 * @param ctx the parse tree
	 */
	void enterPrintStatement(SimpleLOOPParser.PrintStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link SimpleLOOPParser#printStatement}.
	 * @param ctx the parse tree
	 */
	void exitPrintStatement(SimpleLOOPParser.PrintStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link SimpleLOOPParser#methodCallStmt}.
	 * @param ctx the parse tree
	 */
	void enterMethodCallStmt(SimpleLOOPParser.MethodCallStmtContext ctx);
	/**
	 * Exit a parse tree produced by {@link SimpleLOOPParser#methodCallStmt}.
	 * @param ctx the parse tree
	 */
	void exitMethodCallStmt(SimpleLOOPParser.MethodCallStmtContext ctx);
	/**
	 * Enter a parse tree produced by {@link SimpleLOOPParser#returnStatement}.
	 * @param ctx the parse tree
	 */
	void enterReturnStatement(SimpleLOOPParser.ReturnStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link SimpleLOOPParser#returnStatement}.
	 * @param ctx the parse tree
	 */
	void exitReturnStatement(SimpleLOOPParser.ReturnStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link SimpleLOOPParser#assignmentStatement}.
	 * @param ctx the parse tree
	 */
	void enterAssignmentStatement(SimpleLOOPParser.AssignmentStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link SimpleLOOPParser#assignmentStatement}.
	 * @param ctx the parse tree
	 */
	void exitAssignmentStatement(SimpleLOOPParser.AssignmentStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link SimpleLOOPParser#loopStatement}.
	 * @param ctx the parse tree
	 */
	void enterLoopStatement(SimpleLOOPParser.LoopStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link SimpleLOOPParser#loopStatement}.
	 * @param ctx the parse tree
	 */
	void exitLoopStatement(SimpleLOOPParser.LoopStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link SimpleLOOPParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterExpression(SimpleLOOPParser.ExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link SimpleLOOPParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitExpression(SimpleLOOPParser.ExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link SimpleLOOPParser#ternaryExpression}.
	 * @param ctx the parse tree
	 */
	void enterTernaryExpression(SimpleLOOPParser.TernaryExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link SimpleLOOPParser#ternaryExpression}.
	 * @param ctx the parse tree
	 */
	void exitTernaryExpression(SimpleLOOPParser.TernaryExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link SimpleLOOPParser#orExpression}.
	 * @param ctx the parse tree
	 */
	void enterOrExpression(SimpleLOOPParser.OrExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link SimpleLOOPParser#orExpression}.
	 * @param ctx the parse tree
	 */
	void exitOrExpression(SimpleLOOPParser.OrExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link SimpleLOOPParser#andExpression}.
	 * @param ctx the parse tree
	 */
	void enterAndExpression(SimpleLOOPParser.AndExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link SimpleLOOPParser#andExpression}.
	 * @param ctx the parse tree
	 */
	void exitAndExpression(SimpleLOOPParser.AndExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link SimpleLOOPParser#equalityExpression}.
	 * @param ctx the parse tree
	 */
	void enterEqualityExpression(SimpleLOOPParser.EqualityExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link SimpleLOOPParser#equalityExpression}.
	 * @param ctx the parse tree
	 */
	void exitEqualityExpression(SimpleLOOPParser.EqualityExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link SimpleLOOPParser#relationalExpression}.
	 * @param ctx the parse tree
	 */
	void enterRelationalExpression(SimpleLOOPParser.RelationalExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link SimpleLOOPParser#relationalExpression}.
	 * @param ctx the parse tree
	 */
	void exitRelationalExpression(SimpleLOOPParser.RelationalExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link SimpleLOOPParser#additiveExpression}.
	 * @param ctx the parse tree
	 */
	void enterAdditiveExpression(SimpleLOOPParser.AdditiveExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link SimpleLOOPParser#additiveExpression}.
	 * @param ctx the parse tree
	 */
	void exitAdditiveExpression(SimpleLOOPParser.AdditiveExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link SimpleLOOPParser#multiplicativeExpression}.
	 * @param ctx the parse tree
	 */
	void enterMultiplicativeExpression(SimpleLOOPParser.MultiplicativeExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link SimpleLOOPParser#multiplicativeExpression}.
	 * @param ctx the parse tree
	 */
	void exitMultiplicativeExpression(SimpleLOOPParser.MultiplicativeExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link SimpleLOOPParser#preUnaryExpression}.
	 * @param ctx the parse tree
	 */
	void enterPreUnaryExpression(SimpleLOOPParser.PreUnaryExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link SimpleLOOPParser#preUnaryExpression}.
	 * @param ctx the parse tree
	 */
	void exitPreUnaryExpression(SimpleLOOPParser.PreUnaryExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link SimpleLOOPParser#postUnaryExpression}.
	 * @param ctx the parse tree
	 */
	void enterPostUnaryExpression(SimpleLOOPParser.PostUnaryExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link SimpleLOOPParser#postUnaryExpression}.
	 * @param ctx the parse tree
	 */
	void exitPostUnaryExpression(SimpleLOOPParser.PostUnaryExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link SimpleLOOPParser#accessExpression}.
	 * @param ctx the parse tree
	 */
	void enterAccessExpression(SimpleLOOPParser.AccessExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link SimpleLOOPParser#accessExpression}.
	 * @param ctx the parse tree
	 */
	void exitAccessExpression(SimpleLOOPParser.AccessExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link SimpleLOOPParser#otherExpression}.
	 * @param ctx the parse tree
	 */
	void enterOtherExpression(SimpleLOOPParser.OtherExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link SimpleLOOPParser#otherExpression}.
	 * @param ctx the parse tree
	 */
	void exitOtherExpression(SimpleLOOPParser.OtherExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link SimpleLOOPParser#accessByIndex}.
	 * @param ctx the parse tree
	 */
	void enterAccessByIndex(SimpleLOOPParser.AccessByIndexContext ctx);
	/**
	 * Exit a parse tree produced by {@link SimpleLOOPParser#accessByIndex}.
	 * @param ctx the parse tree
	 */
	void exitAccessByIndex(SimpleLOOPParser.AccessByIndexContext ctx);
	/**
	 * Enter a parse tree produced by {@link SimpleLOOPParser#setNew}.
	 * @param ctx the parse tree
	 */
	void enterSetNew(SimpleLOOPParser.SetNewContext ctx);
	/**
	 * Exit a parse tree produced by {@link SimpleLOOPParser#setNew}.
	 * @param ctx the parse tree
	 */
	void exitSetNew(SimpleLOOPParser.SetNewContext ctx);
	/**
	 * Enter a parse tree produced by {@link SimpleLOOPParser#setInclude}.
	 * @param ctx the parse tree
	 */
	void enterSetInclude(SimpleLOOPParser.SetIncludeContext ctx);
	/**
	 * Exit a parse tree produced by {@link SimpleLOOPParser#setInclude}.
	 * @param ctx the parse tree
	 */
	void exitSetInclude(SimpleLOOPParser.SetIncludeContext ctx);
	/**
	 * Enter a parse tree produced by {@link SimpleLOOPParser#value}.
	 * @param ctx the parse tree
	 */
	void enterValue(SimpleLOOPParser.ValueContext ctx);
	/**
	 * Exit a parse tree produced by {@link SimpleLOOPParser#value}.
	 * @param ctx the parse tree
	 */
	void exitValue(SimpleLOOPParser.ValueContext ctx);
	/**
	 * Enter a parse tree produced by {@link SimpleLOOPParser#boolValue}.
	 * @param ctx the parse tree
	 */
	void enterBoolValue(SimpleLOOPParser.BoolValueContext ctx);
	/**
	 * Exit a parse tree produced by {@link SimpleLOOPParser#boolValue}.
	 * @param ctx the parse tree
	 */
	void exitBoolValue(SimpleLOOPParser.BoolValueContext ctx);
	/**
	 * Enter a parse tree produced by {@link SimpleLOOPParser#class_identifier}.
	 * @param ctx the parse tree
	 */
	void enterClass_identifier(SimpleLOOPParser.Class_identifierContext ctx);
	/**
	 * Exit a parse tree produced by {@link SimpleLOOPParser#class_identifier}.
	 * @param ctx the parse tree
	 */
	void exitClass_identifier(SimpleLOOPParser.Class_identifierContext ctx);
	/**
	 * Enter a parse tree produced by {@link SimpleLOOPParser#identifier}.
	 * @param ctx the parse tree
	 */
	void enterIdentifier(SimpleLOOPParser.IdentifierContext ctx);
	/**
	 * Exit a parse tree produced by {@link SimpleLOOPParser#identifier}.
	 * @param ctx the parse tree
	 */
	void exitIdentifier(SimpleLOOPParser.IdentifierContext ctx);
	/**
	 * Enter a parse tree produced by {@link SimpleLOOPParser#type}.
	 * @param ctx the parse tree
	 */
	void enterType(SimpleLOOPParser.TypeContext ctx);
	/**
	 * Exit a parse tree produced by {@link SimpleLOOPParser#type}.
	 * @param ctx the parse tree
	 */
	void exitType(SimpleLOOPParser.TypeContext ctx);
	/**
	 * Enter a parse tree produced by {@link SimpleLOOPParser#array_type}.
	 * @param ctx the parse tree
	 */
	void enterArray_type(SimpleLOOPParser.Array_typeContext ctx);
	/**
	 * Exit a parse tree produced by {@link SimpleLOOPParser#array_type}.
	 * @param ctx the parse tree
	 */
	void exitArray_type(SimpleLOOPParser.Array_typeContext ctx);
	/**
	 * Enter a parse tree produced by {@link SimpleLOOPParser#fptr_type}.
	 * @param ctx the parse tree
	 */
	void enterFptr_type(SimpleLOOPParser.Fptr_typeContext ctx);
	/**
	 * Exit a parse tree produced by {@link SimpleLOOPParser#fptr_type}.
	 * @param ctx the parse tree
	 */
	void exitFptr_type(SimpleLOOPParser.Fptr_typeContext ctx);
	/**
	 * Enter a parse tree produced by {@link SimpleLOOPParser#set_type}.
	 * @param ctx the parse tree
	 */
	void enterSet_type(SimpleLOOPParser.Set_typeContext ctx);
	/**
	 * Exit a parse tree produced by {@link SimpleLOOPParser#set_type}.
	 * @param ctx the parse tree
	 */
	void exitSet_type(SimpleLOOPParser.Set_typeContext ctx);
}