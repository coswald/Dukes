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

  public void semanticCheck(){}

}
