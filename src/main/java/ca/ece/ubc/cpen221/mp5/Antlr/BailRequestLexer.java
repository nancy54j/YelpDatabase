package ca.ece.ubc.cpen221.mp5.Antlr;


import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.LexerNoViableAltException;

public class BailRequestLexer extends RequestLexer{

    public BailRequestLexer(CharStream input) {
        super(input);
    }

    public void recover(LexerNoViableAltException e) {
        throw new RuntimeException(e); // Bail out
    }
}
