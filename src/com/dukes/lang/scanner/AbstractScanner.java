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
package com.dukes.lang.scanner;

/**
 * Represents an object that can produce {@code AbstractToken}s from a given
 * {@code Inputter}.
 *
 * @author Coved W Oswald
 * @version 1.0
 * @since 0.1.0
 */
public abstract class AbstractScanner<E extends AbstractToken> extends Object {
  private E nextToken;
  protected Inputter input;

  public AbstractScanner(Inputter input) {
    this.input = input;
    this.nextToken = null;
  }

  public E peek() throws LexicalScanningException {
    if(nextToken == null) {
      this.nextToken = this.next();
    }
    return this.nextToken;
  }

  public E next() throws LexicalScanningException {
    if(nextToken != null) {
      E currentToken = this.nextToken;
      this.nextToken = null;
      return currentToken;
    }
    else {
      return this.generateNextToken();
    }
  }

  public abstract E generateNextToken()
      throws LexicalScanningException;
}
