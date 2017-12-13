grammar Request;

//TODO: ADD TO CLASSPATH
@header {
}

LPAREN : '(';
RPAREN : ')';
OR : '||';
AND : '&&';
NUM : [1-5];
GT : '>';
GTE : '>=';
LT : '<';
LTE : '<=';
EQ : '=';
PRICE: 'price';
RATING: 'rating';
NAME: 'name';
CATEGORY: 'category';
IN: 'in';


req : andExpr EOF;
andExpr :  atom ( AND atom )*;
atom :  in | category | rating | price | name | (LPAREN  orExpr  RPAREN);
orExpr : andExpr ( OR andExpr)*;

in : IN LPAREN line RPAREN;
category : CATEGORY LPAREN line RPAREN;
name : NAME LPAREN line RPAREN;
rating : RATING ineq NUM;
price : PRICE ineq NUM;

ineq returns [String value]:
    GT {$value = $GT.text;}
    | GTE {$value = $GTE.text;}
    | LT {$value = $LT.text;}
    | LTE {$value = $LTE.text;}
    | EQ {$value = $EQ.text;}
    ;

WS : [ \t\r\n]+ -> skip;

//TODO: HOW DO I INITALIZE VARIABLES
line returns [String value]:
    (phrase)* {$value += $phrase.value;}
    ;

phrase returns [String value]:
    ANYTOKEN { $value = $ANYTOKEN.text;}
    | WORD {$value = $WORD.text;}
    ;

ANYTOKEN :
    .
    ;

//word has to be put at the very bottom or else it's going to get matched before the
//other tokens (I think)
WORD:
    [a-zA-Z]+
    ;

