package com.dukes.klein.parser.node;

import com.dukes.klein.parser.node.AbstractSyntaxNode;
import com.dukes.klein.parser.node.NullNode;

/**
 *
 * @author Coved W Oswald
 * @version 1.0
 * @since 0.2.0
 */
public class FormalNode extends AbstractSyntaxNode {
  private TerminalNode identifier;
  private TerminalNode type;
  
  public FormalNode(TerminalNode identifier, TerminalNode type) {
    super();
    this.identifier = identifier;
    this.type = type;
  }
  
  public String getIdentifier() {
    return this.identifier.getValue();
  }
  
  public String getType() {
    return this.type.getValue();
  }
  
  @Override
  public String dataAsString() {
    return "[Identifier: " + this.identifier.toString() +
        ", Type: " + this.type.toString() + "]";
  }
  
  @Override
  public String toString() {
    String s = "Formal: " + this.identifier + " : " + this.type;
    return s;
  }
}