package com.dukes.klein.parser.node;

/**
 * @author Daniel J. Holland
 * @version 1.0
 */
public class TerminalNode extends ExpressionNode {

  private String value;

  public TerminalNode(String value) {
    super();
    this.value = value;
  }

  public String getValue(){
    return this.value;
  }

}
