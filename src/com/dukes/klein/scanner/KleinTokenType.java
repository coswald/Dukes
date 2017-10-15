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
package com.dukes.klein.scanner;

/**
 * <p>A {@code KlienTokenType} used as the enumeration to describe what a token
 * actually is. This is defined by the Klein Language specification, and we
 * interpreted it as follows:
 * <table width="100%">
 * <tr><th>Token Types</th><th>Token Values</th></tr>
 * <tr><td>Keywords</td><td>function, main, print, if, then, else</td></tr>
 * <tr><td>Identifiers</td><td>Name_Of_a_Function</td></tr>
 * <tr><td>Types</td><td>integer, boolean</td></tr>
 * <tr><td>Symbol</td><td>+, -, &lt, =, *, /, and, or, not</td></tr>
 * <tr><td>Boolean</td><td>true, false</td></tr>
 * <tr><td>Integer</td><td>0, 999, -1</td></tr>
 * <tr><td>StartComment</td><td>(*</td></tr>
 * <tr><td>EndComment</td><td>*)</td></tr>
 * <tr><td>Separator</td><td>, ;</td></tr>
 * <tr><td>LeftParenthesis</td><td>(</td></tr>
 * <tr><td>RightParenthesis</td><td>)</td></tr>
 * <tr><td>EndOfFile</td><td>$</td></tr>
 * </table></p>
 *
 * @author Coved W Oswald
 * @version 1.0
 * @since 0.1.0
 */
public enum KleinTokenType {
  /**
   * The start of a comment. The only valid value is '(*'.
   */
  STARTCOMMENT(0),
  
  /**
   * The end of a comment. The only valid value is '*)'.
   */
  ENDCOMMENT(1),
  
  /**
   * The end of a file. The only valid value is really nothing, but for theory
   * purposes we will say '$'.
   */
  EOF(2),
  
  /**
   * Any keyword in Klein. This can be, exactly: 'function', 'main', 'print',
   * 'if', 'then', and 'else'.
   */
  KEYWORD(3),
  
  /**
   * Any identifier in Klein. This can be any alpha character followed by any
   * alhpa-numeric character or an underscore that does not exceed a length of
   * 256.
   */
  IDENTIFIER(4),
  
  /**
   * The left parenthesis. The only valid value is '('.
   */
  LEFTPARENTHESIS(5),
  
  /**
   * The right parenthesis. The only valid value is ')'.
   */
  RIGHTPARENTHESIS(6),
  
  /**
   * A seperator in Klein. The two valid values are ',' and ':'.
   */
  SEPARATOR(7),
  
  /**
   * A type in klein. The two valid values are 'integer' and 'boolean'.
   */
  TYPE(8),
  
  /**
   * A symbol in Klein. This can be, exactly: '+', '-', '&lt', '=', '*', '/', 
   * 'and', 'or', and 'not'.
   */
  SYMBOL(9),
  
  /**
   * An integer in Klein.
   */
  INTEGER(10),
  
  /**
   * A boolean in Klein. The two valid values are 'true' and 'false'.
   */
  BOOLEAN(11);

  private final int id;
  
  /**
   * Constructs a {@code KleinTokenType} with the given Id.
   * @param id The unique identifier.
   */
  KleinTokenType(int id) {
    this.id = id;
  }
  
  /**
   * Returns this token's unique identifier.
   * @return The unique identifier this token holds.
   */
  public int getId() {
    return id;
  }
}
