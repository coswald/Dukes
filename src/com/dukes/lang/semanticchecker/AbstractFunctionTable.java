package com.dukes.lang.semanticchecker;

import com.dukes.lang.parser.node.AbstractSyntaxNode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

/**
 * @author Daniel J. Holland
 * @version 1.0
 */
public abstract class AbstractFunctionTable extends Object {

  protected HashMap<String, LinkedHashMap<String, Integer>> functionParamTable =
      new HashMap<String, LinkedHashMap<String, Integer>>();

  protected HashMap<String, ArrayList<String>> functionCallTable =
      new HashMap<String, ArrayList<String>>();

  public void AbstractFunctionTable(AbstractSyntaxNode top) {}

  public int getFunctionReturnType(String functionName)
      throws SemanticException {
    return 0;
  }

  public ArrayList<String> getFunctionParameterNames(String functionName) {
    return new ArrayList<String>();
  }

  public ArrayList<String> getFunctionNames() {
    return new ArrayList<String>();
  }

  public ArrayList<Integer> getFunctionParameterTypes(String functionName) {
    return new ArrayList<Integer>();
  }

  public int getParameterType(String functionName, String formalName) {
    return 0;
  }

  public void addFunctionCall(String calledFromFunction, String calledFunction){}

  public Boolean containsFunction(String functionName){
    return false;
  }
}
