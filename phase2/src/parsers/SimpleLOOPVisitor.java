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
 
import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link SimpleLOOPParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface SimpleLOOPVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link SimpleLOOPParser#simpleLOOP}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSimpleLOOP(SimpleLOOPParser.SimpleLOOPContext ctx);
	/**
	 * Visit a parse tree produced by {@link SimpleLOOPParser#program}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitProgram(SimpleLOOPParser.ProgramContext ctx);
	/**
	 * Visit a parse tree produced by {@link SimpleLOOPParser#constructor}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitConstructor(SimpleLOOPParser.ConstructorContext ctx);
	/**
	 * Visit a parse tree produced by {@link SimpleLOOPParser#classDeclaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitClassDeclaration(SimpleLOOPParser.ClassDeclarationContext ctx);
	/**
	 * Visit a parse tree produced by {@link SimpleLOOPParser#field_decleration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitField_decleration(SimpleLOOPParser.Field_declerationContext ctx);
	/**
	 * Visit a parse tree produced by {@link SimpleLOOPParser#method}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMethod(SimpleLOOPParser.MethodContext ctx);
	/**
	 * Visit a parse tree produced by {@link SimpleLOOPParser#methodBody}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMethodBody(SimpleLOOPParser.MethodBodyContext ctx);
	/**
	 * Visit a parse tree produced by {@link SimpleLOOPParser#methodArgsDec}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMethodArgsDec(SimpleLOOPParser.MethodArgsDecContext ctx);
	/**
	 * Visit a parse tree produced by {@link SimpleLOOPParser#argDec}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArgDec(SimpleLOOPParser.ArgDecContext ctx);
	/**
	 * Visit a parse tree produced by {@link SimpleLOOPParser#methodArgs}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMethodArgs(SimpleLOOPParser.MethodArgsContext ctx);
	/**
	 * Visit a parse tree produced by {@link SimpleLOOPParser#body}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBody(SimpleLOOPParser.BodyContext ctx);
	/**
	 * Visit a parse tree produced by {@link SimpleLOOPParser#blockStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBlockStatement(SimpleLOOPParser.BlockStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link SimpleLOOPParser#singleStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSingleStatement(SimpleLOOPParser.SingleStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link SimpleLOOPParser#addStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAddStatement(SimpleLOOPParser.AddStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link SimpleLOOPParser#mergeStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMergeStatement(SimpleLOOPParser.MergeStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link SimpleLOOPParser#deleteStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDeleteStatement(SimpleLOOPParser.DeleteStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link SimpleLOOPParser#varDecStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVarDecStatement(SimpleLOOPParser.VarDecStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link SimpleLOOPParser#ifStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIfStatement(SimpleLOOPParser.IfStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link SimpleLOOPParser#elsifStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitElsifStatement(SimpleLOOPParser.ElsifStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link SimpleLOOPParser#condition}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCondition(SimpleLOOPParser.ConditionContext ctx);
	/**
	 * Visit a parse tree produced by {@link SimpleLOOPParser#elseStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitElseStatement(SimpleLOOPParser.ElseStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link SimpleLOOPParser#printStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPrintStatement(SimpleLOOPParser.PrintStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link SimpleLOOPParser#methodCallStmt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMethodCallStmt(SimpleLOOPParser.MethodCallStmtContext ctx);
	/**
	 * Visit a parse tree produced by {@link SimpleLOOPParser#returnStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitReturnStatement(SimpleLOOPParser.ReturnStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link SimpleLOOPParser#assignmentStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAssignmentStatement(SimpleLOOPParser.AssignmentStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link SimpleLOOPParser#loopStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLoopStatement(SimpleLOOPParser.LoopStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link SimpleLOOPParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpression(SimpleLOOPParser.ExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link SimpleLOOPParser#ternaryExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTernaryExpression(SimpleLOOPParser.TernaryExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link SimpleLOOPParser#orExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitOrExpression(SimpleLOOPParser.OrExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link SimpleLOOPParser#andExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAndExpression(SimpleLOOPParser.AndExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link SimpleLOOPParser#equalityExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitEqualityExpression(SimpleLOOPParser.EqualityExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link SimpleLOOPParser#relationalExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRelationalExpression(SimpleLOOPParser.RelationalExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link SimpleLOOPParser#additiveExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAdditiveExpression(SimpleLOOPParser.AdditiveExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link SimpleLOOPParser#multiplicativeExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMultiplicativeExpression(SimpleLOOPParser.MultiplicativeExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link SimpleLOOPParser#preUnaryExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPreUnaryExpression(SimpleLOOPParser.PreUnaryExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link SimpleLOOPParser#postUnaryExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPostUnaryExpression(SimpleLOOPParser.PostUnaryExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link SimpleLOOPParser#accessExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAccessExpression(SimpleLOOPParser.AccessExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link SimpleLOOPParser#otherExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitOtherExpression(SimpleLOOPParser.OtherExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link SimpleLOOPParser#accessByIndex}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAccessByIndex(SimpleLOOPParser.AccessByIndexContext ctx);
	/**
	 * Visit a parse tree produced by {@link SimpleLOOPParser#setNew}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSetNew(SimpleLOOPParser.SetNewContext ctx);
	/**
	 * Visit a parse tree produced by {@link SimpleLOOPParser#setInclude}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSetInclude(SimpleLOOPParser.SetIncludeContext ctx);
	/**
	 * Visit a parse tree produced by {@link SimpleLOOPParser#value}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitValue(SimpleLOOPParser.ValueContext ctx);
	/**
	 * Visit a parse tree produced by {@link SimpleLOOPParser#boolValue}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBoolValue(SimpleLOOPParser.BoolValueContext ctx);
	/**
	 * Visit a parse tree produced by {@link SimpleLOOPParser#class_identifier}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitClass_identifier(SimpleLOOPParser.Class_identifierContext ctx);
	/**
	 * Visit a parse tree produced by {@link SimpleLOOPParser#identifier}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIdentifier(SimpleLOOPParser.IdentifierContext ctx);
	/**
	 * Visit a parse tree produced by {@link SimpleLOOPParser#type}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitType(SimpleLOOPParser.TypeContext ctx);
	/**
	 * Visit a parse tree produced by {@link SimpleLOOPParser#array_type}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArray_type(SimpleLOOPParser.Array_typeContext ctx);
	/**
	 * Visit a parse tree produced by {@link SimpleLOOPParser#fptr_type}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFptr_type(SimpleLOOPParser.Fptr_typeContext ctx);
	/**
	 * Visit a parse tree produced by {@link SimpleLOOPParser#set_type}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSet_type(SimpleLOOPParser.Set_typeContext ctx);
}