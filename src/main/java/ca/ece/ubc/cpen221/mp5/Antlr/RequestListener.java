// Generated from Request.g4 by ANTLR 4.7.1

    package ca.ece.ubc.cpen221.mp5.Antlr;
    import java.util.*;
    import ca.ece.ubc.cpen221.mp5.Database.YelpDataBase;

import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link RequestParser}.
 */
public interface RequestListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link RequestParser#req}.
	 * @param ctx the parse tree
	 */
	void enterReq(RequestParser.ReqContext ctx);
	/**
	 * Exit a parse tree produced by {@link RequestParser#req}.
	 * @param ctx the parse tree
	 */
	void exitReq(RequestParser.ReqContext ctx);
	/**
	 * Enter a parse tree produced by {@link RequestParser#andExpr}.
	 * @param ctx the parse tree
	 */
	void enterAndExpr(RequestParser.AndExprContext ctx);
	/**
	 * Exit a parse tree produced by {@link RequestParser#andExpr}.
	 * @param ctx the parse tree
	 */
	void exitAndExpr(RequestParser.AndExprContext ctx);
	/**
	 * Enter a parse tree produced by {@link RequestParser#atom}.
	 * @param ctx the parse tree
	 */
	void enterAtom(RequestParser.AtomContext ctx);
	/**
	 * Exit a parse tree produced by {@link RequestParser#atom}.
	 * @param ctx the parse tree
	 */
	void exitAtom(RequestParser.AtomContext ctx);
	/**
	 * Enter a parse tree produced by {@link RequestParser#orExpr}.
	 * @param ctx the parse tree
	 */
	void enterOrExpr(RequestParser.OrExprContext ctx);
	/**
	 * Exit a parse tree produced by {@link RequestParser#orExpr}.
	 * @param ctx the parse tree
	 */
	void exitOrExpr(RequestParser.OrExprContext ctx);
	/**
	 * Enter a parse tree produced by {@link RequestParser#in}.
	 * @param ctx the parse tree
	 */
	void enterIn(RequestParser.InContext ctx);
	/**
	 * Exit a parse tree produced by {@link RequestParser#in}.
	 * @param ctx the parse tree
	 */
	void exitIn(RequestParser.InContext ctx);
	/**
	 * Enter a parse tree produced by {@link RequestParser#category}.
	 * @param ctx the parse tree
	 */
	void enterCategory(RequestParser.CategoryContext ctx);
	/**
	 * Exit a parse tree produced by {@link RequestParser#category}.
	 * @param ctx the parse tree
	 */
	void exitCategory(RequestParser.CategoryContext ctx);
	/**
	 * Enter a parse tree produced by {@link RequestParser#name}.
	 * @param ctx the parse tree
	 */
	void enterName(RequestParser.NameContext ctx);
	/**
	 * Exit a parse tree produced by {@link RequestParser#name}.
	 * @param ctx the parse tree
	 */
	void exitName(RequestParser.NameContext ctx);
	/**
	 * Enter a parse tree produced by {@link RequestParser#rating}.
	 * @param ctx the parse tree
	 */
	void enterRating(RequestParser.RatingContext ctx);
	/**
	 * Exit a parse tree produced by {@link RequestParser#rating}.
	 * @param ctx the parse tree
	 */
	void exitRating(RequestParser.RatingContext ctx);
	/**
	 * Enter a parse tree produced by {@link RequestParser#price}.
	 * @param ctx the parse tree
	 */
	void enterPrice(RequestParser.PriceContext ctx);
	/**
	 * Exit a parse tree produced by {@link RequestParser#price}.
	 * @param ctx the parse tree
	 */
	void exitPrice(RequestParser.PriceContext ctx);
	/**
	 * Enter a parse tree produced by {@link RequestParser#ineq}.
	 * @param ctx the parse tree
	 */
	void enterIneq(RequestParser.IneqContext ctx);
	/**
	 * Exit a parse tree produced by {@link RequestParser#ineq}.
	 * @param ctx the parse tree
	 */
	void exitIneq(RequestParser.IneqContext ctx);
	/**
	 * Enter a parse tree produced by {@link RequestParser#line}.
	 * @param ctx the parse tree
	 */
	void enterLine(RequestParser.LineContext ctx);
	/**
	 * Exit a parse tree produced by {@link RequestParser#line}.
	 * @param ctx the parse tree
	 */
	void exitLine(RequestParser.LineContext ctx);
	/**
	 * Enter a parse tree produced by {@link RequestParser#phrase}.
	 * @param ctx the parse tree
	 */
	void enterPhrase(RequestParser.PhraseContext ctx);
	/**
	 * Exit a parse tree produced by {@link RequestParser#phrase}.
	 * @param ctx the parse tree
	 */
	void exitPhrase(RequestParser.PhraseContext ctx);
}