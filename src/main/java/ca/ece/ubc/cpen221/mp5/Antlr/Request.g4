grammar Request;

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
ineq : GT | GTE | LT | LTE | EQ;

WS : [ \t\r\n]+ -> skip;



line : (text)*;
text: ANYTOKEN | WORD;
ANYTOKEN : .;
//word has to be put at the very bottom or else it's going to get matched before the
//other tokens
WORD: [a-zA-Z]+;

