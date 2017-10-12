package com.dukes.klein.parser;

import com.dukes.klein.scanner.KleinScanner;
import com.dukes.klein.scanner.KleinToken;
import com.dukes.klein.scanner.KleinTokenType;
import com.sun.javafx.scene.control.TableColumnComparatorBase;
import jdk.management.resource.ThrottledMeter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Stack;


public class KleinParser extends AbstractTableParser<KleinScanner, KleinToken> {

    private Stack<Enum> stack;

    public KleinParser(KleinScanner ks) {
        ParseTable pt = new ParseTable();
        // <PROGRAM> → <DEFINITIONS> ::= function
        pt.addRule(NonTerminalType.PROGRAM, TerminalType.FUNCTION, new KleinRule(new ArrayList<Enum>(Arrays.asList(
                NonTerminalType.DEFINITIONS))));
        // <DEFINITIONS> → ε ::= $
        // <DEFINITIONS> → <DEF> <DEFINITIONS> ::= function
        pt.addRule(NonTerminalType.DEFINITIONS, TerminalType.EOF, new KleinRule(new ArrayList<Enum>()));
        pt.addRule(NonTerminalType.DEFINITIONS, TerminalType.FUNCTION, new KleinRule(new ArrayList<Enum>(Arrays.asList(
                NonTerminalType.DEF, NonTerminalType.DEFINITIONS))));
        // <DEF> → function <IDENTIFIER> ( <FORMALS> ) : <TYPE> <BODY> ::= function
        pt.addRule(NonTerminalType.DEF, TerminalType.FUNCTION, new KleinRule(new ArrayList<Enum>(Arrays.asList(
                TerminalType.FUNCTION, TerminalType.STRING, TerminalType.LEFTPAREN,
                NonTerminalType.FORMALS, TerminalType.RIGHTPAREN, TerminalType.COLON,
                NonTerminalType.TYPE, NonTerminalType.BODY))));
        // <FORMALS> → ε ::= )
        pt.addRule(NonTerminalType.FORMALS, TerminalType.RIGHTPAREN, new KleinRule(new ArrayList<Enum>()));
        // <FORMALS> → <NONEMPTYFORMALS> ::= <STRING>
        pt.addRule(NonTerminalType.FORMALS, TerminalType.STRING, new KleinRule(new ArrayList<Enum>(Arrays.asList(
                NonTerminalType.NONEMPTYFORMALS))));
        // <NONEMPTYFORMALS> → <FORMAL> <NONEMPTYFORMALS_PRIME> ::= <STRING>
        pt.addRule(NonTerminalType.NONEMPTYFORMALS, TerminalType.STRING, new KleinRule(new ArrayList<Enum>(Arrays.asList(
                NonTerminalType.FORMAL, NonTerminalType.NONEMPTYFORMALS_PRIME))));
        // <NONEMPTYFORMALS_PRIME> → ε ::= )
        // <NONEMPTYFORMALS_PRIME> → , <NONEMPTYFORMALS> ::= ,
        pt.addRule(NonTerminalType.NONEMPTYFORMALS_PRIME, TerminalType.RIGHTPAREN, new KleinRule(new ArrayList<Enum>()));
        pt.addRule(NonTerminalType.NONEMPTYFORMALS_PRIME, TerminalType.COMMA, new KleinRule(new ArrayList<Enum>(Arrays.asList(
                TerminalType.COMMA, NonTerminalType.NONEMPTYFORMALS))));
        // <FORMAL> → <IDENTIFIER> : <TYPE> ::= <STRING>
        pt.addRule(NonTerminalType.FORMAL, TerminalType.STRING, new KleinRule(new ArrayList<Enum>(Arrays.asList(
                NonTerminalType.IDENTIFIER, TerminalType.COLON, NonTerminalType.TYPE))));
        // <BODY> → <PRINT-STATEMENT> <BODY> ::= print
        pt.addRule(NonTerminalType.BODY, TerminalType.PRINT, new KleinRule(new ArrayList<Enum>(Arrays.asList(
                NonTerminalType.PRINT_STATEMENT, NonTerminalType.BODY))));
        // <BODY> → <EXPR> ::= if, (, not, -, <NUMBER>, <BOOLEAN>, <STRING>
        pt.addRule(NonTerminalType.BODY, TerminalType.IF, new KleinRule(new ArrayList<Enum>(Arrays.asList(
                NonTerminalType.EXPR))));
        pt.addRule(NonTerminalType.BODY, TerminalType.LEFTPAREN, new KleinRule(new ArrayList<Enum>(Arrays.asList(
                NonTerminalType.EXPR))));
        pt.addRule(NonTerminalType.BODY, TerminalType.NOT, new KleinRule(new ArrayList<Enum>(Arrays.asList(
                NonTerminalType.EXPR))));
        pt.addRule(NonTerminalType.BODY, TerminalType.MINUS, new KleinRule(new ArrayList<Enum>(Arrays.asList(
                NonTerminalType.EXPR))));
        pt.addRule(NonTerminalType.BODY, TerminalType.NUMBER, new KleinRule(new ArrayList<Enum>(Arrays.asList(
                NonTerminalType.EXPR))));
        pt.addRule(NonTerminalType.BODY, TerminalType.BOOLEAN, new KleinRule(new ArrayList<Enum>(Arrays.asList(
                NonTerminalType.EXPR))));
        pt.addRule(NonTerminalType.BODY, TerminalType.STRING, new KleinRule(new ArrayList<Enum>(Arrays.asList(
                NonTerminalType.EXPR))));
        //<TYPE> → integer ::= integer
        pt.addRule(NonTerminalType.TYPE, TerminalType.INTEGERSTR, new KleinRule(new ArrayList<Enum>(Arrays.asList(
                TerminalType.INTEGERSTR))));
        //<TYPE> → boolean ::= boolean
        pt.addRule(NonTerminalType.TYPE, TerminalType.BOOLEANSTR, new KleinRule(new ArrayList<Enum>(Arrays.asList(
                TerminalType.BOOLEANSTR))));
        // <EXPR> → <SIMPLE-EXPR> <EXPR-END> ::= if, (, not, -, <NUMBER>, <BOOLEAN>, <STRING>
        pt.addRule(NonTerminalType.EXPR, TerminalType.IF, new KleinRule(new ArrayList<Enum>(Arrays.asList(
                NonTerminalType.SIMPLE_EXPR, NonTerminalType.EXPR_END))));
        pt.addRule(NonTerminalType.EXPR, TerminalType.LEFTPAREN, new KleinRule(new ArrayList<Enum>(Arrays.asList(
                NonTerminalType.SIMPLE_EXPR, NonTerminalType.EXPR_END))));
        pt.addRule(NonTerminalType.EXPR, TerminalType.NOT, new KleinRule(new ArrayList<Enum>(Arrays.asList(
                NonTerminalType.SIMPLE_EXPR, NonTerminalType.EXPR_END))));
        pt.addRule(NonTerminalType.EXPR, TerminalType.MINUS, new KleinRule(new ArrayList<Enum>(Arrays.asList(
                NonTerminalType.SIMPLE_EXPR, NonTerminalType.EXPR_END))));
        pt.addRule(NonTerminalType.EXPR, TerminalType.NUMBER, new KleinRule(new ArrayList<Enum>(Arrays.asList(
                NonTerminalType.SIMPLE_EXPR, NonTerminalType.EXPR_END))));
        pt.addRule(NonTerminalType.EXPR, TerminalType.BOOLEAN, new KleinRule(new ArrayList<Enum>(Arrays.asList(
                NonTerminalType.SIMPLE_EXPR, NonTerminalType.EXPR_END))));
        pt.addRule(NonTerminalType.EXPR, TerminalType.STRING, new KleinRule(new ArrayList<Enum>(Arrays.asList(
                NonTerminalType.SIMPLE_EXPR, NonTerminalType.EXPR_END))));
        //<EXPR-END> → ε ::= ), ,, then, else, and, *, /, function, $, or, +, -
        pt.addRule(NonTerminalType.EXPR_END, TerminalType.RIGHTPAREN, new KleinRule(new ArrayList<Enum>()));
        pt.addRule(NonTerminalType.EXPR_END, TerminalType.COMMA, new KleinRule(new ArrayList<Enum>()));
        pt.addRule(NonTerminalType.EXPR_END, TerminalType.THEN, new KleinRule(new ArrayList<Enum>()));
        pt.addRule(NonTerminalType.EXPR_END, TerminalType.ELSE, new KleinRule(new ArrayList<Enum>()));
        pt.addRule(NonTerminalType.EXPR_END, TerminalType.AND, new KleinRule(new ArrayList<Enum>()));
        pt.addRule(NonTerminalType.EXPR_END, TerminalType.PRODUCT, new KleinRule(new ArrayList<Enum>()));
        pt.addRule(NonTerminalType.EXPR_END, TerminalType.DIVIDE, new KleinRule(new ArrayList<Enum>()));
        pt.addRule(NonTerminalType.EXPR_END, TerminalType.FUNCTION, new KleinRule(new ArrayList<Enum>()));
        pt.addRule(NonTerminalType.EXPR_END, TerminalType.EOF, new KleinRule(new ArrayList<Enum>()));
        pt.addRule(NonTerminalType.EXPR_END, TerminalType.OR, new KleinRule(new ArrayList<Enum>()));
        pt.addRule(NonTerminalType.EXPR_END, TerminalType.PLUS, new KleinRule(new ArrayList<Enum>()));
        pt.addRule(NonTerminalType.EXPR_END, TerminalType.MINUS, new KleinRule(new ArrayList<Enum>()));
        // <EXPR-END> → <EXPR-SYMBOL> <EXPR> ::= 	<, =
        pt.addRule(NonTerminalType.EXPR_END, TerminalType.LESSTHAN, new KleinRule(new ArrayList<Enum>(Arrays.asList(
                NonTerminalType.EXPR_SYMBOL, NonTerminalType.EXPR))));
        pt.addRule(NonTerminalType.EXPR_END, TerminalType.EQUAL, new KleinRule(new ArrayList<Enum>(Arrays.asList(
                NonTerminalType.EXPR_SYMBOL, NonTerminalType.EXPR))));
        //<SIMPLE-EXPR> → <TERM> <SIMPLE-EXPR-END> ::= if, (, not, -, <NUMBER>, <BOOLEAN>, <STRING>
        pt.addRule(NonTerminalType.SIMPLE_EXPR, TerminalType.IF, new KleinRule(new ArrayList<Enum>(Arrays.asList(
                NonTerminalType.TERM, NonTerminalType.SIMPLE_EXPR_END))));
        pt.addRule(NonTerminalType.SIMPLE_EXPR, TerminalType.LEFTPAREN, new KleinRule(new ArrayList<Enum>(Arrays.asList(
                NonTerminalType.TERM, NonTerminalType.SIMPLE_EXPR_END))));
        pt.addRule(NonTerminalType.SIMPLE_EXPR, TerminalType.NOT, new KleinRule(new ArrayList<Enum>(Arrays.asList(
                NonTerminalType.TERM, NonTerminalType.SIMPLE_EXPR_END))));
        pt.addRule(NonTerminalType.SIMPLE_EXPR, TerminalType.MINUS, new KleinRule(new ArrayList<Enum>(Arrays.asList(
                NonTerminalType.TERM, NonTerminalType.SIMPLE_EXPR_END))));
        pt.addRule(NonTerminalType.SIMPLE_EXPR, TerminalType.NUMBER, new KleinRule(new ArrayList<Enum>(Arrays.asList(
                NonTerminalType.TERM, NonTerminalType.SIMPLE_EXPR_END))));
        pt.addRule(NonTerminalType.SIMPLE_EXPR, TerminalType.BOOLEAN, new KleinRule(new ArrayList<Enum>(Arrays.asList(
                NonTerminalType.TERM, NonTerminalType.SIMPLE_EXPR_END))));
        pt.addRule(NonTerminalType.SIMPLE_EXPR, TerminalType.STRING, new KleinRule(new ArrayList<Enum>(Arrays.asList(
                NonTerminalType.TERM, NonTerminalType.SIMPLE_EXPR_END))));
        //<SIMPLE-EXPR-END> → ε ::= <, =, ), ,, then, else, and, *, /, function, $
        pt.addRule(NonTerminalType.SIMPLE_EXPR_END, TerminalType.LESSTHAN, new KleinRule(new ArrayList<Enum>()));
        pt.addRule(NonTerminalType.SIMPLE_EXPR_END, TerminalType.EQUAL, new KleinRule(new ArrayList<Enum>()));
        pt.addRule(NonTerminalType.SIMPLE_EXPR_END, TerminalType.RIGHTPAREN, new KleinRule(new ArrayList<Enum>()));
        pt.addRule(NonTerminalType.SIMPLE_EXPR_END, TerminalType.COMMA, new KleinRule(new ArrayList<Enum>()));
        pt.addRule(NonTerminalType.SIMPLE_EXPR_END, TerminalType.THEN, new KleinRule(new ArrayList<Enum>()));
        pt.addRule(NonTerminalType.SIMPLE_EXPR_END, TerminalType.ELSE, new KleinRule(new ArrayList<Enum>()));
        pt.addRule(NonTerminalType.SIMPLE_EXPR_END, TerminalType.AND, new KleinRule(new ArrayList<Enum>()));
        pt.addRule(NonTerminalType.SIMPLE_EXPR_END, TerminalType.PRODUCT, new KleinRule(new ArrayList<Enum>()));
        pt.addRule(NonTerminalType.SIMPLE_EXPR_END, TerminalType.DIVIDE, new KleinRule(new ArrayList<Enum>()));
        pt.addRule(NonTerminalType.SIMPLE_EXPR_END, TerminalType.FUNCTION, new KleinRule(new ArrayList<Enum>()));
        pt.addRule(NonTerminalType.SIMPLE_EXPR_END, TerminalType.EOF, new KleinRule(new ArrayList<Enum>()));
        // <SIMPLE-EXPR-END> → <SIMPLE-EXPR-SYMBOL> <SIMPLE-EXPR> ::= or, +, -
        pt.addRule(NonTerminalType.SIMPLE_EXPR_END, TerminalType.OR, new KleinRule(new ArrayList<Enum>(Arrays.asList(
                NonTerminalType.SIMPLE_EXPR_SYMBOL, NonTerminalType.SIMPLE_EXPR))));
        pt.addRule(NonTerminalType.SIMPLE_EXPR_END, TerminalType.PLUS, new KleinRule(new ArrayList<Enum>(Arrays.asList(
                NonTerminalType.SIMPLE_EXPR_SYMBOL, NonTerminalType.SIMPLE_EXPR))));
        pt.addRule(NonTerminalType.SIMPLE_EXPR_END, TerminalType.MINUS, new KleinRule(new ArrayList<Enum>(Arrays.asList(
                NonTerminalType.SIMPLE_EXPR_SYMBOL, NonTerminalType.SIMPLE_EXPR))));
        //<TERM> → <FACTOR> <TERM-END> ::= if, (, not, -, <NUMBER>, <BOOLEAN>, <STRING>
        pt.addRule(NonTerminalType.TERM, TerminalType.IF, new KleinRule(new ArrayList<Enum>(Arrays.asList(
                NonTerminalType.FACTOR, NonTerminalType.TERM_END))));
        pt.addRule(NonTerminalType.TERM, TerminalType.LEFTPAREN, new KleinRule(new ArrayList<Enum>(Arrays.asList(
                NonTerminalType.FACTOR, NonTerminalType.TERM_END))));
        pt.addRule(NonTerminalType.TERM, TerminalType.NOT, new KleinRule(new ArrayList<Enum>(Arrays.asList(
                NonTerminalType.FACTOR, NonTerminalType.TERM_END))));
        pt.addRule(NonTerminalType.TERM, TerminalType.MINUS, new KleinRule(new ArrayList<Enum>(Arrays.asList(
                NonTerminalType.FACTOR, NonTerminalType.TERM_END))));
        pt.addRule(NonTerminalType.TERM, TerminalType.NUMBER, new KleinRule(new ArrayList<Enum>(Arrays.asList(
                NonTerminalType.FACTOR, NonTerminalType.TERM_END))));
        pt.addRule(NonTerminalType.TERM, TerminalType.BOOLEAN, new KleinRule(new ArrayList<Enum>(Arrays.asList(
                NonTerminalType.FACTOR, NonTerminalType.TERM_END))));
        pt.addRule(NonTerminalType.TERM, TerminalType.STRING, new KleinRule(new ArrayList<Enum>(Arrays.asList(
                NonTerminalType.FACTOR, NonTerminalType.TERM_END))));
        //<TERM-END> → ε ::= or, +, -, <, =, ), ,, then, else, function, $
        pt.addRule(NonTerminalType.TERM_END, TerminalType.OR, new KleinRule(new ArrayList<Enum>()));
        pt.addRule(NonTerminalType.TERM_END, TerminalType.PLUS, new KleinRule(new ArrayList<Enum>()));
        pt.addRule(NonTerminalType.TERM_END, TerminalType.MINUS, new KleinRule(new ArrayList<Enum>()));
        pt.addRule(NonTerminalType.TERM_END, TerminalType.LESSTHAN, new KleinRule(new ArrayList<Enum>()));
        pt.addRule(NonTerminalType.TERM_END, TerminalType.EQUAL, new KleinRule(new ArrayList<Enum>()));
        pt.addRule(NonTerminalType.TERM_END, TerminalType.RIGHTPAREN, new KleinRule(new ArrayList<Enum>()));
        pt.addRule(NonTerminalType.TERM_END, TerminalType.COMMA, new KleinRule(new ArrayList<Enum>()));
        pt.addRule(NonTerminalType.TERM_END, TerminalType.THEN, new KleinRule(new ArrayList<Enum>()));
        pt.addRule(NonTerminalType.TERM_END, TerminalType.ELSE, new KleinRule(new ArrayList<Enum>()));
        pt.addRule(NonTerminalType.TERM_END, TerminalType.FUNCTION, new KleinRule(new ArrayList<Enum>()));
        pt.addRule(NonTerminalType.TERM_END, TerminalType.EOF, new KleinRule(new ArrayList<Enum>()));
        //<TERM-END> → <TERM-SYMBOL> <TERM> ::= and, *, /
        pt.addRule(NonTerminalType.TERM_END, TerminalType.AND, new KleinRule(new ArrayList<Enum>(Arrays.asList(
                NonTerminalType.TERM_SYMBOL, NonTerminalType.TERM))));
        pt.addRule(NonTerminalType.TERM_END, TerminalType.PRODUCT, new KleinRule(new ArrayList<Enum>(Arrays.asList(
                NonTerminalType.TERM_SYMBOL, NonTerminalType.TERM))));
        pt.addRule(NonTerminalType.TERM_END, TerminalType.DIVIDE, new KleinRule(new ArrayList<Enum>(Arrays.asList(
                NonTerminalType.TERM_SYMBOL, NonTerminalType.TERM))));
        //<FACTOR> → if <EXPR> then <EXPR> else <EXPR> ::= if
        pt.addRule(NonTerminalType.FACTOR, TerminalType.IF, new KleinRule(new ArrayList<Enum>(Arrays.asList(
                TerminalType.IF, NonTerminalType.EXPR, TerminalType.THEN, NonTerminalType.EXPR,
                TerminalType.ELSE, NonTerminalType.EXPR))));
        //<FACTOR> → <FACTOR-SYMBOL> <FACTOR> ::= not, -
        pt.addRule(NonTerminalType.FACTOR, TerminalType.NOT, new KleinRule(new ArrayList<Enum>(Arrays.asList(
                NonTerminalType.FACTOR_SYMBOL, NonTerminalType.FACTOR))));
        pt.addRule(NonTerminalType.FACTOR, TerminalType.MINUS, new KleinRule(new ArrayList<Enum>(Arrays.asList(
                NonTerminalType.FACTOR_SYMBOL, NonTerminalType.FACTOR))));
        //<FACTOR> → <IDENTIFIER> ( <ACTUALS> ) ::= <STRING>
        pt.addRule(NonTerminalType.FACTOR, TerminalType.STRING, new KleinRule(new ArrayList<Enum>(Arrays.asList(
                NonTerminalType.IDENTIFIER, TerminalType.LEFTPAREN, NonTerminalType.ACTUALS, TerminalType.RIGHTPAREN))));
        //<FACTOR> → <LITERAL> ::= <NUMBER>, <BOOLEAN>
        pt.addRule(NonTerminalType.FACTOR, TerminalType.NUMBER, new KleinRule(new ArrayList<Enum>(Arrays.asList(
                NonTerminalType.LITERAL))));
        pt.addRule(NonTerminalType.FACTOR, TerminalType.BOOLEAN, new KleinRule(new ArrayList<Enum>(Arrays.asList(
                NonTerminalType.LITERAL))));
        //<FACTOR> → ( <EXPR> ) ::= (
        pt.addRule(NonTerminalType.FACTOR, TerminalType.LEFTPAREN, new KleinRule(new ArrayList<Enum>(Arrays.asList(
                TerminalType.LEFTPAREN, NonTerminalType.EXPR, TerminalType.RIGHTPAREN))));
        //<FACTOR-SYMBOL> → not ::= not
        pt.addRule(NonTerminalType.FACTOR_SYMBOL, TerminalType.NOT, new KleinRule(new ArrayList<Enum>(Arrays.asList(
                TerminalType.NOT))));
        //<FACTOR-SYMBOL> → - ::= -
        pt.addRule(NonTerminalType.FACTOR_SYMBOL, TerminalType.MINUS, new KleinRule(new ArrayList<Enum>(Arrays.asList(
                TerminalType.MINUS))));
        // <EXPR-SYMBOL> → < ::= <
        pt.addRule(NonTerminalType.EXPR_SYMBOL, TerminalType.LESSTHAN, new KleinRule(new ArrayList<Enum>(Arrays.asList(
                TerminalType.LESSTHAN))));
        // <EXPR-SYMBOL> → = ::= =
        pt.addRule(NonTerminalType.EXPR_SYMBOL, TerminalType.EQUAL, new KleinRule(new ArrayList<Enum>(Arrays.asList(
                TerminalType.EQUAL))));
        //<SIMPLE-EXPR-SYMBOL> → or	::= or
        pt.addRule(NonTerminalType.SIMPLE_EXPR_SYMBOL, TerminalType.OR, new KleinRule(new ArrayList<Enum>(Arrays.asList(
                TerminalType.OR))));
        //<SIMPLE-EXPR-SYMBOL> → + ::= +
        pt.addRule(NonTerminalType.SIMPLE_EXPR_SYMBOL, TerminalType.PLUS, new KleinRule(new ArrayList<Enum>(Arrays.asList(
                TerminalType.PLUS))));
        //<SIMPLE-EXPR-SYMBOL> → - ::= -
        pt.addRule(NonTerminalType.SIMPLE_EXPR_SYMBOL, TerminalType.MINUS, new KleinRule(new ArrayList<Enum>(Arrays.asList(
                TerminalType.MINUS))));
        //<TERM-SYMBOL> → and ::= and
        pt.addRule(NonTerminalType.TERM_SYMBOL, TerminalType.AND, new KleinRule(new ArrayList<Enum>(Arrays.asList(
                TerminalType.AND))));
        //<TERM-SYMBOL> → * ::= *
        pt.addRule(NonTerminalType.TERM_SYMBOL, TerminalType.PRODUCT, new KleinRule(new ArrayList<Enum>(Arrays.asList(
                TerminalType.PRODUCT))));
        //<TERM-SYMBOL> → / ::= /
        pt.addRule(NonTerminalType.TERM_SYMBOL, TerminalType.DIVIDE, new KleinRule(new ArrayList<Enum>(Arrays.asList(
                TerminalType.DIVIDE))));
        //<ACTUALS> → ε ::= )
        pt.addRule(NonTerminalType.ACTUALS, TerminalType.RIGHTPAREN, new KleinRule(new ArrayList<Enum>()));
        //<ACTUALS> → <NONEMPTYACTUALS> ::= if, (, not, -, <NUMBER>, <BOOLEAN>, <STRING>
        pt.addRule(NonTerminalType.ACTUALS, TerminalType.IF, new KleinRule(new ArrayList<Enum>(Arrays.asList(
                NonTerminalType.NONEMPTYACTUALS))));
        pt.addRule(NonTerminalType.ACTUALS, TerminalType.LEFTPAREN, new KleinRule(new ArrayList<Enum>(Arrays.asList(
                NonTerminalType.NONEMPTYACTUALS))));
        pt.addRule(NonTerminalType.ACTUALS, TerminalType.NOT, new KleinRule(new ArrayList<Enum>(Arrays.asList(
                NonTerminalType.NONEMPTYACTUALS))));
        pt.addRule(NonTerminalType.ACTUALS, TerminalType.MINUS, new KleinRule(new ArrayList<Enum>(Arrays.asList(
                NonTerminalType.NONEMPTYACTUALS))));
        pt.addRule(NonTerminalType.ACTUALS, TerminalType.NUMBER, new KleinRule(new ArrayList<Enum>(Arrays.asList(
                NonTerminalType.NONEMPTYACTUALS))));
        pt.addRule(NonTerminalType.ACTUALS, TerminalType.BOOLEAN, new KleinRule(new ArrayList<Enum>(Arrays.asList(
                NonTerminalType.NONEMPTYACTUALS))));
        pt.addRule(NonTerminalType.ACTUALS, TerminalType.STRING, new KleinRule(new ArrayList<Enum>(Arrays.asList(
                NonTerminalType.NONEMPTYACTUALS))));
        //<NONEMPTYACTUALS> → <EXPR> <NONEMPTYACTUALS'> ::= if, (, not, -, <NUMBER>, <BOOLEAN>, <STRING>
        pt.addRule(NonTerminalType.NONEMPTYACTUALS, TerminalType.IF, new KleinRule(new ArrayList<Enum>(Arrays.asList(
                NonTerminalType.EXPR, NonTerminalType.NONEMPTYACTUALS_PRIME))));
        pt.addRule(NonTerminalType.NONEMPTYACTUALS, TerminalType.LEFTPAREN, new KleinRule(new ArrayList<Enum>(Arrays.asList(
                NonTerminalType.EXPR, NonTerminalType.NONEMPTYACTUALS_PRIME))));
        pt.addRule(NonTerminalType.NONEMPTYACTUALS, TerminalType.NOT, new KleinRule(new ArrayList<Enum>(Arrays.asList(
                NonTerminalType.EXPR, NonTerminalType.NONEMPTYACTUALS_PRIME))));
        pt.addRule(NonTerminalType.NONEMPTYACTUALS, TerminalType.MINUS, new KleinRule(new ArrayList<Enum>(Arrays.asList(
                NonTerminalType.EXPR, NonTerminalType.NONEMPTYACTUALS_PRIME))));
        pt.addRule(NonTerminalType.NONEMPTYACTUALS, TerminalType.NUMBER, new KleinRule(new ArrayList<Enum>(Arrays.asList(
                NonTerminalType.EXPR, NonTerminalType.NONEMPTYACTUALS_PRIME))));
        pt.addRule(NonTerminalType.NONEMPTYACTUALS, TerminalType.BOOLEAN, new KleinRule(new ArrayList<Enum>(Arrays.asList(
                NonTerminalType.EXPR, NonTerminalType.NONEMPTYACTUALS_PRIME))));
        pt.addRule(NonTerminalType.NONEMPTYACTUALS, TerminalType.STRING, new KleinRule(new ArrayList<Enum>(Arrays.asList(
                NonTerminalType.EXPR, NonTerminalType.NONEMPTYACTUALS_PRIME))));
        //<NONEMPTYACTUALS'> → ε ::= )
        pt.addRule(NonTerminalType.NONEMPTYACTUALS_PRIME, TerminalType.RIGHTPAREN, new KleinRule(new ArrayList<Enum>()));
        //<NONEMPTYACTUALS'> → , <NONEMPTYACTUALS> ::=
        pt.addRule(NonTerminalType.NONEMPTYACTUALS_PRIME, TerminalType.COMMA, new KleinRule(new ArrayList<Enum>(Arrays.asList(
                TerminalType.COMMA, NonTerminalType.NONEMPTYACTUALS))));
        //<LITERAL> → <NUMBER> ::= <NUMBER>
        pt.addRule(NonTerminalType.LITERAL, TerminalType.NUMBER, new KleinRule(new ArrayList<Enum>(Arrays.asList(
                TerminalType.NUMBER))));
        //<LITERAL> → <BOOLEAN> ::= <BOOLEAN>
        pt.addRule(NonTerminalType.LITERAL, TerminalType.BOOLEAN, new KleinRule(new ArrayList<Enum>(Arrays.asList(
                TerminalType.BOOLEAN))));
        //<IDENTIFIER> → <STRING> ::= <STRING>
        pt.addRule(NonTerminalType.IDENTIFIER, TerminalType.STRING, new KleinRule(new ArrayList<Enum>(Arrays.asList(
                TerminalType.STRING))));
        //<PRINT-STATEMENT> → print ( <EXPR> ) ::= print
        pt.addRule(NonTerminalType.PRINT_STATEMENT, TerminalType.PRINT, new KleinRule(new ArrayList<Enum>(Arrays.asList(
                TerminalType.PRINT, TerminalType.LEFTPAREN, NonTerminalType.EXPR, TerminalType.RIGHTPAREN))));

        this.scanner = ks;
        this.PARSETABLE = pt;
        this.stack = new Stack<>();
    }

    private void parseProgram() {
        Enum stackTop = null;
        KleinToken scannerToken;
        KleinRule kRule;
        this.stack.push(TerminalType.EOF);
        this.stack.push(NonTerminalType.PROGRAM);
        while (!this.stack.empty()) {
            stackTop = this.stack.peek();
            if (stackTop instanceof TerminalType) {
                scannerToken = this.scanner.next();
                if (scannerToken.getTokenType().equals(KleinTokenType.STARTCOMMENT) ||
                        scannerToken.getTokenType().equals(KleinTokenType.ENDCOMMENT)){
                    continue;
                }
                if (stackTop == scannerToken.getTerminal()) {
                    this.stack.pop();
                }
                else {
                    throw new ParsingException(String.format(
                            "Token mismatch! Expected %s but got %s: %s",
                            stackTop.name(), scannerToken.getTokenType().name(), scannerToken.getTerminal().name()));
                }
            }
            else if (stackTop instanceof NonTerminalType) {
                scannerToken = this.scanner.peek();
                if (scannerToken.getTokenType().equals(KleinTokenType.STARTCOMMENT) ||
                        scannerToken.getTokenType().equals(KleinTokenType.ENDCOMMENT)){
                    this.scanner.next();
                    continue;
                }
                kRule = this.PARSETABLE.getRule((NonTerminalType) stackTop, scannerToken.getTerminal());
                if (kRule.exists()){
                    this.stack.pop();
                    kRule.pushRule(this.stack);
                }
                else{
                    throw new ParsingException(String.format(
                            "Invalid item '%s' found on the stack!", stackTop.name()));
                }
            }
        }
        if (stackTop != null && !stackTop.equals(TerminalType.EOF)){
            throw new ParsingException(String.format("Unexpected token '%s' at end of file", stackTop.name()));
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
