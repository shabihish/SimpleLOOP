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
    ;
/*

classScope
    : (classStatement NEWLINE)*
    | LBRACK classStatement RBRACK
    ;
*/

methodArguments
    :methodArgument COMMA methodArguments
    |methodArgument
    ;

methodArgument
    : ArgumentType IDENTIFIER
    ;

methodBodyReturn
    :LBRACK statement NEWLINE RETURN RBRACK // expression or assignment
    ;

methodDeclaration
    : accessModifier returnType IDENTIFIER LPar methodArguments? RPar methodBodyReturn//(ClassDeclaration (SemiCollon ClassDeclaration)* SemiCollon? NewLine+)+
    | accessModifier VOID? IDENTIFIER LPar methodArguments? RPar LCURLYBRACE NEWLINE+ scope RCURLYBRACE//(ClassDeclaration (SemiCollon ClassDeclaration)* SemiCollon? NewLine+)+
    ;

declaration
    : type IDENTIFIER
    ;

assignment
    : type IDENTIFIER ASSIGN literal
    ;

/*
classDeclaration
    : type r = IDENTIFIER{System.out.println("VarDec : " + $r.text);} LPar args RPar Begin NEWLINE+ Set{System.out.println("Setter");} mainStatementBlock NEWLINE+ Get{System.out.println("Getter");} mainStatementBlock NEWLINE+ End
    | declarationStatement
    ;
*/

/*
scope
    : NEWLINE+ scopeBody
    | LCURLYBRACE scope RCURLYBRACE
    ;*/

scope
    : (statement NEWLINE+)+
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
    inlineConditionalExpression (op = ASSIGN expression )?
    ;

//todo

inlineConditionalExpression
    : expression QUESTION_MARK expression
    |orExpression
    ;

orExpression:
    andExpression (op = OR andExpression )*
    ;

//todo
andExpression:
    equalityExpression (op = AND equalityExpression )*
    ;

//todo
equalityExpression:
    relationalExpression (op = EQUAL relationalExpression )*
    ;

//todo
relationalExpression:
    additiveExpression ((op = GREATER_THAN | op = LESS_THAN) additiveExpression )*
    ;

//todo
additiveExpression:
    multiplicativeExpression ((op = PLUS | op = MINUS) multiplicativeExpression )*
    ;

//todo
multiplicativeExpression:
    preUnaryExpression ((op = MULT | op = DIVIDE) preUnaryExpression )*
    ;

//todo
preUnaryExpression:
    ((op = MINUS | op = EXCLAMATION_MARK) preUnaryExpression ) | postUnaryExpression
    ;

postUnaryExpression:
     accessExpression (PLUSPLUS|MINUSMINUS)?
    ;

//todo
accessExpression:
    otherExpression ((LPAR functionArguments RPAR) | (DOT IDENTIFIER))*  ((LBRACK expression RBRACK) | (DOT IDENTIFIER))*
    ;

//todo
otherExpression:
    /*value | */IDENTIFIER | LPAR (functionArguments) RPAR/* | size | append*/
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
    : functionType r = IDENTIFIER{System.out.println("FunctionDec : " + $r.text);} LPar args RPar mainStatementBlock NewLine+
    ;

functionType
    : type
    | Void
    ;

mainFunction
    : Main{System.out.println("Main");} LPar RPar mainStatementBlock NewLine*
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
    | {System.out.println("FunctionCall");}memberExpression LPar params RPar
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
    : memberExpression LPar params RPar
    | memberExpression Dot (specialExpression | valExpression)
    | memberExpression LBrack expression RBrack
    | specialExpression
    | valExpression
    ;

specialExpression
    : Display{System.out.println("Built-in : display");} LPar assignExpression RPar
    | Append{System.out.println("Append");} LPar assignExpression Comma assignExpression RPar
    | Size{System.out.println("Size");} LPar assignExpression RPar
    ;

valExpression
    : LPar expression RPar
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
//    : LPar params RPar
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

Main: 'main';

While: 'while';
Do: 'do';
If: 'if';
Else: 'else';

Return: 'return';

Get: 'get';
Set: 'set';

Append: 'append';
Display: 'display';

Size: 'size';

LPar: '(';
RPar: ')';

LBRACK: '[';
RBRACK: ']';

LCURLYBRACE: '{';
RCURLYBRACE: '}';

COMMA: ',';

EQUALS: '==';

ASSIGN: '=';

Plus: '+';

Minus: '-';

QUESTION_MARK: '?';

EXCLAMATION_MARK: '!';

PLUSPLUS: '++';

MINUSMINUS: '--';

Multiply: '*';

Division: '/';

Dot: '.';

Sharp: '#';

ARROW: '->';

LT: '<';

GT: '>';

And: '&';

Or: '|';

IDENTIFIER: [a-zA-Z_] [a-zA-Z0-9_]*;

NEWLINE: [\n\r];

Comment: '/*' .*? '*/' -> skip;

WS: [ \t;\n] -> skip;

