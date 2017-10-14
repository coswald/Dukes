package com.dukes.klein.parser.node;

import com.dukes.lang.parser.node.AbstractSyntaxNode;

/**
 * @author Coved W Oswald
 * @version 1.0
 * @since 0.2.0
 */
public abstract class ExpressionNode extends AbstractSyntaxNode {
  public ExpressionNode(ExpressionNode... exprNode) {
    super(exprNode);
  }
}