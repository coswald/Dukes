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
package com.dukes.klein.parser.node;

import com.dukes.lang.parser.node.ExpressionNode;
import com.dukes.klein.generator.KleinCodeGenerator;

/**
 * Describes any declared value, such as an identifier, an integer, and a
 * boolean. 
 * @author Coved W Oswald
 * @version 1.0
 * @since 0.3.0
 */
public class DeclaredNode extends ExpressionNode {

  private TerminalNode declared;

  /**
   * Constructs a declared value in Klein.
   * @param declared The value that is declared.
   * @param type The type of the declared value.
   * @throws IllegalArgumentException If the given value isn't a valid value.
   */
  public DeclaredNode(TerminalNode declared, int type)
      throws IllegalArgumentException {
    super();

    if(type > 5) {
      throw new IllegalArgumentException("Invalid type given to declared " +
          "Node!");
    }

    this.declared = declared;
    this.type = type;
  }
  
  /**
   * Returns the value of the declared node.
   * @return The value of the declared node.
   */
  public String getDeclared() {
    return this.declared.getValue();
  }
  
  /**
   * Tests whether the given type is the type of this node.
   * @param type The type to test.
   * @throws IllegalArgumentException If the type lies outside the type range.
   */
  public boolean isType(int type) throws IllegalArgumentException {
    if(type > 7) {
      throw new IllegalArgumentException("Invalid type asked of declared " +
          "node!");
    }

    return this.type == type;
  }
  
  /**
   * Returns the comma separated value and type between two brackets.
   * @return The value and type.
   */
  @Override
  public String dataAsString() {
    return "[Value: " + this.declared.toString() +
        ", Type: " + this.typeToString() + "]";
  }
  
  @Override
  public String toTargetCode() {
    String s = "";
    String value = "";
    if(this.isType(1) || this.isType(3) || this.isType(5) || this.isType(7))
    {
      //TO IMPLEMENT, using LD and memory addressing.
      return s;
    }
    else if(this.isType(2))
    {
      value = (this.getDeclared().equals("true") ? "1" : "0");
    }
    else
    {
      value = this.getDeclared();
    }
    s += KleinCodeGenerator.emitCode("LDC", null, value, "0");
    s += KleinCodeGenerator.emitCode("ST", null, null, "0");
    return s;
  }
}
