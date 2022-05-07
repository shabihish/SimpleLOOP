grammar SimpleLOOP;

@header{
     import main.ast.nodes.*;
     import main.ast.nodes.declaration.*;
     import main.ast.nodes.declaration.classDec.*;
     import main.ast.nodes.declaration.classDec.classMembersDec.*;
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
 }

simpleLOOP returns [Program simpleLOOPProgram]:
    NEWLINE* p = program {$simpleLOOPProgram = $p.programRet;} NEWLINE* EOF;

program returns[Program programRet]:
    {$programRet = new Program();
     int line = 1;
     $programRet.setLine(line);}
    (v = varDecStatement NEWLINE+
    {
        for (VariableDeclaration varDec: $v.vardDecStatementRet){
            varDec.setAsGlobal();
            $programRet.addGlobalVariable(varDec);
        }
    })*
    (c = classDeclaration NEWLINE+ {$programRet.addClass($c.classDeclarationRet);})*;

//todo
//done
//correct
constructor returns [ConstructorDeclaration constructorRet]:
      PUBLIC l = INITIALIZE
      {$constructorRet = new ConstructorDeclaration();
       $constructorRet.setLine($l.getLine());}
       args = methodArgsDec {$constructorRet.setArgs($args.methodArgsDecRet);}
       NEWLINE*
       m = methodBody
        { $constructorRet.setBody($m.methodBodyRet);
          $constructorRet.setLocalVars($m.localVars);
        };

//todo
//done
//correct
classDeclaration returns [ClassDeclaration classDeclarationRet]:

    c = CLASS id = class_identifier
     { $classDeclarationRet = new ClassDeclaration($id.idRet);
       $classDeclarationRet.setLine($c.getLine());}
    (LESS_THAN par_id = class_identifier  { $classDeclarationRet.setParentClassName($par_id.idRet);})?
    NEWLINE* ((LBRACE NEWLINE+ ( f1 = field_decleration
    { for (Declaration field : $f1.field_declerationRet) {
           if(field instanceof FieldDeclaration)
           {
                $classDeclarationRet.addField((FieldDeclaration) field);
           }
           if(field instanceof ConstructorDeclaration)
           {
                $classDeclarationRet.setConstructor((ConstructorDeclaration) field);
           }
           if(field instanceof MethodDeclaration && !(field instanceof ConstructorDeclaration))
           {
                $classDeclarationRet.addMethod((MethodDeclaration) field);
           }
      }
    }
     NEWLINE+)+ RBRACE) | ( f2 = field_decleration
     { for (Declaration field : $f1.field_declerationRet) {
          if(field instanceof FieldDeclaration)
          {
              $classDeclarationRet.addField((FieldDeclaration) field);
          }
          if(field instanceof ConstructorDeclaration)
          {
              $classDeclarationRet.setConstructor((ConstructorDeclaration) field);
          }
          if(field instanceof MethodDeclaration && !(field instanceof ConstructorDeclaration))
          {
              $classDeclarationRet.addMethod((MethodDeclaration) field);
          }
        }
     }
     ));

//todo
//done
//correct
field_decleration returns [ArrayList<Declaration> field_declerationRet]:
    { $field_declerationRet  = new ArrayList<>();}
    ((( acc = (PUBLIC |PRIVATE)( v = varDecStatement
    { for (VariableDeclaration declaration: $v.vardDecStatementRet){
        boolean access = $acc.toString() == "public" ? true : false;
        var newfield = new FieldDeclaration(declaration, access);
        newfield.setLine(declaration.getLine());
        $field_declerationRet.add(newfield);
      }
    }
    | m = method
     { var newmethod = $m.methodDeclarationRet;
       boolean access = $acc.toString() == "public" ? true : false;
       newmethod.setPrivate(access);
       $field_declerationRet.add(newmethod);
     }
     )) | c = constructor {$field_declerationRet.add($c.constructorRet);}));

//todo
//done
//correct
method returns [MethodDeclaration methodDeclarationRet] locals [Type rtype]:
    ( t = type {$rtype = $t.typeRet;}
    | VOID {$rtype = new VoidType();}

     ) id = identifier args = methodArgsDec NEWLINE* m = methodBody
     { $methodDeclarationRet = new MethodDeclaration($id.idRet, $rtype, false);
       $methodDeclarationRet.setLine($id.line);
       $methodDeclarationRet.setLocalVars($m.localVars);
       $methodDeclarationRet.setArgs($args.methodArgsDecRet);
       $methodDeclarationRet.setBody($m.methodBodyRet);
      };

//todo
//done
//correct
methodBody returns [ArrayList<Statement> methodBodyRet, ArrayList<VariableDeclaration> localVars ]:
    { $methodBodyRet = new ArrayList<>();
      $localVars = new ArrayList<>(); }
    (LBRACE NEWLINE+ ( vd1 = varDecStatement
    { for (VariableDeclaration vd: $vd1.vardDecStatementRet)
        $localVars.add(vd);}
    NEWLINE+)* ( ss1 = singleStatement  { $methodBodyRet.add($ss1.singleStatementRet);}
    NEWLINE+)* RBRACE)| (( vd2 = varDecStatement
    { for (VariableDeclaration vd: $vd2.vardDecStatementRet)
           $localVars.add(vd);})
    | (ss2=singleStatement
    {if($methodBodyRet==null)
        $methodBodyRet = new ArrayList<>();
    $methodBodyRet.add($ss2.singleStatementRet);} ));

//todo/////////////////////////////////
//done
//not sure
methodArgsDec returns[ArrayList<VariableDeclaration> methodArgsDecRet]:
    { $methodArgsDecRet = new ArrayList<>();}
    LPAR ( arg1 = argDec {$methodArgsDecRet.add($arg1.argDecRet);}
    ((ASSIGN expr1 = orExpression
    ) | (COMMA arg2 = argDec {$methodArgsDecRet.add($arg2.argDecRet);}
    )*) (COMMA arg3 = argDec {$methodArgsDecRet.add($arg3.argDecRet);}
    ASSIGN orExpression)*)? RPAR ;

//todo
//done
//correct
argDec returns [VariableDeclaration argDecRet]:
    t = type id = identifier
    { $argDecRet = new VariableDeclaration($id.idRet,$t.typeRet);
      $argDecRet.setLine($id.line);};

//todo
//done
methodArgs returns [ArrayList<Expression> methodArgsRet]:
    { $methodArgsRet = new ArrayList<>();}
    ( e1 = expression  { $methodArgsRet.add($e1.exprRet);}
    (COMMA e2=expression {$methodArgsRet.add($e2.exprRet);} )*)?;

//todo
//done
//correct_
body returns [Statement bodyRet]:
     ( b = blockStatement  { $bodyRet  = $b.blockStatementRet;}
     | (NEWLINE+ s = singleStatement { $bodyRet  = $s.singleStatementRet;} ));

//todo
//done
//correct_
blockStatement returns [BlockStmt blockStatementRet]: //BlockStmt or Statement????
    { $blockStatementRet = new BlockStmt();}
    l = LBRACE  {$blockStatementRet.setLine($l.getLine());}
    NEWLINE+ ( s = singleStatement {$blockStatementRet.addStatement($s.singleStatementRet);}
    NEWLINE+)* RBRACE;

//todo
//done
//not sure
singleStatement returns [Statement singleStatementRet]:
     s1 = ifStatement {$singleStatementRet = $s1.ifStatementRet;}
    | s2 = printStatement {$singleStatementRet = $s2.printStatementRet;}
    | s3 = methodCallStmt {$singleStatementRet = $s3.methodCallStmtRet;}
    | s4 = returnStatement {$singleStatementRet = $s4.returnStatementRet;}
    | s5 = assignmentStatement {$singleStatementRet = $s5.assignmentStatementRet;}
    | s6 = loopStatement {$singleStatementRet = $s6.loopStatementRet;}
    | s7 = addStatement {$singleStatementRet = $s7.addStatementRet;}
    | s8 = mergeStatement {$singleStatementRet = $s8.mergeStatementRet;}
    | s9 = deleteStatement {$singleStatementRet = $s9.deleteStatementRet;};


//todo
//is done
//not sure about return type where to set line?
addStatement returns [SetAdd addStatementRet]:
    e1 = expression DOT l = ADD LPAR e2 = orExpression RPAR
    { $addStatementRet = new SetAdd($e1.exprRet, $e2.orExpressionRet);
     $addStatementRet.setLine($l.getLine());};

//todo
//done
//not sure about return type where to set line?
mergeStatement returns [SetMerge mergeStatementRet] locals[ArrayList<Expression> elementargs ,Expression setarg, int line]:
    {$elementargs = new ArrayList<>();}
    e1 = expression DOT l = MERGE LPAR e2 = orExpression
    { $setarg = $e1.exprRet;
      $line = $l.getLine();
      $elementargs.add($e2.orExpressionRet);
    }
    (COMMA e3 = orExpression {$elementargs.add($e3.orExpressionRet);}
    )* RPAR
    {$mergeStatementRet = new SetMerge($setarg,$elementargs );
     $mergeStatementRet.setLine($line);
    };

//todo
//done
//correct
deleteStatement returns [SetDelete deleteStatementRet]:
    e1 = expression DOT l = DELETE LPAR e2 = orExpression RPAR
      { $deleteStatementRet = new SetDelete($e1.exprRet, $e2.orExpressionRet);
        $deleteStatementRet.setLine($l.getLine());};

//todo
//done
//correct
varDecStatement returns [ArrayList<VariableDeclaration> vardDecStatementRet]:
    {$vardDecStatementRet = new ArrayList<>();}
    t = type id1 = identifier
     { var newvar = new VariableDeclaration($id1.idRet, $t.typeRet);
       newvar.setLine($id1.line);
       $vardDecStatementRet.add(newvar);
     }
     (COMMA id2 = identifier
     {newvar = new VariableDeclaration($id2.idRet, $t.typeRet);
            newvar.setLine($id2.line);
            $vardDecStatementRet.add(newvar);
     }
     )*;

//todo
//done
//correct_
ifStatement returns [ConditionalStmt ifStatementRet]:
    l = IF c = condition b = body
     { $ifStatementRet = new ConditionalStmt($c.conditionRet , $b.bodyRet);
       $ifStatementRet.setLine($l.getLine());}
    ( e1 = elsifStatement {$ifStatementRet.addElsif($e1.elsifStatementRet);})*
    ( e2 = elseStatement {$ifStatementRet.setElseBody($e2.elseStatementRet);})?;

//todo
//done
//correct
elsifStatement returns [ElsifStmt elsifStatementRet]:
     NEWLINE* l = ELSIF c = condition b = body
      { $elsifStatementRet = new ElsifStmt($c.conditionRet , $b.bodyRet);
        $elsifStatementRet.setLine($l.getLine());} ;

//todo
//done
//correct
condition returns [Expression conditionRet]:
    (LPAR expr1 = expression {$conditionRet = $expr1.exprRet;}
    RPAR) | (expr2 = expression
    {$conditionRet = $expr2.exprRet;});

//todo
//done
//correct
elseStatement returns [Statement elseStatementRet]:
    NEWLINE* ELSE b = body {$elseStatementRet = $b.bodyRet;};

//todo
//done
//correct
printStatement returns [PrintStmt printStatementRet]:
    l = PRINT LPAR expr = expression RPAR
    { $printStatementRet = new PrintStmt($expr.exprRet);
      $printStatementRet.setLine($l.getLine());};

//todo: Needs a line to be set
//dont know what to do with inc and dec
methodCallStmt returns [MethodCallStmt methodCallStmtRet] locals [Expression instance, MethodCall expr]:
    e1 = accessExpression{$instance = $e1.exprRet;}
    (DOT ( id1 = INITIALIZE {var id = new Identifier($id1.text);
    id.setLine($id1.getLine());
    $instance = new ObjectMemberAccess($instance, id);
    $instance.setLine($id1.getLine());
    }
    | id2 = identifier {
    $instance = new ObjectMemberAccess($instance, $id2.idRet);
    $instance.setLine($id2.idRet.getLine());
    }))*
    (( l = LPAR args = methodArgs
    {$expr = new MethodCall($instance , $args.methodArgsRet);
     $expr.setLine($l.getLine());
    } RPAR)
    {$methodCallStmtRet = new MethodCallStmt($expr);
     $methodCallStmtRet.setLine($expr.getLine());
    }/* | ((op = INC | op = DEC))*/);

//todo
//done
//correct_
returnStatement returns [ReturnStmt returnStatementRet ]:
    l = RETURN
    {$returnStatementRet = new NullValue();
    $returnStatementRet.setLine($l.getLine());}
    (expr = expression
    {$returnStatementRet = new ReturnStmt();
    $returnStatementRet.setLine($l.getLine());
    $returnStatementRet.setReturnedExpr($expr.exprRet);})?;

//todo
//done
//correct_
assignmentStatement returns [AssignmentStmt assignmentStatementRet]:
    lval = orExpression l = ASSIGN rval = expression
    {$assignmentStatementRet = new AssignmentStmt($lval.orExpressionRet, $rval.exprRet);
     $assignmentStatementRet.setLine($l.getLine());}
    ;

//todo
loopStatement returns [EachStmt loopStatementRet] locals [Expression list]:
    ((aExpr = accessExpression
    {$list = $aExpr.exprRet;}
    ) | (l = LPAR lex = expression DOT DOT rex = expression RPAR
    {$list = new RangeExpression($lex.exprRet, $rex.exprRet);
    $list.setLine($l.getLine())}
    ))DOT l1=EACH DO BAR id = identifier BAR b = body
    {
      $loopStatementRet = new EachStmt($id.idRet, $list);
      $loopStatementRet.setBody($b.bodyRet);
      $loopStatementRet.setLine($l1.getLine());
    };

//todo
//done
//correct
expression returns [Expression exprRet]:
    lexpr = ternaryExpression {$exprRet = $lexpr.ternaryExpressionRet;}
    ( l = ASSIGN e1 = expression
    { BinaryOperator opr = BinaryOperator.assign;
      $exprRet = new BinaryExpression($exprRet , $e1.exprRet, opr);
      $exprRet.setLine($l.getLine());}
    )? (DOT inc=INCLUDE LPAR oe=orExpression RPAR
    {$exprRet = new SetInclude($exprRet, $oe.orExpressionRet);}
    )?;

//todo
//done
//correct
ternaryExpression returns [Expression ternaryExpressionRet]:
    c = orExpression {$ternaryExpressionRet = $c.orExpressionRet;}
    (l = TIF e1 = ternaryExpression TELSE e2 = ternaryExpression
    { TernaryOperator opr = TernaryOperator.ternary;
      $ternaryExpressionRet = new TernaryExpression($ternaryExpressionRet, $e1.ternaryExpressionRet, $e2.ternaryExpressionRet);
      $ternaryExpressionRet.setLine($l.getLine());
    }
    )?;

//todo
//done
//correct
orExpression returns [Expression orExpressionRet]:
    lexpr = andExpression {$orExpressionRet = $lexpr.andExpressionRet;}
    ( l = OR rexpr = andExpression
     { BinaryOperator opr = BinaryOperator.or;
       $orExpressionRet = new BinaryExpression($orExpressionRet, $rexpr.andExpressionRet, opr);
       $orExpressionRet.setLine($l.getLine());}
    )*;

//todo
//done
//correct
andExpression returns [Expression andExpressionRet]:
    lexpr = equalityExpression {$andExpressionRet = $lexpr.equalityExpressionRet;}
    (l = AND rexpr = equalityExpression
    { BinaryOperator opr = BinaryOperator.and;
     $andExpressionRet = new BinaryExpression($andExpressionRet, $rexpr.equalityExpressionRet, opr);
     $andExpressionRet.setLine($l.getLine());}
    )*;

//todo
//done
//correct
equalityExpression returns [Expression equalityExpressionRet]:
    lexpr = relationalExpression {$equalityExpressionRet = $lexpr.relationalExpressionRet;}
    (l = EQUAL rexpr = relationalExpression
    { BinaryOperator opr = BinaryOperator.eq;
      $equalityExpressionRet = new BinaryExpression($equalityExpressionRet, $rexpr.relationalExpressionRet, opr);
      $equalityExpressionRet.setLine($l.getLine());}
    )*;

//todo
///done
//correct
relationalExpression returns [Expression relationalExpressionRet] locals [BinaryOperator op, int line]:
    lexpr = additiveExpression {$relationalExpressionRet = $lexpr.exprRet;}
    (( op1 = GREATER_THAN {
     $op = BinaryOperator.gt;
     $line = $op1.getLine();
     }
     | op2 = LESS_THAN
      { $op = BinaryOperator.lt;
        $line = $op2.getLine();}
     ) rexpr = additiveExpression
     { $relationalExpressionRet = new BinaryExpression($relationalExpressionRet,$rexpr.exprRet,$op);
       $relationalExpressionRet.setLine($line);}
     )*;

//todo
//done
//correct_
additiveExpression returns [Expression exprRet] locals [BinaryOperator op, int line]:
    lexpr = multiplicativeExpression {$exprRet = $lexpr.exprRet;}
    ((op1 = PLUS
    { $op = BinaryOperator.add;
      $line = $op1.getLine();}
    | op2 = MINUS
     {$op = BinaryOperator.sub;
     $line = $op2.getLine();}
    ) rexpr = multiplicativeExpression
     { $exprRet = new BinaryExpression($exprRet,$rexpr.exprRet,$op);
        $exprRet.setLine($line);}
    )*;

//todo
//done
//correct_
multiplicativeExpression returns [Expression exprRet] locals [BinaryOperator op, int line]:
    lexpr = preUnaryExpression {$exprRet = $lexpr.exprRet;}
    ((op1 = MULT
     { $op = BinaryOperator.mult;
       $line = $op1.getLine();}
    |op2 = DIVIDE
     { $op = BinaryOperator.div;
     $line = $op2.getLine();}
    ) rexpr = preUnaryExpression
    { $exprRet = new BinaryExpression($exprRet,$rexpr.exprRet,$op);
      $exprRet.setLine($line);}
    )*;

//todo
//done
//correct_
preUnaryExpression returns[Expression exprRet] locals[UnaryOperator op, int line]:
    ((op1 = NOT
     {$op = UnaryOperator.not;
     $line = $op1.getLine();}
    | op2 = MINUS
      {$op = UnaryOperator.minus;
      $line = $op2.getLine();}
    ) expr1 = preUnaryExpression
     {$exprRet = new UnaryExpression($expr1.exprRet, $op);
    $exprRet.setLine($line);}
    ) | expr2=postUnaryExpression  {$exprRet = $expr2.exprRet;} ;

//todo
//done
//correct_
postUnaryExpression returns [Expression exprRet] locals[UnaryOperator op, int line]:
    expr = accessExpression  {$exprRet = $expr.exprRet;}

    (( op1 = INC
     {$exprRet = new UnaryExpression($expr.exprRet, UnaryOperator.postinc);
          $exprRet.setLine($op1.getLine());}
     |op2 = DEC
     {$exprRet = new UnaryExpression($expr.exprRet, UnaryOperator.postdec);
      $exprRet.setLine($op2.getLine());}
      ))?;

//todo: Check correctness of returned class types
//todo: Does initialize need a different printing procedere in place?
//todo: "NEW" return value needs a look
//correct_
accessExpression returns [Expression exprRet]:
    e1 = otherExpression {$exprRet = $e1.otherExpressionRet;}
    (
    (lpar=LPAR m = methodArgs
    {
        if(!($exprRet instanceof NewClassInstance)){
            $exprRet = new MethodCall($exprRet, $m.methodArgsRet);
            $exprRet.setLine($lpar.getLine());
        }else{
            ((NewClassInstance) $exprRet).setArgs($m.methodArgsRet);
        }
    }
     RPAR)|
     (DOT
     ( id = identifier
     { $exprRet = new ObjectMemberAccess($exprRet,$id.idRet);
       $exprRet.setLine($id.idRet.getLine());
     }|
     nw=NEW
     {$exprRet = new NewClassInstance(new ClassType((Identifier)$exprRet));
     $exprRet.setLine($nw.getLine());} |
     INITIALIZE)
     )
     )*

    ((DOT (id = identifier
     { $exprRet = new ObjectMemberAccess($exprRet,$id.idRet);
     $exprRet.setLine($id.line);
     })) | (l = LBRACK e = expression
     { $exprRet = new ArrayAccessByIndex($exprRet,$e.exprRet);
      $exprRet.setLine($l.getLine()); }
      RBRACK))*;

//todo
//done
//correct
otherExpression returns [Expression otherExpressionRet]:
     e1 = SELF
     { $otherExpressionRet = new SelfClass();
       $otherExpressionRet.setLine($e1.getLine());}
    |e2 = class_identifier {$otherExpressionRet = $e2.idRet;}
    |e3 = value {$otherExpressionRet = $e3.valueRet;}
    |e4 = identifier {$otherExpressionRet = $e4.idRet;}
    |e5 = setNew {$otherExpressionRet = $e5.setNewRet;}
    | LPAR e6 = expression RPAR {$otherExpressionRet = $e6.exprRet;}
    ;

//todo: Needs a check
//done
//changed
setNew returns [Expression setNewRet] locals[ArrayList<Expression> args]:
    {$args = new ArrayList<>();}
    SET DOT l = NEW LPAR (LPAR e1 = orExpression {$args.add($e1.orExpressionRet);}
     (COMMA e2 = orExpression {$args.add($e2.orExpressionRet);}
     )* RPAR)?  RPAR
     {
     $setNewRet = new SetNew($args);
     $setNewRet.setLine($l.getLine());
     };

//todo
//done
//correct_
value returns [Value valueRet]:
    b = boolValue {$valueRet = $b.boolValueRet;}
    | i = INT_VALUE
    { $valueRet = new IntValue($i.int);
      $valueRet.setLine($i.getLine());};

//todo
//done
//correct_
boolValue returns [BoolValue boolValueRet]:
    t=TRUE
    {$boolValueRet = new BoolValue(true);
     $boolValueRet.setLine($t.getLine());}
    | f=FALSE
    {$boolValueRet = new BoolValue(false);
     $boolValueRet.setLine($f.getLine());};

//todo
//correct
class_identifier returns [Identifier idRet]:
    c  = CLASS_IDENTIFIER
    {$idRet = new Identifier($c.text);
    $idRet.setLine($c.getLine());};

//todo
//done
//correct
identifier returns [Identifier idRet, int line]:
    id = IDENTIFIER
        {$idRet = new Identifier($id.text);
         $idRet.setLine($id.getLine());
         $line = $id.getLine();}
    ;

//todo
//done
//correct
type returns [Type typeRet]:
    INT {$typeRet = new IntType();}
    | BOOL {$typeRet = new BoolType();}
    | arr = array_type {$typeRet = $arr.array_typeRet;}
    | fptr = fptr_type {$typeRet = $fptr.fptr_typrRet;}
    | set = set_type {$typeRet = $set.setTypeRet;}
    | class1 = class_identifier {$typeRet = new ClassType($class1.idRet);}  ;


//todo: use expression instead of INT_VAL for dimensions
//done
//correct
array_type returns [ArrayType array_typeRet] locals [Type t, ArrayList<Expression> args]:
    {$args = new ArrayList<>();}
    (INT {$t = new IntType();}
    | BOOL {$t = new BoolType();}
    | c = class_identifier {$t = new ClassType($c.idRet);})
    (LBRACK e = expression { $args.add($e.exprRet);} RBRACK)+
    {$array_typeRet = new ArrayType($t, $args);};

//todo
//done
//correct
fptr_type returns [FptrType fptr_typrRet]:
    { ArrayList<Type> args = new ArrayList<>(); }
    FPTR LESS_THAN (VOID | (t1=type {args.add($t1.typeRet); }
    (COMMA t2=type { args.add($t2.typeRet); } )* ))
    ARROW (t3 = type {$fptr_typrRet = new FptrType(args, $t3.typeRet);}
    | VOID {$fptr_typrRet = new FptrType(args, new VoidType());}) GREATER_THAN;

//todo
//done
//correct
set_type returns [SetType setTypeRet]:
    SET LESS_THAN (INT) GREATER_THAN
    {$setTypeRet = new SetType();};


LINE_BREAK: ('//\n') -> skip;

CLASS: 'class';
PUBLIC: 'public';
PRIVATE: 'private';
INITIALIZE: 'initialize';
NEW: 'new';
SELF: 'self';


RETURN: 'return';
VOID: 'void';


DELETE: 'delete';
INCLUDE: 'include';
ADD: 'add';
MERGE: 'merge';
PRINT: 'print';


IF: 'if';
ELSE: 'else';
ELSIF: 'elsif';

PLUS: '+';
MINUS: '-';
MULT: '*';
DIVIDE: '/';
INC: '++';
DEC: '--';

EQUAL: '==';
GREATER_THAN: '>';
LESS_THAN: '<';

ARROW: '->';
BAR: '|';

AND: '&&';
OR: '||';
NOT: '!';

TIF: '?';
TELSE: ':';

TRUE: 'true';
FALSE: 'false';
NULL: 'null';

BEGIN: '=begin';
END: '=end';

INT: 'int';
BOOL: 'bool';
FPTR: 'fptr';
SET: 'Set';
EACH: 'each';
DO: 'do';

ASSIGN: '=';
SHARP: '#';
LPAR: '(';
RPAR: ')';
LBRACK: '[';
RBRACK: ']';
LBRACE: '{';
RBRACE: '}';

COMMA: ',';
DOT: '.';
SEMICOLON: ';' -> skip;
NEWLINE: '\n';

INT_VALUE: '0' | [1-9][0-9]*;
IDENTIFIER: [a-z_][A-Za-z0-9_]*;
CLASS_IDENTIFIER: [A-Z][A-Za-z0-9_]*;

COMMENT: '#' .*? '\n' -> skip;
MLCOMMENT: ('=begin' .*? '=end') -> skip;
WS: ([ \t\r]) -> skip;
