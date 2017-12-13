// Generated from Request.g4 by ANTLR 4.7.1


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

	public RequestParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}
	public static class ReqContext extends ParserRuleContext {
		public AndExprContext andExpr() {
			return getRuleContext(AndExprContext.class,0);
		}
		public TerminalNode EOF() { return getToken(RequestParser.EOF, 0); }
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
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(24);
			andExpr();
			setState(25);
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
			setState(27);
			atom();
			setState(32);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==AND) {
				{
				{
				setState(28);
				match(AND);
				setState(29);
				atom();
				}
				}
				setState(34);
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
			setState(44);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case IN:
				enterOuterAlt(_localctx, 1);
				{
				setState(35);
				in();
				}
				break;
			case CATEGORY:
				enterOuterAlt(_localctx, 2);
				{
				setState(36);
				category();
				}
				break;
			case RATING:
				enterOuterAlt(_localctx, 3);
				{
				setState(37);
				rating();
				}
				break;
			case PRICE:
				enterOuterAlt(_localctx, 4);
				{
				setState(38);
				price();
				}
				break;
			case NAME:
				enterOuterAlt(_localctx, 5);
				{
				setState(39);
				name();
				}
				break;
			case LPAREN:
				enterOuterAlt(_localctx, 6);
				{
				{
				setState(40);
				match(LPAREN);
				setState(41);
				orExpr();
				setState(42);
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
			setState(46);
			andExpr();
			setState(51);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==OR) {
				{
				{
				setState(47);
				match(OR);
				setState(48);
				andExpr();
				}
				}
				setState(53);
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

	public static class InContext extends ParserRuleContext {
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
			setState(54);
			match(IN);
			setState(55);
			match(LPAREN);
			setState(56);
			line();
			setState(57);
			match(RPAREN);
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
			setState(59);
			match(CATEGORY);
			setState(60);
			match(LPAREN);
			setState(61);
			line();
			setState(62);
			match(RPAREN);
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
			setState(64);
			match(NAME);
			setState(65);
			match(LPAREN);
			setState(66);
			line();
			setState(67);
			match(RPAREN);
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
			setState(69);
			match(RATING);
			setState(70);
			ineq();
			setState(71);
			match(NUM);
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
			setState(73);
			match(PRICE);
			setState(74);
			ineq();
			setState(75);
			match(NUM);
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
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(77);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << GT) | (1L << GTE) | (1L << LT) | (1L << LTE) | (1L << EQ))) != 0)) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
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

	public static class LineContext extends ParserRuleContext {
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
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(82);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==ANYTOKEN || _la==WORD) {
				{
				{
				setState(79);
				phrase();
				}
				}
				setState(84);
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

	public static class PhraseContext extends ParserRuleContext {
		public TerminalNode ANYTOKEN() { return getToken(RequestParser.ANYTOKEN, 0); }
		public TerminalNode WORD() { return getToken(RequestParser.WORD, 0); }
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
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(85);
			_la = _input.LA(1);
			if ( !(_la==ANYTOKEN || _la==WORD) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
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

	public static final String _serializedATN =
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\3\24Z\4\2\t\2\4\3\t"+
		"\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13\t\13\4"+
		"\f\t\f\4\r\t\r\3\2\3\2\3\2\3\3\3\3\3\3\7\3!\n\3\f\3\16\3$\13\3\3\4\3\4"+
		"\3\4\3\4\3\4\3\4\3\4\3\4\3\4\5\4/\n\4\3\5\3\5\3\5\7\5\64\n\5\f\5\16\5"+
		"\67\13\5\3\6\3\6\3\6\3\6\3\6\3\7\3\7\3\7\3\7\3\7\3\b\3\b\3\b\3\b\3\b\3"+
		"\t\3\t\3\t\3\t\3\n\3\n\3\n\3\n\3\13\3\13\3\f\7\fS\n\f\f\f\16\fV\13\f\3"+
		"\r\3\r\3\r\2\2\16\2\4\6\b\n\f\16\20\22\24\26\30\2\4\3\2\b\f\3\2\23\24"+
		"\2U\2\32\3\2\2\2\4\35\3\2\2\2\6.\3\2\2\2\b\60\3\2\2\2\n8\3\2\2\2\f=\3"+
		"\2\2\2\16B\3\2\2\2\20G\3\2\2\2\22K\3\2\2\2\24O\3\2\2\2\26T\3\2\2\2\30"+
		"W\3\2\2\2\32\33\5\4\3\2\33\34\7\2\2\3\34\3\3\2\2\2\35\"\5\6\4\2\36\37"+
		"\7\6\2\2\37!\5\6\4\2 \36\3\2\2\2!$\3\2\2\2\" \3\2\2\2\"#\3\2\2\2#\5\3"+
		"\2\2\2$\"\3\2\2\2%/\5\n\6\2&/\5\f\7\2\'/\5\20\t\2(/\5\22\n\2)/\5\16\b"+
		"\2*+\7\3\2\2+,\5\b\5\2,-\7\4\2\2-/\3\2\2\2.%\3\2\2\2.&\3\2\2\2.\'\3\2"+
		"\2\2.(\3\2\2\2.)\3\2\2\2.*\3\2\2\2/\7\3\2\2\2\60\65\5\4\3\2\61\62\7\5"+
		"\2\2\62\64\5\4\3\2\63\61\3\2\2\2\64\67\3\2\2\2\65\63\3\2\2\2\65\66\3\2"+
		"\2\2\66\t\3\2\2\2\67\65\3\2\2\289\7\21\2\29:\7\3\2\2:;\5\26\f\2;<\7\4"+
		"\2\2<\13\3\2\2\2=>\7\20\2\2>?\7\3\2\2?@\5\26\f\2@A\7\4\2\2A\r\3\2\2\2"+
		"BC\7\17\2\2CD\7\3\2\2DE\5\26\f\2EF\7\4\2\2F\17\3\2\2\2GH\7\16\2\2HI\5"+
		"\24\13\2IJ\7\7\2\2J\21\3\2\2\2KL\7\r\2\2LM\5\24\13\2MN\7\7\2\2N\23\3\2"+
		"\2\2OP\t\2\2\2P\25\3\2\2\2QS\5\30\r\2RQ\3\2\2\2SV\3\2\2\2TR\3\2\2\2TU"+
		"\3\2\2\2U\27\3\2\2\2VT\3\2\2\2WX\t\3\2\2X\31\3\2\2\2\6\".\65T";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}