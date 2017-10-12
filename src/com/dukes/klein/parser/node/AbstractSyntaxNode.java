package com.dukes.klein.parser.node;

import java.lang.Object;
import java.lang.System;

/**
 *
 * @author Coved W Oswald
 * @version 1.0
 * @since 0.2.0
 */
public abstract class AbstractSyntaxNode extends Object {
  protected AbstractSyntaxNode[] children;
  
  protected AbstractSyntaxNode(AbstractSyntaxNode... children) {
    this.children = children;
  }

  public AbstractSyntaxNode[] getChildren() {
    return this.children;
  }
  
  @Override
  public String toString()
  {
    String s = "";
    for(int i = 0; i < this.children.length; i++) {
      s += (children.get(i)).toString();
    }
    return s;
  }

  public static AbstractSyntaxNode[] concat(AbstractSyntaxNode frontNode,
                                            AbstractSyntaxNode... end) {
    AbstractSyntaxNode[] ret = new AbstractSyntaxNode[end.length + 1];
    ret[0] = frontNode;
    System.arrayCopy(end, 0, ret, 1, end.length);
    return ret;
  }
}
