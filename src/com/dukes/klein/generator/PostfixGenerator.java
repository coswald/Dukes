package com.dukes.klein.generator;

import com.dukes.lang.generator.CodeGenerator;
import com.dukes.lang.parser.node.AbstractSyntaxNode;
import com.dukes.klein.parser.node.*;

public final class PostfixGenerator
    implements CodeGenerator<AbstractSyntaxNode, String>
{
  @Override
  public String generateCode(AbstractSyntaxNode ast) {
    return generatorHelper(ast);
  }
    
  private String generatorHelper(AbstractSyntaxNode ast) {
    if(ast.getChildren().length <= 0) {
      return this.getPostFix(ast);
    }
    else if(ast instanceof BodyNode) {
      String s = "";
      for(AbstractSyntaxNode c : ast.getChildren()) {
        s = " " + generatorHelper(c) + s;
      }
      return (s + " ").trim().replaceAll("[(] ", "(");
    }
    else {
      AbstractSyntaxNode[] children = ast.getChildren();
      String s = "";
      for(AbstractSyntaxNode c : children) {
        s = " " + generatorHelper(c) + s;
      }
      return
          ("(" + s + " " + this.getPostFix(ast) + ")").trim().replaceAll("[(] ", "(");
    }
  }
    
  private String getPostFix(AbstractSyntaxNode ast) {
    if(ast instanceof DeclaredNode) {
      return ((DeclaredNode)ast).getDeclared();
    }
    else if(ast instanceof FunctionNode) {
      return ((FunctionNode)ast).getName().dataAsString() + " def";
    }
    else if(ast instanceof FormalNode) {
      return ((FormalNode)ast).getIdentifier();
    }
    else if(ast instanceof CallNode) {
      return ((CallNode)ast).getIdentifier() + " call";
    }
    else if(ast instanceof IfNode) {
      return "if";
    }
    else if(ast instanceof OperatorNode) {
      return ((OperatorNode)ast).getOperator();
    }
    /*
    else if(ast instanceof ParameterizedExpressionNode) {
      return this.getPostFix(ast.getChildren()[0]);
    }
     */
    else if(ast instanceof PrintNode) {
      return "print";
    }
    else if(ast instanceof ProgramNode) {
      return "program";
    }
    else {
      return "";
    }
  }
}
