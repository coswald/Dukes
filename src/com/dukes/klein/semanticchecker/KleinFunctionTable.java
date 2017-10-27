package com.dukes.klein.semanticchecker;

import com.dukes.klein.parser.node.FormalNode;
import com.dukes.klein.parser.node.FunctionNode;
import com.dukes.lang.parser.node.AbstractSyntaxNode;
import com.dukes.lang.semanticchecker.AbstractFunctionTable;
import com.dukes.lang.semanticchecker.SemanticException;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * @author Daniel J. Holland
 * @version 1.0
 */
public class KleinFunctionTable extends AbstractFunctionTable {

  public KleinFunctionTable(AbstractSyntaxNode top) {
    HashMap<String, Integer> functionValues;
    for(AbstractSyntaxNode functionNode : top.getChildren()) {
      functionValues = new HashMap<String, Integer>();
      functionValues.put("", functionNode.getType());
      for(AbstractSyntaxNode node : functionNode.getChildren()) {
        if(node instanceof FormalNode) {
          functionValues.put(((FormalNode) node).getIdentifier(),
              ((FormalNode) node).getType());
        }
      }
      this.table.put(((FunctionNode) functionNode).getName().getValue(),
          functionValues);
      //System.out.print("test");
    }
  }

  @Override
  public int getFunctionReturnType(String functionName)
      throws SemanticException {
    if (this.table.containsKey(functionName)){
      return this.table.get(functionName).get("");
    }
    throw new SemanticException(
        "Function Named '" + functionName + "' Not Found in Table");
  }

  @Override
  public ArrayList<String> getFunctionParameterNames(String functionName) {
    ArrayList<String> returnList = new ArrayList<String>();
    if(functionName == null || functionName.equals("")) {
      returnList.add("");
    }
    else {
      for(String name : this.table.get(functionName).keySet()) {
        if(!name.equals("")) {
          returnList.add(name);
        }
      }
    }

    return returnList;
  }

  @Override
  public ArrayList<String> getFunctionNames() {
    return new ArrayList<String>(this.table.keySet());
  }

  @Override
  public ArrayList<Integer> getFunctionParameterTypes(String functionName) {
    ArrayList<Integer> returnList = new ArrayList<Integer>();
    for(String name : this.table.get(functionName).keySet()) {
      if(!name.equals("")) {
        returnList.add(this.table.get(functionName).get(name));
      }
    }
    return returnList;
  }

  @Override
  public int getParameterType(String functionName, String formalName) {
    return this.table.get(functionName).get(formalName);
  }

}
