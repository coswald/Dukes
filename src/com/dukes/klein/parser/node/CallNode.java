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
import com.dukes.klein.generator.KleinCodeGenerator;

/**
 * Defines a function call in Klein. This contains the identifier and a number
 * of arguments.
 * @author Coved W Oswald
 * @version 1.0
 * @since 0.3.0
 */
public class CallNode extends ExpressionNode {
  private TerminalNode identifier;
  private String returnRegister;
  /**
   * Creates a call node.
   * @param identifier The identifier
   * @param exprNodes The expression list within the calling node.
   */
  public CallNode(TerminalNode identifier, ExpressionNode... exprNodes) {
    super(exprNodes);
    this.identifier = identifier;
    this.returnRegister = KleinCodeGenerator.getMemoryHolder();
  }
  
  /**
   * Gets the identifier.
   * @return The identifier.
   */
  public String getIdentifier() {
    return this.identifier.getValue();
  }

  /**
   * Gets the identifier between two brackets.
   * @return The {@link #getIdentifier()} function.
   */
  @Override
  public String dataAsString() {
    return "[Identifier: " + this.getIdentifier() + ", Type: " +
        this.typeToString() +"]";
  }
  
  @Override
  public String toTargetCode() {
    //template
    String s = "";
    s += KleinCodeGenerator.emitCode("ST", "6", returnRegister, "0");
    s += KleinCodeGenerator.emitCode("LDA", "6", "1", "7");
    s += KleinCodeGenerator.emitCode("LDA", "7", this.identifier.getValue(), "0");
    s += KleinCodeGenerator.emitCode("LD", "6", returnRegister, "0");
    s += "* END Call to " + this.identifier.getValue().toUpperCase() + "\n";
    return s;
  }
  
  @Override
  public String getReturnRegister() {
    return this.returnRegister;
  }
}
