grammar SimpleLOOP;

simpleLoop
    : NEWLINE* classDec NEWLINE* EOF
    ;

classDec
    : CLASS CLASS_IDENTIFIER LCURLYBRACE NEWLINE+ classBody RCURLYBRACE
    | CLASS CLASS_IDENTIFIER LT IDENTIFIER LCURLYBRACE NEWLINE+ classBody RCURLYBRACE
    ;

classBody
    :(classStatement NEWLINE+)* (methodDeclaration NEWLINE+)*
    ;

classScope
    : LCURLYBRACE classStatement? RCURLYBRACE classScopeprime
    ;


classScopeprime
    : (classStatement NEWLINE classScopeprime)?
    ;

classStatement
    : assignment
    | declaration
    | classScope
    ;
/*

classScope
    : (classStatement NEWLINE+)*
    | LBRACK classStatement RBRACK
    ;
*/

methodDeclaration
    : accessModifier returnType IDENTIFIER LPAR methodParams? RPAR methodBodyReturn
    | accessModifier VOID? IDENTIFIER LPAR methodParams? RPAR LCURLYBRACE NEWLINE+ scope RCURLYBRACE
    ;

methodBodyReturn
    : LCURLYBRACE NEWLINE+ scope RETURN expression NEWLINE* RCURLYBRACE // expression or assignment
    ;

methodParams
    :methodParam COMMA methodParams
    |methodParam
    ;

methodParam
    : type IDENTIFIER
    ;

methodArgs
    : IDENTIFIER COMMA methodArgs
    | IDENTIFIER
    ;

declaration
    : type IDENTIFIER
    ;

assignment
    : type? IDENTIFIER ASSIGN expression
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
    | loopStatement
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

// TODO: Enfocre .new() to be called on CLASS_IDENTIFIERS
accessExpression:
    otherExpression ((LPAR methodArgs? RPAR) | (DOT NEW) | (DOT DELETE) | (DOT NEW) | (DOT IDENTIFIER))*
                 ((LBRACK expression RBRACK) | (DOT NEW) | (DOT DELETE) | (DOT NEW) | (DOT IDENTIFIER))*
    ;

otherExpression:
    /*value | */literal | SET | IDENTIFIER | LPAR (methodArgs?) RPAR/* | size | append*/
    ;

returnStatement
//TODO: function or variable return
    : RETURN IDENTIFIER
    |
    ;

//functionArguments
//    : IDENTIFIER
//    | IDENTIFIER COMMA functionArguments
//    | IDENTIFIER COMMA functionArguments
//    ;

loopStatement
    : (range | IDENTIFIER) DOT EACH DO STRAIGHT_SLASH IDENTIFIER STRAIGHT_SLASH (LCURLYBRACE NEWLINE+ scope NEWLINE+ RCURLYBRACE | NEWLINE+ statement NEWLINE+)
    ;

range
    : LPAR INT_LITERAL DOT DOT INT_LITERAL RPAR
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
    | CLASS_IDENTIFIER
    | arrayType
    | fptrType
    | SET LT type GT
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
SET: 'Set';

TRUE: 'true';
FALSE: 'false';
VOID: 'void';

PUBLIC: 'public';
PRIVATE: 'private';

MAIN: 'main';
SELF: 'self';

INITIALIZE: 'initialize';
NEW: 'new';
DELETE: 'delete';
INCLUDE: 'include';

EACH: 'each';
DO: 'do';

IF: 'if';
ELSE: 'else';
ELSEIF: 'elseif';

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

SCOPE_COMMENT: '=begin\n' .*? '\n=end' -> skip;
INLINE_COMMENT: '#' .*? '\n' -> skip;

WS: [ \t;\n] -> skip;

