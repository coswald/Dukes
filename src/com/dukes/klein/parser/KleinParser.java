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
        pt.addRule(NonTerminalType.DEFINITIONS, KleinTokenType.EOF, new KleinRule());
        pt.addRule(NonTerminalType.DEFINITIONS, KleinTokenType.KEYWORD, new KleinRule(new ArrayList<Enum>(Arrays.asList(
                   NonTerminalType.DEF, NonTerminalType.DEFINITIONS))));
        pt.addRule(NonTerminalType.DEF, KleinTokenType.KEYWORD, new KleinRule(new ArrayList<Enum>(Arrays.asList(
                   KleinTokenType.KEYWORD, KleinTokenType.IDENTIFIER, KleinTokenType.LEFTPARENTHESIS,
                   NonTerminalType.FORMALS, KleinTokenType.RIGHTPARENTHESIS, KleinTokenType.SEPARATOR,
                   NonTerminalType.TYPE, NonTerminalType.BODY))));
        pt.addRule(NonTerminalType.FORMALS, KleinTokenType.RIGHTPARENTHESIS, new KleinRule());
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
        // These rules are written the same
        //<TYPE> → integer
        //<TYPE> → boolean
        pt.addRule(NonTerminalType.TYPE, KleinTokenType.TYPE, new KleinRule(new ArrayList<Enum>(Arrays.asList(
                KleinTokenType.TYPE))));
        // <EXPR> → <SIMPLE-EXPR> <EXPR-END>
        pt.addRule(NonTerminalType.EXPR, KleinTokenType.IF, new KleinRule(new ArrayList<Enum>(Arrays.asList(
                NonTerminalType.SIMPLE_EXPR, NonTerminalType.EXPR_END, NonTerminalType.EXPR))));
        pt.addRule(NonTerminalType.EXPR, KleinTokenType.LEFTPARENTHESIS, new KleinRule(new ArrayList<Enum>(Arrays.asList(
                NonTerminalType.SIMPLE_EXPR, NonTerminalType.EXPR_END, NonTerminalType.EXPR))));
        pt.addRule(NonTerminalType.EXPR, KleinTokenType.NOT, new KleinRule(new ArrayList<Enum>(Arrays.asList(
                NonTerminalType.SIMPLE_EXPR, NonTerminalType.EXPR_END, NonTerminalType.EXPR))));
        pt.addRule(NonTerminalType.EXPR, KleinTokenType.SIMPLEEXPRESSION, new KleinRule(new ArrayList<Enum>(Arrays.asList(
                NonTerminalType.SIMPLE_EXPR, NonTerminalType.EXPR_END, NonTerminalType.EXPR))));
        pt.addRule(NonTerminalType.EXPR, KleinTokenType.INTEGER, new KleinRule(new ArrayList<Enum>(Arrays.asList(
                NonTerminalType.SIMPLE_EXPR, NonTerminalType.EXPR_END, NonTerminalType.EXPR))));
        pt.addRule(NonTerminalType.EXPR, KleinTokenType.BOOLEAN, new KleinRule(new ArrayList<Enum>(Arrays.asList(
                NonTerminalType.SIMPLE_EXPR, NonTerminalType.EXPR_END, NonTerminalType.EXPR))));
        pt.addRule(NonTerminalType.EXPR, KleinTokenType.IDENTIFIER, new KleinRule(new ArrayList<Enum>(Arrays.asList(
                NonTerminalType.SIMPLE_EXPR, NonTerminalType.EXPR_END, NonTerminalType.EXPR))));
        //<EXPR-END> → ε:
        pt.addRule(NonTerminalType.EXPR_END, KleinTokenType.RIGHTPARENTHESIS, new KleinRule());
        pt.addRule(NonTerminalType.EXPR_END, KleinTokenType.SEPARATOR, new KleinRule());
        pt.addRule(NonTerminalType.EXPR_END, KleinTokenType.THEN, new KleinRule());
        pt.addRule(NonTerminalType.EXPR_END, KleinTokenType.ELSE, new KleinRule());
        pt.addRule(NonTerminalType.EXPR_END, KleinTokenType.TERM, new KleinRule());
        pt.addRule(NonTerminalType.EXPR_END, KleinTokenType.KEYWORD, new KleinRule());
        pt.addRule(NonTerminalType.EXPR_END, KleinTokenType.EOF, new KleinRule());
        pt.addRule(NonTerminalType.EXPR_END, KleinTokenType.SIMPLEEXPRESSION, new KleinRule());
        // <EXPR-END> → <EXPR-SYMBOL> <EXPR>
        pt.addRule(NonTerminalType.EXPR_END, KleinTokenType.EXPRESSION, new KleinRule());
        //<SIMPLE-EXPR> → <TERM> <SIMPLE-EXPR-END>
        pt.addRule(NonTerminalType.SIMPLE_EXPR, KleinTokenType.IF, new KleinRule(new ArrayList<Enum>(Arrays.asList(
                NonTerminalType.TERM, NonTerminalType.SIMPLE_EXPR_END, NonTerminalType.SIMPLE_EXPR))));
        pt.addRule(NonTerminalType.SIMPLE_EXPR, KleinTokenType.LEFTPARENTHESIS, new KleinRule(new ArrayList<Enum>(Arrays.asList(
                NonTerminalType.TERM, NonTerminalType.SIMPLE_EXPR_END, NonTerminalType.SIMPLE_EXPR))));
        pt.addRule(NonTerminalType.SIMPLE_EXPR, KleinTokenType.NOT, new KleinRule(new ArrayList<Enum>(Arrays.asList(
                NonTerminalType.TERM, NonTerminalType.SIMPLE_EXPR_END, NonTerminalType.SIMPLE_EXPR))));
        pt.addRule(NonTerminalType.SIMPLE_EXPR, KleinTokenType.SIMPLEEXPRESSION, new KleinRule(new ArrayList<Enum>(Arrays.asList(
                NonTerminalType.TERM, NonTerminalType.SIMPLE_EXPR_END, NonTerminalType.SIMPLE_EXPR))));
        pt.addRule(NonTerminalType.SIMPLE_EXPR, KleinTokenType.INTEGER, new KleinRule(new ArrayList<Enum>(Arrays.asList(
                NonTerminalType.TERM, NonTerminalType.SIMPLE_EXPR_END, NonTerminalType.SIMPLE_EXPR))));
        pt.addRule(NonTerminalType.SIMPLE_EXPR, KleinTokenType.BOOLEAN, new KleinRule(new ArrayList<Enum>(Arrays.asList(
                NonTerminalType.TERM, NonTerminalType.SIMPLE_EXPR_END, NonTerminalType.SIMPLE_EXPR))));
        pt.addRule(NonTerminalType.SIMPLE_EXPR, KleinTokenType.IDENTIFIER, new KleinRule(new ArrayList<Enum>(Arrays.asList(
                NonTerminalType.TERM, NonTerminalType.SIMPLE_EXPR_END, NonTerminalType.SIMPLE_EXPR))));
        //<SIMPLE-EXPR-END> → ε
        pt.addRule(NonTerminalType.SIMPLE_EXPR_END, KleinTokenType.EXPRESSION, new KleinRule());
        pt.addRule(NonTerminalType.SIMPLE_EXPR_END, KleinTokenType.RIGHTPARENTHESIS, new KleinRule());
        pt.addRule(NonTerminalType.SIMPLE_EXPR_END, KleinTokenType.SEPARATOR, new KleinRule());
        pt.addRule(NonTerminalType.SIMPLE_EXPR_END, KleinTokenType.THEN, new KleinRule());
        pt.addRule(NonTerminalType.SIMPLE_EXPR_END, KleinTokenType.ELSE, new KleinRule());
        pt.addRule(NonTerminalType.SIMPLE_EXPR_END, KleinTokenType.TERM, new KleinRule());
        pt.addRule(NonTerminalType.SIMPLE_EXPR_END, KleinTokenType.KEYWORD, new KleinRule());
        pt.addRule(NonTerminalType.SIMPLE_EXPR_END, KleinTokenType.EOF, new KleinRule());
        pt.addRule(NonTerminalType.SIMPLE_EXPR_END, KleinTokenType.SIMPLEEXPRESSION, new KleinRule());

        // <SIMPLE-EXPR-END> → <SIMPLE-EXPR-SYMBOL> <SIMPLE-EXPR>
        pt.addRule(NonTerminalType.SIMPLE_EXPR_END, KleinTokenType.SIMPLEEXPRESSION, new KleinRule(new ArrayList<Enum>(Arrays.asList(
                NonTerminalType.SIMPLE_EXPR_SYMBOL, NonTerminalType.SIMPLE_EXPR))));

        //<TERM> → <FACTOR> <TERM-END>
        pt.addRule(NonTerminalType.TERM, KleinTokenType.IF, new KleinRule(new ArrayList<Enum>(Arrays.asList(
                NonTerminalType.FACTOR, NonTerminalType.TERM_END))));
        pt.addRule(NonTerminalType.TERM, KleinTokenType.LEFTPARENTHESIS, new KleinRule(new ArrayList<Enum>(Arrays.asList(
                NonTerminalType.FACTOR, NonTerminalType.TERM_END))));
        pt.addRule(NonTerminalType.TERM, KleinTokenType.NOT, new KleinRule(new ArrayList<Enum>(Arrays.asList(
                NonTerminalType.FACTOR, NonTerminalType.TERM_END))));
        pt.addRule(NonTerminalType.TERM, KleinTokenType.SIMPLEEXPRESSION, new KleinRule(new ArrayList<Enum>(Arrays.asList(
                NonTerminalType.FACTOR, NonTerminalType.TERM_END))));
        pt.addRule(NonTerminalType.TERM, KleinTokenType.INTEGER, new KleinRule(new ArrayList<Enum>(Arrays.asList(
                NonTerminalType.FACTOR, NonTerminalType.TERM_END))));
        pt.addRule(NonTerminalType.TERM, KleinTokenType.BOOLEAN, new KleinRule(new ArrayList<Enum>(Arrays.asList(
                NonTerminalType.FACTOR, NonTerminalType.TERM_END))));
        pt.addRule(NonTerminalType.TERM, KleinTokenType.IDENTIFIER, new KleinRule(new ArrayList<Enum>(Arrays.asList(
                NonTerminalType.FACTOR, NonTerminalType.TERM_END))));

        //<TERM-END> → ε
        pt.addRule(NonTerminalType.TERM_END, KleinTokenType.SIMPLEEXPRESSION, new KleinRule());
        pt.addRule(NonTerminalType.TERM_END, KleinTokenType.EXPRESSION, new KleinRule());
        pt.addRule(NonTerminalType.TERM_END, KleinTokenType.RIGHTPARENTHESIS, new KleinRule());
        pt.addRule(NonTerminalType.TERM_END, KleinTokenType.SEPARATOR, new KleinRule());
        pt.addRule(NonTerminalType.TERM_END, KleinTokenType.THEN, new KleinRule());
        pt.addRule(NonTerminalType.TERM_END, KleinTokenType.ELSE, new KleinRule());
        pt.addRule(NonTerminalType.TERM_END, KleinTokenType.TERM, new KleinRule());
        pt.addRule(NonTerminalType.TERM_END, KleinTokenType.KEYWORD, new KleinRule());
        pt.addRule(NonTerminalType.TERM_END, KleinTokenType.EOF, new KleinRule());

        //<TERM-END> → <TERM-SYMBOL> <TERM>
        pt.addRule(NonTerminalType.TERM_END, KleinTokenType.TERM, new KleinRule(new ArrayList<Enum>(Arrays.asList(
                NonTerminalType.TERM_SYMBOL, NonTerminalType.TERM))));

        //<FACTOR> → if <EXPR> then <EXPR> else <EXPR>
        pt.addRule(NonTerminalType.FACTOR, KleinTokenType.IF, new KleinRule(new ArrayList<Enum>(Arrays.asList(
                KleinTokenType.IF, NonTerminalType.EXPR, KleinTokenType.THEN, NonTerminalType.EXPR,
                KleinTokenType.ELSE, NonTerminalType.EXPR))));
        //<FACTOR> → <FACTOR-SYMBOL> <FACTOR>
        pt.addRule(NonTerminalType.FACTOR, KleinTokenType.SIMPLEEXPRESSION, new KleinRule(new ArrayList<Enum>(Arrays.asList(
                NonTerminalType.FACTOR_SYMBOL, NonTerminalType.FACTOR))));
        pt.addRule(NonTerminalType.FACTOR, KleinTokenType.NOT, new KleinRule(new ArrayList<Enum>(Arrays.asList(
                NonTerminalType.FACTOR_SYMBOL, NonTerminalType.FACTOR))));
        //<FACTOR> → <IDENTIFIER> <FACTOR-END>
        pt.addRule(NonTerminalType.FACTOR, KleinTokenType.IDENTIFIER, new KleinRule(new ArrayList<Enum>(Arrays.asList(
                NonTerminalType.IDENTIFIER, NonTerminalType.FACTOR_END))));
        //<FACTOR> → <LITERAL>
        pt.addRule(NonTerminalType.FACTOR, KleinTokenType.INTEGER, new KleinRule(new ArrayList<Enum>(Arrays.asList(
                NonTerminalType.LITERAL))));
        pt.addRule(NonTerminalType.FACTOR, KleinTokenType.BOOLEAN, new KleinRule(new ArrayList<Enum>(Arrays.asList(
                NonTerminalType.LITERAL))));
        //<FACTOR> → ( <EXPR> )
        pt.addRule(NonTerminalType.FACTOR, KleinTokenType.LEFTPARENTHESIS, new KleinRule(new ArrayList<Enum>(Arrays.asList(
                KleinTokenType.LEFTPARENTHESIS, NonTerminalType.EXPR, KleinTokenType.RIGHTPARENTHESIS))));

        //<FACTOR-END> → ε
        pt.addRule(NonTerminalType.FACTOR_END, KleinTokenType.TERM, new KleinRule());
        pt.addRule(NonTerminalType.FACTOR_END, KleinTokenType.SIMPLEEXPRESSION, new KleinRule());
        pt.addRule(NonTerminalType.FACTOR_END, KleinTokenType.EXPRESSION, new KleinRule());
        pt.addRule(NonTerminalType.FACTOR_END, KleinTokenType.RIGHTPARENTHESIS, new KleinRule());
        pt.addRule(NonTerminalType.FACTOR_END, KleinTokenType.SEPARATOR, new KleinRule());
        pt.addRule(NonTerminalType.FACTOR_END, KleinTokenType.THEN, new KleinRule());
        pt.addRule(NonTerminalType.FACTOR_END, KleinTokenType.ELSE, new KleinRule());
        pt.addRule(NonTerminalType.FACTOR_END, KleinTokenType.KEYWORD, new KleinRule());
        pt.addRule(NonTerminalType.FACTOR_END, KleinTokenType.EOF, new KleinRule());
        //<FACTOR-END> → ( <ACTUALS> )
        pt.addRule(NonTerminalType.FACTOR_END, KleinTokenType.LEFTPARENTHESIS, new KleinRule(new ArrayList<Enum>(Arrays.asList(
                KleinTokenType.LEFTPARENTHESIS, NonTerminalType.ACTUALS, KleinTokenType.RIGHTPARENTHESIS))));

        //<FACTOR-SYMBOL> → not
        pt.addRule(NonTerminalType.FACTOR_SYMBOL, KleinTokenType.NOT, new KleinRule(new ArrayList<Enum>(Arrays.asList(
                KleinTokenType.NOT))));
        //<FACTOR-SYMBOL> → -
        pt.addRule(NonTerminalType.FACTOR_SYMBOL, KleinTokenType.SIMPLEEXPRESSION, new KleinRule(new ArrayList<Enum>(Arrays.asList(
                KleinTokenType.SIMPLEEXPRESSION))));

        //<TERM-SYMBOL> → and, <TERM-SYMBOL> → *, <TERM-SYMBOL> → /
        pt.addRule(NonTerminalType.TERM_SYMBOL, KleinTokenType.TERM, new KleinRule(new ArrayList<Enum>(Arrays.asList(
                KleinTokenType.TERM))));
        pt.addRule(NonTerminalType.TERM_SYMBOL, KleinTokenType.TERM, new KleinRule(new ArrayList<Enum>(Arrays.asList(
                KleinTokenType.TERM))));
        pt.addRule(NonTerminalType.TERM_SYMBOL, KleinTokenType.TERM, new KleinRule(new ArrayList<Enum>(Arrays.asList(
                KleinTokenType.TERM))));

        //<ACTUALS> → ε
        pt.addRule(NonTerminalType.ACTUALS, KleinTokenType.RIGHTPARENTHESIS, new KleinRule());
        //<ACTUALS> → <NONEMPTYACTUALS>
        pt.addRule(NonTerminalType.ACTUALS, KleinTokenType.IF, new KleinRule(new ArrayList<Enum>(Arrays.asList(
                NonTerminalType.NONEMPTYACTUALS))));
        pt.addRule(NonTerminalType.ACTUALS, KleinTokenType.LEFTPARENTHESIS, new KleinRule(new ArrayList<Enum>(Arrays.asList(
                NonTerminalType.NONEMPTYACTUALS))));
        pt.addRule(NonTerminalType.ACTUALS, KleinTokenType.NOT, new KleinRule(new ArrayList<Enum>(Arrays.asList(
                NonTerminalType.NONEMPTYACTUALS))));
        pt.addRule(NonTerminalType.ACTUALS, KleinTokenType.SIMPLEEXPRESSION, new KleinRule(new ArrayList<Enum>(Arrays.asList(
                NonTerminalType.NONEMPTYACTUALS))));
        pt.addRule(NonTerminalType.ACTUALS, KleinTokenType.INTEGER, new KleinRule(new ArrayList<Enum>(Arrays.asList(
                NonTerminalType.NONEMPTYACTUALS))));
        pt.addRule(NonTerminalType.ACTUALS, KleinTokenType.BOOLEAN, new KleinRule(new ArrayList<Enum>(Arrays.asList(
                NonTerminalType.NONEMPTYACTUALS))));
        pt.addRule(NonTerminalType.ACTUALS, KleinTokenType.IDENTIFIER, new KleinRule(new ArrayList<Enum>(Arrays.asList(
                NonTerminalType.NONEMPTYACTUALS))));

        //<NONEMPTYACTUALS> → <EXPR> <NONEMPTYACTUALS'>
        pt.addRule(NonTerminalType.NONEMPTYACTUALS, KleinTokenType.IF, new KleinRule(new ArrayList<Enum>(Arrays.asList(
                NonTerminalType.EXPR, NonTerminalType.NONEMPTYACTUALS_PRIME))));
        pt.addRule(NonTerminalType.NONEMPTYACTUALS, KleinTokenType.LEFTPARENTHESIS, new KleinRule(new ArrayList<Enum>(Arrays.asList(
                NonTerminalType.EXPR, NonTerminalType.NONEMPTYACTUALS_PRIME))));
        pt.addRule(NonTerminalType.NONEMPTYACTUALS, KleinTokenType.NOT, new KleinRule(new ArrayList<Enum>(Arrays.asList(
                NonTerminalType.EXPR, NonTerminalType.NONEMPTYACTUALS_PRIME))));
        pt.addRule(NonTerminalType.NONEMPTYACTUALS, KleinTokenType.SIMPLEEXPRESSION, new KleinRule(new ArrayList<Enum>(Arrays.asList(
                NonTerminalType.EXPR, NonTerminalType.NONEMPTYACTUALS_PRIME))));
        pt.addRule(NonTerminalType.NONEMPTYACTUALS, KleinTokenType.INTEGER, new KleinRule(new ArrayList<Enum>(Arrays.asList(
                NonTerminalType.EXPR, NonTerminalType.NONEMPTYACTUALS_PRIME))));
        pt.addRule(NonTerminalType.NONEMPTYACTUALS, KleinTokenType.BOOLEAN, new KleinRule(new ArrayList<Enum>(Arrays.asList(
                NonTerminalType.EXPR, NonTerminalType.NONEMPTYACTUALS_PRIME))));
        pt.addRule(NonTerminalType.NONEMPTYACTUALS, KleinTokenType.IDENTIFIER, new KleinRule(new ArrayList<Enum>(Arrays.asList(
                NonTerminalType.EXPR, NonTerminalType.NONEMPTYACTUALS_PRIME))));

        //<NONEMPTYACTUALS'> → ε
        pt.addRule(NonTerminalType.NONEMPTYACTUALS_PRIME, KleinTokenType.RIGHTPARENTHESIS, new KleinRule());
        //<NONEMPTYACTUALS'> → , <NONEMPTYACTUALS>
        pt.addRule(NonTerminalType.NONEMPTYACTUALS_PRIME, KleinTokenType.SEPARATOR, new KleinRule(new ArrayList<Enum>(Arrays.asList(
                NonTerminalType.NONEMPTYACTUALS))));

        //<LITERAL> → <NUMBER>
        pt.addRule(NonTerminalType.LITERAL, KleinTokenType.INTEGER, new KleinRule(new ArrayList<Enum>(Arrays.asList(
                KleinTokenType.INTEGER))));
        pt.addRule(NonTerminalType.LITERAL, KleinTokenType.BOOLEAN, new KleinRule(new ArrayList<Enum>(Arrays.asList(
                KleinTokenType.BOOLEAN))));
        //<IDENTIFIER> → <STRING>
        pt.addRule(NonTerminalType.IDENTIFIER, KleinTokenType.IDENTIFIER, new KleinRule(new ArrayList<Enum>(Arrays.asList(
                KleinTokenType.IDENTIFIER))));

        //<PRINT-STATEMENT> → print ( <EXPR> )
        pt.addRule(NonTerminalType.PRINT_STATEMENT, KleinTokenType.IDENTIFIER, new KleinRule(new ArrayList<Enum>(Arrays.asList(
                KleinTokenType.IDENTIFIER, KleinTokenType.LEFTPARENTHESIS, NonTerminalType.EXPR, KleinTokenType.RIGHTPARENTHESIS))));

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
