package com.dukes.lang.parser.node;

/**
 * @author Coved W Oswald
 * @version 1.0
 * @since 0.2.0
 */
public class NullNode extends ExpressionNode {
  public NullNode() {
    super();
  }

  @Override
  public String toString() {
    return "Null Node";
  }
}
