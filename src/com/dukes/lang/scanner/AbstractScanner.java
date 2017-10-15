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
  
  /**
   * The {@code Inputter} class used for looking through a program.
   */
  protected Inputter input;
  
  /**
   * Constructs a scanner with the given Inputter.
   * @param input The program inputter.
   */
  protected AbstractScanner(Inputter input) {
    this.input = input;
    this.nextToken = null;
  }
  
  /**
   * Looks at the next token that would be generated. This does not consume the
   * next token, but just looks at it.
   * @return The {@code AbstractToken} that would be generated after the
   *     current one.
   * @throws LexicalAnalysisException If {@link #generateNextToken()} throws an
   *     exception.
   * @throws LexicalScanningException If an invalid character was seen.
   */
  public E peek() throws LexicalAnalysisException, LexicalScanningException {
    if(nextToken == null) {
      this.nextToken = this.next();
    }
    return this.nextToken;
  }
  
  /**
   * Goes to the next token. This will consume the current token, moving to
   * the next value as predicated by {@link #generateNextToken()}.
   * @return The {@code AbstractToken} that is generated.
   * @throws LexicalAnalysisException If {@link #generateNextToken()} throws an
   *     exception.
   * @throws LexicalScanningException If an invalid character was seen.
   */
  public E next() throws LexicalAnalysisException, LexicalScanningException {
    if(nextToken != null) {
      E currentToken = this.nextToken;
      this.nextToken = null;
      return currentToken;
    }
    else {
      return this.generateNextToken();
    }
  }
  
  /**
   * Generates the next token. This consumes the characters found in the
   * {@code Inputter} and generates the valid token seen by the characters.
   * This is defined by the language specification.
   * @return The generated next token.
   * @throws LexicalAnalysisException If there was a valid character seen, but
   *     did not fit the language specification. An example might be in a
   *     language that says there is a range of numbers, whether a comment was
   *     ended, etc.
   * @throws LexicalScanningException If an invalid character was seen.
   */
  public abstract E generateNextToken() throws LexicalAnalysisException, 
      LexicalScanningException;
}
