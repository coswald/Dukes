package com.dukes.klein.parser;

import com.dukes.klein.scanner.AbstractScanner;
import com.dukes.klein.scanner.AbstractToken;

import java.util.List;

public abstract class AbstractTableParser<E extends AbstractScanner<F>, F extends AbstractToken> implements Parser {
    protected final List<List<Integer>> PARSETABLE;
    protected final List<AbstractRule> RULES;

    protected E scanner;
    private boolean isValidProgram;
    private boolean hasParsed = false;
    private AbstractSyntaxNode ast;

    protected AbstractTableParser(E scanner, List<List<Integer>> PARSETABLE, List<AbstractRule> RULES){
        this.scanner = scanner;
        this.PARSETABLE = PARSETABLE;
        this.RULES = RULES;
    }

    @Override
    public boolean isValid() {
        if(!hasParsed) {
            isValidProgram = this.generateAST() != null;
        }
        return isValidProgram;
    }

    @Override
    public abstract AbstractSyntaxNode generateAST();

    protected abstract AbstractSyntaxNode parseState();

}
