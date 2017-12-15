// Generated from Request.g4 by ANTLR 4.7.1

    package ca.ece.ubc.cpen221.mp5.Antlr;
    import java.util.*;
    import ca.ece.ubc.cpen221.mp5.Database.YelpDataBase;

import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class RequestParser extends Parser {
	static { RuntimeMetaData.checkVersion("4.7.1", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		LPAREN=1, RPAREN=2, OR=3, AND=4, NUM=5, GT=6, GTE=7, LT=8, LTE=9, EQ=10, 
		PRICE=11, RATING=12, NAME=13, CATEGORY=14, IN=15, WS=16, ANYTOKEN=17, 
		WORD=18;
	public static final int
		RULE_req = 0, RULE_andExpr = 1, RULE_atom = 2, RULE_orExpr = 3, RULE_in = 4, 
		RULE_category = 5, RULE_name = 6, RULE_rating = 7, RULE_price = 8, RULE_ineq = 9, 
		RULE_line = 10, RULE_phrase = 11;
	public static final String[] ruleNames = {
		"req", "andExpr", "atom", "orExpr", "in", "category", "name", "rating", 
		"price", "ineq", "line", "phrase"
	};

	private static final String[] _LITERAL_NAMES = {
		null, "'('", "')'", "'||'", "'&&'", null, "'>'", "'>='", "'<'", "'<='", 
		"'='", "'price'", "'rating'", "'name'", "'category'", "'in'"
	};
	private static final String[] _SYMBOLIC_NAMES = {
		null, "LPAREN", "RPAREN", "OR", "AND", "NUM", "GT", "GTE", "LT", "LTE", 
		"EQ", "PRICE", "RATING", "NAME", "CATEGORY", "IN", "WS", "ANYTOKEN", "WORD"
	};
	public static final Vocabulary VOCABULARY = new VocabularyImpl(_LITERAL_NAMES, _SYMBOLIC_NAMES);

	/**
	 * @deprecated Use {@link #VOCABULARY} instead.
	 */
	@Deprecated
	public static final String[] tokenNames;
	static {
		tokenNames = new String[_SYMBOLIC_NAMES.length];
		for (int i = 0; i < tokenNames.length; i++) {
			tokenNames[i] = VOCABULARY.getLiteralName(i);
			if (tokenNames[i] == null) {
				tokenNames[i] = VOCABULARY.getSymbolicName(i);
			}

			if (tokenNames[i] == null) {
				tokenNames[i] = "<INVALID>";
			}
		}
	}

	@Override
	@Deprecated
	public String[] getTokenNames() {
		return tokenNames;
	}

	@Override

	public Vocabulary getVocabulary() {
		return VOCABULARY;
	}

	@Override
	public String getGrammarFileName() { return "Request.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }


	    YelpDataBase ydb;
	    public RequestParser(TokenStream input, YelpDataBase ydb) {
	        this(input);
	        this.ydb = ydb;
	    }

	public RequestParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}
	public static class ReqContext extends ParserRuleContext {
		public Set<String> restaurants;
		public AndExprContext arg1;
		public AndExprContext arg2;
		public TerminalNode EOF() { return getToken(RequestParser.EOF, 0); }
		public List<AndExprContext> andExpr() {
			return getRuleContexts(AndExprContext.class);
		}
		public AndExprContext andExpr(int i) {
			return getRuleContext(AndExprContext.class,i);
		}
		public List<TerminalNode> OR() { return getTokens(RequestParser.OR); }
		public TerminalNode OR(int i) {
			return getToken(RequestParser.OR, i);
		}
		public ReqContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_req; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof RequestListener ) ((RequestListener)listener).enterReq(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof RequestListener ) ((RequestListener)listener).exitReq(this);
		}
	}

	public final ReqContext req() throws RecognitionException {
		ReqContext _localctx = new ReqContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_req);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(24);
			((ReqContext)_localctx).arg1 = andExpr();
			((ReqContext)_localctx).restaurants =  ((ReqContext)_localctx).arg1.restaurants;
			setState(32);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==OR) {
				{
				{
				setState(26);
				match(OR);
				setState(27);
				((ReqContext)_localctx).arg2 = andExpr();
				_localctx.restaurants.addAll(((ReqContext)_localctx).arg2.restaurants);
				}
				}
				setState(34);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(35);
			match(EOF);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class AndExprContext extends ParserRuleContext {
		public Set<String> restaurants;
		public AtomContext arg1;
		public AtomContext arg2;
		public List<AtomContext> atom() {
			return getRuleContexts(AtomContext.class);
		}
		public AtomContext atom(int i) {
			return getRuleContext(AtomContext.class,i);
		}
		public List<TerminalNode> AND() { return getTokens(RequestParser.AND); }
		public TerminalNode AND(int i) {
			return getToken(RequestParser.AND, i);
		}
		public AndExprContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_andExpr; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof RequestListener ) ((RequestListener)listener).enterAndExpr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof RequestListener ) ((RequestListener)listener).exitAndExpr(this);
		}
	}

	public final AndExprContext andExpr() throws RecognitionException {
		AndExprContext _localctx = new AndExprContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_andExpr);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(37);
			((AndExprContext)_localctx).arg1 = atom();
			((AndExprContext)_localctx).restaurants =  ((AndExprContext)_localctx).arg1.restaurants;
			setState(45);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==AND) {
				{
				{
				setState(39);
				match(AND);
				setState(40);
				((AndExprContext)_localctx).arg2 = atom();
				_localctx.restaurants.retainAll(((AndExprContext)_localctx).arg2.restaurants);
				}
				}
				setState(47);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class AtomContext extends ParserRuleContext {
		public Set<String> restaurants;
		public InContext in;
		public CategoryContext category;
		public RatingContext rating;
		public PriceContext price;
		public NameContext name;
		public OrExprContext orExpr;
		public InContext in() {
			return getRuleContext(InContext.class,0);
		}
		public CategoryContext category() {
			return getRuleContext(CategoryContext.class,0);
		}
		public RatingContext rating() {
			return getRuleContext(RatingContext.class,0);
		}
		public PriceContext price() {
			return getRuleContext(PriceContext.class,0);
		}
		public NameContext name() {
			return getRuleContext(NameContext.class,0);
		}
		public TerminalNode LPAREN() { return getToken(RequestParser.LPAREN, 0); }
		public OrExprContext orExpr() {
			return getRuleContext(OrExprContext.class,0);
		}
		public TerminalNode RPAREN() { return getToken(RequestParser.RPAREN, 0); }
		public AtomContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_atom; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof RequestListener ) ((RequestListener)listener).enterAtom(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof RequestListener ) ((RequestListener)listener).exitAtom(this);
		}
	}

	public final AtomContext atom() throws RecognitionException {
		AtomContext _localctx = new AtomContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_atom);
		try {
			setState(68);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case IN:
				enterOuterAlt(_localctx, 1);
				{
				setState(48);
				((AtomContext)_localctx).in = in();
				((AtomContext)_localctx).restaurants =  ((AtomContext)_localctx).in.restaurants;
				}
				break;
			case CATEGORY:
				enterOuterAlt(_localctx, 2);
				{
				setState(51);
				((AtomContext)_localctx).category = category();
				((AtomContext)_localctx).restaurants =  ((AtomContext)_localctx).category.restaurants;
				}
				break;
			case RATING:
				enterOuterAlt(_localctx, 3);
				{
				setState(54);
				((AtomContext)_localctx).rating = rating();
				((AtomContext)_localctx).restaurants =  ((AtomContext)_localctx).rating.restaurants;
				}
				break;
			case PRICE:
				enterOuterAlt(_localctx, 4);
				{
				setState(57);
				((AtomContext)_localctx).price = price();
				((AtomContext)_localctx).restaurants =  ((AtomContext)_localctx).price.restaurants;
				}
				break;
			case NAME:
				enterOuterAlt(_localctx, 5);
				{
				setState(60);
				((AtomContext)_localctx).name = name();
				((AtomContext)_localctx).restaurants =  ((AtomContext)_localctx).name.restaurants;
				}
				break;
			case LPAREN:
				enterOuterAlt(_localctx, 6);
				{
				{
				setState(63);
				match(LPAREN);
				setState(64);
				((AtomContext)_localctx).orExpr = orExpr();
				((AtomContext)_localctx).restaurants =  ((AtomContext)_localctx).orExpr.restaurants;
				setState(66);
				match(RPAREN);
				}
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class OrExprContext extends ParserRuleContext {
		public Set<String> restaurants;
		public AndExprContext arg1;
		public AndExprContext arg2;
		public List<AndExprContext> andExpr() {
			return getRuleContexts(AndExprContext.class);
		}
		public AndExprContext andExpr(int i) {
			return getRuleContext(AndExprContext.class,i);
		}
		public List<TerminalNode> OR() { return getTokens(RequestParser.OR); }
		public TerminalNode OR(int i) {
			return getToken(RequestParser.OR, i);
		}
		public OrExprContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_orExpr; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof RequestListener ) ((RequestListener)listener).enterOrExpr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof RequestListener ) ((RequestListener)listener).exitOrExpr(this);
		}
	}

	public final OrExprContext orExpr() throws RecognitionException {
		OrExprContext _localctx = new OrExprContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_orExpr);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(70);
			((OrExprContext)_localctx).arg1 = andExpr();
			((OrExprContext)_localctx).restaurants =  ((OrExprContext)_localctx).arg1.restaurants;
			setState(76);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==OR) {
				{
				{
				setState(72);
				match(OR);
				setState(73);
				((OrExprContext)_localctx).arg2 = andExpr();
				}
				}
				setState(78);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			_localctx.restaurants.addAll(((OrExprContext)_localctx).arg2.restaurants);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class InContext extends ParserRuleContext {
		public Set<String> restaurants;
		public LineContext line;
		public TerminalNode IN() { return getToken(RequestParser.IN, 0); }
		public TerminalNode LPAREN() { return getToken(RequestParser.LPAREN, 0); }
		public LineContext line() {
			return getRuleContext(LineContext.class,0);
		}
		public TerminalNode RPAREN() { return getToken(RequestParser.RPAREN, 0); }
		public InContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_in; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof RequestListener ) ((RequestListener)listener).enterIn(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof RequestListener ) ((RequestListener)listener).exitIn(this);
		}
	}

	public final InContext in() throws RecognitionException {
		InContext _localctx = new InContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_in);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(81);
			match(IN);
			setState(82);
			match(LPAREN);
			setState(83);
			((InContext)_localctx).line = line();
			setState(84);
			match(RPAREN);
			((InContext)_localctx).restaurants =  ydb.inAtom(((InContext)_localctx).line.value);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class CategoryContext extends ParserRuleContext {
		public Set<String> restaurants;
		public LineContext line;
		public TerminalNode CATEGORY() { return getToken(RequestParser.CATEGORY, 0); }
		public TerminalNode LPAREN() { return getToken(RequestParser.LPAREN, 0); }
		public LineContext line() {
			return getRuleContext(LineContext.class,0);
		}
		public TerminalNode RPAREN() { return getToken(RequestParser.RPAREN, 0); }
		public CategoryContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_category; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof RequestListener ) ((RequestListener)listener).enterCategory(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof RequestListener ) ((RequestListener)listener).exitCategory(this);
		}
	}

	public final CategoryContext category() throws RecognitionException {
		CategoryContext _localctx = new CategoryContext(_ctx, getState());
		enterRule(_localctx, 10, RULE_category);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(87);
			match(CATEGORY);
			setState(88);
			match(LPAREN);
			setState(89);
			((CategoryContext)_localctx).line = line();
			setState(90);
			match(RPAREN);
			((CategoryContext)_localctx).restaurants =  ydb.categoryAtom(((CategoryContext)_localctx).line.value);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class NameContext extends ParserRuleContext {
		public Set<String> restaurants;
		public LineContext line;
		public TerminalNode NAME() { return getToken(RequestParser.NAME, 0); }
		public TerminalNode LPAREN() { return getToken(RequestParser.LPAREN, 0); }
		public LineContext line() {
			return getRuleContext(LineContext.class,0);
		}
		public TerminalNode RPAREN() { return getToken(RequestParser.RPAREN, 0); }
		public NameContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_name; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof RequestListener ) ((RequestListener)listener).enterName(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof RequestListener ) ((RequestListener)listener).exitName(this);
		}
	}

	public final NameContext name() throws RecognitionException {
		NameContext _localctx = new NameContext(_ctx, getState());
		enterRule(_localctx, 12, RULE_name);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(93);
			match(NAME);
			setState(94);
			match(LPAREN);
			setState(95);
			((NameContext)_localctx).line = line();
			setState(96);
			match(RPAREN);
			((NameContext)_localctx).restaurants =  ydb.nameAtom(((NameContext)_localctx).line.value);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class RatingContext extends ParserRuleContext {
		public Set<String> restaurants;
		public IneqContext ineq;
		public Token NUM;
		public TerminalNode RATING() { return getToken(RequestParser.RATING, 0); }
		public IneqContext ineq() {
			return getRuleContext(IneqContext.class,0);
		}
		public TerminalNode NUM() { return getToken(RequestParser.NUM, 0); }
		public RatingContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_rating; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof RequestListener ) ((RequestListener)listener).enterRating(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof RequestListener ) ((RequestListener)listener).exitRating(this);
		}
	}

	public final RatingContext rating() throws RecognitionException {
		RatingContext _localctx = new RatingContext(_ctx, getState());
		enterRule(_localctx, 14, RULE_rating);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(99);
			match(RATING);
			setState(100);
			((RatingContext)_localctx).ineq = ineq();
			setState(101);
			((RatingContext)_localctx).NUM = match(NUM);
			((RatingContext)_localctx).restaurants =  ydb.ratingAtom(((RatingContext)_localctx).ineq.v, Integer.parseInt((((RatingContext)_localctx).NUM!=null?((RatingContext)_localctx).NUM.getText():null)));
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class PriceContext extends ParserRuleContext {
		public Set<String> restaurants;
		public IneqContext ineq;
		public Token NUM;
		public TerminalNode PRICE() { return getToken(RequestParser.PRICE, 0); }
		public IneqContext ineq() {
			return getRuleContext(IneqContext.class,0);
		}
		public TerminalNode NUM() { return getToken(RequestParser.NUM, 0); }
		public PriceContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_price; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof RequestListener ) ((RequestListener)listener).enterPrice(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof RequestListener ) ((RequestListener)listener).exitPrice(this);
		}
	}

	public final PriceContext price() throws RecognitionException {
		PriceContext _localctx = new PriceContext(_ctx, getState());
		enterRule(_localctx, 16, RULE_price);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(104);
			match(PRICE);
			setState(105);
			((PriceContext)_localctx).ineq = ineq();
			setState(106);
			((PriceContext)_localctx).NUM = match(NUM);
			((PriceContext)_localctx).restaurants =  ydb.priceAtom(((PriceContext)_localctx).ineq.v, Integer.parseInt((((PriceContext)_localctx).NUM!=null?((PriceContext)_localctx).NUM.getText():null)));
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class IneqContext extends ParserRuleContext {
		public String v;
		public Token GT;
		public Token GTE;
		public Token LT;
		public Token LTE;
		public Token EQ;
		public TerminalNode GT() { return getToken(RequestParser.GT, 0); }
		public TerminalNode GTE() { return getToken(RequestParser.GTE, 0); }
		public TerminalNode LT() { return getToken(RequestParser.LT, 0); }
		public TerminalNode LTE() { return getToken(RequestParser.LTE, 0); }
		public TerminalNode EQ() { return getToken(RequestParser.EQ, 0); }
		public IneqContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_ineq; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof RequestListener ) ((RequestListener)listener).enterIneq(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof RequestListener ) ((RequestListener)listener).exitIneq(this);
		}
	}

	public final IneqContext ineq() throws RecognitionException {
		IneqContext _localctx = new IneqContext(_ctx, getState());
		enterRule(_localctx, 18, RULE_ineq);
		try {
			setState(119);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case GT:
				enterOuterAlt(_localctx, 1);
				{
				setState(109);
				((IneqContext)_localctx).GT = match(GT);
				((IneqContext)_localctx).v =  (((IneqContext)_localctx).GT!=null?((IneqContext)_localctx).GT.getText():null);
				}
				break;
			case GTE:
				enterOuterAlt(_localctx, 2);
				{
				setState(111);
				((IneqContext)_localctx).GTE = match(GTE);
				((IneqContext)_localctx).v =  (((IneqContext)_localctx).GTE!=null?((IneqContext)_localctx).GTE.getText():null);
				}
				break;
			case LT:
				enterOuterAlt(_localctx, 3);
				{
				setState(113);
				((IneqContext)_localctx).LT = match(LT);
				((IneqContext)_localctx).v =  (((IneqContext)_localctx).LT!=null?((IneqContext)_localctx).LT.getText():null);
				}
				break;
			case LTE:
				enterOuterAlt(_localctx, 4);
				{
				setState(115);
				((IneqContext)_localctx).LTE = match(LTE);
				((IneqContext)_localctx).v =  (((IneqContext)_localctx).LTE!=null?((IneqContext)_localctx).LTE.getText():null);
				}
				break;
			case EQ:
				enterOuterAlt(_localctx, 5);
				{
				setState(117);
				((IneqContext)_localctx).EQ = match(EQ);
				((IneqContext)_localctx).v =  (((IneqContext)_localctx).EQ!=null?((IneqContext)_localctx).EQ.getText():null);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class LineContext extends ParserRuleContext {
		public String value;
		public PhraseContext phrase;
		public List<PhraseContext> phrase() {
			return getRuleContexts(PhraseContext.class);
		}
		public PhraseContext phrase(int i) {
			return getRuleContext(PhraseContext.class,i);
		}
		public LineContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_line; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof RequestListener ) ((RequestListener)listener).enterLine(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof RequestListener ) ((RequestListener)listener).exitLine(this);
		}
	}

	public final LineContext line() throws RecognitionException {
		LineContext _localctx = new LineContext(_ctx, getState());
		enterRule(_localctx, 20, RULE_line);
		((LineContext)_localctx).value =  ".*";
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(124);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << NUM) | (1L << ANYTOKEN) | (1L << WORD))) != 0)) {
				{
				{
				setState(121);
				((LineContext)_localctx).phrase = phrase();
				}
				}
				setState(126);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			_localctx.value += ((LineContext)_localctx).phrase.v + ".*";
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class PhraseContext extends ParserRuleContext {
		public String v;
		public Token ANYTOKEN;
		public Token WORD;
		public Token NUM;
		public TerminalNode ANYTOKEN() { return getToken(RequestParser.ANYTOKEN, 0); }
		public TerminalNode WORD() { return getToken(RequestParser.WORD, 0); }
		public TerminalNode NUM() { return getToken(RequestParser.NUM, 0); }
		public PhraseContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_phrase; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof RequestListener ) ((RequestListener)listener).enterPhrase(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof RequestListener ) ((RequestListener)listener).exitPhrase(this);
		}
	}

	public final PhraseContext phrase() throws RecognitionException {
		PhraseContext _localctx = new PhraseContext(_ctx, getState());
		enterRule(_localctx, 22, RULE_phrase);
		try {
			setState(135);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case ANYTOKEN:
				enterOuterAlt(_localctx, 1);
				{
				setState(129);
				((PhraseContext)_localctx).ANYTOKEN = match(ANYTOKEN);
				 ((PhraseContext)_localctx).v =  (((PhraseContext)_localctx).ANYTOKEN!=null?((PhraseContext)_localctx).ANYTOKEN.getText():null);
				}
				break;
			case WORD:
				enterOuterAlt(_localctx, 2);
				{
				setState(131);
				((PhraseContext)_localctx).WORD = match(WORD);
				((PhraseContext)_localctx).v =  (((PhraseContext)_localctx).WORD!=null?((PhraseContext)_localctx).WORD.getText():null);
				}
				break;
			case NUM:
				enterOuterAlt(_localctx, 3);
				{
				setState(133);
				((PhraseContext)_localctx).NUM = match(NUM);
				((PhraseContext)_localctx).v =  (((PhraseContext)_localctx).NUM!=null?((PhraseContext)_localctx).NUM.getText():null);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static final String _serializedATN =
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\3\24\u008c\4\2\t\2"+
		"\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13"+
		"\t\13\4\f\t\f\4\r\t\r\3\2\3\2\3\2\3\2\3\2\3\2\7\2!\n\2\f\2\16\2$\13\2"+
		"\3\2\3\2\3\3\3\3\3\3\3\3\3\3\3\3\7\3.\n\3\f\3\16\3\61\13\3\3\4\3\4\3\4"+
		"\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\5"+
		"\4G\n\4\3\5\3\5\3\5\3\5\7\5M\n\5\f\5\16\5P\13\5\3\5\3\5\3\6\3\6\3\6\3"+
		"\6\3\6\3\6\3\7\3\7\3\7\3\7\3\7\3\7\3\b\3\b\3\b\3\b\3\b\3\b\3\t\3\t\3\t"+
		"\3\t\3\t\3\n\3\n\3\n\3\n\3\n\3\13\3\13\3\13\3\13\3\13\3\13\3\13\3\13\3"+
		"\13\3\13\5\13z\n\13\3\f\7\f}\n\f\f\f\16\f\u0080\13\f\3\f\3\f\3\r\3\r\3"+
		"\r\3\r\3\r\3\r\5\r\u008a\n\r\3\r\2\2\16\2\4\6\b\n\f\16\20\22\24\26\30"+
		"\2\2\2\u008e\2\32\3\2\2\2\4\'\3\2\2\2\6F\3\2\2\2\bH\3\2\2\2\nS\3\2\2\2"+
		"\fY\3\2\2\2\16_\3\2\2\2\20e\3\2\2\2\22j\3\2\2\2\24y\3\2\2\2\26~\3\2\2"+
		"\2\30\u0089\3\2\2\2\32\33\5\4\3\2\33\"\b\2\1\2\34\35\7\5\2\2\35\36\5\4"+
		"\3\2\36\37\b\2\1\2\37!\3\2\2\2 \34\3\2\2\2!$\3\2\2\2\" \3\2\2\2\"#\3\2"+
		"\2\2#%\3\2\2\2$\"\3\2\2\2%&\7\2\2\3&\3\3\2\2\2\'(\5\6\4\2(/\b\3\1\2)*"+
		"\7\6\2\2*+\5\6\4\2+,\b\3\1\2,.\3\2\2\2-)\3\2\2\2.\61\3\2\2\2/-\3\2\2\2"+
		"/\60\3\2\2\2\60\5\3\2\2\2\61/\3\2\2\2\62\63\5\n\6\2\63\64\b\4\1\2\64G"+
		"\3\2\2\2\65\66\5\f\7\2\66\67\b\4\1\2\67G\3\2\2\289\5\20\t\29:\b\4\1\2"+
		":G\3\2\2\2;<\5\22\n\2<=\b\4\1\2=G\3\2\2\2>?\5\16\b\2?@\b\4\1\2@G\3\2\2"+
		"\2AB\7\3\2\2BC\5\b\5\2CD\b\4\1\2DE\7\4\2\2EG\3\2\2\2F\62\3\2\2\2F\65\3"+
		"\2\2\2F8\3\2\2\2F;\3\2\2\2F>\3\2\2\2FA\3\2\2\2G\7\3\2\2\2HI\5\4\3\2IN"+
		"\b\5\1\2JK\7\5\2\2KM\5\4\3\2LJ\3\2\2\2MP\3\2\2\2NL\3\2\2\2NO\3\2\2\2O"+
		"Q\3\2\2\2PN\3\2\2\2QR\b\5\1\2R\t\3\2\2\2ST\7\21\2\2TU\7\3\2\2UV\5\26\f"+
		"\2VW\7\4\2\2WX\b\6\1\2X\13\3\2\2\2YZ\7\20\2\2Z[\7\3\2\2[\\\5\26\f\2\\"+
		"]\7\4\2\2]^\b\7\1\2^\r\3\2\2\2_`\7\17\2\2`a\7\3\2\2ab\5\26\f\2bc\7\4\2"+
		"\2cd\b\b\1\2d\17\3\2\2\2ef\7\16\2\2fg\5\24\13\2gh\7\7\2\2hi\b\t\1\2i\21"+
		"\3\2\2\2jk\7\r\2\2kl\5\24\13\2lm\7\7\2\2mn\b\n\1\2n\23\3\2\2\2op\7\b\2"+
		"\2pz\b\13\1\2qr\7\t\2\2rz\b\13\1\2st\7\n\2\2tz\b\13\1\2uv\7\13\2\2vz\b"+
		"\13\1\2wx\7\f\2\2xz\b\13\1\2yo\3\2\2\2yq\3\2\2\2ys\3\2\2\2yu\3\2\2\2y"+
		"w\3\2\2\2z\25\3\2\2\2{}\5\30\r\2|{\3\2\2\2}\u0080\3\2\2\2~|\3\2\2\2~\177"+
		"\3\2\2\2\177\u0081\3\2\2\2\u0080~\3\2\2\2\u0081\u0082\b\f\1\2\u0082\27"+
		"\3\2\2\2\u0083\u0084\7\23\2\2\u0084\u008a\b\r\1\2\u0085\u0086\7\24\2\2"+
		"\u0086\u008a\b\r\1\2\u0087\u0088\7\7\2\2\u0088\u008a\b\r\1\2\u0089\u0083"+
		"\3\2\2\2\u0089\u0085\3\2\2\2\u0089\u0087\3\2\2\2\u008a\31\3\2\2\2\t\""+
		"/FNy~\u0089";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}