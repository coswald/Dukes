package com.dukes.lang.parser.node;

/**
 * @author Daniel J. Holland
 * @version 1.0
 */
public abstract class AbstractTreeTraverser extends Object {

  protected AbstractSyntaxNode top;

  protected AbstractTreeTraverser(AbstractSyntaxNode top) {
    this.top = top;
  }

  protected interface NodeOperation {
    void traversed(AbstractSyntaxNode node);

    void execute(AbstractSyntaxNode node, Object... objects);
  }

  public void semanticCheck() {
  }

  public AbstractSyntaxNode getTop() {
    return this.top;
  }

  protected void traversePreOrder(AbstractSyntaxNode node, NodeOperation op,
                                  Object... objects) {
    op.traversed(node);
    for(AbstractSyntaxNode child : node.getChildren()) {
      this.traversePreOrder(child, op, objects);
    }
    op.execute(node, objects);
  }

}
