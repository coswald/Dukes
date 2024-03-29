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

import com.dukes.klein.scanner.KleinScanner;
import com.dukes.lang.parser.node.AbstractSyntaxNode;
import com.dukes.lang.parser.node.ExpressionNode;
import com.dukes.lang.parser.node.NullNode;

import java.util.ArrayList;
import java.util.Stack;

/**
 * Describes actions to be taken on a semantic stack from a table-driven
 * parser. 
 * @author Daniel J. Holland
 * @version 1.0
 * @since 0.3.0
 */
public enum SemanticActionType {
  MAKE_PROGRAM(0),
  MAKE_FUNCTION(1),
  MAKE_BODY(2),
  MAKE_FORMAL(3),
  MAKE_PRINT(4),
  MAKE_DECLARED(5),
  MAKE_IF(6),
  MAKE_CALL(7),
  MAKE_PARAMETERIZED(8),
  MAKE_OPERATOR(9),
  MAKE_UNARYOPERATOR(10);

  private final int id;

  SemanticActionType(int id) {
    this.id = id;
  }

  public int getId() {
    return id;
  }

  public AbstractSyntaxNode run(Stack<AbstractSyntaxNode> semanticStack) {
    switch(this) {
      case MAKE_PROGRAM:
        return SemanticActionType.makeProgram(semanticStack);
      case MAKE_FUNCTION:
        return SemanticActionType.makeFunction(semanticStack);
      case MAKE_BODY:
        return SemanticActionType.makeBody(semanticStack);
      case MAKE_FORMAL:
        return SemanticActionType.makeFormal(semanticStack);
      case MAKE_PRINT:
        return SemanticActionType.makePrint(semanticStack);
      case MAKE_DECLARED:
        return SemanticActionType.makeDeclared(semanticStack);
      case MAKE_IF:
        return SemanticActionType.makeIF(semanticStack);
      case MAKE_CALL:
        return SemanticActionType.makeCall(semanticStack);
      case MAKE_PARAMETERIZED:
        return SemanticActionType.makeParameterized(semanticStack);
      case MAKE_OPERATOR:
        return SemanticActionType.makeOperator(semanticStack);
      case MAKE_UNARYOPERATOR:
        return SemanticActionType.makeUnaryoperator(semanticStack);
      default:
        return new NullNode();
    }
  }

  private static FunctionNode[] helpFunctionNode(Object[] nodes) {
    FunctionNode[] ret = new FunctionNode[nodes.length];
    for(int i = 0; i < nodes.length; i++) {
      ret[i] = (FunctionNode) nodes[i];
    }
    return ret;
  }

  private static FormalNode[] helpFormalNode(Object[] nodes) {
    FormalNode[] ret = new FormalNode[nodes.length];
    for(int i = 0; i < nodes.length; i++) {
      ret[i] = (FormalNode) nodes[i];
    }
    return ret;
  }

  private static PrintNode[] helpPrintNode(Object[] nodes) {
    PrintNode[] ret = new PrintNode[nodes.length];
    for(int i = 0; i < nodes.length; i++) {
      ret[i] = (PrintNode) nodes[i];
    }
    return ret;
  }

  private static ExpressionNode[] helpExpressionNode(Object[] nodes) {
    ExpressionNode[] ret = new ExpressionNode[nodes.length];
    for(int i = 0; i < nodes.length; i++) {
      ret[i] = (ExpressionNode) nodes[i];
    }
    return ret;
  }


  private static ProgramNode makeProgram(
      Stack<AbstractSyntaxNode> semanticStack) {
    ArrayList<AbstractSyntaxNode> nodes = new ArrayList<AbstractSyntaxNode>();

    while(!semanticStack.empty() &&
        semanticStack.peek() instanceof FunctionNode) {
      nodes.add(semanticStack.pop());
    }

    return new ProgramNode(
        SemanticActionType.helpFunctionNode(nodes.toArray()));
  }

  private static FunctionNode makeFunction(
      Stack<AbstractSyntaxNode> semanticStack) {
    ArrayList<AbstractSyntaxNode> formals = new ArrayList<AbstractSyntaxNode>();
    BodyNode bn = (BodyNode) semanticStack.pop();
    TerminalNode type = (TerminalNode) semanticStack.pop();
    TerminalNode id;

    while(!semanticStack.empty() &&
        semanticStack.peek() instanceof FormalNode) {
      formals.add(semanticStack.pop());
    }
    id = (TerminalNode) semanticStack.pop();

    return new FunctionNode(id, type, bn, helpFormalNode(formals.toArray()));
  }

  private static BodyNode makeBody(Stack<AbstractSyntaxNode> semanticStack) {
    ArrayList<PrintNode> pn = new ArrayList<PrintNode>();
    ExpressionNode en = (ExpressionNode) semanticStack.pop();

    while(!semanticStack.empty() && semanticStack.peek() instanceof PrintNode) {
      pn.add((PrintNode) semanticStack.pop());
    }

    return new BodyNode(en, helpPrintNode(pn.toArray()));
  }

  private static FormalNode makeFormal(Stack<AbstractSyntaxNode> semanticStack) {
    TerminalNode type = (TerminalNode) semanticStack.pop();
    TerminalNode id = (TerminalNode) semanticStack.pop();

    return new FormalNode(id, type);
  }

  private static PrintNode makePrint(Stack<AbstractSyntaxNode> semanticStack) {
    ExpressionNode en = (ExpressionNode) semanticStack.pop();
    //semanticStack.pop();
    return new PrintNode(en);
  }

  private static DeclaredNode makeDeclared(
      Stack<AbstractSyntaxNode> semanticStack) {
    TerminalNode tn = (TerminalNode) semanticStack.pop();
    int type;

    if(KleinScanner.isDigit(tn.getValue().charAt(0))) {
      type = AbstractSyntaxNode.INTEGER_TYPE;
    }
    else if(tn.getValue().equals("true") || tn.getValue().equals("false")) {
      type = AbstractSyntaxNode.BOOLEAN_TYPE;
    }
    else {
      type = AbstractSyntaxNode.IDENTIFIER_TYPE;
    }

    return new DeclaredNode(tn, type);
  }

  private static IfNode makeIF(Stack<AbstractSyntaxNode> semanticStack) {
    ExpressionNode testExp = (ExpressionNode) semanticStack.pop();
    ExpressionNode ifExp = (ExpressionNode) semanticStack.pop();
    ExpressionNode elseExp = (ExpressionNode) semanticStack.pop();

    return new IfNode(testExp, ifExp, elseExp);
  }

  private static CallNode makeCall(Stack<AbstractSyntaxNode> semanticStack) {
    ArrayList<ExpressionNode> nodes = new ArrayList<ExpressionNode>();

    while(!semanticStack.empty() &&
        !(semanticStack.peek() instanceof TerminalNode)) {
      nodes.add((ExpressionNode) semanticStack.pop());
    }
    TerminalNode id = (TerminalNode) semanticStack.pop();

    return new CallNode(id, helpExpressionNode(nodes.toArray()));
  }

  private static ParameterizedExpressionNode makeParameterized(
      Stack<AbstractSyntaxNode> semanticStack) {

    return new ParameterizedExpressionNode(
        (ExpressionNode) semanticStack.pop());
  }

  private static OperatorNode makeOperator(
      Stack<AbstractSyntaxNode> semanticStack) {
    ExpressionNode rs = (ExpressionNode) semanticStack.pop();
    TerminalNode op = (TerminalNode) semanticStack.pop();
    ExpressionNode ls = (ExpressionNode) semanticStack.pop();

    return new OperatorNode(op, ls, rs);
  }

  private static OperatorNode makeUnaryoperator(
      Stack<AbstractSyntaxNode> semanticStack) {
    ExpressionNode rs = (ExpressionNode) semanticStack.pop();
    TerminalNode op = (TerminalNode) semanticStack.pop();
    
    return new OperatorNode(op, rs);
  }
}
