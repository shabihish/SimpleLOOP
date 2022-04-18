grammar SimpleLOOP;

simpleLoop
    : NEWLINE* (declaration NEWLINE+)* (classDec)* (mainClassDec)? (classDec)* EOF
    ;

mainClassDec
    :CLASS NEWLINE* id=MAIN {System.out.println("ClassDec : " + $id.getText());} (NEWLINE* LCURLYBRACE NEWLINE+ classBody RCURLYBRACE NEWLINE+ | NEWLINE+ (classStatement | methodDeclaration))
    ;

classDec
    : CLASS NEWLINE* id=CLASS_IDENTIFIER {System.out.println("ClassDec : " + $id.getText());} (NEWLINE* LCURLYBRACE NEWLINE+ classBody RCURLYBRACE  NEWLINE+ | NEWLINE+ (classStatement | methodDeclaration) NEWLINE+)
    | CLASS NEWLINE* id=CLASS_IDENTIFIER {System.out.println("ClassDec : " + $id.getText());} LT pid=CLASS_IDENTIFIER {System.out.println("Inheritance : " + $id.getText() + " < " + $pid.getText());}  (NEWLINE* LCURLYBRACE NEWLINE+ classBody RCURLYBRACE  NEWLINE+ | NEWLINE+ (classStatement | methodDeclaration) NEWLINE+)
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
    : (methodParam ASSIGN otherExpression) COMMA methodParamsWithDefaultVal
    | methodParam ASSIGN otherExpression
    ;

methodParam
    : type id=IDENTIFIER {System.out.println("ArgumentDec : " + $id.getText());}
    ;

methodArgs
    : (expression | IDENTIFIER ASSIGN expression {System.out.println("Operator : =");}) COMMA methodArgs
    | (expression | IDENTIFIER ASSIGN expression {System.out.println("Operator : =");})
    ;

newSetArgs
    : signedIntLiteral COMMA newSetArgs
    | signedIntLiteral
    ;

declaration
    : type id=IDENTIFIER {System.out.println("VarDec : " + $id.getText());} ASSIGN expression {System.out.println("Operator : =");} SEMICOLON?
    | type id=IDENTIFIER {System.out.println("VarDec : " + $id.getText());} (COMMA id=IDENTIFIER {System.out.println("VarDec : " + $id.getText());})* SEMICOLON?
    ;

classFieldDeclaration
    : accessModifier type id=IDENTIFIER {System.out.println("VarDec : " + $id.getText());} (COMMA id=IDENTIFIER {System.out.println("VarDec : " + $id.getText());})* SEMICOLON?
    | accessModifier type id=IDENTIFIER {System.out.println("VarDec : " + $id.getText());} ASSIGN expression {System.out.println("Operator : =");} SEMICOLON?
    ;

assignment
    : lExpression ASSIGN expression {System.out.println("Operator : =");}
    ;

scope
    : statement+
    ;

nonReturningScope
    : nonReturningStatement+
    ;

nonReturningStatement
    : lExpression SEMICOLON? NEWLINE+
    | block
    | assignment SEMICOLON? NEWLINE+
    | postUnaryExpression SEMICOLON? NEWLINE+
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
    : RETURN {System.out.println("Return");} expression
    | RETURN {System.out.println("Return");}
    ;

methodCallStatement
    : (IDENTIFIER | INITIALIZE) LPAR methodArgs? RPAR
    ;


funcCallStatement
    : PRINT {System.out.println("Built-in : print");} LPAR expression RPAR
    ;

loopStatement
    : (expression | range) DOT EACH {System.out.println("Loop : each");} DO STRAIGHT_SLASH IDENTIFIER STRAIGHT_SLASH (LCURLYBRACE NEWLINE+ scope RCURLYBRACE NEWLINE+ | NEWLINE+ statement)
    ;

// TODO: Check whether the usage of negative int's is correct
range
    : LPAR expression DOT DOT expression RPAR
    ;

statementBlock
    : NEWLINE* LCURLYBRACE NEWLINE+ scope RCURLYBRACE NEWLINE+
    | NEWLINE+ statement
    ;

ifStatement
    : IF {System.out.println("Conditional : if");} expression statementBlock
    ;

ifStatementBlock
    : NEWLINE* LCURLYBRACE NEWLINE+ scope RCURLYBRACE NEWLINE+
    | NEWLINE+ insideIfStatementBlock
    ;

elseStatement
    : IF {System.out.println("Conditional : if");} expression ifStatementBlock ELSE {System.out.println("Conditional : else");} statementBlock
    ;

elsifStatement
    : IF {System.out.println("Conditional : if");} expression ifStatementBlock (ELSIF {System.out.println("Conditional : elsif");} expression statementBlock)+ (ELSE {System.out.println("Conditional : else");} statementBlock)?
    ;

insideIfStatementBlock
    : expression SEMICOLON? NEWLINE+
    | returnStatement SEMICOLON? NEWLINE+
    | assignment SEMICOLON? NEWLINE+
    | elsifStatement
    | elseStatement
    ;

expression
    : LPAR expression RPAR
    | inlineConditionalExpression
    ;

inlineConditionalExpression
    : orExpression inlineConditionalExpressionPrime
    ;

inlineConditionalExpressionPrime
    : (op=QUESTION_MARK expression COLON expression {System.out.println("Operator : ?:");} inlineConditionalExpressionPrime)?
    ;

orExpression:
    andExpression (op = OR  andExpression {System.out.println("Operator : " + $op.getText());} )*
    | andExpression
    ;

andExpression:
    equalityExpression (op = AND  equalityExpression {System.out.println("Operator : " + $op.getText());})*
    | equalityExpression
    ;

equalityExpression:
    relationalExpression
    | relationalExpression (op =  EQUALS {System.out.println("Operator : " + $op.getText());} relationalExpression  )*
    ;

relationalExpression:
    additiveExpression
    | additiveExpression ((op= GT | op = LT) additiveExpression  {System.out.println("Operator : " + $op.getText());} )*
    ;

additiveExpression:
    multiplicativeExpression
    | multiplicativeExpression ((op=PLUS | op=MINUS)  multiplicativeExpression {System.out.println("Operator : " + $op.getText());})*
    ;

multiplicativeExpression:
    preUnaryExpression
    | preUnaryExpression ((op=MULT | op=DIVIDE)  preUnaryExpression {System.out.println("Operator : " + $op.getText());})*
    ;

preUnaryExpression
    : postUnaryExpression
    | (op=MINUS | op=EXCLAMATION_MARK) {System.out.println("Operator : " + $op.getText());} preUnaryExpression
    ;

postUnaryExpression:
     (setExpression | selfExpression | newClassExpression | accessExpression)( op=PLUSPLUS {System.out.println("Operator : " + $op.getText());} | op=MINUSMINUS {System.out.println("Operator : " + $op.getText());})?
    ;

setExpression
        : SET (DOT NEW {System.out.println("NEW");} LPAR (newSetArgs? | LPAR newSetArgs RPAR) RPAR) setExpressionPrime
        | (accessExpression | selfExpression) DOT ((ADD {System.out.println("ADD");} | INCLUDE {System.out.println("INCLUDE");} | DELETE {System.out.println("DELETE");}) LPAR expression RPAR | MERGE LPAR (setExpression) RPAR ) setExpressionPrime
        ;

setExpressionPrime
        : (DOT ((ADD {System.out.println("ADD");} | INCLUDE {System.out.println("INCLUDE");} | DELETE {System.out.println("DELETE");}) LPAR expression RPAR | MERGE LPAR (setExpression) RPAR ) setExpressionPrime)?
        ;

selfExpression
        : SELF (DOT IDENTIFIER (LPAR methodArgs? RPAR | LBRACK expression RBRACK)*)*
        ;

newClassExpression
        : CLASS_IDENTIFIER DOT NEW LPAR methodArgs? RPAR
        ;


accessExpression
        : otherExpression (DOT (IDENTIFIER | INITIALIZE) | LPAR methodArgs? RPAR | LBRACK expression RBRACK)*
        ;

//: Is "LPAR (methodArgs?) RPAR" RHS needed?
otherExpression
    : literal | IDENTIFIER  | NULL | LPAR (methodArgs?) RPAR
    ;

lExpression
    : lAccessExpression
    ;

lAccessExpression
    : lOtherExpression ( DOT (INITIALIZE  |  IDENTIFIER) |  (LBRACK expression RBRACK))* LPAR {System.out.println("MethodCall");} methodArgs? RPAR secondlAccessExpression?
    | lOtherExpression (DOT (IDENTIFIER | INITIALIZE))*
    ;
  
secondlAccessExpression
    : (DOT (IDENTIFIER | INITIALIZE) | LPAR methodArgs? RPAR | LBRACK expression RBRACK)*
    ;

lOtherExpression
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
    : (INT | BOOL | CLASS_IDENTIFIER) (LBRACK (expression) RBRACK)+
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

SCOPE_COMMENT: '=begin' NEWLINE+ .*? NEWLINE+ [ \t]* '=end' -> skip;
INLINE_COMMENT: '#' .*? '\n' -> skip;

WS: [ \t] -> skip;
