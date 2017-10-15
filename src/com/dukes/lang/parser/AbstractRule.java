package com.dukes.lang.parser;

import java.util.List;
import java.util.ListIterator;
import java.util.Stack;

public abstract class AbstractRule {  
  protected List<Enum> rule;
  private boolean exists;

  protected AbstractRule(List<Enum> rule) {
    this.rule = rule;
    this.exists = (rule != null);
  }

  protected AbstractRule() {
    this(null);
  }

  public List<Enum> getRule() {
    return this.rule;
  }

  public void pushRule(Stack<Enum> stack) {
    if(this.exists) {
      ListIterator ri = this.rule.listIterator(this.rule.size());
      while(ri.hasPrevious()) {
        stack.push((Enum)ri.previous());
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
