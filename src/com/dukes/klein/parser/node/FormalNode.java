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
  private String identifier;
  private String type;
  
  public FormalNode(String identifier, String type) {
    super(new NullNode());
    this.identifier = identifier;
    this.type = type;
  }
  
  public String getIdentifier() {
    return this.identifier;
  }
  
  public String getType() {
    return this.type;
  }
  
  @Override
  public String toString() {
    String s = "Formal: " + this.name + " : " + this.type;
    return s;
  }
}