/*
 * Copyright (C) 2017 Coved W Oswald, Daniel Holland, and Patrick Sedlacek
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along
 * with this program; if not, write to the Free Software Foundation, Inc.,
 * 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.
 */
package com.dukes.klein.parser.node;

import com.dukes.lang.parser.node.AbstractSyntaxNode;
import com.dukes.lang.parser.node.ExpressionNode;
import com.dukes.lang.parser.node.NullNode;
import com.dukes.klein.parser.node.DeclaredNode;
import com.dukes.klein.generator.KleinCodeGenerator;

/**
 * This class describes an operation in Klein. This can be either a binary or
 * unary operation. If the left side is a {@code NullNode}, then the expression
 * is a unary operation; otherwise, it is a binary expression. No {@code null}
 * values should be added to the operator node. The operation should be a valid
 * Klein operation; however, this is not checked within this class.
 * @author Coved W Oswald
 * @version 1.0
 * @since 0.3.0
 */
public class OperatorNode extends ExpressionNode {

  private TerminalNode operator;
  private Boolean isUnary;
  private String returnRegister;
  /**
   * Constructs an operation withe the given operator, left hand side, and
   * right hand side. Note that this operation is unary if the left side is a
   * {@code NullNode}.
   * @param operator The operator of the operation.
   * @param leftNode The left hand side.
   * @param rightNode The right hand side.
   */
  public OperatorNode(TerminalNode operator, ExpressionNode leftNode,
                      ExpressionNode rightNode) {
    super(rightNode, leftNode);
    // Assign expectedType
    switch(operator.getValue()) {
      case "+":
      case "-":
      case "<":
      case "=":
      case "*":
      case "/":
        this.type = AbstractSyntaxNode.INTEGER_TYPE;
        break;
      case "and":
      case "or":
        this.type = AbstractSyntaxNode.BOOLEAN_TYPE;
        break;
      default:
        this.type = 0; //ERROR
  }
    this.operator = operator;
    this.isUnary = false;
    this.returnRegister = KleinCodeGenerator.getMemoryHolder();
  }
  
  /**
   * Constructs a unary operation.
   * @param operator The operator the the unary operation.
   * @param exprNode The expression node to apply the operation to.
   */
  public OperatorNode(TerminalNode operator, ExpressionNode exprNode) {
    this(operator, new NullNode(), exprNode);
    // Assign expectedType
    switch(operator.getValue()) {
      case "-":
        this.type = AbstractSyntaxNode.INTEGER_TYPE;
        break;
      case "not":
        this.type = AbstractSyntaxNode.BOOLEAN_TYPE;
        break;
      default:
        this.type = 0; //ERROR
    }
    this.isUnary = true;
  }
  
  /**
   * Returns the operator of this operation.
   * @return The string value of the operator.
   */
  public String getOperator() {
    return this.operator.getValue();
  }
  
  /**
   * Returns the operator separated and type by brackets.
   * @return The operator separated and type by brackets.
   */
  @Override
  public String dataAsString() {
    return "[Operator: " + this.getOperator() + "" +
        ", Type: " + this.typeToString() + "]";
  }

  public Boolean isUnary(){
    return this.isUnary;
  }
  
  @Override
  public String toTargetCode() {
    String s = "*\n* Operator " + this.getOperator() + "\n*\n";
    //String a = KleinCodeGenerator.getPlaceHolder(); //lefthand-side
    //String b = KleinCodeGenerator.getPlaceHolder(); //righthand-side
    String a = this.children[0].getReturnRegister(); //if not declaredNode, then it is a memory location.
    String b = this.children[1].getReturnRegister();
    
    if(!(this.children[0] instanceof DeclaredNode) && !(this.children[0] instanceof NullNode)) {
      String temp = a;
      a = KleinCodeGenerator.getPlaceHolder();
      s += KleinCodeGenerator.emitCode("LD", a, temp, "0");
    }
    else if(this.children[0] instanceof NullNode) {
      a = b;
    }
    if(!(this.children[1] instanceof DeclaredNode)) {
      String temp = b;
      b = KleinCodeGenerator.getPlaceHolder();
      s += KleinCodeGenerator.emitCode("LD", b, temp, "0");
    }
    
    String c = KleinCodeGenerator.getPlaceHolder(); //temps
    String d = KleinCodeGenerator.getPlaceHolder();
    
    if(this.isUnary) {
      switch(this.operator.getValue()) {
        case "-":
          s += KleinCodeGenerator.emitCode("SUB", b, "0", b);
          break;
        case "not":
          s += KleinCodeGenerator.emitCode("LDC", c, "-1", "0");
          s += KleinCodeGenerator.emitCode("ADD", b, b, c);
          s += KleinCodeGenerator.emitCode("MUL", b, b, c);
          break;
      }
    }
    else {
      switch(this.operator.getValue()) {
        case "+":
          s += KleinCodeGenerator.emitCode("ADD", a, b, a);
          break;
        case "-":
          s += KleinCodeGenerator.emitCode("SUB", a, b, a);
          break;
        case "<":
          s += KleinCodeGenerator.emitCode("SUB", a, b, a);
          s += KleinCodeGenerator.emitCode("LDC", c, "1", "0");
          s += KleinCodeGenerator.emitCode("JLT", a, "1", "7");
          s += KleinCodeGenerator.emitCode("LDC", c, "0", "0");
          s += KleinCodeGenerator.emitCode("ADD", a, "0", c);
          break;
        case "=":
          s += KleinCodeGenerator.emitCode("SUB", a, b, a);
          s += KleinCodeGenerator.emitCode("JNE", a, "2", "7");
          s += KleinCodeGenerator.emitCode("LDC", c, "1", "0");
          s += KleinCodeGenerator.emitCode("ADD", a, a, c);
          //final value in A
          break;
        case "*":
          s += KleinCodeGenerator.emitCode("MUL", a, b, a);
          break;
        case "/":
          s += KleinCodeGenerator.emitCode("DIV", a, b, a);
          break;
        case "and":
          s += KleinCodeGenerator.emitCode("MUL", a, b, a);
          break;
        case "or":
          s += KleinCodeGenerator.emitCode("ADD", a, b, a);
          s += KleinCodeGenerator.emitCode("JEQ", a, "1", "7");
          s += KleinCodeGenerator.emitCode("DIV", a, a, a);
          break;
        default:
          s = "BLAH"; //ERROR
      }
    }
    s += KleinCodeGenerator.emitCode("ST", a, this.returnRegister, "0");
    s += "* END Operator " + this.getOperator() + "\n";
    return s;
  }
  
  @Override
  public String getReturnRegister() {
    return this.returnRegister;
  }
}
