grammar SimpleLOOP;

simpleLoop

    : NEWLINE* (declaration NEWLINE*)* (classDec NEWLINE*)* mainClassDec NEWLINE* (classDec NEWLINE*)* EOF

    ;

// if inheritance should we print both statements??
// VarDec in both

ruleLCURLYBRACE
    : NEWLINE* LCURLYBRACE NEWLINE+
    ;

ruleRCURLYBRACE
    : NEWLINE+ RCURLYBRACE NEWLINE+
    ;



mainClassDec
//    : CLASS NEWLINE* MAIN NEWLINE* LCURLYBRACE NEWLINE* classBody NEWLINE* RCURLYBRACE
    :CLASS NEWLINE* id=MAIN {System.out.println("ClassDec : " + $id.getText());} NEWLINE* LCURLYBRACE NEWLINE+ classBody NEWLINE* RCURLYBRACE
    ;

classDec
//    : CLASS NEWLINE* CLASS_IDENTIFIER NEWLINE* LCURLYBRACE NEWLINE* classBody NEWLINE* RCURLYBRACE
//    | CLASS NEWLINE* CLASS_IDENTIFIER LT CLASS_IDENTIFIER LCURLYBRACE NEWLINE+ classBody RCURLYBRACE
    : CLASS NEWLINE* id=CLASS_IDENTIFIER {System.out.println("ClassDec : " + $id.getText());} NEWLINE* LCURLYBRACE NEWLINE+ classBody NEWLINE* RCURLYBRACE
    | CLASS NEWLINE* id=CLASS_IDENTIFIER {System.out.println("ClassDec : " + $id.getText());} LT pid=CLASS_IDENTIFIER {System.out.println("Inheritance : " + $id.getText() + "<" + $pid.getText());}  NEWLINE* LCURLYBRACE NEWLINE+ classBody NEWLINE* RCURLYBRACE

    ;

classBody
    : (classStatement NEWLINE+)* (methodDeclaration NEWLINE+)* (initializeMethodDeclaration NEWLINE+) ((methodDeclaration NEWLINE+))*
    ;


classScope
    : LCURLYBRACE classStatement? RCURLYBRACE classScopeprime
    ;

classScopeprime
    : (classStatement NEWLINE classScopeprime)?
    ;

classStatement
    : assignment
    | classFieldDeclaration
    | classScope
    ;

// TODO: Check if any constraints are to be enforced by method var declaration rules
methodDeclaration
    : accessModifier (VOID? | type) id=IDENTIFIER {System.out.println("MethodDec : " + $id.getText());} LPAR finalmethodParams? RPAR LCURLYBRACE NEWLINE* methodBody RCURLYBRACE
    ;

initializeMethodDeclaration
    : accessModifier INITIALIZE LPAR finalmethodParams? RPAR LCURLYBRACE NEWLINE* methodBody RCURLYBRACE
    ;

mainInitializeMethodDeclaration
    : accessModifier INITIALIZE LPAR RPAR LCURLYBRACE NEWLINE* methodBody RCURLYBRACE
    ;

methodBody
    : (declaration NEWLINE*)* scope
    ;
/*
methodBodyReturn
    : LCURLYBRACE NEWLINE* scope RETURN expression NEWLINE* RCURLYBRACE // expression or assignment
    ;
*/
finalmethodParams
    : methodParamsWithoutDefaultVal (COMMA methodParamsWithDefaultVal)?
    ;

methodParamsWithoutDefaultVal
    : methodParam COMMA methodParamsWithoutDefaultVal
    | methodParam
    ;

methodParamsWithDefaultVal
    : (methodParam ASSIGN otherExpression) COMMA methodParamsWithDefaultVal
    | methodParam ASSIGN otherExpression
    ;

methodParam
    : type id=IDENTIFIER {System.out.println("ArgumentDec : " + $id.getText());}
    ;

methodArgs
    : IDENTIFIER COMMA methodArgs
    | IDENTIFIER
    ;

newSetArgs
    : signedIntLiteral COMMA newSetArgs
    | signedIntLiteral
    ;

// TODO: Check for mandates on public/private modifiers
declaration

    : type id=IDENTIFIER {System.out.println("VarDec : " + $id.getText());} ASSIGN expression
    | type id=IDENTIFIER {System.out.println("VarDec : " + $id.getText());} (COMMA id=IDENTIFIER {System.out.println("VarDec : " + $id.getText());})*
    ;

classFieldDeclaration
    : accessModifier type id=IDENTIFIER {System.out.println("VarDec : " + $id.getText());} ASSIGN expression
    | accessModifier type id=IDENTIFIER {System.out.println("VarDec : " + $id.getText());} (COMMA id=IDENTIFIER {System.out.println("VarDec : " + $id.getText());})*
    ;


assignment

    : (IDENTIFIER | lExpression) () ASSIGN expression

    ;

scope
    : NEWLINE* (statement NEWLINE+)* NEWLINE*
    ;

statement
//    : expression
    : assignment

    | methodCallStatement
    | funcCallStatement
    | ifStatement
    | elsifStatement
    | elseStatement
    | loopStatement
    | returnStatement

    ;

selfStatement
    : SELF DOT IDENTIFIER ASSIGN expression
    ;

printFunction
    : PRINT {System.out.println("Built-in : print ");} expression
    ;
returnStatement
//TODO: function or variable return
    : RETURN {System.out.println("Return");} expression
    | RETURN {System.out.println("Return");}
    ;

methodCallStatement
    : IDENTIFIER {System.out.println("MethodCall");} LPAR methodArgs? RPAR
    ;

// TODO: print args verification's left
funcCallStatement
    : PRINT LPAR expression RPAR
    ;

loopStatement

    : (expression | range | IDENTIFIER) DOT EACH {System.out.println("Loop : each");} DO STRAIGHT_SLASH IDENTIFIER STRAIGHT_SLASH (LCURLYBRACE NEWLINE+ scope NEWLINE* RCURLYBRACE | NEWLINE+ statement NEWLINE*)

    ;

// TODO: Check whether the usage of negative int's is correct
range
    : LPAR signedIntLiteral DOT DOT signedIntLiteral RPAR
    ;

statementBlock
    : NEWLINE* LCURLYBRACE NEWLINE* scope RCURLYBRACE
    | NEWLINE* statement
    ;

ifStatement
    : IF {System.out.println("Conditional : if");} expression statementBlock
    ;

ifStatementBlock
    : LCURLYBRACE NEWLINE* scope RCURLYBRACE
    | NEWLINE* insideIfStatementBlock
    ;

elseStatement
    : IF {System.out.println("Conditional : if");} expression ifStatementBlock NEWLINE+ ELSE {System.out.println("Conditional : else");} statementBlock
    ;

elsifStatement
    : IF {System.out.println("Conditional : if");} expression ifStatementBlock NEWLINE+ (NEWLINE* ELSIF {System.out.println("Conditional : elsif");} expression statementBlock)+ (NEWLINE+  ELSE {System.out.println("Conditional : else");} statementBlock)?
    ;
insideIfStatementBlock
    : expression
    | assignment
    | elsifStatement
    | elseStatement
    ;
// todo COPYED!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
//todo
expression
    : LPAR expression RPAR
    | inlineConditionalExpression /*(op = ASSIGN expression )?*/
    ;

inlineConditionalExpression
    : orExpression inlineConditionalExpressionPrime
    ;

inlineConditionalExpressionPrime
    : (op=QUESTION_MARK {System.out.println("Operator : " + $op.getText());} expression COLON expression inlineConditionalExpressionPrime)?
    ;

orExpression:
    andExpression op = OR andExpression {System.out.println("Operator : " + $op.getText());}
    | andExpression
    ;

andExpression:
    equalityExpression op = AND equalityExpression {System.out.println("Operator : " + $op.getText());}
    | equalityExpression
    ;

equalityExpression:
    relationalExpression op =  EQUALS relationalExpression  {System.out.println("Operator : " + $op.getText());}
    | relationalExpression
    ;

relationalExpression:
    additiveExpression (op= GT | op = LT) additiveExpression {System.out.println("Operator : " + $op.getText());}
    | additiveExpression
    ;

additiveExpression:
    multiplicativeExpression (op=PLUS | op=MINUS) multiplicativeExpression {System.out.println("Operator : " + $op.getText());}
    | multiplicativeExpression
    ;

multiplicativeExpression:
    preUnaryExpression (op=MULT | op=DIVIDE) preUnaryExpression {System.out.println("Operator : " + $op.getText());}
    |preUnaryExpression
    ;

preUnaryExpression
    : postUnaryExpression
    | (MINUS | EXCLAMATION_MARK) preUnaryExpression
    ;

postUnaryExpression:

     (setExpression | selfExpression | newClassExpression | accessExpression)(PLUSPLUS|MINUSMINUS)?

    ;

setExpression
        : SET (DOT NEW {System.out.println("NEW");} LPAR (newSetArgs? | LPAR newSetArgs RPAR) RPAR)
        | IDENTIFIER DOT ((ADD {System.out.println("ADD");} | INCLUDE {System.out.println("INCLUDE");} | DELETE {System.out.println("DELETE");}) LPAR signedIntLiteral RPAR| MERGE LPAR (setExpression | IDENTIFIER) RPAR )
        ;

selfExpression
        : SELF (DOT IDENTIFIER (LPAR methodArgs? RPAR | LBRACK expression RBRACK)*)*
        ;

newClassExpression
        : CLASS_IDENTIFIER DOT NEW LPAR methodArgs? RPAR
        ;

// TODO: Must also have "(LPAR methodArgs? RPAR)" in the second line
accessExpression:
//    otherExpression ((LPAR methodArgs? RPAR) | (DOT IDENTIFIER))*
//                 ((LBRACK expression RBRACK) | (DOT IDENTIFIER))*
        otherExpression (DOT IDENTIFIER | LPAR methodArgs? RPAR | LBRACK expression RBRACK)*
        ;

//TODO: Is "LPAR (methodArgs?) RPAR" RHS needed?
otherExpression
    : /*value | */literal | IDENTIFIER /* | LPAR (methodArgs?) RPAR | size | append*/
    ;


lExpression
    : lAccessExpression /*(op = ASSIGN expression )?*/
    ;

// TODO: Must also have "(LPAR methodArgs? RPAR)" in the second line
lAccessExpression/*:
    otherExpression (((LPAR methodArgs? RPAR) | (DOT IDENTIFIER))*
                    ((LBRACK expression RBRACK) | (DOT IDENTIFIER))*)?*/
    : lOtherExpression (DOT IDENTIFIER | LPAR methodArgs? RPAR | LBRACK expression RBRACK)*
    ;

//TODO: Is "LPAR (methodArgs?) RPAR" RHS needed?
lOtherExpression
    : /*value | */IDENTIFIER | SELF /* | LPAR (methodArgs?) RPAR | size | append*/
    ;
/*
printFunction
    : PRINT {System.out.println("Built-in : print ");} LPAR expression RPAR
    ;*/
/*functionSection
    : (NewLine* function)*
    ;

function
    : functionType r = IDENTIFIER{System.out.println("FunctionDec : " + $r.text);} LPAR args RPAR mainStatementBlock NewLine+
    ;

functionType
    : type
    | Void
    ;

mainFunction
    : Main{System.out.println("Main");} LPAR RPAR mainStatementBlock NewLine*
    ;

statementBlock
    : Begin NewLine+ scope End
    | NewLine* statement
    ;

mainStatementBlock
    : Begin NewLine+ scope End
    | NewLine* statement SemiCollon?
    ;

scope
    : (statement (SemiCollon statement)* SemiCollon? NewLine+)+
    ;

// Statements
statement
    : declarationStatement
    | expressionStatement
    | ifStatement
    | elseStatement
    | whileLoop
    | doLoop
    | returnStatement
    ;

returnStatement
    : Return{System.out.println("Return");} expression
    | Return{System.out.println("Return");}
    ;

doLoop
    : Do{System.out.println("Loop : do...while");} statementBlock SemiCollon? NewLine* While expression
    ;

whileLoop
    : While{System.out.println("Loop : while");} expression statementBlock
    ;

ifStatement
    : If{System.out.println("Conditional : if");} expression statementBlock
    ;

ifStatementBlock
    : Begin NewLine+ scope End
    | NewLine* insideIfStatement
    ;

elseStatement
    : If{System.out.println("Conditional : if");} expression ifStatementBlock SemiCollon? NewLine* Else{System.out.println("Conditional : else");} statementBlock
    ;

insideIfStatement
    : declarationStatement
    | expressionStatement
    | elseStatement
    | whileLoop
    | doLoop
    | returnStatement
    ;

expressionStatement
    : memberExpression Assign expression
    | {System.out.println("FunctionCall");}memberExpression LPAR params RPAR
    | specialExpression
    ;

declarationStatement
    : type declarationAssignment (Comma declarationAssignment)*
    ;

declarationAssignment
    : r = IDENTIFIER{System.out.println("VarDec : " + $r.text);}
    | r = IDENTIFIER{System.out.println("VarDec : " + $r.text);} Assign assignExpression
    ;

// Expressions
expression
    : expression Comma assignExpression
    | assignExpression
    ;

assignExpression
    : logicalOrExpression Assign assignExpression
    | logicalOrExpression
    ;

inlineConditionalExpression
    ://todo
    |logicalOrExpression
    ;

logicalOrExpression
    : logicalOrExpression Or logicalAndExpression{System.out.println("Operator : |");}
    | logicalAndExpression
    ;

logicalAndExpression
    : logicalAndExpression And equalityExpression{System.out.println("Operator : &");}
    | equalityExpression
    ;

equalityExpression
    : equalityExpression Equals relationExpression{System.out.println("Operator : ==");}
    | relationExpression
    ;

relationExpression
    : relationExpression r = (Less | Greater) additiveExpression{System.out.println("Operator : " + $r.text);}
    | additiveExpression
    ;

additiveExpression
    : additiveExpression r = (Plus | Minus) multExpression{System.out.println("Operator : " + $r.text);}
    | multExpression
    ;

multExpression
    : multExpression r = (Multiply | Division) unaryExpression{System.out.println("Operator : " + $r.text);}
    | unaryExpression
    ;

unaryExpression
    : r = (Minus | Not) unaryExpression{System.out.println("Operator : " + $r.text);}
    | memberExpression
    ;

memberExpression
    : memberExpression LPAR params RPAR
    | memberExpression Dot (specialExpression | valExpression)
    | memberExpression LBrack expression RBrack
    | specialExpression
    | valExpression
    ;

specialExpression
    : Display{System.out.println("Built-in : display");} LPAR assignExpression RPAR
    | Append{System.out.println("Append");} LPAR assignExpression Comma assignExpression RPAR
    | Size{System.out.println("Size");} LPAR assignExpression RPAR
    ;

valExpression
    : LPAR expression RPAR
    | literal
    | IDENTIFIER
    ;
    */

type
    : arrayType
    | INT
    | BOOL
    | CLASS_IDENTIFIER
    | fptrType
    | SET LT type GT
    ;

arrayType
    : (INT | BOOL | CLASS_IDENTIFIER) (LBRACK POSITIVE_INT_LITERAL RBRACK)+
    ;

fptrType
    : FPTR LT (type | VOID) ARROW (type | VOID) GT
    ;

accessModifier
    : PUBLIC | PRIVATE
    ;
/*

arg
    : type r = IDENTIFIER{System.out.println("ArgumentDec : " + $r.text);}
    ;

args
    : arg (COMMA arg)*
    |
    ;

params
    : assignExpression (COMMA assignExpression)*
    |
    ;
*/

// TODO: Add array literal types
// TODO: Is Null valid as value?
literal
    : signedIntLiteral
    | boolLiteral
    | NULL
//    | setLiteral
    ;
//
//setLiteral
//    : LPAR params RPAR
//    ;

boolLiteral
    : TRUE
    | FALSE
    ;
signedIntLiteral
    : (PLUS | MINUS)? POSITIVE_INT_LITERAL
    ;

// TODO: What about negtive values?
POSITIVE_INT_LITERAL
    : [1-9] [0-9]*
    | [0]
    ;


// KeyWords
CLASS: 'class';

INT: 'int';
BOOL: 'bool';
FPTR: 'fptr';
SET: 'Set';

TRUE: 'true';
FALSE: 'false';
VOID: 'void';

PUBLIC: 'public';
PRIVATE: 'private';

MAIN: 'Main';
SELF: 'self';

INITIALIZE: 'initialize';
NEW: 'new';
DELETE: 'delete';
INCLUDE: 'include';

EACH: 'each';
DO: 'do';

IF: 'if';
ELSE: 'else';
ELSIF: 'elsif';

RETURN: 'return';

PRINT: 'print';
ADD: 'add';
MERGE: 'merge';

APPEND: 'append';
DISPLAY: 'display';

SIZE: 'size';

LPAR: '(';
RPAR: ')';

LBRACK: '[';
RBRACK: ']';

LCURLYBRACE: '{';
RCURLYBRACE: '}';

COMMA: ',';

EQUALS: '==';

ASSIGN: '=';

PLUS: '+';

MINUS: '-';

QUESTION_MARK: '?';

EXCLAMATION_MARK: '!';

COLON: ':';

PLUSPLUS: '++';

MINUSMINUS: '--';

MULT: '*';

STRAIGHT_SLASH: '|';

DOUBLE_SLASH: '//\n' -> skip;

DIVIDE: '/';

SHARP: '#';

DOT: '.';

ARROW: '->';

LT: '<';

GT: '>';

AND: '&&';

OR: '||';

NULL: 'null';

IDENTIFIER: [a-z_] [a-zA-Z0-9_]*;
CLASS_IDENTIFIER: [A-Z] [a-zA-Z0-9_]*;

NEWLINE: [\n\r];

WS: [ \t;\n] -> skip;
SCOPE_COMMENT: '=begin\n' .*? '\n=end' -> skip;
INLINE_COMMENT: '#' .*? '\n' -> skip;