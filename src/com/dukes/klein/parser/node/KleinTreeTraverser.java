package com.dukes.klein.parser.node;

import com.dukes.klein.semanticchecker.KleinFunctionTable;
import com.dukes.lang.parser.node.AbstractSyntaxNode;
import com.dukes.lang.parser.node.AbstractTreeTraverser;
import com.dukes.lang.parser.node.NullNode;
import com.dukes.lang.semanticchecker.SemanticException;

/**
 * @author Daniel J. Holland
 * @version 1.0
 */
public final class KleinTreeTraverser extends AbstractTreeTraverser {

  public KleinTreeTraverser(AbstractSyntaxNode top) {
    super(top);
  }

  @Override
  public void semanticCheck() {
    // Create functionParamTable of function names/return types, parameters names/types
    KleinFunctionTable functionTable = new KleinFunctionTable(this.top);
    // Check for the presence of function "main"
    if(!functionTable.getFunctionNames().contains("main")) {
      System.out.println(
          "Semantic Error: Function 'main' not found in program.");
    }
    // Verify that there are no user defined functions named "print"
    if(functionTable.getFunctionNames().contains("print")) {
      System.out.println(
          "Semantic Error: User defined function named 'print' not allowed!");
    }
    // Traverse AST and set and verify types for each node
    this.traversePreOrder(this.top, new TypeCheck(functionTable));
    // Semantic Warning: Check for uncalled functions
    for(String functionName : functionTable.getUncalledFunctions()) {
      System.out.println(
          "Semantic Warning: The function '" + functionName +
              "' is never called.");
    }
  }

  private class TypeCheck implements NodeOperation {

    KleinFunctionTable table;

    public TypeCheck(Object table) {
      this.table = (KleinFunctionTable) table;
    }

    @Override
    public void execute(Object... objects) {
      AbstractSyntaxNode node = (AbstractSyntaxNode) objects[0];
      String functionName = (String) objects[1];
      // If the Declared node is an Identifier
      if(node instanceof DeclaredNode &&
          node.getType() == AbstractSyntaxNode.IDENTIFIER_TYPE) {
        // Check to see that the identifier is defined in
        //  the functions parameter set.
        if(this.table.getFunctionParameterNames(functionName).contains(
            ((DeclaredNode) node).getDeclared())) {
          node.setType(this.table.getParameterType(
              functionName, ((DeclaredNode) node).getDeclared()));
        }
        else {
          // Otherwise the Identifier was not found and is undefined.
          throw new SemanticException(
              "Identifier '" + ((DeclaredNode) node).getDeclared() +
                  "' not defined as a parameter in function '" +
                  functionName + "'."
          );
        }
      }
      else if(node instanceof OperatorNode) {
        // Check to see that Operators children match types.
        if(((OperatorNode) node).isUnary()) {
          if(!(node.getChildren()[1].getType() == node.getType())) {
            throw new SemanticException(
                "Type Mismatch for unary operator '" +
                    ((OperatorNode) node).getOperator() + "' in '" +
                    functionName + "', expected '" +
                    node.typeToString() + "' but got '" +
                    node.getChildren()[1].typeToString() + "'."
            );
          }
        }
        else { // Or if it is a binary operator
          if(!((node.getChildren()[0].getType() &
              node.getChildren()[1].getType()) ==
              node.getType())) {
            throw new SemanticException(
                "Type Mismatch for operator '" +
                    ((OperatorNode) node).getOperator() + "' in '" +
                    functionName + "', expected '" +
                    node.typeToString() + "' but got '" +
                    node.getChildren()[0].typeToString() + "', and '" +
                    node.getChildren()[1].typeToString() + "'."
            );
          }
          // Everything good we can set her to boolean
          if(((OperatorNode) node).getOperator().equals("=")) {
            node.setType(AbstractSyntaxNode.BOOLEAN_TYPE);
          }
        }
      }
      else if(node instanceof CallNode) {
        try {
          // Setting the type equal to the return type of the called func.
          node.setType(
              this.table.getFunctionReturnType(
                  ((CallNode) node).getIdentifier()));
          // Add this function call to this functions call list
          this.table.addFunctionCall(
              functionName, ((CallNode) node).getIdentifier());
        } catch(SemanticException e) {
          throw new SemanticException(
              "Undefined function '" + ((CallNode) node).getIdentifier() +
                  "' called in function '" + functionName + "'."
          );
        }
        // Verify that the correct number of parameters are in the call
        if(node.getChildren().length !=
            this.table.getFunctionParameterNames(
                ((CallNode) node).getIdentifier()).size()) {
          throw new SemanticException(
              "Invalid number of parameters in call to '" +
                  ((CallNode) node).getIdentifier() +
                  "' in function '" + functionName + "'."
          );
        }
        // If call has the correct number of parameters then verify their types
        else {
          for(int i = 0; i < node.getChildren().length; i++) {
            String pNameActual = this.table.getFunctionParameterNames(
                ((CallNode) node).getIdentifier()).get(i);
            int pTypeActual = this.table.getFunctionParameterTypes(
                ((CallNode) node).getIdentifier()).get(i);
            int pType = node.getChildren()[i].getType();
            // If the parameter type in the call doesn't match the function
            //  declaration throw and error
            if(pType != pTypeActual) {
              throw new SemanticException(
                  "For call to function '" +
                      ((CallNode) node).getIdentifier() + "' in function '" +
                      functionName + "' expected type '" +
                      AbstractSyntaxNode.typeToString(pTypeActual) +
                      "' for parameter '" + pNameActual +
                      "' but got type '" +
                      AbstractSyntaxNode.typeToString(pType) + "'."
              );
            }
          }
        }
      }
      else if(node instanceof IfNode) {
        node.setType(
            node.getChildren()[1].getType() | node.getChildren()[2].getType());
      }
      else if(node instanceof PrintNode) {
        // Verify that the arg for print is either of type bool or int.
        if((node.getType() | node.getChildren()[0].getType()) !=
            AbstractSyntaxNode.BOOL_OR_INT_TYPE) {
          throw new SemanticException("For call to print in function '" +
              functionName + "', arg is not of type Boolean or Integer");
        }
      }
      else if(node instanceof BodyNode ||
          node instanceof ParameterizedExpressionNode) {
        node.setType(
            node.getChildren()[node.getChildren().length - 1].getType());
      }

      // Check body node type against function return type
      if(node instanceof BodyNode &&
          node.getType() != this.table.getFunctionReturnType(functionName)) {
        throw new SemanticException("Expected return type '" +
            AbstractSyntaxNode.typeToString(
                this.table.getFunctionReturnType(functionName)) +
            "' for function '" + functionName +
            "' but got type '" + node.typeToString() + "'."
        );
      }
    }
  }

  @Override
  protected void traversePreOrder(AbstractSyntaxNode node, NodeOperation op,
                                  Object... objects) {
    String functionName = "";
    if(objects.length > 0) {
      functionName = (String) objects[0];
    }
    if(node instanceof NullNode) {
      return;
    }
    if(node instanceof FunctionNode) {
      functionName = ((FunctionNode) node).getName().getValue();
    }
    for(AbstractSyntaxNode child : node.getChildren()) {
      this.traversePreOrder(child, op, functionName);
    }
    // Catching semantic errors here...
    //  need to decide how to package them for display
    try {
      op.execute(node, functionName);
    } catch(Exception err) {
      if(err instanceof SemanticException) {
        System.out.println("Semantic Error: " + err.getMessage());
      }
      else {
        err.printStackTrace();
      }
    }
  }
}