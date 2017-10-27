package com.dukes.lang.semanticchecker;

import com.dukes.lang.parser.node.AbstractSyntaxNode;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * @author Daniel J. Holland
 * @version 1.0
 */
public abstract class AbstractFunctionTable extends Object {

  protected HashMap<String, HashMap<String, Integer>> table =
      new HashMap<String, HashMap<String, Integer>>();

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
}
