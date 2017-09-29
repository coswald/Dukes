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
        pt.addRule(NonTerminalType.PROGRAM, TerminalType.FUNCTION, new KleinRule(new ArrayList<Enum>(Arrays.asList(
                   NonTerminalType.DEFINITIONS))));
        pt.addRule(NonTerminalType.DEFINITIONS, TerminalType.$, new KleinRule(new ArrayList<Enum>(Arrays.asList(
                   TerminalType.$))));
        pt.addRule(NonTerminalType.DEFINITIONS, TerminalType.FUNCTION, new KleinRule(new ArrayList<Enum>(Arrays.asList(
                   NonTerminalType.DEF, NonTerminalType.DEFINITIONS))));
        pt.addRule(NonTerminalType.DEF, TerminalType.FUNCTION, new KleinRule(new ArrayList<Enum>(Arrays.asList(
                   TerminalType.FUNCTION, KleinTokenType.IDENTIFIER, KleinTokenType.LEFTPARENTHESIS,
                   NonTerminalType.FORMALS, KleinTokenType.RIGHTPARENTHESIS, KleinTokenType.SEPARATOR,
                   NonTerminalType.TYPE, NonTerminalType.BODY))));
        pt.addRule(NonTerminalType.FORMALS, TerminalType.LEFTPAREN, new KleinRule(new ArrayList<Enum>(Arrays.asList(
                   TerminalType.EMPTY))));
        pt.addRule(NonTerminalType.FORMALS, TerminalType.STRING, new KleinRule(new ArrayList<Enum>(Arrays.asList(
                   NonTerminalType.NONEMPTYFORMALS))));
        pt.addRule(NonTerminalType.NONEMPTYFORMALS, TerminalType.STRING, new KleinRule(new ArrayList<Enum>(Arrays.asList(
                   NonTerminalType.FORMAL, NonTerminalType.NONEMPTYFORMALS_PRIME))));
        pt.addRule(NonTerminalType.NONEMPTYFORMALS_PRIME, TerminalType.RIGHTPAREN, new KleinRule(new ArrayList<Enum>(Arrays.asList(
                   TerminalType.EMPTY))));
        pt.addRule(NonTerminalType.NONEMPTYFORMALS_PRIME, TerminalType.COMMA, new KleinRule(new ArrayList<Enum>(Arrays.asList(
                   TerminalType.COMMA, NonTerminalType.NONEMPTYFORMALS))));
        pt.addRule(NonTerminalType.FORMAL, TerminalType.STRING, new KleinRule(new ArrayList<Enum>(Arrays.asList(
                   NonTerminalType.IDENTIFIER, KleinTokenType.SEPARATOR, NonTerminalType.TYPE))));
        pt.addRule(NonTerminalType.BODY, TerminalType.PRINT, new KleinRule(new ArrayList<Enum>(Arrays.asList(
                   NonTerminalType.PRINT_STATEMENT, NonTerminalType.BODY))));
        // <BODY> Rules
        pt.addRule(NonTerminalType.BODY, TerminalType.STRING, new KleinRule(new ArrayList<Enum>(Arrays.asList(
                NonTerminalType.EXPR))));
        pt.addRule(NonTerminalType.BODY, TerminalType.NUMBER, new KleinRule(new ArrayList<Enum>(Arrays.asList(
                NonTerminalType.EXPR))));
        pt.addRule(NonTerminalType.BODY, TerminalType.BOOLEAN, new KleinRule(new ArrayList<Enum>(Arrays.asList(
                NonTerminalType.EXPR))));
        pt.addRule(NonTerminalType.BODY, TerminalType.IF, new KleinRule(new ArrayList<Enum>(Arrays.asList(
                NonTerminalType.EXPR))));
        pt.addRule(NonTerminalType.BODY, TerminalType.LEFTPAREN, new KleinRule(new ArrayList<Enum>(Arrays.asList(
                NonTerminalType.EXPR))));
        pt.addRule(NonTerminalType.BODY, TerminalType.NOT, new KleinRule(new ArrayList<Enum>(Arrays.asList(
                NonTerminalType.EXPR))));
        pt.addRule(NonTerminalType.BODY, TerminalType.MINUS, new KleinRule(new ArrayList<Enum>(Arrays.asList(
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
