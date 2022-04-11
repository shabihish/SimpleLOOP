grammar SimpleLOOP;

simpleLoop
    : ClassSection functionSection NewLine* mainFunction EOF
    ;

ClassSection
    : (NewLine* Class)*
    ;

Class
    : CLASS r = IDENTIFIER{System.out.print("CLASSDec : " + $r.text);} LCURLYBRACE NewLine+ ClassBody RCURLYBRACE
    | CLASS r = IDENTIFIER{System.out.print("CLASSDec : " + $r.text);} LT IDENTIFIER LCURLYBRACE NewLine+ ClassBody RCURLYBRACE
    ;

ClassBody
    :ClassStatement
    |ClassScope
    |MethodDeclaration
    ;

MethodArguments
    :MethodArgument Comma MethodArguments
    |MethodArgument
    ;

MethodArgument
    : ArgumentType IDENTIFIER
    ;



MethodBodyReturn
    :LBrack Statement NewLine RETURN RBrack // expression or assignment
    ;

MethodDeclaration
    : AccessModifier ReturnType IDENTIFIER LPar MethodArguments? RPar MethodBodyReturn//(ClassDeclaration (SemiCollon ClassDeclaration)* SemiCollon? NewLine+)+
    | AccessModifier IDENTIFIER LPar MethodArguments? RPar NewLine Scope//(ClassDeclaration (SemiCollon ClassDeclaration)* SemiCollon? NewLine+)+
    ;


Expression
    :ArithmetciExpr
    |ComparisionExpr
    |LogicalExpr
    |ConditionalExpr
    ;



ClassDeclaration
    : type r = IDENTIFIER{System.out.println("VarDec : " + $r.text);} LPar args RPar Begin NewLine+ Set{System.out.println("Setter");} mainStatementBlock NewLine+ Get{System.out.println("Getter");} mainStatementBlock NewLine+ End
    | declarationStatement
    ;

Scope
    : (Statement Newline)*
    | LBrack Statement RBrack

    ;

ClassScope
    : (ClassStatement Newline)*
    | LBrack ClassStatement RBrack
    ;

Statement
    : Expression
    | IfStatement
    | LoopStatement
    | Scope
    | Assignment
    | Decleration
    ;

ClassStatement
    : Assignment
    | ClassScope
    | Decleration
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
    inlineConditionalExpression (op = ASSIGN expression )? ;

//todo

inlineConditionalExpression
    ://todo
    |orExpression
    ;

orExpression:
    andExpression (op = OR andExpression )*;

//todo
andExpression:
    equalityExpression (op = AND equalityExpression )*;

//todo
equalityExpression:
    relationalExpression (op = EQUAL relationalExpression )*;

//todo
relationalExpression:
    additiveExpression ((op = GREATER_THAN | op = LESS_THAN) additiveExpression )*;

//todo
additiveExpression:
    multiplicativeExpression ((op = PLUS | op = MINUS) multiplicativeExpression )*;

//todo
multiplicativeExpression:
    preUnaryExpression ((op = MULT | op = DIVIDE) preUnaryExpression )*;

//todo
preUnaryExpression:
    ((op = MINUS | op = EXCLAMATION_MARK) preUnaryExpression ) | postUnaryExpression;

postUnaryExpression:
     accessExpression (PLUSPLUS|MINUSMINUS)?
    ;

//todo
accessExpression:
    otherExpression ((LPAR functionArguments RPAR) | (DOT identifier))*  ((LBRACK expression RBRACK) | (DOT identifier))*;

//todo
otherExpression:
    value | identifier | LPAR (functionArguments) RPAR | size | append ;

ReturnStatement
//TODO: function or variable return
    :RETURN IDENTIFIER

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
    ;*/

ReturnType
    :INT
    |VOID
    ;

Type
    : INT
    | BOOL
    | IDENTIFIER
    | ArrayType
    | FptrType
    ;

ArrayType
    : (INT | BOOL | IDENTIFIER) LBrack INT RBrack
    ;

FptrType
    : FPTR LT (INT | BOOL | VOID) ARROW (INT | BOOL | VOID) GT IDENTIFIER
    ;
/*types
    : type
    | type Comma types
    ;*/
AccessModifier
    : PUBLIC | PRIVATE
    ;
arg
    : type r = IDENTIFIER{System.out.println("ArgumentDec : " + $r.text);}
    ;

args
    : arg (Comma arg)*
    |
    ;

params
    : assignExpression (Comma assignExpression)*
    |
    ;

literal
    : IntLiteral
    | boolLiteral
    | setLiteral
    ;

setLiteral
    : LPar params RPar
    ;

boolLiteral
    : True
    | False
    ;

IntLiteral: [1-9] [0-9]* | [0];

// KeyWords
CLASS: 'class';

Begin: 'begin';

End: 'end';

INT: 'int';

BOOL: 'bool';

True: 'true';

False: 'false';

PUBLIC: 'public';

PRIVATE: 'private';


FPTR: 'fptr';

Main: 'main';

VOID: 'void';

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

SemiCollon: ';';

LPar: '(';

RPar: ')';

LBrack: '[';

RBrack: ']';

Comma: ',';

Equals: '==';

Assign: '=';

Plus: '+';

Minus: '-';

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

NewLine: [\n\r];

Comment: '/*' .*? '*/' -> skip;

WS: [ \t] -> skip;

RCURLYBRACE: '{';

LCURLYBRACE: '}';
