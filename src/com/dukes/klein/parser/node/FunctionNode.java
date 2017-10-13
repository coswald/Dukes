package com.dukes.klein.parser.node;

import com.dukes.klein.parser.TerminalType;
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
  private TerminalNode name;
  private TerminalNode returnType;

  public FunctionNode(TerminalNode name, TerminalNode returnType,
                      BodyNode bodyNode, FormalNode... formals) {
    super(AbstractSyntaxNode.concat(bodyNode, formals));
    this.name = name;
    this.returnType = returnType;
  }
  
  public TerminalNode getName() {
    return this.name;
  }

  public String getReturnType(){return this.returnType.getValue();}

  @Override
  public String toString() {
    String s = "Function " + this.name + ": ";
    s += super.toString();
    return s;
  }
}