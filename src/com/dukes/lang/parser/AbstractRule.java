package com.dukes.lang.parser;

import java.util.List;
import java.util.ListIterator;
import java.util.Stack;

/**
 * @author Coved W Oswald
 * @author Daniel Holland
 * @version 1.0
 * @since 0.2.0
 */
public abstract class AbstractRule {  
  
  /**
   * The rule represented as a list.
   */
  protected List<Enum> rule;
  
  private boolean exists;
  
  /**
   * Creates a rule. This also determines whether a rule "exists". This is done
   * by saying that a rule that has a {@code null} value does not exist, while
   * any other value does.
   * @param rule The rule, represented as a list.
   */
  protected AbstractRule(List<Enum> rule) {
    this.rule = rule;
    this.exists = (rule != null);
  }

  /**
   * Constructs a non-existing rule.
   * @see #AbstractRule(List)
   */
  protected AbstractRule() {
    this(null);
  }
  
  /**
   * Gets the rule, represented as a list.
   * @return The list representation of this rule.
   */
  public List<Enum> getRule() {
    return this.rule;
  }
  
  /**
   * Pushes a rule to the given stack. This is done in reverse order, so the
   * first value of the list is pushed last. This is expected, as the last part
   * of a rule should be seen last in the stack.
   * @param stack The stack to push the rule to.
   */
  public void pushRule(Stack<Enum> stack) {
    if(this.exists) {
      ListIterator ri = this.rule.listIterator(this.rule.size());
      while(ri.hasPrevious()) {
        stack.push((Enum)ri.previous());
      }
    }
  }

  /**
   * Determines whether the rule exists or not. This is represented as a
   * {@code null} list.
   * @return {@code true} if the rule exists, {@code false} otherwise.
   */
  public boolean exists() {
    return this.exists;
  }

  /**
   * Gives a string representation of the rule. If the rule exists, it is
   * simply space seperated values of the rule, if it does not exist, this is
   * the string "Doesn't Exist!".
   * @return A string representation of the rule.
   */
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
