grammar Request;

//TODO: ADD TO CLASSPATH
@header {
    package ca.ece.ubc.cpen221.mp5.Antlr;
    import java.util.*;
    import ca.ece.ubc.cpen221.mp5.Database.YelpDataBase;
}

@parser::members{
    YelpDataBase ydb;

    public RequestParser(TokenStream input, YelpDataBase ydb) {
        this(input);
        this.ydb = ydb;
    }
}

/**
    @param: String query: represents the request that was given to the ydb server as string
    @param: YelpDataBase ydb: this is the database object on which we perform the query on

    This grammar file is responsible for parsing the given query strings and returning a set of
    business ids that represent all the restaurants that match the query.

    It splits the request into individual "atoms", and each individual atom calls the respective
    ydb method that has been set up to interact specifically with this parser.
    From there, the returned set of strings will either be intersected or unioned with the sets
    created from other inidividual atoms based on either || or &&.
*/

//head of parser
req returns [Set<String> restaurants]:
    arg1=andExpr {$restaurants = $arg1.restaurants;}
    (OR arg2=andExpr {$restaurants.addAll($arg2.restaurants);})* EOF
    ;

andExpr returns [Set<String> restaurants]:
    arg1=atom {$restaurants = $arg1.restaurants;}
    ( AND arg2=atom {$restaurants.retainAll($arg2.restaurants);})*
    ;

atom returns [Set<String> restaurants] :
    in {$restaurants = $in.restaurants;}
    | category {$restaurants = $category.restaurants;}
    | rating {$restaurants = $rating.restaurants;}
    | price {$restaurants = $price.restaurants;}
    | name {$restaurants = $name.restaurants;}
    | (LPAREN  orExpr {$restaurants = $orExpr.restaurants;} RPAREN)
    ;

orExpr returns [Set<String> restaurants]:
    arg1=andExpr {$restaurants = $arg1.restaurants;}
    ( OR arg2=andExpr)* {$restaurants.addAll($arg2.restaurants);};

in returns [Set<String> restaurants]:
    IN LPAREN line RPAREN {$restaurants = ydb.inAtom($line.value);}
    ;

category returns [Set<String> restaurants]:
    CATEGORY LPAREN line RPAREN {$restaurants = ydb.categoryAtom($line.value);}
    ;

name returns [Set<String> restaurants]:
    NAME LPAREN line RPAREN {$restaurants = ydb.nameAtom($line.value);}
    ;

rating returns [Set<String> restaurants]:
    RATING ineq NUM {$restaurants = ydb.ratingAtom($ineq.v, Integer.parseInt($NUM.text));}
    ;

price returns [Set<String> restaurants]:
    PRICE ineq NUM {$restaurants = ydb.priceAtom($ineq.v, Integer.parseInt($NUM.text));}
    ;

ineq returns [String v]:
    GT {$v = $GT.text;}
    | GTE {$v = $GTE.text;}
    | LT {$v = $LT.text;}
    | LTE {$v = $LTE.text;}
    | EQ {$v = $EQ.text;}
    ;
line returns [String value] @init{$value = ".*";}
    :
    (phrase)* {$value += $phrase.v + ".*";}
    ;

phrase returns [String v]:
    ANYTOKEN { $v = $ANYTOKEN.text;}
    | WORD {$v = $WORD.text;}
    | NUM {$v = $NUM.text;}
    ;

LPAREN : '(';
RPAREN : ')';
OR : '||';
AND : '&&';
NUM : [0-9]+;
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
WS : [ \t\r\n]+ -> skip;

ANYTOKEN :
    .
    ;

//word has to be put at the very bottom or else it's going to get matched before the
//other tokens (I think)
WORD:
    [a-zA-Z]+
    ;