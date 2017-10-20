/*
 * Copyright (C) 2017 Coved W Oswald, Dan Holland, Patrick Sedlacek
 * 
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License along
 * with this program; if not, write to the Free Software Foundation, Inc.,
 * 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.  
 */
package com.dukes.lang.parser;

import java.util.List;
import java.util.ListIterator;
import java.util.Stack;

/**
 * Describes a rule that would be found within a parse table. The rule is
 * represented as a {@code List}, as this would allow an implementation to
 * supply the type of the rule that would fit the implementation the best.
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
