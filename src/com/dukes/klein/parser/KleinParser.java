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

    @Override
    public boolean isValid() {
        Enum sToken = null;
        KleinToken kToken;
        KleinRule kRule;
        this.stack.push(NonTerminalType.PROGRAM);
        this.stack.push(KleinTokenType.EOF);
        while (!this.stack.empty()) {
            sToken = this.stack.pop();
            if (sToken instanceof KleinTokenType) {
                kToken = this.scanner.next();
                if (sToken == kToken.getTokenType()) {
                    this.stack.pop();
                } else {
                    // ERROR: TOKEN MISMATCH ERROR (sToken,kToken)
                    throw new ParsingException("Token mismatch!");
                }
            }
            if (sToken instanceof NonTerminalType) {
                kToken = this.scanner.peek();
                kRule = this.PARSETABLE.getRule((NonTerminalType) sToken, kToken.getTokenType());
                if (kRule.exists()){
                    this.stack.pop();
                    kRule.pushRule(this.stack);
                }
                else{
                    // ERROR: Invalid item in stack (sToken)
                    throw new ParsingException("Invalid item in stack!");
                }
            }
        }
        if (sToken != null && !sToken.equals(KleinTokenType.EOF)){
            // ERROR: Unexpected token at end of file
        }

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
