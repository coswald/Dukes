package com.dukes.klein.parser.node;

import com.dukes.klein.parser.node.ExpressionNode;
import com.dukes.klein.parser.node.NullNode;

import java.lang.IllegalArgumentException;

/**
 *
 * @author Coved W Oswald
 * @version 1.0
 * @since 0.2.0
 */
public class DeclaredNode extends ExpressionNode {
  public static final int IDENTIFIER_TYPE = 0;
  public static final int BOOLEAN_TYPE = 1;
  public static final int INTEGER_TYPE = 2;
  
  private TerminalNode declared;
  private int type;
  
  public DeclaredNode(TerminalNode declared, int type)
                      throws IllegalArgumentException {
    super(new NullNode());
    
    if(type > 2) {
      throw new IllegalArgumentException("Invalid type given to declared " +
                                         "Node!");
    }
    
    this.declared = declared;
    this.type = type;
  }
  
  public String getDeclared() {
    return this.declared.getValue();
  }
  
  public boolean isType(int type) throws IllegalArgumentException {
    if(type > 2) {
      throw new IllegalArgumentException("Invalid type asked of declared " +
                                         "node!");
    }
      
    return this.type == type;
  }

  @Override
  public String toString() {
    return this.declared.toString();
  }

}