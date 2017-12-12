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
import com.dukes.lang.parser.node.ExpressionNode;

/**
 * Defines the body of a function. This can contain any number of print
 * statements, but only one expression node.
 * @author Coved W Oswald
 * @version 1.0
 * @since 0.3.0
 */
public class BodyNode extends AbstractSyntaxNode {
  /**
   * Constructs a body node.
   * @param exprNode The expressionnode.
   * @param prints The print statements.
   */
  public BodyNode(ExpressionNode exprNode, PrintNode... prints) {
    super(AbstractSyntaxNode.concat(exprNode, prints));
  }

  /**
   * Gets the type between two brackets.
   * @return The {@link #typeToString()} function.
   */
  @Override
  public String dataAsString() {
    return "[Type: " + this.typeToString() +"]";
  }

  /**
   * Gets the expression node of the body.
   * @return The expression Node.
   */
  public AbstractSyntaxNode getExpression() {
    return this.children[this.children.length - 1];
  }
  
  @Override
  public String toTargetCode() {
    return "";
  }
  
  @Override
  public String getReturnRegister() {
    return this.children[this.children.length - 1].getReturnRegister();
  }
}
