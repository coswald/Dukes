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
package com.dukes.lang.parser;

import com.dukes.lang.parser.AbstractParseTable;
import com.dukes.lang.parser.node.AbstractSyntaxNode;
import com.dukes.lang.parser.node.NullNode;
import com.dukes.lang.scanner.AbstractScanner;
import com.dukes.lang.scanner.AbstractToken;

import java.util.Stack;

/**
 * Represents an parser that works on a table. This is not in stark constrast
 * to a recursive decent algorithm, as this type uses a stack to construct the
 * necessary AST. However, this algorithm works by manually containing a stack
 * and pushing values to it as a {@code AbstractScanner} works through a
 * program.
 * 
 * @author Coved W Oswald
 * @version 1.0
 * @since 0.2.0
 */
public abstract class AbstractTableParser<E extends AbstractScanner<F>,
    F extends AbstractToken, G extends AbstractParseTable> extends Object
    implements Parser {
  
  /**
   * The parse table for the language grammar. This is constructed using the
   * first and follow sets of a grammar, and is final as it should not be
   * reassigned after it is created.
   */
  protected final G PARSETABLE;
  
  /**
   * The scanner that the parser uses to go through the program.
   */
  protected E scanner;
  
  /**
   * The stack that the non-terminals, terminals, and semantic actions get put
   * onto. Basically, the program gets put onto this stack.
   */
  protected Stack<Enum> stack;
  
  /**
   * The stack that the semantic values get pushed to.
   */
  protected Stack<AbstractSyntaxNode> semanticStack;

  private boolean hasParsed = false;
  private AbstractSyntaxNode ast = new NullNode();
  private boolean isValidProgram;
  
  /**
   * Constructs the table parser with the given parse table and the scanner
   * given.
   * @param apt The abstract parse table.
   * @param scanner The scanner.
   */
  protected AbstractTableParser(G apt, E scanner) {
    this.PARSETABLE = apt;
    this.scanner = scanner;
    this.stack = new Stack<Enum>();
    this.semanticStack = new Stack<AbstractSyntaxNode>();
  }
  
  /**
   * Parses the table and creates an abstract syntax tree. This is
   * implementation dependend, but should be done in an LL(1) fashion if
   * possible. It is unclear to us if our understanding of a table driven
   * parser would work for LL(2) or any other types of parsers. Therefore, this
   * method would allow the programmer to expirement with a language grammar
   * and try it out! <b>NOTE: This method should not be called more than
   * once.</b>
   * @return The program tree.
   */
  protected abstract AbstractSyntaxNode parseProgram();
  
  /**
   * Returns whether a given program parses correctly or not. This does not
   * mean that the program is completely <b>correct</b>; however, it does mean
   * a valid syntax tree can be generated. This handles the fact that the
   * {@link #parseProgram()} can't be called more than once.
   * @return {@code true} if the program generates an ast, {@code false}
   *     otherwise.
   */
  @Override
  public boolean isValid() {
    if(!hasParsed) {
      isValidProgram = this.generateAST() != null;
    }
    return isValidProgram;
  }
  
  /**
   * Generates the ast for the program. This handles the fact that the 
   * {@link #parseProgram()} cannot be called more than once.
   * @return The program's AST.
   */
  @Override
  public AbstractSyntaxNode generateAST() {
    if(this.ast instanceof NullNode && !this.hasParsed) {
      ast = this.parseProgram();
      if(!(ast instanceof NullNode)) {
        this.hasParsed = true;
      }
      return this.ast;
    }
    else {
      return this.ast;
    }
  }
}
