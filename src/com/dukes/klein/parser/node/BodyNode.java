package com.dukes.klein.parser.node;

import com.dukes.lang.parser.node.AbstractSyntaxNode;
import com.dukes.lang.parser.node.ExpressionNode;

/**
 * @author Coved W Oswald
 * @version 1.0
 * @since 0.2.0
 */
public class BodyNode extends AbstractSyntaxNode {
  public BodyNode(ExpressionNode exprNode, PrintNode... prints) {
    super(AbstractSyntaxNode.concat(exprNode, prints));
  }

  public AbstractSyntaxNode getExpression() {
    return this.children[0];
  }

  public AbstractSyntaxNode[] getPrintNodes() {
    AbstractSyntaxNode[] ret =
        new AbstractSyntaxNode[this.children.length - 1];

    System.arraycopy(this.children, 1, ret, 0, ret.length);
    return ret;
  }

  @Override
  public String toString() {
    return "Body: " + super.toString();
  }
}
