package com.dukes.klein.parser.node;

import java.lang.Object;
import java.lang.System;

/**
 * <p>Represents an abstract syntax tree. This class is extended to allow the
 * use of other programmers to make their own syntax.</p>
 * @author Coved W Oswald
 * @version 1.0
 * @since 0.2.0
 */
public abstract class AbstractSyntaxNode extends Object {
  
  /**
   *
   */
  protected AbstractSyntaxNode[] children;
  
  protected AbstractSyntaxNode(AbstractSyntaxNode... children) {
    this.children = new AbstractSyntaxNode[children.length];
    for(int i = children.length - 1, j = 0; i >= 0; i--, j++) {
      this.children[j] = children[i];
    }
  }

  public AbstractSyntaxNode[] getChildren() {
    return this.children;
  }
  
  public String dataAsString()
  {
    return "";
  }
  
  @Override
  public String toString()
  {
    String s = "";
    for(int i = 0; i < this.children.length; i++) {
      s += (children[i]).toString() + " ";
    }
    return s;
  }

  public static AbstractSyntaxNode[] concat(AbstractSyntaxNode frontNode,
                                            AbstractSyntaxNode... end) {
    AbstractSyntaxNode[] ret = new AbstractSyntaxNode[end.length + 1];
    ret[0] = frontNode;
    System.arraycopy(end, 0, ret, 1, end.length);
    return ret;
  }
}
