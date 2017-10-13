package com.dukes.klein.parser;

import com.dukes.klein.parser.node.AbstractSyntaxNode;
import com.dukes.klein.parser.node.NullNode;
import com.dukes.klein.parser.node.SemanticActionType;
import com.dukes.klein.parser.node.TerminalNode;
import com.dukes.klein.scanner.KleinScanner;
import com.dukes.klein.scanner.KleinToken;
import com.dukes.klein.scanner.KleinTokenType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Stack;


public class KleinParser extends AbstractTableParser<KleinScanner, KleinToken> {

  private Stack<Enum> stack;
  private Stack<AbstractSyntaxNode> semanticStack;

  public KleinParser(KleinScanner ks) {
    ParseTable pt = new ParseTable();
    KleinRule epsilon = new KleinRule(new ArrayList<Enum>());

    // <PROGRAM> → $ ::= <EOF>
    pt.addRule(NonTerminalType.PROGRAM, TerminalType.EOF, 
        new KleinRule(new ArrayList<Enum>(Arrays.asList(
        SemanticActionType.MAKE_PROGRAM))));

    // <PROGRAM> → <DEFINITIONS> ::= function
    pt.addRule(NonTerminalType.PROGRAM, TerminalType.FUNCTION,
        new KleinRule(new ArrayList<Enum>(Arrays.asList(
            NonTerminalType.DEFINITIONS, SemanticActionType.MAKE_PROGRAM))));
    // <DEFINITIONS> → ε ::= $
    pt.addRule(NonTerminalType.DEFINITIONS, TerminalType.EOF, epsilon);

    // <DEFINITIONS> → <DEF> <DEFINITIONS> ::= function
    pt.addRule(NonTerminalType.DEFINITIONS, TerminalType.FUNCTION,
        new KleinRule(new ArrayList<Enum>(Arrays.asList(
            NonTerminalType.DEF, NonTerminalType.DEFINITIONS))));

    // <DEF> → function <IDENTIFIER> ( <FORMALS> ) : <TYPE> <BODY> ::= function
    pt.addRule(NonTerminalType.DEF, TerminalType.FUNCTION,
        new KleinRule(new ArrayList<Enum>(Arrays.asList(
            TerminalType.FUNCTION, TerminalType.STRING, TerminalType.LEFTPAREN,
            NonTerminalType.FORMALS, TerminalType.RIGHTPAREN, TerminalType.COLON,
            NonTerminalType.TYPE, NonTerminalType.BODY,
            SemanticActionType.MAKE_BODY, SemanticActionType.MAKE_FUNCTION))));

    // <FORMALS> → ε ::= )
    pt.addRule(NonTerminalType.FORMALS, TerminalType.RIGHTPAREN, epsilon);

    // <FORMALS> → <NONEMPTYFORMALS> ::= <STRING>
    pt.addRule(NonTerminalType.FORMALS, TerminalType.STRING,
        new KleinRule(new ArrayList<Enum>(Arrays.asList(
            NonTerminalType.NONEMPTYFORMALS))));

    // <NONEMPTYFORMALS> → <FORMAL> <NONEMPTYFORMALS_PRIME> ::= <STRING>
    pt.addRule(NonTerminalType.NONEMPTYFORMALS, TerminalType.STRING,
        new KleinRule(new ArrayList<Enum>(Arrays.asList(
            NonTerminalType.FORMAL, SemanticActionType.MAKE_FORMAL,
            NonTerminalType.NONEMPTYFORMALS_PRIME))));

    // <NONEMPTYFORMALS_PRIME> → ε ::= )
    pt.addRule(NonTerminalType.NONEMPTYFORMALS_PRIME, TerminalType.RIGHTPAREN,
        epsilon);

    // <NONEMPTYFORMALS_PRIME> → , <NONEMPTYFORMALS> ::= ,
    pt.addRule(NonTerminalType.NONEMPTYFORMALS_PRIME, TerminalType.COMMA,
        new KleinRule(new ArrayList<Enum>(Arrays.asList(
            TerminalType.COMMA, NonTerminalType.NONEMPTYFORMALS))));

    // <FORMAL> → <IDENTIFIER> : <TYPE> ::= <STRING>
    pt.addRule(NonTerminalType.FORMAL, TerminalType.STRING,
        new KleinRule(new ArrayList<Enum>(Arrays.asList(
            NonTerminalType.IDENTIFIER, TerminalType.COLON,
            NonTerminalType.TYPE))));

    // <BODY> → <PRINT-STATEMENT> <BODY> ::= print
    pt.addRule(NonTerminalType.BODY, TerminalType.PRINT,
        new KleinRule(new ArrayList<Enum>(Arrays.asList(
            NonTerminalType.PRINT_STATEMENT, SemanticActionType.MAKE_PRINT,
            NonTerminalType.BODY))));

    // <BODY> → <EXPR> ::= if, (, not, -, <NUMBER>, <BOOLEAN>, <STRING>
    KleinRule bodyToExpr = new KleinRule(new ArrayList<Enum>(Arrays.asList(
        NonTerminalType.EXPR)));
    pt.addRule(NonTerminalType.BODY, TerminalType.IF, bodyToExpr);
    pt.addRule(NonTerminalType.BODY, TerminalType.LEFTPAREN, bodyToExpr);
    pt.addRule(NonTerminalType.BODY, TerminalType.NOT, bodyToExpr);
    pt.addRule(NonTerminalType.BODY, TerminalType.MINUS, bodyToExpr);
    pt.addRule(NonTerminalType.BODY, TerminalType.NUMBER, bodyToExpr);
    pt.addRule(NonTerminalType.BODY, TerminalType.BOOLEAN, bodyToExpr);
    pt.addRule(NonTerminalType.BODY, TerminalType.STRING, bodyToExpr);

    //<TYPE> → integer ::= integer
    pt.addRule(NonTerminalType.TYPE, TerminalType.INTEGER_STR,
        new KleinRule(new ArrayList<Enum>(Arrays.asList(
            TerminalType.INTEGER_STR))));

    //<TYPE> → boolean ::= boolean
    pt.addRule(NonTerminalType.TYPE, TerminalType.BOOLEAN_STR,
        new KleinRule(new ArrayList<Enum>(Arrays.asList(
            TerminalType.BOOLEAN_STR))));

    // <EXPR> → <SIMPLE-EXPR> <EXPR-END> ::=
    //               if, (, not, -, <NUMBER>, <BOOLEAN>, <STRING>
    KleinRule exprToSimpleExpr = new KleinRule(new ArrayList<Enum>(
        Arrays.asList(NonTerminalType.SIMPLE_EXPR, NonTerminalType.EXPR_END)));
    pt.addRule(NonTerminalType.EXPR, TerminalType.IF, exprToSimpleExpr);
    pt.addRule(NonTerminalType.EXPR, TerminalType.LEFTPAREN, exprToSimpleExpr);
    pt.addRule(NonTerminalType.EXPR, TerminalType.NOT, exprToSimpleExpr);
    pt.addRule(NonTerminalType.EXPR, TerminalType.MINUS, exprToSimpleExpr);
    pt.addRule(NonTerminalType.EXPR, TerminalType.NUMBER, exprToSimpleExpr);
    pt.addRule(NonTerminalType.EXPR, TerminalType.BOOLEAN, exprToSimpleExpr);
    pt.addRule(NonTerminalType.EXPR, TerminalType.STRING, exprToSimpleExpr);

    //<EXPR-END> → ε ::= ), ,, then, else, and, *, /, function, $, or, +, -
    pt.addRule(NonTerminalType.EXPR_END, TerminalType.RIGHTPAREN, epsilon);
    pt.addRule(NonTerminalType.EXPR_END, TerminalType.COMMA, epsilon);
    pt.addRule(NonTerminalType.EXPR_END, TerminalType.THEN, epsilon);
    pt.addRule(NonTerminalType.EXPR_END, TerminalType.ELSE, epsilon);
    pt.addRule(NonTerminalType.EXPR_END, TerminalType.AND, epsilon);
    pt.addRule(NonTerminalType.EXPR_END, TerminalType.PRODUCT, epsilon);
    pt.addRule(NonTerminalType.EXPR_END, TerminalType.DIVIDE, epsilon);
    pt.addRule(NonTerminalType.EXPR_END, TerminalType.FUNCTION, epsilon);
    pt.addRule(NonTerminalType.EXPR_END, TerminalType.EOF, epsilon);
    pt.addRule(NonTerminalType.EXPR_END, TerminalType.OR, epsilon);
    pt.addRule(NonTerminalType.EXPR_END, TerminalType.PLUS, epsilon);
    pt.addRule(NonTerminalType.EXPR_END, TerminalType.MINUS, epsilon);

    // <EXPR-END> → <EXPR-SYMBOL> <EXPR> ::= 	<, =
    KleinRule exprEndToSym = new KleinRule(new ArrayList<Enum>(
        Arrays.asList(NonTerminalType.EXPR_SYMBOL, NonTerminalType.EXPR,
            SemanticActionType.MAKE_OPERATOR)));
    pt.addRule(NonTerminalType.EXPR_END, TerminalType.LESSTHAN, exprEndToSym);
    pt.addRule(NonTerminalType.EXPR_END, TerminalType.EQUAL, exprEndToSym);

    //<SIMPLE-EXPR> → <TERM> <SIMPLE-EXPR-END> ::=
    //                     if, (, not, -, <NUMBER>, <BOOLEAN>, <STRING>
    KleinRule simToTerm = new KleinRule(new ArrayList<Enum>(Arrays.asList(
        NonTerminalType.TERM, NonTerminalType.SIMPLE_EXPR_END)));
    pt.addRule(NonTerminalType.SIMPLE_EXPR, TerminalType.IF, simToTerm);
    pt.addRule(NonTerminalType.SIMPLE_EXPR, TerminalType.LEFTPAREN, simToTerm);
    pt.addRule(NonTerminalType.SIMPLE_EXPR, TerminalType.NOT, simToTerm);
    pt.addRule(NonTerminalType.SIMPLE_EXPR, TerminalType.MINUS, simToTerm);
    pt.addRule(NonTerminalType.SIMPLE_EXPR, TerminalType.NUMBER, simToTerm);
    pt.addRule(NonTerminalType.SIMPLE_EXPR, TerminalType.BOOLEAN, simToTerm);
    pt.addRule(NonTerminalType.SIMPLE_EXPR, TerminalType.STRING, simToTerm);

    //<SIMPLE-EXPR-END> → ε ::= <, =, ), ,, then, else, and, *, /, function, $
    pt.addRule(NonTerminalType.SIMPLE_EXPR_END, TerminalType.LESSTHAN,
        epsilon);
    pt.addRule(NonTerminalType.SIMPLE_EXPR_END, TerminalType.EQUAL, epsilon);
    pt.addRule(NonTerminalType.SIMPLE_EXPR_END, TerminalType.RIGHTPAREN,
        epsilon);
    pt.addRule(NonTerminalType.SIMPLE_EXPR_END, TerminalType.COMMA, epsilon);
    pt.addRule(NonTerminalType.SIMPLE_EXPR_END, TerminalType.THEN, epsilon);
    pt.addRule(NonTerminalType.SIMPLE_EXPR_END, TerminalType.ELSE, epsilon);
    pt.addRule(NonTerminalType.SIMPLE_EXPR_END, TerminalType.AND, epsilon);
    pt.addRule(NonTerminalType.SIMPLE_EXPR_END, TerminalType.PRODUCT, epsilon);
    pt.addRule(NonTerminalType.SIMPLE_EXPR_END, TerminalType.DIVIDE, epsilon);
    pt.addRule(NonTerminalType.SIMPLE_EXPR_END, TerminalType.FUNCTION,
        epsilon);
    pt.addRule(NonTerminalType.SIMPLE_EXPR_END, TerminalType.EOF, epsilon);

    // <SIMPLE-EXPR-END> → <SIMPLE-EXPR-SYMBOL> <SIMPLE-EXPR> ::= or, +, -
    pt.addRule(NonTerminalType.SIMPLE_EXPR_END, TerminalType.OR,
        new KleinRule(new ArrayList<Enum>(Arrays.asList(
            NonTerminalType.SIMPLE_EXPR_SYMBOL, NonTerminalType.SIMPLE_EXPR,
            SemanticActionType.MAKE_OPERATOR))));
    pt.addRule(NonTerminalType.SIMPLE_EXPR_END, TerminalType.PLUS,
        new KleinRule(new ArrayList<Enum>(Arrays.asList(
            NonTerminalType.SIMPLE_EXPR_SYMBOL, NonTerminalType.SIMPLE_EXPR,
            SemanticActionType.MAKE_OPERATOR))));
    pt.addRule(NonTerminalType.SIMPLE_EXPR_END, TerminalType.MINUS,
        new KleinRule(new ArrayList<Enum>(Arrays.asList(
            NonTerminalType.SIMPLE_EXPR_SYMBOL, NonTerminalType.SIMPLE_EXPR,
            SemanticActionType.MAKE_OPERATOR))));
    //<TERM> → <FACTOR> <TERM-END> ::=
    //                     if, (, not, -, <NUMBER>, <BOOLEAN>, <STRING>
    pt.addRule(NonTerminalType.TERM, TerminalType.IF,
        new KleinRule(new ArrayList<Enum>(Arrays.asList(
            NonTerminalType.FACTOR, NonTerminalType.TERM_END))));
    pt.addRule(NonTerminalType.TERM, TerminalType.LEFTPAREN,
        new KleinRule(new ArrayList<Enum>(Arrays.asList(
            NonTerminalType.FACTOR, NonTerminalType.TERM_END))));
    pt.addRule(NonTerminalType.TERM, TerminalType.NOT,
        new KleinRule(new ArrayList<Enum>(Arrays.asList(
            NonTerminalType.FACTOR, NonTerminalType.TERM_END))));
    pt.addRule(NonTerminalType.TERM, TerminalType.MINUS,
        new KleinRule(new ArrayList<Enum>(Arrays.asList(
            NonTerminalType.FACTOR, NonTerminalType.TERM_END))));
    pt.addRule(NonTerminalType.TERM, TerminalType.NUMBER,
        new KleinRule(new ArrayList<Enum>(Arrays.asList(
            NonTerminalType.FACTOR, NonTerminalType.TERM_END))));
    pt.addRule(NonTerminalType.TERM, TerminalType.BOOLEAN,
        new KleinRule(new ArrayList<Enum>(Arrays.asList(
            NonTerminalType.FACTOR, NonTerminalType.TERM_END))));
    pt.addRule(NonTerminalType.TERM, TerminalType.STRING,
        new KleinRule(new ArrayList<Enum>(Arrays.asList(
            NonTerminalType.FACTOR, NonTerminalType.TERM_END))));
    //<TERM-END> → ε ::= or, +, -, <, =, ), ,, then, else, function, $
    pt.addRule(NonTerminalType.TERM_END, TerminalType.OR,
        epsilon);
    pt.addRule(NonTerminalType.TERM_END, TerminalType.PLUS,
        epsilon);
    pt.addRule(NonTerminalType.TERM_END, TerminalType.MINUS,
        epsilon);
    pt.addRule(NonTerminalType.TERM_END, TerminalType.LESSTHAN,
        epsilon);
    pt.addRule(NonTerminalType.TERM_END, TerminalType.EQUAL,
        epsilon);
    pt.addRule(NonTerminalType.TERM_END, TerminalType.RIGHTPAREN,
        epsilon);
    pt.addRule(NonTerminalType.TERM_END, TerminalType.COMMA,
        epsilon);
    pt.addRule(NonTerminalType.TERM_END, TerminalType.THEN,
        epsilon);
    pt.addRule(NonTerminalType.TERM_END, TerminalType.ELSE,
        epsilon);
    pt.addRule(NonTerminalType.TERM_END, TerminalType.FUNCTION,
        epsilon);
    pt.addRule(NonTerminalType.TERM_END, TerminalType.EOF,
        epsilon);
    //<TERM-END> → <TERM-SYMBOL> <TERM> ::= and, *, /
    pt.addRule(NonTerminalType.TERM_END, TerminalType.AND,
        new KleinRule(new ArrayList<Enum>(Arrays.asList(
            NonTerminalType.TERM_SYMBOL, NonTerminalType.TERM,
            SemanticActionType.MAKE_OPERATOR))));
    pt.addRule(NonTerminalType.TERM_END, TerminalType.PRODUCT,
        new KleinRule(new ArrayList<Enum>(Arrays.asList(
            NonTerminalType.TERM_SYMBOL, NonTerminalType.TERM,
            SemanticActionType.MAKE_OPERATOR))));
    pt.addRule(NonTerminalType.TERM_END, TerminalType.DIVIDE,
        new KleinRule(new ArrayList<Enum>(Arrays.asList(
            NonTerminalType.TERM_SYMBOL, NonTerminalType.TERM,
            SemanticActionType.MAKE_OPERATOR))));
    //<FACTOR> → if <EXPR> then <EXPR> else <EXPR> ::= if
    pt.addRule(NonTerminalType.FACTOR, TerminalType.IF,
        new KleinRule(new ArrayList<Enum>(Arrays.asList(
            TerminalType.IF, NonTerminalType.EXPR,
            TerminalType.THEN, NonTerminalType.EXPR,
            TerminalType.ELSE, NonTerminalType.EXPR,
            SemanticActionType.MAKE_IF))));
    //<FACTOR> → <FACTOR-SYMBOL> <FACTOR> ::= not, -
    pt.addRule(NonTerminalType.FACTOR, TerminalType.NOT,
        new KleinRule(new ArrayList<Enum>(Arrays.asList(
            NonTerminalType.FACTOR_SYMBOL, NonTerminalType.FACTOR,
            SemanticActionType.MAKE_UNARYOPERATOR))));
    pt.addRule(NonTerminalType.FACTOR, TerminalType.MINUS,
        new KleinRule(new ArrayList<Enum>(Arrays.asList(
            NonTerminalType.FACTOR_SYMBOL, NonTerminalType.FACTOR,
            SemanticActionType.MAKE_UNARYOPERATOR))));
    //<FACTOR> → <IDENTIFIER> <FACTOR-END> ::= <STRING>
    pt.addRule(NonTerminalType.FACTOR, TerminalType.STRING,
        new KleinRule(new ArrayList<Enum>(Arrays.asList(
            NonTerminalType.IDENTIFIER, NonTerminalType.FACTOR_END))));
    //<FACTOR> → <LITERAL> ::= <NUMBER>, <BOOLEAN>
    pt.addRule(NonTerminalType.FACTOR, TerminalType.NUMBER,
        new KleinRule(new ArrayList<Enum>(Arrays.asList(
            NonTerminalType.LITERAL, SemanticActionType.MAKE_DECLARED))));
    pt.addRule(NonTerminalType.FACTOR, TerminalType.BOOLEAN,
        new KleinRule(new ArrayList<Enum>(Arrays.asList(
            NonTerminalType.LITERAL, SemanticActionType.MAKE_DECLARED))));
    //<FACTOR> → ( <EXPR> ) ::= (
    pt.addRule(NonTerminalType.FACTOR, TerminalType.LEFTPAREN,
        new KleinRule(new ArrayList<Enum>(Arrays.asList(
            TerminalType.LEFTPAREN, NonTerminalType.EXPR, TerminalType.RIGHTPAREN,
            SemanticActionType.MAKE_PARAMETERIZED))));
    //<FACTOR-SYMBOL> → not ::= not
    pt.addRule(NonTerminalType.FACTOR_SYMBOL, TerminalType.NOT,
        new KleinRule(new ArrayList<Enum>(Arrays.asList(
            TerminalType.NOT))));
    //<FACTOR-SYMBOL> → - ::= -
    pt.addRule(NonTerminalType.FACTOR_SYMBOL, TerminalType.MINUS,
        new KleinRule(new ArrayList<Enum>(Arrays.asList(
            TerminalType.MINUS))));
    //<FACTOR-END> → ε	::=
    //         and, *, /, or, +, -, <, =, ), ,, then, else, function, $
    KleinRule factorEndToEpsilon = new KleinRule(new ArrayList<Enum>(
        Arrays.asList(SemanticActionType.MAKE_DECLARED)));

    pt.addRule(NonTerminalType.FACTOR_END, TerminalType.AND,
        factorEndToEpsilon);
    pt.addRule(NonTerminalType.FACTOR_END, TerminalType.PRODUCT,
        factorEndToEpsilon);
    pt.addRule(NonTerminalType.FACTOR_END, TerminalType.DIVIDE,
        factorEndToEpsilon);
    pt.addRule(NonTerminalType.FACTOR_END, TerminalType.OR,
        factorEndToEpsilon);
    pt.addRule(NonTerminalType.FACTOR_END, TerminalType.PLUS,
        factorEndToEpsilon);
    pt.addRule(NonTerminalType.FACTOR_END, TerminalType.MINUS,
        factorEndToEpsilon);
    pt.addRule(NonTerminalType.FACTOR_END, TerminalType.LESSTHAN,
        factorEndToEpsilon);
    pt.addRule(NonTerminalType.FACTOR_END, TerminalType.EQUAL,
        factorEndToEpsilon);
    pt.addRule(NonTerminalType.FACTOR_END, TerminalType.RIGHTPAREN,
        factorEndToEpsilon);
    pt.addRule(NonTerminalType.FACTOR_END, TerminalType.COMMA,
        factorEndToEpsilon);
    pt.addRule(NonTerminalType.FACTOR_END, TerminalType.THEN,
        factorEndToEpsilon);
    pt.addRule(NonTerminalType.FACTOR_END, TerminalType.ELSE,
        factorEndToEpsilon);
    pt.addRule(NonTerminalType.FACTOR_END, TerminalType.FUNCTION,
        factorEndToEpsilon);
    pt.addRule(NonTerminalType.FACTOR_END, TerminalType.EOF,
        factorEndToEpsilon);
    //<FACTOR-END> → ( <ACTUALS> ) ::= (
    pt.addRule(NonTerminalType.FACTOR_END, TerminalType.LEFTPAREN,
        new KleinRule(new ArrayList<Enum>(Arrays.asList(
            TerminalType.LEFTPAREN, NonTerminalType.ACTUALS,
            TerminalType.RIGHTPAREN, SemanticActionType.MAKE_CALL))));
    // <EXPR-SYMBOL> → < ::= <
    pt.addRule(NonTerminalType.EXPR_SYMBOL, TerminalType.LESSTHAN,
        new KleinRule(new ArrayList<Enum>(Arrays.asList(
            TerminalType.LESSTHAN))));
    // <EXPR-SYMBOL> → = ::= =
    pt.addRule(NonTerminalType.EXPR_SYMBOL, TerminalType.EQUAL,
        new KleinRule(new ArrayList<Enum>(Arrays.asList(
            TerminalType.EQUAL))));
    //<SIMPLE-EXPR-SYMBOL> → or	::= or
    pt.addRule(NonTerminalType.SIMPLE_EXPR_SYMBOL, TerminalType.OR,
        new KleinRule(new ArrayList<Enum>(Arrays.asList(
            TerminalType.OR))));
    //<SIMPLE-EXPR-SYMBOL> → + ::= +
    pt.addRule(NonTerminalType.SIMPLE_EXPR_SYMBOL, TerminalType.PLUS,
        new KleinRule(new ArrayList<Enum>(Arrays.asList(
            TerminalType.PLUS))));
    //<SIMPLE-EXPR-SYMBOL> → - ::= -
    pt.addRule(NonTerminalType.SIMPLE_EXPR_SYMBOL, TerminalType.MINUS,
        new KleinRule(new ArrayList<Enum>(Arrays.asList(
            TerminalType.MINUS))));
    //<TERM-SYMBOL> → and ::= and
    pt.addRule(NonTerminalType.TERM_SYMBOL, TerminalType.AND,
        new KleinRule(new ArrayList<Enum>(Arrays.asList(
            TerminalType.AND))));
    //<TERM-SYMBOL> → * ::= *
    pt.addRule(NonTerminalType.TERM_SYMBOL, TerminalType.PRODUCT,
        new KleinRule(new ArrayList<Enum>(Arrays.asList(
            TerminalType.PRODUCT))));
    //<TERM-SYMBOL> → / ::= /
    pt.addRule(NonTerminalType.TERM_SYMBOL, TerminalType.DIVIDE,
        new KleinRule(new ArrayList<Enum>(Arrays.asList(
            TerminalType.DIVIDE))));
    //<ACTUALS> → ε ::= )
    pt.addRule(NonTerminalType.ACTUALS, TerminalType.RIGHTPAREN,
        epsilon);
    //<ACTUALS> → <NONEMPTYACTUALS> ::=
    //                  if, (, not, -, <NUMBER>, <BOOLEAN>, <STRING>
    pt.addRule(NonTerminalType.ACTUALS, TerminalType.IF,
        new KleinRule(new ArrayList<Enum>(Arrays.asList(
            NonTerminalType.NONEMPTYACTUALS))));
    pt.addRule(NonTerminalType.ACTUALS, TerminalType.LEFTPAREN,
        new KleinRule(new ArrayList<Enum>(Arrays.asList(
            NonTerminalType.NONEMPTYACTUALS))));
    pt.addRule(NonTerminalType.ACTUALS, TerminalType.NOT,
        new KleinRule(new ArrayList<Enum>(Arrays.asList(
            NonTerminalType.NONEMPTYACTUALS))));
    pt.addRule(NonTerminalType.ACTUALS, TerminalType.MINUS,
        new KleinRule(new ArrayList<Enum>(Arrays.asList(
            NonTerminalType.NONEMPTYACTUALS))));
    pt.addRule(NonTerminalType.ACTUALS, TerminalType.NUMBER,
        new KleinRule(new ArrayList<Enum>(Arrays.asList(
            NonTerminalType.NONEMPTYACTUALS))));
    pt.addRule(NonTerminalType.ACTUALS, TerminalType.BOOLEAN,
        new KleinRule(new ArrayList<Enum>(Arrays.asList(
            NonTerminalType.NONEMPTYACTUALS))));
    pt.addRule(NonTerminalType.ACTUALS, TerminalType.STRING,
        new KleinRule(new ArrayList<Enum>(Arrays.asList(
            NonTerminalType.NONEMPTYACTUALS))));
    //<NONEMPTYACTUALS> → <EXPR> <NONEMPTYACTUALS'> ::=
    //                        if, (, not, -, <NUMBER>, <BOOLEAN>, <STRING>
    pt.addRule(NonTerminalType.NONEMPTYACTUALS, TerminalType.IF,
        new KleinRule(new ArrayList<Enum>(Arrays.asList(
            NonTerminalType.EXPR, NonTerminalType.NONEMPTYACTUALS_PRIME))));
    pt.addRule(NonTerminalType.NONEMPTYACTUALS, TerminalType.LEFTPAREN,
        new KleinRule(new ArrayList<Enum>(Arrays.asList(
            NonTerminalType.EXPR, NonTerminalType.NONEMPTYACTUALS_PRIME))));
    pt.addRule(NonTerminalType.NONEMPTYACTUALS, TerminalType.NOT,
        new KleinRule(new ArrayList<Enum>(Arrays.asList(
            NonTerminalType.EXPR, NonTerminalType.NONEMPTYACTUALS_PRIME))));
    pt.addRule(NonTerminalType.NONEMPTYACTUALS, TerminalType.MINUS,
        new KleinRule(new ArrayList<Enum>(Arrays.asList(
            NonTerminalType.EXPR, NonTerminalType.NONEMPTYACTUALS_PRIME))));
    pt.addRule(NonTerminalType.NONEMPTYACTUALS, TerminalType.NUMBER,
        new KleinRule(new ArrayList<Enum>(Arrays.asList(
            NonTerminalType.EXPR, NonTerminalType.NONEMPTYACTUALS_PRIME))));
    pt.addRule(NonTerminalType.NONEMPTYACTUALS, TerminalType.BOOLEAN,
        new KleinRule(new ArrayList<Enum>(Arrays.asList(
            NonTerminalType.EXPR, NonTerminalType.NONEMPTYACTUALS_PRIME))));
    pt.addRule(NonTerminalType.NONEMPTYACTUALS, TerminalType.STRING,
        new KleinRule(new ArrayList<Enum>(Arrays.asList(
            NonTerminalType.EXPR, NonTerminalType.NONEMPTYACTUALS_PRIME))));
    //<NONEMPTYACTUALS'> → ε ::= )
    pt.addRule(NonTerminalType.NONEMPTYACTUALS_PRIME, TerminalType.RIGHTPAREN,
        epsilon);
    //<NONEMPTYACTUALS'> → , <NONEMPTYACTUALS> ::=
    pt.addRule(NonTerminalType.NONEMPTYACTUALS_PRIME, TerminalType.COMMA,
        new KleinRule(new ArrayList<Enum>(Arrays.asList(
            TerminalType.COMMA, NonTerminalType.NONEMPTYACTUALS))));
    //<LITERAL> → <NUMBER> ::= <NUMBER>
    pt.addRule(NonTerminalType.LITERAL, TerminalType.NUMBER,
        new KleinRule(new ArrayList<Enum>(Arrays.asList(
            TerminalType.NUMBER))));
    //<LITERAL> → <BOOLEAN> ::= <BOOLEAN>
    pt.addRule(NonTerminalType.LITERAL, TerminalType.BOOLEAN,
        new KleinRule(new ArrayList<Enum>(Arrays.asList(
            TerminalType.BOOLEAN))));
    //<IDENTIFIER> → <STRING> ::= <STRING>
    pt.addRule(NonTerminalType.IDENTIFIER, TerminalType.STRING,
        new KleinRule(new ArrayList<Enum>(Arrays.asList(
            TerminalType.STRING))));
    //<PRINT-STATEMENT> → print ( <EXPR> ) ::= print
    pt.addRule(NonTerminalType.PRINT_STATEMENT, TerminalType.PRINT,
        new KleinRule(new ArrayList<Enum>(Arrays.asList(
            TerminalType.PRINT, TerminalType.LEFTPAREN, NonTerminalType.EXPR,
            TerminalType.RIGHTPAREN))));

    this.scanner = ks;
    this.PARSETABLE = pt;
    this.stack = new Stack<>();
    this.semanticStack = new Stack<AbstractSyntaxNode>();
  }

  private AbstractSyntaxNode parseProgram() {
    this.hasParsed = true;
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
            scannerToken.getTokenType().equals(KleinTokenType.ENDCOMMENT)) {
          continue;
        }
        if (stackTop == scannerToken.getTerminal()) {
          this.stack.pop();
          if(scannerToken.getTokenType() == KleinTokenType.BOOLEAN ||
              scannerToken.getTokenType() == KleinTokenType.INTEGER ||
              scannerToken.getTokenType() == KleinTokenType.IDENTIFIER ||
              scannerToken.getTokenType() == KleinTokenType.TYPE ||
              scannerToken.getTokenType() == KleinTokenType.SYMBOL) {
            this.semanticStack.push(
                new TerminalNode(scannerToken.getTokenValue()));
          }
        }
        else {
          throw new ParsingException(String.format(
              "Token mismatch! Expected %s but got %s: %s",
              stackTop.name(), scannerToken.getTokenType().name(),
              scannerToken.getTerminal().name()));
        }
      }
      else if (stackTop instanceof NonTerminalType) {
        scannerToken = this.scanner.peek();
        if (scannerToken.getTokenType().equals(KleinTokenType.STARTCOMMENT) ||
            scannerToken.getTokenType().equals(KleinTokenType.ENDCOMMENT)) {
          this.scanner.next();
          continue;
        }
        kRule = this.PARSETABLE.getRule((NonTerminalType) stackTop,
            scannerToken.getTerminal());
        if (kRule.exists()) {
          this.stack.pop();
          kRule.pushRule(this.stack);
        }
        else {
          throw new ParsingException(String.format(
              "Invalid item '%s' found on the stack when handling %s: %s",
              stackTop.name(), scannerToken.getTokenType().name(),
              scannerToken.getTerminal().name()));
        }
      }
      else if (stackTop instanceof SemanticActionType) {
        AbstractSyntaxNode ast =
            ((SemanticActionType) stackTop).run(this.semanticStack);
        this.semanticStack.push(ast);
        this.stack.pop();
      }
    }
    if (stackTop != null && !stackTop.equals(TerminalType.EOF)) {
      throw new ParsingException(String.format(
          "Unexpected token '%s' at end of file", stackTop.name()));
    }
    if (this.semanticStack.size() != 1) {
      throw new ParsingException("Extra Tokens are in semantic stack: " +
          this.semanticStack.toString() + "!");
    }
    this.ast = this.semanticStack.pop();

    return ast;
  }

  @Override
  public AbstractSyntaxNode generateAST () {
    if (this.ast instanceof NullNode && !this.hasParsed) {
      return this.parseProgram();
    }
    else {
      return this.ast;
    }
  }
}
