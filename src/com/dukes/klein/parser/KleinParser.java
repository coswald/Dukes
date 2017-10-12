package com.dukes.klein.parser;

import com.dukes.klein.scanner.KleinScanner;
import com.dukes.klein.scanner.KleinToken;
import com.dukes.klein.scanner.KleinTokenType;

import java.util.Stack;


public class KleinParser extends AbstractTableParser<KleinScanner, KleinToken> {

    private Stack<Enum> stack;

    public KleinParser(KleinScanner ks) {
        ParseTable pt = new ParseTable();

        this.scanner = ks;
        this.PARSETABLE = pt;
        this.stack = new Stack<>();
    }

    private void parseProgram() throws ParsingException {
        Enum sToken = null;
        KleinToken kToken;
        KleinRule kRule;
        this.stack.push(KleinTokenType.EOF);
        this.stack.push(NonTerminalType.PROGRAM);
        while (!this.stack.empty()) {
            sToken = this.stack.pop();
            if (sToken instanceof KleinTokenType) {
                kToken = this.scanner.next();
                if (sToken != kToken.getTokenType()) {
                    throw new ParsingException(String.format(
                            "Token mismatch! Expected '%s' but got '%s'", sToken.name(), kToken.getTokenType().name()));
                }
            }
            else if (sToken instanceof NonTerminalType) {
                kToken = this.scanner.peek();
                kRule = this.PARSETABLE.getRule((NonTerminalType) sToken, kToken.getTokenType());
                if (kRule.exists()){
                    kRule.pushRule(this.stack);
                }
                else{
                    throw new ParsingException(String.format("Invalid item '%s' found on the stack!", sToken.name()));
                }
            }
        }
        if (sToken != null && !sToken.equals(KleinTokenType.EOF)){
            throw new ParsingException(String.format("Unexpected token '%s' at end of file", sToken.name()));
        }
    }


    @Override
    public boolean isValid() {
        this.parseProgram();
        // If we made it to here then this program is valid.
        return true;
    }

    @Override
    public AbstractSyntaxNode generateAST() {
        return null;
    }

    @Override
    protected AbstractSyntaxNode parseState() {
        return null;
    }
}
