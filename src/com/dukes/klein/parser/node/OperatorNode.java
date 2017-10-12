package com.dukes.klein.parser.node;

import com.dukes.klein.parser.node.ExpressionNode;

/**
 *
 * @author Coved W Oswald
 * @version 1.0
 * @since 0.2.0
 */
public class OperatorNode extends ExpressionNode {
  private String operator;
  
  public OperatorNode(String operator, ExpressionNode leftNode,
                  ExpressionNode rightNode) {
    super(leftNode, rightNode);
    this.operator = operator;
  }
}