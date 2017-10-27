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
    // Create table of funtion names/return types, parameters names/types
    KleinFunctionTable functionTable = new KleinFunctionTable(this.top);
    // Traverse AST and determine and check types for each node
    this.traversePreOrder(this.top, new TypeCheck(functionTable), "");
  }

  private interface NodeOperation {
    public void execute(Object... objects);
  }

  private class TypeCheck implements NodeOperation{

    KleinFunctionTable table;

    public TypeCheck(Object table){
      this.table = (KleinFunctionTable) table;
    }

    @Override
    public void execute(Object... objects){
      AbstractSyntaxNode node = (AbstractSyntaxNode) objects[0];
      String functionName = (String) objects[1];
      // If the Declared node is an Identifier
      if (node instanceof DeclaredNode &&
          ((DeclaredNode) node).getType() == AbstractSyntaxNode.IDENTIFIER_TYPE)
      {
        // Check to see that the identifier is defined in
        //  the function parameter set.
        for (String parameterName :
            this.table.getFunctionParameterNames(functionName)){
          // Parameter was found, setting Declared node type.
          if (parameterName.equals(((DeclaredNode) node).getDeclared())){
            ((DeclaredNode) node).setType(this.table.getParameterType(
                functionName, parameterName));
            return;
          }
        }
        // If we make it to here then the Identifier was not found.
        // DECLARED NODE IDENTIFIER NOT A VALID FUNCTION PARAMETER
      }
      if (node instanceof OperatorNode){
        // If a Unary Operator set Operator Node to type of right child
        if (((OperatorNode) node).getChildren()[0] instanceof NullNode){
          ((OperatorNode) node).setType(
              ((OperatorNode) node).getChildren()[0].getType());
        }
        else { // AND the types of the two children together
          ((OperatorNode) node).setType(
              ((OperatorNode) node).getChildren()[0].getType() &
                  ((OperatorNode) node).getChildren()[1].getType());
        }
        if (((AbstractSyntaxNode) node).getType() > 7){
          throw new SemanticException("Invalid Type");
        }
      }



    }
  }

  private void traversePreOrder(AbstractSyntaxNode node, NodeOperation op ,
                                String name) {
    String functionName = name;
    if(node instanceof NullNode) {
      return;
    }
    if (node instanceof FunctionNode){
      functionName = ((FunctionNode) node).getName().getValue();
    }
    for(AbstractSyntaxNode child : node.getChildren()) {
      this.traversePreOrder(child, op, functionName);
    }
    op.execute(node, functionName);
  }

}
