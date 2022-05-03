// Generated from E:/UT/S6/TA/Compiler/SimpleLOOP-Compiler/src/main/grammar\SimpleLOOP.g4 by ANTLR 4.9.2
package parsers;

     import main.ast.nodes.*;
     import main.ast.nodes.declaration.*;
     import main.ast.nodes.declaration.classDec.*;
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
 
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.misc.*;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class SimpleLOOPLexer extends Lexer {
	static { RuntimeMetaData.checkVersion("4.9.2", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		LINE_BREAK=1, CLASS=2, PUBLIC=3, PRIVATE=4, INITIALIZE=5, NEW=6, RETURN=7, 
		VOID=8, DELETE=9, INCLUDE=10, ADD=11, MERGE=12, PRINT=13, IF=14, ELSE=15, 
		ELSIF=16, PLUS=17, MINUS=18, MULT=19, DIVIDE=20, INC=21, DEC=22, EQUAL=23, 
		GREATER_THAN=24, LESS_THAN=25, ARROW=26, BAR=27, AND=28, OR=29, NOT=30, 
		TIF=31, TELSE=32, TRUE=33, FALSE=34, NULL=35, BEGIN=36, END=37, INT=38, 
		BOOL=39, FPTR=40, SET=41, EACH=42, DO=43, ASSIGN=44, SHARP=45, LPAR=46, 
		RPAR=47, LBRACK=48, RBRACK=49, LBRACE=50, RBRACE=51, COMMA=52, DOT=53, 
		SEMICOLON=54, NEWLINE=55, INT_VALUE=56, IDENTIFIER=57, CLASS_IDENTIFIER=58, 
		COMMENT=59, MLCOMMENT=60, WS=61;
	public static String[] channelNames = {
		"DEFAULT_TOKEN_CHANNEL", "HIDDEN"
	};

	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	private static String[] makeRuleNames() {
		return new String[] {
			"LINE_BREAK", "CLASS", "PUBLIC", "PRIVATE", "INITIALIZE", "NEW", "RETURN", 
			"VOID", "DELETE", "INCLUDE", "ADD", "MERGE", "PRINT", "IF", "ELSE", "ELSIF", 
			"PLUS", "MINUS", "MULT", "DIVIDE", "INC", "DEC", "EQUAL", "GREATER_THAN", 
			"LESS_THAN", "ARROW", "BAR", "AND", "OR", "NOT", "TIF", "TELSE", "TRUE", 
			"FALSE", "NULL", "BEGIN", "END", "INT", "BOOL", "FPTR", "SET", "EACH", 
			"DO", "ASSIGN", "SHARP", "LPAR", "RPAR", "LBRACK", "RBRACK", "LBRACE", 
			"RBRACE", "COMMA", "DOT", "SEMICOLON", "NEWLINE", "INT_VALUE", "IDENTIFIER", 
			"CLASS_IDENTIFIER", "COMMENT", "MLCOMMENT", "WS"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, null, "'class'", "'public'", "'private'", "'initialize'", "'new'", 
			"'return'", "'void'", "'delete'", "'include'", "'add'", "'merge'", "'print'", 
			"'if'", "'else'", "'elsif'", "'+'", "'-'", "'*'", "'/'", "'++'", "'--'", 
			"'=='", "'>'", "'<'", "'->'", "'|'", "'&&'", "'||'", "'!'", "'?'", "':'", 
			"'true'", "'false'", "'null'", "'=begin'", "'=end'", "'int'", "'bool'", 
			"'fptr'", "'Set'", "'each'", "'do'", "'='", "'#'", "'('", "')'", "'['", 
			"']'", "'{'", "'}'", "','", "'.'", "';'", "'\n'"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, "LINE_BREAK", "CLASS", "PUBLIC", "PRIVATE", "INITIALIZE", "NEW", 
			"RETURN", "VOID", "DELETE", "INCLUDE", "ADD", "MERGE", "PRINT", "IF", 
			"ELSE", "ELSIF", "PLUS", "MINUS", "MULT", "DIVIDE", "INC", "DEC", "EQUAL", 
			"GREATER_THAN", "LESS_THAN", "ARROW", "BAR", "AND", "OR", "NOT", "TIF", 
			"TELSE", "TRUE", "FALSE", "NULL", "BEGIN", "END", "INT", "BOOL", "FPTR", 
			"SET", "EACH", "DO", "ASSIGN", "SHARP", "LPAR", "RPAR", "LBRACK", "RBRACK", 
			"LBRACE", "RBRACE", "COMMA", "DOT", "SEMICOLON", "NEWLINE", "INT_VALUE", 
			"IDENTIFIER", "CLASS_IDENTIFIER", "COMMENT", "MLCOMMENT", "WS"
		};
	}
	private static final String[] _SYMBOLIC_NAMES = makeSymbolicNames();
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


	public SimpleLOOPLexer(CharStream input) {
		super(input);
		_interp = new LexerATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@Override
	public String getGrammarFileName() { return "SimpleLOOP.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public String[] getChannelNames() { return channelNames; }

	@Override
	public String[] getModeNames() { return modeNames; }

	@Override
	public ATN getATN() { return _ATN; }

	public static final String _serializedATN =
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\2?\u0191\b\1\4\2\t"+
		"\2\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13"+
		"\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22\t\22"+
		"\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\4\31\t\31"+
		"\4\32\t\32\4\33\t\33\4\34\t\34\4\35\t\35\4\36\t\36\4\37\t\37\4 \t \4!"+
		"\t!\4\"\t\"\4#\t#\4$\t$\4%\t%\4&\t&\4\'\t\'\4(\t(\4)\t)\4*\t*\4+\t+\4"+
		",\t,\4-\t-\4.\t.\4/\t/\4\60\t\60\4\61\t\61\4\62\t\62\4\63\t\63\4\64\t"+
		"\64\4\65\t\65\4\66\t\66\4\67\t\67\48\t8\49\t9\4:\t:\4;\t;\4<\t<\4=\t="+
		"\4>\t>\3\2\3\2\3\2\3\2\3\2\3\2\3\3\3\3\3\3\3\3\3\3\3\3\3\4\3\4\3\4\3\4"+
		"\3\4\3\4\3\4\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\6\3\6\3\6\3\6\3\6\3\6\3"+
		"\6\3\6\3\6\3\6\3\6\3\7\3\7\3\7\3\7\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\t\3\t"+
		"\3\t\3\t\3\t\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\13\3\13\3\13\3\13\3\13\3\13"+
		"\3\13\3\13\3\f\3\f\3\f\3\f\3\r\3\r\3\r\3\r\3\r\3\r\3\16\3\16\3\16\3\16"+
		"\3\16\3\16\3\17\3\17\3\17\3\20\3\20\3\20\3\20\3\20\3\21\3\21\3\21\3\21"+
		"\3\21\3\21\3\22\3\22\3\23\3\23\3\24\3\24\3\25\3\25\3\26\3\26\3\26\3\27"+
		"\3\27\3\27\3\30\3\30\3\30\3\31\3\31\3\32\3\32\3\33\3\33\3\33\3\34\3\34"+
		"\3\35\3\35\3\35\3\36\3\36\3\36\3\37\3\37\3 \3 \3!\3!\3\"\3\"\3\"\3\"\3"+
		"\"\3#\3#\3#\3#\3#\3#\3$\3$\3$\3$\3$\3%\3%\3%\3%\3%\3%\3%\3&\3&\3&\3&\3"+
		"&\3\'\3\'\3\'\3\'\3(\3(\3(\3(\3(\3)\3)\3)\3)\3)\3*\3*\3*\3*\3+\3+\3+\3"+
		"+\3+\3,\3,\3,\3-\3-\3.\3.\3/\3/\3\60\3\60\3\61\3\61\3\62\3\62\3\63\3\63"+
		"\3\64\3\64\3\65\3\65\3\66\3\66\3\67\3\67\3\67\3\67\38\38\39\39\39\79\u015a"+
		"\n9\f9\169\u015d\139\59\u015f\n9\3:\3:\7:\u0163\n:\f:\16:\u0166\13:\3"+
		";\3;\7;\u016a\n;\f;\16;\u016d\13;\3<\3<\7<\u0171\n<\f<\16<\u0174\13<\3"+
		"<\3<\3<\3<\3=\3=\3=\3=\3=\3=\3=\3=\7=\u0182\n=\f=\16=\u0185\13=\3=\3="+
		"\3=\3=\3=\3=\3=\3>\3>\3>\3>\4\u0172\u0183\2?\3\3\5\4\7\5\t\6\13\7\r\b"+
		"\17\t\21\n\23\13\25\f\27\r\31\16\33\17\35\20\37\21!\22#\23%\24\'\25)\26"+
		"+\27-\30/\31\61\32\63\33\65\34\67\359\36;\37= ?!A\"C#E$G%I&K\'M(O)Q*S"+
		"+U,W-Y.[/]\60_\61a\62c\63e\64g\65i\66k\67m8o9q:s;u<w=y>{?\3\2\b\3\2\63"+
		";\3\2\62;\4\2aac|\6\2\62;C\\aac|\3\2C\\\5\2\13\13\17\17\"\"\2\u0196\2"+
		"\3\3\2\2\2\2\5\3\2\2\2\2\7\3\2\2\2\2\t\3\2\2\2\2\13\3\2\2\2\2\r\3\2\2"+
		"\2\2\17\3\2\2\2\2\21\3\2\2\2\2\23\3\2\2\2\2\25\3\2\2\2\2\27\3\2\2\2\2"+
		"\31\3\2\2\2\2\33\3\2\2\2\2\35\3\2\2\2\2\37\3\2\2\2\2!\3\2\2\2\2#\3\2\2"+
		"\2\2%\3\2\2\2\2\'\3\2\2\2\2)\3\2\2\2\2+\3\2\2\2\2-\3\2\2\2\2/\3\2\2\2"+
		"\2\61\3\2\2\2\2\63\3\2\2\2\2\65\3\2\2\2\2\67\3\2\2\2\29\3\2\2\2\2;\3\2"+
		"\2\2\2=\3\2\2\2\2?\3\2\2\2\2A\3\2\2\2\2C\3\2\2\2\2E\3\2\2\2\2G\3\2\2\2"+
		"\2I\3\2\2\2\2K\3\2\2\2\2M\3\2\2\2\2O\3\2\2\2\2Q\3\2\2\2\2S\3\2\2\2\2U"+
		"\3\2\2\2\2W\3\2\2\2\2Y\3\2\2\2\2[\3\2\2\2\2]\3\2\2\2\2_\3\2\2\2\2a\3\2"+
		"\2\2\2c\3\2\2\2\2e\3\2\2\2\2g\3\2\2\2\2i\3\2\2\2\2k\3\2\2\2\2m\3\2\2\2"+
		"\2o\3\2\2\2\2q\3\2\2\2\2s\3\2\2\2\2u\3\2\2\2\2w\3\2\2\2\2y\3\2\2\2\2{"+
		"\3\2\2\2\3}\3\2\2\2\5\u0083\3\2\2\2\7\u0089\3\2\2\2\t\u0090\3\2\2\2\13"+
		"\u0098\3\2\2\2\r\u00a3\3\2\2\2\17\u00a7\3\2\2\2\21\u00ae\3\2\2\2\23\u00b3"+
		"\3\2\2\2\25\u00ba\3\2\2\2\27\u00c2\3\2\2\2\31\u00c6\3\2\2\2\33\u00cc\3"+
		"\2\2\2\35\u00d2\3\2\2\2\37\u00d5\3\2\2\2!\u00da\3\2\2\2#\u00e0\3\2\2\2"+
		"%\u00e2\3\2\2\2\'\u00e4\3\2\2\2)\u00e6\3\2\2\2+\u00e8\3\2\2\2-\u00eb\3"+
		"\2\2\2/\u00ee\3\2\2\2\61\u00f1\3\2\2\2\63\u00f3\3\2\2\2\65\u00f5\3\2\2"+
		"\2\67\u00f8\3\2\2\29\u00fa\3\2\2\2;\u00fd\3\2\2\2=\u0100\3\2\2\2?\u0102"+
		"\3\2\2\2A\u0104\3\2\2\2C\u0106\3\2\2\2E\u010b\3\2\2\2G\u0111\3\2\2\2I"+
		"\u0116\3\2\2\2K\u011d\3\2\2\2M\u0122\3\2\2\2O\u0126\3\2\2\2Q\u012b\3\2"+
		"\2\2S\u0130\3\2\2\2U\u0134\3\2\2\2W\u0139\3\2\2\2Y\u013c\3\2\2\2[\u013e"+
		"\3\2\2\2]\u0140\3\2\2\2_\u0142\3\2\2\2a\u0144\3\2\2\2c\u0146\3\2\2\2e"+
		"\u0148\3\2\2\2g\u014a\3\2\2\2i\u014c\3\2\2\2k\u014e\3\2\2\2m\u0150\3\2"+
		"\2\2o\u0154\3\2\2\2q\u015e\3\2\2\2s\u0160\3\2\2\2u\u0167\3\2\2\2w\u016e"+
		"\3\2\2\2y\u0179\3\2\2\2{\u018d\3\2\2\2}~\7\61\2\2~\177\7\61\2\2\177\u0080"+
		"\7\f\2\2\u0080\u0081\3\2\2\2\u0081\u0082\b\2\2\2\u0082\4\3\2\2\2\u0083"+
		"\u0084\7e\2\2\u0084\u0085\7n\2\2\u0085\u0086\7c\2\2\u0086\u0087\7u\2\2"+
		"\u0087\u0088\7u\2\2\u0088\6\3\2\2\2\u0089\u008a\7r\2\2\u008a\u008b\7w"+
		"\2\2\u008b\u008c\7d\2\2\u008c\u008d\7n\2\2\u008d\u008e\7k\2\2\u008e\u008f"+
		"\7e\2\2\u008f\b\3\2\2\2\u0090\u0091\7r\2\2\u0091\u0092\7t\2\2\u0092\u0093"+
		"\7k\2\2\u0093\u0094\7x\2\2\u0094\u0095\7c\2\2\u0095\u0096\7v\2\2\u0096"+
		"\u0097\7g\2\2\u0097\n\3\2\2\2\u0098\u0099\7k\2\2\u0099\u009a\7p\2\2\u009a"+
		"\u009b\7k\2\2\u009b\u009c\7v\2\2\u009c\u009d\7k\2\2\u009d\u009e\7c\2\2"+
		"\u009e\u009f\7n\2\2\u009f\u00a0\7k\2\2\u00a0\u00a1\7|\2\2\u00a1\u00a2"+
		"\7g\2\2\u00a2\f\3\2\2\2\u00a3\u00a4\7p\2\2\u00a4\u00a5\7g\2\2\u00a5\u00a6"+
		"\7y\2\2\u00a6\16\3\2\2\2\u00a7\u00a8\7t\2\2\u00a8\u00a9\7g\2\2\u00a9\u00aa"+
		"\7v\2\2\u00aa\u00ab\7w\2\2\u00ab\u00ac\7t\2\2\u00ac\u00ad\7p\2\2\u00ad"+
		"\20\3\2\2\2\u00ae\u00af\7x\2\2\u00af\u00b0\7q\2\2\u00b0\u00b1\7k\2\2\u00b1"+
		"\u00b2\7f\2\2\u00b2\22\3\2\2\2\u00b3\u00b4\7f\2\2\u00b4\u00b5\7g\2\2\u00b5"+
		"\u00b6\7n\2\2\u00b6\u00b7\7g\2\2\u00b7\u00b8\7v\2\2\u00b8\u00b9\7g\2\2"+
		"\u00b9\24\3\2\2\2\u00ba\u00bb\7k\2\2\u00bb\u00bc\7p\2\2\u00bc\u00bd\7"+
		"e\2\2\u00bd\u00be\7n\2\2\u00be\u00bf\7w\2\2\u00bf\u00c0\7f\2\2\u00c0\u00c1"+
		"\7g\2\2\u00c1\26\3\2\2\2\u00c2\u00c3\7c\2\2\u00c3\u00c4\7f\2\2\u00c4\u00c5"+
		"\7f\2\2\u00c5\30\3\2\2\2\u00c6\u00c7\7o\2\2\u00c7\u00c8\7g\2\2\u00c8\u00c9"+
		"\7t\2\2\u00c9\u00ca\7i\2\2\u00ca\u00cb\7g\2\2\u00cb\32\3\2\2\2\u00cc\u00cd"+
		"\7r\2\2\u00cd\u00ce\7t\2\2\u00ce\u00cf\7k\2\2\u00cf\u00d0\7p\2\2\u00d0"+
		"\u00d1\7v\2\2\u00d1\34\3\2\2\2\u00d2\u00d3\7k\2\2\u00d3\u00d4\7h\2\2\u00d4"+
		"\36\3\2\2\2\u00d5\u00d6\7g\2\2\u00d6\u00d7\7n\2\2\u00d7\u00d8\7u\2\2\u00d8"+
		"\u00d9\7g\2\2\u00d9 \3\2\2\2\u00da\u00db\7g\2\2\u00db\u00dc\7n\2\2\u00dc"+
		"\u00dd\7u\2\2\u00dd\u00de\7k\2\2\u00de\u00df\7h\2\2\u00df\"\3\2\2\2\u00e0"+
		"\u00e1\7-\2\2\u00e1$\3\2\2\2\u00e2\u00e3\7/\2\2\u00e3&\3\2\2\2\u00e4\u00e5"+
		"\7,\2\2\u00e5(\3\2\2\2\u00e6\u00e7\7\61\2\2\u00e7*\3\2\2\2\u00e8\u00e9"+
		"\7-\2\2\u00e9\u00ea\7-\2\2\u00ea,\3\2\2\2\u00eb\u00ec\7/\2\2\u00ec\u00ed"+
		"\7/\2\2\u00ed.\3\2\2\2\u00ee\u00ef\7?\2\2\u00ef\u00f0\7?\2\2\u00f0\60"+
		"\3\2\2\2\u00f1\u00f2\7@\2\2\u00f2\62\3\2\2\2\u00f3\u00f4\7>\2\2\u00f4"+
		"\64\3\2\2\2\u00f5\u00f6\7/\2\2\u00f6\u00f7\7@\2\2\u00f7\66\3\2\2\2\u00f8"+
		"\u00f9\7~\2\2\u00f98\3\2\2\2\u00fa\u00fb\7(\2\2\u00fb\u00fc\7(\2\2\u00fc"+
		":\3\2\2\2\u00fd\u00fe\7~\2\2\u00fe\u00ff\7~\2\2\u00ff<\3\2\2\2\u0100\u0101"+
		"\7#\2\2\u0101>\3\2\2\2\u0102\u0103\7A\2\2\u0103@\3\2\2\2\u0104\u0105\7"+
		"<\2\2\u0105B\3\2\2\2\u0106\u0107\7v\2\2\u0107\u0108\7t\2\2\u0108\u0109"+
		"\7w\2\2\u0109\u010a\7g\2\2\u010aD\3\2\2\2\u010b\u010c\7h\2\2\u010c\u010d"+
		"\7c\2\2\u010d\u010e\7n\2\2\u010e\u010f\7u\2\2\u010f\u0110\7g\2\2\u0110"+
		"F\3\2\2\2\u0111\u0112\7p\2\2\u0112\u0113\7w\2\2\u0113\u0114\7n\2\2\u0114"+
		"\u0115\7n\2\2\u0115H\3\2\2\2\u0116\u0117\7?\2\2\u0117\u0118\7d\2\2\u0118"+
		"\u0119\7g\2\2\u0119\u011a\7i\2\2\u011a\u011b\7k\2\2\u011b\u011c\7p\2\2"+
		"\u011cJ\3\2\2\2\u011d\u011e\7?\2\2\u011e\u011f\7g\2\2\u011f\u0120\7p\2"+
		"\2\u0120\u0121\7f\2\2\u0121L\3\2\2\2\u0122\u0123\7k\2\2\u0123\u0124\7"+
		"p\2\2\u0124\u0125\7v\2\2\u0125N\3\2\2\2\u0126\u0127\7d\2\2\u0127\u0128"+
		"\7q\2\2\u0128\u0129\7q\2\2\u0129\u012a\7n\2\2\u012aP\3\2\2\2\u012b\u012c"+
		"\7h\2\2\u012c\u012d\7r\2\2\u012d\u012e\7v\2\2\u012e\u012f\7t\2\2\u012f"+
		"R\3\2\2\2\u0130\u0131\7U\2\2\u0131\u0132\7g\2\2\u0132\u0133\7v\2\2\u0133"+
		"T\3\2\2\2\u0134\u0135\7g\2\2\u0135\u0136\7c\2\2\u0136\u0137\7e\2\2\u0137"+
		"\u0138\7j\2\2\u0138V\3\2\2\2\u0139\u013a\7f\2\2\u013a\u013b\7q\2\2\u013b"+
		"X\3\2\2\2\u013c\u013d\7?\2\2\u013dZ\3\2\2\2\u013e\u013f\7%\2\2\u013f\\"+
		"\3\2\2\2\u0140\u0141\7*\2\2\u0141^\3\2\2\2\u0142\u0143\7+\2\2\u0143`\3"+
		"\2\2\2\u0144\u0145\7]\2\2\u0145b\3\2\2\2\u0146\u0147\7_\2\2\u0147d\3\2"+
		"\2\2\u0148\u0149\7}\2\2\u0149f\3\2\2\2\u014a\u014b\7\177\2\2\u014bh\3"+
		"\2\2\2\u014c\u014d\7.\2\2\u014dj\3\2\2\2\u014e\u014f\7\60\2\2\u014fl\3"+
		"\2\2\2\u0150\u0151\7=\2\2\u0151\u0152\3\2\2\2\u0152\u0153\b\67\2\2\u0153"+
		"n\3\2\2\2\u0154\u0155\7\f\2\2\u0155p\3\2\2\2\u0156\u015f\7\62\2\2\u0157"+
		"\u015b\t\2\2\2\u0158\u015a\t\3\2\2\u0159\u0158\3\2\2\2\u015a\u015d\3\2"+
		"\2\2\u015b\u0159\3\2\2\2\u015b\u015c\3\2\2\2\u015c\u015f\3\2\2\2\u015d"+
		"\u015b\3\2\2\2\u015e\u0156\3\2\2\2\u015e\u0157\3\2\2\2\u015fr\3\2\2\2"+
		"\u0160\u0164\t\4\2\2\u0161\u0163\t\5\2\2\u0162\u0161\3\2\2\2\u0163\u0166"+
		"\3\2\2\2\u0164\u0162\3\2\2\2\u0164\u0165\3\2\2\2\u0165t\3\2\2\2\u0166"+
		"\u0164\3\2\2\2\u0167\u016b\t\6\2\2\u0168\u016a\t\5\2\2\u0169\u0168\3\2"+
		"\2\2\u016a\u016d\3\2\2\2\u016b\u0169\3\2\2\2\u016b\u016c\3\2\2\2\u016c"+
		"v\3\2\2\2\u016d\u016b\3\2\2\2\u016e\u0172\7%\2\2\u016f\u0171\13\2\2\2"+
		"\u0170\u016f\3\2\2\2\u0171\u0174\3\2\2\2\u0172\u0173\3\2\2\2\u0172\u0170"+
		"\3\2\2\2\u0173\u0175\3\2\2\2\u0174\u0172\3\2\2\2\u0175\u0176\7\f\2\2\u0176"+
		"\u0177\3\2\2\2\u0177\u0178\b<\2\2\u0178x\3\2\2\2\u0179\u017a\7?\2\2\u017a"+
		"\u017b\7d\2\2\u017b\u017c\7g\2\2\u017c\u017d\7i\2\2\u017d\u017e\7k\2\2"+
		"\u017e\u017f\7p\2\2\u017f\u0183\3\2\2\2\u0180\u0182\13\2\2\2\u0181\u0180"+
		"\3\2\2\2\u0182\u0185\3\2\2\2\u0183\u0184\3\2\2\2\u0183\u0181\3\2\2\2\u0184"+
		"\u0186\3\2\2\2\u0185\u0183\3\2\2\2\u0186\u0187\7?\2\2\u0187\u0188\7g\2"+
		"\2\u0188\u0189\7p\2\2\u0189\u018a\7f\2\2\u018a\u018b\3\2\2\2\u018b\u018c"+
		"\b=\2\2\u018cz\3\2\2\2\u018d\u018e\t\7\2\2\u018e\u018f\3\2\2\2\u018f\u0190"+
		"\b>\2\2\u0190|\3\2\2\2\t\2\u015b\u015e\u0164\u016b\u0172\u0183\3\b\2\2";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}