package com.dukes.klein.parser.node;

import com.dukes.lang.parser.node.AbstractSyntaxNode;

/**
 * @author Coved W Oswald
 * @version 1.0
 * @since 0.2.0
 */
public class ProgramNode extends AbstractSyntaxNode {
  public ProgramNode(FunctionNode... functions) {
    super(functions);
  }

  @Override
  public String toString() {
    return "Program: " + super.toString();
  }
}