package com.dukes.klein.semanticchecker;

import com.dukes.klein.parser.node.FormalNode;
import com.dukes.klein.parser.node.FunctionNode;
import com.dukes.lang.parser.node.AbstractSyntaxNode;
import com.dukes.lang.semanticchecker.AbstractFunctionTable;
import com.dukes.lang.semanticchecker.SemanticException;
import com.sun.org.apache.xpath.internal.operations.Bool;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

/**
 * @author Daniel J. Holland
 * @version 1.0
 */
public class KleinFunctionTable extends AbstractFunctionTable {

  public KleinFunctionTable(AbstractSyntaxNode top) {
    LinkedHashMap<String, Integer> functionValues;
    for(AbstractSyntaxNode functionNode : top.getChildren()) {
      functionValues = new LinkedHashMap<String, Integer>();
      functionValues.put("", functionNode.getType());
      for(AbstractSyntaxNode node : functionNode.getChildren()) {
        if(node instanceof FormalNode) {
          functionValues.put(((FormalNode) node).getIdentifier(),
              ((FormalNode) node).getType());
        }
      }
      // Check for duplicate errors
      if(this.functionParamTable.containsKey(
          ((FunctionNode) functionNode).getName().getValue())) {
        System.out.println(//throw new SemanticException(
            "Semantic Error: Multiple functions with name '" +
                ((FunctionNode) functionNode).getName().getValue() +
                "' found. Function names must be unique.");
      }
      this.functionParamTable.put(
          ((FunctionNode) functionNode).getName().getValue(), functionValues);
      this.functionCallTable.put(
          ((FunctionNode) functionNode).getName().getValue(),
          new ArrayList<String>());
    }
  }

  @Override
  public int getFunctionReturnType(String functionName)
      throws SemanticException {
    if(this.functionParamTable.containsKey(functionName)) {
      return this.functionParamTable.get(functionName).get("");
    }
    throw new SemanticException(
        "Function Named '" + functionName + "' Not Found in the Table");
  }

  @Override
  public ArrayList<String> getFunctionParameterNames(String functionName) {
    ArrayList<String> returnList = new ArrayList<String>();
    if(functionName == null || functionName.equals("")) {
      returnList.add("");
    }
    else {
      for(String name : this.functionParamTable.get(functionName).keySet()) {
        if(!name.equals("")) {
          returnList.add(name);
        }
      }
    }
    return returnList;
  }

  @Override
  public ArrayList<String> getFunctionNames() {
    return new ArrayList<String>(this.functionParamTable.keySet());
  }

  @Override
  public ArrayList<Integer> getFunctionParameterTypes(String functionName) {
    ArrayList<Integer> returnList = new ArrayList<Integer>();
    for(String name : this.functionParamTable.get(functionName).keySet()) {
      if(!name.equals("")) {
        returnList.add(this.functionParamTable.get(functionName).get(name));
      }
    }
    return returnList;
  }

  @Override
  public int getParameterType(String functionName, String formalName) {
    return this.functionParamTable.get(functionName).get(formalName);
  }

  /**
   * Will add the name of the function from which a function call is being
   * made to the listing of the called function.
   *
   * @param calledFromFunction The function in which the call is made.
   * @param calledFunction The function being called.
   */
  @Override
  public void addFunctionCall(String calledFromFunction, String calledFunction) {
    this.functionCallTable.get(calledFunction).add(calledFromFunction);
  }

  @Override
  public Boolean containsFunction(String functionName){
    return this.functionParamTable.containsKey(functionName);
  }

  /**
   * Get the names of all functions that are never called.
   *
   * @return List of function names as Strings
   */
  public ArrayList<String> getUncalledFunctions() {
    ArrayList<String> retList = new ArrayList<String>();
    for(String functionName : this.functionCallTable.keySet()) {
      if(this.functionCallTable.get(functionName).size() == 0) {
        retList.add(functionName);
      }
    }
    return retList;
  }
}
