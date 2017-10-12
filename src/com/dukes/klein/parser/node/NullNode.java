package com.dukes.klein.parser.node;

import com.dukes.klein.parser.node.ExpressionNode;

/**
 *
 * @author Coved W Oswald
 * @version 1.0
 * @since 0.2.0
 */
public class NullNode extends ExpressionNode {
  public FormalNode() {
    super();
  }
  
  @Override
  public String toString() {
    return "Null Node";
  }
}