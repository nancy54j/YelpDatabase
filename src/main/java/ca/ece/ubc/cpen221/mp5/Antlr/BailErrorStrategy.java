package ca.ece.ubc.cpen221.mp5.Antlr;

import org.antlr.v4.runtime.*;
/**
 * Taken from definitive antlr4 reference, page 186:
 * This is used to modify the "error strategy" of antlr" - by default, antlr chooses to try to recover
 * from erroneous text met during the parsing stage, but because we do not care if the text doesn't match
 * the specific guidelines outlined in MP5 Part 5, we modify the strategy to just abandon parsing and throw
 * an exception when met with unparsable text.
 */

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
