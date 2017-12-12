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

import com.dukes.lang.parser.node.AbstractSyntaxNode;
import com.dukes.lang.parser.node.ExpressionNode;
import com.dukes.klein.generator.KleinCodeGenerator;

/**
 * A node representing a print statement in Klein. This is more than a function
 * call, as a print statement can either consist of a integer or a boolean.
 * This seperates it from other functions, and is therefore seperated. Another
 * reason it is special is that a function can contain only one expression, but
 * many print statements.
 * @author Coved W Oswald
 * @version 1.0
 * @since 0.3.0
 */
public class PrintNode extends AbstractSyntaxNode {
  private String returnRegister;
  
  /**
   * Creates a print statement with the given expression.
   * @param exprNode The expression to print.
   */
  public PrintNode(ExpressionNode exprNode) {
    super(exprNode);
    this.type = AbstractSyntaxNode.BOOL_OR_INT_TYPE;
    returnRegister = KleinCodeGenerator.getMemoryHolder();
  }

  /**
   * Gets the type between two brackets.
   * @return The {@link #typeToString()} function.
   */
  @Override
  public String dataAsString() {
    return "[Type: " + this.typeToString() +"]";
  }
  
  @Override
  public String toTargetCode() {
    String s = "";
    s += KleinCodeGenerator.emitCode("ST", "6", returnRegister, "0");
    s += KleinCodeGenerator.emitCode("LDA", "6", "1", "7");
    s += KleinCodeGenerator.emitCode("LDA", "7", "print", "0");
    s += KleinCodeGenerator.emitCode("LD", "6", returnRegister, "0");
    s += "* END Call to PRINT \n";
    return s;
  }
  
  @Override
  public String getReturnRegister() {
    return this.returnRegister;
  }
}
