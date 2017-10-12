/*
 * Copyright (C) 2017 Coved W Oswald
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
package com.dukes.klein.scanner;

import java.lang.Object;

/**
 * Describes an object that iterates over an entire object for characters. Now,
 * we chose to not use the {@code Iterator} interface, as it would not allow
 * for primitive {@code char}s to be read. This is why this class is included.
 *
 * @author Coved W Oswald
 * @version 1.0
 * @since 1.0
 */
public abstract class Inputter extends Object {
  private char currentChar;

  /**
   * The current position of the Inputter within the parsed object.
   */
  protected int position;

  /**
   * Returns the character at the current position of the parser.
   *
   * @return The current character.
   */
  public char currentChar() {
    return this.currentChar;
  }

  /**
   * Returns the next character within the inputter. <b>This is the only method
   * that should increment the position variable</b>. Making position protected
   * allows classes that need to maniuplate position available to it, but this
   * method will automatically increment the position by one.
   */
  public void next() {
    this.currentChar = this.lookAhead();
    position++;
  }

  /**
   * Determines whether there is another character in the inputter object.
   *
   * @return {@code true} if there is another character in the parser,
   * {@code false} otherwise.
   */
  public abstract boolean hasNext();

  /**
   * Looks ahead in the inputter without consuming the current character. This
   * method has undefined behaviour at the end of the parser.
   *
   * @return The next character in the stream, if there is one.
   */
  public abstract char lookAhead();
}
