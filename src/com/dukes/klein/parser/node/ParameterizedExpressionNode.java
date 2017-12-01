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
 * Describes an expression within a left and right parenthesis, respectively.
 * This should indicate that it is executed first, although the class is
 * basically a wrapper. The tree structure should take care of the execution
 * cycle.
 * @author Coved W Oswald
 * @version 1.0
 * @since 0.3.0
 */
public class ParameterizedExpressionNode extends ExpressionNode {
  /**
   * Constructs a parameterized expression.
   * @param exprNode The expression found between two parenthesis.
   */
  public ParameterizedExpressionNode(ExpressionNode exprNode) {
    super(exprNode);
  }
  
  @Override
  public String toTargetCode() {
    return "";
  }
}
