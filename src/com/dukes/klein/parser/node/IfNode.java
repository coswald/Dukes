package com.dukes.klein.parser.node;

import com.dukes.klein.parser.node.ExpressionNode;

/**
 *
 * @author Coved W Oswald
 * @version 1.0
 * @since 0.2.0
 */
public class IfNode extends ExpressionNode {
  public IfNode(ExpressionNode testExpression, ExpressionNode ifExpression,
                ExpressionNode elseExpression) {
    super(testExpression, ifExpression, elseExpression);
  }
}