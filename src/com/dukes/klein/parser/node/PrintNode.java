package com.dukes.klein.parser.node;

import com.dukes.klein.parser.node.AbstractSyntaxNode;
import com.dukes.klein.parser.node.ExpressionNode;

/**
 *
 * @author Coved W Oswald
 * @version 1.0
 * @since 0.2.0
 */
public class PrintNode extends AbstractSyntaxNode {
  public PrintNode(ExpressionNode exprNode) {
    super(exprNode);
  }

  @Override
  public String toString()
  {
    return "Print: " + this.children[0].toString();
  }
}