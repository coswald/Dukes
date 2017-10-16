package com.dukes.klein.parser.node;

import com.dukes.lang.parser.node.ExpressionNode;

/**
 * @author Coved W Oswald
 * @version 1.0
 * @since 0.2.0
 */
public class ParameterizedExpressionNode extends ExpressionNode {
  public ParameterizedExpressionNode(ExpressionNode exprNode) {
    super(exprNode);
  }

  @Override
  public String toString() {
    return "Paramaterized: " + this.children[0].toString();
  }
}
