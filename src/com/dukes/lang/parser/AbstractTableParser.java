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

import com.dukes.klein.parser.node.NullNode;
import com.dukes.lang.parser.AbstractParseTable;
import com.dukes.lang.parser.node.AbstractSyntaxNode;
import com.dukes.lang.scanner.AbstractScanner;
import com.dukes.lang.scanner.AbstractToken;

public abstract class AbstractTableParser<E extends AbstractScanner<F>,
    F extends AbstractToken, G extends AbstractParseTable> extends Object
    implements Parser {
  
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
