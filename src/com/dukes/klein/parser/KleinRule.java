package com.dukes.klein.parser;

import java.util.ArrayList;
import java.util.ListIterator;
import java.util.Stack;

@SuppressWarnings("unchecked")
public class KleinRule {

  private ArrayList<Enum> rule;
  private boolean exists;

  public KleinRule(ArrayList<Enum> rule) {
    this.rule = rule;
    this.exists = true;
  }

  public KleinRule() {
    this.rule = null;
    this.exists = false;

  }

  public ArrayList<Enum> getRule() {
    return this.rule;
  }

  public void pushRule(Stack stack) {
    if(this.exists) {
      ListIterator ri = this.rule.listIterator(this.rule.size());
      while(ri.hasPrevious()) {
        stack.push(ri.previous());
      }
    }
  }

  public boolean exists() {
    return this.exists;
  }

  @Override
  public String toString() {
    String s = "";
    if(exists) {
      for(Enum e : this.rule) {
        s += e + " ";
      }
    }
    else {
      s = "Doesn't Exist!";
    }
    return s;
  }
}
