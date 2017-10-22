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

import com.dukes.lang.parser.node.AbstractSyntaxNode;

/**
 * Discerns a function in Klein. This contains an identifier, a return type, a
 * body, and a variable amount of formals.
 * @author Coved W Oswald
 * @version 1.0
 * @since 0.3.0
 */
public class FunctionNode extends AbstractSyntaxNode {
  private TerminalNode name;

  /**
   * Constructs a function.
   * @param name The name of the function.
   * @param returnType The return type of the function.
   * @param bodyNode The body of the function.
   * @param formals The parameters of the function.
   */
  public FunctionNode(TerminalNode name, TerminalNode returnType,
                      BodyNode bodyNode, FormalNode... formals) {
    super(AbstractSyntaxNode.concat(bodyNode, formals));
    this.name = name;
    switch(returnType.getValue()){
      case "integer":
        this.type = AbstractSyntaxNode.INTEGER_TYPE;
        break;
      case "boolean":
        this.type = AbstractSyntaxNode.BOOLEAN_TYPE;
        break;
    }
  }

  /**
   * Returns the identifier of the function. Also known as the name of the
   * function.
   * @return The name of the function.
   */
  public TerminalNode getName() {
    return this.name;
  }

  /**
   * Gets the name and return type between two brackets.
   * @return The name and the return type.
   */
  @Override
  public String dataAsString() {
    return "[Name: " + this.getName() +
        ", Return Type: " + this.typeToString() + "]";
  }
}
