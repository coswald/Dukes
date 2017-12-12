/*
 * Copyright (C) 2017 Coved W Oswald, Daniel Holland, and Patrick Sedlacek
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
package com.dukes.klein.parser.node;

import com.dukes.lang.parser.node.ExpressionNode;

/**
 * A wrapper class for any String value. This allows classes to put Nodes on
 * the stack without doing anything with these nodes. They are used to
 * construct other nodes.
 * @author Daniel J. Holland
 * @version 1.0
 * @since 0.3.0
 */
public class TerminalNode extends ExpressionNode {

  private String value;
  
  /**
   * Makes a terminal node with the given value.
   * @param value The value of the terminal node.
   */
  public TerminalNode(String value) {
    super();
    this.value = value;
  }
  
  /**
   * Returns the terminal this node represents.
   * @return The terminal of this node. 
   */
  public String getValue() {
    return this.value;
  }
  
  /**
   * Returns the value of this terminal. This will call {@link #getValue()}, so
   * override that method if you wish to change the output of both functions.
   * @return The value as a String.
   */
  @Override
  public String dataAsString() {
    return this.getValue();
  }
  
  /**
   * Prints the terminal all pretty like.
   * @return The {@link #dataAsString()} return value.
   */
  @Override
  public String prettyPrint() {
    return this.dataAsString();
  }
  
  @Override
  public String toTargetCode() {
    return "";
  }
  
  @Override
  public String getReturnRegister() {
    return "";
  }
}
