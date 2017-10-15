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

import com.dukes.lang.parser.node.AbstractSyntaxNode;

/**
 * <p>An interface that describes the workings of a language parser. A parser
 * should be able to identify if a program is valid, and be able to generate an
 * abstract syntax tree. This is done by the {@link #isValid()} and 
 * {@link #generateAST()} methods, respectively. Note that a parser should be
 * fed a specific scanner in order to make sense of tokens, not characters.</p>
 * @author Coved W Oswald
 * @version 1.0
 * @since 0.2.0
 */
public interface Parser {
  
  /**
   * Determines whether a program is valid. This determines on the programmer's
   * implementation of the method itself.
   * @return {@code true} when a program is valid, {@code false} otherwise.
   */
  public boolean isValid();

  /**
   * Generates an abstract syntax tree based on the program.
   * @return An {@code AbstractSyntaxNode} that represents the given program.
   * @throws InvalidStackValueException If the semantic actions did not receive
   *     valid values.
   * @throws ParsingException If the parsing did not go as planned.
   */
  public AbstractSyntaxNode generateAST() throws ParsingException;
}
