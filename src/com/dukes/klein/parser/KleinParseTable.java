/*
 * Copyright (C) 2017 Coved W Oswald, Daniel Holland, and Patrick Sedlacek
 * 
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License along
 * with this program; if not, write to the Free Software Foundation, Inc.,
 * 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.
 */
package com.dukes.klein.parser;

import com.dukes.klein.parser.node.SemanticActionType;
import com.dukes.lang.parser.AbstractParseTable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Coved W Oswald
 * @author Dan Holland
 */
public final class KleinParseTable extends
    AbstractParseTable<NonTerminalType, TerminalType, KleinRule> {
  
  public KleinParseTable() {
    this.table = new ArrayList<List<KleinRule>>();
    for(int i = 0; i < NonTerminalType.values().length; i++) {
      this.table.add(new ArrayList<KleinRule>());
      for(int j = 0; j < TerminalType.values().length; j++) {
        this.table.get(i).add(new KleinRule());
      }
    }
    this.initialize();
  }

  @Override
  protected KleinRule getRule(NonTerminalType nt, TerminalType tk) {
    return this.table.get(nt.getId()).get(tk.getId());
  }
  
  @Override
  protected void addRule(NonTerminalType t1, TerminalType t2, KleinRule kr) {
    this.table.get(t1.getId()).set(t2.getId(), kr);
  }
  
  private void initialize() {
    KleinRule epsilon = new KleinRule(new ArrayList<Enum>());
   
    // <PROGRAM> → $ ::= <EOF>
    this.addRule(NonTerminalType.PROGRAM, TerminalType.EOF,
        new KleinRule(new ArrayList<Enum>(Arrays.asList(
        SemanticActionType.MAKE_PROGRAM))));

    // <PROGRAM> → <DEFINITIONS> ::= function
    this.addRule(NonTerminalType.PROGRAM, TerminalType.FUNCTION,
        new KleinRule(new ArrayList<Enum>(Arrays.asList(
        NonTerminalType.DEFINITIONS, SemanticActionType.MAKE_PROGRAM))));
    
    // <DEFINITIONS> → ε ::= $
    this.addRule(NonTerminalType.DEFINITIONS, TerminalType.EOF,
        epsilon);

    // <DEFINITIONS> → <DEF> <DEFINITIONS> ::= function
    this.addRule(NonTerminalType.DEFINITIONS, TerminalType.FUNCTION,
        new KleinRule(new ArrayList<Enum>(Arrays.asList(
        NonTerminalType.DEF, NonTerminalType.DEFINITIONS))));

    // <DEF> → function <IDENTIFIER> ( <FORMALS> ) : <TYPE> <BODY> ::= function
    this.addRule(NonTerminalType.DEF, TerminalType.FUNCTION,
        new KleinRule(new ArrayList<Enum>(Arrays.asList(
        TerminalType.FUNCTION, TerminalType.STRING, TerminalType.LEFTPAREN,
        NonTerminalType.FORMALS, TerminalType.RIGHTPAREN,
        TerminalType.COLON, NonTerminalType.TYPE, NonTerminalType.BODY,
        SemanticActionType.MAKE_BODY, SemanticActionType.MAKE_FUNCTION))));

    // <FORMALS> → ε ::= )
    this.addRule(NonTerminalType.FORMALS, TerminalType.RIGHTPAREN,
        epsilon);

    // <FORMALS> → <NONEMPTYFORMALS> ::= <STRING>
    this.addRule(NonTerminalType.FORMALS, TerminalType.STRING,
        new KleinRule(new ArrayList<Enum>(Arrays.asList(
        NonTerminalType.NONEMPTYFORMALS))));

    // <NONEMPTYFORMALS> → <FORMAL> <NONEMPTYFORMALS_PRIME> ::= <STRING>
    this.addRule(NonTerminalType.NONEMPTYFORMALS,
        TerminalType.STRING, new KleinRule(new ArrayList<Enum>(Arrays.asList(
        NonTerminalType.FORMAL, SemanticActionType.MAKE_FORMAL,
        NonTerminalType.NONEMPTYFORMALS_PRIME))));

    // <NONEMPTYFORMALS_PRIME> → ε ::= )
    this.addRule(NonTerminalType.NONEMPTYFORMALS_PRIME,
        TerminalType.RIGHTPAREN, epsilon);

    // <NONEMPTYFORMALS_PRIME> → , <NONEMPTYFORMALS> ::= ,
    this.addRule(NonTerminalType.NONEMPTYFORMALS_PRIME,
        TerminalType.COMMA, new KleinRule(new ArrayList<Enum>(Arrays.asList(
        TerminalType.COMMA, NonTerminalType.NONEMPTYFORMALS))));

    // <FORMAL> → <IDENTIFIER> : <TYPE> ::= <STRING>
    this.addRule(NonTerminalType.FORMAL, TerminalType.STRING,
        new KleinRule(new ArrayList<Enum>(Arrays.asList(
        NonTerminalType.IDENTIFIER, TerminalType.COLON,
        NonTerminalType.TYPE))));

    // <BODY> → <PRINT-STATEMENT> <BODY> ::= print
    this.addRule(NonTerminalType.BODY, TerminalType.PRINT,
        new KleinRule(new ArrayList<Enum>(Arrays.asList(
        NonTerminalType.PRINT_STATEMENT, SemanticActionType.MAKE_PRINT,
        NonTerminalType.BODY))));

    // <BODY> → <EXPR> ::= if, (, not, -, <NUMBER>, <BOOLEAN>, <STRING>
    KleinRule bodyToExpr = new KleinRule(new ArrayList<Enum>(Arrays.asList(
        NonTerminalType.EXPR)));
    this.addRule(NonTerminalType.BODY, TerminalType.IF, bodyToExpr);
    this.addRule(NonTerminalType.BODY, TerminalType.LEFTPAREN,
        bodyToExpr);
    this.addRule(NonTerminalType.BODY, TerminalType.NOT,
        bodyToExpr);
    this.addRule(NonTerminalType.BODY, TerminalType.MINUS,
        bodyToExpr);
    this.addRule(NonTerminalType.BODY, TerminalType.NUMBER,
        bodyToExpr);
    this.addRule(NonTerminalType.BODY, TerminalType.BOOLEAN,
        bodyToExpr);
    this.addRule(NonTerminalType.BODY, TerminalType.STRING,
        bodyToExpr);

    //<TYPE> → integer ::= integer
    this.addRule(NonTerminalType.TYPE, TerminalType.INTEGER_STR,
        new KleinRule(new ArrayList<Enum>(Arrays.asList(
        TerminalType.INTEGER_STR))));

    //<TYPE> → boolean ::= boolean
    this.addRule(NonTerminalType.TYPE, TerminalType.BOOLEAN_STR,
        new KleinRule(new ArrayList<Enum>(Arrays.asList(
        TerminalType.BOOLEAN_STR))));

    // <EXPR> → <SIMPLE-EXPR> <EXPR-END> ::=
    //               if, (, not, -, <NUMBER>, <BOOLEAN>, <STRING>
    KleinRule exprToSimpleExpr = new KleinRule(new ArrayList<Enum>(
        Arrays.asList(NonTerminalType.SIMPLE_EXPR, NonTerminalType.EXPR_END)));
    this.addRule(NonTerminalType.EXPR, TerminalType.IF,
        exprToSimpleExpr);
    this.addRule(NonTerminalType.EXPR, TerminalType.LEFTPAREN,
	exprToSimpleExpr);
    this.addRule(NonTerminalType.EXPR, TerminalType.NOT,
        exprToSimpleExpr);
    this.addRule(NonTerminalType.EXPR, TerminalType.MINUS,
        exprToSimpleExpr);
    this.addRule(NonTerminalType.EXPR, TerminalType.NUMBER,
        exprToSimpleExpr);
    this.addRule(NonTerminalType.EXPR, TerminalType.BOOLEAN,
        exprToSimpleExpr);
    this.addRule(NonTerminalType.EXPR, TerminalType.STRING,
        exprToSimpleExpr);

    //<EXPR-END> → ε ::= ), ,, then, else, and, *, /, function, $, or, +, -
    this.addRule(NonTerminalType.EXPR_END, TerminalType.RIGHTPAREN,
        epsilon);
    this.addRule(NonTerminalType.EXPR_END, TerminalType.COMMA,
        epsilon);
    this.addRule(NonTerminalType.EXPR_END, TerminalType.THEN,
        epsilon);
    this.addRule(NonTerminalType.EXPR_END, TerminalType.ELSE,
        epsilon);
    this.addRule(NonTerminalType.EXPR_END, TerminalType.AND,
        epsilon);
    this.addRule(NonTerminalType.EXPR_END, TerminalType.PRODUCT,
        epsilon);
    this.addRule(NonTerminalType.EXPR_END, TerminalType.DIVIDE,
        epsilon);
    this.addRule(NonTerminalType.EXPR_END, TerminalType.FUNCTION,
        epsilon);
    this.addRule(NonTerminalType.EXPR_END, TerminalType.EOF,
        epsilon);
    this.addRule(NonTerminalType.EXPR_END, TerminalType.OR,
        epsilon);
    this.addRule(NonTerminalType.EXPR_END, TerminalType.PLUS,
        epsilon);
    this.addRule(NonTerminalType.EXPR_END, TerminalType.MINUS,
        epsilon);

    // <EXPR-END> → <EXPR-SYMBOL> <EXPR> ::= 	<, =
    KleinRule exprEndToSym = new KleinRule(new ArrayList<Enum>(
        Arrays.asList(NonTerminalType.EXPR_SYMBOL, NonTerminalType.EXPR,
        SemanticActionType.MAKE_OPERATOR)));
    this.addRule(NonTerminalType.EXPR_END, TerminalType.LESSTHAN,
        exprEndToSym);
    this.addRule(NonTerminalType.EXPR_END, TerminalType.EQUAL,
        exprEndToSym);

    //<SIMPLE-EXPR> → <TERM> <SIMPLE-EXPR-END> ::=
    //                     if, (, not, -, <NUMBER>, <BOOLEAN>, <STRING>
    KleinRule simToTerm = new KleinRule(new ArrayList<Enum>(Arrays.asList(
        NonTerminalType.TERM, NonTerminalType.SIMPLE_EXPR_END)));
    this.addRule(NonTerminalType.SIMPLE_EXPR, TerminalType.IF,
        simToTerm);
    this.addRule(NonTerminalType.SIMPLE_EXPR,
        TerminalType.LEFTPAREN, simToTerm);
    this.addRule(NonTerminalType.SIMPLE_EXPR, TerminalType.NOT,
        simToTerm);
    this.addRule(NonTerminalType.SIMPLE_EXPR, TerminalType.MINUS,
        simToTerm);
    this.addRule(NonTerminalType.SIMPLE_EXPR, TerminalType.NUMBER,
        simToTerm);
    this.addRule(NonTerminalType.SIMPLE_EXPR, TerminalType.BOOLEAN,
        simToTerm);
    this.addRule(NonTerminalType.SIMPLE_EXPR, TerminalType.STRING,
        simToTerm);

    //<SIMPLE-EXPR-END> → ε ::= <, =, ), ,, then, else, and, *, /, function, $
    this.addRule(NonTerminalType.SIMPLE_EXPR_END,
        TerminalType.LESSTHAN, epsilon);
    this.addRule(NonTerminalType.SIMPLE_EXPR_END,
        TerminalType.EQUAL, epsilon);
    this.addRule(NonTerminalType.SIMPLE_EXPR_END,
        TerminalType.RIGHTPAREN, epsilon);
    this.addRule(NonTerminalType.SIMPLE_EXPR_END,
        TerminalType.COMMA, epsilon);
    this.addRule(NonTerminalType.SIMPLE_EXPR_END,
        TerminalType.THEN, epsilon);
    this.addRule(NonTerminalType.SIMPLE_EXPR_END,
        TerminalType.ELSE, epsilon);
    this.addRule(NonTerminalType.SIMPLE_EXPR_END,
        TerminalType.AND, epsilon);
    this.addRule(NonTerminalType.SIMPLE_EXPR_END,
        TerminalType.PRODUCT, epsilon);
    this.addRule(NonTerminalType.SIMPLE_EXPR_END,
        TerminalType.DIVIDE, epsilon);
    this.addRule(NonTerminalType.SIMPLE_EXPR_END,
        TerminalType.FUNCTION, epsilon);
    this.addRule(NonTerminalType.SIMPLE_EXPR_END,
        TerminalType.EOF, epsilon);

    // <SIMPLE-EXPR-END> → <SIMPLE-EXPR-SYMBOL> <SIMPLE-EXPR> ::= or, +, -
    KleinRule simEndToSym = new KleinRule(new ArrayList<Enum>(Arrays.asList(
        NonTerminalType.SIMPLE_EXPR_SYMBOL, NonTerminalType.SIMPLE_EXPR,
        SemanticActionType.MAKE_OPERATOR)));
    this.addRule(NonTerminalType.SIMPLE_EXPR_END,
        TerminalType.OR, simEndToSym);
    this.addRule(NonTerminalType.SIMPLE_EXPR_END,
        TerminalType.PLUS, simEndToSym);
    this.addRule(NonTerminalType.SIMPLE_EXPR_END,
        TerminalType.MINUS, simEndToSym);
    
    //<TERM> → <FACTOR> <TERM-END> ::=
    //                     if, (, not, -, <NUMBER>, <BOOLEAN>, <STRING>
    KleinRule termToFact = new KleinRule(new ArrayList<Enum>(Arrays.asList(
            NonTerminalType.FACTOR, NonTerminalType.TERM_END)));
    this.addRule(NonTerminalType.TERM, TerminalType.IF,
        termToFact);
    this.addRule(NonTerminalType.TERM, TerminalType.LEFTPAREN,
        termToFact);
    this.addRule(NonTerminalType.TERM, TerminalType.NOT,
        termToFact);
    this.addRule(NonTerminalType.TERM, TerminalType.MINUS,
        termToFact);
    this.addRule(NonTerminalType.TERM, TerminalType.NUMBER,
        termToFact);
    this.addRule(NonTerminalType.TERM, TerminalType.BOOLEAN,
        termToFact);
    this.addRule(NonTerminalType.TERM, TerminalType.STRING,
        termToFact);
    
    //<TERM-END> → ε ::= or, +, -, <, =, ), ,, then, else, function, $
    this.addRule(NonTerminalType.TERM_END, TerminalType.OR,
        epsilon);
    this.addRule(NonTerminalType.TERM_END, TerminalType.PLUS,
        epsilon);
    this.addRule(NonTerminalType.TERM_END, TerminalType.MINUS,
        epsilon);
    this.addRule(NonTerminalType.TERM_END, TerminalType.LESSTHAN,
        epsilon);
    this.addRule(NonTerminalType.TERM_END, TerminalType.EQUAL,
        epsilon);
    this.addRule(NonTerminalType.TERM_END, TerminalType.RIGHTPAREN,
        epsilon);
    this.addRule(NonTerminalType.TERM_END, TerminalType.COMMA,
        epsilon);
    this.addRule(NonTerminalType.TERM_END, TerminalType.THEN,
        epsilon);
    this.addRule(NonTerminalType.TERM_END, TerminalType.ELSE,
        epsilon);
    this.addRule(NonTerminalType.TERM_END, TerminalType.FUNCTION,
        epsilon);
    this.addRule(NonTerminalType.TERM_END, TerminalType.EOF,
        epsilon);
    
    //<TERM-END> → <TERM-SYMBOL> <TERM> ::= and, *, /
    KleinRule termEndToSym = new KleinRule(new ArrayList<Enum>(Arrays.asList(
        NonTerminalType.TERM_SYMBOL, NonTerminalType.TERM,
        SemanticActionType.MAKE_OPERATOR)));
    this.addRule(NonTerminalType.TERM_END, TerminalType.AND,
        termEndToSym);
    this.addRule(NonTerminalType.TERM_END, TerminalType.PRODUCT,
        termEndToSym);
    this.addRule(NonTerminalType.TERM_END, TerminalType.DIVIDE,
        termEndToSym);
    
    //<FACTOR> → if <EXPR> then <EXPR> else <EXPR> ::= if
    this.addRule(NonTerminalType.FACTOR, TerminalType.IF,
        new KleinRule(new ArrayList<Enum>(Arrays.asList(
        TerminalType.IF, NonTerminalType.EXPR,
        TerminalType.THEN, NonTerminalType.EXPR,
        TerminalType.ELSE, NonTerminalType.EXPR,
        SemanticActionType.MAKE_IF))));
    
    //<FACTOR> → <FACTOR-SYMBOL> <FACTOR> ::= not, -
    KleinRule factToSym = new KleinRule(new ArrayList<Enum>(Arrays.asList(
        NonTerminalType.FACTOR_SYMBOL, NonTerminalType.FACTOR,
        SemanticActionType.MAKE_UNARYOPERATOR)));
    this.addRule(NonTerminalType.FACTOR, TerminalType.NOT,
        factToSym);
    this.addRule(NonTerminalType.FACTOR, TerminalType.MINUS,
        factToSym);
    
    //<FACTOR> → <IDENTIFIER> <FACTOR-END> ::= <STRING>
    this.addRule(NonTerminalType.FACTOR, TerminalType.STRING,
        new KleinRule(new ArrayList<Enum>(Arrays.asList(
        NonTerminalType.IDENTIFIER, NonTerminalType.FACTOR_END))));
    
    //<FACTOR> → <LITERAL> ::= <NUMBER>, <BOOLEAN>
    KleinRule factToLit = new KleinRule(new ArrayList<Enum>(Arrays.asList(
        NonTerminalType.LITERAL, SemanticActionType.MAKE_DECLARED)));
    this.addRule(NonTerminalType.FACTOR, TerminalType.NUMBER,
        factToLit);
    this.addRule(NonTerminalType.FACTOR, TerminalType.BOOLEAN,
        factToLit);
    
    //<FACTOR> → ( <EXPR> ) ::= (
    this.addRule(NonTerminalType.FACTOR, TerminalType.LEFTPAREN,
        new KleinRule(new ArrayList<Enum>(Arrays.asList(
        TerminalType.LEFTPAREN, NonTerminalType.EXPR,
        TerminalType.RIGHTPAREN, SemanticActionType.MAKE_PARAMETERIZED))));
    
    //<FACTOR-SYMBOL> → not ::= not
    this.addRule(NonTerminalType.FACTOR_SYMBOL, TerminalType.NOT,
        new KleinRule(new ArrayList<Enum>(Arrays.asList(
        TerminalType.NOT))));
    
    //<FACTOR-SYMBOL> → - ::= -
    this.addRule(NonTerminalType.FACTOR_SYMBOL, TerminalType.MINUS,
        new KleinRule(new ArrayList<Enum>(Arrays.asList(
        TerminalType.MINUS))));
    
    //<FACTOR-END> → ε	::=
    //         and, *, /, or, +, -, <, =, ), ,, then, else, function, $
    KleinRule factorEndToEpsilon = new KleinRule(new ArrayList<Enum>(
        Arrays.asList(SemanticActionType.MAKE_DECLARED)));
    this.addRule(NonTerminalType.FACTOR_END, TerminalType.AND,
        factorEndToEpsilon);
    this.addRule(NonTerminalType.FACTOR_END, TerminalType.PRODUCT,
        factorEndToEpsilon);
    this.addRule(NonTerminalType.FACTOR_END, TerminalType.DIVIDE,
        factorEndToEpsilon);
    this.addRule(NonTerminalType.FACTOR_END, TerminalType.OR,
        factorEndToEpsilon);
    this.addRule(NonTerminalType.FACTOR_END, TerminalType.PLUS,
        factorEndToEpsilon);
    this.addRule(NonTerminalType.FACTOR_END, TerminalType.MINUS,
        factorEndToEpsilon);
    this.addRule(NonTerminalType.FACTOR_END, TerminalType.LESSTHAN,
        factorEndToEpsilon);
    this.addRule(NonTerminalType.FACTOR_END, TerminalType.EQUAL,
        factorEndToEpsilon);
    this.addRule(NonTerminalType.FACTOR_END,
        TerminalType.RIGHTPAREN, factorEndToEpsilon);
    this.addRule(NonTerminalType.FACTOR_END, TerminalType.COMMA,
        factorEndToEpsilon);
    this.addRule(NonTerminalType.FACTOR_END, TerminalType.THEN,
        factorEndToEpsilon);
    this.addRule(NonTerminalType.FACTOR_END, TerminalType.ELSE,
        factorEndToEpsilon);
    this.addRule(NonTerminalType.FACTOR_END, TerminalType.FUNCTION,
        factorEndToEpsilon);
    this.addRule(NonTerminalType.FACTOR_END, TerminalType.EOF,
        factorEndToEpsilon);
    
    //<FACTOR-END> → ( <ACTUALS> ) ::= (
    this.addRule(NonTerminalType.FACTOR_END, TerminalType.LEFTPAREN,
        new KleinRule(new ArrayList<Enum>(Arrays.asList(
        TerminalType.LEFTPAREN, NonTerminalType.ACTUALS,
        TerminalType.RIGHTPAREN, SemanticActionType.MAKE_CALL))));
    
    // <EXPR-SYMBOL> → < ::= <
    this.addRule(NonTerminalType.EXPR_SYMBOL, TerminalType.LESSTHAN,
        new KleinRule(new ArrayList<Enum>(Arrays.asList(
        TerminalType.LESSTHAN))));
    
    // <EXPR-SYMBOL> → = ::= =
    this.addRule(NonTerminalType.EXPR_SYMBOL, TerminalType.EQUAL,
        new KleinRule(new ArrayList<Enum>(Arrays.asList(
        TerminalType.EQUAL))));
    
    //<SIMPLE-EXPR-SYMBOL> → or	::= or
    this.addRule(NonTerminalType.SIMPLE_EXPR_SYMBOL,
        TerminalType.OR, new KleinRule(new ArrayList<Enum>(Arrays.asList(
        TerminalType.OR))));
    
    //<SIMPLE-EXPR-SYMBOL> → + ::= +
    this.addRule(NonTerminalType.SIMPLE_EXPR_SYMBOL,
        TerminalType.PLUS, new KleinRule(new ArrayList<Enum>(Arrays.asList(
        TerminalType.PLUS))));
    
    //<SIMPLE-EXPR-SYMBOL> → - ::= -
    this.addRule(NonTerminalType.SIMPLE_EXPR_SYMBOL,
        TerminalType.MINUS, new KleinRule(new ArrayList<Enum>(Arrays.asList(
        TerminalType.MINUS))));
    
    //<TERM-SYMBOL> → and ::= and
    this.addRule(NonTerminalType.TERM_SYMBOL, TerminalType.AND,
        new KleinRule(new ArrayList<Enum>(Arrays.asList(
        TerminalType.AND))));
    
    //<TERM-SYMBOL> → * ::= *
    this.addRule(NonTerminalType.TERM_SYMBOL, TerminalType.PRODUCT,
        new KleinRule(new ArrayList<Enum>(Arrays.asList(
        TerminalType.PRODUCT))));
    
    //<TERM-SYMBOL> → / ::= /
    this.addRule(NonTerminalType.TERM_SYMBOL, TerminalType.DIVIDE,
        new KleinRule(new ArrayList<Enum>(Arrays.asList(
        TerminalType.DIVIDE))));
    
    //<ACTUALS> → ε ::= )
    this.addRule(NonTerminalType.ACTUALS, TerminalType.RIGHTPAREN,
        epsilon);
    
    //<ACTUALS> → <NONEMPTYACTUALS> ::=
    //                  if, (, not, -, <NUMBER>, <BOOLEAN>, <STRING>
    KleinRule actToNEAct = new KleinRule(new ArrayList<Enum>(Arrays.asList(
        NonTerminalType.NONEMPTYACTUALS)));
    this.addRule(NonTerminalType.ACTUALS, TerminalType.IF,
        actToNEAct);
    this.addRule(NonTerminalType.ACTUALS, TerminalType.LEFTPAREN,
        actToNEAct);
    this.addRule(NonTerminalType.ACTUALS, TerminalType.NOT,
        actToNEAct);
    this.addRule(NonTerminalType.ACTUALS, TerminalType.MINUS,
        actToNEAct);
    this.addRule(NonTerminalType.ACTUALS, TerminalType.NUMBER,
        actToNEAct);
    this.addRule(NonTerminalType.ACTUALS, TerminalType.BOOLEAN,
        actToNEAct);
    this.addRule(NonTerminalType.ACTUALS, TerminalType.STRING,
        actToNEAct);
    
    //<NONEMPTYACTUALS> → <EXPR> <NONEMPTYACTUALS'> ::=
    //                        if, (, not, -, <NUMBER>, <BOOLEAN>, <STRING>
    KleinRule neActToExpr = new KleinRule(new ArrayList<Enum>(Arrays.asList(
        NonTerminalType.EXPR, NonTerminalType.NONEMPTYACTUALS_PRIME)));
    this.addRule(NonTerminalType.NONEMPTYACTUALS,
        TerminalType.IF, neActToExpr);
    this.addRule(NonTerminalType.NONEMPTYACTUALS,
        TerminalType.LEFTPAREN, neActToExpr);
    this.addRule(NonTerminalType.NONEMPTYACTUALS,
        TerminalType.NOT, neActToExpr);
    this.addRule(NonTerminalType.NONEMPTYACTUALS,
        TerminalType.MINUS, neActToExpr);
    this.addRule(NonTerminalType.NONEMPTYACTUALS,
        TerminalType.NUMBER, neActToExpr);
    this.addRule(NonTerminalType.NONEMPTYACTUALS,
        TerminalType.BOOLEAN, neActToExpr);
    this.addRule(NonTerminalType.NONEMPTYACTUALS,
        TerminalType.STRING, neActToExpr);
    
    //<NONEMPTYACTUALS'> → ε ::= )
    this.addRule(NonTerminalType.NONEMPTYACTUALS_PRIME,
        TerminalType.RIGHTPAREN, epsilon);
    
    //<NONEMPTYACTUALS'> → , <NONEMPTYACTUALS> ::=
    this.addRule(NonTerminalType.NONEMPTYACTUALS_PRIME,
        TerminalType.COMMA, new KleinRule(new ArrayList<Enum>(Arrays.asList(
        TerminalType.COMMA, NonTerminalType.NONEMPTYACTUALS))));
    
    //<LITERAL> → <NUMBER> ::= <NUMBER>
    this.addRule(NonTerminalType.LITERAL, TerminalType.NUMBER,
        new KleinRule(new ArrayList<Enum>(Arrays.asList(
        TerminalType.NUMBER))));
    
    //<LITERAL> → <BOOLEAN> ::= <BOOLEAN>
    this.addRule(NonTerminalType.LITERAL, TerminalType.BOOLEAN,
        new KleinRule(new ArrayList<Enum>(Arrays.asList(
        TerminalType.BOOLEAN))));
    
    //<IDENTIFIER> → <STRING> ::= <STRING>
    this.addRule(NonTerminalType.IDENTIFIER, TerminalType.STRING,
        new KleinRule(new ArrayList<Enum>(Arrays.asList(
        TerminalType.STRING))));
    
    //<PRINT-STATEMENT> → print ( <EXPR> ) ::= print
    this.addRule(NonTerminalType.PRINT_STATEMENT,
        TerminalType.PRINT, new KleinRule(new ArrayList<Enum>(Arrays.asList(
        TerminalType.PRINT, TerminalType.LEFTPAREN, NonTerminalType.EXPR,
        TerminalType.RIGHTPAREN))));
  }
}
