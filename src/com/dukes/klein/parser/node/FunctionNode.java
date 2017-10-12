package com.dukes.klein.parser.node;

import com.dukes.klein.parser.node.AbstractSyntaxNode;
import com.dukes.klein.parser.node.BodyNode;
import com.dukes.klein.parser.node.FormalNode;

/**
 *
 * @author Coved W Oswald
 * @version 1.0
 * @since 0.2.0
 */
public class FunctionNode extends AbstractSyntaxNode {
  private String name;
  
  public FunctionNode(String name, BodyNode bodyNode, FormalNode... formals) {
    super(AbstractSyntaxNode.concat(bodyNode, formals));
    this.name = name;
  }
  
  public String getName() {
    return this.name;
  }
  
  @Override
  public String toString() {
    String s = "Function " + this.name + ": ";
    s += super.toString();
    return s;
  }
}