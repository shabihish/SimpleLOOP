grammar SimpleLOOP;

simpleLoop
  : NEWLINE* (declaration NEWLINE*)* (classDec NEWLINE*)* mainClassDec NEWLINE* (classDec NEWLINE*)* EOF
//    : NEWLINE* (classDec NEWLINE*)* mainClassDec NEWLINE* (classDec NEWLINE*)* EOF
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
    : methodParam COMMA finalmethodParams
    | methodParams
    ;

methodParams
    : (methodParam ASSIGN otherExpression) COMMA methodParams
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
    : type id=IDENTIFIER {System.out.println("VarDec : " + $id.getText());} ASSIGN {System.out.println("Operator : =");} expression
    | type id=IDENTIFIER {System.out.println("VarDec : " + $id.getText());} (COMMA id=IDENTIFIER {System.out.println("VarDec : " + $id.getText());})*
    ;

classFieldDeclaration
    : accessModifier type IDENTIFIER ASSIGN expression
    | accessModifier type IDENTIFIER (COMMA IDENTIFIER)*
    ;

assignment
    : (IDENTIFIER | accessExpression) ASSIGN {System.out.println("Operator : =");} expression
    ;

scope
    : NEWLINE* (statement NEWLINE+)* NEWLINE*
    ;

statement
//    : expression
    : assignment
//    | declaration
    | methodCallStatement
    | funcCallStatement
    | ifStatement
    | elsifStatement
    | elseStatement
    | loopStatement
    | returnStatement
    ;

returnStatement
//TODO: function or variable return
    : RETURN {System.out.println("Return");} expression
    | RETURN  {System.out.println("Return");}
    ;

methodCallStatement
    : IDENTIFIER {System.out.println("MethodCall");} LPAR methodArgs? RPAR
    ;

// TODO: print args verification's left
funcCallStatement
    : PRINT {System.out.println("Built-in : print ");} LPAR expression RPAR
    ;
loopStatement
    : (expression | range | IDENTIFIER) DOT EACH  {System.out.println("Loop : each");} DO STRAIGHT_SLASH IDENTIFIER STRAIGHT_SLASH (LCURLYBRACE NEWLINE+ scope NEWLINE* RCURLYBRACE | NEWLINE+ statement NEWLINE*)
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
    : (QUESTION_MARK expression COLON expression inlineConditionalExpressionPrime)?
    ;

orExpression:
    andExpression (op = OR {System.out.println("Operator : " + $op.getText());} andExpression )*
    ;

andExpression:
    equalityExpression (op = AND {System.out.println("Operator : " + $op.getText());} equalityExpression )*
    ;

equalityExpression:
    relationalExpression (op = EQUALS {System.out.println("Operator : " + $op.getText());} relationalExpression )*
    ;

relationalExpression:
    additiveExpression ((op = GT {System.out.println("Operator : " + $op.getText());}| op = LT {System.out.println("Operator : " + $op.getText());}) additiveExpression )*
    ;

additiveExpression:
    multiplicativeExpression ((op = PLUS {System.out.println("Operator : " + $op.getText());}| op = MINUS {System.out.println("Operator : " + $op.getText());}) multiplicativeExpression )*
    ;

multiplicativeExpression:
    preUnaryExpression ((op=MULT {System.out.println("Operator : " + $op.getText());}| op=DIVIDE {System.out.println("Operator : " + $op.getText());}) preUnaryExpression )*
    ;

preUnaryExpression
    : postUnaryExpression
    | (op=MINUS {System.out.println("Operator : " + $op.getText());} | op=EXCLAMATION_MARK {System.out.println("Operator : " + $op.getText());}) preUnaryExpression
    ;

postUnaryExpression:
     (setExpression | selfExpression | newClassExpression | accessExpression)(PLUSPLUS|MINUSMINUS)?
    ;

setExpression
        : SET (DOT NEW {System.out.println("NEW");} LPAR (newSetArgs? | LPAR newSetArgs RPAR) RPAR)
        | IDENTIFIER DOT ((ADD {System.out.println("ADD");} | INCLUDE {System.out.println("INCLUDE");}| DELETE {System.out.println("DELETE");}) LPAR signedIntLiteral RPAR| MERGE {System.out.println("MERGE");} LPAR (setExpression | IDENTIFIER) RPAR )
        ;

selfExpression
        : SELF (DOT IDENTIFIER LPAR RPAR)?
        ;

newClassExpression
        : CLASS_IDENTIFIER DOT NEW LPAR methodArgs? RPAR
        ;

// TODO: Must also have "(LPAR methodArgs? RPAR)" in the second line
accessExpression:
    otherExpression ((LPAR methodArgs? RPAR) | (DOT IDENTIFIER))*
                 ((LBRACK expression RBRACK) | (DOT IDENTIFIER))*
    ;

//TODO: Is "LPAR (methodArgs?) RPAR" RHS needed?
otherExpression
    : /*value | */literal | IDENTIFIER /* | LPAR (methodArgs?) RPAR | size | append*/
    ;


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