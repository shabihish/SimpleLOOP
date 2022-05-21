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
     import main.util.*;
 }

simpleLOOP returns [Program simpleLOOPProgram]:
    NEWLINE* p = program {$simpleLOOPProgram = $p.programRet;} NEWLINE* EOF;

program returns[Program programRet]:
    {$programRet = new Program();
     int line = 1;
     $programRet.setLine(line);}
    (v = varDecStatement NEWLINE+
    {
        for (VariableDeclaration varDec: $v.varDecStmtRet)
            $programRet.addGlobalVariable(varDec);
    })*
    (c = classDeclaration NEWLINE+ {$programRet.addClass($c.classDeclarationRet);})*;

constructor returns [ConstructorDeclaration constructorRet]
    : PUBLIC i = INITIALIZE
    {
        $constructorRet = new ConstructorDeclaration();
        var newInit = new Identifier("initialize");
        newInit.setLine($i.getLine());
        $constructorRet.setLine(newInit.getLine());
    }
    args = methodArgsDec NEWLINE*
    { $constructorRet.setArgs($args.argsRet); }
    b = methodBody
    {
        $constructorRet.setLocalVars($b.localVars);
        $constructorRet.setBody($b.statements);
    };


classDeclaration returns [ ClassDeclaration classDeclarationRet]
    : cl=CLASS name=class_identifier
    {
        $classDeclarationRet = new ClassDeclaration($name.idRet);
        $classDeclarationRet.setLine($cl.getLine());
    }
    (LESS_THAN parentName=class_identifier
    { $classDeclarationRet.setParentClassName($parentName.idRet); }
    )?
    NEWLINE* ((LBRACE NEWLINE+ (mf=field_decleration
                                 {
                                     for (Declaration field : $mf.decRet) {
                                         if (field instanceof FieldDeclaration)
                                             $classDeclarationRet.addField((FieldDeclaration) field);
                                         else if (field instanceof ConstructorDeclaration){
                                             $classDeclarationRet.setConstructor((ConstructorDeclaration) field);
                                         }
                                         else if (field instanceof MethodDeclaration) {
                                             $classDeclarationRet.addMethod((MethodDeclaration) field);
                                         }
                                     }
                                 } NEWLINE+)+ RBRACE)
    | (sf=field_decleration
        {
            for (Declaration field : $sf.decRet) {
             if (field instanceof FieldDeclaration)
                 $classDeclarationRet.addField((FieldDeclaration) field);
             else if (field instanceof ConstructorDeclaration){
                 $classDeclarationRet.setConstructor((ConstructorDeclaration) field);
             }
             else if (field instanceof MethodDeclaration) {
                 $classDeclarationRet.addMethod((MethodDeclaration) field);
             }

}
        }));

field_decleration returns [ArrayList<Declaration> decRet]:
    { $decRet = new ArrayList<>(); }
    ((( access=(PUBLIC | PRIVATE)
       (v=varDecStatement
       {
            for (VariableDeclaration varDec: $v.varDecStmtRet) {
                var newField = new FieldDeclaration(varDec, $access.toString() == "public" ? true : false );
                newField.setLine(varDec.getLine());
                $decRet.add(newField);
            }
       }
       | m=method
       {
            var newMethod = $m.methodDecRet;
            newMethod.setPrivate($access.toString() == "public" ? false : true);
            $decRet.add(newMethod);
       }))
       | c=constructor
       {
           $decRet.add($c.constructorRet);
        })
    );

method returns [MethodDeclaration methodDecRet]
    locals [Type returnType]
    : (t=type { $returnType = $t.typeRet; }
     | VOID { $returnType = new VoidType(); }
     ) name=identifier
      args = methodArgsDec NEWLINE*
      b=methodBody
      {
        $methodDecRet = new MethodDeclaration($name.idRet, $returnType, false);
        $methodDecRet.setLine($name.idRet.getLine());
        $methodDecRet.setArgs($args.argsRet);
        $methodDecRet.setLocalVars($b.localVars);
        $methodDecRet.setBody($b.statements);
      };

methodBody returns [ArrayList<VariableDeclaration> localVars, ArrayList<Statement> statements]
    :
    (
    {
        $localVars = new ArrayList<>();
        $statements = new ArrayList<>();
    }
    LBRACE NEWLINE+
    (v=varDecStatement NEWLINE+
    {
        for (VariableDeclaration varDec : $v.varDecStmtRet)
            $localVars.add(varDec);
    })*
    (s=singleStatement NEWLINE+
    { $statements.add($s.singleRet); })* RBRACE)
    |
    {
        $localVars = new ArrayList<>();
        $statements = new ArrayList<>();
    }
    (
    (v=varDecStatement
    {
        for (VariableDeclaration varDec : $v.varDecStmtRet)
            $localVars.add(varDec);
    }
    )
    | (s=singleStatement
    { $statements.add($s.singleRet); })
    );

methodArgsDec returns [ArrayList<ArgPair> argsRet]
    : LPAR
    { $argsRet = new ArrayList<>(); }
    (
        arg=argDec { $argsRet.add(new ArgPair($arg.arg)); }
        (
            (ASSIGN oe=orExpression {
                                        $argsRet.get(0).setDefaultValue($oe.orExprRet);
                                    }
            )
            | (COMMA arg=argDec { $argsRet.add(new ArgPair($arg.arg)); })*
        )
        (
            COMMA arg=argDec
            ASSIGN oe=orExpression
            { $argsRet.add(new ArgPair($arg.arg, $oe.orExprRet)); }
        )*
    )?
    RPAR ;

argDec returns [VariableDeclaration arg]
    : typ=type
     name=identifier
     { $arg = new VariableDeclaration($name.idRet, $typ.typeRet);
       $arg.setLine($name.idRet.getLine()); };

methodArgs returns [ArrayList<Expression> methodCallArgsRet]
    : { $methodCallArgsRet = new ArrayList<>(); }
      (e1=expression
      { $methodCallArgsRet.add($e1.expRet); }
      (COMMA e2=expression
      { $methodCallArgsRet.add($e2.expRet); }
      )*
      )?;

body returns [Statement statementRet]:
     (bs=blockStatement
      { $statementRet = $bs.blockRet; }
      | (NEWLINE+ ss=singleStatement { $statementRet = $ss.singleRet; }));

blockStatement returns [BlockStmt blockRet]:
    lb=LBRACE
     { $blockRet = new BlockStmt();
        $blockRet.setLine($lb.getLine());}
    NEWLINE+ (ss=singleStatement
                { $blockRet.addStatement($ss.singleRet);}
                 NEWLINE+)* RBRACE;

singleStatement returns [Statement singleRet]:
    i=ifStatement
    { $singleRet = $i.ifRet; }
    | p=printStatement
    { $singleRet = $p.printRet;}
    | m=methodCallStmt
    { $singleRet = $m.methRet;}
    | r=returnStatement
    { $singleRet = $r.returnRet;}
    | as=assignmentStatement
    { $singleRet = $as.assignRet;}
    | l=loopStatement
    { $singleRet = $l.loopRet;}
    | ad=addStatement
    { $singleRet = $ad.addRet;}
    | me=mergeStatement
    { $singleRet = $me.mergeRet;}
    | d=deleteStatement
    { $singleRet = $d.deleteRet;}
    ;

addStatement returns [SetAdd addRet]:
    exp=expression DOT add=ADD LPAR orexp=orExpression RPAR
    {
        $addRet = new SetAdd($exp.expRet, $orexp.orExprRet);
        $addRet.setLine($add.getLine());
    };

mergeStatement returns [SetMerge mergeRet]
    locals [ArrayList<Expression> expArgs]:
    exp=expression DOT merge=MERGE LPAR orexp=orExpression
    {
        $expArgs = new ArrayList<>();
        $expArgs.add($orexp.orExprRet);
    }
    (COMMA orexp=orExpression { $expArgs.add($orexp.orExprRet); })*
    {
        $mergeRet = new SetMerge($exp.expRet, $expArgs);
        $mergeRet.setLine($merge.getLine());
    } RPAR;

deleteStatement returns [SetDelete deleteRet]:
    exp=expression DOT delete=DELETE LPAR orexp=orExpression RPAR
    {
        $deleteRet = new SetDelete($exp.expRet, $orexp.orExprRet);
        $deleteRet.setLine($delete.getLine());
    };

varDecStatement returns [ArrayList<VariableDeclaration> varDecStmtRet]:
    t=type name=identifier
    {
     $varDecStmtRet = new ArrayList<>();
     var newDec = new VariableDeclaration($name.idRet, $t.typeRet);
     newDec.setLine($name.idRet.getLine());
     $varDecStmtRet.add(newDec);
    }
    (COMMA n=identifier {
                            var extraDec = new VariableDeclaration($n.idRet, $t.typeRet);
                            extraDec.setLine($n.idRet.getLine());
                            $varDecStmtRet.add(extraDec);
                        })*;

ifStatement returns [ConditionalStmt ifRet]:
    i=IF c=condition b=body
    {
        $ifRet = new ConditionalStmt($c.conditionRet, $b.statementRet);
        $ifRet.setLine($i.getLine());
    }
    (ei=elsifStatement { $ifRet.addElsif($ei.elsifRet); })*
    (e=elseStatement { $ifRet.setElseBody($e.elseRet); })?;

elsifStatement returns [ElsifStmt elsifRet]:
     NEWLINE* el=ELSIF c=condition b=body
     {
        $elsifRet = new ElsifStmt($c.conditionRet, $b.statementRet);
        $elsifRet.setLine($el.getLine());
      };

condition returns [Expression conditionRet] :
    (LPAR ex=expression {$conditionRet = $ex.expRet;} RPAR) | (ex=expression {$conditionRet = $ex.expRet;});

elseStatement returns [Statement elseRet]:
     NEWLINE* ELSE b=body {$elseRet = $b.statementRet;};

printStatement returns [PrintStmt printRet] :
    p=PRINT LPAR e=expression
    {
        $printRet = new PrintStmt($e.expRet);
        $printRet.setLine($p.getLine());
    }
    RPAR;

methodCallStmt returns [MethodCallStmt methRet]
    locals [Expression inst, MethodCall methCallExpr]:
    ax=accessExpression { $inst = $ax.accessExprRet; }
    (DOT (init=INITIALIZE {$inst = new ObjectMemberAccess($inst, new Identifier($init.toString())); }
            | id=identifier {$inst = new ObjectMemberAccess($inst, new Identifier($id.idRet.toString())); }))*
    (l=LPAR args=methodArgs {$methCallExpr = new MethodCall($inst, $args.methodCallArgsRet);
                             $methCallExpr.setLine($l.getLine());} RPAR)
    {
        $methRet = new MethodCallStmt($methCallExpr);
        $methRet.setLine($l.getLine());
    };

returnStatement returns [ReturnStmt returnRet]:
    r=RETURN { $returnRet = new ReturnStmt();
               $returnRet.setLine($r.getLine()); }
    (e=expression { $returnRet.setReturnedExpr($e.expRet); })?;

assignmentStatement returns [AssignmentStmt assignRet]:
    e=orExpression ((a=ASSIGN ex=expression
    {
        $assignRet = new AssignmentStmt($e.orExprRet, $ex.expRet);
        $assignRet.setLine($a.getLine());
    }) );

loopStatement returns [EachStmt loopRet]
    locals [Expression list]:
    (
    (ax=accessExpression { $list = $ax.accessExprRet; })
    | (
        l=LPAR el=expression DOT DOT er=expression RPAR
        { var rangeExpr = new RangeExpression($el.expRet, $er.expRet);
          rangeExpr.setLine($l.getLine());
          $list = rangeExpr;
        }
      )
    ) DOT each=EACH DO BAR name=identifier BAR
    b=body
    {
        $loopRet = new EachStmt($name.idRet, $list);
        $loopRet.setBody($b.statementRet);
        $loopRet.setLine($each.getLine());
     };

expression returns[Expression expRet]:
    tex=ternaryExpression {$expRet = $tex.ternaryExprRet;}
    (op=ASSIGN ex=expression{
        BinaryOperator opr = BinaryOperator.assign;
        $expRet = new BinaryExpression($expRet, $ex.expRet, opr);
        $expRet.setLine($op.getLine());
    })? (DOT inc=INCLUDE LPAR oe=orExpression RPAR { $expRet = new SetInclude($expRet, $oe.orExprRet);
                                                     $expRet.setLine($inc.getLine()); })?;

ternaryExpression returns [Expression ternaryExprRet]:
    oex=orExpression { $ternaryExprRet = $oex.orExprRet; }
    (q=TIF ttex=ternaryExpression TELSE ftex=ternaryExpression{
          TernaryOperator opr = TernaryOperator.ternary;
          $ternaryExprRet = new TernaryExpression($ternaryExprRet, $ttex.ternaryExprRet, $ftex.ternaryExprRet);
          $ternaryExprRet.setLine($q.getLine());
      }
    )?;

orExpression returns[Expression orExprRet]:
    expl = andExpression {$orExprRet = $expl.andExprRet;}
    (op = OR expr = andExpression{
        BinaryOperator opr = BinaryOperator.or;
        $orExprRet = new BinaryExpression($orExprRet, $expr.andExprRet, opr);
        $orExprRet.setLine($op.getLine());
    })*
    ;

andExpression returns[Expression andExprRet]:
    expl = equalityExpression {$andExprRet = $expl.equalityExprRet;}
    (op = AND expr = equalityExpression{
        BinaryOperator opr = BinaryOperator.and;
        $andExprRet = new BinaryExpression($andExprRet, $expr.equalityExprRet, opr);
        $andExprRet.setLine($op.getLine());
    })*;

equalityExpression returns[Expression equalityExprRet]:
    expl = relationalExpression {$equalityExprRet = $expl.relationalExprRet;}
    (op = EQUAL expr = relationalExpression{
        BinaryOperator opr = BinaryOperator.eq;
        $equalityExprRet = new BinaryExpression($equalityExprRet, $expr.relationalExprRet, opr);
        $equalityExprRet.setLine($op.getLine());
    })*;

relationalExpression returns [Expression relationalExprRet]
    locals [BinaryOperator op, int line]:
    l = additiveExpression
    {$relationalExprRet = $l.expr;}
    ((op1 = GREATER_THAN
    {$op = BinaryOperator.gt;
    $line = $op1.getLine();}
    | op2 = LESS_THAN
    {$op = BinaryOperator.lt;
     $line = $op2.getLine();}
    ) r = additiveExpression
    {$relationalExprRet = new BinaryExpression($relationalExprRet,$r.expr,$op);
     $relationalExprRet.setLine($line);}
    )*;

additiveExpression returns [Expression expr]
    locals [BinaryOperator op, int line]:
    l = multiplicativeExpression
    {$expr = $l.expr;}
    ((op1 = PLUS
    {$op = BinaryOperator.add;
     $line = $op1.getLine();}
    | op2 = MINUS
    {$op = BinaryOperator.sub;
     $line = $op2.getLine();}
    ) r = multiplicativeExpression
    {$expr = new BinaryExpression($expr,$r.expr,$op);
     $expr.setLine($line);}
    )*;

multiplicativeExpression returns [Expression expr]
    locals [BinaryOperator op, int line]:
    l = preUnaryExpression
    {$expr = $l.expr;}
    ((op1 = MULT
    {$op = BinaryOperator.mult;
     $line = $op1.getLine();}
    | op2 = DIVIDE
    {$op = BinaryOperator.div;
     $line = $op2.getLine();}
    ) r = preUnaryExpression
    {$expr = new BinaryExpression($expr,$r.expr,$op);
    $expr.setLine($line);}
    )*;

preUnaryExpression returns [Expression expr]
    locals[UnaryOperator op, int line]:
    ((op1 = NOT
    {$op = UnaryOperator.not;
     $line = $op1.getLine();}
    | op2 = MINUS
    {$op = UnaryOperator.minus;
     $line = $op2.getLine();}
    ) pre = preUnaryExpression
    {$expr = new UnaryExpression($pre.expr, $op);
     $expr.setLine($line);}
    ) | post = postUnaryExpression
    {$expr = $post.postUnaryExprRet;}
    ;

postUnaryExpression returns[Expression postUnaryExprRet]:
    ae=accessExpression { $postUnaryExprRet = $ae.accessExprRet; }
    (
    (postinc=INC
    {
        UnaryOperator op = UnaryOperator.postinc;
        $postUnaryExprRet = new UnaryExpression($postUnaryExprRet, op);
        $postUnaryExprRet.setLine($postinc.getLine());
    }
    | postdec=DEC
    {
        UnaryOperator op = UnaryOperator.postdec;
        $postUnaryExprRet = new UnaryExpression($postUnaryExprRet, op);
        $postUnaryExprRet.setLine($postdec.getLine());
    }
    )
    )?;

accessExpression returns[Expression accessExprRet]:
    oe=otherExpression { $accessExprRet = $oe.otherExprRet; }
    (
    (l=LPAR m=methodArgs
    {
        if ($accessExprRet instanceof NewClassInstance)
            ((NewClassInstance) $accessExprRet).setArgs($m.methodCallArgsRet);
        else {
            $accessExprRet = new MethodCall($accessExprRet, $m.methodCallArgsRet);
            $accessExprRet.setLine($l.line);
        }
    }
    RPAR)
    | (DOT (name=identifier
            {
                $accessExprRet = new ObjectMemberAccess($accessExprRet, $name.idRet);
                $accessExprRet.setLine($name.idRet.getLine());
            }
        | n=NEW
            {
                var classType = new ClassType((Identifier) $accessExprRet);
                $accessExprRet = new NewClassInstance(classType);
                $accessExprRet.setLine($n.getLine());
            }
       ))
    )*
    (
    (DOT (name=identifier
            {
                $accessExprRet = new ObjectMemberAccess($accessExprRet, $name.idRet);
                $accessExprRet.setLine($name.idRet.getLine());
            }
         ))
    | (l=LBRACK index=expression RBRACK
            {
                $accessExprRet = new ArrayAccessByIndex($accessExprRet, $index.expRet);
                $accessExprRet.setLine($l.getLine());
            }
        )
    )*;

otherExpression returns [Expression otherExprRet]:
    s=SELF
    {
        $otherExprRet = new SelfClass();
        $otherExprRet.setLine($s.getLine());
    }
    | cid=class_identifier
    { $otherExprRet = $cid.idRet; }
    | v=value
    { $otherExprRet = $v.valuesRet; }
    | id=identifier
    { $otherExprRet = $id.idRet; }
    | sn=setNew
    { $otherExprRet = $sn.setNewRet; }
    | LPAR e=expression RPAR
    { $otherExprRet = $e.expRet; }
    ;

setNew returns [Expression setNewRet]
    locals [ArrayList<Expression> args]:
    { $args = new ArrayList<>();}
    SET DOT n=NEW LPAR (LPAR oe=orExpression { $args.add($oe.orExprRet);}
                       (COMMA oex=orExpression { $args.add($oex.orExprRet);})* RPAR)?
               {
                $setNewRet = new SetNew($args);
                $setNewRet.setLine($n.getLine());
               }
               RPAR;

value returns[Value valuesRet]:
    b=boolValue
    {
        $valuesRet = new BoolValue($b.boolValueRet);
        $valuesRet.setLine($b.line);
    }
    | i=INT_VALUE
    {
        $valuesRet = new IntValue($i.int);
        $valuesRet.setLine($i.getLine());
    }
    ;

boolValue returns[boolean boolValueRet, int line]:
    t=TRUE
    {
        $boolValueRet = true;
        $line = $t.getLine();
    }
    | f=FALSE
    {
        $boolValueRet = false;
        $line = $f.getLine();
    }
    ;

class_identifier returns[Identifier idRet, int line]:
    cid=CLASS_IDENTIFIER
    {
        $idRet = new Identifier($cid.text);
        $idRet.setLine($cid.getLine());
        $line = $cid.getLine();
    }
    ;

identifier returns[Identifier idRet, int line]:
    id=IDENTIFIER
    {
        $idRet = new Identifier($id.text);
        $idRet.setLine($id.getLine());
        $line = $id.getLine();
    }
    ;

type returns [Type typeRet]:
    INT
    { $typeRet = new IntType(); }
    | BOOL
    { $typeRet = new BoolType(); }
    | arr=array_type
    { $typeRet = $arr.arrTypeRet; }
    | f=fptr_type
    { $typeRet = $f.fptrTypeRet; }
    | s=set_type
    { $typeRet = $s.setTypeRet; }
    | cid=class_identifier
    { $typeRet = new ClassType($cid.idRet); }
    ;

array_type returns [ArrayType arrTypeRet]
    locals[Type t, ArrayList<Expression> dims]:
    { $dims = new ArrayList<>(); }
    (INT { $t = new IntType(); }
    | BOOL { $t = new BoolType(); }
    | cid=class_identifier { $t = new ClassType($cid.idRet); }
    )
    (LBRACK ex=expression RBRACK { $dims.add($ex.expRet); })+
    { $arrTypeRet = new ArrayType($t, $dims); };

fptr_type returns[FptrType fptrTypeRet]:
    { ArrayList<Type> args = new ArrayList<>(); }
    FPTR LESS_THAN (VOID
    | (t1 = type { args.add($t1.typeRet); } (COMMA t2 = type { args.add($t2.typeRet); })* ))
    ARROW (t3 = type {$fptrTypeRet = new FptrType(args, $t3.typeRet);}
    | VOID {$fptrTypeRet = new FptrType(args, new VoidType());}) GREATER_THAN;

set_type returns [SetType setTypeRet]:
    SET LESS_THAN (INT) GREATER_THAN { $setTypeRet = new SetType(); };


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
