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

/**
 * <p>A {@code KlienTokenType} used as the enumeration to describe what a token
 * actually is. This is defined by the Klien Language specification, and we
 * interpreted it as follows:
 * <table width="100%"><tr>                         
 * <th>Token Types</th><th>Positive Examples</th><th>Negative Examples</th>
 * </tr>
 * <tr><td>Keywords</td><td>function</td><td>12, main, print</td></tr>
 * <tr><td>Identifiers</td><td>main, print, mod,  Name, NameOfFunction</td> 
 * 
 * <td>1Festival, *Hi, greater than 256 characters</td></tr>
 * 
 * <tr><td>Types</td><td>integer, boolean</td><td>true, false, &lt; -2^32, &gt;
 * 2^32 - 1, Integer, Boolean, 2, 69</td></tr>
 * 
 * <tr><td>SimpleExpression</td><td>+, or, -</td><td>&lt;, and, false</td></tr>
 * <tr><td>Expression</td><td>&lt;, =</td><td> +, *, &gt;, &lt;=</td></tr>
 * <tr><td>Term</td><td>*, and, /</td><td>true, =, not</td><tr>
 * <tr><td>Boolean</td><td>true, false</td><td>True, False, 1, 0</td></tr> 
 * <tr><td>Integer</td><td>, 0, 999, -1</td><td>"H", 2^32, 1.8, -2^32</td></tr>
 * <tr><td>StartComment</td><td>(*</td><td>*), *, (</td></tr>
 * <tr><td>EndComment</td><td>*)</td><td>(*, *, )</td></tr>
 * <tr><td>Separator</td><td>, OR ;</td><td>and, 123, *</td></tr>
 * <tr><td>LeftParenthesis</td><td>(</td><td>)</td></tr>
 * <tr><td>RightParenthesis</td><td>)</td><td>(</td></tr>
 * <tr><td>If</td><td>if</td><td>IF, If, if8</td></tr>
 * <tr><td>Then</td><td>then</td><td>Then, THEN, then1</td></tr>
 * <tr><td>Else</td><td>else</td><td>Else, ELSE, else2</td></tr>
 * <tr><td>Not</td><td>not</td><td>Not, NOT, !=</td></tr>
 * </table></p>
 * @since 1.0
 * @author Coved W Oswald
 * @version 1.0
 */
public enum KleinTokenType
{
  STARTCOMMENT(0),
  ENDCOMMENT(1),
  EOF(2),
  KEYWORD(3),
  IDENTIFIER(4),
  LEFTPARENTHESIS(5),
  RIGHTPARENTHESIS(6),
  SEPARATOR(7),
  TYPE(8),
  SIMPLEEXPRESSION(9),
  EXPRESSION(10),
  TERM(11),
  INTEGER(12),
  BOOLEAN(13),
  IF(14),
  THEN(15),
  ELSE(16),
  NOT(17);

  private final int id;

  KleinTokenType(int id){
    this.id = id;
  }
  public int getId(){
    return id;
  }

}
