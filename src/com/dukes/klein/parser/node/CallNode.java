package com.dukes.klein.parser.node;

import com.dukes.klein.parser.node.ExpressionNode;

/**
 *
 * @author Coved W Oswald
 * @version 1.0
 * @since 0.2.0
 */
public class CallNode extends ExpressionNode {
  private TerminalNode identifier;
  
  public CallNode(TerminalNode identifier, ExpressionNode... exprNodes) {
    super(exprNodes);
    this.identifier = identifier;
  }

  public String getIdentifier(){return this.identifier.getValue();}

  @Override
  public String toString() {
    return "Call: " + this.identifier + this.children[0];
  }

}