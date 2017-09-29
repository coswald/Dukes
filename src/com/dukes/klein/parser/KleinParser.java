package com.dukes.klein.parser;

import com.dukes.klein.scanner.KleinScanner;
import com.dukes.klein.scanner.KleinToken;
import com.dukes.klein.scanner.KleinTokenType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Stack;


public class KleinParser extends AbstractTableParser<KleinScanner, KleinToken> {

    private Stack<Enum> stack;

    public KleinParser(KleinScanner ks) {
        ParseTable pt = new ParseTable();
        pt.addRule(NonTerminalType.PROGRAM, KleinTokenType.KEYWORD, new KleinRule(new ArrayList<Enum>(Arrays.asList(
                   NonTerminalType.DEFINITIONS))));
        pt.addRule(NonTerminalType.DEFINITIONS, KleinTokenType.EOF, new KleinRule(new ArrayList<Enum>(Arrays.asList(
                   KleinTokenType.EOF))));
        pt.addRule(NonTerminalType.DEFINITIONS, KleinTokenType.KEYWORD, new KleinRule(new ArrayList<Enum>(Arrays.asList(
                   NonTerminalType.DEF, NonTerminalType.DEFINITIONS))));
        pt.addRule(NonTerminalType.DEF, KleinTokenType.KEYWORD, new KleinRule(new ArrayList<Enum>(Arrays.asList(
                   KleinTokenType.KEYWORD, KleinTokenType.IDENTIFIER, KleinTokenType.LEFTPARENTHESIS,
                   NonTerminalType.FORMALS, KleinTokenType.RIGHTPARENTHESIS, KleinTokenType.SEPARATOR,
                   NonTerminalType.TYPE, NonTerminalType.BODY))));
        pt.addRule(NonTerminalType.FORMALS, KleinTokenType.LEFTPARENTHESIS, new KleinRule());
        pt.addRule(NonTerminalType.FORMALS, KleinTokenType.IDENTIFIER, new KleinRule(new ArrayList<Enum>(Arrays.asList(
                   NonTerminalType.NONEMPTYFORMALS))));
        pt.addRule(NonTerminalType.NONEMPTYFORMALS, KleinTokenType.IDENTIFIER, new KleinRule(new ArrayList<Enum>(Arrays.asList(
                   NonTerminalType.FORMAL, NonTerminalType.NONEMPTYFORMALS_PRIME))));
        pt.addRule(NonTerminalType.NONEMPTYFORMALS_PRIME, KleinTokenType.RIGHTPARENTHESIS, new KleinRule());
        pt.addRule(NonTerminalType.NONEMPTYFORMALS_PRIME, KleinTokenType.SEPARATOR, new KleinRule(new ArrayList<Enum>(Arrays.asList(
                   KleinTokenType.SEPARATOR, NonTerminalType.NONEMPTYFORMALS))));
        pt.addRule(NonTerminalType.FORMAL, KleinTokenType.IDENTIFIER, new KleinRule(new ArrayList<Enum>(Arrays.asList(
                   NonTerminalType.IDENTIFIER, KleinTokenType.SEPARATOR, NonTerminalType.TYPE))));
        // <BODY> → <PRINT-STATEMENT> <BODY> ** print should probably be a keyword token **
        pt.addRule(NonTerminalType.BODY, KleinTokenType.IDENTIFIER, new KleinRule(new ArrayList<Enum>(Arrays.asList(
                   NonTerminalType.PRINT_STATEMENT, NonTerminalType.BODY))));
        // <BODY> → <EXPR>
        pt.addRule(NonTerminalType.BODY, KleinTokenType.IDENTIFIER, new KleinRule(new ArrayList<Enum>(Arrays.asList(
                NonTerminalType.EXPR))));
        pt.addRule(NonTerminalType.BODY, KleinTokenType.INTEGER, new KleinRule(new ArrayList<Enum>(Arrays.asList(
                NonTerminalType.EXPR))));
        pt.addRule(NonTerminalType.BODY, KleinTokenType.BOOLEAN, new KleinRule(new ArrayList<Enum>(Arrays.asList(
                NonTerminalType.EXPR))));
        pt.addRule(NonTerminalType.BODY, KleinTokenType.IF, new KleinRule(new ArrayList<Enum>(Arrays.asList(
                NonTerminalType.EXPR))));
        pt.addRule(NonTerminalType.BODY, KleinTokenType.LEFTPARENTHESIS, new KleinRule(new ArrayList<Enum>(Arrays.asList(
                NonTerminalType.EXPR))));
        pt.addRule(NonTerminalType.BODY, KleinTokenType.NOT, new KleinRule(new ArrayList<Enum>(Arrays.asList(
                NonTerminalType.EXPR))));
        pt.addRule(NonTerminalType.BODY, KleinTokenType.SIMPLEEXPRESSION, new KleinRule(new ArrayList<Enum>(Arrays.asList(
                NonTerminalType.EXPR))));
        
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
                }
            }
            if (sToken instanceof NonTerminalType) {
                kToken = this.scanner.peek();
                kRule = this.PARSETABLE.getRule((NonTerminalType) sToken, kToken.getTokenType());
                if (kRule != null){
                    this.stack.pop();
                    kRule.pushRule(this.stack);
                }
                else{
                    // ERROR: Invalid item in stack (sToken)
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
