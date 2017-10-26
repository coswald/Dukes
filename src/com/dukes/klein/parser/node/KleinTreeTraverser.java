package com.dukes.klein.parser.node;

import com.dukes.klein.semanticchecker.KleinFunctionTable;
import com.dukes.lang.parser.node.AbstractSyntaxNode;
import com.dukes.lang.parser.node.AbstractTreeTraverser;
import com.dukes.lang.parser.node.NullNode;

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
    this.traversePreOrder(this.top, new TypeCheck(functionTable));
  }

  private interface NodeOperation {
    public void execute(Object data);
  }

  private class TypeCheck implements NodeOperation{

    KleinFunctionTable table;

    public TypeCheck(KleinFunctionTable table){
      this.table = table;
    }

    @Override
    public void execute(Object node){
      node = (AbstractSyntaxNode) node;
      if (node instanceof DeclaredNode){

      }
      if (node instanceof OperatorNode){
      }


    }
  }

  private void traversePreOrder(AbstractSyntaxNode node, NodeOperation op) {
    if(node instanceof NullNode) {
      return;
    }
    for(AbstractSyntaxNode child : node.getChildren()) {
      this.traversePreOrder(child, op);
    }
    op.execute(node);
  }

}
