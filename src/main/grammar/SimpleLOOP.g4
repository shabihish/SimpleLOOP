grammar SimpleLOOP;

simpleLoop
    : NEWLINE* (declaration NEWLINE+)* (classDec)* EOF
    ;

/*
mainClassDec
    :CLASS NEWLINE* id=MAIN {System.out.println("ClassDec : " + $id.getText());} (NEWLINE* LCURLYBRACE NEWLINE+ classBody RCURLYBRACE NEWLINE+ | NEWLINE+ (classStatement | methodDeclaration))
    ;
*/

classDec
    : CLASS NEWLINE* id=(CLASS_IDENTIFIER | MAIN) {System.out.println("ClassDec : " + $id.getText());} (NEWLINE* LCURLYBRACE NEWLINE+ classBody RCURLYBRACE  NEWLINE+ | NEWLINE+ (classStatement | methodDeclaration))
    | CLASS NEWLINE* id=(CLASS_IDENTIFIER | MAIN) {System.out.println("ClassDec : " + $id.getText());} LT pid=CLASS_IDENTIFIER {System.out.println("Inheritance : " + $id.getText() + " < " + $pid.getText());}  (NEWLINE* LCURLYBRACE NEWLINE+ classBody RCURLYBRACE NEWLINE+ | NEWLINE+ (classStatement | methodDeclaration))
    ;

classBody
    : ((classStatement | methodDeclaration)* initializeMethodDeclaration? (classStatement | methodDeclaration)* | NEWLINE*)
    ;


classStatement
    : classFieldDeclaration SEMICOLON? NEWLINE+
    ;

methodDeclaration
    : accessModifier type id=IDENTIFIER {System.out.println("MethodDec : " + $id.getText());} LPAR finalmethodParams? RPAR (NEWLINE* LCURLYBRACE NEWLINE+ methodBody RCURLYBRACE NEWLINE+ | NEWLINE+ statement)
    | accessModifier VOID id=IDENTIFIER {System.out.println("MethodDec : " + $id.getText());} LPAR finalmethodParams? RPAR (NEWLINE* LCURLYBRACE NEWLINE+ methodBody RCURLYBRACE NEWLINE+ | NEWLINE+ statement)
    ;

initializeMethodDeclaration
    : accessModifier INITIALIZE LPAR finalmethodParams? RPAR (NEWLINE* LCURLYBRACE NEWLINE* methodBody RCURLYBRACE NEWLINE+ | NEWLINE+ statement)
    ;

methodBody
    : (declaration NEWLINE+)+ | (declaration NEWLINE+)* scope
    ;

returningMethodBody
    : (declaration NEWLINE+)* nonReturningScope? (returnStatement SEMICOLON? NEWLINE+) scope?
    ;

finalmethodParams
    : methodParamsWithoutDefaultVal (COMMA methodParamsWithDefaultVal)?
    ;

methodParamsWithoutDefaultVal
    : methodParam COMMA methodParamsWithoutDefaultVal
    | methodParam
    ;

methodParamsWithDefaultVal
    : (methodParam ASSIGN otherExpr) COMMA methodParamsWithDefaultVal
    | methodParam ASSIGN otherExpr
    ;

methodParam
    : type id=IDENTIFIER {System.out.println("ArgumentDec : " + $id.getText());}
    ;

methodArgs
    : (expr | IDENTIFIER ASSIGN expr {System.out.println("Operator : =");}) COMMA methodArgs
    | (expr | IDENTIFIER ASSIGN expr {System.out.println("Operator : =");})
    ;

newSetArgs
    : signedIntLiteral COMMA newSetArgs
    | signedIntLiteral
    ;

declaration
    : type id=IDENTIFIER {System.out.println("VarDec : " + $id.getText());} ASSIGN expr {System.out.println("Operator : =");} SEMICOLON?
    | type id=IDENTIFIER {System.out.println("VarDec : " + $id.getText());} (COMMA id=IDENTIFIER {System.out.println("VarDec : " + $id.getText());})* SEMICOLON?
    ;

classFieldDeclaration
    : accessModifier type id=IDENTIFIER {System.out.println("VarDec : " + $id.getText());} (COMMA id=IDENTIFIER {System.out.println("VarDec : " + $id.getText());})* SEMICOLON?
    | accessModifier type id=IDENTIFIER {System.out.println("VarDec : " + $id.getText());} ASSIGN expr {System.out.println("Operator : =");} SEMICOLON?
    ;

assignment
    : lExpr ASSIGN expr {System.out.println("Operator : =");}
    ;

scope
    : statement+
    ;

nonReturningScope
    : nonReturningStatement+
    ;

nonReturningStatement
    : lExpr SEMICOLON? NEWLINE+
    | block
    | assignment SEMICOLON? NEWLINE+
    | singlePostExpr SEMICOLON? NEWLINE+
    | funcCallStatement SEMICOLON? NEWLINE+
    | ifStatement
    | elsifStatement
    | elseStatement
    | loopStatement
    ;

statement
    : nonReturningStatement
    | returnStatement SEMICOLON? NEWLINE+
    ;

block
    : LCURLYBRACE NEWLINE+ scope? RCURLYBRACE NEWLINE+
    ;

returnStatement
    : RETURN {System.out.println("Return");} expr
    | RETURN {System.out.println("Return");}
    ;

methodCallStatement
    : (IDENTIFIER | INITIALIZE) LPAR methodArgs? RPAR
    ;


funcCallStatement
    : PRINT {System.out.println("Built-in : print");} LPAR expr RPAR
    ;

loopStatement
    : (expr | range) DOT EACH {System.out.println("Loop : each");} DO STRAIGHT_SLASH IDENTIFIER STRAIGHT_SLASH (LCURLYBRACE NEWLINE+ scope RCURLYBRACE NEWLINE+ | NEWLINE+ statement)
    ;

range
    : LPAR expr DOT DOT expr RPAR
    ;

statementBlock
    : NEWLINE* LCURLYBRACE NEWLINE+ scope RCURLYBRACE NEWLINE+
    | NEWLINE+ statement
    ;

ifStatement
    : IF {System.out.println("Conditional : if");} expr statementBlock
    ;

ifStatementBlock
    : NEWLINE* LCURLYBRACE NEWLINE+ scope RCURLYBRACE NEWLINE+
    | NEWLINE+ insideIfStatementBlock
    ;

elseStatement
    : IF {System.out.println("Conditional : if");} expr ifStatementBlock ELSE {System.out.println("Conditional : else");} statementBlock
    ;

elsifStatement
    : IF {System.out.println("Conditional : if");} expr ifStatementBlock (ELSIF {System.out.println("Conditional : elsif");} expr statementBlock)+ (ELSE {System.out.println("Conditional : else");} statementBlock)?
    ;

insideIfStatementBlock
    : expr SEMICOLON? NEWLINE+
    | returnStatement SEMICOLON? NEWLINE+
    | assignment SEMICOLON? NEWLINE+
    | elsifStatement
    | elseStatement
    ;

expr
    : LPAR expr RPAR
    | inlineConditionalExpr
    ;

inlineConditionalExpr
    : orExpr inlineConditionalExprPrime
    ;

inlineConditionalExprPrime
    : (op=QUESTION_MARK expr COLON expr {System.out.println("Operator : ?:");} inlineConditionalExprPrime)?
    ;

orExpr:
    andExpr (op = OR  andExpr {System.out.println("Operator : " + $op.getText());} )*
    | andExpr
    ;

andExpr:
    equiExpr (op = AND  equiExpr {System.out.println("Operator : " + $op.getText());})*
    | equiExpr
    ;

equiExpr:
    relExpr
    | relExpr (op =  EQUALS {System.out.println("Operator : " + $op.getText());} relExpr  )*
    ;

relExpr:
    addExpr
    | addExpr ((op= GT | op = LT) addExpr  {System.out.println("Operator : " + $op.getText());} )*
    ;

addExpr:
    multExpr
    | multExpr ((op=PLUS | op=MINUS)  multExpr {System.out.println("Operator : " + $op.getText());})*
    ;

multExpr:
    singlePreExpr
    | singlePreExpr ((op=MULT | op=DIVIDE)  singlePreExpr {System.out.println("Operator : " + $op.getText());})*
    ;

singlePreExpr
    : singlePostExpr
    | (op=MINUS | op=EXCLAMATION_MARK) {System.out.println("Operator : " + $op.getText());} singlePreExpr
    ;

singlePostExpr:
     (setExpr | selfExpr | newClassExpr | accessExpr)( op=PLUSPLUS {System.out.println("Operator : " + $op.getText());} | op=MINUSMINUS {System.out.println("Operator : " + $op.getText());})?
    ;

setExpr
        : SET (DOT NEW {System.out.println("NEW");} LPAR (newSetArgs? | LPAR newSetArgs RPAR) RPAR) setExprPrime
        | (accessExpr | selfExpr) DOT ((ADD {System.out.println("ADD");} | INCLUDE {System.out.println("INCLUDE");} | DELETE {System.out.println("DELETE");}) LPAR expr RPAR | MERGE LPAR (setExpr) RPAR ) setExprPrime
        ;

setExprPrime
        : (DOT ((ADD {System.out.println("ADD");} | INCLUDE {System.out.println("INCLUDE");} | DELETE {System.out.println("DELETE");}) LPAR expr RPAR | MERGE LPAR (setExpr) RPAR ) setExprPrime)?
        ;

selfExpr
        : SELF (DOT IDENTIFIER (LPAR methodArgs? RPAR | LBRACK expr RBRACK)*)*
        ;

newClassExpr
        : CLASS_IDENTIFIER DOT NEW LPAR methodArgs? RPAR
        ;


accessExpr
        : otherExpr (DOT (IDENTIFIER | INITIALIZE) | LPAR methodArgs? RPAR | LBRACK expr RBRACK)*
        ;

//: Is "LPAR (methodArgs?) RPAR" RHS needed?
otherExpr
    : literal | IDENTIFIER  | NULL | LPAR (methodArgs?) RPAR
    ;

lExpr
    : lAccessExpr
    ;

lAccessExpr
       : lOtherExpr (DOT (IDENTIFIER | INITIALIZE) | LPAR methodArgs? RPAR |  (LBRACK expr RBRACK))* DOT (IDENTIFIER | INITIALIZE) LPAR {System.out.println("MethodCall");} methodArgs? RPAR
       | lOtherExpr (DOT (IDENTIFIER | INITIALIZE) | LPAR methodArgs? RPAR | (LBRACK expr RBRACK))* (DOT (IDENTIFIER) | (LBRACK expr RBRACK))?
       ;

secondlAccessExpression
    : (DOT (IDENTIFIER | INITIALIZE) | LPAR methodArgs? RPAR | LBRACK expr RBRACK)*
    ;

lOtherExpr
    : IDENTIFIER | SELF | LPAR (methodArgs?) RPAR
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
    : (INT | BOOL | CLASS_IDENTIFIER) (LBRACK (expr) RBRACK)+
    ;

fptrType
    : FPTR LT (type | VOID) (COMMA (type | VOID))* ARROW (type | VOID) GT
    ;

accessModifier
    : PUBLIC | PRIVATE
    ;


//Is Null valid as value?
literal
    : signedIntLiteral
    | boolLiteral
    | NULL
    ;

boolLiteral
    : TRUE
    | FALSE
    ;
signedIntLiteral
    : (PLUS {System.out.println("Operator : + ");}| MINUS {System.out.println("Operator : -");})? POSITIVE_INT_LITERAL
    ;

POSITIVE_INT_LITERAL
    : [1-9] [0-9]*
    | [0]
    ;

// tokens
DOUBLE_SLASH: '//' NEWLINE [ \t]* -> skip;
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

LPAR: '(';
RPAR: ')';

LBRACK: '[';
RBRACK: ']';

LCURLYBRACE: '{';
RCURLYBRACE: '}';

COMMA: ',';

SEMICOLON: ';';

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

SCOPE_COMMENT: '=begin' NEWLINE .*? NEWLINE [ \t]* '=end' -> skip;
INLINE_COMMENT: '#' .*? NEWLINE -> skip;

WS: [ \t] -> skip;
