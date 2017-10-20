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
 * Shows an if statement within Klein. An if statement consists of three
 * expressions: the test expression, the expression executed if the test is
 * true, and the expression that is executed if the test is false. That means
 * the first expression must be a boolean value. As long as the if expression
 * is not in a print statement, the return types of the two following
 * expressions must match.
 * @author Coved W Oswald
 * @version 1.0
 * @since 0.3.0
 */
public class IfNode extends ExpressionNode {
  /**
   * Constructs an if expression.
   * @param testExpression
   * @param ifExpression
   * @param elseExpression
   */
  public IfNode(ExpressionNode testExpression, ExpressionNode ifExpression,
                ExpressionNode elseExpression) {
    super(testExpression, ifExpression, elseExpression);
  }
}
