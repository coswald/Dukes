package com.dukes.klein.semanticchecker;

import com.dukes.klein.parser.node.FormalNode;
import com.dukes.klein.parser.node.FunctionNode;
import com.dukes.klein.parser.node.PrintNode;
import com.dukes.lang.parser.node.AbstractSyntaxNode;
import com.dukes.lang.semanticchecker.AbstractFunctionTable;
import com.dukes.lang.semanticchecker.SemanticException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

/**
 * @author Daniel J. Holland
 * @version 1.0
 */
public class KleinFunctionTable extends AbstractFunctionTable {
  private boolean usedPrint = false;
  private HashMap<String, HashMap<String, Integer>> paramLocations;
  
  public KleinFunctionTable(AbstractSyntaxNode top) {
    paramLocations = new HashMap<String, HashMap<String, Integer>>();
    for(AbstractSyntaxNode functionNode : top.getChildren()) {
      paramLocations.put(((FunctionNode)functionNode).getName().getValue(),
          ((FunctionNode)functionNode).getParamMemory());
    }
    
    LinkedHashMap<String, Integer> functionValues;
    for(AbstractSyntaxNode functionNode : top.getChildren()) {
      functionValues = new LinkedHashMap<String, Integer>();
      functionValues.put("", functionNode.getType());
      for(AbstractSyntaxNode node : functionNode.getChildren()) {
        if(node instanceof FormalNode) {
          functionValues.put(((FormalNode) node).getIdentifier(),
              node.getType());
        }
        else {
          for(AbstractSyntaxNode n : node.getChildren()) {
            if(n instanceof PrintNode) {
              this.usedPrint = true;
              break;
            }
          }
        }
      }
      // Check for duplicate errors
      if(this.functionParamTable.containsKey(
          ((FunctionNode) functionNode).getName().getValue())) {
        System.out.println(String.format(
            "SemanticError: Multiple functions with the name '%s' found. " +
                "Function names must be unique.",
            ((FunctionNode) functionNode).getName().getValue())
        );
      }
      this.functionParamTable.put(
          ((FunctionNode) functionNode).getName().getValue(), functionValues);
      this.functionCallTable.put(
          ((FunctionNode) functionNode).getName().getValue(),
          new ArrayList<String>());
    }
  }

  public HashMap<String, HashMap<String, Integer>> getParamLocations(){
    return this.paramLocations;
  }

  public HashMap<String, Integer> getParamLocationValues(String functionName) {
    return this.paramLocations.get(functionName);
  }

  public Integer getParamLocationValues(String functionName, String paramName) {
    return this.paramLocations.get(functionName).get(paramName);
  }

  public boolean hasPrint() {
    return this.usedPrint;
  }

  @Override
  public int getFunctionReturnType(String functionName)
      throws SemanticException {
    if(this.functionParamTable.containsKey(functionName)) {
      return this.functionParamTable.get(functionName).get("");
    }
    throw new SemanticException(String.format(
        "Function Named '%s' Not Found in the Table", functionName)
    );
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
   * @param calledFunction     The function being called.
   */
  @Override
  public void addFunctionCall(String calledFromFunction,
                              String calledFunction) {
    this.functionCallTable.get(calledFunction).add(calledFromFunction);
  }

  @Override
  public Boolean containsFunction(String functionName) {
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
      if(this.functionCallTable.get(functionName).size() == 0 &&
          !functionName.equals("main")) {
        retList.add(functionName);
      }
    }
    return retList;
  }
}
