package com.dukes.klein.parser.node;

import com.dukes.klein.semanticchecker.KleinFunctionTable;
import com.dukes.lang.parser.node.AbstractSyntaxNode;
import com.dukes.lang.parser.node.AbstractTreeTraverser;
import com.dukes.lang.parser.node.NullNode;
import com.dukes.lang.semanticchecker.SemanticException;

import java.util.ArrayList;

/**
 * @author Daniel J. Holland
 * @version 1.0
 */
public final class KleinTreeTraverser extends AbstractTreeTraverser {
  private KleinFunctionTable functionTable;
  
  public KleinTreeTraverser(AbstractSyntaxNode top) {
    super(top);
    this.functionTable = new KleinFunctionTable(this.top);
  }
  
  public KleinFunctionTable getFunctionTable() {
    return this.functionTable;
  }
  
  @Override
  public void semanticCheck() {
    ArrayList<SemanticException> semanticExceptions =
        new ArrayList<SemanticException>();
    // Check for the presence of function "main"
    if(!functionTable.getFunctionNames().contains("main")) {
      semanticExceptions.add(new SemanticException(
          "Function 'main' not found in program.")
      );
    }
    // Parser verifies that there are no user defined functions named "print"

    // Traverse AST and set and verify types for each node
    this.traversePreOrder(this.top, new TypeCheck(functionTable),
        semanticExceptions);
    // Semantic Warning: Check for uncalled functions
    for(String functionName : functionTable.getUncalledFunctions()) {
      System.out.println(String.format(
          "SemanticWarning: The function '%s' is never called.", functionName)
      );
    }
    // Throwing captured Semantic Errors.. This is ugly
    String errMsg = "";
    for(SemanticException error : semanticExceptions) {
      errMsg += "SemanticError: " + error.getMessage() + "\n";
    }
    if(!errMsg.equals("")) {
      throw new SemanticException(errMsg.replaceFirst("SemanticError: ", ""));
    }
  }

  private class TypeCheck implements NodeOperation {

    KleinFunctionTable table;
    String functionName;

    public TypeCheck(KleinFunctionTable table) {
      this.table = table;
      this.functionName = "";
    }

    @Override
    public void traversed(AbstractSyntaxNode node, Object... objects) {
      if(node instanceof FunctionNode) {
        this.functionName = ((FunctionNode) node).getName().getValue();
      }
    }

    @SuppressWarnings("unchecked")
    @Override
    public void execute(AbstractSyntaxNode node, Object... objects) {
      if(node instanceof NullNode) {
        return;
      }
      ArrayList<SemanticException> semanticExceptions =
          (ArrayList<SemanticException>) objects[0];
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
          semanticExceptions.add(new SemanticException(String.format(
              "Identifier '%s' not defined as a parameter in function '%s'.",
              ((DeclaredNode) node).getDeclared(), functionName)
              )
          );
        }
      }
      else if(node instanceof OperatorNode) {
        // Check to see that Operators children match types.
        if(((OperatorNode) node).isUnary()) {
          if(!(node.getChildren()[1].getType() == node.getType())) {
            semanticExceptions.add(new SemanticException(String.format(
                "Type Mismatch for unary operator '%s' in '%s', " +
                    "expected '%s' but got '%s'.",
                ((OperatorNode) node).getOperator(), functionName,
                node.typeToString(), node.getChildren()[1].typeToString()))
            );
          }
        }
        else { // Or if it is a binary operator
          if((node.getChildren()[0].getType() &
              node.getChildren()[1].getType()) != node.getType()) {
            semanticExceptions.add(new SemanticException(String.format(
                "Type Mismatch for operator '%s' in '%s', " +
                    "expected '%s' but got '%s' and '%s'.",
                ((OperatorNode) node).getOperator(), functionName,
                node.typeToString(), node.getChildren()[0].typeToString(),
                node.getChildren()[1].typeToString()))
            );
          }
          // If the child types are correct then we need to set special cases
          //  to boolean.
          else if(((OperatorNode) node).getOperator().equals("=") ||
              ((OperatorNode) node).getOperator().equals("<")) {
            node.setType(AbstractSyntaxNode.BOOLEAN_TYPE);
          }
        }
      }
      else if(node instanceof CallNode) {
        // Set the type equal to the return type of the called function
        if(this.table.containsFunction(((CallNode) node).getIdentifier())) {
          node.setType(this.table.getFunctionReturnType(
              ((CallNode) node).getIdentifier()));
          // Add called function name to this functions call list
          this.table.addFunctionCall(functionName,
              ((CallNode) node).getIdentifier());
          // Verify that the correct number of parameters are in the call
          if(node.getChildren().length != this.table.getFunctionParameterNames(
              ((CallNode) node).getIdentifier()).size()) {
            semanticExceptions.add(new SemanticException(String.format(
                "Invalid number of parameters in call to '%s' " +
                    "in function '%s'.",
                ((CallNode) node).getIdentifier(), functionName)
                )
            );
          }
          // Else the call has the correct number of parameters
          //  so verify their types
          else {
            String semanticErrors = "";
            for(int i = 0; i < node.getChildren().length; i++) {
              String pNameActual = this.table.getFunctionParameterNames(
                  ((CallNode) node).getIdentifier()).get(i);
              int pTypeActual = this.table.getFunctionParameterTypes(
                  ((CallNode) node).getIdentifier()).get(i);
              int pType = node.getChildren()[i].getType();
              // Verify that the parameter type in the call matches that of
              //   the corresponding parameter in the function declaration
              if(pType != pTypeActual) {
                semanticErrors += (String.format(
                    "\texpected type '%s' for parameter '%s' " +
                        "but got type '%s'.\n",
                    AbstractSyntaxNode.typeToString(pTypeActual), pNameActual,
                    AbstractSyntaxNode.typeToString(pType))
                );
              }
            }
            // If any semantic errors were caught add them to the list.
            if(!(semanticErrors.equals(""))) {
              semanticExceptions.add(new SemanticException(String.format(
                  "For call to function '%s' in function '%s':\n%s",
                  ((CallNode) node).getIdentifier(), functionName,
                  semanticErrors.substring(0, semanticErrors.length() - 2)
                  ))
              );
            }
          }
        }
        // Otherwise the function being called is not defined
        else {
          semanticExceptions.add(new SemanticException(String.format(
              "Undefined function '%s' called in function '%s'.",
              ((CallNode) node).getIdentifier(), functionName)
              )
          );
        }
      }
      else if(node instanceof IfNode) {
        node.setType(
            node.getChildren()[1].getType() | node.getChildren()[2].getType());
      }
      else if(node instanceof PrintNode) {
        // Verify that the arg for print is either of type boolean or integer.
        if((node.getType() | node.getChildren()[0].getType()) !=
            AbstractSyntaxNode.BOOL_OR_INT_TYPE) {
          semanticExceptions.add(new SemanticException(String.format(
              "For call to print in function '%s', " +
                  "arg is not of type Boolean or Integer", functionName))
          );
        }
      }
      else if(node instanceof BodyNode ||
          node instanceof ParameterizedExpressionNode) {
        node.setType(
            node.getChildren()[node.getChildren().length - 1].getType());
        // Check body node type against function return type
        if(node instanceof BodyNode &&
            node.getType() != this.table.getFunctionReturnType(functionName)) {
          semanticExceptions.add(new SemanticException(String.format(
              "Expected return type '%s' for function '%s' but got type '%s'.",
              AbstractSyntaxNode.typeToString(
                  this.table.getFunctionReturnType(functionName)),
              functionName, node.typeToString())
              )
          );
        }
      }
    }
  }
}
