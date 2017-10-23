package com.dukes.lang.semanticchecker;

import com.dukes.lang.parser.node.AbstractTreeTraverser;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * @author Daniel J. Holland
 * @version 1.0
 */
public abstract class AbstractFunctionTable extends Object {

  private HashMap<String, HashMap<String, Integer>> table;

  public void AbstractFunctionTable(AbstractTreeTraverser astt) {
  }

  public int getReturnType(String functionName) {
    return 0;
  }

  public ArrayList<String> getParameterNames(String functionName) {
    return new ArrayList<String>();
  }

  public ArrayList<Integer> getAllParameterTypes(String functionName) {
    return new ArrayList<Integer>();
  }

  public int getParameterType(String functionName, String formalName) {
    return 0;
  }
}
