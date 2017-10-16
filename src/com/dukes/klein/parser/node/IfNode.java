package com.dukes.klein.parser.node;

import com.dukes.lang.parser.node.ExpressionNode;

/**
 * @author Coved W Oswald
 * @version 1.0
 * @since 0.2.0
 */
public class IfNode extends ExpressionNode {
  public IfNode(ExpressionNode testExpression, ExpressionNode ifExpression,
                ExpressionNode elseExpression) {
    super(testExpression, ifExpression, elseExpression);
  }

  @Override
  public String toString() {
    return "If: " +
        this.children[0] + " Then " +
        this.children[1] + " Else " + this.children[2];
  }
}
