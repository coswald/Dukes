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

import com.dukes.lang.parser.node.NullNode;
import com.dukes.lang.parser.AbstractParseTable;
import com.dukes.lang.parser.node.AbstractSyntaxNode;
import com.dukes.lang.scanner.AbstractScanner;
import com.dukes.lang.scanner.AbstractToken;

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
  protected E scanner;
  protected boolean hasParsed = false;
  protected AbstractSyntaxNode ast = new NullNode();
  
  private boolean isValidProgram;
  
  protected AbstractTableParser(G apt, E scanner) {
    this.PARSETABLE = apt;
    this.scanner = scanner;
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
}
