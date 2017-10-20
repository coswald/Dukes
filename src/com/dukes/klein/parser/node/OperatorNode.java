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
import com.dukes.lang.parser.node.NullNode;

/**
 * This class describes an operation in Klein. This can be either a binary or
 * unary operation. If the left side is a {@code NullNode}, then the expression
 * is a unary operation; otherwise, it is a binary expression. No {@code null}
 * values should be added to the operator node. The operation should be a valid
 * Klein operation; however, this is not checked within this class.
 * @author Coved W Oswald
 * @version 1.0
 * @since 0.3.0
 */
public class OperatorNode extends ExpressionNode {
  private TerminalNode operator;

  /**
   * Constructs an operation withe the given operator, left hand side, and
   * right hand side. Note that this operation is unary if the left side is a
   * {@code NullNode}.
   * @param operator The operator of the operation.
   * @param leftNode The left hand side.
   * @param rightNode The right hand side.
   */
  public OperatorNode(TerminalNode operator, ExpressionNode leftNode,
                      ExpressionNode rightNode) {
    super(rightNode, leftNode);
    this.operator = operator;
  }
  
  /**
   * Constructs a unary operation.
   * @param operator The operator the the unary operation.
   * @param exprNode The expression node to apply the operation to.
   */
  public OperatorNode(TerminalNode operator, ExpressionNode exprNode) {
    this(operator, new NullNode(), exprNode);
  }
  
  /**
   * Returns the operator of this operation.
   * @return The string value of the operator.
   */
  public String getOperator() {
    return this.operator.getValue();
  }
  
  /**
   * Returns the operator seperated by brackets. This is done for the string
   * representation of this operation.
   * @return The operator seperated by brackets.
   */
  @Override
  public String dataAsString() {
    return "[" + this.getOperator() + "]";
  }
}
