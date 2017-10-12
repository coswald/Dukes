package com.dukes.klein.parser.node;

import com.dukes.klein.parser.node.AbstractSyntaxNode;
import com.dukes.klein.parser.node.ExpressionNode;
import com.dukes.klein.parser.node.PrintNode;

import java.lang.System;

/**
 *
 * @author Coved W Oswald
 * @version 1.0
 * @since 0.2.0
 */
public class BodyNode extends AbstractSyntaxNode {
  public BodyNode(ExpressionNode exprNode, PrintNode... prints) {
    super(AbstractSyntaxNode.concat(exprNode, prints));
  }
  
  public ExpressionNode getExpression() {
    return this.children[0];
  }
  public PrintNode[] getPrintNodes() {
    AbstractSyntaxNode[] ret = new AbstractSyntaxNode[this.children.length - 1];
    System.arraycopy(this.children, 1, ret, 0, ret.length);
    return ret;
  }
}