grammar SimpleLOOP;

simpleLoop
    : NEWLINE* classDec NEWLINE* EOF
    ;

/*
classSection
    : (NEWLINE* class)*
    ;
*/

classDec
    : CLASS IDENTIFIER LCURLYBRACE NEWLINE+ classBody RCURLYBRACE
    | CLASS IDENTIFIER LT IDENTIFIER LCURLYBRACE NEWLINE+ classBody RCURLYBRACE
    ;

classBody
    :(classStatement NEWLINE+)* (methodDeclaration NEWLINE+)*
    ;

classStatement
    : assignment
    | declaration
//    |

//    | classScope

    | classScope

    ;


classScope
    : LBRACK classStatement RBRACK classScopeprime
    ;


classScopeprime
    : classStatement NEWLINE classScopeprime
    |()?
    ;
/*

classScope
    : (classStatement NEWLINE+)*
    | LBRACK classStatement RBRACK
    ;
*/

methodDeclaration
    : accessModifier returnType IDENTIFIER LPAR methodArguments? RPAR methodBodyReturn //(ClassDeclaration (SemiCollon ClassDeclaration)* SemiCollon? NewLine+)+
    | accessModifier VOID? IDENTIFIER LPAR methodArguments? RPAR LCURLYBRACE NEWLINE+ (statement NEWLINE+)* RCURLYBRACE //(ClassDeclaration (SemiCollon ClassDeclaration)* SemiCollon? NewLine+)+
    ;

methodBodyReturn
    : LBRACK statement NEWLINE RETURN RBRACK // expression or assignment
    ;

methodArguments
    :methodArgument COMMA methodArguments
    |methodArgument
    ;

methodArgument
    : type IDENTIFIER
    ;

declaration
    : type IDENTIFIER
    ;

assignment
    : type IDENTIFIER ASSIGN expression
    ;

/*
classDeclaration
    : type r = IDENTIFIER{System.out.println("VarDec : " + $r.text);} LPAR args RPAR Begin NEWLINE+ Set{System.out.println("Setter");} mainStatementBlock NEWLINE+ Get{System.out.println("Getter");} mainStatementBlock NEWLINE+ End
    | declarationStatement
    ;
*/

/*
scope
    : NEWLINE+ scopeBody
    | LCURLYBRACE scope RCURLYBRACE
    ;*/

scope
    : (statement NEWLINE+)*
    ;

/*
scopeBody
    : (statement NEWLINE)*
    | statement
    ;
*/

statement
    : expression
//    | IfStatement
//    | LoopStatement
    | assignment
    | declaration
    ;

   // | Type? IDENTIFIER (Dot IDENTIFIER)? (Equals Expression)?
    /*
    x //
    self.y //
    x+x//
    x = 4
    int x //
    int x =
    */
// todo COPYED!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
//todo
expression:
    inlineConditionalExpression /*(op = ASSIGN expression )?*/
    ;

inlineConditionalExpression
    : orExpression inlineConditionalExpressionPrime
    ;


inlineConditionalExpressionPrime
    : (QUESTION_MARK expression COLON expression inlineConditionalExpressionPrime)?
    ;

orExpression:
    andExpression (op = OR andExpression )*
    ;

andExpression:
    equalityExpression (op = AND equalityExpression )*
    ;

equalityExpression:
    relationalExpression (op = EQUALS relationalExpression )*
    ;

relationalExpression:
    additiveExpression ((op = GT | op = LT) additiveExpression )*
    ;

additiveExpression:
    multiplicativeExpression ((op = PLUS | op = MINUS) multiplicativeExpression )*
    ;

multiplicativeExpression:
    preUnaryExpression ((MULT | DIVIDE) preUnaryExpression )*
    ;

preUnaryExpression
    : postUnaryExpression
    | (MINUS | EXCLAMATION_MARK) preUnaryExpression
    ;

postUnaryExpression:
     accessExpression (PLUSPLUS|MINUSMINUS)?
    ;

accessExpression:
    otherExpression ((LPAR functionArguments RPAR) | (DOT IDENTIFIER))*  ((LBRACK expression RBRACK) | (DOT IDENTIFIER))*
    ;

otherExpression:
    /*value | */literal | IDENTIFIER | LPAR (functionArguments) RPAR/* | size | append*/
    ;

returnStatement
//TODO: function or variable return
    :RETURN IDENTIFIER
    ;

functionArguments
    : IDENTIFIER
    | IDENTIFIER COMMA functionArguments
    ;
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
    : INT
    | BOOL
    | IDENTIFIER
    | arrayType
    | fptrType
    ;


returnType
    : INT
    | BOOL
    | IDENTIFIER
    | arrayType
    | fptrType
    ;


arrayType
    : (INT | BOOL | IDENTIFIER) LBRACK INT RBRACK
    ;

fptrType
    : FPTR LT (INT | BOOL | VOID) ARROW (INT | BOOL | VOID) GT IDENTIFIER
    ;
/*types
    : type
    | type Comma types
    ;*/
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
literal
    : INT_LITERAL
    | boolLiteral
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

INT_LITERAL
    : [1-9] [0-9]*
    | [0]
    ;


// KeyWords
CLASS: 'class';

INT: 'int';
BOOL: 'bool';
FPTR: 'fptr';

TRUE: 'true';
FALSE: 'false';
VOID: 'void';

PUBLIC: 'public';
PRIVATE: 'private';

MAIN: 'main';

WHILE: 'while';
DO: 'do';
IF: 'if';
ELSE: 'else';

RETURN: 'return';

GET: 'get';
SET: 'set';

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


DIVIDE: '/';


DOT: '.';

ARROW: '->';

LT: '<';

GT: '>';

AND: '&&';

OR: '||';

IDENTIFIER: [a-zA-Z_] [a-zA-Z0-9_]*;

NEWLINE: [\n\r];

Comment: '/*' .*? '*/' -> skip;

WS: [ \t;\n] -> skip;

