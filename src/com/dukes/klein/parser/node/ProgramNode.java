package com.dukes.klein.parser.node;

import com.dukes.klein.parser.node.AbstractSyntaxNode;
import com.dukes.klein.parser.node.FunctionNode;

/**
 *
 * @author Coved W Oswald
 * @version 1.0
 * @since 0.2.0
 */
public class ProgramNode extends AbstractSyntaxNode {
  public ProgramNode(FunctionNode... functions) {
    super(functions);
  }
}