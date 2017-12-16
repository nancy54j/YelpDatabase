package ca.ece.ubc.cpen221.mp5.Antlr;

import org.antlr.v4.runtime.*;

//taken from definitive antlr4 reference, page 186, used to throw errors that occur during parsing
public class BailErrorStrategy extends DefaultErrorStrategy {
    @Override
    public void recover(Parser recognizer, RecognitionException e) {
        throw new RuntimeException(e);
    }
    @Override
    public Token recoverInline(Parser recognizer) throws RecognitionException {
        throw new RuntimeException(new InputMismatchException(recognizer));
    }
    @Override
    public void sync(Parser recognizer) { }
}
