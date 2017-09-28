package com.dukes.klein.parser;

import com.dukes.klein.scanner.KleinScanner;
import com.dukes.klein.scanner.KleinToken;


public class KleinParser extends AbstractTableParser<KleinScanner, KleinToken> {

    public KleinParser(KleinScanner ks) {
        super(ks, null, null);//Parse table will go here, along with rules
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
