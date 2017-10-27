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
    public void execute(Object... objects);
  }

  //protected void traversePreOrder(NodeOperation operation, Object... objects){}

  public void semanticCheck(){}

  public AbstractSyntaxNode getTop(){return this.top;}

  protected void traversePreOrder(AbstractSyntaxNode node, NodeOperation op,
                                  Object... objects){}

}
