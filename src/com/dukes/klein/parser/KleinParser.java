package com.dukes.klein.parser;

import com.dukes.klein.parser.node.NullNode;
import com.dukes.klein.parser.node.SemanticActionType;
import com.dukes.klein.parser.node.TerminalNode;
import com.dukes.klein.scanner.KleinScanner;
import com.dukes.klein.scanner.KleinToken;
import com.dukes.klein.scanner.KleinTokenType;
import com.dukes.lang.parser.AbstractTableParser;
import com.dukes.lang.parser.ParsingException;
import com.dukes.lang.parser.node.AbstractSyntaxNode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Stack;


public class KleinParser
    extends AbstractTableParser<KleinScanner, KleinToken, KleinParseTable> {

  private Stack<Enum> stack;
  private Stack<AbstractSyntaxNode> semanticStack;

  public KleinParser(KleinScanner ks) {
    super(new KleinParseTable(), ks);
    KleinRule epsilon = new KleinRule(new ArrayList<Enum>());

    // <PROGRAM> → $ ::= <EOF>
    this.PARSETABLE.addRule(NonTerminalType.PROGRAM, TerminalType.EOF,
        new KleinRule(new ArrayList<Enum>(Arrays.asList(
        SemanticActionType.MAKE_PROGRAM))));

    // <PROGRAM> → <DEFINITIONS> ::= function
    this.PARSETABLE.addRule(NonTerminalType.PROGRAM, TerminalType.FUNCTION,
        new KleinRule(new ArrayList<Enum>(Arrays.asList(
        NonTerminalType.DEFINITIONS, SemanticActionType.MAKE_PROGRAM))));
    
    // <DEFINITIONS> → ε ::= $
    this.PARSETABLE.addRule(NonTerminalType.DEFINITIONS, TerminalType.EOF,
        epsilon);

    // <DEFINITIONS> → <DEF> <DEFINITIONS> ::= function
    this.PARSETABLE.addRule(NonTerminalType.DEFINITIONS, TerminalType.FUNCTION,
        new KleinRule(new ArrayList<Enum>(Arrays.asList(
        NonTerminalType.DEF, NonTerminalType.DEFINITIONS))));

    // <DEF> → function <IDENTIFIER> ( <FORMALS> ) : <TYPE> <BODY> ::= function
    this.PARSETABLE.addRule(NonTerminalType.DEF, TerminalType.FUNCTION,
        new KleinRule(new ArrayList<Enum>(Arrays.asList(
        TerminalType.FUNCTION, TerminalType.STRING, TerminalType.LEFTPAREN,
        NonTerminalType.FORMALS, TerminalType.RIGHTPAREN,
        TerminalType.COLON, NonTerminalType.TYPE, NonTerminalType.BODY,
        SemanticActionType.MAKE_BODY, SemanticActionType.MAKE_FUNCTION))));

    // <FORMALS> → ε ::= )
    this.PARSETABLE.addRule(NonTerminalType.FORMALS, TerminalType.RIGHTPAREN,
        epsilon);

    // <FORMALS> → <NONEMPTYFORMALS> ::= <STRING>
    this.PARSETABLE.addRule(NonTerminalType.FORMALS, TerminalType.STRING,
        new KleinRule(new ArrayList<Enum>(Arrays.asList(
        NonTerminalType.NONEMPTYFORMALS))));

    // <NONEMPTYFORMALS> → <FORMAL> <NONEMPTYFORMALS_PRIME> ::= <STRING>
    this.PARSETABLE.addRule(NonTerminalType.NONEMPTYFORMALS,
        TerminalType.STRING, new KleinRule(new ArrayList<Enum>(Arrays.asList(
        NonTerminalType.FORMAL, SemanticActionType.MAKE_FORMAL,
        NonTerminalType.NONEMPTYFORMALS_PRIME))));

    // <NONEMPTYFORMALS_PRIME> → ε ::= )
    this.PARSETABLE.addRule(NonTerminalType.NONEMPTYFORMALS_PRIME,
        TerminalType.RIGHTPAREN, epsilon);

    // <NONEMPTYFORMALS_PRIME> → , <NONEMPTYFORMALS> ::= ,
    this.PARSETABLE.addRule(NonTerminalType.NONEMPTYFORMALS_PRIME,
        TerminalType.COMMA, new KleinRule(new ArrayList<Enum>(Arrays.asList(
        TerminalType.COMMA, NonTerminalType.NONEMPTYFORMALS))));

    // <FORMAL> → <IDENTIFIER> : <TYPE> ::= <STRING>
    this.PARSETABLE.addRule(NonTerminalType.FORMAL, TerminalType.STRING,
        new KleinRule(new ArrayList<Enum>(Arrays.asList(
        NonTerminalType.IDENTIFIER, TerminalType.COLON,
        NonTerminalType.TYPE))));

    // <BODY> → <PRINT-STATEMENT> <BODY> ::= print
    this.PARSETABLE.addRule(NonTerminalType.BODY, TerminalType.PRINT,
        new KleinRule(new ArrayList<Enum>(Arrays.asList(
        NonTerminalType.PRINT_STATEMENT, SemanticActionType.MAKE_PRINT,
        NonTerminalType.BODY))));

    // <BODY> → <EXPR> ::= if, (, not, -, <NUMBER>, <BOOLEAN>, <STRING>
    KleinRule bodyToExpr = new KleinRule(new ArrayList<Enum>(Arrays.asList(
        NonTerminalType.EXPR)));
    this.PARSETABLE.addRule(NonTerminalType.BODY, TerminalType.IF, bodyToExpr);
    this.PARSETABLE.addRule(NonTerminalType.BODY, TerminalType.LEFTPAREN,
        bodyToExpr);
    this.PARSETABLE.addRule(NonTerminalType.BODY, TerminalType.NOT,
        bodyToExpr);
    this.PARSETABLE.addRule(NonTerminalType.BODY, TerminalType.MINUS,
        bodyToExpr);
    this.PARSETABLE.addRule(NonTerminalType.BODY, TerminalType.NUMBER,
        bodyToExpr);
    this.PARSETABLE.addRule(NonTerminalType.BODY, TerminalType.BOOLEAN,
        bodyToExpr);
    this.PARSETABLE.addRule(NonTerminalType.BODY, TerminalType.STRING,
        bodyToExpr);

    //<TYPE> → integer ::= integer
    this.PARSETABLE.addRule(NonTerminalType.TYPE, TerminalType.INTEGER_STR,
        new KleinRule(new ArrayList<Enum>(Arrays.asList(
        TerminalType.INTEGER_STR))));

    //<TYPE> → boolean ::= boolean
    this.PARSETABLE.addRule(NonTerminalType.TYPE, TerminalType.BOOLEAN_STR,
        new KleinRule(new ArrayList<Enum>(Arrays.asList(
        TerminalType.BOOLEAN_STR))));

    // <EXPR> → <SIMPLE-EXPR> <EXPR-END> ::=
    //               if, (, not, -, <NUMBER>, <BOOLEAN>, <STRING>
    KleinRule exprToSimpleExpr = new KleinRule(new ArrayList<Enum>(
        Arrays.asList(NonTerminalType.SIMPLE_EXPR, NonTerminalType.EXPR_END)));
    this.PARSETABLE.addRule(NonTerminalType.EXPR, TerminalType.IF,
        exprToSimpleExpr);
    this.PARSETABLE.addRule(NonTerminalType.EXPR, TerminalType.LEFTPAREN,
	exprToSimpleExpr);
    this.PARSETABLE.addRule(NonTerminalType.EXPR, TerminalType.NOT,
        exprToSimpleExpr);
    this.PARSETABLE.addRule(NonTerminalType.EXPR, TerminalType.MINUS,
        exprToSimpleExpr);
    this.PARSETABLE.addRule(NonTerminalType.EXPR, TerminalType.NUMBER,
        exprToSimpleExpr);
    this.PARSETABLE.addRule(NonTerminalType.EXPR, TerminalType.BOOLEAN,
        exprToSimpleExpr);
    this.PARSETABLE.addRule(NonTerminalType.EXPR, TerminalType.STRING,
        exprToSimpleExpr);

    //<EXPR-END> → ε ::= ), ,, then, else, and, *, /, function, $, or, +, -
    this.PARSETABLE.addRule(NonTerminalType.EXPR_END, TerminalType.RIGHTPAREN,
        epsilon);
    this.PARSETABLE.addRule(NonTerminalType.EXPR_END, TerminalType.COMMA,
        epsilon);
    this.PARSETABLE.addRule(NonTerminalType.EXPR_END, TerminalType.THEN,
        epsilon);
    this.PARSETABLE.addRule(NonTerminalType.EXPR_END, TerminalType.ELSE,
        epsilon);
    this.PARSETABLE.addRule(NonTerminalType.EXPR_END, TerminalType.AND,
        epsilon);
    this.PARSETABLE.addRule(NonTerminalType.EXPR_END, TerminalType.PRODUCT,
        epsilon);
    this.PARSETABLE.addRule(NonTerminalType.EXPR_END, TerminalType.DIVIDE,
        epsilon);
    this.PARSETABLE.addRule(NonTerminalType.EXPR_END, TerminalType.FUNCTION,
        epsilon);
    this.PARSETABLE.addRule(NonTerminalType.EXPR_END, TerminalType.EOF,
        epsilon);
    this.PARSETABLE.addRule(NonTerminalType.EXPR_END, TerminalType.OR,
        epsilon);
    this.PARSETABLE.addRule(NonTerminalType.EXPR_END, TerminalType.PLUS,
        epsilon);
    this.PARSETABLE.addRule(NonTerminalType.EXPR_END, TerminalType.MINUS,
        epsilon);

    // <EXPR-END> → <EXPR-SYMBOL> <EXPR> ::= 	<, =
    KleinRule exprEndToSym = new KleinRule(new ArrayList<Enum>(
        Arrays.asList(NonTerminalType.EXPR_SYMBOL, NonTerminalType.EXPR,
        SemanticActionType.MAKE_OPERATOR)));
    this.PARSETABLE.addRule(NonTerminalType.EXPR_END, TerminalType.LESSTHAN,
        exprEndToSym);
    this.PARSETABLE.addRule(NonTerminalType.EXPR_END, TerminalType.EQUAL,
        exprEndToSym);

    //<SIMPLE-EXPR> → <TERM> <SIMPLE-EXPR-END> ::=
    //                     if, (, not, -, <NUMBER>, <BOOLEAN>, <STRING>
    KleinRule simToTerm = new KleinRule(new ArrayList<Enum>(Arrays.asList(
        NonTerminalType.TERM, NonTerminalType.SIMPLE_EXPR_END)));
    this.PARSETABLE.addRule(NonTerminalType.SIMPLE_EXPR, TerminalType.IF,
        simToTerm);
    this.PARSETABLE.addRule(NonTerminalType.SIMPLE_EXPR,
        TerminalType.LEFTPAREN, simToTerm);
    this.PARSETABLE.addRule(NonTerminalType.SIMPLE_EXPR, TerminalType.NOT,
        simToTerm);
    this.PARSETABLE.addRule(NonTerminalType.SIMPLE_EXPR, TerminalType.MINUS,
        simToTerm);
    this.PARSETABLE.addRule(NonTerminalType.SIMPLE_EXPR, TerminalType.NUMBER,
        simToTerm);
    this.PARSETABLE.addRule(NonTerminalType.SIMPLE_EXPR, TerminalType.BOOLEAN,
        simToTerm);
    this.PARSETABLE.addRule(NonTerminalType.SIMPLE_EXPR, TerminalType.STRING,
        simToTerm);

    //<SIMPLE-EXPR-END> → ε ::= <, =, ), ,, then, else, and, *, /, function, $
    this.PARSETABLE.addRule(NonTerminalType.SIMPLE_EXPR_END,
        TerminalType.LESSTHAN, epsilon);
    this.PARSETABLE.addRule(NonTerminalType.SIMPLE_EXPR_END,
        TerminalType.EQUAL, epsilon);
    this.PARSETABLE.addRule(NonTerminalType.SIMPLE_EXPR_END,
        TerminalType.RIGHTPAREN, epsilon);
    this.PARSETABLE.addRule(NonTerminalType.SIMPLE_EXPR_END,
        TerminalType.COMMA, epsilon);
    this.PARSETABLE.addRule(NonTerminalType.SIMPLE_EXPR_END,
        TerminalType.THEN, epsilon);
    this.PARSETABLE.addRule(NonTerminalType.SIMPLE_EXPR_END,
        TerminalType.ELSE, epsilon);
    this.PARSETABLE.addRule(NonTerminalType.SIMPLE_EXPR_END,
        TerminalType.AND, epsilon);
    this.PARSETABLE.addRule(NonTerminalType.SIMPLE_EXPR_END,
        TerminalType.PRODUCT, epsilon);
    this.PARSETABLE.addRule(NonTerminalType.SIMPLE_EXPR_END,
        TerminalType.DIVIDE, epsilon);
    this.PARSETABLE.addRule(NonTerminalType.SIMPLE_EXPR_END,
        TerminalType.FUNCTION, epsilon);
    this.PARSETABLE.addRule(NonTerminalType.SIMPLE_EXPR_END,
        TerminalType.EOF, epsilon);

    // <SIMPLE-EXPR-END> → <SIMPLE-EXPR-SYMBOL> <SIMPLE-EXPR> ::= or, +, -
    KleinRule simEndToSym = new KleinRule(new ArrayList<Enum>(Arrays.asList(
        NonTerminalType.SIMPLE_EXPR_SYMBOL, NonTerminalType.SIMPLE_EXPR,
        SemanticActionType.MAKE_OPERATOR)));
    this.PARSETABLE.addRule(NonTerminalType.SIMPLE_EXPR_END,
        TerminalType.OR, simEndToSym);
    this.PARSETABLE.addRule(NonTerminalType.SIMPLE_EXPR_END,
        TerminalType.PLUS, simEndToSym);
    this.PARSETABLE.addRule(NonTerminalType.SIMPLE_EXPR_END,
        TerminalType.MINUS, simEndToSym);
    
    //<TERM> → <FACTOR> <TERM-END> ::=
    //                     if, (, not, -, <NUMBER>, <BOOLEAN>, <STRING>
    KleinRule termToFact = new KleinRule(new ArrayList<Enum>(Arrays.asList(
            NonTerminalType.FACTOR, NonTerminalType.TERM_END)));
    this.PARSETABLE.addRule(NonTerminalType.TERM, TerminalType.IF,
        termToFact);
    this.PARSETABLE.addRule(NonTerminalType.TERM, TerminalType.LEFTPAREN,
        termToFact);
    this.PARSETABLE.addRule(NonTerminalType.TERM, TerminalType.NOT,
        termToFact);
    this.PARSETABLE.addRule(NonTerminalType.TERM, TerminalType.MINUS,
        termToFact);
    this.PARSETABLE.addRule(NonTerminalType.TERM, TerminalType.NUMBER,
        termToFact);
    this.PARSETABLE.addRule(NonTerminalType.TERM, TerminalType.BOOLEAN,
        termToFact);
    this.PARSETABLE.addRule(NonTerminalType.TERM, TerminalType.STRING,
        termToFact);
    
    //<TERM-END> → ε ::= or, +, -, <, =, ), ,, then, else, function, $
    this.PARSETABLE.addRule(NonTerminalType.TERM_END, TerminalType.OR,
        epsilon);
    this.PARSETABLE.addRule(NonTerminalType.TERM_END, TerminalType.PLUS,
        epsilon);
    this.PARSETABLE.addRule(NonTerminalType.TERM_END, TerminalType.MINUS,
        epsilon);
    this.PARSETABLE.addRule(NonTerminalType.TERM_END, TerminalType.LESSTHAN,
        epsilon);
    this.PARSETABLE.addRule(NonTerminalType.TERM_END, TerminalType.EQUAL,
        epsilon);
    this.PARSETABLE.addRule(NonTerminalType.TERM_END, TerminalType.RIGHTPAREN,
        epsilon);
    this.PARSETABLE.addRule(NonTerminalType.TERM_END, TerminalType.COMMA,
        epsilon);
    this.PARSETABLE.addRule(NonTerminalType.TERM_END, TerminalType.THEN,
        epsilon);
    this.PARSETABLE.addRule(NonTerminalType.TERM_END, TerminalType.ELSE,
        epsilon);
    this.PARSETABLE.addRule(NonTerminalType.TERM_END, TerminalType.FUNCTION,
        epsilon);
    this.PARSETABLE.addRule(NonTerminalType.TERM_END, TerminalType.EOF,
        epsilon);
    
    //<TERM-END> → <TERM-SYMBOL> <TERM> ::= and, *, /
    KleinRule termEndToSym = new KleinRule(new ArrayList<Enum>(Arrays.asList(
        NonTerminalType.TERM_SYMBOL, NonTerminalType.TERM,
        SemanticActionType.MAKE_OPERATOR)));
    this.PARSETABLE.addRule(NonTerminalType.TERM_END, TerminalType.AND,
        termEndToSym);
    this.PARSETABLE.addRule(NonTerminalType.TERM_END, TerminalType.PRODUCT,
        termEndToSym);
    this.PARSETABLE.addRule(NonTerminalType.TERM_END, TerminalType.DIVIDE,
        termEndToSym);
    
    //<FACTOR> → if <EXPR> then <EXPR> else <EXPR> ::= if
    this.PARSETABLE.addRule(NonTerminalType.FACTOR, TerminalType.IF,
        new KleinRule(new ArrayList<Enum>(Arrays.asList(
        TerminalType.IF, NonTerminalType.EXPR,
        TerminalType.THEN, NonTerminalType.EXPR,
        TerminalType.ELSE, NonTerminalType.EXPR,
        SemanticActionType.MAKE_IF))));
    
    //<FACTOR> → <FACTOR-SYMBOL> <FACTOR> ::= not, -
    KleinRule factToSym = new KleinRule(new ArrayList<Enum>(Arrays.asList(
        NonTerminalType.FACTOR_SYMBOL, NonTerminalType.FACTOR,
        SemanticActionType.MAKE_UNARYOPERATOR)));
    this.PARSETABLE.addRule(NonTerminalType.FACTOR, TerminalType.NOT,
        factToSym);
    this.PARSETABLE.addRule(NonTerminalType.FACTOR, TerminalType.MINUS,
        factToSym);
    
    //<FACTOR> → <IDENTIFIER> <FACTOR-END> ::= <STRING>
    this.PARSETABLE.addRule(NonTerminalType.FACTOR, TerminalType.STRING,
        new KleinRule(new ArrayList<Enum>(Arrays.asList(
        NonTerminalType.IDENTIFIER, NonTerminalType.FACTOR_END))));
    
    //<FACTOR> → <LITERAL> ::= <NUMBER>, <BOOLEAN>
    KleinRule factToLit = new KleinRule(new ArrayList<Enum>(Arrays.asList(
        NonTerminalType.LITERAL, SemanticActionType.MAKE_DECLARED)));
    this.PARSETABLE.addRule(NonTerminalType.FACTOR, TerminalType.NUMBER,
        factToLit);
    this.PARSETABLE.addRule(NonTerminalType.FACTOR, TerminalType.BOOLEAN,
        factToLit);
    
    //<FACTOR> → ( <EXPR> ) ::= (
    this.PARSETABLE.addRule(NonTerminalType.FACTOR, TerminalType.LEFTPAREN,
        new KleinRule(new ArrayList<Enum>(Arrays.asList(
        TerminalType.LEFTPAREN, NonTerminalType.EXPR,
        TerminalType.RIGHTPAREN, SemanticActionType.MAKE_PARAMETERIZED))));
    
    //<FACTOR-SYMBOL> → not ::= not
    this.PARSETABLE.addRule(NonTerminalType.FACTOR_SYMBOL, TerminalType.NOT,
        new KleinRule(new ArrayList<Enum>(Arrays.asList(
        TerminalType.NOT))));
    
    //<FACTOR-SYMBOL> → - ::= -
    this.PARSETABLE.addRule(NonTerminalType.FACTOR_SYMBOL, TerminalType.MINUS,
        new KleinRule(new ArrayList<Enum>(Arrays.asList(
        TerminalType.MINUS))));
    
    //<FACTOR-END> → ε	::=
    //         and, *, /, or, +, -, <, =, ), ,, then, else, function, $
    KleinRule factorEndToEpsilon = new KleinRule(new ArrayList<Enum>(
        Arrays.asList(SemanticActionType.MAKE_DECLARED)));
    this.PARSETABLE.addRule(NonTerminalType.FACTOR_END, TerminalType.AND,
        factorEndToEpsilon);
    this.PARSETABLE.addRule(NonTerminalType.FACTOR_END, TerminalType.PRODUCT,
        factorEndToEpsilon);
    this.PARSETABLE.addRule(NonTerminalType.FACTOR_END, TerminalType.DIVIDE,
        factorEndToEpsilon);
    this.PARSETABLE.addRule(NonTerminalType.FACTOR_END, TerminalType.OR,
        factorEndToEpsilon);
    this.PARSETABLE.addRule(NonTerminalType.FACTOR_END, TerminalType.PLUS,
        factorEndToEpsilon);
    this.PARSETABLE.addRule(NonTerminalType.FACTOR_END, TerminalType.MINUS,
        factorEndToEpsilon);
    this.PARSETABLE.addRule(NonTerminalType.FACTOR_END, TerminalType.LESSTHAN,
        factorEndToEpsilon);
    this.PARSETABLE.addRule(NonTerminalType.FACTOR_END, TerminalType.EQUAL,
        factorEndToEpsilon);
    this.PARSETABLE.addRule(NonTerminalType.FACTOR_END,
        TerminalType.RIGHTPAREN, factorEndToEpsilon);
    this.PARSETABLE.addRule(NonTerminalType.FACTOR_END, TerminalType.COMMA,
        factorEndToEpsilon);
    this.PARSETABLE.addRule(NonTerminalType.FACTOR_END, TerminalType.THEN,
        factorEndToEpsilon);
    this.PARSETABLE.addRule(NonTerminalType.FACTOR_END, TerminalType.ELSE,
        factorEndToEpsilon);
    this.PARSETABLE.addRule(NonTerminalType.FACTOR_END, TerminalType.FUNCTION,
        factorEndToEpsilon);
    this.PARSETABLE.addRule(NonTerminalType.FACTOR_END, TerminalType.EOF,
        factorEndToEpsilon);
    
    //<FACTOR-END> → ( <ACTUALS> ) ::= (
    this.PARSETABLE.addRule(NonTerminalType.FACTOR_END, TerminalType.LEFTPAREN,
        new KleinRule(new ArrayList<Enum>(Arrays.asList(
        TerminalType.LEFTPAREN, NonTerminalType.ACTUALS,
        TerminalType.RIGHTPAREN, SemanticActionType.MAKE_CALL))));
    
    // <EXPR-SYMBOL> → < ::= <
    this.PARSETABLE.addRule(NonTerminalType.EXPR_SYMBOL, TerminalType.LESSTHAN,
        new KleinRule(new ArrayList<Enum>(Arrays.asList(
        TerminalType.LESSTHAN))));
    
    // <EXPR-SYMBOL> → = ::= =
    this.PARSETABLE.addRule(NonTerminalType.EXPR_SYMBOL, TerminalType.EQUAL,
        new KleinRule(new ArrayList<Enum>(Arrays.asList(
        TerminalType.EQUAL))));
    
    //<SIMPLE-EXPR-SYMBOL> → or	::= or
    this.PARSETABLE.addRule(NonTerminalType.SIMPLE_EXPR_SYMBOL,
        TerminalType.OR, new KleinRule(new ArrayList<Enum>(Arrays.asList(
        TerminalType.OR))));
    
    //<SIMPLE-EXPR-SYMBOL> → + ::= +
    this.PARSETABLE.addRule(NonTerminalType.SIMPLE_EXPR_SYMBOL,
        TerminalType.PLUS, new KleinRule(new ArrayList<Enum>(Arrays.asList(
        TerminalType.PLUS))));
    
    //<SIMPLE-EXPR-SYMBOL> → - ::= -
    this.PARSETABLE.addRule(NonTerminalType.SIMPLE_EXPR_SYMBOL,
        TerminalType.MINUS, new KleinRule(new ArrayList<Enum>(Arrays.asList(
        TerminalType.MINUS))));
    
    //<TERM-SYMBOL> → and ::= and
    this.PARSETABLE.addRule(NonTerminalType.TERM_SYMBOL, TerminalType.AND,
        new KleinRule(new ArrayList<Enum>(Arrays.asList(
        TerminalType.AND))));
    
    //<TERM-SYMBOL> → * ::= *
    this.PARSETABLE.addRule(NonTerminalType.TERM_SYMBOL, TerminalType.PRODUCT,
        new KleinRule(new ArrayList<Enum>(Arrays.asList(
        TerminalType.PRODUCT))));
    
    //<TERM-SYMBOL> → / ::= /
    this.PARSETABLE.addRule(NonTerminalType.TERM_SYMBOL, TerminalType.DIVIDE,
        new KleinRule(new ArrayList<Enum>(Arrays.asList(
        TerminalType.DIVIDE))));
    
    //<ACTUALS> → ε ::= )
    this.PARSETABLE.addRule(NonTerminalType.ACTUALS, TerminalType.RIGHTPAREN,
        epsilon);
    
    //<ACTUALS> → <NONEMPTYACTUALS> ::=
    //                  if, (, not, -, <NUMBER>, <BOOLEAN>, <STRING>
    KleinRule actToNEAct = new KleinRule(new ArrayList<Enum>(Arrays.asList(
        NonTerminalType.NONEMPTYACTUALS)));
    this.PARSETABLE.addRule(NonTerminalType.ACTUALS, TerminalType.IF,
        actToNEAct);
    this.PARSETABLE.addRule(NonTerminalType.ACTUALS, TerminalType.LEFTPAREN,
        actToNEAct);
    this.PARSETABLE.addRule(NonTerminalType.ACTUALS, TerminalType.NOT,
        actToNEAct);
    this.PARSETABLE.addRule(NonTerminalType.ACTUALS, TerminalType.MINUS,
        actToNEAct);
    this.PARSETABLE.addRule(NonTerminalType.ACTUALS, TerminalType.NUMBER,
        actToNEAct);
    this.PARSETABLE.addRule(NonTerminalType.ACTUALS, TerminalType.BOOLEAN,
        actToNEAct);
    this.PARSETABLE.addRule(NonTerminalType.ACTUALS, TerminalType.STRING,
        actToNEAct);
    
    //<NONEMPTYACTUALS> → <EXPR> <NONEMPTYACTUALS'> ::=
    //                        if, (, not, -, <NUMBER>, <BOOLEAN>, <STRING>
    KleinRule neActToExpr = new KleinRule(new ArrayList<Enum>(Arrays.asList(
        NonTerminalType.EXPR, NonTerminalType.NONEMPTYACTUALS_PRIME)));
    this.PARSETABLE.addRule(NonTerminalType.NONEMPTYACTUALS,
        TerminalType.IF, neActToExpr);
    this.PARSETABLE.addRule(NonTerminalType.NONEMPTYACTUALS,
        TerminalType.LEFTPAREN, neActToExpr);
    this.PARSETABLE.addRule(NonTerminalType.NONEMPTYACTUALS,
        TerminalType.NOT, neActToExpr);
    this.PARSETABLE.addRule(NonTerminalType.NONEMPTYACTUALS,
        TerminalType.MINUS, neActToExpr);
    this.PARSETABLE.addRule(NonTerminalType.NONEMPTYACTUALS,
        TerminalType.NUMBER, neActToExpr);
    this.PARSETABLE.addRule(NonTerminalType.NONEMPTYACTUALS,
        TerminalType.BOOLEAN, neActToExpr);
    this.PARSETABLE.addRule(NonTerminalType.NONEMPTYACTUALS,
        TerminalType.STRING, neActToExpr);
    
    //<NONEMPTYACTUALS'> → ε ::= )
    this.PARSETABLE.addRule(NonTerminalType.NONEMPTYACTUALS_PRIME,
        TerminalType.RIGHTPAREN, epsilon);
    
    //<NONEMPTYACTUALS'> → , <NONEMPTYACTUALS> ::=
    this.PARSETABLE.addRule(NonTerminalType.NONEMPTYACTUALS_PRIME,
        TerminalType.COMMA, new KleinRule(new ArrayList<Enum>(Arrays.asList(
        TerminalType.COMMA, NonTerminalType.NONEMPTYACTUALS))));
    
    //<LITERAL> → <NUMBER> ::= <NUMBER>
    this.PARSETABLE.addRule(NonTerminalType.LITERAL, TerminalType.NUMBER,
        new KleinRule(new ArrayList<Enum>(Arrays.asList(
        TerminalType.NUMBER))));
    
    //<LITERAL> → <BOOLEAN> ::= <BOOLEAN>
    this.PARSETABLE.addRule(NonTerminalType.LITERAL, TerminalType.BOOLEAN,
        new KleinRule(new ArrayList<Enum>(Arrays.asList(
        TerminalType.BOOLEAN))));
    
    //<IDENTIFIER> → <STRING> ::= <STRING>
    this.PARSETABLE.addRule(NonTerminalType.IDENTIFIER, TerminalType.STRING,
        new KleinRule(new ArrayList<Enum>(Arrays.asList(
        TerminalType.STRING))));
    
    //<PRINT-STATEMENT> → print ( <EXPR> ) ::= print
    this.PARSETABLE.addRule(NonTerminalType.PRINT_STATEMENT,
        TerminalType.PRINT, new KleinRule(new ArrayList<Enum>(Arrays.asList(
        TerminalType.PRINT, TerminalType.LEFTPAREN, NonTerminalType.EXPR,
        TerminalType.RIGHTPAREN))));

    this.stack = new Stack<Enum>();
    this.semanticStack = new Stack<AbstractSyntaxNode>();
  }

  private AbstractSyntaxNode parseProgram() {
    this.hasParsed = true;
    Enum stackTop = null;
    KleinToken scannerToken;
    KleinRule kRule;
    this.stack.push(TerminalType.EOF);
    this.stack.push(NonTerminalType.PROGRAM);

    while(!this.stack.empty()) {
      stackTop = this.stack.peek();
      if(stackTop instanceof TerminalType) {
        scannerToken = this.scanner.next();
        if(scannerToken.getTokenType().equals(KleinTokenType.STARTCOMMENT) ||
            scannerToken.getTokenType().equals(KleinTokenType.ENDCOMMENT)) {
          continue;
        }
        if(stackTop == scannerToken.getTerminal()) {
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
      else if(stackTop instanceof NonTerminalType) {
        scannerToken = this.scanner.peek();
        if(scannerToken.getTokenType().equals(KleinTokenType.STARTCOMMENT) ||
            scannerToken.getTokenType().equals(KleinTokenType.ENDCOMMENT)) {
          this.scanner.next();
          continue;
        }
        kRule = (KleinRule)this.PARSETABLE.getRule((NonTerminalType) stackTop,
            scannerToken.getTerminal());
        if(kRule.exists()) {
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
      else if(stackTop instanceof SemanticActionType) {
        AbstractSyntaxNode ast =
            ((SemanticActionType) stackTop).run(this.semanticStack);
        this.semanticStack.push(ast);
        this.stack.pop();
      }
    }
    if(stackTop != null && !stackTop.equals(TerminalType.EOF)) {
      throw new ParsingException(String.format(
          "Unexpected token '%s' at end of file", stackTop.name()));
    }
    if(this.semanticStack.size() != 1) {
      throw new ParsingException("Extra Tokens are in semantic stack: " +
          this.semanticStack.toString() + "!");
    }
    this.ast = this.semanticStack.pop();

    return ast;
  }

  @Override
  public AbstractSyntaxNode generateAST() {
    if(this.ast instanceof NullNode && !this.hasParsed) {
      return this.parseProgram();
    }
    else {
      return this.ast;
    }
  }
}
