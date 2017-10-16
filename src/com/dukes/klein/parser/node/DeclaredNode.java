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

/**
 * @author Coved W Oswald
 * @version 1.0
 * @since 0.2.0
 */
public class DeclaredNode extends ExpressionNode {
  public static final int IDENTIFIER_TYPE = 0;
  public static final int BOOLEAN_TYPE = 1;
  public static final int INTEGER_TYPE = 2;

  private TerminalNode declared;
  private int type;

  public DeclaredNode(TerminalNode declared, int type)
      throws IllegalArgumentException {
    super();

    if(type > 2) {
      throw new IllegalArgumentException("Invalid type given to declared " +
          "Node!");
    }

    this.declared = declared;
    this.type = type;
  }

  public String getDeclared() {
    return this.declared.getValue();
  }

  public boolean isType(int type) throws IllegalArgumentException {
    if(type > 2) {
      throw new IllegalArgumentException("Invalid type asked of declared " +
          "node!");
    }

    return this.type == type;
  }

  @Override
  public String dataAsString() {
    return "[Value: " + this.declared.toString() +
        ", Type: " + DeclaredNode.typeToString(this.type) + "]";
  }

  public static String typeToString(int type) {
    switch(type) {
      case DeclaredNode.IDENTIFIER_TYPE:
        return "Identifier";
      case DeclaredNode.BOOLEAN_TYPE:
        return "Boolean";
      case DeclaredNode.INTEGER_TYPE:
        return "Integer";
      default:
        throw new IllegalArgumentException(
            "Invalid type given to typeToString!");
    }
  }

}
