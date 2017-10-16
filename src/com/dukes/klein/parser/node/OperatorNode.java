package com.dukes.klein.parser.node;

import com.dukes.lang.parser.node.ExpressionNode;

/**
 * @author Coved W Oswald
 * @version 1.0
 * @since 0.2.0
 */
public class OperatorNode extends ExpressionNode {
  private TerminalNode operator;

  public OperatorNode(TerminalNode operator, ExpressionNode leftNode,
                      ExpressionNode rightNode) {
    super(rightNode, leftNode);
    this.operator = operator;
  }

  public String getOperator() {
    return this.operator.getValue();
  }

  @Override
  public String dataAsString() {
    return "[" + operator.toString() + "]";
  }

  @Override
  public String toString() {
    return "Operator: " +
        this.children[0] + this.getOperator() + this.children[1];
  }
}
