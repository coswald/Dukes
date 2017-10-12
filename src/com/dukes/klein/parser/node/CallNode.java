package com.dukes.klein.parser.node;

import com.dukes.klein.parser.node.ExpressionNode;

/**
 *
 * @author Coved W Oswald
 * @version 1.0
 * @since 0.2.0
 */
public class CallNode extends ExpressionNode {
  private String identifier;
  
  public CallNode(String identifier, ExpressionNode... exprNodes) {
    super(exprNodes);
    this.identifier = identifier;
  }
}