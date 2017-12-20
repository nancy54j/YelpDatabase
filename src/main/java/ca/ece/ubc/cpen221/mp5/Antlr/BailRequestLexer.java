package ca.ece.ubc.cpen221.mp5.Antlr;


import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.LexerNoViableAltException;

/**
 * Taken from definitive antlr guide
 *
 * Similar to BailErrorStrategy, but this time for throwing exceptions at the lexer level.
 * This is a simple task of extending to the normal Lexer and just overriding the method in which the
 * lexer tries to recover by just throwing an exception.
 */
public class BailRequestLexer extends RequestLexer{

    public BailRequestLexer(CharStream input) {
        super(input);
    }

    @Override
    public void recover(LexerNoViableAltException e) {
        throw new RuntimeException(e); // Bail out
    }
}
