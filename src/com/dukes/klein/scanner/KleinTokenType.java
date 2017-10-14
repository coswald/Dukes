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
 * <tr><td>Keywords</td><td>function, main, print, if then, else</td></tr>
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
  STARTCOMMENT(0),
  ENDCOMMENT(1),
  EOF(2),
  KEYWORD(3),
  IDENTIFIER(4),
  LEFTPARENTHESIS(5),
  RIGHTPARENTHESIS(6),
  SEPARATOR(7),
  TYPE(8),
  SYMBOL(9),
  INTEGER(10),
  BOOLEAN(11);

  private final int id;

  KleinTokenType(int id) {
    this.id = id;
  }

  public int getId() {
    return id;
  }

}
